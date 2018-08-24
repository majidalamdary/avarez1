package com.sputa.avarez;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Splash extends AppCompatActivity {
    private int screenWidth;
    private int screenHeight;
    ImageView img_red,img_grey,img_red_grey;
    private int start_margin;
    private int top_margin;
    private int transition_time_out=500;

    private boolean move_arm=true;
    private Timer tim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        img_red=findViewById(R.id.img_red);
        img_grey=findViewById(R.id.img_grey);
        img_red_grey=findViewById(R.id.img_red_grey);

        RelativeLayout.LayoutParams lp_grey =  new RelativeLayout.LayoutParams((int)(screenWidth*.5),(int)(screenWidth*.5));
        start_margin=(screenWidth/2)-(lp_grey.width/2)-(int)(screenWidth*.05);
        top_margin=screenHeight/2-(lp_grey.height/2)-(int)(screenHeight*.14);
        lp_grey.setMarginStart(start_margin);
        lp_grey.topMargin =top_margin ;
        img_grey.setLayoutParams(lp_grey);

        RelativeLayout.LayoutParams lp_red =  new RelativeLayout.LayoutParams((int)(screenWidth*.5),(int)(screenWidth*.5));
        start_margin=(screenWidth/2)-(lp_grey.width/2)-(int)(screenWidth*.05);
        top_margin=screenHeight/2-(lp_grey.height/2)-(int)(screenHeight*.14);
        lp_red.setMarginStart(start_margin);
        lp_red.topMargin =top_margin ;
        img_red.setLayoutParams(lp_red);
        img_red.setVisibility(View.GONE);


        RelativeLayout.LayoutParams lp_red_grey =  new RelativeLayout.LayoutParams((int)(screenWidth*.5),(int)(screenWidth*.5));
        lp_red_grey.setMarginStart(lp_red_grey.width*-1);
        lp_red_grey.topMargin =top_margin ;
        img_red_grey.setLayoutParams(lp_red_grey);

        TextView txt_company = (TextView) findViewById(R.id.txt_company);
        txt_company.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * 0.065));
        RelativeLayout.LayoutParams lp_txt_company = (RelativeLayout.LayoutParams) txt_company.getLayoutParams();
        lp_txt_company.setMarginStart((int)(screenWidth*.11));
        lp_txt_company.topMargin=((int)(screenHeight*.53));
        txt_company.setLayoutParams(lp_txt_company);
        txt_company.setVisibility(View.GONE);

        TextView txt_software = (TextView) findViewById(R.id.txt_software);
        txt_software.setTextSize(TypedValue.COMPLEX_UNIT_PX, (int) (screenWidth * 0.045));
        RelativeLayout.LayoutParams lp_txt_software = (RelativeLayout.LayoutParams) txt_software.getLayoutParams();
        lp_txt_software.setMarginStart((int)(screenWidth*.22));
        lp_txt_software.topMargin=((int)(screenHeight*.62));
        txt_software.setLayoutParams(lp_txt_software);
        txt_software.setVisibility(View.GONE);


        SharedPreferences preferences = getSharedPreferences("profile",MODE_PRIVATE);
        String u_id = preferences.getString("u_id", "");
        if(!u_id.equalsIgnoreCase(""))
        {
            Functions.u_id = u_id;
            String u_mobile = preferences.getString("u_mobile", "");
            Functions.u_mobile = u_mobile;
        }


        tim = new Timer("draw");
        tim.start();
    }
    private void fin()
    {
     //   startActivity(new Intent(this,MainActivity.class));
        tim.typ="majid";

        finish();
        if(Functions.u_id.equals("0"))
            startActivity(new Intent(this,RegisterActivity.class));
        else
            startActivity(new Intent(this,DrawerTest.class));
    }
    public class Timer extends Thread {

        int oneSecond=1000;
        int value=0;
        String TAG="Timer";
        String typ="";
        public long milles=2;


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



                        if(typ.equals("draw"))
                        {

                            if(move_arm) {
                                RelativeLayout.LayoutParams lp_red = (RelativeLayout.LayoutParams) img_red_grey.getLayoutParams();
                                lp_red.setMarginStart(lp_red.getMarginStart() + 2);
                                img_red_grey.setLayoutParams(lp_red);
                                if (lp_red.getMarginStart() == start_margin || lp_red.getMarginStart() == start_margin+1) {
//                                    Log.d("majid", "ok1" + String.valueOf(lp_red.getMarginStart()));
                                    move_arm=false;
                                    img_red_grey.setVisibility(View.GONE);
                                    img_grey.setVisibility(View.GONE);
                                    img_red.setVisibility(View.VISIBLE);
                                    TextView txt_company = (TextView) findViewById(R.id.txt_company);
                                    txt_company.setVisibility(View.VISIBLE);
                                    TextView txt_software = (TextView) findViewById(R.id.txt_software);
                                    txt_software.setVisibility(View.VISIBLE);
                                    typ="transition";

                                }
                            }
                        }
                        if(typ.equals("transition")) {
                           // Log.d("majid","transition"+String.valueOf(transition_time_out));
                            transition_time_out--;
                            if(transition_time_out<0)
                            {
                                fin();
                                typ="majid";
                               // Toast.makeText(Splash.this, "majid", Toast.LENGTH_SHORT).show();
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
