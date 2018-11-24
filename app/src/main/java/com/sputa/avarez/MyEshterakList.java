package com.sputa.avarez;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
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
    private SQLiteDatabase myDB;

    @Override
    public void recyclerViewListClicked(View v, final int position) {

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
                            items_eshterak itm = (item.get(position));

                            myDB.execSQL("delete from MyGhabz where AboneID='"+itm.getTxt_eshterak()+"'");



                            item.clear();

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
        myDB = openOrCreateDatabase(getString(R.string.DB_name), MODE_PRIVATE, null);

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
    private String digiting(String string) {
        String new_str = "";
        int j = 0;
        for (int ii = string.length() - 1; ii >= 0; ii--) {
            j++;
            if (j != string.length() && j % 3 == 0)
                new_str = "," + string.charAt(ii) + new_str;
            else
                new_str = string.charAt(ii) + new_str;
        }
        return new_str;
    }
    private void load_my_eshterak() {



        Cursor cr = myDB.rawQuery("select * from MyGhabz ",null);
        if(cr.getCount()>0) cr.moveToFirst();
        while(!cr.isAfterLast()) {
//            Toast.makeText(this, String.valueOf(cr.getCount()), Toast.LENGTH_SHORT).show();
            if(cr.getString(1).equals("gas")) {
                Cursor cr1 = myDB.rawQuery("select * from Gas where AboneID='"+cr.getString(0)+"'", null);
                if(cr1.getCount()>0) {
                    cr1.moveToFirst();
                    item.add(new items_eshterak(cr.getString(0), cr1.getString(2) + " " + cr1.getString(3), digiting(cr1.getString(12)), cr1.getString(0), "gas"));
                }
            }
            if(cr.getString(1).equals("water")) {
                Cursor cr1 = myDB.rawQuery("select * from water where AboneID='"+cr.getString(0)+"'", null);
                if(cr1.getCount()>0) {
                    cr1.moveToFirst();
                    item.add(new items_eshterak(cr.getString(0), cr1.getString(2) + " " + cr1.getString(3), digiting(String.valueOf((int)(cr1.getFloat(14)))), cr1.getString(0), "water"));
                }
            }
            if(cr.getString(1).equals("electric")) {
                    Cursor cr1 = myDB.rawQuery("select * from power where AboneID='"+cr.getString(0)+"'", null);
                if(cr1.getCount()>0) {
                    cr1.moveToFirst();
                    item.add(new items_eshterak(cr.getString(0), cr1.getString(2) + " " + cr1.getString(3), digiting(cr1.getString(11)), cr1.getString(0), "electric"));
                }
            }
            if(cr.getString(1).equals("telphone")) {
                Cursor cr1 = myDB.rawQuery("select * from CellPhone where AboneID='"+cr.getString(0)+"'", null);
                if(cr1.getCount()>0) {
                    cr1.moveToFirst();
                    item.add(new items_eshterak(cr.getString(0), cr1.getString(2) + " " + cr1.getString(3), digiting(cr1.getString(9)), cr1.getString(0), "telphone"));
                }
            }
            cr.moveToNext();
        }
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

        if(s.getType().equals("gas"))
        {
            search_gas_ghabz(s.getTxt_eshterak(),"111");
        }
        if(s.getType().equals("water"))
        {
            search_water_ghabz(s.getTxt_eshterak(),"111");
        }
        if(s.getType().equals("electric"))
        {
            search_electric_ghabz(s.getTxt_eshterak(),"111");
        }
        if(s.getType().equals("telphone"))
        {
            search_telphone_ghabz(s.getTxt_eshterak(),"111");
        }



    }


    private void search_gas_ghabz(String eshterak, String ghabz) {
        //Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        Cursor cr= myDB.rawQuery("select * from Gas where AboneID='"+eshterak+"'", null);
        if(eshterak.length()>0) {
            cr = myDB.rawQuery("select * from Gas where AboneID='"+eshterak+"'", null);
            Log.d("majid","esht=");
        }
        else if(ghabz.length()>0) {
            cr = myDB.rawQuery("select * from Gas where ID='"+ghabz+"'", null);
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                Log.d("majid","ghabz=");
            }
        }
        if(cr!=null) {
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                String ID= cr.getString(0);
                Log.d("majid","ok");
                cr.moveToFirst();
                String
                        msg = "";

                TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
                TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);

                msg = "";
                msg += "تاریخ قرائت پیشین" + "\n";
                msg += "تاریخ قرائت فعلی" + "\n";
                msg += "رقم پیشین شمارشگر" + "\n";
                msg += "رقم فعلی شمارشگر" + "\n";
                msg += "مصرف به متر مکعب" + "\n";
                msg += "تعداد واحد" + "\n";
                msg += "بهای گاز مصرفی" + "\n";

                lbl_msg_right_detail.setText(msg);
                msg = "";
                msg =" :   "+ cr.getString(5) + "\n" ;
                msg +=" :   "+ cr.getString(6)+ "\n" ;
                msg +=" :   "+ cr.getString(7)+ "\n" ;
                msg +=" :   "+ cr.getString(8)+ "\n" ;
                msg +=" :   "+ cr.getString(9)+ "\n" ;
                msg +=" :   "+ cr.getString(10)+ "  \n" ;

                msg +=" :   "+ digiting(cr.getString(12))+ " ریال \n" ;

                lbl_msg_left_detail.setText(msg);

                LinearLayout btn_pay = findViewById(R.id.btn_pay);
                btn_pay.setVisibility(View.VISIBLE);
//                LinearLayout btn_detail = findViewById(R.id.btn_detail);
//                btn_detail.setVisibility(View.VISIBLE);



            }
        }
    }
    private void search_water_ghabz(String eshterak, String ghabz) {
        //Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        Cursor cr= myDB.rawQuery("select * from water where AboneID='"+eshterak+"'", null);
        if(eshterak.length()>0) {
            cr = myDB.rawQuery("select * from water where AboneID='"+eshterak+"'", null);
            Log.d("majid","esht=");
        }
        else if(ghabz.length()>0) {
            cr = myDB.rawQuery("select * from water where ID='"+ghabz+"'", null);
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                Log.d("majid","ghabz=");
            }
        }
        if(cr!=null) {
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                String ID= cr.getString(0);
                Log.d("majid","ok");
                cr.moveToFirst();
                String
                        msg = "";


                TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
                TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);

                msg = "";
                msg += "تاریخ قرائت پیشین" + "\n";
                msg += "تاریخ قرائت فعلی" + "\n";
                msg += "رقم پیشین شمارشگر" + "\n";
                msg += "رقم فعلی شمارشگر" + "\n";
                msg += "مصرف به متر مکعب" + "\n";
                msg += "عوارض فاضلاب" + "\n";
                msg += "مالیات" + "\n";
                msg += "تبصره های قانونی" + "\n";
                msg += "بهای آب مصرفی" + "\n";

                lbl_msg_right_detail.setText(msg);
                msg = "";
                msg =" :   "+ cr.getString(5) + "\n" ;
                msg +=" :   "+ cr.getString(6)+ "\n" ;
                msg +=" :   "+ cr.getString(7)+ "\n" ;
                msg +=" :   "+ cr.getString(8)+ "\n" ;
                msg +=" :   "+ cr.getString(9)+ "\n" ;
                msg +=" :   "+ digiting(cr.getString(10))+ "\n" ;
                msg +=" :   "+ cr.getString(11)+ "\n" ;
                msg +=" :   "+ cr.getString(12)+ "\n" ;
                msg +=" :   "+ digiting(String.valueOf((int)(cr.getFloat(14))))+ " ریال \n" ;

                lbl_msg_left_detail.setText(msg);

                LinearLayout btn_pay = findViewById(R.id.btn_pay);
                btn_pay.setVisibility(View.VISIBLE);




            }
        }
    }
    private void search_electric_ghabz(String eshterak, String ghabz) {
        //Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        Cursor cr= myDB.rawQuery("select * from power where AboneID='"+eshterak+"'", null);
        if(eshterak.length()>0) {
            cr = myDB.rawQuery("select * from power where AboneID='"+eshterak+"'", null);
            Log.d("majid","esht=");
        }
        else if(ghabz.length()>0) {
            cr = myDB.rawQuery("select * from power where ID='"+ghabz+"'", null);
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                Log.d("majid","ghabz=");
            }
        }
        if(cr!=null) {
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                String ID= cr.getString(0);
                Log.d("majid","ok");
                cr.moveToFirst();
                String
                        msg = "";

                TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
                TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);

                msg = "";
                msg += "تاریخ قرائت پیشین" + "\n";
                msg += "تاریخ قرائت فعلی" + "\n";
                msg += "رقم پیشین شمارشگر" + "\n";
                msg += "رقم فعلی شمارشگر" + "\n";
                msg += "مصرف به کیلو وات" + "\n";
                msg += "بهای برق مصرفی" + "\n";

                lbl_msg_right_detail.setText(msg);
                msg = "";
                msg =" :   "+ cr.getString(5) + "\n" ;
                msg +=" :   "+ cr.getString(6)+ "\n" ;
                msg +=" :   "+ cr.getString(7)+ "\n" ;
                msg +=" :   "+ cr.getString(8)+ "\n" ;
                msg +=" :   "+ cr.getString(9)+ "\n" ;
                msg +=" :   "+ digiting(cr.getString(11))+ " ریال \n" ;

                lbl_msg_left_detail.setText(msg);

                LinearLayout btn_pay = findViewById(R.id.btn_pay);
                btn_pay.setVisibility(View.VISIBLE);




            }
        }
    }
    private void search_telphone_ghabz(String eshterak, String ghabz) {
        //Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        Cursor cr= myDB.rawQuery("select * from CellPhone where AboneID='"+eshterak+"'", null);
        if(eshterak.length()>0) {
            cr = myDB.rawQuery("select * from CellPhone where AboneID='"+eshterak+"'", null);
            Log.d("majid","esht=");
        }
        else if(ghabz.length()>0) {
            cr = myDB.rawQuery("select * from CellPhone where ID='"+ghabz+"'", null);
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                Log.d("majid","ghabz=");
            }
        }
        if(cr!=null) {
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                String ID= cr.getString(0);
                Log.d("majid","ok");
                cr.moveToFirst();
                String
                        msg = "";

                TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
                TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);

                msg = "";
                msg += "تاریخ پیشین" + "\n";
                msg += "تاریخ فعلی" + "\n";

                msg += "مصرف به دقیقه" + "\n";
                msg += "بهای مکالمه با تلفن" + "\n";

                lbl_msg_right_detail.setText(msg);
                msg = "";
                msg =" :   "+ cr.getString(5) + "\n" ;
                msg +=" :   "+ cr.getString(6)+ "\n" ;
                msg +=" :   "+ cr.getString(7)+ "\n" ;
                msg +=" :   "+ digiting(cr.getString(9))+ " ریال \n" ;

                lbl_msg_left_detail.setText(msg);

                LinearLayout btn_pay = findViewById(R.id.btn_pay);
                btn_pay.setVisibility(View.VISIBLE);


            }
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
