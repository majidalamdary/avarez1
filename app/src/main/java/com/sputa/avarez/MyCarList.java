package com.sputa.avarez;

import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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

public class MyCarList extends AppCompatActivity {
    private MyAsyncTask mm;
    private String last_requested_query;
    List<items_cars> item =     new ArrayList<>();
    List<items> item1 =     new ArrayList<>();
    List<String> itms=     new ArrayList<>();
    List<String> itms_avarez=     new ArrayList<>();

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView_cars;
    private RecyclerView mRecyclerView;
    private Functions fun;
    LinearLayout lay_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_list);
        mm = new MyAsyncTask();
        last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_my_cars&ID="+Functions.u_id+ "&rdn="+String.valueOf(new Random().nextInt());
        // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();

        mm.url = (last_requested_query);
        mm.execute("");
        lay_main = findViewById(R.id.lay_main);
        fun= new Functions();
    }


    private void show_detail(String s) {
        fun.enableDisableView(lay_main, false);
        RelativeLayout lay_message = findViewById(R.id.lay_message);
        lay_message.setVisibility(View.VISIBLE);
        ConstraintLayout lay_detail = findViewById(R.id.lay_detail);
        lay_detail.setVisibility(View.VISIBLE);

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
                                avarez = new_str;
                                //Toast.makeText(MyCarList.this, avarez, Toast.LENGTH_SHORT).show();
                                item.add(new items_cars(name,pelak,"مبلغ عوارض :"+avarez,carID));
                                itms.add(carID);
                                itms_avarez.add(avarez);






                            }
                        }

                        mRecyclerView_cars = (RecyclerView) findViewById(R.id.my_recycler_cars);
                        mRecyclerView_cars.setHasFixedSize(true);
                        mLayoutManager = new LinearLayoutManager(MyCarList.this);
                        mRecyclerView_cars.setLayoutManager(mLayoutManager);


                        mAdapter = new item_cars_adapter(MyCarList.this,item);
                        mRecyclerView_cars.setAdapter(mAdapter);


                        mRecyclerView_cars.addOnItemTouchListener(
                                new RecyclerItemClickListener(MyCarList.this, mRecyclerView_cars ,new RecyclerItemClickListener.OnItemClickListener() {
                                    @Override public void onItemClick(View view, int position) {
                                        //    Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
                                        // do whatever

                                        show_detail(itms.get(position));

                                    }

                                    @Override public void onLongItemClick(View view, int position) {
                                        // Toast.makeText(NewItem.this, "long Click", Toast.LENGTH_SHORT).show();
                                        // do whatever
                                    }
                                })
                        );


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
