package com.example.studentmanager.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentmanager.R;
import com.example.studentmanager.network.DTOs.response.GetFeeResponse;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.Locale;

public class FeeAdapter extends RecyclerView.Adapter<FeeAdapter.FeeViewHolder> {
    private GetFeeResponse feeResponse = new GetFeeResponse();

    public void setFeeResponse(GetFeeResponse feeResponse) {
        this.feeResponse = feeResponse;
        Log.d("DATA", new Gson().toJson(feeResponse.getData()));
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new FeeViewHolder(inflater.inflate(R.layout.item_fee, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FeeViewHolder holder, int position) {
        holder.bind(feeResponse.getData().get(position));
    }

    @Override
    public int getItemCount() {
        return feeResponse.getData() != null ? feeResponse.getData().size() : 0;
    }

    static class FeeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus, tvSemester, tvAmount;

        public FeeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = (TextView) ((ViewGroup) ((ViewGroup) itemView).getChildAt(1)).getChildAt(0);
            tvStatus = (TextView) ((ViewGroup) ((ViewGroup) itemView).getChildAt(1)).getChildAt(1);
            tvSemester = (TextView) ((ViewGroup) ((ViewGroup) itemView).getChildAt(1)).getChildAt(2);
            tvAmount = (TextView) ((ViewGroup) itemView).getChildAt(2);
        }

        void bind(GetFeeResponse.Data data) {
            tvTitle.setText("Học phí tín chỉ");
            
            if (data.getPayment_invoices() != null && !data.getPayment_invoices().isEmpty()) {
                tvStatus.setText("• Đã đóng");
                tvStatus.setTextColor(Color.parseColor("#16A34A"));
            } else {
                tvStatus.setText("• Chưa đóng");
                tvStatus.setTextColor(Color.RED);
            }

            tvSemester.setText(data.getSemester().getName());

            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            tvAmount.setText(currencyVN.format(data.getMoney()));
        }
    }
}
