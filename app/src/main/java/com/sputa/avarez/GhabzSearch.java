package com.sputa.avarez;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sputa.avarez.classes.StaticGasGhabz;
import com.sputa.avarez.classes.StaticWaterGhabz;
import com.sputa.avarez.model.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GhabzSearch extends AppCompatActivity {



    //
    private int screenWidth;
    private int screenHeight;
    private boolean is_requested;
    //MyAsyncTask mm;
    private String last_query;
    RelativeLayout lay_main;
    private Functions fun;
    private int tim=1;
    private Timer timer;
    private String motorSerial;
    private String rslt_MerchantId="",rslt_TerMinalId="";
    private String rslt_TransactionKey="",rslt_OrderId="";
    private String rslt_MainProfile="";
    private String rslt_price="0";
    private String rslt_CanEPay="";
    private int avarez_price;
    private String rslt_name;
    private String rslt_family;
    List<items> item =     new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private String rslt_NationalNumber;
    private String rslt_ChassiSerial;
    private String rslt_VinNumber;
    private String public_eshterak;
    private String public_shenase;
    private String ghabz_id;
    private boolean allowBack=true;
    private String ghabz_type="electric";


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
        setContentView(R.layout.activity_ghabz_search);
        lay_main= findViewById(R.id.lay_main);

        Intent I = getIntent();
        ghabz_type = I.getStringExtra("type");


        String[] arraySpinner = new String[] {
                "شماره اشتراک", "شناسه قبض"
        };
        Spinner s = (Spinner) findViewById(R.id.spn_search_type);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);
        fun=new Functions();

        timer = new Timer("timeout");
        timer.start();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        set_size(R.id.txt_eshterak,.49,.07,"cons");
        set_size_txt(R.id.lbl_title,.065,"cons");
        set_size_txt(R.id.lbl_eshterak,.05,"cons");
        set_size_edit(R.id.txt_eshterak,.06,"cons");
        set_size_txt(R.id.lbl_search_type,.05,"cons");
        set_size_txt(R.id.lbl_msg_right,.039,"line");
        set_size_txt(R.id.lbl_msg_left,.039,"line");
        set_size(R.id.lay_message_main,.9,.18,"cons");
        Spinner spn= findViewById(R.id.spn_search_type);
        set_size(R.id.spn_search_type,.52,.07,"cons");
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                EditText txt_eshterak = findViewById(R.id.txt_eshterak);
                EditText txt_parvande = findViewById(R.id.txt_ghabz);

                txt_eshterak.setText("");
                txt_parvande.setText("");
                if (position == 0) {
                    ConstraintLayout lay_motor_search = findViewById(R.id.lay_eshterak_search);
                    lay_motor_search.setVisibility(View.VISIBLE);
                    ConstraintLayout lay_ghabz_search = findViewById(R.id.lay_ghabz_search);
                    lay_ghabz_search.setVisibility(View.GONE);
                }
                if (position == 1) {
                    ConstraintLayout lay_motor_search = findViewById(R.id.lay_eshterak_search);
                    lay_motor_search.setVisibility(View.GONE);
                    ConstraintLayout lay_ghabz_search = findViewById(R.id.lay_ghabz_search);
                    lay_ghabz_search.setVisibility(View.VISIBLE);
                 }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
//        ConstraintLayout.LayoutParams lp= (ConstraintLayout.LayoutParams) spn.getLayoutParams();
//        spn.sette setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
//        spn.setLayoutParams(lp);

        set_size(R.id.txt_ghabz,.54,.07,"cons");
        set_size_txt(R.id.lbl_ghabz,.04,"cons");
        set_size_edit(R.id.txt_ghabz,.04,"cons");
        set_size_edit(R.id.txt_eshterak,.04,"cons");


        set_size(R.id.btn_search,.3,.065,"cons");
        set_size(R.id.btn_back,.3,.065,"cons");
        set_size_txt(R.id.lbl_search,.054,"line");
        set_size_txt(R.id.lbl_back,.054,"line");
        set_size(R.id.btn_pay,.3,.065,"cons");
        set_size(R.id.btn_detail,.3,.065,"cons");

        set_size_txt(R.id.lbl_help,.033,"cons");
        set_size(R.id.img_help_motor,.08,.06,"cons");
        set_size(R.id.img_help_ghabz,.08,.06,"cons");



        set_size(R.id.lay_confirm,0.95,.14,"cons");
        set_size(R.id.btn_yes_correct,.12,.052,"cons");
        set_size(R.id.btn_no_will_correct,.3,.052,"cons");
        set_size_txt(R.id.lbl_confirm_msg,.044,"cons");
        set_size_txt(R.id.lbl_yes_correct,.04,"line");
        set_size_txt(R.id.lbl_no_will_correct,.04,"line");

        set_size(R.id.lay_complete_info,0.83,.76,"rel");
        set_size(R.id.btn_save_complete,0.26,.06,"cons");
        set_size(R.id.btn_back_complete,0.26,.06,"cons");

        set_size(R.id.txt_name_complete,.44,.07,"cons");
        set_size(R.id.txt_family_complete,.44,.07,"cons");
        set_size_txt(R.id.lbl_name_complete,.04,"cons");
        set_size_edit(R.id.txt_name_complete,.04,"cons");

        set_size_txt(R.id.lbl_family_complete,.04,"cons");
        set_size_edit(R.id.txt_family_complete,.04,"cons");

        set_size_txt(R.id.lbl_melliId_complete,.04,"cons");
        set_size_edit(R.id.txt_melliID_complete,.044,"cons");
        set_size(R.id.txt_melliID_complete,.44,.07,"cons");

        set_size_txt(R.id.lbl_shasi_complete,.04,"cons");
        set_size_edit(R.id.txt_shasi_complete,.044,"cons");
        set_size(R.id.txt_shasi_complete,.44,.07,"cons");

        set_size_txt(R.id.lbl_VIN_complete,.04,"cons");
        set_size_txt(R.id.lbl_VIN_complete,.04,"cons");
        set_size_edit(R.id.txt_VIN_complete,.044,"cons");
        set_size(R.id.txt_VIN_complete,.44,.07,"cons");

        set_size_txt(R.id.lbl_complete_title,.051,"cons");


        set_size(R.id.lay_more_detail,.85,.8,"rel");
        set_size(R.id.lay_msg_more_detail,.7,.6,"cons");
        set_size_txt(R.id.lbl_more_detail_title,.045,"cons");
        set_size_txt(R.id.lbl_msg_right_detail,.04,"line");
        set_size_txt(R.id.lbl_msg_left_detail,.04,"line");



    }
    public void clk_search(View view) {
        EditText txt_eshterak = findViewById(R.id.txt_eshterak);
        EditText txt_ghabz = findViewById(R.id.txt_ghabz);



        String
                search_type = "none";
        if (txt_eshterak.getText().toString().length() > 0) {
            search_type = "ok";
        } else if (txt_ghabz.getText().toString().length() > 0) {
            search_type = "ok";
        }

        if (search_type.equals("ok")) {


            if(txt_eshterak.length()>0) {
                search_ghabz_eshterak(txt_eshterak.getText().toString(), txt_ghabz.getText().toString());
            }else {
                search_ghabz_shenase(txt_ghabz.getText().toString(), txt_ghabz.getText().toString());
            }


        } else {
            Toast.makeText(this, "لطفا اطلاعات قبض خود را وارد کنید", Toast.LENGTH_SHORT).show();
        }
    }
    public void clk_detail(View view) {
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
        lay_message.setVisibility(View.VISIBLE);
        lay_more_detail.setVisibility(View.VISIBLE);

        allowBack=false;
    }

    private void search_ghabz_eshterak(String eshterak,String ghabz) {
        InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        public_eshterak = eshterak;
        String ghabz1_eshterak = "";
        String ghabz2_eshterak = "";
        if(ghabz_type.equals("gas")) {
            ghabz1_eshterak = StaticGasGhabz.ghabz1_eshteraak;
            ghabz2_eshterak = StaticGasGhabz.ghabz2_eshteraak;
        }
         if(ghabz_type.equals("water")) {
            ghabz1_eshterak = StaticWaterGhabz.ghabz1_eshteraak;
            ghabz2_eshterak = StaticWaterGhabz.ghabz2_eshteraak;
        }

        if(eshterak.equals(ghabz1_eshterak) || eshterak.equals(ghabz2_eshterak)) {
            if(ghabz_type.equals("gas")) {
                ghabz_id = StaticGasGhabz.get_ghabz_ID_eshterak(eshterak);
            }
            if(ghabz_type.equals("water")) {
                ghabz_id = StaticWaterGhabz.get_ghabz_ID_eshterak(eshterak);
            }
            String
                    msg = "";
            msg += " نام مالک"+ "\n" ;
            msg += " مبلغ قابل پرداخت قبض" + "\n" ;
            msg += " مبلغ بدهی قبض" + "\n" ;
            msg += " کارکرد دوره" + "\n" ;

            TextView lbl_msg = findViewById(R.id.lbl_msg_right);
            lbl_msg.setText(msg);

            if(ghabz_type.equals("gas")) {
                msg = StaticGasGhabz.get_ghabz_price_eshterak(eshterak);
            }
            if(ghabz_type.equals("water")) {
                msg = StaticWaterGhabz.get_ghabz_price_eshterak(eshterak);
            }
            TextView lbl_msg1 = findViewById(R.id.lbl_msg_left);
            lbl_msg1.setText(msg);

            TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
            TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);
            if(ghabz_type.equals("gas")) {
                msg = "";
                msg += "تاریخ قرائت پیشین" + "\n";
                msg += "تاریخ قرائت فعلی" + "\n";
                msg += "رقم پیشین شمارشگر" + "\n";
                msg += "رقم فعلی شمارشگر" + "\n";
                msg += "مصرف به متر مکعب" + "\n";
                msg += "بهای گاز مصرفی" + "\n";
                msg += "تعداد واحد" + "\n";

                lbl_msg_right_detail.setText(msg);
                msg = StaticGasGhabz.get_ghabz_detail_eshterak(eshterak);
                lbl_msg_left_detail.setText(msg);
            }
            if(ghabz_type.equals("water")) {
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
                msg = StaticWaterGhabz.get_ghabz_detail_eshterak(eshterak);
                lbl_msg_left_detail.setText(msg);
            }
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            btn_pay.setVisibility(View.VISIBLE);
            LinearLayout btn_detail = findViewById(R.id.btn_detail);
            btn_detail.setVisibility(View.VISIBLE);




            bookmark_ghabz();

        }
        else
        {
            Toast.makeText(this, "اشتراک موردنظر پیدا نشد", Toast.LENGTH_SHORT).show();
        }

    }
    private void search_ghabz_shenase(String shenase,String ghabz) {
        InputMethodManager imm = (InputMethodManager) getBaseContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        public_shenase = shenase;
        String ghabz1_shenase = "";
        String ghabz2_shenase = "";
        if(ghabz_type.equals("gas")) {
            ghabz1_shenase = StaticGasGhabz.ghabz1_shenase;
            ghabz2_shenase = StaticGasGhabz.ghabz2_shenase;
        }
        if(ghabz_type.equals("water")) {
            ghabz1_shenase = StaticWaterGhabz.ghabz1_shenase;
            ghabz2_shenase = StaticWaterGhabz.ghabz2_shenase;
        }

        if(shenase.equals(ghabz1_shenase) || shenase.equals(ghabz2_shenase)) {
            if(ghabz_type.equals("gas")) {
                ghabz_id = StaticGasGhabz.get_ghabz_ID_shenase(shenase);
            }
            if(ghabz_type.equals("water")) {
                ghabz_id = StaticWaterGhabz.get_ghabz_ID_shenase(shenase);
            }
            String
                    msg = "";
            msg += " نام مالک"+ "\n" ;
            msg += " مبلغ قابل پرداخت قبض" + "\n" ;
            msg += " مبلغ بدهی قبض" + "\n" ;
            msg += " کارکرد دوره" + "\n" ;

            TextView lbl_msg = findViewById(R.id.lbl_msg_right);
            lbl_msg.setText(msg);

            if(ghabz_type.equals("gas")) {
                msg = StaticGasGhabz.get_ghabz_price_shenase(shenase);
            }
            if(ghabz_type.equals("water")) {
                msg = StaticWaterGhabz.get_ghabz_price_shenase(shenase);
            }
            TextView lbl_msg1 = findViewById(R.id.lbl_msg_left);
            lbl_msg1.setText(msg);

            TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
            TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);
            if(ghabz_type.equals("gas")) {
                msg = "";
                msg += "تاریخ قرائت پیشین" + "\n";
                msg += "تاریخ قرائت فعلی" + "\n";
                msg += "رقم پیشین شمارشگر" + "\n";
                msg += "رقم فعلی شمارشگر" + "\n";
                msg += "مصرف به متر مکعب" + "\n";
                msg += "بهای گاز مصرفی" + "\n";
                msg += "تعداد واحد" + "\n";

                lbl_msg_right_detail.setText(msg);
                msg = StaticGasGhabz.get_ghabz_detail_shenase(shenase);
                lbl_msg_left_detail.setText(msg);
            }
            if(ghabz_type.equals("water")) {
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
                msg = StaticGasGhabz.get_ghabz_detail_shenase(shenase);
                lbl_msg_left_detail.setText(msg);
            }
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            btn_pay.setVisibility(View.VISIBLE);
            LinearLayout btn_detail = findViewById(R.id.btn_detail);
            btn_detail.setVisibility(View.VISIBLE);




            bookmark_ghabz();

        }
        else
        {
            Toast.makeText(this, "اشتراک موردنظر پیدا نشد", Toast.LENGTH_SHORT).show();
        }

    }

    private void bookmark_ghabz() {
       // Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        SharedPreferences prefs = this.getSharedPreferences("ghabz", Context.MODE_PRIVATE);
        String ghabz1 = prefs.getString("ghabz_id1", null);
        String ghabz2 = prefs.getString("ghabz_id2", null);
        String ghabz3 = prefs.getString("ghabz_id3", null);
        String ghabz4 = prefs.getString("ghabz_id4", null);
        //Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        if(ghabz_id.equals(StaticGasGhabz.ghabz1_ID))
        {
            if(ghabz1 ==null )
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(GhabzSearch.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(GhabzSearch.this);
                }
                builder.setTitle("ذخیره اشتراک؟")
                        .setMessage("آیا می خواهید این اشتراک به لیست اشتراک های من اضافه شود؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = getSharedPreferences("ghabz", MODE_PRIVATE).edit();
                                editor.putString("ghabz_id1", ghabz_id);
                                editor.apply();

                            }
                        }).setNegativeButton("نه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
        if(ghabz_id.equals(StaticGasGhabz.ghabz2_ID))
        {
            if(ghabz2 == null)
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(GhabzSearch.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(GhabzSearch.this);
                }
                builder.setTitle("ذخیره اشتراک؟")
                        .setMessage("آیا می خواهید این اشتراک به لیست اشتراک های من اضافه شود؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = getSharedPreferences("ghabz", MODE_PRIVATE).edit();
                                editor.putString("ghabz_id2", ghabz_id);
                                editor.apply();

                            }
                        }).setNegativeButton("نه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
       // Toast.makeText(this, ghabz_id+"--"+StaticWaterGhabz.ghabz1_ID, Toast.LENGTH_SHORT).show();
        if(ghabz_id.equals(StaticWaterGhabz.ghabz1_ID))
        {

            if(ghabz3 == null)
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(GhabzSearch.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(GhabzSearch.this);
                }
                builder.setTitle("ذخیره اشتراک؟")
                        .setMessage("آیا می خواهید این اشتراک به لیست اشتراک های من اضافه شود؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = getSharedPreferences("ghabz", MODE_PRIVATE).edit();
                                editor.putString("ghabz_id3", ghabz_id);
                                editor.apply();

                            }
                        }).setNegativeButton("نه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }

        if(ghabz_id.equals(StaticWaterGhabz.ghabz2_ID))
        {
            if(ghabz4 == null)
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(GhabzSearch.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(GhabzSearch.this);
                }
                builder.setTitle("ذخیره اشتراک؟")
                        .setMessage("آیا می خواهید این اشتراک به لیست اشتراک های من اضافه شود؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences.Editor editor = getSharedPreferences("ghabz", MODE_PRIVATE).edit();
                                editor.putString("ghabz_id4", ghabz_id);
                                editor.apply();

                            }
                        }).setNegativeButton("نه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete

                    }
                })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }







    }

    public void clk_message(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.GONE);
        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
        lay_more_detail.setVisibility(View.GONE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.GONE);

    }

    public void clk_back(View view) {
        finish();
    }

    public void clk_pay(View view) {
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.VISIBLE);
        WebView webview = (WebView) findViewById(R.id.web_view);
        webview.setWebViewClient(new myWebClient());
        webview.getSettings().setJavaScriptEnabled(true);
        //rslt_price="1000";
        allowBack=true;
        String
                price="18000";
        if(ghabz_id.equals(StaticGasGhabz.ghabz1_ID))
            price = StaticGasGhabz.ghabz1_price_number_simple;
        if(ghabz_id.equals(StaticGasGhabz.ghabz2_ID))
            price = StaticGasGhabz.ghabz2_price_number_simple;
        if(ghabz_id.equals(StaticWaterGhabz.ghabz1_ID))
            price = StaticWaterGhabz.ghabz1_price_number_simple;
        if(ghabz_id.equals(StaticWaterGhabz.ghabz2_ID))
            price = StaticWaterGhabz.ghabz2_price_number_simple;

        webview.loadUrl("http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount="+price+"&AdditionalInfo=10000089-CTSCar&MerchantID=118088384&TerminalId=17995091&TransactionKey=AZ24JJ95SS&OrderId=10000089235123552");
    }

    public void clk_back_complete(View view) {
//        fun.enableDisableView(lay_main, true);
////        RelativeLayout lay_message = findViewById(R.id.lay_message);
////        LinearLayout lay_more_detail = findViewById(R.id.lay_more_detail);
////        lay_message.setVisibility(View.GONE);
////        lay_more_detail.setVisibility(View.GONE);
     //   Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        if (!allowBack) {


        } else {
            super.onBackPressed();
        }
    }
    public void clk_back_more_info(View view) {
       // Toast.makeText(this, "321", Toast.LENGTH_SHORT).show();
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.GONE);

        lay_message.setVisibility(View.GONE);
        lay_more_detail.setVisibility(View.GONE);
        allowBack=true;
    }

    public void clk_back_gate(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.GONE);
        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
        lay_more_detail.setVisibility(View.GONE);
        lay_message.setVisibility(View.GONE);

        allowBack=true;
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


    public class Timer extends Thread {

        int oneSecond=1000;
        int value=0;
        String TAG="Timer";
        String typ="";
        public long milles=1000;


        //@Override
        public Timer(String type)
        {
            typ = type;
        }
        @Override
        public void run() {

            for(;;){


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {




                        if(typ.equals("timeout")) {
                            if(is_requested)
                            {
                                tim++;
                                if(tim>Functions.Time_out_limit)
                                {
                                    is_requested = false;
                                    fun.enableDisableView(lay_main,true);
                                    RelativeLayout lay_message = findViewById(R.id.lay_message);
                                    lay_message.setVisibility(View.GONE);
                                    LinearLayout lay_wait = findViewById(R.id.lay_wait);
                                    lay_wait.setVisibility(View.GONE);
                                    tim=1;
                                    // Log.d("majid",String.valueOf(tim));
                                    Toast.makeText(GhabzSearch.this, "خطای شبکه رخ داد", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        if(typ.equals("break")) {

                        }
                    }
                });


                //   Log.d("majid", String.valueOf(value));
                //Thread.currentThread();
                try {


                    Thread.sleep(milles);
                    //	Log.d(TAG, " " + value);
                } catch (InterruptedException e) {
                    System.out.println("timer interrupted");
                    //value=0;
                    e.printStackTrace();
                }
            }
        }



    }


}
