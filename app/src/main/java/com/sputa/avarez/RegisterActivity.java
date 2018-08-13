package com.sputa.avarez;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private boolean is_requested;
    private MyAsyncTask mm;
    String last_requested_query="";
    private String tel_number="09141484633";
    private String action="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Intent inten=getIntent();
        action = inten.getStringExtra("action");

        MyTextView txt_title = findViewById(R.id.txt_title);

    }

    public void clk_register(View view) {

        boolean
                flag = true;
        TextView txt_name = findViewById(R.id.txt_name);
        TextView txt_family = findViewById(R.id.txt_family);
        TextView txt_email = findViewById(R.id.txt_email);
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
        if(flag) {
            if(!is_requested) {
                //  Toast.makeText(this, "majid", Toast.LENGTH_SHORT).show();
                is_requested = true;
                mm = new MyAsyncTask();
                last_requested_query = getResources().getString(R.string.site_url) + "do.php?param=new_user&name=" + URLEncoder.encode(txt_name.getText().toString()) + "&family=" + URLEncoder.encode(txt_family.getText().toString()) + "&email=" + URLEncoder.encode(txt_email.getText().toString()) + "&tel=" + URLEncoder.encode(tel_number)+"&rdn="+String.valueOf(new Random().nextInt());
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
            random_code="";
    public void clk_send_number(View view) {

//        CropImage.activity()
//                .setGuidelines(CropImageView.Guidelines.ON)
//                .start(this);
        if(typ_tel_conftim.equals("code")) {
            TextView txt_tel = findViewById(R.id.txt_tel);
            if(txt_tel.getText().toString().equals(random_code))
            {



                if(!is_requested) {
                    //  Toast.makeText(this, "majid", Toast.LENGTH_SHORT).show();
                    is_requested = true;
                    mm = new MyAsyncTask();
                    last_requested_query = getResources().getString(R.string.site_url) + "do.php?param=check_code&code="+txt_tel.getText().toString()+ "&tel=" + URLEncoder.encode(tel_number)+"&rdn="+String.valueOf(new Random().nextInt());
                    // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();
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
                    TextView lbl_tel = findViewById(R.id.lbl_tel);
                    TextView lbl_message = findViewById(R.id.lbl_message);
                    TextView lbl_time = findViewById(R.id.lbl_time);
                    lbl_message.setText(" کدی که از طریق پیامک دریافت خواهید کرد را در کادر بالا وارد کنید.");
                    lbl_time.setText("");
                    lbl_tel.setText("کد تائید :");
                    tel_number = txt_tel.getText().toString();
                    txt_tel.setHint("");
                    txt_tel.setText("");
                    int rnd=1000 +  new Random().nextInt(8999);
                    Toast.makeText(this, String.valueOf(rnd), Toast.LENGTH_SHORT).show();
                    random_code = String.valueOf(rnd);
                    typ_tel_conftim ="code";

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
            //Toast.makeText(register_user.this, ss, Toast.LENGTH_SHORT).show();
            if(end>0 && ss.length()>0) {
                output_str = ss.substring(start + 8, end);
                int
                        start1 = ss.indexOf("<param>");
                int
                        end1 = ss.indexOf("</param>");

                param_str = ss.substring(start1 + 7, end1);

                if (param_str.equals("new_user") && is_requested) {
                    //  Toast.makeText(getBaseContext(),ss,Toast.LENGTH_LONG).show();
                    //  EditText txt_email = findViewById(R.id.txt_email);
                    //   txt_email.setText(ss);
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");
                    is_requested = false;
                    String rslt = ss.substring(start1 + 8, end1);
                    if (!rslt.equals("0")) {
                        start1 = ss.indexOf("<u_id>");
                        end1 = ss.indexOf("</u_id>");
                        String u_id = ss.substring(start1 + 6, end1);
                        Functions.u_id = u_id;
                        start1 = ss.indexOf("<u_name>");
                        end1 = ss.indexOf("</u_name>");
                        String u_name = ss.substring(start1 + 8, end1);
                        Functions.u_name = u_name;

                        SharedPreferences.Editor editor = getSharedPreferences("profile", MODE_PRIVATE).edit();
                        editor.putString("u_id", u_id);
                        editor.putString("u_name", u_name);
                        editor.apply();
                        Toast.makeText(RegisterActivity.this, "کاربر ثبت شد", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(register_user.this,NewItem.class));
                        if (action.equals("finish"))
                            finish();
                        else if (action.equals("new_item")) {
                            finish();
                            startActivity(new Intent(RegisterActivity.this, DrawerTest.class));
                        }
                    }

                }

                if (param_str.equals("check_code") && is_requested)

                {
                    //  Toast.makeText(getBaseContext(),ss,Toast.LENGTH_LONG).show();
                    //  EditText txt_email = findViewById(R.id.txt_email);
                    //   txt_email.setText(ss);
                    start = ss.indexOf("<result>");
                    end = ss.indexOf("</result>");
                    is_requested = false;
                    String rslt = ss.substring(start + 8, end);
                    if (!rslt.equals("0")) {
                        start1 = ss.indexOf("<u_id>");
                        end1 = ss.indexOf("</u_id>");
                        String u_id = ss.substring(start1 + 6, end1);
                        Functions.u_id = u_id;
                        start1 = ss.indexOf("<u_name>");
                        end1 = ss.indexOf("</u_name>");
                        String u_name = ss.substring(start1 + 8, end1);
                        Functions.u_name = u_name;

                        SharedPreferences.Editor editor = getSharedPreferences("profile", MODE_PRIVATE).edit();
                        editor.putString("u_id", u_id);
                        editor.putString("u_name", u_name);
                        editor.apply();
                        if (action.equals("finish"))
                            finish();
                        else if (action.equals("new_item")) {
                            startActivity(new Intent(RegisterActivity.this, DrawerTest.class));
                            finish();
                        }
                    } else {
                        LinearLayout lay_payamak = findViewById(R.id.lay_payamak);
                        lay_payamak.setVisibility(View.GONE);
                        LinearLayout lay_register = findViewById(R.id.lay_register);
                        lay_register.setVisibility(View.VISIBLE);
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



}
