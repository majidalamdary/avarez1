package com.sputa.avarez;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.sputa.avarez.Functions;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sputa.avarez.adapters.adapter_bussiness;
import com.sputa.avarez.adapters.adapter_nosazi;
import com.sputa.avarez.adapters.adapter_pasmand;
import com.sputa.avarez.adapters.adapter_tablo;
import com.sputa.avarez.adapters.item_adapter;
import com.sputa.avarez.adapters.item_detail_parvandeh_adapter;
import com.sputa.avarez.classes.CallSoap;
import com.sputa.avarez.classes.StaticGasGhabz;
import com.sputa.avarez.classes.StaticWaterGhabz;
import com.sputa.avarez.model.item_bussiness;
import com.sputa.avarez.model.item_nosazi;
import com.sputa.avarez.model.item_tablo;
import com.sputa.avarez.model.item_pasmand;
import com.sputa.avarez.model.items;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static com.sputa.avarez.Functions.Lag;

public class CarSearch extends AppCompatActivity {
    private int screenWidth;
    private int screenHeight;
    private boolean is_requested;
    MyAsyncTask mm;
    MyAsyncTaskService Asy;
    private String last_query;
    RelativeLayout lay_main;
    private Functions fun;
    private int tim = 1;
    private Timer timer;
    private String motorSerial;
    private String rslt_MerchantId = "", rslt_TerMinalId = "";
    private String rslt_TransactionKey = "", rslt_OrderId = "";
    private String rslt_MainProfile = "";
    private String rslt_price = "0";
    private String rslt_CanEPay = "";
    private int avarez_price;
    private String rslt_name;
    private String rslt_family;
    List<items> item = new ArrayList<>();
    List<item_bussiness> item_bussinesses = new ArrayList<>();
    List<item_tablo> item_tablo = new ArrayList<>();
    List<item_pasmand> item_pasmand = new ArrayList<>();
    List<item_nosazi> item_nosazi = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private String rslt_NationalNumber;
    private String rslt_ChassiSerial;
    private String rslt_VinNumber;
    private boolean allowBack = true;
    private int avarez_Type;
    private int at_car = 1;
    private int at_bussiness = 2;
    private int at_tablo = 3;
    private int at_pasmand = 4;
    private int at_nosazi = 6;
    private int at_jame = 5;
    private String price_avarez;
    private SQLiteDatabase myDB;


    String
            NosaziBillID="";
    String
            NosaziPaymentID="";
    private boolean isPaid=false;
    private String pay_type="";
    private String NosaziCode="";
    private String IsPaid="";
    private UUID PublicAID;
    private boolean GoToPay=false;


    private void set_size(int vid, Double width, Double height, String typ) {

        View v = findViewById(vid);
        if (typ.equals("cons")) {
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) v.getLayoutParams();
            lp.width = (int) (screenWidth * width);
            lp.height = (int) (screenHeight * height);
            ;
            v.setLayoutParams(lp);
        }

        if (typ.equals("line")) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v.getLayoutParams();
            lp.width = (int) (screenWidth * width);
            lp.height = (int) (screenHeight * height);
            ;
            v.setLayoutParams(lp);
        }

        if (typ.equals("rel")) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
            lp.width = (int) (screenWidth * width);
            lp.height = (int) (screenHeight * height);
            ;
            v.setLayoutParams(lp);
        }

    }

    private void set_size_txt(int vid, Double size, String typ) {
        TextView v = (TextView) findViewById(vid);
        if (typ.equals("cons")) {
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
        if (typ.equals("line")) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
        if (typ.equals("rel")) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
    }

    private void set_size_edit(int vid, Double size, String typ) {
        EditText v = findViewById(vid);
        if (typ.equals("cons")) {
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
        if (typ.equals("line")) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
        if (typ.equals("rel")) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) v.getLayoutParams();
            v.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * size));
            v.setLayoutParams(lp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_search);
        lay_main = findViewById(R.id.lay_main);




        String str = getIntent().getStringExtra("typ");
        //  Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        if (str.equals("car"))
            avarez_Type = at_car;
        if (str.equals("bussiness"))
            avarez_Type = at_bussiness;
        if (str.equals("tablo"))
            avarez_Type = at_tablo;
        if (str.equals("pasmand"))
            avarez_Type = at_pasmand;
        if (str.equals("nosazi"))
            avarez_Type = at_nosazi;
        if (str.equals("jame"))
            avarez_Type = at_jame;

        TextView lbl_title = findViewById(R.id.lbl_title);
        ImageView img_title = findViewById(R.id.img_title);
        String[] arraySpinner;
        arraySpinner = new String[]{
                "شماره موتور", "شماره شاسی", "شماره VIN"
        };
        if (avarez_Type == at_car) {
            arraySpinner = new String[]{
                    "شماره موتور", "شماره شاسی", "شماره VIN"
            };
            lbl_title.setText("جستجو و پرداخت عوارض خودرو");
            img_title.setBackgroundResource(R.drawable.car);
        }
        if (avarez_Type == at_bussiness) {
            arraySpinner = new String[]{
                    "شماره پرونده", "کد ملی",
            };
            lbl_title.setText("جستجو و پرداخت عوارض کسب  و کار");
            img_title.setBackgroundResource(R.drawable.store);
        }
        if (avarez_Type == at_tablo) {
            arraySpinner = new String[]{
                    "شماره پرونده", "کد ملی",
            };
            lbl_title.setText("جستجو و پرداخت عوارض تابلو");
            img_title.setBackgroundResource(R.drawable.panel);
        }
        if (avarez_Type == at_pasmand) {
            arraySpinner = new String[]{
                    "شماره پرونده", "کد ملی",
            };
            lbl_title.setText("جستجو و پرداخت عوارض پسماند");
            img_title.setBackgroundResource(R.drawable.waste);
        }
        if (avarez_Type == at_nosazi) {
            arraySpinner = new String[]{
                    "کد نوسازی"
            };
            lbl_title.setText("جستجو و پرداخت عوارض نوسازی");
            img_title.setBackgroundResource(R.drawable.renovation);
            EditText editText = (EditText) findViewById(R.id.txt_nosazi_code1);
            editText.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }

        if (avarez_Type == at_jame) {
            arraySpinner = new String[]{
                    "کد ملی",
            };
            lbl_title.setText("جستجو و پرداخت عوارض جامع");
            img_title.setBackgroundResource(R.drawable.jame);
        }
        myDB = this.openOrCreateDatabase(getString(R.string.DB_name), MODE_PRIVATE, null);

        Spinner s = (Spinner) findViewById(R.id.spn_search_type);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);


        if (avarez_Type == at_car) {
            ConstraintLayout lay_motor_search = findViewById(R.id.lay_motor_search);
            lay_motor_search.setVisibility(View.VISIBLE);
            ConstraintLayout lay_parvandeh_search = findViewById(R.id.lay_parvandeh_search);
            lay_parvandeh_search.setVisibility(View.GONE);

        } else if (avarez_Type != at_jame) {
            ConstraintLayout lay_motor_search = findViewById(R.id.lay_motor_search);
            lay_motor_search.setVisibility(View.GONE);
            ConstraintLayout lay_parvandeh_search = findViewById(R.id.lay_parvandeh_search);
            lay_parvandeh_search.setVisibility(View.VISIBLE);

        } else {
            ConstraintLayout lay_motor_search = findViewById(R.id.lay_motor_search);
            lay_motor_search.setVisibility(View.GONE);
            ConstraintLayout lay_melli_search = findViewById(R.id.lay_melli_search);
            lay_melli_search.setVisibility(View.VISIBLE);
        }

        fun = new Functions();

        timer = new Timer("timeout");
        timer.start();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        set_size(R.id.txt_motor, .52, .07, "cons");




        set_size_txt(R.id.lbl_nosazi, .04, "line");
        set_size_txt(R.id.lbl_nosazi_code1, .06, "line");
        set_size_txt(R.id.lbl_nosazi_code2, .06, "line");
        set_size_txt(R.id.lbl_nosazi_code3, .06, "line");
        set_size_txt(R.id.lbl_nosazi_code4, .06, "line");
        set_size_txt(R.id.lbl_nosazi_code5, .06, "line");
        set_size_txt(R.id.lbl_nosazi_code6, .06, "line");

        set_size_txt(R.id.txt_nosazi_code1, .04, "line");
        set_size_txt(R.id.txt_nosazi_code2, .04, "line");
        set_size_txt(R.id.txt_nosazi_code3, .04, "line");
        set_size_txt(R.id.txt_nosazi_code4, .04, "line");
        set_size_txt(R.id.txt_nosazi_code5, .04, "line");
        set_size_txt(R.id.txt_nosazi_code6, .04, "line");
        set_size_txt(R.id.txt_nosazi_code7, .04, "line");
        set_size(R.id.lay_nosazi_holder, .92, .06, "cons");

        set_size_txt(R.id.lbl_title, .058, "cons");

        set_size_txt(R.id.lbl_motor, .05, "cons");
        set_size_edit(R.id.txt_motor, .06, "cons");
        set_size_txt(R.id.lbl_search_type, .05, "cons");
        set_size_txt(R.id.lbl_msg, .039, "cons");
        set_size_txt(R.id.lbl_price, .043, "cons");
        set_size_txt(R.id.lbl_msg_jame_left, .039, "line");
        set_size_txt(R.id.lbl_msg_jame_right, .039, "line");
        set_size(R.id.lbl_msg, .9, .18, "cons");
        //set_size(R.id.lbl_msg_jame, .8, .37, "cons");
        if (avarez_Type == at_bussiness)
            set_size(R.id.img_title, .14, .08, "cons");
        else
            set_size(R.id.img_title, .14, .09, "cons");
        Spinner spn = findViewById(R.id.spn_search_type);
        set_size(R.id.spn_search_type, .52, .07, "cons");

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                EditText txt_motor = findViewById(R.id.txt_motor);
                EditText txt_shasi = findViewById(R.id.txt_shasi);
                EditText txt_vin = findViewById(R.id.txt_vin);

                txt_motor.setText("");
                txt_shasi.setText("");
                txt_vin.setText("");

                if (avarez_Type == at_car) {
                    if (position == 0) {
                        ConstraintLayout lay_motor_search = findViewById(R.id.lay_motor_search);
                        lay_motor_search.setVisibility(View.VISIBLE);
                        ConstraintLayout lay_shasi_search = findViewById(R.id.lay_shasi_search);
                        lay_shasi_search.setVisibility(View.GONE);
                        ConstraintLayout lay_vin_search = findViewById(R.id.lay_vin_search);
                        lay_vin_search.setVisibility(View.GONE);
                    }
                    if (position == 1) {
                        ConstraintLayout lay_motor_search = findViewById(R.id.lay_motor_search);
                        lay_motor_search.setVisibility(View.GONE);
                        ConstraintLayout lay_shasi_search = findViewById(R.id.lay_shasi_search);
                        lay_shasi_search.setVisibility(View.VISIBLE);
                        ConstraintLayout lay_vin_search = findViewById(R.id.lay_vin_search);
                        lay_vin_search.setVisibility(View.GONE);
                    }
                    if (position == 2) {
                        ConstraintLayout lay_motor_search = findViewById(R.id.lay_motor_search);
                        lay_motor_search.setVisibility(View.GONE);
                        ConstraintLayout lay_shasi_search = findViewById(R.id.lay_shasi_search);
                        lay_shasi_search.setVisibility(View.GONE);
                        ConstraintLayout lay_vin_search = findViewById(R.id.lay_vin_search);
                        lay_vin_search.setVisibility(View.VISIBLE);
                    }
                } else if (avarez_Type != at_jame && avarez_Type != at_nosazi) {
                    if (position == 0) {
                        ConstraintLayout lay_motor_search = findViewById(R.id.lay_parvandeh_search);
                        lay_motor_search.setVisibility(View.VISIBLE);
                        ConstraintLayout lay_melli_search = findViewById(R.id.lay_melli_search);
                        lay_melli_search.setVisibility(View.GONE);

                    }
                    if (position == 1) {
                        ConstraintLayout lay_parvandeh_search = findViewById(R.id.lay_parvandeh_search);
                        lay_parvandeh_search.setVisibility(View.GONE);
                        ConstraintLayout lay_melli_search = findViewById(R.id.lay_melli_search);
                        lay_melli_search.setVisibility(View.VISIBLE);
                    }
                } else if (avarez_Type == at_nosazi){
                    if (position == 0) {
                        ConstraintLayout lay_motor_search = findViewById(R.id.lay_parvandeh_search);
                        lay_motor_search.setVisibility(View.GONE);
                        ConstraintLayout lay_melli_search = findViewById(R.id.lay_melli_search);
                        lay_melli_search.setVisibility(View.GONE);
                        ConstraintLayout lay_nosazi_search = findViewById(R.id.lay_nosazi_search);
                        lay_nosazi_search.setVisibility(View.VISIBLE);

                    }
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

        set_size(R.id.txt_shasi, .52, .07, "cons");
        set_size_txt(R.id.lbl_shasi, .04, "cons");
        set_size_txt(R.id.lbl_parvandeh, .04, "cons");
        set_size_txt(R.id.lbl_melli, .042, "cons");
        set_size_edit(R.id.txt_shasi, .04, "cons");
        set_size_edit(R.id.txt_motor, .04, "cons");
        set_size_edit(R.id.txt_parvndeh, .04, "cons");
        set_size_edit(R.id.txt_melli, .04, "cons");


        set_size(R.id.btn_search, .3, .065, "cons");
        set_size(R.id.btn_back, .3, .065, "cons");
        set_size_txt(R.id.lbl_search, .054, "line");
        set_size_txt(R.id.lbl_back, .054, "line");
        set_size(R.id.btn_pay, .3, .065, "cons");
        set_size(R.id.btn_detail, .3, .065, "cons");
        set_size(R.id.btn_taghsit, .3, .065, "cons");

        set_size_txt(R.id.lbl_help, .033, "cons");
        set_size(R.id.img_help_motor, .08, .06, "cons");
        set_size(R.id.img_help_shasi, .08, .06, "cons");
        set_size(R.id.img_help_shasi, .08, .06, "cons");


        set_size(R.id.lay_confirm, 0.95, .14, "cons");
        set_size(R.id.btn_yes_correct, .12, .052, "cons");
        set_size(R.id.btn_no_will_correct, .3, .052, "cons");
        set_size_txt(R.id.lbl_confirm_msg, .044, "cons");
        set_size_txt(R.id.lbl_yes_correct, .04, "line");
        set_size_txt(R.id.lbl_no_will_correct, .04, "line");

        set_size(R.id.lay_complete_info, 0.83, .76, "rel");
        set_size(R.id.btn_save_complete, 0.26, .06, "cons");
        set_size(R.id.btn_back_complete, 0.26, .06, "cons");

        set_size(R.id.txt_name_complete, .44, .07, "cons");
        set_size(R.id.txt_family_complete, .44, .07, "cons");
        set_size_txt(R.id.lbl_name_complete, .04, "cons");
        set_size_edit(R.id.txt_name_complete, .04, "cons");

        set_size_txt(R.id.lbl_family_complete, .04, "cons");
        set_size_edit(R.id.txt_family_complete, .04, "cons");

        set_size_txt(R.id.lbl_melliId_complete, .04, "cons");
        set_size_edit(R.id.txt_melliID_complete, .044, "cons");
        set_size(R.id.txt_melliID_complete, .44, .07, "cons");

        set_size_txt(R.id.lbl_shasi_complete, .04, "cons");
        set_size_edit(R.id.txt_shasi_complete, .044, "cons");
        set_size(R.id.txt_shasi_complete, .44, .07, "cons");

        set_size_txt(R.id.lbl_VIN_complete, .04, "cons");
        set_size_edit(R.id.txt_VIN_complete, .042, "cons");
        set_size(R.id.txt_VIN_complete, .44, .07, "cons");
        set_size_txt(R.id.lbl_complete_title, .051, "cons");



    }

    public void clk_search(View view) throws UnsupportedEncodingException {




//        String
//                str="majid"+"مجید";
//        byte[] bytesEncoded = Base64.encode(str.getBytes("UTF-8"), Base64.DEFAULT);
//        String st = Base64.encodeToString(str.getBytes("UTF-8"), Base64.DEFAULT);
//        Lag("st="+st);
////        byte[] bytesEncoded =str.getBytes("UTF-8");
//
//        for (int i = 0; i < bytesEncoded.length; i++) {
//            String h = Integer.toString( bytesEncoded[i]);
//            Lag(h);
//        }

        if (avarez_Type == at_car)
            search_car();
        else if (avarez_Type != at_jame)
            search_bussinessAndOthers();
        else
            search_jame();


    }

    private void search_jame() {
        EditText txt_melli = findViewById(R.id.txt_melli);

        String
                search_type = "none";
        if (txt_melli.getText().toString().length() > 0) {
            search_type = "ok";
        }

        if (search_type.equals("ok")) {

            if (txt_melli.getText().toString().length() > 0) {

                float koll=0;
                String name="";
                String msg_detail_right="";
                String msg_detail_left="";
                Cursor cr = myDB.rawQuery("select * from Business where ID='" + txt_melli.getText().toString() + "'", null);
                if(cr.getCount()>0)
                {
                    cr.moveToFirst();
                    koll+=cr.getFloat(10);
                    if(cr.getFloat(10)>0) {
                        name = cr.getString(2) + " " + cr.getString(3);
                        msg_detail_right += "مبلغ عوارض کسب و کار" + "\n";
                        msg_detail_left += " :   " + digiting(String.valueOf((int) (cr.getFloat(10)))) + "\n";
                    }
                }
                cr = myDB.rawQuery("select * from waste where ID='" + txt_melli.getText().toString() + "'", null);
                if(cr.getCount()>0)
                {
                    cr.moveToFirst();
                    koll+=cr.getFloat(11);
                    if(cr.getFloat(11)>0) {
                        name = cr.getString(2) + " " + cr.getString(3);
                        msg_detail_right += "مبلغ عوارض پسماند" + "\n";
                        msg_detail_left += " :   " + digiting(String.valueOf((int) (cr.getFloat(11)))) + "\n";
                    }
                }
                cr = myDB.rawQuery("select * from nosazi where ID='" + txt_melli.getText().toString() + "'", null);
                if(cr.getCount()>0)
                {
                    cr.moveToFirst();
                    koll+=cr.getFloat(10);
                    if(cr.getFloat(10)>0) {
                        name = cr.getString(2) + " " + cr.getString(3);
                        msg_detail_right += "مبلغ عوارض نوسازی" + "\n";
                        msg_detail_left += " :   " + digiting(String.valueOf((int) (cr.getFloat(10)))) + "\n";
                    }
                }
                cr = myDB.rawQuery("select * from panel where ID='" + txt_melli.getText().toString() + "'", null);
                if(cr.getCount()>0)
                {
                    cr.moveToFirst();
                    koll+=cr.getFloat(10);
                    if(cr.getFloat(10)>0) {
                        name = cr.getString(2) + " " + cr.getString(3);
                        msg_detail_right += "مبلغ عوارض تابلو" + "\n";
                        msg_detail_left += " :   " + digiting(String.valueOf((int) (cr.getFloat(10)))) + "\n";
                    }
                }


                if (name.length()>0) {
                    LinearLayout btn_pay = findViewById(R.id.btn_pay);
                    LinearLayout btn_taghsit = findViewById(R.id.btn_taghsit);
                    LinearLayout btn_detail = findViewById(R.id.btn_detail);
                    TextView lbl_msg = findViewById(R.id.lbl_msg);
                    TextView lbl_msg_jame_right = findViewById(R.id.lbl_msg_jame_right);
                    TextView lbl_msg_jame_left = findViewById(R.id.lbl_msg_jame_left);
                    btn_pay.setVisibility(View.VISIBLE);
                    btn_detail.setVisibility(View.VISIBLE);
                    btn_taghsit.setVisibility(View.VISIBLE);
                    String
                            msg = "";
                    String
                            msg_jame = "";

                        msg = " شهرداری ارومیه " + "\n" + "نام مالک : " + name + "\n" + " مبلغ سرجمع عوارض جامع شما " + digiting(String.valueOf((int) (koll)))  + " ریال می باشد.";
                        msg_jame=msg_detail_right;
                        price_avarez = String.valueOf((int) (koll));





                    lbl_msg.setText(msg);
                    lbl_msg_jame_right.setText(msg_detail_right);
                    lbl_msg_jame_left.setText(msg_detail_left);
                }

            }
        } else {
            Toast.makeText(this, "لطفا اطلاعات درخواستی را وارد کنید", Toast.LENGTH_SHORT).show();
        }
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

    private void search_bussinessAndOthers() {
        EditText txt_parvndeh = findViewById(R.id.txt_parvndeh);
        EditText txt_melli = findViewById(R.id.txt_melli);


        String
                search_type = "none";
        if (txt_parvndeh.getText().toString().length() > 0) {
            search_type = "ok";
        } else if (txt_melli.getText().toString().length() > 0) {
            search_type = "ok";
        }

        if (avarez_Type == at_nosazi) {
            search_nosazi();
        } else if (search_type.equals("ok")) {


            if (avarez_Type == at_bussiness) {
                search_bussiess();
            }
            if (avarez_Type == at_tablo) {
                search_tablo();
            }
            if (avarez_Type == at_pasmand) {
                search_pasmand();
            }



        } else {
            Toast.makeText(this, "لطفا اطلاعات درخواستی را وارد کنید", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(pay_type.equals("nosazi")) {
            checkNosaziIsPaidOrNot();
        }

    }

    private void search_bussiess() {

        LinearLayout lay_header_bussiness=findViewById(R.id.lay_header_bussiness);
        LinearLayout lay_header_pasmand =findViewById(R.id.lay_header_pasmand);
        LinearLayout lay_header_nosazi =findViewById(R.id.lay_header_nosazi);
        lay_header_nosazi.setVisibility(View.INVISIBLE);
        lay_header_bussiness.setVisibility(View.VISIBLE);
        lay_header_pasmand.setVisibility(View.INVISIBLE);

        EditText txt_parvndeh = findViewById(R.id.txt_parvndeh);
        EditText txt_melli = findViewById(R.id.txt_melli);
        Cursor cr = myDB.rawQuery("select * from Business where parvandeh='" + txt_parvndeh.getText().toString() + "'", null);
        if (txt_parvndeh.getText().toString().length() > 0) {
            cr = myDB.rawQuery("select * from Business where parvandeh='" + txt_parvndeh.getText().toString() + "'", null);
        }
        if (txt_melli.getText().toString().length() > 0) {
            cr = myDB.rawQuery("select * from Business where ID='" + txt_melli.getText().toString() + "'", null);
        }

        if (cr.getCount() > 0) {
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            LinearLayout btn_detail = findViewById(R.id.btn_detail);
            TextView lbl_msg = findViewById(R.id.lbl_msg);
            btn_pay.setVisibility(View.VISIBLE);
            btn_detail.setVisibility(View.VISIBLE);
            String
                    msg = "";
            cr.moveToFirst();
            if (cr.getFloat(10) != 0) {
                msg = " شهرداری ارومیه " + "\n" + "نام مالک : " + cr.getString(2) + " " + cr.getString(3) + "\n" + " مبلغ عوارض شما " + digiting(String.valueOf((int) cr.getFloat(10))) + " ریال می باشد.";
                item_bussinesses.clear();
                item_bussinesses.add(new item_bussiness(cr.getString(5), cr.getString(6), cr.getString(7), cr.getString(8), cr.getString(9), cr.getString(10)));
                price_avarez = String.valueOf((int) cr.getFloat(10));
                mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_parvandeh);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(CarSearch.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new adapter_bussiness(CarSearch.this, item_bussinesses);
                mRecyclerView.setAdapter(mAdapter);
                lbl_msg.setText(msg);
            } else {
                msg = "";
                lbl_msg.setText(msg);
                btn_pay.setVisibility(View.GONE);
                btn_detail.setVisibility(View.GONE);
                Toast.makeText(this, "موردی پیدا نشد", Toast.LENGTH_SHORT).show();
            }

        } else {
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            LinearLayout btn_detail = findViewById(R.id.btn_detail);
            TextView lbl_msg = findViewById(R.id.lbl_msg);
            btn_pay.setVisibility(View.GONE);
            btn_detail.setVisibility(View.GONE);
            String
                    msg = "";
            msg = "";
            lbl_msg.setText(msg);
            Toast.makeText(this, "موردی پیدا نشد", Toast.LENGTH_SHORT).show();
        }
    }

    private void search_tablo() {
        EditText txt_parvndeh = findViewById(R.id.txt_parvndeh);
        EditText txt_melli = findViewById(R.id.txt_melli);
        LinearLayout lay_header_bussiness=findViewById(R.id.lay_header_bussiness);
        LinearLayout lay_header_pasmand =findViewById(R.id.lay_header_pasmand);
        LinearLayout lay_header_nosazi =findViewById(R.id.lay_header_nosazi);
        lay_header_nosazi.setVisibility(View.INVISIBLE);
        lay_header_bussiness.setVisibility(View.VISIBLE);
        lay_header_pasmand.setVisibility(View.INVISIBLE);

        Cursor cr = myDB.rawQuery("select * from panel where parvandeh='" + txt_parvndeh.getText().toString() + "'", null);
        if (txt_parvndeh.getText().toString().length() > 0) {
            cr = myDB.rawQuery("select * from panel where parvandeh='" + txt_parvndeh.getText().toString() + "'", null);
        }
        if (txt_melli.getText().toString().length() > 0) {
            cr = myDB.rawQuery("select * from panel where ID='" + txt_melli.getText().toString() + "'", null);
        }

        if (cr.getCount() > 0) {
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            LinearLayout btn_detail = findViewById(R.id.btn_detail);
            TextView lbl_msg = findViewById(R.id.lbl_msg);
            btn_pay.setVisibility(View.VISIBLE);
            btn_detail.setVisibility(View.VISIBLE);
            String
                    msg = "";
            cr.moveToFirst();
            if (cr.getFloat(10) != 0) {
                msg = " شهرداری ارومیه " + "\n" + "نام مالک : " + cr.getString(2) + " " + cr.getString(3) + "\n" + " مبلغ عوارض شما " + digiting(String.valueOf((int) cr.getFloat(10))) + " ریال می باشد.";
                item_tablo.clear();
                item_tablo.add(new item_tablo(cr.getString(5), cr.getString(6), cr.getString(7), digiting(String.valueOf((int) cr.getFloat(8))), digiting(String.valueOf((int) cr.getFloat(9))), digiting(String.valueOf((int) cr.getFloat(10)))));
                price_avarez = String.valueOf((int) cr.getFloat(10));
                mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_parvandeh);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(CarSearch.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new adapter_tablo(CarSearch.this, item_tablo);
                mRecyclerView.setAdapter(mAdapter);
                lbl_msg.setText(msg);
            } else {
                msg = "";
                lbl_msg.setText(msg);
                btn_pay.setVisibility(View.GONE);
                btn_detail.setVisibility(View.GONE);
                Toast.makeText(this, "موردی پیدا نشد", Toast.LENGTH_SHORT).show();
            }

        } else {
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            LinearLayout btn_detail = findViewById(R.id.btn_detail);
            TextView lbl_msg = findViewById(R.id.lbl_msg);
            btn_pay.setVisibility(View.GONE);
            btn_detail.setVisibility(View.GONE);
            String
                    msg = "";
            msg = "";
            lbl_msg.setText(msg);
            Toast.makeText(this, "موردی پیدا نشد", Toast.LENGTH_SHORT).show();
        }
    }

    private void search_pasmand() {
        EditText txt_parvndeh = findViewById(R.id.txt_parvndeh);
        EditText txt_melli = findViewById(R.id.txt_melli);
        LinearLayout lay_header_bussiness=findViewById(R.id.lay_header_bussiness);
        LinearLayout lay_header_pasmand =findViewById(R.id.lay_header_pasmand);
        LinearLayout lay_header_nosazi =findViewById(R.id.lay_header_nosazi);
        lay_header_nosazi.setVisibility(View.INVISIBLE);
        lay_header_bussiness.setVisibility(View.INVISIBLE);
        lay_header_pasmand.setVisibility(View.VISIBLE);

        Cursor cr = myDB.rawQuery("select * from waste where parvandeh='" + txt_parvndeh.getText().toString() + "'", null);
        if (txt_parvndeh.getText().toString().length() > 0) {
            cr = myDB.rawQuery("select * from waste where parvandeh='" + txt_parvndeh.getText().toString() + "'", null);
        }
        if (txt_melli.getText().toString().length() > 0) {
            cr = myDB.rawQuery("select * from waste where ID='" + txt_melli.getText().toString() + "'", null);
        }

        if (cr.getCount() > 0) {
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            LinearLayout btn_detail = findViewById(R.id.btn_detail);
            TextView lbl_msg = findViewById(R.id.lbl_msg);
            btn_pay.setVisibility(View.VISIBLE);
            btn_detail.setVisibility(View.VISIBLE);
            String
                    msg = "";
            cr.moveToFirst();
            if (cr.getFloat(11) != 0) {
                msg = " شهرداری ارومیه " + "\n" + "نام مالک : " + cr.getString(2) + " " + cr.getString(3) + "\n" + " مبلغ عوارض شما " + digiting(String.valueOf((int) cr.getFloat(11))) + " ریال می باشد.";
                item_pasmand.clear();
                item_pasmand.add(new item_pasmand(cr.getString(5), cr.getString(6), cr.getString(7), cr.getString(8), cr.getString(9), digiting(String.valueOf((int) cr.getFloat(10))), digiting(String.valueOf((int) cr.getFloat(11)))));
                price_avarez = String.valueOf((int) cr.getFloat(10));
                mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_parvandeh);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(CarSearch.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new adapter_pasmand(CarSearch.this, item_pasmand);
                mRecyclerView.setAdapter(mAdapter);
                lbl_msg.setText(msg);
            } else {
                msg = "";
                lbl_msg.setText(msg);
                btn_pay.setVisibility(View.GONE);
                btn_detail.setVisibility(View.GONE);
                Toast.makeText(this, "موردی پیدا نشد", Toast.LENGTH_SHORT).show();
            }

        } else {
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            LinearLayout btn_detail = findViewById(R.id.btn_detail);
            TextView lbl_msg = findViewById(R.id.lbl_msg);
            btn_pay.setVisibility(View.GONE);
            btn_detail.setVisibility(View.GONE);
            String
                    msg = "";
            msg = "";
            lbl_msg.setText(msg);
            Toast.makeText(this, "موردی پیدا نشد", Toast.LENGTH_SHORT).show();
        }
    }
    private void search_nosazi()
    {
        EditText txt_nosazi_code1 = findViewById(R.id.txt_nosazi_code1);
        EditText txt_nosazi_code2 = findViewById(R.id.txt_nosazi_code2);
        EditText txt_nosazi_code3 = findViewById(R.id.txt_nosazi_code3);
        EditText txt_nosazi_code4 = findViewById(R.id.txt_nosazi_code4);
        EditText txt_nosazi_code5 = findViewById(R.id.txt_nosazi_code5);
        EditText txt_nosazi_code6 = findViewById(R.id.txt_nosazi_code6);
        EditText txt_nosazi_code7 = findViewById(R.id.txt_nosazi_code7);
        if(txt_nosazi_code1.getText().toString().length()>0 && txt_nosazi_code2.getText().toString().length()>0 && txt_nosazi_code3.getText().toString().length()>0 && txt_nosazi_code4.getText().toString().length()>0 && txt_nosazi_code5.getText().toString().length()>0 && txt_nosazi_code6.getText().toString().length()>0 && txt_nosazi_code7.getText().toString().length()>0  ) {
            NosaziCode = txt_nosazi_code1.getText().toString()+"-"+txt_nosazi_code2.getText().toString()+"-"+txt_nosazi_code3.getText().toString()+"-"+txt_nosazi_code4.getText().toString()+"-"+txt_nosazi_code5.getText().toString()+"-"+txt_nosazi_code6.getText().toString()+"-"+txt_nosazi_code7.getText().toString();
            LinearLayout lay_header_bussiness = findViewById(R.id.lay_header_bussiness);
            LinearLayout lay_header_pasmand = findViewById(R.id.lay_header_pasmand);
            LinearLayout lay_header_nosazi = findViewById(R.id.lay_header_nosazi);
            lay_header_nosazi.setVisibility(View.VISIBLE);
            lay_header_bussiness.setVisibility(View.INVISIBLE);
            lay_header_pasmand.setVisibility(View.INVISIBLE);

            Asy = new MyAsyncTaskService("GetInfoNosazi",NosaziCode );
            Asy.execute();
            is_requested = true;
            fun.enableDisableView(lay_main, false);
            RelativeLayout lay_message = findViewById(R.id.lay_message);
            lay_message.setVisibility(View.VISIBLE);
            LinearLayout lay_wait = findViewById(R.id.lay_wait);
            lay_wait.setVisibility(View.VISIBLE);
            set_size(R.id.lay_wait, .6, .3, "rel");
            set_size_txt(R.id.lbl_please_wait, .05, "line");

            set_size_txt(R.id.lbl_pay_avarez_type, .05, "line");
            set_size_txt(R.id.lbl_pay_result, .05, "line");
            set_size_txt(R.id.lbl_rahgiri, .05, "line");
            set_size_txt(R.id.lbl_result_in_source, .05, "line");
            tim=1;
            Lag( "nosazi");

        }
        else
        {
            Toast.makeText(this, "کد نوسازی صحیح وارد نشده است", Toast.LENGTH_SHORT).show();
        }
    }
    private void search_nosazi_demo() {
        EditText txt_parvndeh = findViewById(R.id.txt_parvndeh);
        EditText txt_melli = findViewById(R.id.txt_melli);
        LinearLayout lay_header_bussiness=findViewById(R.id.lay_header_bussiness);
        LinearLayout lay_header_pasmand =findViewById(R.id.lay_header_pasmand);
        LinearLayout lay_header_nosazi =findViewById(R.id.lay_header_nosazi);
        lay_header_nosazi.setVisibility(View.VISIBLE);
        lay_header_bussiness.setVisibility(View.INVISIBLE);
        lay_header_pasmand.setVisibility(View.INVISIBLE);

        Cursor cr = myDB.rawQuery("select * from nosazi where parvandeh='" + txt_parvndeh.getText().toString() + "'", null);
        if (txt_parvndeh.getText().toString().length() > 0) {
            cr = myDB.rawQuery("select * from nosazi where parvandeh='" + txt_parvndeh.getText().toString() + "'", null);
        }
        if (txt_melli.getText().toString().length() > 0) {
            cr = myDB.rawQuery("select * from nosazi where ID='" + txt_melli.getText().toString() + "'", null);
        }

        if (cr.getCount() > 0) {
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            LinearLayout btn_detail = findViewById(R.id.btn_detail);
            TextView lbl_msg = findViewById(R.id.lbl_msg);
            btn_pay.setVisibility(View.VISIBLE);
            btn_detail.setVisibility(View.VISIBLE);
            String
                    msg = "";
            cr.moveToFirst();
            if (cr.getFloat(10) != 0) {
                msg = " شهرداری ارومیه " + "\n" + "نام مالک : " + cr.getString(2) + " " + cr.getString(3) + "\n" + " مبلغ عوارض شما " + digiting(String.valueOf((int) cr.getFloat(10))) + " ریال می باشد.";
                item_nosazi.clear();
                item_nosazi.add(new item_nosazi(cr.getString(5), cr.getString(6), cr.getString(7), digiting(String.valueOf((int) cr.getFloat(8))), digiting(String.valueOf((int) cr.getFloat(9))), digiting(String.valueOf((int) cr.getFloat(10)))));
                price_avarez = String.valueOf((int) cr.getFloat(10));
                mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_parvandeh);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(CarSearch.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new adapter_nosazi(CarSearch.this, item_nosazi);
                mRecyclerView.setAdapter(mAdapter);
                lbl_msg.setText(msg);
            } else {
                msg = "";
                lbl_msg.setText(msg);
                btn_pay.setVisibility(View.GONE);
                btn_detail.setVisibility(View.GONE);
                Toast.makeText(this, "موردی پیدا نشد", Toast.LENGTH_SHORT).show();
            }

        } else {
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            LinearLayout btn_detail = findViewById(R.id.btn_detail);
            TextView lbl_msg = findViewById(R.id.lbl_msg);
            btn_pay.setVisibility(View.GONE);
            btn_detail.setVisibility(View.GONE);
            String
                    msg = "";
            msg = "";
            lbl_msg.setText(msg);
            Toast.makeText(this, "موردی پیدا نشد", Toast.LENGTH_SHORT).show();
        }
    }

    private void search_car() {
        EditText txt_motor = findViewById(R.id.txt_motor);
        EditText txt_shasi = findViewById(R.id.txt_shasi);
        EditText txt_vin = findViewById(R.id.txt_vin);
        String
                search_type = "none";
        if (txt_motor.getText().toString().length() > 0) {
            search_type = "ok";
        } else if (txt_shasi.getText().toString().length() > 0) {
            search_type = "ok";
        } else if (txt_vin.getText().toString().length() > 0) {
            search_type = "ok";
        }
        if (search_type.equals("ok")) {
            last_query = getResources().getString(R.string.site_url) + "do.aspx?param=get_avarez_motor&key_motor=" + txt_motor.getText().toString() + "&key_shasi=" + txt_shasi.getText().toString() + "&key_vin=" + txt_vin.getText().toString() + "&u_id=" + Functions.u_id + "&rnd=" + String.valueOf(new Random().nextInt());
            //     Toast.makeText(this, last_query, Toast.LENGTH_SHORT).show();
            mm = new MyAsyncTask();
            mm.url = (last_query);
            mm.execute("");
            is_requested = true;
            fun.enableDisableView(lay_main, false);
            RelativeLayout lay_message = findViewById(R.id.lay_message);
            lay_message.setVisibility(View.VISIBLE);
            LinearLayout lay_wait = findViewById(R.id.lay_wait);
            lay_wait.setVisibility(View.VISIBLE);
            set_size(R.id.lay_wait, .6, .3, "rel");
            set_size_txt(R.id.lbl_please_wait, .05, "line");
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            btn_pay.setVisibility(View.GONE);
            LinearLayout btn_detail = findViewById(R.id.btn_detail);
            btn_detail.setVisibility(View.GONE);

            ConstraintLayout lay_confirm = findViewById(R.id.lay_confirm);
            lay_confirm.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "لطفا اطلاعات درخواستی را وارد کنید", Toast.LENGTH_SHORT).show();
        }
    }

    public void clk_back(View view) {
        finish();
    }

    public void clk_help_motor(View view) {
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        RelativeLayout lay_help_motor = findViewById(R.id.lay_help_motor);
        set_size(R.id.im_help_motor_pic, .8, .8, "rel");
        fun.enableDisableView(lay_main, false);
        lay_message.setVisibility(View.VISIBLE);
        lay_help_motor.setVisibility(View.VISIBLE);
    }

    public void clk_help_vin(View view) {
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        RelativeLayout lay_help_motor = findViewById(R.id.lay_help_vin);
        set_size(R.id.im_help_vin_pic, .8, .8, "rel");
        fun.enableDisableView(lay_main, false);
        lay_message.setVisibility(View.VISIBLE);
        lay_help_motor.setVisibility(View.VISIBLE);
    }

    public void clk_message(View view) {
        LinearLayout lay_wait = findViewById(R.id.lay_wait);
        LinearLayout lay_pay_message = findViewById(R.id.lay_pay_message);
        ConstraintLayout lay_complete_info = findViewById(R.id.lay_complete_info);

        if (lay_wait.getVisibility() == (View.GONE) && lay_complete_info.getVisibility() == (View.GONE) && lay_pay_message.getVisibility() == (View.GONE)) {
            RelativeLayout lay_message = findViewById(R.id.lay_message);
            RelativeLayout lay_help_motor = findViewById(R.id.lay_help_motor);
            RelativeLayout lay_help_shasi = findViewById(R.id.lay_help_shasi);
            ConstraintLayout lay_detail = findViewById(R.id.lay_detail);

            fun.enableDisableView(lay_main, true);
            lay_message.setVisibility(View.GONE);
            lay_help_motor.setVisibility(View.GONE);
            lay_help_shasi.setVisibility(View.GONE);
            lay_detail.setVisibility(View.GONE);

        }
    }

    private void checkNosaziIsPaidOrNot() {
        Asy =new MyAsyncTaskService("CheckNosaziPaidOrNot","BackFromPay");
        Asy.execute();

    }

    public void clk_help_shasi(View view) {
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        RelativeLayout lay_help_motor = findViewById(R.id.lay_help_shasi);
        set_size(R.id.lbl_help_shasi, .6, .3, "rel");
        set_size_txt(R.id.lbl_help_shasi, .042, "rel");
        fun.enableDisableView(lay_main, false);
        lay_message.setVisibility(View.VISIBLE);
        lay_help_motor.setVisibility(View.VISIBLE);
    }

    public void clk_yes_correct(View view) {
        ConstraintLayout lay_confirm = findViewById(R.id.lay_confirm);
        LinearLayout lay_btn_pay = findViewById(R.id.btn_pay);
        LinearLayout lay_btn_detail = findViewById(R.id.btn_detail);
        lay_confirm.setVisibility(View.GONE);
        if (avarez_price > 0) {
            lay_btn_pay.setVisibility(View.VISIBLE);
            lay_btn_detail.setVisibility(View.VISIBLE);
        }
    }

    public void clk_no_will_correct(View view) {

        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        ConstraintLayout lay_wait = findViewById(R.id.lay_complete_info);
        lay_wait.setVisibility(View.VISIBLE);
        EditText txt_name_complete = findViewById(R.id.txt_name_complete);
        EditText txt_family_complete = findViewById(R.id.txt_family_complete);
        EditText txt_melliID_complete = findViewById(R.id.txt_melliID_complete);
        EditText txt_shasi_complete = findViewById(R.id.txt_shasi_complete);
        EditText txt_VIN_complete = findViewById(R.id.txt_VIN_complete);
        txt_name_complete.setText(rslt_name);
        txt_family_complete.setText(rslt_family);
        txt_melliID_complete.setText(rslt_NationalNumber);
        txt_shasi_complete.setText(rslt_ChassiSerial);
        txt_VIN_complete.setText(rslt_VinNumber);

    }

    public void clk_back_complete(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.GONE);
        ConstraintLayout lay_wait = findViewById(R.id.lay_complete_info);
        lay_wait.setVisibility(View.GONE);
        ConstraintLayout lay_detail = findViewById(R.id.lay_detail);
        lay_detail.setVisibility(View.GONE);

    }

    public void clk_save_complete(View view) {
        EditText txt_name_complete = findViewById(R.id.txt_name_complete);
        EditText txt_family_complete = findViewById(R.id.txt_family_complete);
        EditText txt_melliID_complete = findViewById(R.id.txt_melliID_complete);
        EditText txt_shasi_complete = findViewById(R.id.txt_shasi_complete);
        EditText txt_VIN_complete = findViewById(R.id.txt_VIN_complete);

        String
                search_type = "ok";
        if (txt_name_complete.getText().toString().length() == 0 && search_type.equals("ok")) {
            search_type = "none";
        }
        if (txt_family_complete.getText().toString().length() == 0 && search_type.equals("ok")) {
            search_type = "none";
        }
        if (txt_melliID_complete.getText().toString().length() == 0 && search_type.equals("ok")) {
            search_type = "none";
        }
        if (txt_shasi_complete.getText().toString().length() == 0 && search_type.equals("ok")) {
            search_type = "none";
        }
        if (txt_VIN_complete.getText().toString().length() == 0 && search_type.equals("ok")) {
            search_type = "none";
        }

        if (search_type.equals("ok")) {
            last_query = getResources().getString(R.string.site_url) + "do.aspx?param=save_complete&motorSerial=" + motorSerial + "&name=" + URLEncoder.encode(txt_name_complete.getText().toString()) + "&family=" + URLEncoder.encode(txt_family_complete.getText().toString()) + "&melliID=" + URLEncoder.encode(txt_melliID_complete.getText().toString()) + "&shasi=" + URLEncoder.encode(txt_shasi_complete.getText().toString()) + "&VIN=" + URLEncoder.encode(txt_VIN_complete.getText().toString()) + "&rnd=" + String.valueOf(new Random().nextInt());
            //     Toast.makeText(this, last_query, Toast.LENGTH_SHORT).show();
            mm = new MyAsyncTask();
            mm.url = (last_query);
            mm.execute("");
            is_requested = true;
            fun.enableDisableView(lay_main, false);
            ConstraintLayout lay_complete_info = findViewById(R.id.lay_complete_info);
            fun.enableDisableView(lay_complete_info, false);
            RelativeLayout lay_message = findViewById(R.id.lay_message);
            lay_message.setVisibility(View.VISIBLE);
            LinearLayout lay_wait = findViewById(R.id.lay_wait);
            lay_wait.setVisibility(View.VISIBLE);
            set_size(R.id.lay_wait, .6, .3, "rel");
            set_size_txt(R.id.lbl_please_wait, .05, "line");
        } else {
            Toast.makeText(this, "لطفا تمامی اطلاعات را کامل وارد کنید", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (!allowBack) {


        } else {
            super.onBackPressed();
        }
    }

    public void clk_pay(View view) {
        if (avarez_Type == at_car) {
            if (rslt_CanEPay.equals("1")) {
                fun.enableDisableView(lay_main, false);
                RelativeLayout lay_message = findViewById(R.id.lay_message);
                lay_message.setVisibility(View.VISIBLE);
                ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
                lay_gate.setVisibility(View.VISIBLE);
                WebView webview = (WebView) findViewById(R.id.web_view);
                webview.setWebViewClient(new CarSearch.myWebClient());
                webview.getSettings().setJavaScriptEnabled(true);
//                rslt_price="1000";
                String url ="http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount=" + rslt_price + "&AdditionalInfo=" + rslt_MainProfile + "-CTSCar&MerchantID=" + rslt_MerchantId + "&TerminalId=" + rslt_TerMinalId + "&TransactionKey=" + rslt_TransactionKey + "&OrderId=" + rslt_OrderId;
                Lag(url);
//                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(i);
                webview.loadUrl(url);
//            EditText txt=findViewById(R.id.txt_motor);
//            txt.setText("http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount=" + rslt_price + "&AdditionalInfo=" + rslt_MainProfile + "-CTSCar&MerchantID=" + rslt_MerchantId + "&TerminalId=" + rslt_TerMinalId + "&TransactionKey=" + rslt_TransactionKey + "&OrderId=" + rslt_OrderId);
                //http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount=187000&AdditionalInfo=10000089-CTSCar&MerchantID=118088384&TerminalId=17995091&TransactionKey=AZ24JJ95SS&OrderId=10000089235123552
            } else {
                Toast.makeText(this, "متاسفانه شهرداری شهر شما فاقد امکان پرداخت الکترونیک می باشد", Toast.LENGTH_SHORT).show();
            }
        }
        else if (avarez_Type == at_nosazi) {
            if(!isPaid) {
//                fun.enableDisableView(lay_main, false);
//                RelativeLayout lay_message = findViewById(R.id.lay_message);
//                lay_message.setVisibility(View.VISIBLE);
//                ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
//                lay_gate.setVisibility(View.VISIBLE);
//                WebView webview = (WebView) findViewById(R.id.web_view);
//                webview.setWebViewClient(new myWebClient());
//                webview.getSettings().setJavaScriptEnabled(true);
                //rslt_price="1000";
//                allowBack = true;
                String
                        price = price_avarez;
                String ShG=NosaziBillID;
                String ShP=NosaziPaymentID;
                String Web="nosazi";
                pay_type="nosazi";

                UUID AID = UUID.randomUUID();
                PublicAID = AID;

                String AtuhIn =ShG+ShP+Web+AID.toString();

                String Atuh=SetAtuh(AtuhIn);
                String
                        URL="";
                URL = "http://testkasbapp.urmia.ir/Payment2.aspx?ShG="+NosaziBillID+"&ShP="+NosaziPaymentID+"&web=nosazi&AID="+AID.toString()+"&Auth="+Atuh;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                startActivity(i);

                Lag("url="+ URL);
//                webview.loadUrl(URL);
            }
        }
        else
        {
            if(!isPaid) {
                fun.enableDisableView(lay_main, false);
                RelativeLayout lay_message = findViewById(R.id.lay_message);
                lay_message.setVisibility(View.VISIBLE);
                ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
                lay_gate.setVisibility(View.VISIBLE);
                WebView webview = (WebView) findViewById(R.id.web_view);
                webview.setWebViewClient(new myWebClient());
                webview.getSettings().setJavaScriptEnabled(true);
                //rslt_price="1000";
                allowBack = true;
                String
                        price = price_avarez;


                webview.loadUrl("http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount=" + price + "&AdditionalInfo=10000089-CTSCar&MerchantID=118088384&TerminalId=17995091&TransactionKey=AZ24JJ95SS&OrderId=10000089235123552");
            }

        }


    }

    public void clk_detail(View view) {
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        if (avarez_Type == at_car) {
            ConstraintLayout lay_detail = findViewById(R.id.lay_detail);
            lay_detail.setVisibility(View.VISIBLE);
        } else if (avarez_Type != at_jame) {

            ConstraintLayout lay_detail_parvandeh = findViewById(R.id.lay_detail_parvandeh);
            lay_detail_parvandeh.setVisibility(View.VISIBLE);
            ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
            lay_gate.setVisibility(View.GONE);
        } else {
            ConstraintLayout lay_avarez_jame = findViewById(R.id.lay_avarez_jame);
            lay_avarez_jame.setVisibility(View.VISIBLE);
        }
    }

    public void clk_back_detail_parvandeh(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.GONE);
        ConstraintLayout lay_detail_parvandeh = findViewById(R.id.lay_detail_parvandeh);
        lay_detail_parvandeh.setVisibility(View.GONE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.GONE);
        Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
    }

    public void clk_back_detail_jame(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.GONE);
        ConstraintLayout lay_avarez_jame = findViewById(R.id.lay_avarez_jame);
        lay_avarez_jame.setVisibility(View.GONE);
        Toast.makeText(this, "321", Toast.LENGTH_SHORT).show();
    }

    public void clk_back_pay_message(View view) {

        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.GONE);
        LinearLayout lay_pay_message = findViewById(R.id.lay_pay_message);
        lay_pay_message.setVisibility(View.GONE);
        fun.enableDisableView(lay_main, true);
        search_nosazi();
    }

    public class myWebClient extends WebViewClient {
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
        public void onLoadResource(WebView view, String url) {


            TextView txt = findViewById(R.id.txt_url);
            txt.setText(view.getUrl());
        }
    }

    private void save_car(String motor) {
        last_query = getResources().getString(R.string.site_url) + "do.aspx?param=save_car&carMotor=" + motor + "&u_id=" + Functions.u_id + "&rnd=" + String.valueOf(new Random().nextInt());
        //     Toast.makeText(this, last_query, Toast.LENGTH_SHORT).show();
        mm = new MyAsyncTask();
        mm.url = (last_query);
        mm.execute("");
        is_requested = true;
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        LinearLayout lay_wait = findViewById(R.id.lay_wait);
        lay_wait.setVisibility(View.VISIBLE);
        set_size(R.id.lay_wait, .6, .3, "rel");
        set_size_txt(R.id.lbl_please_wait, .05, "line");
    }

    public  String SetAtuh(String Text) {
        Text=Text + Functions.key;
        Text=Text.toUpperCase();
        StringBuilder hexString = new StringBuilder();

        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-512");
            try {
                digest.update(Text.getBytes("UTF-8"));
            }
            catch (UnsupportedEncodingException e){
                e.printStackTrace();
            }

            byte messageDigest[] = digest.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() <2)
                    h="0" + h;
                hexString.append(h);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hexString.toString();


    }
    private class MyAsyncTaskService extends AsyncTask<String, Integer, Double> {
        String Param;
        String Type;
        private String resp="";

        public MyAsyncTaskService(String Type,String Param) {
            this.Param=Param;
            this.Type=Type;
            Lag("Creted");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Double aDouble) {

            Lag("Excuted");
            if(Type.equals("GetInfoNosazi")) {

              GetInfoNosaziResult();
            }
            if(Type.equals("GetNosaziPaidOrNot"))
            {
                Lag(resp);
                GetNosaziPaidOrNotResult();
            }
            if(Type.equals("CheckNosaziPaidOrNot"))
            {

                GetNosaziPaidOrNotResult();
            }
            if(Type.equals("CheckNosaziCodeAdded"))
            {
                Lag(resp);
                GetNosaziCodeAddedResult();
            }
        }
        private void GetNosaziCodeAddedResult() {

            if(resp.equals("false"))
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(CarSearch.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(CarSearch.this);
                }
                builder.setTitle("ذخیره خودرو؟")
                        .setMessage("آیا می خواهید این کد نوسازی به لیست اشتراک های من اضافه شود؟")
                        .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                // finish();
                                save_NosaziCode();

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

        private void GetNosaziPaidOrNotResult() {
            int
                    test_res = resp.indexOf(";");
            if(test_res>0)
            {
                String
                        rslt = resp.substring(test_res+1);
                TextView lbl_price = findViewById(R.id.lbl_pay);
                Lag("res"+rslt);
                if(rslt.length()<2)
                {
                    isPaid=true;
//                    lbl_price.setText(lbl_price.getText().toString()+"(پرداخت نشده)");
                }
                else
                {
                    isPaid=false;
                }
                if(pay_type.equals("nosazi")) {
                    String item1="";
                    String item2="";
                    String item3="";
                    String item4="";
                    int j=1;
                    for(int i=0;i<resp.length();i++)
                    {
                        if(resp.charAt(i) !=';')
                        {
                           if(j==1) item1= item1+ resp.charAt(i);
                           if(j==2) item2= item2+ resp.charAt(i);
                           if(j==3) item3= item3+ resp.charAt(i);
                           if(j==4) item4= item4+ resp.charAt(i);
                        }
                        else
                        {
                            j++;
                        }
                    }
//                    Toast.makeText(CarSearch.this, item1, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CarSearch.this, item2, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CarSearch.this, item3, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(CarSearch.this, item4, Toast.LENGTH_SHORT).show();
//                    item1 = "123";
//                    item4 = "1";
//                    item3="1";
                    if (item3.equals("1")) {
                        RelativeLayout lay_message = findViewById(R.id.lay_message);
                        lay_message.setVisibility(View.VISIBLE);
                        LinearLayout lay_pay_message = findViewById(R.id.lay_pay_message);
                        lay_pay_message.setVisibility(View.VISIBLE);
                        set_size(R.id.lay_pay_message, .8, .5, "rel");
                        lay_pay_message.setVisibility(View.VISIBLE);
                        fun.enableDisableView(lay_main, false);
                        TextView lbl_pay_avarez_type = findViewById(R.id.lbl_pay_avarez_type);
                        TextView lbl_pay_result = findViewById(R.id.lbl_pay_result);
                        TextView lbl_rahgiri = findViewById(R.id.lbl_rahgiri);
                        TextView lbl_result_in_source = findViewById(R.id.lbl_result_in_source);
                        SaveRahgiri(item1);
                        lbl_pay_avarez_type.setText("عوارض نوسازی");
                        lbl_pay_result.setText("پرداخت با موفقیت");
                        lbl_rahgiri.setText("کد رهگیری : "+item1);
                        lbl_result_in_source.setText("وضعیت تائید در مبداء : "+item4);



                    } else {
                        Toast.makeText(CarSearch.this, "پرداخت انجام نشد", Toast.LENGTH_SHORT).show();
                    }
                }
                pay_type="";
            }
        }

        private void GetInfoNosaziResult() {
            int
                    test_res = resp.indexOf("PaymentID");
            is_requested = false;
            fun.enableDisableView(lay_main, true);
            RelativeLayout lay_message = findViewById(R.id.lay_message);
            lay_message.setVisibility(View.GONE);
            LinearLayout lay_wait = findViewById(R.id.lay_wait);
            lay_wait.setVisibility(View.GONE);
            if(resp.equals("error"))
            {
                Toast.makeText(CarSearch.this, "خطایی رخ داده است لطفا کد نوسازی و ارتباط اینترنت را بررسی نمایید", Toast.LENGTH_SHORT).show();
            }
            if(test_res>0)
            {
                Check_NosaziCodeAdded();
                LinearLayout btn_pay = findViewById(R.id.btn_pay);
                TextView lbl_price = findViewById(R.id.lbl_price);
                TextView lbl_msg = findViewById(R.id.lbl_msg);
                btn_pay.setVisibility(View.VISIBLE);
                lbl_price.setVisibility(View.VISIBLE);
                LinearLayout btn_detail = findViewById(R.id.btn_detail);
//                btn_detail.setVisibility(View.VISIBLE);
                String
                        msg = "";



                int start1 = resp.indexOf("BillID=");
                int end1 = resp.indexOf("PaymentID");
                IsPaid ="0";
                if (end1 > 0) {


                    String
                            rslt = resp.substring(start1 + 7, end1);
                    NosaziBillID = rslt;
                    start1 = resp.indexOf("PaymentID=");
                    end1 = resp.indexOf(";");
                    String pri="";
                    if(rslt.length()>1)
                    {
                        rslt = resp.substring(start1 + 10, end1);
                        NosaziPaymentID = rslt;
                         pri = "مبلغ : "+NosaziPaymentID.substring(4,5)+","+NosaziPaymentID.substring(5,8)+",000 ریال"+"\n";
                        GetNosaziPaidOrNot();
                        btn_pay.setVisibility(View.VISIBLE);
                        lbl_price.setVisibility(View.VISIBLE);
                        IsPaid="1";

                    }
                    else
                    {
                        btn_pay.setVisibility(View.GONE);
//                        lbl_price.setVisibility(View.GONE);
                        pri="پرداخت شده";
                    }
                    start1 = resp.indexOf(";");
                    msg +=  resp.substring(start1+1);
                    lbl_price.setText(pri);
                    lbl_msg.setText(msg);

                }
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Double doInBackground(String... strings) {
            Lag(Type);
            if(Type.equals("GetInfoNosazi"))
            {
               GetInfoNosazi(Param);
            }
            if(Type.equals("GetNosaziPaidOrNot"))
            {
                GetPaidNosazi(Param);
            }
            if(Type.equals("CheckNosaziPaidOrNot"))
            {
                CheckPaidNosazi(Param);
            }
            if(Type.equals("CheckNosaziCodeAdded"))
            {
                GetNosaziCodeAdded();
            }
            if(Type.equals("SaveNosaziCode"))
            {
                SaveNosaziCode();
            }
            return null;
        }

        private void SaveNosaziCode() {
            



            CallSoap cs;

            try{
                cs = new CallSoap();
                resp=cs.Call_Nosazi_SaveNosziCode(NosaziCode);
                if(IsPaid.equals("1"))
                    myDB.execSQL("insert into MyNosazi (NosaziId,Type) values ('"+NosaziCode+"','Paid') ");
                Lag("Rs_save="+resp);
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());
            }
        }

        private void GetNosaziCodeAdded() {
            



            CallSoap cs;

            try{
                cs = new CallSoap();
                resp=cs.Call_Nosazi_GetAddedToList(NosaziCode);
                Lag("Rs_add="+resp);
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());
            }
        }

        private void CheckPaidNosazi(String param) {
            



            CallSoap cs;

            UUID AID = UUID.randomUUID();
            Lag(AID.toString());
            String AtuhIn =NosaziBillID+NosaziPaymentID+AID;
            Lag(AtuhIn);
            String Atuh=SetAtuh(AtuhIn);
//            String Atuh=SetAtuh(PnosaziKodem+AID);
            Lag(Atuh);

            try{
                cs = new CallSoap();
                resp=cs.Call_Nosazi_GetPaid(AID,Atuh);

                Lag("BAckFromPay="+resp);
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());
            }
        }
  private void GetPaidNosazi(String param) {




            CallSoap cs;

            UUID AID = UUID.randomUUID();
            Lag(AID.toString());
            String AtuhIn =NosaziBillID+NosaziPaymentID+AID;
            Lag(AtuhIn);
            String Atuh=SetAtuh(AtuhIn);
//            String Atuh=SetAtuh(PnosaziKodem+AID);
            Lag(Atuh);

            try{
                cs = new CallSoap();
                resp=cs.Call_Nosazi_GetPaid(NosaziBillID,NosaziPaymentID, AID,Atuh);

                Lag(resp);
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());
            }
        }

        private void GetInfoNosazi(String PnosaziKodem) {
            


            IsPaid="0";
            CallSoap cs;
            //String PnosaziKodem="1-11-40-7-0-0-0";
            String ShG="0126014311061";
            String ShP="0000213460632";
            String Web="nosazi";

//            UUID AID=UUID.fromString("359c7fcd-f403-4819-bb64-91e7deede50c");// UUID.randomUUID().toString();
            UUID AID = UUID.randomUUID();
//            UUID AID=UUID.fromString("867e3038-1db5-4a3f-b249-9ca659ddc356");// UUID.randomUUID().toString();
            Lag(AID.toString());
//            String AtuhIn =ShG+ShP+Web+AID;
            String AtuhIn =PnosaziKodem+AID;
            Lag(AtuhIn);
            String Atuh=SetAtuh(AtuhIn);
//            String Atuh=SetAtuh(PnosaziKodem+AID);
            Lag(Atuh);

            try{
                cs = new CallSoap();
                resp=cs.Call_Nosazi_GetInfo(PnosaziKodem, AID,Atuh);

                Lag("Res=1"+resp);
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());

            }
        }


    }

    private void SaveRahgiri(String rahgiri) {

            last_query = getResources().getString(R.string.site_url) + "do.aspx?param=save_pay_rahgiri_nosazi&NosaziCode="+NosaziCode+"&u_id="+Functions.u_id+"&Rahgiri="+rahgiri+"&price="+price_avarez+"&rnd=" + String.valueOf(new Random().nextInt());
            //     Toast.makeText(this, last_query, Toast.LENGTH_SHORT).show();
            mm = new MyAsyncTask();
            mm.url = (last_query);
            mm.execute("");
    }

    private void save_NosaziCode() {
        Asy =new MyAsyncTaskService("SaveNosaziCode","");
        Asy.execute();
    }

    private void Check_NosaziCodeAdded() {
        Asy =new MyAsyncTaskService("CheckNosaziCodeAdded","");
        Asy.execute();
    }

    private void GetNosaziPaidOrNot() {
        Asy =new MyAsyncTaskService("GetNosaziPaidOrNot","");
        Asy.execute();
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {


        public String ss = "", url = "";


        @Override
        protected Double doInBackground(String... params) {
            // TODO Auto-generated method stub

            //  dd=params[0];
            try {
                postData();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Double result) {

            int
                    start = ss.indexOf("<output>");
            int
                    end = ss.indexOf("</output>");
            String
                    output_str = "";
            String
                    param_str;
            param_str = "";

            int
                    start1 = ss.indexOf("<param>");
            int
                    end1 = ss.indexOf("</param>");
            if (end1 > 7) {

                param_str = ss.substring(start1 + 7, end1);
                // Toast.makeText(CarSearch.this, ss, Toast.LENGTH_SHORT).show();
                if (param_str.equals("save_car") && is_requested) {
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");

                    if (end1 > 0) {
                        String
                                rslt = ss.substring(start1 + 8, end1);
                        if (rslt.equals("ok")) {
                            Toast.makeText(CarSearch.this, "این خودرو به لیست اشتراک های شما اضافه شد", Toast.LENGTH_SHORT).show();
                            fun.enableDisableView(lay_main, true);
                            RelativeLayout lay_message = findViewById(R.id.lay_message);
                            lay_message.setVisibility(View.GONE);
                            LinearLayout lay_wait = findViewById(R.id.lay_wait);
                            lay_wait.setVisibility(View.GONE);
                            SharedPreferences prefs = getSharedPreferences(getString(R.string.MyBills), MODE_PRIVATE);
                            String eshterakCar = prefs.getString("CountCar", null);

                            if (eshterakCar != null) {
                                if(!eshterakCar.equals("0"))
                                {
                                    int iEsterakcar =Integer.valueOf(eshterakCar);
                                    iEsterakcar++;
                                    SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MyBills), MODE_PRIVATE).edit();
                                    editor.putString("CountCar", String.valueOf(iEsterakcar));
                                    editor.apply();
                                }
                            }
                        } else {
                            fun.enableDisableView(lay_main, true);
                            RelativeLayout lay_message = findViewById(R.id.lay_message);
                            lay_message.setVisibility(View.GONE);
                            LinearLayout lay_wait = findViewById(R.id.lay_wait);
                            lay_wait.setVisibility(View.GONE);
                        }
                    }
                }
                if (param_str.equals("save_pay_rahgiri_nosazi") ) {
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");

                    if (end1 > 0) {

                    }
                }
                if (param_str.equals("save_complete") && is_requested) {
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");

                    if (end1 > 0) {
                        String
                                rslt = ss.substring(start1 + 8, end1);
                        if (rslt.equals("ok")) {
                            Toast.makeText(CarSearch.this, "ذخیره با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
                            LinearLayout btn_pay = findViewById(R.id.btn_pay);
                            btn_pay.setVisibility(View.VISIBLE);
                            LinearLayout btn_detail = findViewById(R.id.btn_detail);
                            btn_detail.setVisibility(View.VISIBLE);

                            ConstraintLayout lay_complete = findViewById(R.id.lay_confirm);
                            lay_complete.setVisibility(View.GONE);
                        }
                    }
                    is_requested = false;
                    fun.enableDisableView(lay_main, true);
                    RelativeLayout lay_message = findViewById(R.id.lay_message);
                    ConstraintLayout lay_complete_info = findViewById(R.id.lay_complete_info);
                    fun.enableDisableView(lay_complete_info, true);
                    lay_complete_info.setVisibility(View.GONE);
                    lay_message.setVisibility(View.GONE);
                    LinearLayout lay_wait = findViewById(R.id.lay_wait);
                    lay_wait.setVisibility(View.GONE);
                }
                if (param_str.equals("get_avarez_motor") && is_requested) {
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");
                    TextView lbl_msg = findViewById(R.id.lbl_msg);
                    if (end1 > 0) {
                        String
                                rslt = ss.substring(start1 + 8, end1);
                        if (rslt.equals("NotFound")) {
                            lbl_msg.setText("اطلاعات خودرو شما پیدا نشد.");
                        } else {
                            start1 = rslt.indexOf("<price>");
                            end1 = rslt.indexOf("</price>");
                            rslt_price = rslt.substring(start1 + 7, end1);
                            avarez_price = -1;
                            try {
                                avarez_price = Integer.valueOf(rslt_price);
                            } catch (Exception e1) {
                            }
                            if (avarez_price >= 0) {
                                String
                                        new_str = "";
                                int j = 0;
                                for (int i = rslt_price.length() - 1; i >= 0; i--) {
                                    j++;
                                    if (j != rslt_price.length() && j % 3 == 0)
                                        new_str = "," + rslt_price.charAt(i) + new_str;
                                    else
                                        new_str = rslt_price.charAt(i) + new_str;
                                }


                                start1 = rslt.indexOf("<name>");
                                end1 = rslt.indexOf("</name>");
                                rslt_name = rslt.substring(start1 + 6, end1);

                                start1 = rslt.indexOf("<family>");
                                end1 = rslt.indexOf("</family>");
                                rslt_family = rslt.substring(start1 + 8, end1);
                                start1 = rslt.indexOf("<NationalNumber>");
                                end1 = rslt.indexOf("</NationalNumber>");
                                rslt_NationalNumber = rslt.substring(start1 + 16, end1);
                                start1 = rslt.indexOf("<ChassiSerial>");
                                end1 = rslt.indexOf("</ChassiSerial>");
                                rslt_ChassiSerial = rslt.substring(start1 + 14, end1);
                                start1 = rslt.indexOf("<VinNumber>");
                                end1 = rslt.indexOf("</VinNumber>");
                                rslt_VinNumber = rslt.substring(start1 + 11, end1);

                                start1 = rslt.indexOf("<motorSerial>");
                                end1 = rslt.indexOf("</motorSerial>");
                                motorSerial = rslt.substring(start1 + 13, end1);

                                start1 = rslt.indexOf("<CarName>");
                                end1 = rslt.indexOf("</CarName>");
                                String
                                        rslt_CarName = rslt.substring(start1 + 9, end1);
                                start1 = rslt.indexOf("<CouncilName>");
                                end1 = rslt.indexOf("</CouncilName>");
                                String
                                        rslt_CouncilName = rslt.substring(start1 + 13, end1);
                                start1 = rslt.indexOf("<MainProfile>");
                                end1 = rslt.indexOf("</MainProfile>");
                                rslt_MainProfile = rslt.substring(start1 + 13, end1);
                                start1 = rslt.indexOf("<CanEPay>");
                                end1 = rslt.indexOf("</CanEPay>");
                                rslt_CanEPay = rslt.substring(start1 + 9, end1);
                                if (rslt_CanEPay.equals("1")) {
                                    start1 = rslt.indexOf("<MerchantId>");
                                    end1 = rslt.indexOf("</MerchantId>");
                                    rslt_MerchantId = rslt.substring(start1 + 12, end1);
                                    start1 = rslt.indexOf("<TerMinalId>");
                                    end1 = rslt.indexOf("</TerMinalId>");
                                    rslt_TerMinalId = rslt.substring(start1 + 12, end1);
                                    start1 = rslt.indexOf("<TransactionKey>");
                                    end1 = rslt.indexOf("</TransactionKey>");
                                    rslt_TransactionKey = rslt.substring(start1 + 16, end1);
                                    start1 = rslt.indexOf("<OrderId>");
                                    end1 = rslt.indexOf("</OrderId>");
                                    rslt_OrderId = rslt.substring(start1 + 9, end1);

                                }


                                LinearLayout btn_pay = findViewById(R.id.btn_pay);
                                LinearLayout btn_detail = findViewById(R.id.btn_detail);
                                ConstraintLayout lay_confirm = findViewById(R.id.lay_confirm);
                                lay_confirm.setVisibility(View.VISIBLE);
                                if (avarez_price > 0) {
                                    //btn_pay.setVisibility(View.VISIBLE);
                                } else {
                                    btn_pay.setVisibility(View.GONE);
                                    btn_detail.setVisibility(View.GONE);
                                }
                                String
                                        msg = "";
                                if (rslt_CouncilName.length() > 0) {
                                    msg += "" + rslt_CouncilName + "\n";
                                }
                                if (rslt_name.length() > 2) {
                                    msg += "نام مالک : " + rslt_name + " " + rslt_family + "\n";
                                }
                                if (rslt_CarName.length() > 2) {
                                    msg += "نام خودرو : " + rslt_CarName + "\n";
                                }
                                msg += " مبلغ عوارض شما " + new_str + " ریال می باشد.";
                                lbl_msg.setText(msg);


                                start1 = rslt.indexOf("<hisCount>");
                                end1 = rslt.indexOf("</hisCount>");
                                int rslt_hisCount = Integer.valueOf(rslt.substring(start1 + 10, end1));
                                //Toast.makeText(CarSearch.this, String.valueOf(rslt_hisCount), Toast.LENGTH_SHORT).show();
                                item.clear();
                                if (rslt_hisCount > 0) {
                                    for (int i = 0; i < rslt_hisCount; i++) {
                                        start1 = rslt.indexOf("<his" + String.valueOf(i) + ">");
                                        end1 = rslt.indexOf("</his" + String.valueOf(i) + ">");
                                        String rslt_hisItems = (rslt.substring(start1 + 6, end1));
                                        start1 = rslt_hisItems.indexOf("<year>");
                                        end1 = rslt_hisItems.indexOf("</year>");
                                        String rslt_year = (rslt_hisItems.substring(start1 + 6, end1));
                                        start1 = rslt_hisItems.indexOf("<avarez>");
                                        end1 = rslt_hisItems.indexOf("</avarez>");
                                        String rslt_avarez = (rslt_hisItems.substring(start1 + 8, end1));
                                        new_str = "";
                                        j = 0;
                                        for (int ii = rslt_avarez.length() - 1; ii >= 0; ii--) {
                                            j++;
                                            if (j != rslt_avarez.length() && j % 3 == 0)
                                                new_str = "," + rslt_avarez.charAt(ii) + new_str;
                                            else
                                                new_str = rslt_avarez.charAt(ii) + new_str;
                                        }
                                        rslt_avarez = new_str;
                                        start1 = rslt_hisItems.indexOf("<farsudegi>");
                                        end1 = rslt_hisItems.indexOf("</farsudegi>");
                                        String rslt_farsudegi = (rslt_hisItems.substring(start1 + 11, end1));
                                        new_str = "";
                                        j = 0;
                                        for (int ii = rslt_farsudegi.length() - 1; ii >= 0; ii--) {
                                            j++;
                                            if (j != rslt_farsudegi.length() && j % 3 == 0)
                                                new_str = "," + rslt_farsudegi.charAt(ii) + new_str;
                                            else
                                                new_str = rslt_farsudegi.charAt(ii) + new_str;
                                        }
                                        rslt_farsudegi = new_str;
                                        start1 = rslt_hisItems.indexOf("<ratePunish>");
                                        end1 = rslt_hisItems.indexOf("</ratePunish>");
                                        String rslt_ratePunish = (rslt_hisItems.substring(start1 + 12, end1));
                                        start1 = rslt_hisItems.indexOf("<Punish>");
                                        end1 = rslt_hisItems.indexOf("</Punish>");
                                        String rslt_Punish = (rslt_hisItems.substring(start1 + 8, end1));
                                        new_str = "";
                                        j = 0;
                                        for (int ii = rslt_Punish.length() - 1; ii >= 0; ii--) {
                                            j++;
                                            if (j != rslt_Punish.length() && j % 3 == 0)
                                                new_str = "," + rslt_Punish.charAt(ii) + new_str;
                                            else
                                                new_str = rslt_Punish.charAt(ii) + new_str;
                                        }
                                        rslt_Punish = new_str;
                                        item.add(new items(rslt_year, rslt_avarez, rslt_farsudegi, rslt_ratePunish, rslt_Punish, "11"));
                                    }
                                }


                                mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
                                mRecyclerView.setHasFixedSize(true);
                                mLayoutManager = new LinearLayoutManager(CarSearch.this);
                                mRecyclerView.setLayoutManager(mLayoutManager);


                                mAdapter = new item_adapter(CarSearch.this, item);
                                mRecyclerView.setAdapter(mAdapter);

                                start1 = rslt.indexOf("<carSaved>");
                                end1 = rslt.indexOf("</carSaved>");
                                if (end1 > 0) {
                                    String carSaved = rslt.substring(start1 + 10, end1);
                                    if (carSaved.equals("not")) {
                                        AlertDialog.Builder builder;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            builder = new AlertDialog.Builder(CarSearch.this, android.R.style.Theme_Material_Dialog_Alert);
                                        } else {
                                            builder = new AlertDialog.Builder(CarSearch.this);
                                        }
                                        builder.setTitle("ذخیره خودرو؟")
                                                .setMessage("آیا می خواهید این خودرو به لیست خودرو های من اضافه شود؟")
                                                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        // continue with delete
                                                        // finish();
                                                        save_car(motorSerial);

                                                    }
                                                }).setNegativeButton("نه", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete

                                            }
                                        })
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .show();
                                    } else if (carSaved.equals("yes")) {

                                    }

                                }


                            } else {
//                                lbl_msg.setText("متاسفانه خطایی رخ داده است");
                            }
                        }


//                        LinearLayout lay_main =findViewById(R.id.lay_main);
//                        fun.enableDisableView(lay_main,true);
//                        LinearLayout lay_wait =findViewById(R.id.lay_wait);
//                        lay_wait.setVisibility(View.GONE);
                    }
                    is_requested = false;
                    fun.enableDisableView(lay_main, true);
                    RelativeLayout lay_message = findViewById(R.id.lay_message);
                    lay_message.setVisibility(View.GONE);
                    LinearLayout lay_wait = findViewById(R.id.lay_wait);
                    lay_wait.setVisibility(View.GONE);
                }

            }

        }


        protected void onProgressUpdate(Integer... progress) {
            //pb.setProgress(progress[0]);
        }

        public void postData() throws IOException {
            HttpClient httpclient = new DefaultHttpClient(); // Create HTTP Client
            HttpGet httpget = new HttpGet(url); // Set the action you want to do
            HttpResponse response = httpclient.execute(httpget); // Executeit
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent(); // Create an InputStream with the response
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf8"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) // Read line by line
                sb.append(line);

            String resString = sb.toString(); // Result is here
            ss = resString;
            //Lag("majid", resString);
            is.close();
        }
    }


    public class Timer extends Thread {

        int oneSecond = 1000;
        int value = 0;
        String TAG = "Timer";
        String typ = "";
        public long milles = 1000;


        //@Override
        public Timer(String type) {
            typ = type;
        }

        @Override
        public void run() {

            for (; ; ) {


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (typ.equals("timeout")) {
                            if (is_requested) {
                                tim++;
                                if (tim > Functions.Time_out_limit) {
                                    is_requested = false;
                                    fun.enableDisableView(lay_main, true);
                                    RelativeLayout lay_message = findViewById(R.id.lay_message);
                                    lay_message.setVisibility(View.GONE);
                                    LinearLayout lay_wait = findViewById(R.id.lay_wait);
                                    lay_wait.setVisibility(View.GONE);
                                    tim = 1;
                                    // Lag("majid",String.valueOf(tim));
//                                    Toast.makeText(CarSearch.this, "خطای شبکه رخ داد", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        if (typ.equals("break")) {

                        }
                    }
                });


                //   Lag("majid", String.valueOf(value));
                //Thread.currentThread();
                try {


                    Thread.sleep(milles);
                    //	Lag( " " + value);
                } catch (InterruptedException e) {
                    System.out.println("timer interrupted");
                    //value=0;
                    e.printStackTrace();
                }
            }
        }


    }

}
