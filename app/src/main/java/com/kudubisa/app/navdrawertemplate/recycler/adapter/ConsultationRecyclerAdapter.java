package com.kudubisa.app.navdrawertemplate.recycler.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kudubisa.app.navdrawertemplate.ConsultationDetailActivity;
import com.kudubisa.app.navdrawertemplate.R;
import com.kudubisa.app.navdrawertemplate.model.Consultation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by asus on 8/19/18.
 */

public class ConsultationRecyclerAdapter extends RecyclerView.Adapter<ConsultationRecyclerAdapter.ViewHolder>{
    private Context context;
    private List<Consultation> consultationList;

    public ConsultationRecyclerAdapter(List<Consultation> consultationList) {
        this.consultationList = consultationList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_consultation_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Consultation consultation = consultationList.get(position);

        String listTitle = "Pregnancy Age : " + consultation.getPregnancyAge() + " Week(s)";

        String listDesc = "Daily Calorie Need : " + consultation.getCalorie() + " KKal";

        holder.listTitle.setText(listTitle);
        holder.listDescription.setText(listDesc);
        holder.listDate.setText(consultation.getCreatedAt());
        holder.listPicture.setImageDrawable(context.getDrawable(R.drawable.consulting));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ConsultationDetailActivity.class);
                intent.putExtra("calorie", consultation.getCalorie());
                intent.putExtra("consultation_id", consultation.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return consultationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView listTitle, listDescription, listDate;
        ImageView listPicture;
        public ViewHolder(View itemView) {
            super(itemView);
            listPicture = itemView.findViewById(R.id.list_image);
            listTitle = itemView.findViewById(R.id.list_title);
            listDescription = itemView.findViewById(R.id.list_desc);
            listDate = itemView.findViewById(R.id.list_date);
        }
    }
}
