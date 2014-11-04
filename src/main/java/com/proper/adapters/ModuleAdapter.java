package com.proper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.proper.data.binmove.Module;
import com.proper.warehousetools.R;

import java.util.List;

/**
 * Created by Lebel on 27/08/2014.
 */
public class ModuleAdapter extends BaseAdapter {
    private List<Module> listItems;
    private Context mContext;
    private LayoutInflater listContainer;
    private int itemViewResource;

    static class ListItemView {
        public TextView tvTitle;
        public ImageView ivIcon;
    }

    public ModuleAdapter(Context context, List<Module> modules, int resource) {
        mContext = context;
        this.listContainer = LayoutInflater.from(context);
        listItems = modules;
        this.itemViewResource = resource;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListItemView listItemView = null;

        if (convertView == null) {
            convertView = listContainer.inflate(this.itemViewResource, null);
            listItemView = new ListItemView();
            listItemView.ivIcon = (ImageView) convertView
                    .findViewById(R.id.ivIcon);
            listItemView.tvTitle = (TextView) convertView
                    .findViewById(R.id.tvTitle);
            convertView.setTag(listItemView);
        } else {
            listItemView = (ListItemView) convertView.getTag();
        }

        Module module = listItems.get(position);
        listItemView.ivIcon.setImageResource(module.getIcon());
        listItemView.ivIcon.setTag("false");
        listItemView.tvTitle.setText(module.getName());

        return convertView;
    }

}