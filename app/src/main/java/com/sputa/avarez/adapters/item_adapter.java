package com.sputa.avarez.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.sputa.avarez.Functions;
import com.sputa.avarez.R;
import com.sputa.avarez.model.items;


import java.util.List;


public class item_adapter extends RecyclerView.Adapter<item_adapter.my_view_holder> {
    private Context context;
    private List<items> item;

    private boolean is_requested;
    Functions fun;
    String
            font_name = "";
    Typeface tf;
    public item_adapter(Context context, List<items> item) {
        this.context = context;
        this.item=item;
        fun = new Functions();



    }

    @Override
    public my_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.list_pay_item,parent,false);

        return new my_view_holder(view);
    }

    @Override
    public void onBindViewHolder(final my_view_holder holder, final int position) {
        holder.txt_year.setText(item.get(position).getTxt_year());
        holder.txt_avarez.setText(item.get(position).getTxt_avarez());
        holder.txt_farsudegi.setText(item.get(position).getTxt_farsudegi());
        holder.txt_rate_punish.setText(item.get(position).getTxt_rate_punish());
        holder.txt_punish.setText(item.get(position).getTxt_punish());




        holder.crd1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //if(view.getId()==R.id.crd1) {
                    Toast.makeText(context, "1", Toast.LENGTH_SHORT).show();
                //}
            }
        });

    }



    @Override
    public int getItemCount() {
        return item.size();
    }


    class my_view_holder extends RecyclerView.ViewHolder
    {

        private TextView txt_year;
        private TextView txt_avarez;
        private TextView txt_farsudegi;
        private TextView txt_rate_punish;
        private TextView txt_punish;

        private CardView crd1;

        public my_view_holder(View itemView) {
            super(itemView);
            txt_year = itemView.findViewById(R.id.txt_year);
            txt_avarez = itemView.findViewById(R.id.txt_avarez);
            txt_farsudegi = itemView.findViewById(R.id.txt_farsudegi);
            txt_rate_punish = itemView.findViewById(R.id.txt_rate_punish);
            txt_punish = itemView.findViewById(R.id.txt_punish);

            crd1 = itemView.findViewById(R.id.crd1);


            TextView txt_year =  itemView.findViewById(R.id.txt_title);


        }
    }




}
