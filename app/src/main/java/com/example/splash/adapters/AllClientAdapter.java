package com.example.splash.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.splash.Api.modal.vendor.ClientDelivery;
import com.example.splash.Api.modal.vendor.UserClient;
import com.example.splash.R;
import com.example.splash.vendor.ViewClient;

import java.util.ArrayList;
import java.util.List;

public class AllClientAdapter extends RecyclerView.Adapter<AllClientAdapter.ViewHolder> implements Filterable {

    List<UserClient> changeablelist;
    List<UserClient> list;
    Activity activity;

    public AllClientAdapter(List<UserClient> list, Activity activity) {
        this.activity=activity;

        changeablelist=list;
        this.list=new ArrayList<>(list);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_client,parent,false);

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

        holder.getSno().setText(String.valueOf(position+1));
        holder.getClientName().setText(changeablelist.get(position).getName());
        holder.getHouseno().setText(changeablelist.get(position).getAddress());
        holder.getBottles().setText(String.valueOf(changeablelist.get(position).getBottles()));

        final int index=position;
        holder.getViewClient().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity,ViewClient.class);
                intent.putExtra("userid",changeablelist.get(index).getUserid());
                intent.putExtra("clientid",changeablelist.get(index).getClientid());
                activity.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return changeablelist.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder{


        private final TextView clientName,sno;
        private final TextView houseno;
        private final TextView bottles;

        private final ImageView viewClient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sno= itemView.findViewById(R.id.sno);
            clientName= itemView.findViewById(R.id.namecard);
            houseno=itemView.findViewById(R.id.housenocard);
            bottles=itemView.findViewById(R.id.noofbottles);
            viewClient=itemView.findViewById(R.id.viewclient);
        }

        public TextView getSno() {
            return sno;
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

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<UserClient> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(list);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (UserClient item : list) {
                    if (item.getName().toLowerCase().contains(filterPattern) || item.getAddress().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            changeablelist.clear();
            changeablelist.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
