package com.proper.data.goodsin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.proper.data.goodsin.GoodsInThumbnail;
import com.proper.warehousetools.R;

import java.util.List;

/**
 * Created by Lebel on 31/10/2014.
 */
public class GalleryGridAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsInThumbnail> thumbs;

    public GalleryGridAdapter(Context context, List<GoodsInThumbnail> thumbNails) {
        this.context = context;
        this.thumbs = thumbNails;
    }

    @Override
    public int getCount() {
        return thumbs.size();
    }

    @Override
    public GoodsInThumbnail getItem(int position) {
        return thumbs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder = new ViewHolder();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.gallery_gridrow, parent, false);

            holder.imageTitle = (TextView) view.findViewById(R.id.galleryRowText);
            holder.image = (ImageView) view.findViewById(R.id.galleryRowImage);
            holder.fileName = "";
            holder.position = position;
            view.setTag(holder);
        } else  {
            holder = (ViewHolder) view.getTag();
        }

        holder.fileName = this.thumbs.get(position).getFileName();
        holder.imageTitle.setText(String.format("%s", this.thumbs.get(position).getURI()));
        holder.image.setImageBitmap(this.thumbs.get(position).getThumbNail());
        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);

        return view;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
        String fileName;
        int position;
    }
}
