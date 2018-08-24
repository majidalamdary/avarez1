package com.sputa.avarez;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.sputa.avarez.adapters.item_adapter;
import com.sputa.avarez.adapters.item_eshterak_adapter;
import com.sputa.avarez.classes.StaticGasGhabz;
import com.sputa.avarez.model.items;
import com.sputa.avarez.model.items_cars;
import com.sputa.avarez.model.items_eshterak;

import java.util.ArrayList;
import java.util.List;

public class MyEshterakList extends AppCompatActivity {
    List<items_eshterak> item =     new ArrayList<>();
    List<items> item1 =     new ArrayList<>();
    LinearLayout lay_main;
    private Functions fun;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView_cars;
    private RecyclerView mRecyclerView;
    private int screenWidth;
    private int screenHeight;


    private void set_size(int vid,Double width,Double height,String typ)
    {
        View v =findViewById(vid);
        if(typ.equals("cons")) {
            ConstraintLayout.LayoutParams lp= (ConstraintLayout.LayoutParams) v.getLayoutParams();
            lp.width=(int)(screenWidth* width);
            lp.height=(int)(screenHeight* height);;
            v.setLayoutParams(lp);
        }
        if(typ.equals("line")) {
            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) v.getLayoutParams();
            lp.width=(int)(screenWidth* width);
            lp.height=(int)(screenHeight* height);;
            v.setLayoutParams(lp);
        }
        if(typ.equals("rel")) {
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) v.getLayoutParams();
            lp.width=(int)(screenWidth* width);
            lp.height=(int)(screenHeight* height);;
            v.setLayoutParams(lp);
        }
    }

    private void set_size_txt(int vid,Double size,String typ)
    {
        TextView v =(TextView) findViewById(vid);
        if(typ.equals("cons")) {
            ConstraintLayout.LayoutParams lp= (ConstraintLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
        if(typ.equals("line")) {
            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
        if(typ.equals("rel")) {
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
    }
    private void set_size_edit(int vid,Double size,String typ)
    {
        EditText v = findViewById(vid);
        if(typ.equals("cons")) {
            ConstraintLayout.LayoutParams lp= (ConstraintLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
        if(typ.equals("line")) {
            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
        if(typ.equals("rel")) {
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_eshterak_list);
        SharedPreferences prefs = this.getSharedPreferences("ghabz", Context.MODE_PRIVATE);
        String ghabz1 = prefs.getString("ghabz_id1", null);
        String ghabz2 = prefs.getString("ghabz_id2", null);
        lay_main= findViewById(R.id.lay_main);
        fun=new Functions();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        set_size(R.id.lay_more_detail,.85,.8,"rel");
        set_size(R.id.lay_msg_more_detail,.7,.6,"cons");
        set_size_txt(R.id.lbl_more_detail_title,.045,"cons");
        set_size_txt(R.id.lbl_msg_right_detail,.04,"line");
        set_size_txt(R.id.lbl_msg_left_detail,.04,"line");
        item.clear();

        if(ghabz1!=null)
            item.add(new items_eshterak(StaticGasGhabz.ghabz1_eshteraak,StaticGasGhabz.ghabz1_name,StaticGasGhabz.ghabz1_price_number,StaticGasGhabz.ghabz1_ID));
        if(ghabz2!=null)
            item.add(new items_eshterak(StaticGasGhabz.ghabz2_eshteraak,StaticGasGhabz.ghabz2_name,StaticGasGhabz.ghabz2_price_number,StaticGasGhabz.ghabz2_ID));


            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);


            mAdapter = new item_eshterak_adapter(this,item);
            mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, mRecyclerView_cars ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        //    Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
                        // do whatever

                        show_detail(item.get(position));

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // Toast.makeText(NewItem.this, "long Click", Toast.LENGTH_SHORT).show();
                        // do whatever
                    }
                })
        );

    }

    private void show_detail(items_eshterak s) {

        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
        lay_message.setVisibility(View.VISIBLE);
        lay_more_detail.setVisibility(View.VISIBLE);

        TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
        TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);
        String msg="";
        msg+="تاریخ قرائت پیشین"+"\n"+"\n";
        msg+="تاریخ قرائت فعلی"+"\n"+"\n";
        msg+="رقم پیشین شمارشگر"+"\n"+"\n";
        msg+="رقم فعلی شمارشگر"+"\n"+"\n";
        msg+="مصرف به متر مکعب"+"\n"+"\n";
        msg+="بهای گاز مصرفی"+"\n"+"\n";
        msg+="تعداد واحد"+"\n"+"\n";

        lbl_msg_right_detail.setText(msg);
        if((s.getRadif().equals(StaticGasGhabz.ghabz1_ID))) {
            msg = StaticGasGhabz.get_ghabz_detail_shenase(StaticGasGhabz.ghabz1_shenase);
            lbl_msg_left_detail.setText(msg);
        }
        if((s.getRadif().equals(StaticGasGhabz.ghabz2_ID))) {
            msg = StaticGasGhabz.get_ghabz_detail_shenase(StaticGasGhabz.ghabz2_shenase);
            lbl_msg_left_detail.setText(msg);
        }



    }

    public void clk_msg(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
        lay_message.setVisibility(View.GONE);
        lay_more_detail.setVisibility(View.GONE);
    }

}
