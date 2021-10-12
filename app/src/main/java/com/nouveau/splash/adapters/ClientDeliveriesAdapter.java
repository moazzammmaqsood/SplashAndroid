package com.nouveau.splash.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nouveau.splash.Api.modal.vendor.Orders;
import com.nouveau.splash.R;
import com.nouveau.splash.callbacks.OnItemClick;
import com.nouveau.splash.utils.Utils;

import java.util.List;

public class ClientDeliveriesAdapter extends RecyclerView.Adapter<ClientDeliveriesAdapter.ViewHolder> {

    List<Orders> list;
    Activity activity;
    OnItemClick onItemClick;

    public ClientDeliveriesAdapter(List<Orders> list, Activity activity,OnItemClick onItemClick) {
        this.activity=activity;
        this.list=list;
        this.onItemClick=onItemClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_client_delivery,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {
        if(position==0  ){
            holder.getDate().setText("Date");
            holder.getBottlesdel().setText("Del");
            holder.getBottlesrec().setText("Rec");
            holder.getPaid().setText("Paid");
            holder.getDelete().setVisibility(View.INVISIBLE);

        }else {

            holder.getDate().setText(Utils.getDatetoString(list.get(position-1).getDate()));
            holder.getBottlesdel().setText(String.valueOf(list.get(position-1).getBottlesdelivered()));
            holder.getBottlesrec().setText(String.valueOf(list.get(position-1).getBottlesrecieved()));
            holder.getPaid().setText(String.valueOf(list.get(position-1).getPayment()));
            final int index = position;
            holder.getDelete().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick.DeleteOrder(list.get(index-1).getOrderid());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{


        private final TextView date;
        private final TextView bottlesdel;
        private final TextView bottlesrec;
        private final TextView paid;
        private final ImageView delete;

        public TextView getDate() {
            return date;
        }

        public TextView getBottlesdel() {
            return bottlesdel;
        }

        public TextView getBottlesrec() {
            return bottlesrec;
        }

        public TextView getPaid() {
            return paid;
        }

        public ImageView getDelete() {
            return delete;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date= itemView.findViewById(R.id.date);
            bottlesdel=itemView.findViewById(R.id.bottlesdel);
            bottlesrec=itemView.findViewById(R.id.bottlesrec);
            paid=itemView.findViewById(R.id.paid);
            delete=itemView.findViewById(R.id.delete);


        }

    }
}
