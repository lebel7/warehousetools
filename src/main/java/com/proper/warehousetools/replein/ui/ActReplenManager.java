package com.proper.warehousetools.replein.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.*;
import com.proper.data.binmove.Bin;
import com.proper.data.binmove.ProductBinResponse;
import com.proper.data.binmove.ProductResponse;
import com.proper.data.replein.ReplenMiniMove;
import com.proper.data.replein.adapters.ReplenMiniMoveAdapter;
import com.proper.utils.BinQuantitySorted;
import com.proper.warehousetools.R;
import com.proper.warehousetools.binmove.BaseActivity;
import com.proper.warehousetools.replein.ui.chainway_C4000.ActReplenCreateMiniMove;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Lebel on 04/09/2014.
 */
public class ActReplenManager extends BaseActivity {
    private SharedPreferences prefs = null;
    private LinearLayout mainLayout;
    private TextView txtProductDetails;
    private TextView txtPalate;
    private TextView txtQty;
    private TextView txtListTile;
    private Button btnNewMove;
    private Button btnExit;
    private ListView lvRepelen;
    private List<ProductBinResponse> inputList;
    private List<ReplenMiniMove> moveList = new ArrayList<ReplenMiniMove>();
    private ReplenMiniMoveAdapter adapter;
    private List<Bin> primaryList = new ArrayList<Bin>();
    private String currentSource = "";
    private int tot = 0;
    private int backParam = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_replen_manager);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.flat_button_palebrown));
        getSupportActionBar().setTitle("Manage Replen");

        Bundle extra = getIntent().getExtras();
        if (extra == null) throw new RuntimeException("onCreate: Bundled Extra cannot be null!, Line: 44");
        prefs = getSharedPreferences("Proper_Replen", Context.MODE_PRIVATE);        //get preferences

        inputList = new ArrayList<ProductBinResponse>();
        inputList = (List<ProductBinResponse>) extra.getSerializable("PRODUCT_EXTRA");
        currentSource = extra.getString("SOURCE_EXTRA");
        primaryList = (List<Bin>) extra.getSerializable("PRIMARY_EXTRA");

        mainLayout = (LinearLayout) this.findViewById(R.id.lytReplenManager);
        txtProductDetails = (TextView) this.findViewById(R.id.txtvReplenManagerProductTitle);
        txtPalate = (TextView) this.findViewById(R.id.txtvReplenManagerPalate);
        txtQty = (TextView) this.findViewById(R.id.txtvReplenManagerTotalQuantity);
        btnNewMove = (Button) this.findViewById(R.id.bnReplenNewMove);
        btnExit = (Button) this.findViewById(R.id.bnExitActReplenManager);
        txtListTile = (TextView) this.findViewById(R.id.txtvReplenManListTitle);
        lvRepelen = (ListView) this.findViewById(R.id.lvReplenManagerMoves);
//        lvRepelen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                onListItemClicked(adapterView, view, position, id);
//            }
//        });

        btnNewMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(v);
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked(v);
            }
        });

        for (ProductBinResponse prod : inputList) {
            tot = tot + prod.getQtyInBin();
        }

        txtProductDetails.setText(inputList.get(0).getArtist() + " - " + inputList.get(0).getTitle());
        txtPalate.setText(currentSource);
        txtQty.setText(String.format("%s", tot));
        adapter = new ReplenMiniMoveAdapter(ActReplenManager.this, moveList);
        lvRepelen.setAdapter(adapter);

        if (adapter.isEmpty()) {
            txtListTile.setVisibility(View.GONE);
        }
        saveQuantityData();
    }

//    private void onListItemClicked(AdapterView<?> adapterView, View view, int position, long id) {
        //do
//        this.setSelectedProduct(thisBinResponse.getProducts().get(pos));
//        this.setCurrentBinSelection(this.getMoveList().get(pos));       //current selection
//        this.setCurrentSelectedIndex(pos);
//        this.lvProducts.setItemChecked(pos, true);
//        view.setSelected(true);
//        long i = id;
//        updateTitleBar();
//    }

    private void saveQuantityData() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("From", currentSource);
        editor.putInt("Quantity", tot);
        editor.commit();
    }

    private void buildPrimaryLocations() {
        if (!adapter.isEmpty()) {
            //List<Bin> bins = adapter.getAllBins();
            //List<Bin> bins = moveList;
            List<Bin> bins = new ArrayList<Bin>();
            for (ReplenMiniMove move : moveList) {
                Bin bin = new Bin(move.getDestination(), move.getQuantity());
                bins.add(bin);
            }
            for (int i = 0; i < bins.size(); i ++) {
                if (bins.get(i).getBinCode().substring(0, 1).equalsIgnoreCase("1")) {
                    bins.add(bins.get(i));
                }
            }
            primaryList = bins;
//            for (Bin bin : adapter.getAllBins()) {
//                if (bin.getBinCode().substring(0, 1).equalsIgnoreCase("1")) {
//                    bins.add(bin);
//                }
//            }
        }

    }

    private void onButtonClicked(View v) {
        switch (v.getId()) {
            case R.id.bnExitActReplenManager:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                ActReplenManager.this.finish();
                break;
            case R.id.bnReplenNewMove:
                //Navigate to the scanning screen to acquire a move (dst + qty)
                int qtyParam = 0;
                int qty = prefs.getInt("NewQuantity", 0);
                if (qty > 0) {
                    qtyParam = qty;
                }else {
                    qtyParam = inputList.get(0).getQtyInBin();
                }
                backParam ++;
                if (primaryList.size() == 0) buildPrimaryLocations();
                Intent i = new Intent(ActReplenManager.this, ActReplenCreateMiniMove.class);
                i.putExtra("QUANTITY_EXTRA", qtyParam);
                i.putExtra("DATA_EXTRA", (java.io.Serializable) inputList);
                i.putExtra("SOURCE_EXTRA", currentSource);
                i.putExtra("PRIMARY_EXTRA", (java.io.Serializable) primaryList);
                startActivityForResult(i, RESULT_OK);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (backParam > 0) {
            try {
                prefs = getSharedPreferences("Proper_Replen", Context.MODE_PRIVATE);
                if (prefs != null) {
                    int lastQty = prefs.getInt("LastQuantity", 0);
                    int oldQty = prefs.getInt("OldQuantity", 0);
                    int newQty = prefs.getInt("NewQuantity", 0);
                    int move = oldQty - newQty;
                    if (lastQty != 0 && lastQty <= newQty) {
                        if (oldQty != 0 && newQty != 0) {
                            ReplenMiniMove miniMove = new ReplenMiniMove(prefs.getString("LastDestination", ""), move);
                            if (miniMove.getQuantity() != 0 && !miniMove.getDestination().isEmpty()) {
                                SharedPreferences.Editor editor = prefs.edit();
                                //editor.putInt("LastQuantity", newQty);
                                editor.putInt("Quantity", newQty);
                                editor.commit();
                                //adapter.add(miniMove);      //update listView
                                moveList.add(miniMove);
                                adapter.notifyDataSetChanged();
                                txtQty.setText(String.format("%s", newQty));      //update the total
                                if (!adapter.isEmpty()) {
                                    if (txtListTile.getVisibility() != View.VISIBLE) txtListTile.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            backParam = 0;
        }
    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //Return ReplenMiniMove so that we can subtract from the total and add the move to the list
//        Bundle extra = data.getExtras();
//        ReplenMiniMove miniMove = (ReplenMiniMove) extra.getSerializable("RETURN_EXTRA");
//        adapter.add(miniMove);      //update the listView
//        txtQty.setText(String.format("%s", tot - miniMove.getQuantity()));      //update the total
//        super.onActivityResult(requestCode, resultCode, data);
////        moveList.add(miniMove);
////        adapter.notifyDataSetChanged();
//    }
}