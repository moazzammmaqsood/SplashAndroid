package com.nouveau.splash.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nouveau.splash.Api.modal.vendor.FinanceEntity;
import com.nouveau.splash.Api.modal.vendor.Orders;
import com.nouveau.splash.R;
import com.nouveau.splash.callbacks.OnItemClick;
import com.nouveau.splash.utils.Utils;
import com.nouveau.splash.vendor.FinanceActivity;
import com.nouveau.splash.vendor.ViewClient;

import java.util.ArrayList;
import java.util.List;

public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.ViewHolder>{

    List<FinanceEntity> list;
    Activity activity;
    OnItemClick onItemClick;

    public FinanceAdapter(List<FinanceEntity> list, Activity activity, OnItemClick onItemClick) {
        this.list = list;
        this.activity = activity;
        this.onItemClick = onItemClick;
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{


        private final TextView date;
        private final TextView remarks;
        private final TextView amount;
        private final ImageView delete;

        public TextView getDate() {
            return date;
        }

        public TextView getRemarks() {
            return remarks;
        }

        public TextView getAmount() {
            return amount;
        }

        public ImageView getDelete() {
            return delete;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            date= itemView.findViewById(R.id.date);
            remarks=itemView.findViewById(R.id.remarks);
            amount=itemView.findViewById(R.id.amount);
            delete=itemView.findViewById(R.id.delete);


        }

    }

    @NonNull
    @Override
    public FinanceAdapter.ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vendor_finance,parent,false);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(parent.getContext(), ViewClient.class);
//                i.putExtra("userid",)
//                parent.getContext().startActivity(i);
//            }
//        });

        FinanceAdapter.ViewHolder viewHolder=new FinanceAdapter.ViewHolder(view);
        return viewHolder;
    }
    public void updateData(List<FinanceEntity> list){
        this.list=new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();


    }
    @Override
    public void onBindViewHolder(@NonNull FinanceAdapter.ViewHolder holder, int position) {
        holder.getDate().setText(Utils.getDatetoString(list.get(position).getDate()));
        holder.getRemarks().setText(list.get(position).getRemarks());
        if(list.get(position).getIncome()>0){
            holder.getAmount().setText(String.valueOf(list.get(position).getIncome()));
        }else if(list.get(position).getExpense()>0){
            holder.getAmount().setText(String.valueOf(list.get(position).getExpense()*-1));
        }

        final int index=position;
        holder.getDelete().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.DeleteOrder(list.get(index).getId());
            }
        });
//        holder.get().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(activity, ViewClient.class);
//                intent.putExtra("userid",changeablelist.get(index).getUserid());
//                intent.putExtra("clientid",changeablelist.get(index).getClientid());
//                activity.startActivity(intent);
//
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
