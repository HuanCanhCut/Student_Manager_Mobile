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
import com.google.android.material.tabs.TabLayout;

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private GradeResponse gradeResponse = new GradeResponse();
    private float gpa;

    private static final int TYPE_GPA_CARD = 0;
    private static final int TYPE_TAB_BAR = 1;
    private static final int TYPE_SUBJECT = 2;

    private int selectedTab = 0;

    private TabLayout.OnTabSelectedListener listener;

    public HomeAdapter(float gpa, TabLayout.OnTabSelectedListener listener) {
        this.gpa = gpa;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case TYPE_GPA_CARD:
                return new GpaCardViewHolder(inflater.inflate(R.layout.item_gpa_card, parent, false));
            case TYPE_TAB_BAR:
                return new TabbarViewHolder(inflater.inflate(R.layout.item_academic_result_tab, parent, false));
            default:
                return new GpaCardViewHolder(inflater.inflate(R.layout.item_gpa_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GpaCardViewHolder) {
            ((GpaCardViewHolder) holder).bind(this.gpa);
        } else if (holder instanceof TabbarViewHolder) {
            ((TabbarViewHolder) holder).bind(this.selectedTab, this.listener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_GPA_CARD;
        if (position == 1) return TYPE_TAB_BAR;
        return TYPE_SUBJECT;
    }

    @Override
    public int getItemCount() {
        return 2 + gradeResponse.getData().size();
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
        notifyItemChanged(0);
    }

    public void setSelectedTab(int tab) {
        this.selectedTab = tab;
        notifyItemChanged(1); // position 1 là TabBar
    }

    static class GpaCardViewHolder extends RecyclerView.ViewHolder {
        TextView textViewGpa;

        public GpaCardViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewGpa = itemView.findViewById(R.id.gpa);
        }

        void bind(float gpa) {
            textViewGpa.setText(String.format("%.2f", gpa));
        }
    }

    static class TabbarViewHolder extends RecyclerView.ViewHolder {

        TabLayout tabLayout;

        public TabbarViewHolder(@NonNull View itemView) {
            super(itemView);

            tabLayout = itemView.findViewById(R.id.tabLayout);
        }

        void bind(int selectedTab, TabLayout.OnTabSelectedListener listener) {
            if (tabLayout.getTabCount() == 0) {
                tabLayout.addTab(tabLayout.newTab().setText("Học kì I"));
                tabLayout.addTab(tabLayout.newTab().setText("Học kì II"));
                tabLayout.addTab(tabLayout.newTab().setText("Tất cả học kì"));
            }

            // Xóa listener cũ trước khi select để tránh trigger không mong muốn
            tabLayout.clearOnTabSelectedListeners();

            TabLayout.Tab tab = tabLayout.getTabAt(selectedTab);
            if (tab != null && !tab.isSelected()) {
                tab.select();
            }

            tabLayout.addOnTabSelectedListener(listener);
        }
    }
}
