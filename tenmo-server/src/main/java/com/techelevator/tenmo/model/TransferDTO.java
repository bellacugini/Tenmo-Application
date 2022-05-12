package com.techelevator.tenmo.model;

public class TransferDTO {
    long transfer_id;
    long user_id_to;
    double amount;
    long user_id_from;
    int transfer_type_id;
    int transfer_status_id;

    public long getTransfer_id() {
        return transfer_id;
    }

    public void setTransfer_id(long transfer_id) {
        this.transfer_id = transfer_id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getUser_id_to() {
        return user_id_to;
    }

    public void setUser_id_to(long user_id_to) {
        this.user_id_to = user_id_to;
    }

    public long getUser_id_from() {
        return user_id_from;
    }

    public void setUser_id_from(long user_id_from) {
        this.user_id_from = user_id_from;
    }

    public int getTransfer_type_id() {
        return transfer_type_id;
    }

    public void setTransfer_type_id(int transfer_type_id) {
        this.transfer_type_id = transfer_type_id;
    }

    public int getTransfer_status_id() {
        return transfer_status_id;
    }

    public void setTransfer_status_id(int transfer_status_id) {
        this.transfer_status_id = transfer_status_id;
    }
}
