package com.example.studentmanager.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.R;
import com.example.studentmanager.network.DTOs.response.GpaHistoryResponse;
import com.google.gson.Gson;

public class GpaHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private GpaHistoryResponse gpaHistory = new GpaHistoryResponse();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return new GpaViewHolder(inflater.inflate(R.layout.gpa_history_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GpaHistoryResponse.Data data = this.gpaHistory.getData().get(position);

        ((GpaViewHolder) holder).bind(data);
    }

    @Override
    public int getItemCount() {
        return gpaHistory.getData().size();
    }

    static class GpaViewHolder extends RecyclerView.ViewHolder {

        TextView semester, schoolYear, gpa, gpaLevel;

        public GpaViewHolder(@NonNull View itemView) {
            super(itemView);
            semester = itemView.findViewById(R.id.semester);
            schoolYear = itemView.findViewById(R.id.school_year);
            gpa = itemView.findViewById(R.id.gpa);
            gpaLevel = itemView.findViewById(R.id.gpa_level);
        }

        void bind(GpaHistoryResponse.Data data) {
            semester.setText(data.getSemester());
            schoolYear.setText("Năm học " + data.getSchool_year());
            gpa.setText(String.valueOf(data.getGpa()));
            gpaLevel.setText(data.getGpa_level());
        }
    }

    public void setGpaHistory (GpaHistoryResponse gpaHistory) {
        this.gpaHistory = gpaHistory;

        Log.d("SET_GPA_HISTORY", new Gson().toJson(gpaHistory.getData()));

        notifyDataSetChanged();
    }
}
