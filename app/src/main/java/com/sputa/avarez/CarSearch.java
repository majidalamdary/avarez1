package com.sputa.avarez;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sputa.avarez.adapters.item_adapter;
import com.sputa.avarez.adapters.item_detail_parvandeh_adapter;
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
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.sputa.avarez.Functions.font_name_vazir;

public class CarSearch extends AppCompatActivity {
    private int screenWidth;
    private int screenHeight;
    private boolean is_requested;
    MyAsyncTask mm;
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
    private boolean allowBack=true;
    private int avarez_Type;
    private int at_car =1;
    private int at_bussiness =2;
    private int at_tablo =3;
    private int at_pasmand =4;
    private int at_jame =5;


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
        setContentView(R.layout.activity_car_search);
        lay_main= findViewById(R.id.lay_main);


        String str=getIntent().getStringExtra("typ");
      //  Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        if(str.equals("car"))
            avarez_Type = at_car;
        if(str.equals("bussiness"))
            avarez_Type = at_bussiness;
        if(str.equals("tablo"))
            avarez_Type = at_tablo;
        if(str.equals("pasmand"))
            avarez_Type = at_pasmand;
        if(str.equals("jame"))
            avarez_Type = at_jame;

        TextView lbl_title = findViewById(R.id.lbl_title);
        ImageView img_title = findViewById(R.id.img_title);
        String[] arraySpinner;
        arraySpinner = new String[] {
                "شماره موتور", "شماره شاسی", "شماره VIN"
        };
        if(avarez_Type==at_car)
        {
            arraySpinner = new String[] {
                    "شماره موتور", "شماره شاسی", "شماره VIN"
            };
            lbl_title.setText("جستجو و پرداخت عوارض خودرو");
             img_title.setBackgroundResource(R.drawable.car);
        }
        if(avarez_Type==at_bussiness)
        {
            arraySpinner = new String[] {
                    "شماره پرونده","کد ملی",
            };
            lbl_title.setText("جستجو و پرداخت عوارض کسب  و کار");
             img_title.setBackgroundResource(R.drawable.store);
        }
        if(avarez_Type==at_tablo)
        {
            arraySpinner = new String[] {
                    "شماره پرونده","کد ملی",
            };
            lbl_title.setText("جستجو و پرداخت عوارض تابلو");
             img_title.setBackgroundResource(R.drawable.panel);
        }
        if(avarez_Type==at_pasmand)
        {
            arraySpinner = new String[] {
                    "شماره پرونده","کد ملی",
            };
            lbl_title.setText("جستجو و پرداخت عوارض پسماند");
            img_title.setBackgroundResource(R.drawable.waste);
        }

        Spinner s = (Spinner) findViewById(R.id.spn_search_type);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        s.setAdapter(adapter);



        if(avarez_Type==at_car) {
            ConstraintLayout lay_motor_search = findViewById(R.id.lay_motor_search);
            lay_motor_search.setVisibility(View.VISIBLE);
            ConstraintLayout lay_parvandeh_search = findViewById(R.id.lay_parvandeh_search);
            lay_parvandeh_search.setVisibility(View.GONE);

        }
        else
        {
            ConstraintLayout lay_motor_search = findViewById(R.id.lay_motor_search);
            lay_motor_search.setVisibility(View.GONE);
            ConstraintLayout lay_parvandeh_search = findViewById(R.id.lay_parvandeh_search);
            lay_parvandeh_search.setVisibility(View.VISIBLE);

        }

        fun=new Functions();

        timer = new Timer("timeout");
        timer.start();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        set_size(R.id.txt_motor,.52,.07,"cons");

        set_size_txt(R.id.lbl_title,.058,"cons");

        set_size_txt(R.id.lbl_motor,.05,"cons");
        set_size_edit(R.id.txt_motor,.06,"cons");
        set_size_txt(R.id.lbl_search_type,.05,"cons");
        set_size_txt(R.id.lbl_msg,.039,"cons");
        set_size(R.id.lbl_msg,.9,.18,"cons");
        if(avarez_Type==at_bussiness)
            set_size(R.id.img_title,.14,.08,"cons");
        else
            set_size(R.id.img_title,.14,.09,"cons");
        Spinner spn= findViewById(R.id.spn_search_type);
        set_size(R.id.spn_search_type,.52,.07,"cons");

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

                if(avarez_Type==at_car) {
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
                }
                else
                {
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

        set_size(R.id.txt_shasi,.52,.07,"cons");
        set_size_txt(R.id.lbl_shasi,.04,"cons");
        set_size_txt(R.id.lbl_parvandeh,.04,"cons");
        set_size_txt(R.id.lbl_melli,.042,"cons");
        set_size_edit(R.id.txt_shasi,.04,"cons");
        set_size_edit(R.id.txt_motor,.04,"cons");
        set_size_edit(R.id.txt_parvndeh,.04,"cons");
        set_size_edit(R.id.txt_melli,.04,"cons");


        set_size(R.id.btn_search,.3,.065,"cons");
        set_size(R.id.btn_back,.3,.065,"cons");
        set_size_txt(R.id.lbl_search,.054,"line");
        set_size_txt(R.id.lbl_back,.054,"line");
        set_size(R.id.btn_pay,.3,.065,"cons");
        set_size(R.id.btn_detail,.3,.065,"cons");

        set_size_txt(R.id.lbl_help,.033,"cons");
        set_size(R.id.img_help_motor,.08,.06,"cons");
        set_size(R.id.img_help_shasi,.08,.06,"cons");
        set_size(R.id.img_help_shasi,.08,.06,"cons");



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
        set_size_edit(R.id.txt_VIN_complete,.042,"cons");
        set_size(R.id.txt_VIN_complete,.44,.07,"cons");
        set_size_txt(R.id.lbl_complete_title,.051,"cons");






    }

    public void clk_search(View view) {
        if(avarez_Type==at_car)
            search_car();
        if(avarez_Type==at_bussiness)
            search_bussiness();


    }

    private void search_bussiness() {
        EditText txt_parvndeh=findViewById(R.id.txt_parvndeh);
        EditText txt_melli=findViewById(R.id.txt_melli);

        String
                search_type="none";
        if(txt_parvndeh.getText().toString().length()>0)
        {
            search_type="ok";
        }
        else if(txt_melli.getText().toString().length()>0)
        {
            search_type="ok";
        }

        if(search_type.equals("ok"))
        {

            if(txt_parvndeh.getText().toString().length()>0)
            {
                if(txt_parvndeh.getText().toString().equals("123"))
                {
                    LinearLayout btn_pay = findViewById(R.id.btn_pay);
                    LinearLayout btn_detail = findViewById(R.id.btn_detail);
                    TextView lbl_msg = findViewById(R.id.lbl_msg);
                    btn_pay.setVisibility(View.VISIBLE);
                    btn_detail.setVisibility(View.VISIBLE);
                    String
                            msg="";
                        msg += " شهرداری ارومیه "  + "\n";
                        msg += "نام مالک : " + "علی" +" "+ "جاودانی" + "\n";
                        //msg += "نام خودرو : " + "" + "\n";
                    msg+= " مبلغ عوارض شما " + "450,000" + " ریال می باشد.";
                    lbl_msg.setText(msg);
                }
                else
                {
                    TextView lbl_msg = findViewById(R.id.lbl_msg);
                    String
                            msg="اطلاعات وارد شده صحیح نمی باشد";
                    lbl_msg.setText(msg);
                }
            }
        }
        else
        {
            Toast.makeText(this, "لطفا اطلاعات درخواستی را وارد کنید", Toast.LENGTH_SHORT).show();
        }
    }

    private void search_car() {
        EditText txt_motor=findViewById(R.id.txt_motor);
        EditText txt_shasi=findViewById(R.id.txt_shasi);
        EditText txt_vin=findViewById(R.id.txt_vin);
        String
                search_type="none";
        if(txt_motor.getText().toString().length()>0)
        {
            search_type="ok";
        }
        else if(txt_shasi.getText().toString().length()>0)
        {
            search_type="ok";
        }
        else if(txt_vin.getText().toString().length()>0)
        {
            search_type="ok";
        }
        if(search_type.equals("ok"))
        {
            last_query = getResources().getString(R.string.site_url) + "do.aspx?param=get_avarez_motor&key_motor="+txt_motor.getText().toString()+"&key_shasi="+txt_shasi.getText().toString()+ "&key_vin="+txt_vin.getText().toString()+ "&u_id="+Functions.u_id+"&rnd=" + String.valueOf(new Random().nextInt());
            //     Toast.makeText(this, last_query, Toast.LENGTH_SHORT).show();
            mm=new MyAsyncTask();
            mm.url = (last_query);
            mm.execute("");
            is_requested=true;
            fun.enableDisableView(lay_main,false);
            RelativeLayout lay_message = findViewById(R.id.lay_message);
            lay_message.setVisibility(View.VISIBLE);
            LinearLayout lay_wait = findViewById(R.id.lay_wait);
            lay_wait.setVisibility(View.VISIBLE);
            set_size(R.id.lay_wait,.6,.3,"rel");
            set_size_txt(R.id.lbl_please_wait,.05,"line");
            LinearLayout btn_pay= findViewById(R.id.btn_pay);
            btn_pay.setVisibility(View.GONE);
            LinearLayout btn_detail= findViewById(R.id.btn_detail);
            btn_detail.setVisibility(View.GONE);

            ConstraintLayout lay_confirm = findViewById(R.id.lay_confirm);
            lay_confirm.setVisibility(View.GONE);
        }
        else
        {
            Toast.makeText(this, "لطفا اطلاعات درخواستی را وارد کنید", Toast.LENGTH_SHORT).show();
        }
    }

    public void clk_back(View view) {
        finish();
    }

    public void clk_help_motor(View view) {
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        RelativeLayout lay_help_motor = findViewById(R.id.lay_help_motor);
        set_size(R.id.im_help_motor_pic,.8,.8,"rel");
        fun.enableDisableView(lay_main,false);
        lay_message.setVisibility(View.VISIBLE);
        lay_help_motor.setVisibility(View.VISIBLE);
    }
    public void clk_help_vin(View view) {
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        RelativeLayout lay_help_motor = findViewById(R.id.lay_help_vin);
        set_size(R.id.im_help_vin_pic,.8,.8,"rel");
        fun.enableDisableView(lay_main,false);
        lay_message.setVisibility(View.VISIBLE);
        lay_help_motor.setVisibility(View.VISIBLE);
    }

    public void clk_message(View view) {
        LinearLayout lay_wait = findViewById(R.id.lay_wait);
        ConstraintLayout lay_complete_info = findViewById(R.id.lay_complete_info);

        if(lay_wait.getVisibility()==(View.GONE) && lay_complete_info.getVisibility()==(View.GONE))  {
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

    public void clk_help_shasi(View view) {
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        RelativeLayout lay_help_motor = findViewById(R.id.lay_help_shasi);
        set_size(R.id.lbl_help_shasi,.6,.3,"rel");
        set_size_txt(R.id.lbl_help_shasi,.042,"rel");
        fun.enableDisableView(lay_main,false);
        lay_message.setVisibility(View.VISIBLE);
        lay_help_motor.setVisibility(View.VISIBLE);
    }

    public void clk_yes_correct(View view) {
        ConstraintLayout lay_confirm = findViewById(R.id.lay_confirm);
        LinearLayout lay_btn_pay = findViewById(R.id.btn_pay);
        LinearLayout lay_btn_detail = findViewById(R.id.btn_detail);
        lay_confirm.setVisibility(View.GONE);
        if(avarez_price>0) {
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
        EditText txt_name_complete=findViewById(R.id.txt_name_complete);
        EditText txt_family_complete=findViewById(R.id.txt_family_complete);
        EditText txt_melliID_complete=findViewById(R.id.txt_melliID_complete);
        EditText txt_shasi_complete=findViewById(R.id.txt_shasi_complete);
        EditText txt_VIN_complete=findViewById(R.id.txt_VIN_complete);
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
        EditText txt_name_complete=findViewById(R.id.txt_name_complete);
        EditText txt_family_complete=findViewById(R.id.txt_family_complete);
        EditText txt_melliID_complete=findViewById(R.id.txt_melliID_complete);
        EditText txt_shasi_complete=findViewById(R.id.txt_shasi_complete);
        EditText txt_VIN_complete=findViewById(R.id.txt_VIN_complete);

        String
                search_type="ok";
        if(txt_name_complete.getText().toString().length()==0 && search_type.equals("ok"))
        {
            search_type="none";
        }
        if(txt_family_complete.getText().toString().length()==0 && search_type.equals("ok"))
        {
            search_type="none";
        }
        if(txt_melliID_complete.getText().toString().length()==0 && search_type.equals("ok"))
        {
            search_type="none";
        }
        if(txt_shasi_complete.getText().toString().length()==0 && search_type.equals("ok"))
        {
            search_type="none";
        }
        if(txt_VIN_complete.getText().toString().length()==0 && search_type.equals("ok"))
        {
            search_type="none";
        }

        if(search_type.equals("ok"))
        {
            last_query = getResources().getString(R.string.site_url) + "do.aspx?param=save_complete&motorSerial="+motorSerial+"&name="+ URLEncoder.encode(txt_name_complete.getText().toString())+"&family="+URLEncoder.encode(txt_family_complete.getText().toString())+ "&melliID="+URLEncoder.encode(txt_melliID_complete.getText().toString())+ "&shasi="+URLEncoder.encode(txt_shasi_complete.getText().toString())+ "&VIN="+URLEncoder.encode(txt_VIN_complete.getText().toString())+ "&rnd=" + String.valueOf(new Random().nextInt());
            //     Toast.makeText(this, last_query, Toast.LENGTH_SHORT).show();
            mm=new MyAsyncTask();
            mm.url = (last_query);
            mm.execute("");
            is_requested=true;
            fun.enableDisableView(lay_main,false);
            ConstraintLayout lay_complete_info = findViewById(R.id.lay_complete_info);
            fun.enableDisableView(lay_complete_info,false);
            RelativeLayout lay_message = findViewById(R.id.lay_message);
            lay_message.setVisibility(View.VISIBLE);
            LinearLayout lay_wait = findViewById(R.id.lay_wait);
            lay_wait.setVisibility(View.VISIBLE);
            set_size(R.id.lay_wait,.6,.3,"rel");
            set_size_txt(R.id.lbl_please_wait,.05,"line");
        }
        else
        {
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
        if (rslt_CanEPay.equals("1")) {
            fun.enableDisableView(lay_main, false);
            RelativeLayout lay_message = findViewById(R.id.lay_message);
            lay_message.setVisibility(View.VISIBLE);
            ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
            lay_gate.setVisibility(View.VISIBLE);
            WebView webview = (WebView) findViewById(R.id.web_view);
            webview.setWebViewClient(new CarSearch.myWebClient());
            webview.getSettings().setJavaScriptEnabled(true);
            //rslt_price="1000";
            webview.loadUrl("http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount=" + rslt_price + "&AdditionalInfo=" + rslt_MainProfile + "-CTSCar&MerchantID=" + rslt_MerchantId + "&TerminalId=" + rslt_TerMinalId + "&TransactionKey=" + rslt_TransactionKey + "&OrderId=" + rslt_OrderId);
//            EditText txt=findViewById(R.id.txt_motor);
//            txt.setText("http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount=" + rslt_price + "&AdditionalInfo=" + rslt_MainProfile + "-CTSCar&MerchantID=" + rslt_MerchantId + "&TerminalId=" + rslt_TerMinalId + "&TransactionKey=" + rslt_TransactionKey + "&OrderId=" + rslt_OrderId);
            //http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount=187000&AdditionalInfo=10000089-CTSCar&MerchantID=118088384&TerminalId=17995091&TransactionKey=AZ24JJ95SS&OrderId=10000089235123552
        }
        else
        {
            Toast.makeText(this, "متاسفانه شهرداری شهر شما فاقد امکان پرداخت الکترونیک می باشد", Toast.LENGTH_SHORT).show();
        }




    }

    public void clk_detail(View view) {
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        if(avarez_Type == at_car) {
            ConstraintLayout lay_detail = findViewById(R.id.lay_detail);
            lay_detail.setVisibility(View.VISIBLE);
        }
        else
        {
            item.add(new items("1397","450,000","","0","0","1"));
            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_parvandeh);
                                            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(CarSearch.this);
                                            mRecyclerView.setLayoutManager(mLayoutManager);


            mAdapter = new item_detail_parvandeh_adapter(CarSearch.this,item);
                                    mRecyclerView.setAdapter(mAdapter);

            ConstraintLayout lay_detail_parvandeh = findViewById(R.id.lay_detail_parvandeh);
            lay_detail_parvandeh.setVisibility(View.VISIBLE);
        }
    }

    public void clk_back_detail_parvandeh(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.GONE);
        ConstraintLayout lay_detail_parvandeh = findViewById(R.id.lay_detail_parvandeh);
        lay_detail_parvandeh.setVisibility(View.GONE);

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
    private void save_car(String motor) {
        last_query = getResources().getString(R.string.site_url) + "do.aspx?param=save_car&carMotor="+motor+"&u_id="+Functions.u_id+"&rnd=" + String.valueOf(new Random().nextInt());
        //     Toast.makeText(this, last_query, Toast.LENGTH_SHORT).show();
        mm=new MyAsyncTask();
        mm.url = (last_query);
        mm.execute("");
        is_requested=true;
        fun.enableDisableView(lay_main,false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        LinearLayout lay_wait = findViewById(R.id.lay_wait);
        lay_wait.setVisibility(View.VISIBLE);
        set_size(R.id.lay_wait,.6,.3,"rel");
        set_size_txt(R.id.lbl_please_wait,.05,"line");
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
                    start=ss.indexOf("<output>");
            int
                    end=ss.indexOf("</output>");
            String
                    output_str="";
            String
                    param_str;
            param_str = "";

            int
                        start1 = ss.indexOf("<param>");
                int
                        end1 = ss.indexOf("</param>");
                if(end1>7) {

                    param_str = ss.substring(start1 + 7, end1);
                    // Toast.makeText(CarSearch.this, ss, Toast.LENGTH_SHORT).show();
                    if (param_str.equals("save_car") && is_requested) {
                        start1 = ss.indexOf("<result>");
                        end1 = ss.indexOf("</result>");

                        if (end1 > 0) {
                            String
                                    rslt = ss.substring(start1 + 8, end1);
                            if(rslt.equals("ok"))
                            {
                                Toast.makeText(CarSearch.this, "این خودرو به لیست خودرو های شما اضافه شد", Toast.LENGTH_SHORT).show();
                                fun.enableDisableView(lay_main,true);
                                RelativeLayout lay_message = findViewById(R.id.lay_message);
                                lay_message.setVisibility(View.GONE);
                                LinearLayout lay_wait = findViewById(R.id.lay_wait);
                                lay_wait.setVisibility(View.GONE);
                            }
                            else
                            {
                                fun.enableDisableView(lay_main,true);
                                RelativeLayout lay_message = findViewById(R.id.lay_message);
                                lay_message.setVisibility(View.GONE);
                                LinearLayout lay_wait = findViewById(R.id.lay_wait);
                                lay_wait.setVisibility(View.GONE);
                            }
                        }
                    }
                    if (param_str.equals("save_complete") && is_requested) {
                        start1 = ss.indexOf("<result>");
                        end1 = ss.indexOf("</result>");
                        
                        if (end1 > 0) {
                            String
                                    rslt = ss.substring(start1 + 8, end1);
                            if(rslt.equals("ok"))
                            {
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
                        fun.enableDisableView(lay_complete_info,true);
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
                                    if(rslt_CanEPay.equals("1"))
                                    {
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
                                    if (avarez_price > 0)
                                    {
                                        //btn_pay.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        btn_pay.setVisibility(View.GONE);
                                        btn_detail.setVisibility(View.GONE);
                                    }
                                    String
                                            msg="";
                                    if(rslt_CouncilName.length()>0)
                                    {
                                        msg += "" + rslt_CouncilName + "\n";
                                    }
                                    if(rslt_name.length()>2)
                                    {
                                        msg += "نام مالک : " + rslt_name+" "+rslt_family + "\n";
                                    }
                                    if(rslt_CarName.length()>2)
                                    {
                                        msg += "نام خودرو : " + rslt_CarName + "\n";
                                    }
                                    msg+= " مبلغ عوارض شما " + new_str + " ریال می باشد.";
                                    lbl_msg.setText(msg);


                                    start1 = rslt.indexOf("<hisCount>");
                                    end1 = rslt.indexOf("</hisCount>");
                                    int rslt_hisCount = Integer.valueOf(rslt.substring(start1 + 10, end1));
                                    //Toast.makeText(CarSearch.this, String.valueOf(rslt_hisCount), Toast.LENGTH_SHORT).show();
                                    item.clear();
                                    if(rslt_hisCount>0)
                                    {
                                        for(int i=0;i<rslt_hisCount;i++)
                                        {
                                            start1 = rslt.indexOf("<his"+String.valueOf(i)+">");
                                            end1 = rslt.indexOf("</his"+String.valueOf(i)+">");
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
                                            item.add(new items(rslt_year,rslt_avarez,rslt_farsudegi,rslt_ratePunish,rslt_Punish,"11"));
                                        }
                                    }




                                    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
                                    mRecyclerView.setHasFixedSize(true);
                                    mLayoutManager = new LinearLayoutManager(CarSearch.this);
                                    mRecyclerView.setLayoutManager(mLayoutManager);


                                    mAdapter = new item_adapter(CarSearch.this,item);
                                    mRecyclerView.setAdapter(mAdapter);

                                    start1 = rslt.indexOf("<carSaved>");
                                    end1 = rslt.indexOf("</carSaved>");
                                    if(end1>0) {
                                        String carSaved = rslt.substring(start1 + 10, end1);
                                        if(carSaved.equals("not"))
                                        {
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
                                        }
                                        else if(carSaved.equals("yes"))
                                        {

                                        }

                                    }


                                } else {
                                    lbl_msg.setText("متاسفانه خطایی رخ داده است");
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
            //Log.d("majid", resString);
            is.close();
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
                                    Toast.makeText(CarSearch.this, "خطای شبکه رخ داد", Toast.LENGTH_SHORT).show();
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
