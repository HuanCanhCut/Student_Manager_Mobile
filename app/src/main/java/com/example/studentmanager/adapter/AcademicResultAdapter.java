package com.example.studentmanager.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.DTOs.GradeWithSubject;
import com.example.studentmanager.R;
import com.example.studentmanager.network.DTOs.response.GradeResponse;
import com.google.android.material.tabs.TabLayout;

public class AcademicResultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private GradeResponse gradeResponse;
    private float gpa;

    private static final int TYPE_GPA_CARD = 0;
    private static final int TYPE_TAB_BAR = 1;
    private static final int TYPE_SUBJECT = 2;

    private int selectedTab = 0;

    private TabLayout.OnTabSelectedListener listener;

    private Integer currentSemester;

    public AcademicResultAdapter(float gpa, TabLayout.OnTabSelectedListener listener, Integer semester) {
        this.gpa = gpa;
        this.listener = listener;
        this.currentSemester = semester;
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
            case TYPE_SUBJECT:
                return new SubjectViewHolder(inflater.inflate(R.layout.item_subject_card, parent, false));
            default:
                return new GpaCardViewHolder(inflater.inflate(R.layout.item_gpa_card, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GpaCardViewHolder) {
            ((GpaCardViewHolder) holder).bind(this.gpa);
        } else if (holder instanceof TabbarViewHolder) {
            ((TabbarViewHolder) holder).bind(this.selectedTab, this.listener, this.currentSemester);
        } else if (holder instanceof SubjectViewHolder) {
            GradeWithSubject grade = this.gradeResponse.getData().get(position - 2);

            ((SubjectViewHolder) holder).bind(grade);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("HomeAdapter", "getItemViewType pos=" + position);

        if (position == 0) return TYPE_GPA_CARD;
        if (position == 1) return TYPE_TAB_BAR;
        return TYPE_SUBJECT;
    }

    @Override
    public int getItemCount() {
        int count = (gradeResponse == null || gradeResponse.getData() == null)
                ? 2
                : 2 + gradeResponse.getData().size();
        return count;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
        notifyItemChanged(0);
    }

    public void setSelectedTab(int tab) {
        this.selectedTab = tab;
        notifyItemChanged(1); // position 1 là TabBar
    }

    public void setGradeResponse(GradeResponse gradeResponse) {
        this.gradeResponse = gradeResponse;

        notifyDataSetChanged();
    }

    public void setCurrentSemester(Integer currentSemester) {
        this.currentSemester = currentSemester;

        notifyItemChanged(1);
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
        TextView tabTitle;

        public TabbarViewHolder(@NonNull View itemView) {
            super(itemView);

            tabLayout = itemView.findViewById(R.id.tabLayout);
            tabTitle = itemView.findViewById(R.id.tabTitle);
        }

        void bind(int selectedTab, TabLayout.OnTabSelectedListener listener, Integer semester) {
            if (semester != null) {
                tabTitle.setText("Học kì " + semester);
            } else {
                tabTitle.setText("Tất cả học kỳ");
            }

            if (tabLayout.getTabCount() == 0) {
                tabLayout.addTab(tabLayout.newTab().setText("Hiện tại"));
                tabLayout.addTab(tabLayout.newTab().setText("Trước đó"));
                tabLayout.addTab(tabLayout.newTab().setText("Tất cả học kỳ"));
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

    static class SubjectViewHolder extends RecyclerView.ViewHolder {

        TextView name, code, credit, letterGrade, componentGrade, gradeText, gradeResult;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            code = itemView.findViewById(R.id.code);
            credit = itemView.findViewById(R.id.credit);
            letterGrade = itemView.findViewById(R.id.letterGrade);
            componentGrade = itemView.findViewById(R.id.componentGrade);
            gradeText = itemView.findViewById(R.id.grade);
            gradeResult = itemView.findViewById(R.id.gradeResult);
        }

        private String getLetterGrade(double score) {
            if (score >= 8.5) {
                return "A";
            } else if (score >= 8.0) {
                return "B+";
            } else if (score >= 7.0) {
                return "B";
            } else if (score >= 6.5) {
                return "C+";
            } else if (score >= 5.5) {
                return "C";
            } else if (score >= 5.0) {
                return "D+";
            } else if (score >= 4.0) {
                return "D";
            } else {
                return "F";
            }
        }

        void bind(GradeWithSubject grade) {
            Float firstComponentGrade = grade.getFirst_component_grade();
            Float secondComponentGrade = grade.getSecond_component_grade();
            Float exam = grade.getGrade();

            float totalResult = 0;

            if (firstComponentGrade != null && secondComponentGrade != null && exam != null) {
                totalResult = firstComponentGrade * 0.25f + secondComponentGrade * 0.25f + exam * 0.5f;
            }

            name.setText(grade.getSubject().getName());
//            code.setText(grade.getSubject().getCode());
            credit.setText(String.format("• %s tín chỉ", grade.getSubject().getCredit()));

            if (totalResult == 0) {
                letterGrade.setText("-");
            } else {
                letterGrade.setText(getLetterGrade(totalResult));
            }

            String first = (firstComponentGrade != null) ? String.valueOf(firstComponentGrade) : "-";
            String second = (secondComponentGrade != null) ? String.valueOf(secondComponentGrade) : "-";

            if (first.equals("-") && second.equals("-")) {
                componentGrade.setText("Chờ điểm");
            } else {
                componentGrade.setText(String.format("%s | %s", first, second));
            }

            if (grade.getGrade() == null) {
                gradeText.setText(String.format("%s/10", "Chờ điểm"));
            } else {
                gradeText.setText(String.valueOf(grade.getGrade()));
            }

            if (totalResult == 0) {
                gradeResult.setText(String.valueOf("--"));
            } else {
                gradeResult.setText(String.valueOf(totalResult));
            }
        }
    }
}
