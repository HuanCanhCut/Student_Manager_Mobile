package com.example.studentmanager.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.R;
import com.example.studentmanager.network.DTOs.response.GradeResponse;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private GradeResponse gradeResponse = new GradeResponse();

    private static final int TYPE_GPA_CARD = 0;
    private static final int TYPE_TAB_BAR  = 1;
    private static final int TYPE_SUBJECT  = 2;

    public HomeAdapter(GradeResponse gradeResponse) {
        this.gradeResponse = gradeResponse;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TYPE_GPA_CARD:
                return new GpaCardViewHolder(inflater.inflate(R.layout.item_gpa_card, parent, false));
            default:
                return new GpaCardViewHolder(inflater.inflate(R.layout.item_gpa_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GpaCardViewHolder) {
            ((GpaCardViewHolder) holder).bind(this.gradeResponse.getGpa());
        }
    }

    @Override
    public int getItemCount() {
        return 2 + gradeResponse.getData().size();
    }

    static class GpaCardViewHolder extends RecyclerView.ViewHolder{
        TextView textViewGpa;
        public GpaCardViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewGpa = itemView.findViewById(R.id.gpa);
        }

        void bind(float gpa) {
            textViewGpa.setText(String.format("%.2f", gpa));
        }
    }
}
