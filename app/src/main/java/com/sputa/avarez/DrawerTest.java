package com.sputa.avarez;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.sputa.avarez.app.Config;
import com.sputa.avarez.app.NotificationUtils;
import com.sputa.avarez.classes.customFont;

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
import java.util.Base64;
import java.util.Random;

public class DrawerTest extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int screenWidth;
    private int screenHeight;
    public BroadcastReceiver mRegistrationBroadcastReceiver;
    private String regId;
    private boolean is_requested=false;
    private String last_requested_query;
    private MyAsyncTask mm;
    private String rslt_name;
    private String rslt_family;
    private String rslt_mobile;
    TextView navMobile;
    TextView navname;
    private int tim=1;
    private Timer timer;

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
        setContentView(R.layout.activity_drawer_test);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        navMobile = (TextView) headerView.findViewById(R.id.txt_phone);
        navname = (TextView) headerView.findViewById(R.id.txt_name);
      //  navUsername.setText("Your Text Here");


        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        set_content();

        displayFirebaseRegId();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               // Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();
                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
                 //   Toast.makeText(context, "register", Toast.LENGTH_SHORT).show();
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                      Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //  Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                }
            }
        };
        timer = new Timer("timeout");
        timer.start();
      get_info();

    }

    private void get_info() {
        mm = new MyAsyncTask();
        last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_user_info&ID="+Functions.u_id+ "&gcm_id="+ URLEncoder.encode(regId)+"&rdn="+String.valueOf(new Random().nextInt());
        // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();
        is_requested = true;
        tim=1;
        mm.url = (last_requested_query);
        mm.execute("");
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        Log.e("majid", "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            //Toast.makeText(this, "111Firebase Reg Id: " + regId, Toast.LENGTH_SHORT).show();
            mm = new MyAsyncTask();
            last_requested_query = getResources().getString(R.string.site_url) + "do?param=update_gcm_id&ID="+Functions.u_id+ "&gcm_id="+ URLEncoder.encode(regId)+"&rdn="+String.valueOf(new Random().nextInt());
           // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();

            mm.url = (last_requested_query);
            mm.execute("");
          }
        else
            Toast.makeText(this,"Firebase Reg Id is not received yet! ", Toast.LENGTH_SHORT).show();
        EditText ed= findViewById(R.id.editText);
        ed.setText(regId);
    }

    private void set_content() {
        set_size(R.id.lay_profile,.373,.225,"rel");
        set_size(R.id.lay_gas,.53,.225,"rel");
        set_size(R.id.lay_avarez,.53,.39,"rel");
        set_size(R.id.lay_news,.37,.19,"rel");
        set_size(R.id.lay_about_us,.37,.19,"rel");
        set_size(R.id.lay_exit,.92,.115,"rel");

        ConstraintLayout lay_profile = findViewById(R.id.lay_profile);
        RelativeLayout.LayoutParams lp_lay_profile = (RelativeLayout.LayoutParams) lay_profile.getLayoutParams();
        lp_lay_profile.setMarginStart((int)(screenWidth*.04));
        lp_lay_profile.topMargin =(int)(screenHeight*.02);

        ConstraintLayout lay_gas = findViewById(R.id.lay_gas);
        RelativeLayout.LayoutParams lp_lay_gas = (RelativeLayout.LayoutParams) lay_gas.getLayoutParams();
        lp_lay_gas.setMarginStart((int)(screenWidth*.43));
        lp_lay_gas.topMargin =(int)(screenHeight*.02);

        ConstraintLayout lay_avarez = findViewById(R.id.lay_avarez);
        RelativeLayout.LayoutParams lp_lay_avarez = (RelativeLayout.LayoutParams) lay_avarez.getLayoutParams();
        lp_lay_avarez.setMarginStart((int)(screenWidth*.04));
        lp_lay_avarez.topMargin =(int)(screenHeight*.25);

        ConstraintLayout lay_news = findViewById(R.id.lay_news);
        RelativeLayout.LayoutParams lp_lay_news = (RelativeLayout.LayoutParams) lay_news.getLayoutParams();
        lp_lay_news.setMarginStart((int)(screenWidth*.59));
        lp_lay_news.topMargin =(int)(screenHeight*.25);

        ConstraintLayout lay_about_us = findViewById(R.id.lay_about_us);
        RelativeLayout.LayoutParams lp_lay_about_us = (RelativeLayout.LayoutParams) lay_about_us.getLayoutParams();
        lp_lay_about_us.setMarginStart((int)(screenWidth*.59));
        lp_lay_about_us.topMargin =(int)(screenHeight*.45);

        ConstraintLayout lay_exit = findViewById(R.id.lay_exit);
        RelativeLayout.LayoutParams lp_lay_exit = (RelativeLayout.LayoutParams) lay_exit.getLayoutParams();
        lp_lay_exit.setMarginStart((int)(screenWidth*.04));
        lp_lay_exit.topMargin =(int)(screenHeight*.65);

        set_size(R.id.img_profile,.14,.14,"cons");
        set_size(R.id.img_gas,.14,.14,"cons");
        set_size(R.id.img_car,.14,.14,"cons");
        set_size(R.id.img_news,.14,.14,"cons");
        set_size(R.id.img_aboutus,.14,.14,"cons");
        set_size(R.id.img_exit,.14,.14,"cons");

        set_size_txt(R.id.txt_profile,0.055,"cons");
        set_size_txt(R.id.txt_car,0.055,"cons");
        set_size_txt(R.id.txt_gas,0.055,"cons");
        set_size_txt(R.id.txt_aboutus,0.055,"cons");
        set_size_txt(R.id.txt_news,0.055,"cons");
        set_size_txt(R.id.txt_exit,0.055,"cons");

    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           // super.onBackPressed();
            Toast.makeText(this, "برای خروج دکمه خروج را لمس کنید", Toast.LENGTH_SHORT).show();
        }
    }
    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Vazir.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new customFont("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent = new Intent(this,RegisterActivity.class);
            intent.putExtra("type","main");
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_my_cars) {
            startActivity(new Intent(this,MyCarList.class));

        } else if (id == R.id.nav_abone) {
            startActivity(new Intent(this,MyEshterakList.class));

        } else if (id == R.id.nav_messages) {
            startActivity(new Intent(this,NewsList.class));

        } else if (id == R.id.nav_contact_us) {

        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(this,AboutUs.class));

        } else if (id == R.id.nav_exit) {
            fn_exit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void clk_profile(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.putExtra("type","main");
        startActivity(intent);
    }
    public void clk_exit_profile(View view) {
        Functions.u_id="0";
        Functions.u_mobile="";
        SharedPreferences.Editor editor = getSharedPreferences("profile", MODE_PRIVATE).edit();
        editor.putString("u_id", Functions.u_id);
        editor.putString("u_mobile", Functions.u_mobile);
        editor.apply();
        finish();
        Intent intent = new Intent(this,RegisterActivity.class);
        intent.putExtra("type","main");
        startActivity(intent);
    }

    public void clk_avarez(View view) {
        Intent i = new Intent(this,SelectAvarezType.class);
        i.putExtra("typ","search");
        startActivity(i);
    }

    public void clk_exit(View view) {
        fn_exit();
    }

    private void fn_exit() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("خروج؟")
                .setMessage("آیا می خواهید خارج شوید؟")
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        finish();

                    }
                }).setNegativeButton("نه نمی خوام", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // continue with delete

            }
        })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void clk_ghabz(View view) {
        Intent i = new Intent(this,SelectGhabzType.class);
        i.putExtra("typ","search");
        startActivity(i);
    }

    public void clk_about_us(View view) {

        startActivity(new Intent(this,AboutUs.class));
    }

    public void clk_news(View view) {
        startActivity(new Intent(this,NewsList.class));
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
            if(end1>0) {
                param_str = ss.substring(start1 + 7, end1);
               // Toast.makeText(DrawerTest.this, param_str, Toast.LENGTH_SHORT).show();

                if (param_str.equals("get_user_info") && is_requested ) {
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");

                    String rslt = ss.substring(start1 + 8, end1);
                    if (!rslt.equals("0")) {
                        is_requested = false;
                        start1 = ss.indexOf("<name>");
                        end1 = ss.indexOf("</name>");
                         rslt_name = ss.substring(start1 + 6, end1);
                         start1 = ss.indexOf("<family>");
                        end1 = ss.indexOf("</family>");
                         rslt_family = ss.substring(start1 + 8, end1);
                         start1 = ss.indexOf("<mobile>");
                        end1 = ss.indexOf("</mobile>");
                         rslt_mobile = ss.substring(start1 + 8, end1);

                        navMobile.setText(rslt_mobile);
                        navname.setText(rslt_name+" "+rslt_family);
                      //  Toast.makeText(DrawerTest.this, rslt_name, Toast.LENGTH_SHORT).show();
                    }

                }
                if (param_str.equals("update_gcm_id") ) {
                    //  Toast.makeText(getBaseContext(),ss,Toast.LENGTH_LONG).show();
                    //  EditText txt_email = findViewById(R.id.txt_email);
                    //   txt_email.setText(ss);
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");

                    String rslt = ss.substring(start1 + 8, end1);
                    if (!rslt.equals("0")) {

                     //   Toast.makeText(DrawerTest.this, "ok", Toast.LENGTH_SHORT).show();
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
                                    get_info();
                                    tim=1;
                                    // Log.d("majid",String.valueOf(tim));
                                    Toast.makeText(DrawerTest.this, "خطای شبکه- اشکال در دریافت اطاعات", Toast.LENGTH_SHORT).show();
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
