package com.sputa.avarez;

import android.content.DialogInterface;

import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sputa.avarez.adapters.item_adapter;
import com.sputa.avarez.classes.CallSoap;
import com.sputa.avarez.model.items;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;

import org.ksoap2.serialization.SoapObject;

import org.ksoap2.serialization.SoapSerializationEnvelope;

import org.ksoap2.transport.HttpTransportSE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import me.leolin.shortcutbadger.ShortcutBadger;

public class TextService extends AppCompatActivity {
    private static String SOAP_ACTION1 = "http://tempuri.org/HelloWorld";

    private static String SOAP_ACTION2 = "http://tempuri.org/HelloWorld";

    private static String NAMESPACE = "http://tempuri.org/";

    private static String METHOD_NAME1 = "HelloWorld";

    private static String METHOD_NAME2 = "HelloWorld";

    private static String URL = "http://ardnan.ir/WebService1.asmx?WSDL";
    private static String rslt="";


    AsyncTask mm;
    Button btnFar,btnCel,btnClear;

    EditText txtFar,txtCel;
    private String key="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_service);

        Button b1=(Button)findViewById(R.id.button1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

// TODO Auto-generated method stub
                mm = new MyAsyncTask();
                String[] qwe = new String[10];
                qwe[0] = "123";
                mm.execute(  qwe);
            }
        });
    }

    public void clk_cel(View view) {

    }

    public class MyAsyncTask extends AsyncTask<String, Void, Boolean> {


        public String ss = "", url = "";


        @Override
        protected Boolean doInBackground(String ... str) {
            // TODO Auto-generated method stub
            String Tag="tg1";

            key = "46794E7F-1612-46A7-B16B-7414BCC25FDE";

            CallSoap cs;
            String PnosaziKodem="1-11-40-7-0-0-0";
            String ShG="0126014311061";
            String ShP="0000213460632";
            String Web="nosazi";

            UUID AID=UUID.fromString("359c7fcd-f403-4819-bb64-91e7deede50c");// UUID.randomUUID().toString();
//            UUID AID=UUID.fromString("867e3038-1db5-4a3f-b249-9ca659ddc356");// UUID.randomUUID().toString();
            Log.d(Tag,AID.toString());
//            String AtuhIn =ShG+ShP+Web+AID;
            String AtuhIn =PnosaziKodem+AID;
            Log.d(Tag,AtuhIn);
            String Atuh=SetAtuh(AtuhIn);
//            String Atuh=SetAtuh(PnosaziKodem+AID);
            Log.d(Tag,Atuh);

                try{
                    cs = new CallSoap();
                    String  resp=cs.Call_Nosazi_GetInfo(PnosaziKodem, AID,Atuh);
//                    String  resp=cs.Call_paid("0126014311061","0000213460632", AID,Atuh);
                    Log.d(Tag,resp);
                }catch(Exception ex)
                {
                    Log.d(Tag, "err:  " + ex.toString());
                }


            return false;
        }
        public  String SetAtuh(String Text) {
            Text=Text + key;
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
        protected void onPostExecute(Double result) {
            Toast.makeText(TextService.this, "123", Toast.LENGTH_SHORT).show();

        }





    }


}


