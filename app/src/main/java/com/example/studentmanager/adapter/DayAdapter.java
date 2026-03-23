package com.example.studentmanager.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.R;
import com.example.studentmanager.network.DTOs.response.GetWeeksResponse;
import com.google.android.material.card.MaterialCardView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {
    private GetWeeksResponse.Week currentWeek;
    private final String[] daysOfWeek = {"T2", "T3", "T4", "T5", "T6", "T7"};
    private int selectedPosition = 0;
    private int todayPosition = -1;
    private OnDayClickListener listener;

    public interface OnDayClickListener {
        void onDayClick(int position, String date);
    }

    public void setOnDayClickListener(OnDayClickListener listener) {
        this.listener = listener;
    }

    public void setWeek(GetWeeksResponse.Week week) {
        this.currentWeek = week;
        this.todayPosition = -1;

        if (week.isIs_current()) {
            Calendar calendar = Calendar.getInstance();
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            if (dayOfWeek >= Calendar.MONDAY && dayOfWeek <= Calendar.SATURDAY) {
                this.todayPosition = dayOfWeek - 2; // calendar starts from Monday, so subtract 2 to get correct index position
                this.selectedPosition = this.todayPosition;
            } else {
                this.selectedPosition = 0;
            }
        } else {
            this.selectedPosition = 0;
        }

        notifyDataSetChanged();

        if (listener != null && currentWeek != null && currentWeek.getWeek_start() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentWeek.getWeek_start());
            calendar.add(Calendar.DAY_OF_YEAR, selectedPosition);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String dateStr = sdf.format(calendar.getTime());

            listener.onDayClick(selectedPosition, dateStr);
        }
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DayViewHolder(inflater.inflate(R.layout.item_day, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        boolean isSelected = (position == selectedPosition);
        boolean isToday = (position == todayPosition);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentWeek.getWeek_start());
        calendar.add(Calendar.DAY_OF_YEAR, position);

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        Date date = calendar.getTime();

        holder.bind(daysOfWeek[position], String.valueOf(dayOfMonth), isSelected, isToday);

        holder.itemView.setOnClickListener(v -> {
            int oldPosition = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            if (selectedPosition != RecyclerView.NO_POSITION) {
                notifyItemChanged(oldPosition);
                notifyItemChanged(selectedPosition);

                if (listener != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String dateStr = sdf.format(date);


                    listener.onDayClick(selectedPosition, dateStr);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return daysOfWeek.length;
    }

    static class DayViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvDayName;
        private final TextView tvDayNumber;
        private final MaterialCardView cardDay;
        private final View dotSelected;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDayName = itemView.findViewById(R.id.tvDayName);
            tvDayNumber = itemView.findViewById(R.id.tvDayNumber);
            cardDay = itemView.findViewById(R.id.cardDay);
            dotSelected = itemView.findViewById(R.id.dotSelected);
        }

        void bind(String dayName, String dayNumber, boolean isSelected, boolean isToday) {
            tvDayName.setText(dayName);
            tvDayNumber.setText(dayNumber);

            if (isSelected) {
                cardDay.setCardBackgroundColor(Color.parseColor("#137FEC"));
                tvDayName.setTextColor(Color.WHITE);
                tvDayNumber.setTextColor(Color.WHITE);
            } else {
                cardDay.setCardBackgroundColor(Color.WHITE);
                tvDayName.setTextColor(Color.parseColor("#94A3B8"));
                tvDayNumber.setTextColor(Color.parseColor("#1E293B"));
            }

            if (dotSelected != null) {
                dotSelected.setVisibility(isToday ? View.VISIBLE : View.INVISIBLE);
            }
        }
    }
}
