package com.sputa.avarez.my_views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.sputa.avarez.Functions;


/**
 * Created by diego on 13/05/2018.
 */

public class MyTextView extends TextView {

    Context context;
    public MyTextView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        this.context=context;
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Functions.get_font_iransens(getContext().getAssets());
            setTypeface(tf);
        }
    }

}