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
import com.sputa.avarez.model.items;

import java.util.List;


public class adapter_bussiness extends RecyclerView.Adapter<adapter_bussiness.my_view_holder> {
    private Context context;
    private List<item_bussiness> item;

    private boolean is_requested;
    Functions fun;
    String
            font_name = "";
    Typeface tf;
    public adapter_bussiness(Context context, List<item_bussiness> item) {
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
        holder.txtAvarezYear.setText(item.get(position).get_avarezYear());
        holder.txtPenaltyRate.setText(item.get(position).get_penaltyRate());
        holder.txtAvarezPrice.setText(item.get(position).get_avarezPrice());
        holder.txtAvarezLate.setText(item.get(position).get_avarezLate());
        holder.txtServicePrice.setText(item.get(position).get_servicePrice());
        holder.txtServiceLate.setText(item.get(position).get_serviceLate());
        holder.txt_local_service_price.setText(item.get(position).get_localServicePrice());
        holder.txt_local_service_late.setText(item.get(position).get_localServiceLate());




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

        private TextView txtAvarezYear;
        private TextView txtPenaltyRate;
        private TextView txtAvarezPrice;
        private TextView txtAvarezLate;
        private TextView txtServicePrice;
        private TextView txtServiceLate;
        private TextView txt_local_service_price;
        private TextView txt_local_service_late;

        private CardView crd1;

        public my_view_holder(View itemView) {
            super(itemView);
            txtAvarezYear = itemView.findViewById(R.id.txt_avarez_year);
            txtPenaltyRate = itemView.findViewById(R.id.txt_penalty_rate);
            txtAvarezPrice = itemView.findViewById(R.id.txt_avarez_price);
            txtAvarezLate = itemView.findViewById(R.id.txt_avarez_late);
            txtServicePrice = itemView.findViewById(R.id.txt_service_price);
            txtServiceLate = itemView.findViewById(R.id.txt_service_late);
            txt_local_service_price = itemView.findViewById(R.id.txt_local_service_price);
            txt_local_service_late = itemView.findViewById(R.id.txt_local_service_late);

            crd1 = itemView.findViewById(R.id.crd1);


//            TextView txt_year =  itemView.findViewById(R.id.txt_title);


        }
    }




}
