package com.sputa.avarez;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sputa.avarez.classes.customFont;

public class SelectAvarezType extends AppCompatActivity   {

    private String typ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avarez_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);

        setSupportActionBar(toolbar);





        typ = getIntent().getStringExtra("typ");

    }

    public void clk_car_avarez(View view) {
        if(typ.equals("search")) {
            Intent I = new Intent(this, CarSearch.class);
            startActivity(I);
            finish();
        }
        else if(typ.equals("tracking")) {
            Intent I = new Intent(this, Tracking.class);
            startActivity(I);
            finish();
        }

    }

    public void clk_back(View view) {
        finish();
    }
}
