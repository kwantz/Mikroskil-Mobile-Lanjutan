package com.example.you.dummyonlineshop.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Transaction {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("invoice")
    @Expose
    private String invoice;

    @SerializedName("receipt_number")
    @Expose
    private String receiptNumber;

    @SerializedName("address")
    @Expose
    private Address address;

    @SerializedName("cart")
    @Expose
    private List<Cart> cart;

    @SerializedName("payment_status")
    @Expose
    private String paymentStatus;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("total")
    @Expose
    private int total;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Cart> getCart() {
        return cart;
    }

    public void setCart(List<Cart> cart) {
        this.cart = cart;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
