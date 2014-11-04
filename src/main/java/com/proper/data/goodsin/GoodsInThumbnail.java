package com.proper.data.goodsin;

import android.graphics.Bitmap;
import android.net.Uri;
import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created by Lebel on 31/10/2014.
 */
public class GoodsInThumbnail implements Serializable {
    private static final long serialVersionUID = 1L;
    private Uri URI;
    private Bitmap ThumbNail;
    private String fileName;

    public GoodsInThumbnail() {
    }

    public GoodsInThumbnail(Uri URI, Bitmap thumbNail, String fileName) {
        this.URI = URI;
        ThumbNail = thumbNail;
        this.fileName = fileName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @JsonProperty("URI")
    public Uri getURI() {
        return URI;
    }

    public void setURI(Uri URI) {
        this.URI = URI;
    }

    @JsonProperty("ThumbNail")
    public Bitmap getThumbNail() {
        return ThumbNail;
    }

    public void setThumbNail(Bitmap thumbNail) {
        ThumbNail = thumbNail;
    }

    @JsonProperty("FileName")
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
