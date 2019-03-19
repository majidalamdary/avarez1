package com.sputa.avarez.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sputa.avarez.Functions;
import com.sputa.avarez.R;
import com.sputa.avarez.RecyclerViewClickListener;
import com.sputa.avarez.model.items_cars;
import com.sputa.avarez.model.items_eshterak;

import java.util.List;



public class item_eshterak_adapter extends RecyclerView.Adapter<item_eshterak_adapter.my_view_holder> {
    private Context context;
    private List<items_eshterak> item;
    private static RecyclerViewClickListener itemListener;
    private boolean is_requested;
    Functions fun;
    String
            font_name = "";
    Typeface tf;
    public item_eshterak_adapter(Context context, List<items_eshterak> item, RecyclerViewClickListener itemListener) {
        this.context = context;
        this.itemListener = itemListener;
        this.item=item;
        fun = new Functions();



    }

    @Override
    public my_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.list_eshterak_item,parent,false);

        return new my_view_holder(view);
    }

    @Override
    public void onBindViewHolder(final my_view_holder holder, final int position) {
       // holder.txt_radif.setText(item.get(position).getRadif());
        holder.txt_price.setText(item.get(position).getTxt_price());
        holder.txt_name.setText(item.get(position).getTxt_name());
        holder.txt_eshterak.setText(item.get(position).getTxt_eshterak());
        if(item.get(position).getType().equals("gas"))
            holder.img_icon.setBackgroundResource(R.drawable.gas);
        if(item.get(position).getType().equals("water"))
            holder.img_icon.setBackgroundResource(R.drawable.water);
        if(item.get(position).getType().equals("electric"))
            holder.img_icon.setBackgroundResource(R.drawable.electric);
        if(item.get(position).getType().equals("telphone"))
            holder.img_icon.setBackgroundResource(R.drawable.tephone);
        if(item.get(position).getType().equals("nosazi"))
            holder.img_icon.setBackgroundResource(R.drawable.renovation);
        if(item.get(position).getType().equals("car"))
            holder.img_icon.setBackgroundResource(R.drawable.car);









    }



    @Override
    public int getItemCount() {
        return item.size();
    }


    class my_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

       // private TextView txt_radif;
        private TextView txt_price;
        private TextView txt_name;
        private TextView txt_eshterak;
        private ImageView img_delete;
        private ImageView img_detail;
        private ImageView img_icon;

        private CardView crd1;

        public my_view_holder(View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_eshterak = itemView.findViewById(R.id.txt_eshterak);
            txt_price = itemView.findViewById(R.id.txt_price);
            img_delete = itemView.findViewById(R.id.img_delete_eshterak);
            img_detail = itemView.findViewById(R.id.img_detail);
            img_icon = itemView.findViewById(R.id.img_icon);
            //txt_radif = itemView.findViewById(R.id.txt_radif);
            img_delete.setOnClickListener(this);
            img_detail.setOnClickListener(this);


            crd1 = itemView.findViewById(R.id.crd1);



        }
        @Override
        public void onClick(View v) {
            itemListener.recyclerViewListClicked(v, this.getLayoutPosition());
            // Toast.makeText(context, "majid", Toast.LENGTH_SHORT).show();
        }
    }




}
