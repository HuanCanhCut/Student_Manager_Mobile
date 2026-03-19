package com.example.studentmanager.adapter;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.R;
import com.example.studentmanager.ScheduleActivity;
import com.example.studentmanager.network.DTOs.response.GetWeeksResponse;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class WeekAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    GetWeeksResponse weeksResponse = new GetWeeksResponse();

    public void setWeeksResponse(GetWeeksResponse response) {
        this.weeksResponse = response;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new WeekViewHolder(inflater.inflate(R.layout.item_week, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GetWeeksResponse.Week week = this.weeksResponse.getData().get(position);
        ((WeekAdapter.WeekViewHolder) holder).bind(week);
    }

    @Override
    public int getItemCount() {
        return weeksResponse.getData().size();
    }

    static class WeekViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvWeekNumber;
        private final TextView tvDateRange;
        private final MaterialCardView cardCurrentBadge;
        private final ImageView nextIcon;
        private final MaterialCardView cardWeek;
        private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());

        public WeekViewHolder(@NonNull View itemView) {
            super(itemView);
            tvWeekNumber = itemView.findViewById(R.id.tvWeekNumber);
            tvDateRange = itemView.findViewById(R.id.tvDateRange);
            cardCurrentBadge = itemView.findViewById(R.id.cardCurrentBadge);
            cardWeek = itemView.findViewById(R.id.cardWeek);
            nextIcon = itemView.findViewById(R.id.nextIcon);
        }

        void bind(GetWeeksResponse.Week week) {
            tvWeekNumber.setText("Tuần " + week.getWeek_number());

            if (week.getWeek_start() != null && week.getWeek_end() != null) {
                String startDate = dateFormat.format(week.getWeek_start());
                String endDate = dateFormat.format(week.getWeek_end());
                tvDateRange.setText(startDate + " - " + endDate);
            } else {
                tvDateRange.setText("");
            }

            if (week.isIs_current()) {
                cardCurrentBadge.setVisibility(View.VISIBLE);
                cardWeek.setStrokeColor(Color.parseColor("#137FEC"));
                cardWeek.setStrokeWidth(4);
                nextIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor("#137FEC")));
            } else {
                cardCurrentBadge.setVisibility(View.GONE);
                cardWeek.setStrokeColor(Color.TRANSPARENT);
                cardWeek.setStrokeWidth(0);
                nextIcon.setImageTintList(ColorStateList.valueOf(Color.parseColor("#CBD5E1")));
            }

            cardWeek.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent scheduleIntent = new Intent(v.getContext(), ScheduleActivity.class);

                    scheduleIntent.putExtra("week", week);

                    v.getContext().startActivity(scheduleIntent);
                }
            });
        }
    }
}
