package com.example.splash.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.splash.R;
import com.example.splash.data.model.ClientUserModel;
import com.example.splash.vendor.VenderDashboard;
import com.example.splash.vendor.ViewClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class VendorDashboardAdapter extends RecyclerView.Adapter<VendorDashboardAdapter.ViewHolder> {

    List<ClientUserModel> list;

    public VendorDashboardAdapter(List<ClientUserModel> list){
        this.list=list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vendor_dashboard,parent,false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(parent.getContext(), ViewClient.class);
                parent.getContext().startActivity(i);
            }
        });

        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.getClientName().setText(list.get(position).getName());
        holder.getHouseno().setText(list.get(position).getAddress());
        holder.getBottles().setText(String.valueOf(list.get(position).getBottlesrequired()));
//        holder.getViewClient().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{


        private final TextView clientName;
        private final TextView houseno;
        private final TextView bottles;

        private final ImageView viewClient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            clientName= itemView.findViewById(R.id.namecard);
            houseno=itemView.findViewById(R.id.housenocard);
            bottles=itemView.findViewById(R.id.noofbottles);
            viewClient=itemView.findViewById(R.id.viewclient);
        }

        public TextView getClientName() {
            return clientName;
        }

        public TextView getHouseno() {
            return houseno;
        }

        public TextView getBottles() {
            return bottles;
        }

        public ImageView getViewClient() {
            return viewClient;
        }
    }
}
