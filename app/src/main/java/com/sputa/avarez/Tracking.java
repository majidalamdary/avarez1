package com.sputa.avarez;

import android.content.Context;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class Tracking extends AppCompatActivity {
    private int screenWidth;
    private int screenHeight;
    private boolean is_requested;
    MyAsyncTask mm;
    private String last_query;
    RelativeLayout lay_main;
    private Functions fun;
    private int tim=1;
    private Timer timer;

    private void set_size(int vid,Double width,Double height,String typ)
    {
        View v =findViewById(vid);
        if(typ.equals("cons")) {
            ConstraintLayout.LayoutParams lp= (ConstraintLayout.LayoutParams) v.getLayoutParams();
            lp.width=(int)(screenWidth* width);
            lp.height=(int)(screenHeight* height);
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
        TextView v = findViewById(vid);
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
        setContentView(R.layout.activity_tracking);
        lay_main= findViewById(R.id.lay_main);
        fun=new Functions();

        timer = new Timer("timeout");
        timer.start();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        set_size(R.id.txt_motor,.52,.07,"cons");
        set_size_txt(R.id.lbl_title,.08,"cons");
        set_size_txt(R.id.lbl_motor,.05,"cons");
        set_size_edit(R.id.txt_motor,.06,"cons");

        set_size(R.id.txt_parvande,.52,.07,"cons");
        set_size_txt(R.id.lbl_parvandeh,.05,"cons");
        set_size_edit(R.id.txt_parvande,.04,"cons");
        set_size_edit(R.id.txt_motor,.04,"cons");


        set_size(R.id.btn_search,.3,.065,"cons");
        set_size(R.id.btn_back,.3,.065,"cons");
        set_size_txt(R.id.lbl_search,.054,"line");
        set_size_txt(R.id.lbl_back,.054,"line");
        set_size(R.id.btn_pay,.3,.065,"cons");

        set_size_txt(R.id.lbl_help,.033,"cons");
        set_size(R.id.img_help_motor,.08,.06,"cons");
        set_size(R.id.img_help_parvandeh,.08,.06,"cons");

    }

    public void clk_search(View view) {
        EditText txt_motor=findViewById(R.id.txt_motor);
        EditText txt_parvande=findViewById(R.id.txt_parvande);
        String
                search_type="none";
        if(txt_motor.getText().toString().length()>0)
        {
            search_type="ok";
        }
        else if(txt_parvande.getText().toString().length()>0)
        {
            search_type="ok";
        }
        if(search_type.equals("ok"))
        {
            last_query = getResources().getString(R.string.site_url) + "do.aspx?param=get_avarez_pardakht&key_motor="+txt_motor.getText().toString()+"&key_parvandeh="+txt_parvande.getText().toString()+ "&rnd=" + String.valueOf(new Random().nextInt());
            // Toast.makeText(this, last_query, Toast.LENGTH_SHORT).show();
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

    public void clk_message(View view) {
        LinearLayout lay_wait = findViewById(R.id.lay_wait);
        if(lay_wait.getVisibility()==(View.GONE)) {
            RelativeLayout lay_message = findViewById(R.id.lay_message);
            RelativeLayout lay_help_motor = findViewById(R.id.lay_help_motor);
            RelativeLayout lay_help_parvandeh = findViewById(R.id.lay_help_parvandeh);

            fun.enableDisableView(lay_main, true);
            lay_message.setVisibility(View.GONE);
            lay_help_motor.setVisibility(View.GONE);
            lay_help_parvandeh.setVisibility(View.GONE);
        }
    }

    public void clk_help_parvandeh(View view) {
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        RelativeLayout lay_help_motor = findViewById(R.id.lay_help_parvandeh);
        set_size(R.id.lbl_help_parvandeh,.6,.3,"rel");
        set_size_txt(R.id.lbl_help_parvandeh,.042,"rel");
        fun.enableDisableView(lay_main,false);
        lay_message.setVisibility(View.VISIBLE);
        lay_help_motor.setVisibility(View.VISIBLE);
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
            if(end1>7) {

                param_str = ss.substring(start1 + 7, end1);
                // Toast.makeText(CarSearch.this, ss, Toast.LENGTH_SHORT).show();

                if (param_str.equals("get_avarez_pardakht") && is_requested) {
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");
                    TextView lbl_msg = findViewById(R.id.lbl_msg);
                    if (end1 > 0) {
                        String
                                rslt = ss.substring(start1 + 8, end1);


                        if (rslt.equals("NotFound")) {
                            lbl_msg.setText("اطلاعات پرداخت عوارض خودرو شما پیدا نشد لطفا به یکی از شعب پیش خوان دولت یا شهرداری شهر خود مراجعه نمائید.");
                        } else {
                            start1 = rslt.indexOf("<price>");
                            end1 = rslt.indexOf("</price>");
                            String
                                    rslt_price = rslt.substring(start1 + 7, end1);
//                            Toast.makeText(CarSearch.this, rslt_price, Toast.LENGTH_SHORT).show();

                            int
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
                                String
                                        rslt_name = rslt.substring(start1 + 6, end1);
                                start1 = rslt.indexOf("<date>");
                                end1 = rslt.indexOf("</date>");
                                String
                                        rslt_date = rslt.substring(start1 + 6, end1);
                                start1 = rslt.indexOf("<paymentType>");
                                end1 = rslt.indexOf("</paymentType>");
                                String
                                        rslt_paymentType = rslt.substring(start1 + 13, end1);
                                start1 = rslt.indexOf("<CarName>");
                                end1 = rslt.indexOf("</CarName>");
                                String
                                        rslt_CarName = rslt.substring(start1 + 9, end1);


                                String
                                        msg="";
                                if(rslt_name.length()>2)
                                {
                                    msg += "نام مالک : " + rslt_name + "\n";
                                }
                                if(rslt_CarName.length()>2)
                                {
                                    msg += "نام خودرو : " + rslt_CarName + "\n";
                                }
                                msg+= " آخرین مبلغ پرداختی شما " + new_str + " ریال در تاریخ "+ rslt_date+ " بصورت " + rslt_paymentType +" می باشد. ";
                                lbl_msg.setText(msg);
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
                                    Toast.makeText(Tracking.this, "خطای شبکه رخ داد", Toast.LENGTH_SHORT).show();
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






