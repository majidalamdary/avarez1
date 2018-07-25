package com.sputa.avarez;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SelectAvarezType extends AppCompatActivity {

    private String typ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_avarez_type);

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
