package com.proper.data.replein.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.proper.data.binmove.Bin;
import com.proper.data.replein.ReplenMiniMove;
import com.proper.warehousetools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lebel on 08/09/2014.
 */
public class ReplenMiniMoveAdapter extends BaseAdapter {
    private Context context;
    private List<ReplenMiniMove> moves;

    public ReplenMiniMoveAdapter(Context ctxt, List<ReplenMiniMove> moveList) {
        this.context = ctxt;
        this.moves = moveList;
    }

    @Override
    public int getCount() {
        return moves.size();
    }

    @Override
    public ReplenMiniMove getItem(int position) {
        return moves.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myView = convertView;
        if (myView == null) {
            LayoutInflater inflater = (LayoutInflater)  context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myView = inflater.inflate(R.layout.list_replen_minimove, parent, false);
        }
        ReplenMiniMove thisMove = moves.get(position);
        TextView txtBin = (TextView) myView.findViewById(R.id.txtvReplenMiniMoveDestination);
        TextView txtQuantity = (TextView) myView.findViewById(R.id.txtvReplenMiniMoveQuantity);

        txtBin.setText(String.format("Dst Bin: %s", thisMove.getDestination()));
        txtQuantity.setText(String.format("Qty: %s", thisMove.getQuantity()));
        return myView;
    }

    public void add(ReplenMiniMove miniMove) {
        this.moves.add(miniMove);
        this.notifyDataSetChanged();
    }

    public List<Bin> getAllBins() {
        List<Bin> bins = new ArrayList<Bin>();
        for (ReplenMiniMove move : moves) {
            Bin bin = new Bin(move.getDestination(), move.getQuantity());
            bins.add(bin);
        }
        return bins;
    }
}
