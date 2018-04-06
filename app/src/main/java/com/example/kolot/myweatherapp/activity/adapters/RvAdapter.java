package com.example.kolot.myweatherapp.activity.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kolot.myweatherapp.R;
import com.example.kolot.myweatherapp.activity.activity.DetailInfoActivity;
import com.example.kolot.myweatherapp.activity.database.DbDto;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kolot on 06.04.2018.
 */

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.ViewHolder> {

    private List<DbDto> dbDtos = new ArrayList<>();
    private Context context;

    public RvAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DbDto> dbDtos) {
        this.dbDtos = dbDtos;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DbDto dbDto = dbDtos.get(position);

        holder.location.setText("Широта: "+String.valueOf(dbDto.getLat()) + "\n"
                +"Долгота: "+ String.valueOf(dbDto.getLon())+"\n"
        + dbDto.getAddress());
        holder.date.setText(dbDto.getDate() + " " + dbDto.getFormattedTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailInfoActivity.class);
                intent.putExtra("dbdto", new Gson().toJson(dbDto));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dbDtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView date, location;

        public ViewHolder(View itemView) {
            super(itemView);

            date = (TextView) itemView.findViewById(R.id.rv_item_date);
            location = (TextView) itemView.findViewById(R.id.rv_item_location);

        }
    }
}
