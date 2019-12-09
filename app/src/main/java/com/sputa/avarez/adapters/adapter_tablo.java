package com.sputa.avarez.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sputa.avarez.Functions;
import com.sputa.avarez.R;
import com.sputa.avarez.model.item_bussiness;
import com.sputa.avarez.model.item_tablo;

import java.util.List;


public class adapter_tablo extends RecyclerView.Adapter<adapter_tablo.my_view_holder> {
    private Context context;
    private List<item_tablo> item;

    private boolean is_requested;
    Functions fun;
    String
            font_name = "";
    Typeface tf;
    public adapter_tablo(Context context, List<item_tablo> item) {
        this.context = context;
        this.item=item;
        fun = new Functions();



    }

    @Override
    public my_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.list_bussiness_item,parent,false);

        return new my_view_holder(view);
    }

    @Override
    public void onBindViewHolder(final my_view_holder holder, final int position) {
        holder.txt_last_pay.setText(item.get(position).getLastPayDate());
        holder.txt_from_year.setText(item.get(position).getStartYear());
        holder.txt_to_year.setText(item.get(position).getEndYear());
        holder.txt_tax.setText(item.get(position).getTax());
        holder.txt_punishment.setText(item.get(position).getPenalty());
        holder.txt_avarez.setText(item.get(position).getAmount());




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

        private TextView txt_last_pay;
        private TextView txt_from_year;
        private TextView txt_to_year;
        private TextView txt_tax;
        private TextView txt_punishment;
        private TextView txt_avarez;

        private CardView crd1;

        public my_view_holder(View itemView) {
            super(itemView);
            txt_last_pay = itemView.findViewById(R.id.txt_last_pay);
            txt_from_year = itemView.findViewById(R.id.txt_from_year);
            txt_to_year = itemView.findViewById(R.id.txt_to_year);
//            txt_tax = itemView.findViewById(R.id.txt_tax);
//            txt_punishment = itemView.findViewById(R.id.txt_punishment);
            txt_avarez = itemView.findViewById(R.id.txt_avarez);

            crd1 = itemView.findViewById(R.id.crd1);


//            TextView txt_year =  itemView.findViewById(R.id.txt_title);


        }
    }




}
