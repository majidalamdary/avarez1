package com.sputa.avarez;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sputa.avarez.DrawerTest;
import com.sputa.avarez.Functions;
import com.sputa.avarez.R;
import com.sputa.avarez.my_views.MyTextView;


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
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import static com.sputa.avarez.Functions.Lag;

public class RegisterActivity extends AppCompatActivity {

    private boolean is_requested;
    private MyAsyncTask mm;
    String last_requested_query="";
    private String tel_number="09141484633";
    private String action="";
    private LinearLayout lay_wait;
    private Functions fun;
    private LinearLayout lay_payamak;
    private LinearLayout lay_register;
    private int tim=0;
    private Timer timer;
    private Timer tim_remain;
    private boolean is_started;
    private int remaining_time;
    private String cns_enter_code = " کدی که از طریق پیامک دریافت خواهید کرد را در کادر بالا وارد کنید. ";
    private int screenWidth;
    private int screenHeight;
    private String mother_activity="";
    private SQLiteDatabase myDB;

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
        setContentView(R.layout.activity_register);
        myDB = this.openOrCreateDatabase(getString(R.string.DB_name), MODE_PRIVATE, null);

        fun = new Functions();
        Intent inten=getIntent();
        action = inten.getStringExtra("action");
        lay_wait = findViewById(R.id.lay_wait);

        lay_payamak = findViewById(R.id.lay_payamak);
        lay_register = findViewById(R.id.lay_register);
        if(Functions.u_id.equals("0"))
        {
            lay_payamak.setVisibility(View.VISIBLE);
            lay_register.setVisibility(View.GONE);
        }
        else
        {
            lay_payamak.setVisibility(View.GONE);
            lay_register.setVisibility(View.VISIBLE);
            is_requested = true;
            mm = new MyAsyncTask();
            last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_user&ID="+Functions.u_id+ "&rdn="+String.valueOf(new Random().nextInt());
             //Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();
            lay_wait.setVisibility(View.VISIBLE);
            fun.enableDisableView(lay_register,false);
            mm.url = (last_requested_query);
            mm.execute("");

        }
        Intent intent = getIntent();
        mother_activity=intent.getStringExtra("type");
        //Toast.makeText(this, mother_activity, Toast.LENGTH_SHORT).show();
        timer = new Timer("timeout");
        timer.start();
        tim_remain = new Timer("remain");
        tim_remain.start();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        set_size_txt(R.id.txt_title_tell,0.07,"line");
        set_size_txt(R.id.txt_tel,0.06,"line");
        set_size(R.id.txt_tel,.7,.066,"line");

        set_size_txt(R.id.lbl_tel,0.05,"line");
        set_size_txt(R.id.lbl_message,0.047,"line");



        set_size_txt(R.id.txt_title,0.07,"line");

        set_size_txt(R.id.lbl_name,0.039,"line");
        set_size_txt(R.id.lbl_family,0.039,"line");
        set_size_txt(R.id.lbl_email,0.039,"line");


//        set_size_txt(R.id.button22,0.05,"line");
//        set_size(R.id.button22,.35,.25,"line");


    }
    public boolean validateMelliCode(String melliCode) {

        String[] identicalDigits = {"0000000000", "1111111111", "2222222222", "3333333333", "4444444444", "5555555555", "6666666666", "7777777777", "8888888888", "9999999999"};

        if (melliCode.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Melli Code is empty", Toast.LENGTH_LONG).show();
            return false; // Melli Code is empty
        } else if (melliCode.length() != 10) {
            Toast.makeText(getApplicationContext(), "Melli Code must be exactly 10 digits", Toast.LENGTH_LONG).show();
            return false; // Melli Code is less or more than 10 digits
        } else if (Arrays.asList(identicalDigits).contains(melliCode)) {
            Toast.makeText(getApplicationContext(), "MelliCode is not valid (Fake MelliCode)", Toast.LENGTH_LONG).show();
            return false; // Fake Melli Code
        } else {
            int sum = 0;

            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(melliCode.charAt(i)) * (10 - i);
            }
//            Lag("sum="+String.valueOf(sum));

            int lastDigit;
            int divideRemaining = sum % 11;
//            Lag("divideRemaining="+String.valueOf(divideRemaining));
            if (divideRemaining < 2) {
                lastDigit = divideRemaining;
            } else {
                lastDigit = 11 - (divideRemaining);
            }
//            Lag("lastDigit="+String.valueOf(lastDigit));
//            Lag("melliCode.charAt(9)="+String.valueOf(melliCode.charAt(9)));
            if (Character.getNumericValue(melliCode.charAt(9)) == lastDigit) {
//                Toast.makeText(getApplicationContext(), "MelliCode is valid", Toast.LENGTH_LONG).show();
                return true;
            } else {
//                Toast.makeText(getApplicationContext(), "MelliCode is not valid", Toast.LENGTH_LONG).show();
                return false; // Invalid MelliCode
            }
        }
    }
    public void clk_register(View view) {

        boolean
                flag = true;
        TextView txt_name = findViewById(R.id.txt_name);
        TextView txt_family = findViewById(R.id.txt_family);
        TextView txt_email = findViewById(R.id.txt_email);
        TextView txt_national_id = findViewById(R.id.txt_national_id);
        TextView txt_posti_id = findViewById(R.id.txt_posti_id);
        TextView lbl_error_message = findViewById(R.id.lbl_error_message);

        String
                msg="";
        if(txt_name.getText().toString().length()<3)
        {
            msg += ("*طول نام کوتاه است"+"\n");
            flag=false;
        }
        if(txt_family.getText().toString().length()<3)
        {
            msg += ("*طول نام خانوادگی کوتاه است"+"\n");
            flag=false;
        }
        if(txt_national_id.getText().toString().trim().length()<10)
        {
            msg += ("*طول کد ملی کوتاه است"+"\n");
            flag=false;

        }
        if(flag)
        {
            if(!validateMelliCode(txt_national_id.getText().toString())) {
                msg += ("*کدملی صحیح نمی باشد" + "\n");
                flag = false;
            }
        }



        if(flag) {
            if(!is_requested) {
                //  Toast.makeText(this, "majid", Toast.LENGTH_SHORT).show();
                is_requested = true;
                mm = new MyAsyncTask();
                last_requested_query = getResources().getString(R.string.site_url) + "do?param=save_user&name=" + URLEncoder.encode(txt_name.getText().toString()) + "&family=" + URLEncoder.encode(txt_family.getText().toString()) + "&email=" + URLEncoder.encode(txt_email.getText().toString()) + "&NationalID=" + URLEncoder.encode(txt_national_id.getText().toString()) + "&PostalID=" + URLEncoder.encode(txt_posti_id.getText().toString()) + "&ID=" + Functions.u_id+"&rdn="+String.valueOf(new Random().nextInt());
                Log.d("tg1",last_requested_query);
                // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();
                mm.url = (last_requested_query);
                mm.execute("");
            }
        }
        else
        {
            lbl_error_message.setText(msg);
        }
    }

    String
            typ_tel_conftim ="tel";
    String
            random_code="654654564564654";
    public void clk_send_number(View view) {

//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(this);
        if(typ_tel_conftim.equals("code")) {
            TextView txt_tel = findViewById(R.id.txt_tel);
            if(txt_tel.getText().toString().equals(random_code))
            {
                try {
                    myDB.execSQL("delete from MyNosazi ");
                }catch (Exception e1){}
                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MyBills), MODE_PRIVATE).edit();
                editor.putString("CountCar", "0");
                editor.apply();
                if(!is_requested) {
                    //  Toast.makeText(this, "majid", Toast.LENGTH_SHORT).show();
                    is_requested = true;
                    mm = new MyAsyncTask();
                    last_requested_query = getResources().getString(R.string.site_url) + "do?param=create_user&code="+txt_tel.getText().toString()+ "&tel=" + URLEncoder.encode(tel_number)+"&rdn="+String.valueOf(new Random().nextInt());
                    Log.d("tg1",last_requested_query);
                    // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();
                    lay_wait.setVisibility(View.VISIBLE);
                    fun.enableDisableView(lay_payamak,false);
                    mm.url = (last_requested_query);
                    mm.execute("");
                }
            }
            else
            {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(this);
                }
                builder.setTitle("خطا")
                        .setMessage("کد وارد شده صحیح نمی باشد")
                        .setPositiveButton("فهمیدم", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
        if(typ_tel_conftim.equals("tel")) {
            TextView txt_tel = findViewById(R.id.txt_tel);
            if (txt_tel.getText().toString().length() > 2)
                if (txt_tel.getText().toString().substring(0, 2).equals("09") && txt_tel.getText().toString().length() == 11) {

                    tel_number = txt_tel.getText().toString();
                    is_requested = true;
                    mm = new MyAsyncTask();
                    last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_random_code&phone_number="+tel_number+ "&rnd=" + String.valueOf(new Random().nextInt());
                    // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();
                    mm.url = (last_requested_query);
                    mm.execute("");
                    fun.enableDisableView(lay_payamak,false);
                    tim=0;
                    lay_wait.setVisibility(View.VISIBLE);

                } else {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(this);
                    }
                    builder.setTitle("خطا")
                            .setMessage("فرمت شماره همراه صحیح نمی باشد")
                            .setPositiveButton("فهمیدم", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
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


                        if(typ.equals("remain")) {
                            if(is_started)
                            {
                                if(remaining_time>0)
                                     remaining_time--;
                                else
                                {
                                    TextView lbl_tel = findViewById(R.id.lbl_tel);
                                    TextView lbl_message = findViewById(R.id.lbl_message);

                                    lbl_message.setText(" ");

                                    lbl_tel.setText("شماره همراه :");
                                    TextView txt_tel = findViewById(R.id.txt_tel);
                                    txt_tel.setHint("09*********");
                                    txt_tel.setText("");
                                    typ_tel_conftim ="tel";
                                }
                                int min = (int)(remaining_time/60);
                                int sec = (remaining_time%60);
                                String s_min = "0"+String.valueOf(min);
                                String s_sec = String.valueOf(sec);
                                if(sec<10)
                                    s_sec = "0"+s_sec;

                                TextView lbl_message = findViewById(R.id.lbl_message);
                                lbl_message.setText(cns_enter_code +" "+ s_min+":"+s_sec);
                            }
                        }

                        if(typ.equals("timeout")) {
                            if(is_requested)
                            {
                                tim++;
                                if(tim>Functions.Time_out_limit)
                                {
                                    fun.enableDisableView(lay_payamak,true);
                                    fun.enableDisableView(lay_register,true);
                                    is_requested=false;
                                    lay_wait.setVisibility(View.GONE);
                                    //Toast.makeText(RegisterActivity.this, "خطا در ارتباط", Toast.LENGTH_SHORT).show();
                                    tim=0;
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
                    param_str = "";



                int
                        start1 = ss.indexOf("<param>");
                int
                        end1 = ss.indexOf("</param>");
                Log.d("tg1",ss);
                if(end1>0) {
                    param_str = ss.substring(start1 + 7, end1);
                    //Toast.makeText(RegisterActivity.this, param_str, Toast.LENGTH_SHORT).show();
                    if (param_str.equals("get_random_code") && is_requested) {

                        start1 = ss.indexOf("<result>");
                        end1 = ss.indexOf("</result>");
                        is_requested = false;
                        if (end1 > 0) {
                            String rslt = ss.substring(start1 + 8, end1);

                            start1 = ss.indexOf("<msg>");
                            end1 = ss.indexOf("</msg>");
                            String msg = ss.substring(start1 + 5, end1);
                            if (msg.equals("ok")) {
                                start1 = ss.indexOf("<pass>");
                                end1 = ss.indexOf("</pass>");
                                String pass = ss.substring(start1 + 6, end1);
                                random_code = pass;
                                lay_wait.setVisibility(View.GONE);
                                TextView lbl_tel = findViewById(R.id.lbl_tel);
                                TextView lbl_message = findViewById(R.id.lbl_message);

                                lbl_message.setText(cns_enter_code);
                                fun.enableDisableView(lay_payamak, true);
                                lbl_tel.setText("کد تائید :");
                                TextView txt_tel = findViewById(R.id.txt_tel);
                                txt_tel.setHint("");
                                txt_tel.setText("");
                                typ_tel_conftim = "code";
                                remaining_time = 121;
                                is_started = true;
                            } else if (msg.equals("exceed")) {
                                Toast.makeText(RegisterActivity.this, "لطفا 24 ساعت بعد امتحان کنید", Toast.LENGTH_SHORT).show();
                                lay_wait.setVisibility(View.GONE);
                                fun.enableDisableView(lay_payamak, true);
                            } else if (msg.equals("under2")) {
                                start1 = ss.indexOf("<diff>");
                                end1 = ss.indexOf("</diff>");
                                String s_remain = ss.substring(start1 + 6, end1);
                                int remain = (int) ((2 - Double.valueOf(s_remain)) * 60);
                                start1 = ss.indexOf("<pass>");
                                end1 = ss.indexOf("</pass>");
                                String pass = ss.substring(start1 + 6, end1);
                                random_code = pass;
                                lay_wait.setVisibility(View.GONE);
                                fun.enableDisableView(lay_payamak, true);
                                TextView lbl_tel = findViewById(R.id.lbl_tel);
                                TextView lbl_message = findViewById(R.id.lbl_message);

                                lbl_message.setText(cns_enter_code);

                                lbl_tel.setText("کد تائید :");
                                TextView txt_tel = findViewById(R.id.txt_tel);
                                txt_tel.setHint("");
                                txt_tel.setText("");
                                typ_tel_conftim = "code";
                                remaining_time = remain;
                                is_started = true;
                            }
                        }
                    }
                    if (param_str.equals("save_user") && is_requested) {
                        //  Toast.makeText(getBaseContext(),ss,Toast.LENGTH_LONG).show();
                        //  EditText txt_email = findViewById(R.id.txt_email);
                        //   txt_email.setText(ss);
                        start1 = ss.indexOf("<result>");
                        end1 = ss.indexOf("</result>");
                        is_requested = false;
                        String rslt = ss.substring(start1 + 8, end1);
                        if (!rslt.equals("0")) {


                            Toast.makeText(RegisterActivity.this, "کاربر ذخیره شد ", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(register_user.this,NewItem.class));

                            finish();
                            if(mother_activity ==null) {

                                startActivity(new Intent(RegisterActivity.this, DrawerTest.class));

                            }

                        }

                    }
                    if (param_str.equals("get_user") && is_requested) {
                        //Toast.makeText(RegisterActivity.this, "123", Toast.LENGTH_SHORT).show();
                        start = ss.indexOf("<result>");
                        end = ss.indexOf("</result>");
                        is_requested = false;

                        if (end > 0) {
                            start1 = ss.indexOf("<name>");
                            end1 = ss.indexOf("</name>");
                            String name = ss.substring(start1 + 6, end1);
                            start1 = ss.indexOf("<family>");
                            end1 = ss.indexOf("</family>");
                            String family = ss.substring(start1 + 8, end1);
                            start1 = ss.indexOf("<email>");
                            end1 = ss.indexOf("</email>");
                            String email = ss.substring(start1 + 7, end1);
                            start1 = ss.indexOf("<NationalID>");
                            end1 = ss.indexOf("</NationalID>");
                            String NationalID = ss.substring(start1 + 12, end1);
                            start1 = ss.indexOf("<PostalID>");
                            end1 = ss.indexOf("</PostalID>");
                            String PostalID = ss.substring(start1 + 10, end1);

                            EditText txt_name = findViewById(R.id.txt_name);
                            EditText txt_family = findViewById(R.id.txt_family);
                            EditText txt_email = findViewById(R.id.txt_email);
                            EditText txt_national_id = findViewById(R.id.txt_national_id);
                            EditText txt_posti_id = findViewById(R.id.txt_posti_id);
                            txt_name.setText(name);
                            txt_family.setText(family);
                            txt_email.setText(email);
                            txt_national_id.setText(NationalID);
                            txt_posti_id.setText(PostalID);
                            lay_payamak.setVisibility(View.GONE);
                            lay_wait.setVisibility(View.GONE);
                            fun.enableDisableView(lay_register,true);

                        }
                    }

                if (param_str.equals("create_user") && is_requested)
                {
                    Log.d("tg1",ss);
                    start = ss.indexOf("<result>");
                    end = ss.indexOf("</result>");
                    is_requested = false;

                    if(end>0) {
                        lay_wait.setVisibility(View.GONE);
                        fun.enableDisableView(lay_payamak,true);
                        start1 = ss.indexOf("<ID>");
                        end1 = ss.indexOf("</ID>");
                        String u_id = ss.substring(start1 + 4, end1);
                        Functions.u_id = u_id;

                        Functions.u_mobile = tel_number;

                        SharedPreferences.Editor editor = getSharedPreferences("profile", MODE_PRIVATE).edit();
                        editor.putString("u_id", u_id);
                        editor.putString("u_mobile", tel_number);
                        editor.apply();

                        LinearLayout lay_payamak = findViewById(R.id.lay_payamak);
                        lay_payamak.setVisibility(View.GONE);
                        LinearLayout lay_register = findViewById(R.id.lay_register);
                        lay_register.setVisibility(View.VISIBLE);
                        load_user();
                    }

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

    private void load_user() {
        lay_payamak.setVisibility(View.GONE);
        lay_register.setVisibility(View.VISIBLE);
        is_requested = true;
        mm = new MyAsyncTask();
        last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_user&ID="+Functions.u_id+ "&rdn="+String.valueOf(new Random().nextInt());
        Log.d("tg1",last_requested_query);
        //Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();
        lay_wait.setVisibility(View.VISIBLE);
        fun.enableDisableView(lay_register,false);
        mm.url = (last_requested_query);
        mm.execute("");
    }


}
