package com.sputa.avarez;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sputa.avarez.adapters.adapter_bussiness;
import com.sputa.avarez.adapters.item_adapter;
import com.sputa.avarez.adapters.item_eshterak_adapter;
import com.sputa.avarez.classes.CallSoap;
import com.sputa.avarez.model.item_bussiness;
import com.sputa.avarez.model.items;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static com.sputa.avarez.Functions.Lag;

public class MyEshterakList extends AppCompatActivity  implements RecyclerViewClickListener{

    List<items_eshterak> item =     new ArrayList<>();

    List<items> item1 =     new ArrayList<>();

    List<String> itms=     new ArrayList<>();

    List<String> itms_avarez=     new ArrayList<>();

    List<Integer> itms_avarez_number =     new ArrayList<>();
    List<item_bussiness> item_bussinesses = new ArrayList<>();
    String DetailType="";
    LinearLayout lay_main;
    private Functions fun;
    public int NosaziCount=0;
    List<String> NosaziList= new ArrayList<>();
    List<String> NosaziListCode= new ArrayList<>();
    List<String> NosaziListURL= new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView_cars;
    private RecyclerView mRecyclerView;
    private int screenWidth;
    private int screenHeight;
    private int pos;
    private boolean allowBack=true;
    private SQLiteDatabase myDB;
    private MyAsyncTaskService Asy;
    private String pay_type="";
    private String URL="";
    private String UrlForBussiness="";
    private String last_requested_query="";
    private String rslt_MerchantId="";
    private String rslt_TerMinalId="";
    private String rslt_TransactionKey="";
    private String rslt_OrderId="";
    private String rslt_MainProfile="";
    private String IsPaid="0";
    private MyAsyncTask mm;
    private String DeletedNosaziCode="";

    @Override
    public void recyclerViewListClicked(View v, final int position) {

        if(v.getId()==R.id.img_delete_eshterak)
        {
            pos=position;



            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("حذف؟")
                    .setMessage("آیا مطمئن هستید؟")
                    .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete



                            if(item.get(position).getType().equals("nosazi")) {
                                String rad = item.get(position).getRadif();
                                IsPaid=item.get(position).getIsPaid();
                                delete_nosazi(NosaziListCode.get(Integer.valueOf(rad) - 1));
                            }
                            else if(item.get(position).getType().equals("car")) {
                                IsPaid=item.get(position).getIsPaid();
                                delete_car(item.get(position).getRadif());
                            }
                            else if(item.get(position).getType().equals("bussiness")) {
                                IsPaid=item.get(position).getIsPaid();
                                delete_bussiness(item.get(position).getRadif());
                            }
                            else
                            {
                                items_eshterak itm = (item.get(position));
                                myDB.execSQL("delete from MyGhabz where AboneID='"+itm.getTxt_eshterak()+"'");
                                item.clear();
                                load_all_eshterak();
                            }
                        }
                    }).setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete

                }
            })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();



        }
        if(v.getId()==R.id.img_detail)
        {
            pos=position;

            //Toast.makeText(this, "11", Toast.LENGTH_SHORT).show();
            if(item.get(position).getType().equals("nosazi")) {
                String rad = item.get(position).getRadif();

                show_detail_nosazi(NosaziList.get(Integer.valueOf(rad) - 1),NosaziListURL.get(Integer.valueOf(rad) - 1));
            }
            else if(item.get(position).getType().equals("car")) {
//                show_detail_car(itms.get(position));
                show_detail_car(item.get(position).getRadif());
                DetailType="car";
            }
            else if(item.get(position).getType().equals("bussiness")) {
//                show_detail_car(itms.get(position));
                show_detail_bussiness(item.get(position).getRadif());
                DetailType="bussiness";
            }
            else
            {
                show_detail(item.get(position));
            }
        }
    }

    private void delete_nosazi(String s) {
        Asy =new MyAsyncTaskService("DeleteNosazi",s);

        DeletedNosaziCode =s;
        Asy.execute();
    }

    private void delete_car(String s) {
        mm = new MyAsyncTask();
        last_requested_query = getResources().getString(R.string.site_url) + "do?param=del_my_cars&ID="+Functions.u_id+ "&carID="+item.get(pos).getRadif()+"&rdn="+String.valueOf(new Random().nextInt());
        // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();

        mm.url = (last_requested_query);
        mm.execute("");
    }

    private void delete_bussiness(String s) {
        Asy =new MyAsyncTaskService("DeleteBussiness",s);
        Asy.execute();    }

    private void show_detail_car(String s) {
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        LinearLayout lay_detail = findViewById(R.id.lay_detail);
        lay_detail.setVisibility(View.VISIBLE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.GONE);
        LinearLayout lay_header_car = findViewById(R.id.lay_header_car);
        lay_header_car.setVisibility(View.VISIBLE);
        LinearLayout lay_header_bussiness = findViewById(R.id.lay_header_bussiness);
        lay_header_bussiness.setVisibility(View.GONE);
        TextView lbl_header_detail_avarez= findViewById(R.id.lbl_header_detail_avarez);
        lbl_header_detail_avarez.setText("جزئیات مبلغ عوارض خودرو");
        mm = new MyAsyncTask();
        last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_avarez_detail&carID="+s+ "&rdn="+String.valueOf(new Random().nextInt());
        // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();
        Lag(last_requested_query);
        mm.url = (last_requested_query);
        mm.execute("");


    }
    private void show_detail_bussiness(String s) {


        LinearLayout btn_pay_car=findViewById(R.id.btn_pay_car);
        btn_pay_car.setVisibility(View.GONE);
        Asy =new MyAsyncTaskService("ShowBussinessDetail",s);
        Asy.execute();


    }

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
        setContentView(R.layout.activity_my_eshterak_list);


//        SharedPreferences.Editor editor = getSharedPreferences("ghabz", MODE_PRIVATE).edit();
//        editor.putString("ghabz_id1", null);
//
//        editor.putString("ghabz_id2", null);
//
//        editor.putString("ghabz_id3", null);
//
//
//        editor.putString("ghabz_id4", null);
//        editor.apply();
        myDB = openOrCreateDatabase(getString(R.string.DB_name), MODE_PRIVATE, null);


        load_all_eshterak();
        lay_main= findViewById(R.id.lay_main);
        fun=new Functions();


        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE); // the results will be higher than using the activity context object or the getWindowManager() shortcut
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        set_size(R.id.lay_more_detail,.85,.8,"rel");
        set_size(R.id.lay_msg_more_detail,.7,.6,"cons");
        set_size_txt(R.id.lbl_more_detail_title,.045,"cons");
        set_size_txt(R.id.lbl_msg_right_detail,.04,"line");
        set_size_txt(R.id.lbl_msg_left_detail,.04,"line");





    }

    private void load_all_eshterak() {
        load_my_Nosazi();
        load_my_Bussiness();
        load_my_cars();
        load_my_eshterak();
    }

    private void load_my_Nosazi() {
        Asy =new MyAsyncTaskService("GetNosaziList","");
        Asy.execute();
    }
    private void load_my_Bussiness() {
        Asy =new MyAsyncTaskService("GetBussinessList","");
        Asy.execute();
    }

    private String digiting(String string) {
        String new_str = "";
        int j = 0;
        for (int ii = string.length() - 1; ii >= 0; ii--) {
            j++;
            if (j != string.length() && j % 3 == 0)
                new_str = "," + string.charAt(ii) + new_str;
            else
                new_str = string.charAt(ii) + new_str;
        }
        return new_str;
    }
    private void load_my_eshterak() {



        Cursor cr = myDB.rawQuery("select * from MyGhabz ",null);
        if(cr.getCount()>0) cr.moveToFirst();
        while(!cr.isAfterLast()) {
//            Toast.makeText(this, String.valueOf(cr.getCount()), Toast.LENGTH_SHORT).show();
            if(cr.getString(1).equals("gas")) {
                Cursor cr1 = myDB.rawQuery("select * from Gas where AboneID='"+cr.getString(0)+"'", null);
                if(cr1.getCount()>0) {
                    cr1.moveToFirst();
                    item.add(new items_eshterak(cr.getString(0), cr1.getString(2) + " " + cr1.getString(3), digiting(String.format ("%.0f", (cr1.getFloat(12)))), cr1.getString(0), "gas","1"));
                }
            }
            if(cr.getString(1).equals("water")) {
                Cursor cr1 = myDB.rawQuery("select * from water where AboneID='"+cr.getString(0)+"'", null);
                if(cr1.getCount()>0) {
                    cr1.moveToFirst();
                    item.add(new items_eshterak(cr.getString(0), cr1.getString(2) + " " + cr1.getString(3), digiting(String.valueOf((int)(cr1.getFloat(14)))), cr1.getString(0), "water","1"));
                }
            }
            if(cr.getString(1).equals("electric")) {
                    Cursor cr1 = myDB.rawQuery("select * from power where AboneID='"+cr.getString(0)+"'", null);
                if(cr1.getCount()>0) {
                    cr1.moveToFirst();
                    item.add(new items_eshterak(cr.getString(0), cr1.getString(2) + " " + cr1.getString(3), digiting(cr1.getString(11)), cr1.getString(0), "electric","1"));
                }
            }
            if(cr.getString(1).equals("telphone")) {
                Cursor cr1 = myDB.rawQuery("select * from CellPhone where AboneID='"+cr.getString(0)+"'", null);
                if(cr1.getCount()>0) {
                    cr1.moveToFirst();
                    item.add(new items_eshterak(cr.getString(0), cr1.getString(2) + " " + cr1.getString(3), digiting(cr1.getString(9)), cr1.getString(0), "telphone","1"));
                }
            }
            cr.moveToNext();
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new item_eshterak_adapter(this,item,MyEshterakList.this);
        mRecyclerView.setAdapter(mAdapter);

    }


    private void show_detail(items_eshterak s) {
        allowBack=false;
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
        lay_message.setVisibility(View.VISIBLE);
        lay_more_detail.setVisibility(View.VISIBLE);
        TextView txt_left =findViewById(R.id.lbl_msg_left_detail);
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) txt_left.getLayoutParams();
        param.weight = (float) 1.0;
        txt_left.setLayoutParams(param);

        if(s.getType().equals("gas"))
        {
            search_gas_ghabz(s.getTxt_eshterak(),"111");
        }
        if(s.getType().equals("water"))
        {
            search_water_ghabz(s.getTxt_eshterak(),"111");
        }
        if(s.getType().equals("electric"))
        {
            search_electric_ghabz(s.getTxt_eshterak(),"111");
        }
        if(s.getType().equals("telphone"))
        {
            search_telphone_ghabz(s.getTxt_eshterak(),"111");
        }
        pay_type="other";


    }
    public void clk_back_car(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.GONE);
        LinearLayout lay_detail = findViewById(R.id.lay_detail);
        lay_detail.setVisibility(View.GONE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.GONE);
    }
    public void clk_pay_car(View view) {


        if(DetailType.equals("car")) {
            mm = new MyAsyncTask();
            last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_pay_info&CarID=" + item.get(pos).getRadif() + "&rdn=" + String.valueOf(new Random().nextInt());
            //  Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();
            Lag(last_requested_query);
            mm.url = (last_requested_query);
            mm.execute("");
        }
        if(DetailType.equals("bussiness"))
        {
            if(UrlForBussiness.length()>0) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(UrlForBussiness));
                startActivity(i);
                Lag("url=" + UrlForBussiness);
            }

        }


    }
    private void load_my_cars() {
        mm = new MyAsyncTask();
        last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_my_cars&ID="+Functions.u_id+ "&rdn="+String.valueOf(new Random().nextInt());
        // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();
        Lag(last_requested_query);
        mm.url = (last_requested_query);
        mm.execute("");
    }

    private void show_detail_nosazi(String s,String Url) {
        allowBack=false;
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
        lay_message.setVisibility(View.VISIBLE);
        lay_more_detail.setVisibility(View.VISIBLE);

        TextView txt_left =findViewById(R.id.lbl_msg_left_detail);
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) txt_left.getLayoutParams();
        param.weight = (float) 0.0;
        txt_left.setLayoutParams(param);

        TextView txt_right =findViewById(R.id.lbl_msg_right_detail);
        txt_right.setText(s);
        pay_type="nosazi";
        URL=Url;
        if(Url.length()>0) {
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            btn_pay.setVisibility(View.VISIBLE);
        }
        else
        {
            LinearLayout btn_pay = findViewById(R.id.btn_pay);
            btn_pay.setVisibility(View.GONE);
        }

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

    private void search_gas_ghabz(String eshterak, String ghabz) {
        //Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        Cursor cr= myDB.rawQuery("select * from Gas where AboneID='"+eshterak+"'", null);
        if(eshterak.length()>0) {
            cr = myDB.rawQuery("select * from Gas where AboneID='"+eshterak+"'", null);
            Log.d("majid","esht=");
        }
        else if(ghabz.length()>0) {
            cr = myDB.rawQuery("select * from Gas where ID='"+ghabz+"'", null);
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                Log.d("majid","ghabz=");
            }
        }
        if(cr!=null) {
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                String ID= cr.getString(0);
                Log.d("majid","ok");
                cr.moveToFirst();
                String
                        msg = "";

                TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
                TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);

                msg = "";
                msg += "تاریخ قرائت پیشین" + "\n";
                msg += "تاریخ قرائت فعلی" + "\n";
                msg += "رقم پیشین شمارشگر" + "\n";
                msg += "رقم فعلی شمارشگر" + "\n";
                msg += "مصرف به متر مکعب" + "\n";
                msg += "تعداد واحد" + "\n";
                msg += "بهای گاز مصرفی" + "\n";

                lbl_msg_right_detail.setText(msg);
                msg = "";
                msg =" :   "+ cr.getString(5) + "\n" ;
                msg +=" :   "+ cr.getString(6)+ "\n" ;
                msg +=" :   "+ cr.getString(7)+ "\n" ;
                msg +=" :   "+ cr.getString(8)+ "\n" ;
                msg +=" :   "+ cr.getString(9)+ "\n" ;
                msg +=" :   "+ cr.getString(10)+ "  \n" ;

                msg +=" :   "+ digiting(String.format ("%.0f", (cr.getFloat(12))))+ " ریال \n" ;

                lbl_msg_left_detail.setText(msg);

                LinearLayout btn_pay = findViewById(R.id.btn_pay);
                btn_pay.setVisibility(View.VISIBLE);
//                LinearLayout btn_detail = findViewById(R.id.btn_detail);
//                btn_detail.setVisibility(View.VISIBLE);



            }
        }
    }
    private void search_water_ghabz(String eshterak, String ghabz) {
        //Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        Cursor cr= myDB.rawQuery("select * from water where AboneID='"+eshterak+"'", null);
        if(eshterak.length()>0) {
            cr = myDB.rawQuery("select * from water where AboneID='"+eshterak+"'", null);
            Log.d("majid","esht=");
        }
        else if(ghabz.length()>0) {
            cr = myDB.rawQuery("select * from water where ID='"+ghabz+"'", null);
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                Log.d("majid","ghabz=");
            }
        }
        if(cr!=null) {
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                String ID= cr.getString(0);
                Log.d("majid","ok");
                cr.moveToFirst();
                String
                        msg = "";


                TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
                TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);

                msg = "";
                msg += "تاریخ قرائت پیشین" + "\n";
                msg += "تاریخ قرائت فعلی" + "\n";
                msg += "رقم پیشین شمارشگر" + "\n";
                msg += "رقم فعلی شمارشگر" + "\n";
                msg += "مصرف به متر مکعب" + "\n";
                msg += "عوارض فاضلاب" + "\n";
                msg += "مالیات" + "\n";
                msg += "تبصره های قانونی" + "\n";
                msg += "بهای آب مصرفی" + "\n";

                lbl_msg_right_detail.setText(msg);
                msg = "";
                msg =" :   "+ cr.getString(5) + "\n" ;
                msg +=" :   "+ cr.getString(6)+ "\n" ;
                msg +=" :   "+ cr.getString(7)+ "\n" ;
                msg +=" :   "+ cr.getString(8)+ "\n" ;
                msg +=" :   "+ cr.getString(9)+ "\n" ;
                msg +=" :   "+ digiting(cr.getString(10))+ "\n" ;
                msg +=" :   "+ cr.getString(11)+ "\n" ;
                msg +=" :   "+ cr.getString(12)+ "\n" ;
                msg +=" :   "+ digiting(String.valueOf((int)(cr.getFloat(14))))+ " ریال \n" ;

                lbl_msg_left_detail.setText(msg);

                LinearLayout btn_pay = findViewById(R.id.btn_pay);
                btn_pay.setVisibility(View.VISIBLE);




            }
        }
    }
    private void search_electric_ghabz(String eshterak, String ghabz) {
        //Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        Cursor cr= myDB.rawQuery("select * from power where AboneID='"+eshterak+"'", null);
        if(eshterak.length()>0) {
            cr = myDB.rawQuery("select * from power where AboneID='"+eshterak+"'", null);
            Log.d("majid","esht=");
        }
        else if(ghabz.length()>0) {
            cr = myDB.rawQuery("select * from power where ID='"+ghabz+"'", null);
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                Log.d("majid","ghabz=");
            }
        }
        if(cr!=null) {
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                String ID= cr.getString(0);
                Log.d("majid","ok");
                cr.moveToFirst();
                String
                        msg = "";

                TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
                TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);

                msg = "";
                msg += "تاریخ قرائت پیشین" + "\n";
                msg += "تاریخ قرائت فعلی" + "\n";
                msg += "رقم پیشین شمارشگر" + "\n";
                msg += "رقم فعلی شمارشگر" + "\n";
                msg += "مصرف به کیلو وات" + "\n";
                msg += "بهای برق مصرفی" + "\n";

                lbl_msg_right_detail.setText(msg);
                msg = "";
                msg =" :   "+ cr.getString(5) + "\n" ;
                msg +=" :   "+ cr.getString(6)+ "\n" ;
                msg +=" :   "+ cr.getString(7)+ "\n" ;
                msg +=" :   "+ cr.getString(8)+ "\n" ;
                msg +=" :   "+ cr.getString(9)+ "\n" ;
                msg +=" :   "+ digiting(cr.getString(11))+ " ریال \n" ;

                lbl_msg_left_detail.setText(msg);

                LinearLayout btn_pay = findViewById(R.id.btn_pay);
                btn_pay.setVisibility(View.VISIBLE);




            }
        }
    }
    private void search_telphone_ghabz(String eshterak, String ghabz) {
        //Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
        Cursor cr= myDB.rawQuery("select * from CellPhone where AboneID='"+eshterak+"'", null);
        if(eshterak.length()>0) {
            cr = myDB.rawQuery("select * from CellPhone where AboneID='"+eshterak+"'", null);
            Log.d("majid","esht=");
        }
        else if(ghabz.length()>0) {
            cr = myDB.rawQuery("select * from CellPhone where ID='"+ghabz+"'", null);
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                Log.d("majid","ghabz=");
            }
        }
        if(cr!=null) {
            if (cr.getCount() > 0) {
                cr.moveToFirst();
                String ID= cr.getString(0);
                Log.d("majid","ok");
                cr.moveToFirst();
                String
                        msg = "";

                TextView lbl_msg_right_detail=findViewById(R.id.lbl_msg_right_detail);
                TextView lbl_msg_left_detail=findViewById(R.id.lbl_msg_left_detail);

                msg = "";
                msg += "تاریخ پیشین" + "\n";
                msg += "تاریخ فعلی" + "\n";

                msg += "مصرف به دقیقه" + "\n";
                msg += "بهای مکالمه با تلفن" + "\n";

                lbl_msg_right_detail.setText(msg);
                msg = "";
                msg =" :   "+ cr.getString(5) + "\n" ;
                msg +=" :   "+ cr.getString(6)+ "\n" ;
                msg +=" :   "+ cr.getString(7)+ "\n" ;
                msg +=" :   "+ digiting(cr.getString(9))+ " ریال \n" ;

                lbl_msg_left_detail.setText(msg);

                LinearLayout btn_pay = findViewById(R.id.btn_pay);
                btn_pay.setVisibility(View.VISIBLE);


            }
        }
    }





    public void clk_pay(View view) {

        if(pay_type.equals("other")) {
            show_pay();
        }
        else if(pay_type.equals("nosazi")) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
            startActivity(i);
            Lag("url="+ URL);
        }

    }
    public void clk_msg(View view) {
//        fun.enableDisableView(lay_main, true);
//        RelativeLayout lay_message = findViewById(R.id.lay_message);
//        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
//        lay_message.setVisibility(View.GONE);
//        lay_more_detail.setVisibility(View.GONE);
    }
    private void show_pay() {
        allowBack =false;
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.VISIBLE);
        ConstraintLayout lay_detail = findViewById(R.id.lay_more_detail);
        lay_detail.setVisibility(View.GONE);
        WebView webview = (WebView) findViewById(R.id.web_view);
        webview.setWebViewClient(new myWebClient());
        webview.getSettings().setJavaScriptEnabled(true);
        //rslt_price="1000";
//        String tmp_price =item.get(pos).getTxt_price(),price=String.valueOf(itms_avarez_number.get(pos)) ;
        String price=String.valueOf(itms_avarez_number.get(pos)) ;
        //Toast.makeText(this, String.valueOf(pos), Toast.LENGTH_SHORT).show();
//        for(int i=0;i<tmp_price.length();i++)
//        {
//            if(tmp_price.charAt(i)!=',')
//            {
//                price+=tmp_price.charAt(i);
//            }
//        }

        String url ="http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount="+price+"&AdditionalInfo=10000089-CTSCar&MerchantID=118088384&TerminalId=17995091&TransactionKey=AZ24JJ95SS&OrderId=10000089235123552";
        Lag("Url="+url);
    //Toast.makeText(this, url    , Toast.LENGTH_LONG).show();
        webview.loadUrl(url);



    }
    @Override
    public void onBackPressed() {
        if (!allowBack) {


        } else {
            super.onBackPressed();
        }
    }
    public void clk_back_gate(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.GONE);
        ConstraintLayout lay_detail = findViewById(R.id.lay_more_detail);
        lay_detail.setVisibility(View.GONE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.GONE);
        allowBack =true;
    }

    public void clk_back(View view) {
        allowBack = true;
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        ConstraintLayout lay_more_detail = findViewById(R.id.lay_more_detail);
        lay_message.setVisibility(View.GONE);
        lay_more_detail.setVisibility(View.GONE);
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
            // Toast.makeText(MyCarList.this, ss, Toast.LENGTH_SHORT).show();
            if(end1>0) {
                param_str = ss.substring(start1 + 7, end1);
                // Toast.makeText(DrawerTest.this, param_str, Toast.LENGTH_SHORT).show();
                if (param_str.equals("get_pay_info") ) {

                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");
                    if(end1>0) {
                        String rslt = ss.substring(start1 + 8, end1);
                        if (!rslt.equals("0")) {
                            if(rslt.equals("not"))
                            {
                                Toast.makeText(MyEshterakList.this, "پرداخت الکترونیک برای این شهرداری فعال نمی باشد", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                start1 = ss.indexOf("<MerchantId>");
                                end1 = ss.indexOf("</MerchantId>");
                                rslt_MerchantId = ss.substring(start1 + 12, end1);
                                start1 = ss.indexOf("<TerMinalId>");
                                end1 = ss.indexOf("</TerMinalId>");
                                rslt_TerMinalId = ss.substring(start1 + 12, end1);
                                start1 = ss.indexOf("<TransactionKey>");
                                end1 = ss.indexOf("</TransactionKey>");
                                rslt_TransactionKey = ss.substring(start1 + 16, end1);
                                start1 = rslt.indexOf("<OrderId>");
                                end1 = rslt.indexOf("</OrderId>");
                                rslt_OrderId = rslt.substring(start1 + 9, end1);
                                start1 = rslt.indexOf("<MainProfile>");
                                end1 = rslt.indexOf("</MainProfile>");
                                rslt_MainProfile = rslt.substring(start1 + 13, end1);
                                //Toast.makeText(MyCarList.this, "--"+rslt_MainProfile+"--", Toast.LENGTH_SHORT).show();


                                // Toast.makeText(MyCarList.this, ss, Toast.LENGTH_SHORT).show();
                                show_pay();

                            }
                        }
                    }
                }
                if (param_str.equals("get_avarez_detail") ) {
                    //   Toast.makeText(MyCarList.this, "222", Toast.LENGTH_SHORT).show();
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");

                    String rslt = ss.substring(start1 + 8, end1);
                    if (!rslt.equals("0")) {
                        start1 = rslt.indexOf("<hisCount>");
                        end1 = rslt.indexOf("</hisCount>");
                        int rslt_hisCount = Integer.valueOf(rslt.substring(start1 + 10, end1));
                        //Toast.makeText(CarSearch.this, String.valueOf(rslt_hisCount), Toast.LENGTH_SHORT).show();
                        item1.clear();
                        if(rslt_hisCount>0) {
                            for (int i = rslt_hisCount-1; i >=0 ; i--) {
                                start1 = rslt.indexOf("<his" + String.valueOf(i) + ">");
                                end1 = rslt.indexOf("</his" + String.valueOf(i) + ">");
                                String rslt_hisItems = (rslt.substring(start1 + 6, end1));
                                start1 = rslt_hisItems.indexOf("<year>");
                                end1 = rslt_hisItems.indexOf("</year>");
                                String rslt_year = (rslt_hisItems.substring(start1 + 6, end1));
                                start1 = rslt_hisItems.indexOf("<avarez>");
                                end1 = rslt_hisItems.indexOf("</avarez>");
                                String rslt_avarez = (rslt_hisItems.substring(start1 + 8, end1));
                                String new_str = "";
                                int j = 0;
                                for (int ii = rslt_avarez.length() - 1; ii >= 0; ii--) {
                                    j++;
                                    if (j != rslt_avarez.length() && j % 3 == 0)
                                        new_str = "," + rslt_avarez.charAt(ii) + new_str;
                                    else
                                        new_str = rslt_avarez.charAt(ii) + new_str;
                                }
                                rslt_avarez = new_str;
                                start1 = rslt_hisItems.indexOf("<farsudegi>");
                                end1 = rslt_hisItems.indexOf("</farsudegi>");
                                String rslt_farsudegi = (rslt_hisItems.substring(start1 + 11, end1));
                                new_str = "";
                                j = 0;
                                for (int ii = rslt_farsudegi.length() - 1; ii >= 0; ii--) {
                                    j++;
                                    if (j != rslt_farsudegi.length() && j % 3 == 0)
                                        new_str = "," + rslt_farsudegi.charAt(ii) + new_str;
                                    else
                                        new_str = rslt_farsudegi.charAt(ii) + new_str;
                                }
                                rslt_farsudegi = new_str;
                                start1 = rslt_hisItems.indexOf("<ratePunish>");
                                end1 = rslt_hisItems.indexOf("</ratePunish>");
                                String rslt_ratePunish = (rslt_hisItems.substring(start1 + 12, end1));
                                start1 = rslt_hisItems.indexOf("<Punish>");
                                end1 = rslt_hisItems.indexOf("</Punish>");
                                String rslt_Punish = (rslt_hisItems.substring(start1 + 8, end1));
                                new_str = "";
                                j = 0;
                                for (int ii = rslt_Punish.length() - 1; ii >= 0; ii--) {
                                    j++;
                                    if (j != rslt_Punish.length() && j % 3 == 0)
                                        new_str = "," + rslt_Punish.charAt(ii) + new_str;
                                    else
                                        new_str = rslt_Punish.charAt(ii) + new_str;
                                }
                                rslt_Punish = new_str;
                                item1.add(new items(rslt_year, rslt_avarez, rslt_farsudegi, rslt_ratePunish, rslt_Punish, "11"));
                            }
                            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_car);
                            mRecyclerView.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(MyEshterakList.this);
                            mRecyclerView.setLayoutManager(mLayoutManager);


                            mAdapter = new item_adapter(MyEshterakList.this,item1);
                            mRecyclerView.setAdapter(mAdapter);

                        }
                    }


                }
                if (param_str.equals("del_my_cars") ) {
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");
                    if(end1>0) {

                        String rslt = ss.substring(start1 + 8, end1);
                        if (!rslt.equals("0")) {

                            SharedPreferences prefs = getSharedPreferences(getString(R.string.MyBills), MODE_PRIVATE);
                            String eshterakCar = prefs.getString("CountCar", null);

                            if (eshterakCar != null) {
                                if(!eshterakCar.equals("0"))
                                {
                                    int iEsterakcar =Integer.valueOf(eshterakCar);
                                    iEsterakcar--;
                                    SharedPreferences.Editor editor = getSharedPreferences(getString(R.string.MyBills), MODE_PRIVATE).edit();
                                    editor.putString("CountCar", String.valueOf(iEsterakcar));
                                    editor.apply();
                                }
                            }


                            item.clear();
                            load_all_eshterak();
                            // Toast.makeText(MyCarList.this, "ok", Toast.LENGTH_SHORT).show();
                        }
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
                        if(!rslt.equals("0"))
                        {
                            int cnt = Integer.valueOf(rslt);
                            for(int i=1;i<=cnt;i++)
                            {
                                start1 = ss.indexOf("<car"+String.valueOf(i)+">");
                                end1 = ss.indexOf("</car"+String.valueOf(i)+">");
                                rslt = ss.substring(start1 + 6, end1);
                                start1 = rslt.indexOf("<name>");
                                end1 = rslt.indexOf("</name>");
                                String name  = rslt.substring(start1 + 6, end1);
                                start1 = rslt.indexOf("<carID>");
                                end1 = rslt.indexOf("</carID>");
                                String carID  = rslt.substring(start1 + 7, end1);
                                start1 = rslt.indexOf("<pelak>");
                                end1 = rslt.indexOf("</pelak>");
                                String pelak  = rslt.substring(start1 + 7, end1);

                                start1 = rslt.indexOf("<avarez>");
                                end1 = rslt.indexOf("</avarez>");
                                String avarez  = rslt.substring(start1 + 8, end1);
                                String new_str = "";
                                int j = 0;
                                for (int ii = avarez.length() - 1; ii >= 0; ii--) {
                                    j++;
                                    if (j != avarez.length() && j % 3 == 0)
                                        new_str = "," + avarez.charAt(ii) + new_str;
                                    else
                                        new_str = avarez.charAt(ii) + new_str;
                                }
                                String
                                        isPaid ="0";
                                if(avarez.length()>1)
                                    isPaid="1";
                                itms_avarez_number.add(Integer.valueOf(avarez));
                                avarez = new_str;
                                item.add(new items_eshterak(name,pelak,"مبلغ عوارض :"+avarez,carID,"car",isPaid));
                                itms.add(carID);
                                itms_avarez.add((avarez));






                            }
                        }


                        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
                        mRecyclerView.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(MyEshterakList.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);


                        mAdapter = new item_eshterak_adapter(MyEshterakList.this,item,MyEshterakList.this);
                        mRecyclerView.setAdapter(mAdapter);


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
            if(Type.equals("GetBussinessList")) {
                GetBussinessListResult();
            }
            if(Type.equals("ShowBussinessDetail")) {
                GetInfoBussinessByBussinessIdResult();
            }
            if(Type.equals("GetInfoNosazi")) {

                GetInfoNosaziResult();
            }
            if(Type.equals("DeleteNosazi"))
            {
                DeleteNosaziResult();
            }
            if(Type.equals("DeleteBussiness"))
            {
                DeleteBussinessResult();
            }
        }
        private void GetBussinessListResult() {


            if(resp.length()>0)
            {
                String
                        nosaziCode="";

                int start1 = resp.indexOf("<cnt>");
                int end1 = resp.indexOf("</cnt>");
                if(end1>0) {
                    int
                            cnt = Integer.valueOf(resp.substring(start1 + 5, end1));
                    myDB.execSQL("delete from MyBussiness ");
                    for (int i = 1; i <= cnt; i++) {
                        start1 = resp.indexOf("<bussiness" + i + ">");
                        end1 = resp.indexOf("</bussiness" + i + ">");
                        String bussiness = resp.substring(start1 + 10 + String.valueOf(cnt).length(), end1);
                        start1 = bussiness.indexOf("<bussinessId>");
                        end1 = bussiness.indexOf("</bussinessId>");
                        String bussinessId = bussiness.substring(start1 + 13, end1);
                        start1 = bussiness.indexOf("<bussinessName>");
                        end1 = bussiness.indexOf("</bussinessName>");
                        String bussinessName = bussiness.substring(start1 + 15, end1);
                        start1 = bussiness.indexOf("<AvarezPrice>");
                        end1 = bussiness.indexOf("</AvarezPrice>");
                        String avarezPrice = bussiness.substring(start1 + 13, end1);


                        String paidType = "NotPaid";
                        if (avarezPrice.length() > 1)
                            paidType = "Paid";

                        myDB.execSQL("insert into MyBussiness (BussinessId,Type) values ('" + bussiness + "','" + paidType + "') ");
                        item.add(new items_eshterak(bussinessId, "" + bussinessName, Functions.Cur(avarezPrice), bussinessId, "bussiness", paidType));

                    }


                    NosaziListCode.add(Param);
                    NosaziListURL.add(URL);
                    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(MyEshterakList.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
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
        private void DeleteNosaziResult() {
            if (resp.equals("error")) {
                Toast.makeText(MyEshterakList.this, "خطایی رخ داده است لطفا کد نوسازی و ارتباط اینترنت را بررسی نمایید", Toast.LENGTH_SHORT).show();
            }
            else {
                if(IsPaid.equals("1"))
                    myDB.execSQL("delete from MyNosazi where NosaziID='"+DeletedNosaziCode+"'");
                item.clear();
                load_all_eshterak();
            }
        }
        private void GetInfoNosaziResult() {
            int
                    test_res = resp.indexOf("PaymentID");


            if (resp.equals("error")) {
                Toast.makeText(MyEshterakList.this, "خطایی رخ داده است لطفا کد نوسازی و ارتباط اینترنت را بررسی نمایید", Toast.LENGTH_SHORT).show();
            }
            String isPaid ="0";
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
                        isPaid="1";

                    } else {

                        pri = "پرداخت شده";
                    }

                    start1 = resp.indexOf(";");
                    msg += resp.substring(start1 + 1);
                    NosaziCount++;




                    String ShG=NosaziBillID;
                    String ShP=NosaziPaymentID;
                    String Web="nosazi";
                    pay_type="nosazi";

                    UUID AID = UUID.randomUUID();


                    String AtuhIn =ShG+ShP+Web+AID.toString();

                    String Atuh=SetAtuh(AtuhIn);
                    String
                            URL="";

                    if(NosaziPaymentID.length()>0) {
                        URL = "http://testkasbapp.urmia.ir/Payment2.aspx?ShG=" + NosaziBillID + "&ShP=" + NosaziPaymentID + "&web=nosazi&AID=" + AID.toString() + "&Auth=" + Atuh;
                    }
                    //Lag("UrLLL="+URL+"--");




                    //            Toast.makeText(MyEshterakList.this, resp, Toast.LENGTH_SHORT).show();
                    item.add(new items_eshterak("","کد نوسازی:"+Param ,pri,String.valueOf(NosaziCount),  "nosazi",isPaid));
                    NosaziList.add(msg);
                    NosaziListCode.add(Param);
                    NosaziListURL.add(URL);
                    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(MyEshterakList.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);


                    mAdapter = new item_eshterak_adapter(MyEshterakList.this,item,MyEshterakList.this);
                    mRecyclerView.setAdapter(mAdapter);

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
            if(Type.equals("ShowBussinessDetail")) {
                GetInfoBussinessByBussinessId(Param);
            }
            if(Type.equals("GetInfoNosazi"))
            {
                GetInfoNosazi(Param);
            }
            if(Type.equals("DeleteNosazi"))
            {
                DeleteNosazi(Param);
            }
            if(Type.equals("GetBussinessList")) {
                GetBussinessList();
            }
            if(Type.equals("DeleteBussiness"))
            {
                DeleteBussiness(Param);
            }

            return null;
        }

        private void DeleteNosazi(String param) {
            CallSoap cs;



            try{
                cs = new CallSoap();
                resp=cs.Call_Nosazi_DeleteNosazi(param);

                Lag("Res_del="+resp);
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());

            }
        }
        private void DeleteBussiness(String param) {
            CallSoap cs;



            try{
                cs = new CallSoap();
                resp=cs.Call_Nosazi_DeleteBussiness(param);

                Lag("Res_del="+resp);
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());

            }
        }
        private void DeleteBussinessResult() {



            try{
                if(resp.equals("true"))
                {
                    Lag("okokokok");
                    String sql= "delete from MyBussiness where BussinessId='"+Param+"'";
                    Lag(sql);
                    myDB.execSQL(sql);
                    item.clear();
                    load_all_eshterak();
                }
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());

            }
        }

        private void GetInfoNosazi(String PnosaziKodem) {




            CallSoap cs;

            UUID AID = UUID.randomUUID();
            String AtuhIn =PnosaziKodem+AID;
            String Atuh=SetAtuh(AtuhIn);

            try{
                cs = new CallSoap();
                resp=cs.Call_Nosazi_GetInfo(PnosaziKodem, AID,Atuh);

                Lag("Res="+resp);
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());

            }
        }
        private void GetInfoBussinessByBussinessId(String bussinessId) {


            CallSoap cs;


            try{
                cs = new CallSoap();
                resp=cs.Call_Bussiness_GetInfo_ByBussinessId(bussinessId);

                Lag("Res="+resp);
            }catch(Exception ex)
            {
                Lag( "err:  " + ex.toString());

            }
        }
        private void GetInfoBussinessByBussinessIdResult() {

            fun.enableDisableView(lay_main, false);
            RelativeLayout lay_message = findViewById(R.id.lay_message);
            lay_message.setVisibility(View.VISIBLE);
            LinearLayout lay_detail = findViewById(R.id.lay_detail);
            lay_detail.setVisibility(View.VISIBLE);
            ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
            lay_gate.setVisibility(View.GONE);
            LinearLayout lay_header_car = findViewById(R.id.lay_header_car);
            lay_header_car.setVisibility(View.GONE);
            LinearLayout lay_header_bussiness = findViewById(R.id.lay_header_bussiness);
            lay_header_bussiness.setVisibility(View.VISIBLE);
            TextView lbl_header_detail_avarez = findViewById(R.id.lbl_header_detail_avarez);
            String result = resp;



            lbl_header_detail_avarez.setText("جزئیات مبلغ عوارض کسب و کار");

            int start1 = result.indexOf("<cnt>");
            int end1 = result.indexOf("</cnt>");
            String price="0";
            if(end1>0) {
                String cnt = result.substring(start1 + 5, end1);
                start1 = result.indexOf("<AvarezPrice>");
                end1 = result.indexOf("</AvarezPrice>");
                String AvarezPrice = result.substring(start1 + 13 , end1);
                price = AvarezPrice;



                item_bussinesses.clear();
                for (int i = Integer.valueOf(cnt); i >= 1; i--) {
                    start1 = result.indexOf("<detail" + i + ">");
                    end1 = result.indexOf("</detail" + i + ">");
                    String detail = result.substring(start1 + 8 + cnt.length(), end1);

                    start1 = detail.indexOf("<AvarezYear>");
                    end1 = detail.indexOf("</AvarezYear>");
                    String avarezYear = detail.substring(start1 + 12, end1);

                    start1 = detail.indexOf("<AvarezRate>");
                    end1 = detail.indexOf("</AvarezRate>");
                    String penaltyRate = detail.substring(start1 + 12, end1);

                    start1 = detail.indexOf("<Price>");
                    end1 = detail.indexOf("</Price>");
                    String avarezPrice = detail.substring(start1 + 7, end1);
                    start1 = detail.indexOf("<lateAvarez>");
                    end1 = detail.indexOf("</lateAvarez>");
                    String lateAvarez = detail.substring(start1 + 12, end1);

                    start1 = detail.indexOf("<Service>");
                    end1 = detail.indexOf("</Service>");
                    String servicePrice = detail.substring(start1 + 9, end1);

                    start1 = detail.indexOf("<ServiceLate>");
                    end1 = detail.indexOf("</ServiceLate>");
                    String serviceLate = detail.substring(start1 + 13, end1);
                    start1 = detail.indexOf("<LocalService>");
                    end1 = detail.indexOf("</LocalService>");
                    String LocalService = detail.substring(start1 + 14, end1);

                    start1 = detail.indexOf("<LocalServiceLate>");
                    end1 = detail.indexOf("</LocalServiceLate>");
                    String LocalServiceLate = detail.substring(start1 + 18, end1);

                    item_bussinesses.add(new item_bussiness(avarezYear, penaltyRate, avarezPrice, lateAvarez, servicePrice, serviceLate,LocalService,LocalServiceLate));
                }

                UrlForBussiness="";
                start1 = result.indexOf("<merchantId>");
                end1 = result.indexOf("</merchantId>");
                if (end1 > 0) {
                    String merchantId = result.substring(start1 + 12, end1);
                    start1 = result.indexOf("<terminalId>");
                    end1 = result.indexOf("</terminalId>");
                    String terminalId = result.substring(start1 + 12, end1);
                    start1 = result.indexOf("<transactionKey>");
                    end1 = result.indexOf("</transactionKey>");
                    String transactionKey = result.substring(start1 + 16, end1);
                    start1 = result.indexOf("<orderId>");
                    end1 = result.indexOf("</orderId>");
                    String orderId = result.substring(start1 + 9, end1);
                    start1 = result.indexOf("<cityProfileNumberMain>");
                    end1 = result.indexOf("</cityProfileNumberMain>");
                    String cityProfileNumberMain = result.substring(start1 + 23, end1);

                    LinearLayout btn_pay_car = findViewById(R.id.btn_pay_car);
                    btn_pay_car.setVisibility(View.VISIBLE);
                    String url ="http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount=" + price + "&AdditionalInfo=" + cityProfileNumberMain + "-BTS&MerchantID=" + merchantId + "&TerminalId=" + terminalId + "&TransactionKey=" + transactionKey + "&OrderId=" + orderId;
                    UrlForBussiness=url;
                }

                mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_car);
                mRecyclerView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(MyEshterakList.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new adapter_bussiness(MyEshterakList.this, item_bussinesses);
                mRecyclerView.setAdapter(mAdapter);

            }
//            Toast.makeText(MyEshterakList.this, resp, Toast.LENGTH_SHORT).show();
        }
        private void GetInfoBussinessResult() {
            int
                    test_res = resp.indexOf("result");
            fun.enableDisableView(lay_main, true);
            RelativeLayout lay_message = findViewById(R.id.lay_message);
            lay_message.setVisibility(View.GONE);
            LinearLayout lay_wait = findViewById(R.id.lay_wait);
            lay_wait.setVisibility(View.GONE);

            int
                    start1 = resp.indexOf("<result>");
            int
                    end1 = resp.indexOf("</result>");
            String result = "";
            if (end1 > 7) {

                result = resp.substring(start1 + 8, end1);
            }
            if(result.equals("error"))
            {
                Toast.makeText(MyEshterakList.this, "خطایی رخ داده است لطفا اطلاعات ورودی و ارتباط اینترنت را بررسی نمایید", Toast.LENGTH_SHORT).show();
                return;
            }
            if(test_res>0) {


                start1 = result.indexOf("<bussinessId>");
                end1   = result.indexOf("</bussinessId>");
                if(end1>0) {
                    Lag("rrr="+result);

                    LinearLayout btn_pay = findViewById(R.id.btn_pay);
                    TextView lbl_price = findViewById(R.id.lbl_price);
                    TextView lbl_msg = findViewById(R.id.lbl_msg);
                    btn_pay.setVisibility(View.VISIBLE);
                    lbl_price.setVisibility(View.VISIBLE);
                    LinearLayout btn_detail = findViewById(R.id.btn_detail);
                    btn_detail.setVisibility(View.VISIBLE);
                    String
                            msg = "";
                    String bussinessId = result.substring(start1 + 13, end1);
                    start1 = result.indexOf("<fullName>");
                    end1 = result.indexOf("</fullName>");
                    String fullName = result.substring(start1 + 10, end1);
                    start1 = result.indexOf("<bussinessName>");
                    end1 = result.indexOf("</bussinessName>");
                    String bussinessName = result.substring(start1 + 15, end1);
                    start1 = result.indexOf("<AvarezPrice>");
                    end1 = result.indexOf("</AvarezPrice>");
                    String AvarezPrice = result.substring(start1 + 13, end1);
                    start1 = result.indexOf("<cityCouncilId>");
                    end1 = result.indexOf("</cityCouncilId>");
                    String cityCouncilId = result.substring(start1 + 15, end1);
                    start1 = result.indexOf("<cityProfileNumberMain>");
                    end1 = result.indexOf("</cityProfileNumberMain>");
                    String cityProfileNumberMain = result.substring(start1 + 23, end1);
                    start1 = result.indexOf("<merchantId>");
                    end1 = result.indexOf("</merchantId>");
                    String merchantId = result.substring(start1 + 12, end1);
                    start1 = result.indexOf("<terminalId>");
                    end1 = result.indexOf("</terminalId>");
                    String terminalId = result.substring(start1 + 12, end1);
                    start1 = result.indexOf("<transactionKey>");
                    end1 = result.indexOf("</transactionKey>");
                    String transactionKey = result.substring(start1 + 16, end1);
                    start1 = result.indexOf("<orderId>");
                    end1 = result.indexOf("</orderId>");
                    String orderId = result.substring(start1 + 9, end1);


                    rslt_MerchantId = merchantId;
                    rslt_TerMinalId = terminalId;
                    rslt_TransactionKey = transactionKey;
                    rslt_OrderId = orderId;
                    rslt_MainProfile = cityProfileNumberMain;
//                    rslt_CanEPay = "1";
//                    rslt_price = AvarezPrice;
//                    BussinessId = bussinessId;
                    start1 = result.indexOf("<cnt>");
                    end1 = result.indexOf("</cnt>");
                    String cnt = result.substring(start1 + 5, end1);
//                    item_bussinesses.clear();
                    for (int i = Integer.valueOf(cnt); i >= 1; i--) {
                        start1 = result.indexOf("<detail" + i + ">");
                        end1 = result.indexOf("</detail" + i + ">");
                        String detail = result.substring(start1 + 8 + cnt.length(), end1);

                        start1 = detail.indexOf("<AvarezYear>");
                        end1 = detail.indexOf("</AvarezYear>");
                        String avarezYear = detail.substring(start1 + 12, end1);

                        start1 = detail.indexOf("<AvarezRate>");
                        end1 = detail.indexOf("</AvarezRate>");
                        String penaltyRate = detail.substring(start1 + 12, end1);

                        start1 = detail.indexOf("<Price>");
                        end1 = detail.indexOf("</Price>");
                        String avarezPrice = detail.substring(start1 + 7, end1);

                        start1 = detail.indexOf("<lateAvarez>");
                        end1 = detail.indexOf("</lateAvarez>");
                        String lateAvarez = detail.substring(start1 + 12, end1);

                        start1 = detail.indexOf("<Service>");
                        end1 = detail.indexOf("</Service>");
                        String servicePrice = detail.substring(start1 + 9, end1);

                        start1 = detail.indexOf("<LocalService>");
                        end1 = detail.indexOf("</LocalService>");
                        String serviceLate = detail.substring(start1 + 14, end1);


//                        item_bussinesses.add(new item_bussiness(avarezYear, penaltyRate, avarezPrice, lateAvarez, servicePrice, serviceLate));
                    }


                    msg = "نام :" + fullName + "\n";
                    msg += "نام کسب و کار : " + bussinessName + "\n";


                    lbl_price.setText("مبلغ عوارض : " + AvarezPrice);
                    lbl_msg.setText(msg);

                    LinearLayout lay_header_bussiness = findViewById(R.id.lay_header_car);
                    LinearLayout lay_header_pasmand = findViewById(R.id.lay_header_pasmand);
                    LinearLayout lay_header_nosazi = findViewById(R.id.lay_header_nosazi);
                    lay_header_nosazi.setVisibility(View.INVISIBLE);
                    lay_header_bussiness.setVisibility(View.VISIBLE);
                    lay_header_pasmand.setVisibility(View.INVISIBLE);

                    mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_parvandeh);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(MyEshterakList.this);
                    mRecyclerView.setLayoutManager(mLayoutManager);
//                    mAdapter = new adapter_bussiness(MyEshterakList.this, item_bussinesses);
                    mRecyclerView.setAdapter(mAdapter);
                }
                else
                {
                    Toast.makeText(MyEshterakList.this, "کسب و کار مورد نظر پیدانشد!", Toast.LENGTH_SHORT).show();
                }
            }
        }

        private void GetBussinessList() {




            CallSoap cs;
            //String PnosaziKodem="1-11-40-7-0-0-0";



            try{
                cs = new CallSoap();
                resp=cs.Call_Bussiness_GetBussinessList();

                Lag("Res=1"+resp);
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

    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onLoadResource(WebView  view, String  url){


            TextView txt=findViewById(R.id.txt_url);
            txt.setText(view.getUrl());
        }
    }

}
