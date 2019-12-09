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

//
//
public class SelectGhabzType extends AppCompatActivity   {
    private int screenWidth;
    private int screenHeight;
    private String typ;

    LinearLayout lay_gas;
    LinearLayout lay_water;
    LinearLayout lay_electric;
    LinearLayout lay_telephone;

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
        setContentView(R.layout.activity_select_ghabz_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        lay_electric=findViewById(R.id.lay_electric_shade);
        lay_water=findViewById(R.id.lay_water_shade);
        lay_gas=findViewById(R.id.lay_gas_shade);
        lay_telephone=findViewById(R.id.lay_telphone_shade);

        lay_water.setVisibility(View.VISIBLE);
        lay_gas.setVisibility(View.VISIBLE);
        lay_electric.setVisibility(View.VISIBLE);
        lay_telephone.setVisibility(View.VISIBLE);

        if(Functions.pob_gas)
            lay_gas.setVisibility(View.GONE);
        if(Functions.pob_water)
            lay_water.setVisibility(View.GONE);
        if(Functions.pob_electric)
            lay_electric.setVisibility(View.GONE);
        if(Functions.pob_telphone) {
            lay_telephone.setVisibility(View.GONE);
//            Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        }



        set_size(R.id.img_back,.06,.035,"line");
        set_size(R.id.img_next,.06,.035,"line");
        set_size_txt(R.id.lbl_title,.05,"line");



        set_size_txt(R.id.lbl_car_avarez,.045,"line");
        set_size_txt(R.id.lbl_water,.045,"line");
        set_size_txt(R.id.lbl_electric,.045,"line");
        set_size_txt(R.id.lbl_tel,.045,"line");


        set_size(R.id.img_gas,.15,.15,"line");
        set_size(R.id.img_electric,.15,.15,"line");
        set_size(R.id.img_water,.15,.15,"line");
        set_size(R.id.img_tel,.15,.15,"line");
        typ = getIntent().getStringExtra("typ");
        // Toast.makeText(this, typ, Toast.LENGTH_SHORT).show();
    }

    public void clk_gas_ghabz(View view) {
        if(typ.equals("search") && Functions.pob_gas) {
            Intent I = new Intent(this, GhabzSearch.class);
            I.putExtra("type","gas");
            startActivity(I);
//            finish();
        }
    }

    public void clk_back(View view) {
        finish();
    }

    public void clk_under_cunstruct(View view) {
        Toast.makeText(this, "در دست طراحی", Toast.LENGTH_SHORT).show();
    }

    public void clk_water(View view) {
        if(typ.equals("search") && Functions.pob_water) {
            Intent I = new Intent(this, GhabzSearch.class);
            I.putExtra("type","water");
            startActivity(I);
//            finish();
        }
    }
    public void clk_electric(View view) {
        if(typ.equals("search") && Functions.pob_electric) {
            Intent I = new Intent(this, GhabzSearch.class);
            I.putExtra("type","electric");
            startActivity(I);
//            finish();
        }
    }
    public void clk_telphone(View view) {
        if(typ.equals("search") && Functions.pob_telphone) {
            Intent I = new Intent(this, GhabzSearch.class);
            I.putExtra("type","telphone");
            startActivity(I);
//            finish();
        }
    }

}
