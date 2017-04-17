package com.ccjy.wechat.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by dell on 2017/4/11.
 * 图片路径集合
 */

public class FileUtils {

    /**
     * MediaStore.Images.Media.EXTERNAL_CONTENT_URI　手机内存所有图片的ＵＲＩ
     * @param context
     * @return
     */

    public static ArrayList<String> getAllImage(Context context){
        ArrayList<String>list=new ArrayList<>();
        Cursor cursor=context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,null,null,null);
        while (cursor.moveToNext()){
            //获取路径
            String data=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            String width = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
            String height = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
            list.add(data);
        }
        cursor.close();
        return list;
    }

}
