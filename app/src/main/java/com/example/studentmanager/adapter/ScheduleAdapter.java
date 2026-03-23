package com.example.studentmanager.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.R;
import com.example.studentmanager.network.DTOs.response.ScheduleResponse;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ScheduleResponse.Data> schedules = new ArrayList<>();

    public void setSchedules(List<ScheduleResponse.Data> schedules) {
        this.schedules = schedules;

        Log.d("SCHEDULE", new Gson().toJson(schedules));

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new ScheduleViewHolder(inflater.inflate(R.layout.item_schedule, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ScheduleViewHolder) holder).bind(schedules.get(position));
    }

    @Override
    public int getItemCount() {
        return schedules.size();
    }

    static class ScheduleViewHolder extends RecyclerView.ViewHolder {
        TextView startTime, subjectName, room, teacherName, totalTime;
        ImageView roomIcon, teacherIcon;
        LinearLayout cardSchedule;
        MaterialCardView inProgress;

        public ScheduleViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime = itemView.findViewById(R.id.startTime);
            subjectName = itemView.findViewById(R.id.subjectName);
            room = itemView.findViewById(R.id.room);
            teacherName = itemView.findViewById(R.id.teacherName);
            totalTime = itemView.findViewById(R.id.totalTime);
            inProgress = itemView.findViewById(R.id.inProgress);
            cardSchedule = itemView.findViewById(R.id.cardSchedule);
            roomIcon = itemView.findViewById(R.id.roomIcon);
            teacherIcon = itemView.findViewById(R.id.teacherIcon);
        }

        void bind(ScheduleResponse.Data data) {
            LocalTime start = LocalTime.parse(data.getStart_time());
            LocalTime end = LocalTime.parse(data.getEnd_time());
            LocalTime now = LocalTime.now();

            boolean isInProgress = !now.isBefore(start) && !now.isAfter(end);

            startTime.setText(data.getStart_time().split(":")[0] + ":" + data.getStart_time().split(":")[1]);
            subjectName.setText(data.getClass_subject().getSubject().getName());
            room.setText("Phòng " + data.getRoom());
            teacherName.setText(data.getClass_subject().getTeacher().getFull_name());

            if (isInProgress) {
                long minutesLeft = Duration.between(now, end).toMinutes();
                totalTime.setText("Còn " + minutesLeft + " phút");

                startTime.setTextColor(Color.parseColor("#137FEC"));
                inProgress.setVisibility(View.VISIBLE);
                cardSchedule.setBackgroundColor(Color.parseColor("#137FEC"));
                subjectName.setTextColor(Color.WHITE);
                room.setTextColor(Color.WHITE);
                teacherName.setTextColor(Color.WHITE);
                roomIcon.setColorFilter(Color.WHITE);
                teacherIcon.setColorFilter(Color.WHITE);
                totalTime.setTextColor(Color.WHITE);
            } else {
                long totalMinutes = Duration.between(start, end).toMinutes();
                totalTime.setText(totalMinutes + " phút");

                startTime.setTextColor(Color.parseColor("#64748B"));
                inProgress.setVisibility(View.GONE);
                cardSchedule.setBackgroundColor(Color.WHITE);
                subjectName.setTextColor(Color.parseColor("#1E293B"));
                room.setTextColor(Color.parseColor("#64748B"));
                teacherName.setTextColor(Color.parseColor("#64748B"));
                roomIcon.setColorFilter(Color.parseColor("#64748B"));
                teacherIcon.setColorFilter(Color.parseColor("#64748B"));
                totalTime.setTextColor(Color.parseColor("#64748B"));
            }
        }
    }
}
