package com.sputa.avarez;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sputa.avarez.adapters.item_adapter;
import com.sputa.avarez.adapters.item_eshterak_adapter;
import com.sputa.avarez.classes.StaticGasGhabz;
import com.sputa.avarez.classes.StaticWaterGhabz;
import com.sputa.avarez.model.items;
import com.sputa.avarez.model.items_cars;
import com.sputa.avarez.model.items_eshterak;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyEshterakList extends AppCompatActivity  implements RecyclerViewClickListener{
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
    private int pos;
    private boolean allowBack=true;

    @Override
    public void recyclerViewListClicked(View v, final int position) {
        SharedPreferences prefs = this.getSharedPreferences("ghabz", Context.MODE_PRIVATE);
        final String ghabz1 = prefs.getString("ghabz_id1", null);
        final String ghabz2 = prefs.getString("ghabz_id2", null);
        final String ghabz3 = prefs.getString("ghabz_id3", null);
        final String ghabz4 = prefs.getString("ghabz_id4", null);
        if(v.getId()==R.id.img_delete_eshterak)
        {
            pos=position;
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("حذف؟")
                    .setMessage("آیا مطمئن هستید؟")
                    .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete

                            if(item.get(position).getRadif().equals(ghabz1))
                            {
                                SharedPreferences.Editor editor = getSharedPreferences("ghabz", MODE_PRIVATE).edit();
                                editor.putString("ghabz_id1", null);
                                editor.apply();
                              //  Toast.makeText(MyEshterakList.this, String.valueOf("1"), Toast.LENGTH_SHORT).show();
                            }
                            if(item.get(position).getRadif().equals(ghabz2))
                            {
                                SharedPreferences.Editor editor = getSharedPreferences("ghabz", MODE_PRIVATE).edit();
                                editor.putString("ghabz_id2", null);
                                editor.apply();
                               // Toast.makeText(MyEshterakList.this, String.valueOf("2"), Toast.LENGTH_SHORT).show();
                            }
                            if(item.get(position).getRadif().equals(ghabz3))
                            {
                                SharedPreferences.Editor editor = getSharedPreferences("ghabz", MODE_PRIVATE).edit();
                                editor.putString("ghabz_id3", null);
                                editor.apply();
                                //Toast.makeText(MyEshterakList.this, String.valueOf("3"), Toast.LENGTH_SHORT).show();
                            }
                            if(item.get(position).getRadif().equals(ghabz4))
                            {
                                SharedPreferences.Editor editor = getSharedPreferences("ghabz", MODE_PRIVATE).edit();
                                editor.putString("ghabz_id4", null);
                                editor.apply();
                                //Toast.makeText(MyEshterakList.this, String.valueOf("4"), Toast.LENGTH_SHORT).show();
                            }


                            load_my_eshterak();

                        }
                    }).setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete

                }
            })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();



        }
        if(v.getId()==R.id.img_detail)
        {
            pos=position;
            //Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
            show_detail(item.get(position));


        }
    }
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


//        SharedPreferences.Editor editor = getSharedPreferences("ghabz", MODE_PRIVATE).edit();
//        editor.putString("ghabz_id1", null);
//
//        editor.putString("ghabz_id2", null);
//
//        editor.putString("ghabz_id3", null);
//
//
//        editor.putString("ghabz_id4", null);
//        editor.apply();

        load_my_eshterak();
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





    }

    private void load_my_eshterak() {
        SharedPreferences prefs = this.getSharedPreferences("ghabz", Context.MODE_PRIVATE);
        String ghabz1 = prefs.getString("ghabz_id1", null);
        String ghabz2 = prefs.getString("ghabz_id2", null);
        String ghabz3 = prefs.getString("ghabz_id3", null);
        String ghabz4 = prefs.getString("ghabz_id4", null);
        item.clear();
        //Toast.makeText(this, "-"+ghabz1+"-", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "-"+ghabz2+"-", Toast.LENGTH_SHORT).show();
        if(ghabz1!=null)
            if(!ghabz1.equals("")) {
                item.add(new items_eshterak(StaticGasGhabz.ghabz1_eshteraak, StaticGasGhabz.ghabz1_name, StaticGasGhabz.ghabz1_price_number, StaticGasGhabz.ghabz1_ID,"gas"));
        //        Toast.makeText(this, "1212312", Toast.LENGTH_SHORT).show();
            }
        if(ghabz2!=null)
            if(!ghabz2.equals(""))
                 item.add(new items_eshterak(StaticGasGhabz.ghabz2_eshteraak,StaticGasGhabz.ghabz2_name,StaticGasGhabz.ghabz2_price_number,StaticGasGhabz.ghabz2_ID,"gas"));
        if(ghabz3!=null)
            if(!ghabz3.equals(""))
                 item.add(new items_eshterak(StaticWaterGhabz.ghabz1_eshteraak,StaticWaterGhabz.ghabz1_name,StaticWaterGhabz.ghabz1_price_number,StaticWaterGhabz.ghabz1_ID,"water"));
        if(ghabz4!=null)
            if(!ghabz4.equals(""))
                 item.add(new items_eshterak(StaticWaterGhabz.ghabz2_eshteraak,StaticWaterGhabz.ghabz2_name,StaticWaterGhabz.ghabz2_price_number,StaticWaterGhabz.ghabz2_ID,"water"));

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new item_eshterak_adapter(this,item,MyEshterakList.this);
        mRecyclerView.setAdapter(mAdapter);

    }


    private void show_detail(items_eshterak s) {
        allowBack=false;
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
        lay_message.setVisibility(View.VISIBLE);
        lay_more_detail.setVisibility(View.VISIBLE);

        TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
        TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);
        String msg="";



        if((s.getRadif().equals(StaticGasGhabz.ghabz1_ID))) {
            msg="";
            msg+="تاریخ قرائت پیشین"+"\n";
            msg+="تاریخ قرائت فعلی"+"\n";
            msg+="رقم پیشین شمارشگر"+"\n";
            msg+="رقم فعلی شمارشگر"+"\n";
            msg+="مصرف به متر مکعب"+"\n";
            msg+="بهای گاز مصرفی"+"\n";
            msg+="تعداد واحد"+"\n";
            lbl_msg_right_detail.setText(msg);
            msg = StaticGasGhabz.get_ghabz_detail_shenase(StaticGasGhabz.ghabz1_shenase);
            lbl_msg_left_detail.setText(msg);
        }
        if((s.getRadif().equals(StaticGasGhabz.ghabz2_ID))) {
            msg="";
            msg+="تاریخ قرائت پیشین"+"\n";
            msg+="تاریخ قرائت فعلی"+"\n";
            msg+="رقم پیشین شمارشگر"+"\n";
            msg+="رقم فعلی شمارشگر"+"\n";
            msg+="مصرف به متر مکعب"+"\n";
            msg+="بهای گاز مصرفی"+"\n";
            msg+="تعداد واحد"+"\n";
            lbl_msg_right_detail.setText(msg);
            msg = StaticGasGhabz.get_ghabz_detail_shenase(StaticGasGhabz.ghabz2_shenase);
            lbl_msg_left_detail.setText(msg);
        }
        if((s.getRadif().equals(StaticWaterGhabz.ghabz1_ID))) {
            msg="";
            msg+="تاریخ قرائت پیشین"+"\n";
            msg+="تاریخ قرائت فعلی"+"\n";
            msg+="رقم پیشین کنتور"+"\n";
            msg+="رقم فعلی کنتور"+"\n";
            msg+="بهای آب مصرفی"+"\n";
            msg+="بهای خدمات فاضلاب"+"\n";
            msg+="مالیات بر ارزش افزوده"+"\n";
            msg+="تکالیف قانون بودجه"+"\n";
            msg+="قابل پرداخت"+"\n";

            lbl_msg_right_detail.setText(msg);
            msg = StaticWaterGhabz.get_ghabz_detail_shenase(StaticWaterGhabz.ghabz1_shenase);
        //    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            lbl_msg_left_detail.setText(msg);
        }
        if((s.getRadif().equals(StaticWaterGhabz.ghabz2_ID))) {
            msg="";
            msg+="تاریخ قرائت پیشین"+"\n";
            msg+="تاریخ قرائت فعلی"+"\n";
            msg+="رقم پیشین کنتور"+"\n";
            msg+="رقم فعلی کنتور"+"\n";
            msg+="بهای آب مصرفی"+"\n";
            msg+="بهای خدمات فاضلاب"+"\n";
            msg+="مالیات بر ارزش افزوده"+"\n";
            msg+="تکالیف قانون بودجه"+"\n";
            msg+="قابل پرداخت"+"\n";
            lbl_msg_right_detail.setText(msg);
            msg = StaticWaterGhabz.get_ghabz_detail_shenase(StaticWaterGhabz.ghabz2_shenase);
            lbl_msg_left_detail.setText(msg);
        }



    }
    public void clk_pay(View view) {


        show_pay();

    }
    public void clk_msg(View view) {
//        fun.enableDisableView(lay_main, true);
//        RelativeLayout lay_message = findViewById(R.id.lay_message);
//        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
//        lay_message.setVisibility(View.GONE);
//        lay_more_detail.setVisibility(View.GONE);
    }
    private void show_pay() {
        allowBack =false;
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.VISIBLE);
        ConstraintLayout lay_detail = findViewById(R.id.lay_more_detail);
        lay_detail.setVisibility(View.GONE);
        WebView webview = (WebView) findViewById(R.id.web_view);
        webview.setWebViewClient(new myWebClient());
        webview.getSettings().setJavaScriptEnabled(true);
        //rslt_price="1000";
        String tmp_price =item.get(pos).getTxt_price(),price="" ;
        Toast.makeText(this, String.valueOf(pos), Toast.LENGTH_SHORT).show();
        for(int i=0;i<tmp_price.length();i++)
        {
            if(tmp_price.charAt(i)!=',')
            {
                price+=tmp_price.charAt(i);
            }
        }

        String url ="http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount="+price+"&AdditionalInfo=10000089-CTSCar&MerchantID=118088384&TerminalId=17995091&TransactionKey=AZ24JJ95SS&OrderId=10000089235123552";
    //Toast.makeText(this, url    , Toast.LENGTH_LONG).show();
        webview.loadUrl(url);



    }
    @Override
    public void onBackPressed() {
        if (!allowBack) {


        } else {
            super.onBackPressed();
        }
    }
    public void clk_back_gate(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.GONE);
        ConstraintLayout lay_detail = findViewById(R.id.lay_more_detail);
        lay_detail.setVisibility(View.GONE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.GONE);
        allowBack =true;
    }

    public void clk_back(View view) {
        allowBack = true;
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
        lay_message.setVisibility(View.GONE);
        lay_more_detail.setVisibility(View.GONE);
    }
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onLoadResource(WebView  view, String  url){


            TextView txt=findViewById(R.id.txt_url);
            txt.setText(view.getUrl());
        }
    }

}
