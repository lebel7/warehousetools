package com.proper.warehousetools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.proper.adapters.ModuleAdapter;
import com.proper.data.binmove.Module;
import com.proper.data.diagnostics.LogEntry;
import com.proper.warehousetools.binmove.BaseActivity;
import com.proper.warehousetools.goodsin.ui.ActGoodsInManager;

import java.util.ArrayList;
import java.util.List;

public class ActMain extends BaseActivity {
    private TextView lblTitle;
    private ListView lvModule;
    private ModuleAdapter adapter;
    private List<Module> lstModule = new ArrayList<Module>();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getSupportActionBar().setLogo(R.drawable.ic_launcher);
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.back));
            getSupportActionBar().setTitle(String.format("%s %s", getString(R.string.app_name),
                    appContext.getPackageInfo().versionName));
        }
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.lyt_main);
        lvModule = (ListView) findViewById(R.id.lvModule);
        initData();
    }

    private void initData() {

        lstModule.clear();

        String[] modules = getResources().getStringArray(R.array.defaultname);
        String[] moduleIcons = getResources().getStringArray(R.array.defaulticon);

        Module module = null;
        int icon = -1;
        for (int i = 0; i < modules.length; i++) {
            try {
                if (screenSize != Configuration.SCREENLAYOUT_SIZE_SMALL) {
                    icon = getResources().getIdentifier(moduleIcons[i], "drawable",
                            getPackageName());
                } else {
                    //icon = R.drawable.na;
                    //icon = null;
                }
            } catch (Exception e) {
                icon = R.drawable.na;
            }

            //module = new Module(modules[i], icon, moduleClasss[i]);
            module = new Module(modules[i], icon, null);

            lstModule.add(module);
        }

        adapter = new ModuleAdapter(ActMain.this, lstModule,
                R.layout.module_list_item);

        lvModule.setAdapter(adapter);

        lvModule.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos,
                                    long id) {

                Log.i("MY", "arg2=" + pos);

                //lstModule.get(pos).toActivity(ActMain.this);

                //Navigate to chosen activity
                Intent i = null;
                switch (pos) {
                    case 0:
                        //Navigate to BinMove entry point
                        if (screenSize != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                            i =  new Intent(ActMain.this, com.proper.warehousetools.binmove.ui.ActChooser.class);
                            startActivityForResult(i, RESULT_FIRST_USER);
                        } else {
                            String mMsg = "Coming Soon.\nPlease try again later";
                            AlertDialog.Builder builder = new AlertDialog.Builder(ActMain.this);
                            builder.setMessage(mMsg)
                                    .setPositiveButton(R.string.but_ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do nothing
                                        }
                                    });
                            builder.show();
                        }
                        break;
                    case 3:
                        if (screenSize != Configuration.SCREENLAYOUT_SIZE_XLARGE) {
                            i = new Intent(ActMain.this, com.proper.warehousetools.replein.ActReplenChooser.class);
                            startActivityForResult(i, RESULT_FIRST_USER);
                        } else {
                            String mMsg = "Coming Soon.\nPlease try again later";
                            AlertDialog.Builder builder = new AlertDialog.Builder(ActMain.this);
                            builder.setMessage(mMsg)
                                    .setPositiveButton(R.string.but_ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //do nothing
                                        }
                                    });
                            builder.show();
                        }
                        break;
                    case 1:

                        String toastMsg;
                        switch(screenSize) {
                            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                                toastMsg = "Extra Large screen";
                                i = new Intent(ActMain.this, ActGoodsInManager.class);
                                startActivityForResult(i, RESULT_FIRST_USER);
                                break;
                            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                                toastMsg = "Large screen";
                                String mMsgx = "Device Incompatible\nPlease try the tablet instead";
                                AlertDialog.Builder builderx = new AlertDialog.Builder(ActMain.this);
                                builderx.setMessage(mMsgx)
                                        .setPositiveButton(R.string.but_ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do nothing
                                            }
                                        });
                                builderx.show();
                                break;
                            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                                toastMsg = "Normal screen";
                                String mMsg = "Device Incompatible\nPlease try the tablet instead";
                                AlertDialog.Builder builder = new AlertDialog.Builder(ActMain.this);
                                builder.setMessage(mMsg)
                                        .setPositiveButton(R.string.but_ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do nothing
                                            }
                                        });
                                builder.show();
                                break;
                            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                                toastMsg = "Small screen";
                                String mMsg1 = "Device Incompatible\nPlease try the tablet instead";
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ActMain.this);
                                builder1.setMessage(mMsg1)
                                        .setPositiveButton(R.string.but_ok, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //do nothing
                                            }
                                        });
                                builder1.show();
                                break;
                            default:
                                toastMsg = "Screen size is neither large, normal or small";
                        }
                        Toast.makeText(ActMain.this, toastMsg, Toast.LENGTH_LONG).show();

//                        if (deviceID.equalsIgnoreCase(getResources().getString(R.string.LargeDevice))) {
//                            int currentVersion = Build.VERSION.SDK_INT;
//                            if (currentVersion > 15) {
//                                i = new Intent(ActMain.this, com.proper.warehousetools.goodsin.ui.ActGoodsInManager.class);
//                                startActivityForResult(i, RESULT_FIRST_USER);
//                            } else {
//                                String mMsg = "Device Incompatible\nPlease try the tablet instead";
//                                AlertDialog.Builder builder = new AlertDialog.Builder(ActMain.this);
//                                builder.setMessage(mMsg)
//                                        .setPositiveButton(R.string.but_ok, new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                //do nothing
//                                            }
//                                        });
//                                builder.show();
//                            }
//                            break;
//                        }
                        break;
                    default:
                        //Navigate to Goods-In,... entry point
                        String mMsg = "Coming Soon.\nPlease try again later";
                        AlertDialog.Builder builder = new AlertDialog.Builder(ActMain.this);
                        builder.setMessage(mMsg)
                                .setPositiveButton(R.string.but_ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //do nothing
                                    }
                                });
                        builder.show();
                        break;
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            authenticator.logOffUser();
            //soundPool.release();
            if (screenSize != Configuration.SCREENLAYOUT_SIZE_SMALL) {
                overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
            }
        } catch (Exception e) {
            e.printStackTrace();
            today = new java.sql.Timestamp(utilDate.getTime());
            LogEntry log = new LogEntry(1L, ApplicationID, "ActChooser - Attempting Logout - onDestroy", deviceIMEI, e.getClass().getSimpleName(), e.getMessage(), today);
            logger.log(log);
        }
        //android.os.Process.killProcess(android.os.Process.myPid()); //kill it! never returns to login screen
        appManager.AppExit(this);
    }
}
