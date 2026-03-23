package com.example.studentmanager.network.DTOs.response;

import com.example.studentmanager.DTOs.FeeModel;
import com.example.studentmanager.DTOs.PaymentInvoice;
import com.example.studentmanager.DTOs.SemesterModel;
import com.example.studentmanager.network.services.SemesterService;

import java.util.ArrayList;
import java.util.List;

public class GetFeeResponse {
    private List<Data> data = new ArrayList<>();

    public List<Data> getData() {
        return data;
    }

    public static class Data extends FeeModel {
        private List<PaymentInvoice> payment_invoices = new ArrayList<>();
        private SemesterModel semester;


        public List<PaymentInvoice> getPayment_invoices() {
            return payment_invoices;
        }

        public SemesterModel getSemester() {
            return semester;
        }
    }
}
