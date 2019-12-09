package com.sputa.avarez;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sputa.avarez.classes.customFont;

public class SelectAvarezType extends AppCompatActivity   {
    private int screenWidth;
    private int screenHeight;
    private String typ;

    LinearLayout lay_car;
    LinearLayout lay_pasmand;
    LinearLayout lay_renew;
    LinearLayout lay_bussiness;
    LinearLayout lay_tabloo;
    LinearLayout lay_jame;
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
        setContentView(R.layout.activity_select_avarez_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        lay_car = findViewById(R.id.lay_chance_rival_shade);
        lay_pasmand = findViewById(R.id.lay_pasmand_shade);
        lay_renew = findViewById(R.id.lay_nosazi_shade);
        lay_tabloo = findViewById(R.id.lay_tabloo_shade);
        lay_bussiness = findViewById(R.id.lay_bussiness_shade);
        lay_jame = findViewById(R.id.lay_jame_shade);

        lay_car.setVisibility(View.VISIBLE);
        lay_pasmand.setVisibility(View.VISIBLE);
        lay_renew.setVisibility(View.VISIBLE);
        lay_tabloo.setVisibility(View.VISIBLE);
        lay_bussiness.setVisibility(View.VISIBLE);
        lay_jame.setVisibility(View.VISIBLE);

        if(Functions.pob_car)
            lay_car.setVisibility(View.GONE);
        if(Functions.pob_pasmand)
            lay_pasmand.setVisibility(View.GONE);
        if(Functions.pob_renew)
            lay_renew.setVisibility(View.GONE);
        if(Functions.pob_tabloo)
            lay_tabloo.setVisibility(View.GONE);
        if(Functions.pob_bussiness)
            lay_bussiness.setVisibility(View.GONE);
        if(Functions.pob_jameh)
            lay_jame.setVisibility(View.GONE);


        set_size(R.id.img_back,.06,.035,"line");
        set_size(R.id.img_next,.06,.035,"line");
        set_size_txt(R.id.lbl_title,.054,"line");



        set_size_txt(R.id.lbl_car_avarez,.045,"line");
        set_size_txt(R.id.lbl_bussienss,.045,"line");
        set_size_txt(R.id.lbl_panel,.045,"line");
        set_size_txt(R.id.lbl_waste,.045,"line");
        set_size_txt(R.id.lbl_renovation,.045,"line");
        set_size_txt(R.id.lbl_jame,.045,"line");

        set_size(R.id.img_car,.15,.15,"line");
        set_size(R.id.img_busssiness,.15,.15,"line");
        set_size(R.id.img_panel,.15,.15,"line");
        set_size(R.id.img_waste,.15,.15,"line");
        set_size(R.id.img_renovation,.15,.15,"line");
        set_size(R.id.img_jame,.15,.15,"line");

        typ = getIntent().getStringExtra("typ");
       // Toast.makeText(this, typ, Toast.LENGTH_SHORT).show();
    }

    public void clk_car_avarez(View view) {
        if(typ.equals("search") && Functions.pob_car) {
            Intent I = new Intent(this, CarSearch.class);
            I.putExtra("typ","car");
            startActivity(I);
      //      finish();
        }
    }
    public void clk_bussiness_avarez(View view) {
        if(typ.equals("search") && Functions.pob_bussiness) {
            Intent I = new Intent(this, CarSearch.class);
            I.putExtra("typ","bussiness");
            startActivity(I);
      //      finish();
        }
    }
    public void clk_tablo_avarez(View view) {
        if(typ.equals("search") && Functions.pob_tabloo) {
            Intent I = new Intent(this, CarSearch.class);
            I.putExtra("typ","tablo");
            startActivity(I);
      //      finish();
        }
    }
    public void clk_pasmand_avarez(View view) {
        if(typ.equals("search") && Functions.pob_pasmand) {
            Intent I = new Intent(this, CarSearch.class);
            I.putExtra("typ","pasmand");
            startActivity(I);
       //     finish();
        }
    }
    public void clk_nosazi_avarez(View view) {
        if(typ.equals("search") && Functions.pob_renew) {
            Intent I = new Intent(this, CarSearch.class);
            I.putExtra("typ","nosazi");
            startActivity(I);
       //     finish();
        }
    }

    public void clk_back(View view) {
        finish();
    }

    public void clk_under_cunstruct(View view) {
        Toast.makeText(this, "در دست طراحی", Toast.LENGTH_SHORT).show();
    }

    public void clk_jame_avarez(View view) {
        if(typ.equals("search") && Functions.pob_jameh) {
            Intent I = new Intent(this, CarSearch.class);
            I.putExtra("typ","jame");
            startActivity(I);
          //  finish();
        }
    }
}
