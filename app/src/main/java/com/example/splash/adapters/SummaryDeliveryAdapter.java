package com.example.splash.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splash.Api.modal.vendor.ClientDelivery;
import com.example.splash.Api.modal.vendor.SummaryDelivery;
import com.example.splash.R;
import com.example.splash.vendor.ViewClient;

import java.util.List;

public class SummaryDeliveryAdapter extends RecyclerView.Adapter<SummaryDeliveryAdapter.ViewHolder> {

    List<SummaryDelivery> list;
    Activity activity;

    public SummaryDeliveryAdapter(List<SummaryDelivery> list, Activity activity) {
        this.activity=activity;
        this.list=list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_summary,parent,false);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(parent.getContext(), ViewClient.class);
//                i.putExtra("userid",)
//                parent.getContext().startActivity(i);
//            }
//        });

        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,  int position) {

        holder.getClientName().setText(list.get(position).getName());
        holder.getBottlesdel().setText(String.valueOf(list.get(position).getBottlesdelivered()));
        holder.getBottlesrec().setText(String.valueOf(list.get(position).getBottlesrecieved()));
        holder.getPayment().setText(String.valueOf(list.get(position).getPayment()));
        final int index=position;
        holder.getCardll().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity,ViewClient.class);
                intent.putExtra("userid",list.get(index).getUserid());
                intent.putExtra("clientid",list.get(index).getClientid());
                activity.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{


        private final TextView clientName;
        private final TextView bottlesdel;
        private final TextView bottlesrec;

        private final TextView payment;
        LinearLayout cardll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            clientName= itemView.findViewById(R.id.namecard);
            bottlesdel=itemView.findViewById(R.id.bottlesdel);
            bottlesrec=itemView.findViewById(R.id.bottlesrec);
            payment=itemView.findViewById(R.id.payment);
            cardll=itemView.findViewById(R.id.cardll);
        }

        public TextView getClientName() {
            return clientName;
        }

        public TextView getBottlesdel() {
            return bottlesdel;
        }

        public TextView getBottlesrec() {
            return bottlesrec;
        }

        public TextView getPayment() {
            return payment;
        }

        public LinearLayout getCardll() {
            return cardll;
        }
    }
}
