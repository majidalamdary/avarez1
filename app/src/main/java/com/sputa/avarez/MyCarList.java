package com.sputa.avarez;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sputa.avarez.adapters.item_adapter;
import com.sputa.avarez.adapters.item_cars_adapter;
import com.sputa.avarez.model.items;
import com.sputa.avarez.model.items_cars;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.security.AccessController.getContext;

public class MyCarList extends AppCompatActivity implements RecyclerViewClickListener {
    private MyAsyncTask mm;
    private String last_requested_query;
    List<items_cars> item =     new ArrayList<>();
    List<items> item1 =     new ArrayList<>();
    List<String> itms=     new ArrayList<>();
    List<String> itms_avarez=     new ArrayList<>();
    List<Integer> itms_avarez_number =     new ArrayList<>();

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView_cars;
    private RecyclerView mRecyclerView;
    private Functions fun;
    LinearLayout lay_main;
    public int pos=0;
    private String rslt_MerchantId="";
    private String rslt_TerMinalId="";
    private String rslt_TransactionKey="";
    private String rslt_OrderId="";
    private String rslt_MainProfile="";
    private boolean allowBack=true;

    @Override
    public void recyclerViewListClicked(View v, int position){
        //Toast.makeText(this, String.valueOf(position)+"---"+String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();\
        pos=position;
        if(v.getId()==R.id.img_delete_icon)
        {

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
                            mm = new MyAsyncTask();
                            last_requested_query = getResources().getString(R.string.site_url) + "do?param=del_my_cars&ID="+Functions.u_id+ "&carID="+item.get(pos).getRadif()+"&rdn="+String.valueOf(new Random().nextInt());
                            // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();

                            mm.url = (last_requested_query);
                            mm.execute("");

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
            if(!itms_avarez.get(position).equals("0")) {
                show_detail(itms.get(position));
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_list);
        load_my_cars();
        lay_main = findViewById(R.id.lay_main);
        fun= new Functions();



    }

    private void load_my_cars() {
        mm = new MyAsyncTask();
        last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_my_cars&ID="+Functions.u_id+ "&rdn="+String.valueOf(new Random().nextInt());
        // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();

        mm.url = (last_requested_query);
        mm.execute("");
    }


    private void show_detail(String s) {
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        ConstraintLayout lay_detail = findViewById(R.id.lay_detail);
        lay_detail.setVisibility(View.VISIBLE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.GONE);

        mm = new MyAsyncTask();
        last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_avarez_detail&carID="+s+ "&rdn="+String.valueOf(new Random().nextInt());
        // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();

        mm.url = (last_requested_query);
        mm.execute("");


    }

    public void clk_back(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.GONE);
        ConstraintLayout lay_detail = findViewById(R.id.lay_detail);
        lay_detail.setVisibility(View.GONE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (!allowBack) {


        } else {
            super.onBackPressed();
        }
    }
    public void clk_pay(View view) {



        mm = new MyAsyncTask();
        last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_pay_info&CarID="+item.get(pos).getRadif()+"&rdn="+String.valueOf(new Random().nextInt());
       //  Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();

        mm.url = (last_requested_query);
        mm.execute("");

    }
    private void show_pay() {
        allowBack =false;
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.VISIBLE);
        ConstraintLayout lay_detail = findViewById(R.id.lay_detail);
        lay_detail.setVisibility(View.GONE);
        WebView webview = (WebView) findViewById(R.id.web_view);
        webview.setWebViewClient(new myWebClient());
        webview.getSettings().setJavaScriptEnabled(true);
        //rslt_price="1000";
        String url ="http://e-paytoll.ir/Pages/Common/mobilepayment.aspx?Amount="+String.valueOf(itms_avarez_number.get(pos))+"&AdditionalInfo=" + rslt_MainProfile + "-CTSCar&MerchantID=" + rslt_MerchantId + "&TerminalId=" + rslt_TerMinalId + "&TransactionKey=" + rslt_TransactionKey + "&OrderId=" + rslt_OrderId;

        //Toast.makeText(this, url, Toast.LENGTH_LONG).show();
        fun.Lag(url);
        webview.loadUrl(url);


    }

    public void clk_back_gate(View view) {
        fun.enableDisableView(lay_main, true);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.GONE);
        ConstraintLayout lay_detail = findViewById(R.id.lay_detail);
        lay_detail.setVisibility(View.GONE);
        ConstraintLayout lay_gate = findViewById(R.id.lay_gate);
        lay_gate.setVisibility(View.GONE);
        allowBack =true;
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
                                Toast.makeText(MyCarList.this, "پرداخت الکترونیک برای این شهرداری فعال نمی باشد", Toast.LENGTH_SHORT).show();
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
                            for (int i = 0; i < rslt_hisCount; i++) {
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



                            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler);
                            mRecyclerView.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(MyCarList.this);
                            mRecyclerView.setLayoutManager(mLayoutManager);


                            mAdapter = new item_adapter(MyCarList.this,item1);
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
                             load_my_cars();
                           // Toast.makeText(MyCarList.this, "ok", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (param_str.equals("get_my_cars") ) {
                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");

                    String rslt = ss.substring(start1 + 8, end1);
                    if (!rslt.equals("0")) {

                        start1 = ss.indexOf("<carCount>");
                        end1 = ss.indexOf("</carCount>");
                        rslt = ss.substring(start1 + 10, end1);
                        item.clear();
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

                                itms_avarez_number.add(Integer.valueOf(avarez));
                                avarez = new_str;
                                //Toast.makeText(MyCarList.this, avarez, Toast.LENGTH_SHORT).show();
                                item.add(new items_cars(name,pelak,"مبلغ عوارض :"+avarez,carID));
                                itms.add(carID);
                                itms_avarez.add((avarez));






                            }
                        }

                        mRecyclerView_cars = (RecyclerView) findViewById(R.id.my_recycler_cars);
                        mRecyclerView_cars.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(MyCarList.this);
                        mRecyclerView_cars.setLayoutManager(mLayoutManager);


                        mAdapter = new item_cars_adapter(MyCarList.this,item,MyCarList.this);
                        mRecyclerView_cars.setAdapter(mAdapter);


//                        mRecyclerView_cars.addOnItemTouchListener(
//                                new RecyclerItemClickListener(MyCarList.this, mRecyclerView_cars ,new RecyclerItemClickListener.OnItemClickListener() {
//                                    @Override public void onItemClick(View view, int position) {
//                                       //     Toast.makeText(MyCarList.this, String.valueOf(view.getId()), Toast.LENGTH_SHORT).show();
//                                        // do whatever
//
//                                       // show_detail(itms.get(position));
//
//                                    }
//
//                                    @Override public void onLongItemClick(View view, int position) {
//                                         Toast.makeText(MyCarList.this, "long Click", Toast.LENGTH_SHORT).show();
//                                        // do whatever
//                                    }
//                                })
//                        );


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
