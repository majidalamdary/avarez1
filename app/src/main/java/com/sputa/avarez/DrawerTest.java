package com.sputa.avarez;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.sputa.avarez.adapters.item_cars_adapter;
import com.sputa.avarez.adapters.item_eshterak_adapter;
import com.sputa.avarez.app.Config;
import com.sputa.avarez.app.NotificationUtils;
import com.sputa.avarez.classes.CallSoap;
import com.sputa.avarez.classes.StaticGasGhabz;
import com.sputa.avarez.classes.StaticWaterGhabz;
import com.sputa.avarez.classes.customFont;
import com.sputa.avarez.model.DB;
import com.sputa.avarez.model.items_cars;
import com.sputa.avarez.model.items_eshterak;

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
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import me.leolin.shortcutbadger.ShortcutBadger;

import static com.sputa.avarez.Functions.Lag;

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
    private int cnt_eshterak=1;
    private Timer timer;
    private int not_zero_cnt=0;
    private SQLiteDatabase myDB;
    private int NosaziCount=0;
    private MyAsyncTaskService Asy;

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
        Toolbar toolbar =  findViewById(R.id.toolbar);
        TextView mTitle =  toolbar.findViewById(R.id.toolbar_title);



//        Functions.u_id ="2";
//        Toast.makeText(this, Functions.u_id, Toast.LENGTH_SHORT).show();
        setSupportActionBar(toolbar);
        mTitle.setText(toolbar.getTitle());

        getSupportActionBar().setDisplayShowTitleEnabled(false);

            LinearLayout lay_my_car_count= findViewById(R.id.lay_my_car_count);
            TextView lbl_my_car_count=findViewById(R.id.lbl_my_car_count);
            lay_my_car_count.setVisibility(View.GONE);
            lbl_my_car_count.setText(String.valueOf(0));


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


        myDB = this.openOrCreateDatabase(getString(R.string.DB_name), MODE_PRIVATE, null);

//        myDB.execSQL("drop table IF EXISTS water");
//        myDB.execSQL("drop table IF EXISTS waste");
//        myDB.execSQL("drop table IF EXISTS power");
//        myDB.execSQL("drop table IF EXISTS Panel");
//        myDB.execSQL("drop table IF EXISTS Nosazi");
//        myDB.execSQL("drop table IF EXISTS Gas");
//        myDB.execSQL("drop table IF EXISTS CellPhone");
//        myDB.execSQL("drop table IF EXISTS Business");
//        myDB.execSQL("drop table IF EXISTS MyGhabz");
        String sql= "CREATE TABLE  IF NOT EXISTS MyNosazi(" +
                "NosaziID varchar(255) NULL," +
                "Type varchar(255) NULL" +
                ");" ;
        myDB.execSQL(sql);
        Cursor cr = myDB.rawQuery("select DISTINCT tbl_name  from sqlite_master where tbl_name = 'water'", null);
        if(cr!=null)
        {
            if(cr.getCount()==0) {
                set_database();
            }
        }
        cr = myDB.rawQuery("select * from water", null);
        if(cr.getCount()>0)
        {
//            cr.moveToFirst();
//            Toast.makeText(this, cr.getString(0), Toast.LENGTH_SHORT).show();
//            cr.moveToNext();
//            Toast.makeText(this, cr.getString(0), Toast.LENGTH_SHORT).show();
        }

        //
        setNotificationCount();

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

//        load_my_cars();
//        load_eshterak_count();
    }

    private void setNotificationCount() {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.MyBills), MODE_PRIVATE);
        int iNotiCount = 0;
        String eshterakCount = prefs.getString("CountEsterak", null);
//        Toast.makeText(this,"esh=" + eshterakCount, Toast.LENGTH_SHORT).show();
        if (eshterakCount != null) {
            iNotiCount += Integer.valueOf(eshterakCount);
        }
        String eshterakCar = prefs.getString("CountCar", null);
        if (eshterakCar != null) {
            iNotiCount += Integer.valueOf(eshterakCar);
        }
        Cursor cr = myDB.rawQuery("select * from MyNosazi", null);
        if(cr.getCount()>0)
        {
            cr.moveToFirst();
            for (int i=0;i<cr.getCount();i++)
            {
                Lag("PaidOrNot="+cr.getString(1));
                if(cr.getString(1).equals("Paid"))
                {
                    iNotiCount++;
                }
                cr.moveToNext();
            }
        }



        if(iNotiCount>0) {
            LinearLayout lay_my_car_count = findViewById(R.id.lay_my_car_count);
            TextView lbl_my_car_count = findViewById(R.id.lbl_my_car_count);
            lay_my_car_count.setVisibility(View.VISIBLE);
            lbl_my_car_count.setText(String.valueOf(iNotiCount));
        }
    }

    private void set_database() {

        //Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
        String
                query =  DB.query;
        String
                cmd="";
        for(int i=0;i<query.length();i++)
        {
            if(query.charAt(i) != ';') {
                cmd += query.charAt(i);
            }
            else
            {
                //cmd += ";";
                myDB.execSQL(cmd);
                Log.d("majid",cmd);
                cmd="";
            }
        }

            String sql= "CREATE TABLE  IF NOT EXISTS MyGhabz(" +
                    "AboneID varchar(255) NULL," +
                    "Type varchar(255) NULL" +
                    ");" ;
        myDB.execSQL(sql);










    }

    private void load_eshterak_count() {

        cnt_eshterak=0;
        Cursor cr = myDB.rawQuery("select * from MyGhabz ",null);
        if(cr.getCount()>0) cnt_eshterak = cr.getCount();
//        if(cnt_eshterak>0)
        {
            SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MyBills), MODE_PRIVATE).edit();
            editor.putString("CountEsterak", String.valueOf(cnt_eshterak));
            editor.apply();
//            Toast.makeText(this, String.valueOf(cnt_eshterak), Toast.LENGTH_SHORT).show();
        }
//
        Asy =new MyAsyncTaskService("GetNosaziList","");
        Asy.execute();
//
//
//
        mm = new MyAsyncTask();
        last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_my_cars&ID="+Functions.u_id+ "&rdn="+String.valueOf(new Random().nextInt());
        // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();

        mm.url = (last_requested_query);
        mm.execute("");
//        Lag(last_requested_query);
////        int badgeCount = cnt_eshterak+not_zero_cnt;
////        ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+

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
        set_size(R.id.lay_avarez,.53,.19,"rel");
        set_size(R.id.lay_driving_offense,.53,.19,"rel");
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

        ConstraintLayout lay_driving_offense = findViewById(R.id.lay_driving_offense);
        RelativeLayout.LayoutParams lp_lay_driving_offense = (RelativeLayout.LayoutParams) lay_driving_offense.getLayoutParams();
        lp_lay_driving_offense.setMarginStart((int)(screenWidth*.04));
        lp_lay_driving_offense.topMargin =(int)(screenHeight*.452);

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
        set_size(R.id.img_driving_offense,.14,.14,"cons");
        set_size(R.id.img_news,.14,.14,"cons");
        set_size(R.id.img_aboutus,.14,.14,"cons");
        set_size(R.id.img_exit,.14,.14,"cons");

        set_size_txt(R.id.txt_profile,0.055,"cons");
        set_size_txt(R.id.txt_car,0.055,"cons");
        set_size_txt(R.id.txt_driving_offense,0.049,"cons");
        set_size_txt(R.id.txt_gas,0.055,"cons");
        set_size_txt(R.id.txt_aboutus,0.046,"cons");
        set_size_txt(R.id.txt_news,0.050,"cons");
        set_size_txt(R.id.lbl_my_car_count,0.0420,"line");
        set_size_txt(R.id.lbl_my_eshterak_count,0.0420,"line");
        set_size_txt(R.id.txt_exit,0.055,"cons");
        set_size(R.id.lay_my_car_count,.07,.04,"cons");
        set_size(R.id.lay_my_eshterak_count,.07,.04,"cons");
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
        LinearLayout lay_my_car_count= findViewById(R.id.lay_my_car_count);
        TextView lbl_my_car_count=findViewById(R.id.lbl_my_car_count);
        lay_my_car_count.setVisibility(View.GONE);
        lbl_my_car_count.setText(String.valueOf(0));
        load_eshterak_count();
        setNotificationCount();
//        load_my_cars();
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
        }
        else if (id == R.id.nav_pay_history) {
            startActivity(new Intent(this,PayHistoryActivity.class));
        }

         else if (id == R.id.nav_messages) {
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

    public void clk_news(View view) {
        startActivity(new Intent(this,NewsList.class));
    }

    public void clk_driving(View view) {
        Toast.makeText(this, "در دست طراحی", Toast.LENGTH_SHORT).show();
    }

    public void clk_my_cars(View view) {
        startActivity(new Intent(this,MyEshterakList.class));
    }

    public void clk_my_eshterak(View view) {
        startActivity(new Intent(this,NewsList.class));
    }


    private class MyAsyncTaskService extends AsyncTask<String, Integer, Double> {
        String Param;
        String Type;
        private String resp="";

        public MyAsyncTaskService(String Type,String Param) {
            this.Param=Param;
            this.Type=Type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Double aDouble) {

            Lag("Excuted");
            if(Type.equals("GetNosaziList")) {

                GetNosaziListResult();
            }
            if(Type.equals("GetInfoNosazi")) {

                GetInfoNosaziResult();
            }
        }
        private void GetInfoNosaziResult() {
            int
                    test_res = resp.indexOf("PaymentID");


            if (resp.equals("error")) {
//                Toast.makeText(DrawerTest.this, "خطایی رخ داده است لطفا کد نوسازی و ارتباط اینترنت را بررسی نمایید", Toast.LENGTH_SHORT).show();
            }

            int index =resp.indexOf("***");
            String nosaziCode ="";
            if(index>0)
            {
                nosaziCode =resp.substring(0,index);

            }
            myDB.execSQL("delete from MyNosazi where NosaziId='"+nosaziCode+"'");

            if (test_res > 0) {


//                btn_detail.setVisibility(View.VISIBLE);
                String
                        msg = "";


                int start1 = resp.indexOf("BillID=");
                int end1 = resp.indexOf("PaymentID");

                if (end1 > 0) {


                    String
                            rslt = resp.substring(start1 + 7, end1);
                    String NosaziBillID = rslt;
                    String NosaziPaymentID = "";
                    start1 = resp.indexOf("PaymentID=");
                    end1 = resp.indexOf(";");
                    String pri = "";
                    if (rslt.length() > 1) {
                        rslt = resp.substring(start1 + 10, end1);
                        NosaziPaymentID = rslt;
                        pri = "مبلغ : "+NosaziPaymentID.substring(4,5)+","+NosaziPaymentID.substring(5,8)+",000 ریال"+"\n";
                        myDB.execSQL("insert into MyNosazi (NosaziId,Type) values ('"+nosaziCode+"','Paid') ");

                    } else {

                        pri = "پرداخت شده";
                    }

//                if(NosaziPaymentID.length()>0)
//                {
//
//                    LinearLayout lay_my_car_count= findViewById(R.id.lay_my_car_count);
//                            TextView lbl_my_car_count=findViewById(R.id.lbl_my_car_count);
//                            lay_my_car_count.setVisibility(View.VISIBLE);
//                            int
//                                    cnt=Integer.valueOf(lbl_my_car_count.getText().toString());
//                            cnt++;
//                            lbl_my_car_count.setText(String.valueOf(cnt));
//
//                    }

                }
            }
        }

        private void GetNosaziListResult() {


            if(resp.length()>0)
            {
                String
                        nosaziCode="";
                for(int i=0;i<resp.length();i++)
                {

                    if(resp.charAt(i) != ',')
                    {
                        nosaziCode+=resp.charAt(i);
                    }
                    else
                    {
                        Asy = new MyAsyncTaskService("GetInfoNosazi",nosaziCode );
                        Asy.execute();
                        Lag("majid");
                        // Toast.makeText(MyEshterakList.this, nosaziCode, Toast.LENGTH_SHORT).show();
                        nosaziCode="";
                    }
                }
            }

//            Toast.makeText(MyEshterakList.this, resp, Toast.LENGTH_SHORT).show();
//            item.add(new items_eshterak("1024", "123", "456","1",  "nosazi"));
//
//            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
//            mRecyclerView.setHasFixedSize(true);
//            mLayoutManager = new LinearLayoutManager(MyEshterakList.this);
//            mRecyclerView.setLayoutManager(mLayoutManager);
//
//
//            mAdapter = new item_eshterak_adapter(MyEshterakList.this,item,MyEshterakList.this);
//            mRecyclerView.setAdapter(mAdapter);
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Double doInBackground(String... strings) {
            Lag(Type);
            if(Type.equals("GetNosaziList")) {
                GetNosaziList();
            }
            if(Type.equals("GetInfoNosazi"))
            {
                GetInfoNosazi(Param);
            }
            return null;
        }
        private void GetInfoNosazi(String PnosaziKodem) {




            CallSoap cs;

            UUID AID = UUID.randomUUID();
            String AtuhIn =PnosaziKodem+AID;
            String Atuh=SetAtuh(AtuhIn);

            try{
                cs = new CallSoap();
                resp=cs.Call_Nosazi_GetInfo(PnosaziKodem, AID,Atuh);
                resp = PnosaziKodem+"***"+resp;
                Lag("Res="+resp);
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());

            }
        }

        private void GetNosaziList() {




            CallSoap cs;
            //String PnosaziKodem="1-11-40-7-0-0-0";



            try{
                cs = new CallSoap();
                resp=cs.Call_Nosazi_GetNosaziList();

                Lag("Res=1"+resp);
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());

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
            if(end1>0) {
                param_str = ss.substring(start1 + 7, end1);
               // Toast.makeText(DrawerTest.this, param_str, Toast.LENGTH_SHORT).show();

                if (param_str.equals("get_user_info") && is_requested ) {
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");

                    String rslt = ss.substring(start1 + 8, end1);
                    if (!rslt.equals("0")) {
                        is_requested = false;
                        try {
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
                            navname.setText(rslt_name + " " + rslt_family);
                        }
                        catch (Exception e1)
                        {

                        }
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
                if (param_str.equals("get_my_cars") ) {
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");

                    String rslt = ss.substring(start1 + 8, end1);
                    Lag(ss);
                    if (!rslt.equals("0")) {

                        start1 = ss.indexOf("<carCount>");
                        end1 = ss.indexOf("</carCount>");
                        rslt = ss.substring(start1 + 10, end1);
                        if (!rslt.equals("0")) {
                            int cnt = Integer.valueOf(rslt);
                            not_zero_cnt=0;

                            for (int i = 1; i <= cnt; i++) {
                                start1 = ss.indexOf("<car" + String.valueOf(i) + ">");
                                end1 = ss.indexOf("</car" + String.valueOf(i) + ">");
                                rslt = ss.substring(start1 + 6, end1);
                                start1 = rslt.indexOf("<avarez>");
                                end1 = rslt.indexOf("</avarez>");
                                String avarez = rslt.substring(start1 + 8, end1);
                                Lag("avarez="+avarez);
                                if(!avarez.equals("0"))
                                {
                                    not_zero_cnt++;

                                }

                            }
                            if(not_zero_cnt>0)
                            {
                                SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MyBills), MODE_PRIVATE).edit();
                                editor.putString("CountCar", String.valueOf(not_zero_cnt));
                                editor.apply();
//                                Toast.makeText(DrawerTest.this,"not-zero" + not_zero_cnt, Toast.LENGTH_SHORT).show();
                            }

//                            int badgeCount = cnt_eshterak+not_zero_cnt;
//                            ShortcutBadger.applyCount(getApplicationContext(), badgeCount); //for 1.1.4+

                        }


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
                                    //Toast.makeText(DrawerTest.this, "خطای شبکه- اشکال در دریافت اطاعات", Toast.LENGTH_SHORT).show();
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


    public class tim extends Thread
    {
        public tim() {

        }

        @Override
        public void run() {
            for(;;) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }
    }

}
