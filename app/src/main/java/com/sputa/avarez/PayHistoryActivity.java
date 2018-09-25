package com.sputa.avarez;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sputa.avarez.adapters.item_adapter;
import com.sputa.avarez.adapters.item_cars_adapter;
import com.sputa.avarez.adapters.item_pay_history_adapter;
import com.sputa.avarez.model.item_pay_history;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PayHistoryActivity extends AppCompatActivity implements RecyclerViewClickListener {
    private MyAsyncTask mm;
    private String last_requested_query;
    List<item_pay_history> item =     new ArrayList<>();
    List<String> itms=     new ArrayList<>();


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView_cars;
    private RecyclerView mRecyclerView;
    private Functions fun;
    LinearLayout lay_main;

    @Override
    public void recyclerViewListClicked(View v, int position){
        //Toast.makeText(this, String.valueOf(position)+"---"+String.valueOf(v.getId()), Toast.LENGTH_SHORT).show();\

        if(v.getId()==R.id.img_delete_icon)
        {




        }
        if(v.getId()==R.id.img_detail)
        {

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_history);
        load_pay_history();
    }
    private void load_pay_history() {
        mm = new MyAsyncTask();
        last_requested_query = getResources().getString(R.string.site_url) + "do?param=get_pay_history&ID="+Functions.u_id+ "&rdn="+String.valueOf(new Random().nextInt());
        // Toast.makeText(getBaseContext(),last_requested_query,Toast.LENGTH_LONG).show();

        mm.url = (last_requested_query);
        mm.execute("");
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
                    start = ss.indexOf("<output>");
            int
                    end = ss.indexOf("</output>");
            String
                    output_str = "";
            String
                    param_str = "";


            int
                    start1 = ss.indexOf("<param>");
            int
                    end1 = ss.indexOf("</param>");
             //Toast.makeText(PayHistoryActivity.this, ss, Toast.LENGTH_SHORT).show();
            if (end1 > 0) {
                param_str = ss.substring(start1 + 7, end1);
                // Toast.makeText(DrawerTest.this, param_str, Toast.LENGTH_SHORT).show();
                if (param_str.equals("get_pay_history")) {

                    start1 = ss.indexOf("<result>");
                    end1 = ss.indexOf("</result>");
                    if (end1 > 0) {
                        String rslt = ss.substring(start1 + 8, end1);

                        if (!rslt.equals("0")) {
                            start1 = ss.indexOf("<carCount>");
                            end1 = ss.indexOf("</carCount>");
                            rslt = ss.substring(start1 + 10, end1);
                            item.clear();
                            if (!rslt.equals("0")) {
                                int cnt = Integer.valueOf(rslt);
                                for (int i = 1; i <= cnt; i++) {
                                    start1 = ss.indexOf("<pay" + String.valueOf(i) + ">");
                                    end1 = ss.indexOf("</pay" + String.valueOf(i) + ">");
                                    rslt = ss.substring(start1 + 6, end1);
                                    start1 = rslt.indexOf("<name>");
                                    end1 = rslt.indexOf("</name>");
                                    String name = rslt.substring(start1 + 6, end1);
                                    start1 = rslt.indexOf("<carID>");
                                    end1 = rslt.indexOf("</carID>");
                                    String carID = rslt.substring(start1 + 7, end1);
                                    start1 = rslt.indexOf("<pelak>");
                                    end1 = rslt.indexOf("</pelak>");
                                    String pelak = rslt.substring(start1 + 7, end1);
                                    start1 = rslt.indexOf("<date>");
                                    end1 = rslt.indexOf("</date>");
                                    String date = rslt.substring(start1 + 6, end1);

                                    start1 = rslt.indexOf("<price>");
                                    end1 = rslt.indexOf("</price>");
                                    String avarez = rslt.substring(start1 + 7, end1);
                                    String new_str = "";
                                    int j = 0;
                                    for (int ii = avarez.length() - 1; ii >= 0; ii--) {
                                        j++;
                                        if (j != avarez.length() && j % 3 == 0)
                                            new_str = "," + avarez.charAt(ii) + new_str;
                                        else
                                            new_str = avarez.charAt(ii) + new_str;
                                    }

                                    //itms_avarez_number.add(Integer.valueOf(avarez));
                                    avarez = new_str;
                                    //Toast.makeText(MyCarList.this, avarez, Toast.LENGTH_SHORT).show();
                                    item.add(new item_pay_history("تاریخ :"+date, pelak, carID, "مبلغ عوارض :" + avarez, name));
                                    //itms.add(carID);


                                }
                            }

                            mRecyclerView_cars = (RecyclerView) findViewById(R.id.my_recycler_cars);
                            mRecyclerView_cars.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(PayHistoryActivity.this);
                            mRecyclerView_cars.setLayoutManager(mLayoutManager);


                            mAdapter = new item_pay_history_adapter(PayHistoryActivity.this, item, PayHistoryActivity.this);
                            mRecyclerView_cars.setAdapter(mAdapter);
                            //Toast.makeText(PayHistoryActivity.this, "1111", Toast.LENGTH_SHORT).show();
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


}
