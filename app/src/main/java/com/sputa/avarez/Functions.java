package com.sputa.avarez;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by mma on 5/5/2018.
 */

public class Functions {


    public static String[] arr_item_status = new String[] { "همه",
            "در انتظار تائید", "تائید شده", "ویرایش شده", "حذف شده"
    };
    public String
            font_name_pens = "fonts/pens.ttf";
    public String
            font_name_Byekan = "fonts/BYekan.ttf";
    public String
            font_name_yekan = "fonts/Yekan.ttf";
    public static String
            font_name_iiranSens = "fonts/iransans.ttf";
    public static String
            font_name_vazir = "fonts/Vazir.ttf";
    public static String
                TAG = "tg1";
    public static boolean
                TAGisEnabled = true;

    public static int
            Time_out_limit = 10;

    public static String u_id = "0";
    public static String u_mobile = "";
    public static String key = "46794E7F-1612-46A7-B16B-7414BCC25FDE";
    public static int    user_normal =1;
    public Functions() {

    }

    public  void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if ( view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }
    public static void  Lag(String val)
    {
        if(TAGisEnabled)
            Log.d(TAG,val);
    }
    public Typeface get_font_pens(AssetManager context)
    {
        Typeface tf_items =  Typeface.createFromAsset(context,font_name_pens );
        return tf_items;
    }
    public  Typeface get_font_byekan(AssetManager context)
    {
        Typeface tf_items =  Typeface.createFromAsset(context,font_name_Byekan );
        return tf_items;
    }
    public  Typeface get_font_yekan(AssetManager context)
    {
        Typeface tf_items =  Typeface.createFromAsset(context,font_name_yekan );
        return tf_items;
    }
    public static   Typeface get_font_iransens(AssetManager context)
    {
        Typeface tf_items =  Typeface.createFromAsset(context,font_name_iiranSens );
        return tf_items;
    }
    public static   Typeface get_font_vazir(AssetManager context)
    {
        Typeface tf_items =  Typeface.createFromAsset(context,font_name_vazir );
        return tf_items;
    }
}



