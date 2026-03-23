package com.example.studentmanager.DTOs;

public class PaymentInvoice extends BaseModel{

    public int getFee_id() {
        return fee_id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public Status getStatus() {
        return status;
    }

    public enum Status {
        paid, unpaid;
    }

    private int fee_id;
    private int student_id;
    private Status status;
}
