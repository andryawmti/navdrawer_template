package com.kudubisa.app.navdrawertemplate.remote;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Created by asus on 4/17/18.
 */

public class Common {
    private static final String HOST = "http://dev2.dadadasnoopdog.com";

    public String getRealPathFromURI(Uri contentURI, Context context) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public String getHost(){
        return HOST;
    }

    public String getFullUrl(String url){
        return HOST+url;
    }
}
