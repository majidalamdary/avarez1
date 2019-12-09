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
import com.sputa.avarez.model.Itm_city;


import java.util.List;


public class Itm_city_adapter extends RecyclerView.Adapter<Itm_city_adapter.my_view_holder> {
    private Context context;
    private List<Itm_city> item;


    Functions fun;
    String
            font_name = "";
    Typeface tf;
    public Itm_city_adapter(Context context, List<Itm_city> item) {
        this.context = context;
        this.item=item;
        fun = new Functions();



    }
    @Override
    public my_view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.itm_city,parent,false);
        return new my_view_holder(view);
    }

    @Override
    public void onBindViewHolder(my_view_holder holder, final int position) {
        holder.txt_title.setText(item.get(position).getTitle());
        // holder.img_icon.setImageResource(item.get(position).getImg());
    }



    @Override
    public int getItemCount() {
        return item.size();
    }

    public void additem(List<Itm_city> item)
    {
        this.item =item;
        notifyDataSetChanged();
    }
    public void removeitem(int position)
    {
        item.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position,item.size());

    }
    class my_view_holder extends RecyclerView.ViewHolder
    {
        // private ImageView img_icon;
        private TextView txt_title;


        public my_view_holder(View itemView) {
            super(itemView);
            //img_icon = itemView.findViewById(R.id.img_icon);
            txt_title = itemView.findViewById(R.id.lbl_title);

            txt_title.setTypeface(fun.get_font_iransens(context.getAssets()));


        }
    }
}
