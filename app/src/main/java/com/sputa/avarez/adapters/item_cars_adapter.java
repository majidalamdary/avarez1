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
import com.sputa.avarez.model.items;
import com.sputa.avarez.model.items_cars;

import java.util.List;


public class item_cars_adapter extends RecyclerView.Adapter<item_cars_adapter.my_view_holder> {
    private Context context;
    private List<items_cars> item;

    private boolean is_requested;
    Functions fun;
    String
            font_name = "";
    Typeface tf;
    public item_cars_adapter(Context context, List<items_cars> item) {
        this.context = context;
        this.item=item;
        fun = new Functions();



    }

    @Override
    public my_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.list_cars_item,parent,false);

        return new my_view_holder(view);
    }

    @Override
    public void onBindViewHolder(final my_view_holder holder, final int position) {
        holder.txt_radif.setText(item.get(position).getRadif());
        holder.txt_avarez.setText(item.get(position).getTxt_avarez());
        holder.txt_name.setText(item.get(position).getTxt_name());
        holder.txt_pelak.setText(item.get(position).getTxt_pelak());




        holder.crd1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //if(view.getId()==R.id.crd1) {
                  //  Toast.makeText(context, item.get(position).getRadif(), Toast.LENGTH_SHORT).show();
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

        private TextView txt_radif;
        private TextView txt_avarez;
        private TextView txt_name;
        private TextView txt_pelak;

        private CardView crd1;

        public my_view_holder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_pelak = itemView.findViewById(R.id.txt_pelak);
            txt_avarez = itemView.findViewById(R.id.txt_avarez);
            txt_radif = itemView.findViewById(R.id.txt_radif);


            crd1 = itemView.findViewById(R.id.crd1);



        }
    }




}
