package com.example.triversitylingo2.Models;

public class Model {
    private String customerEmail;
    private String customerId;
    private String customerName;
    private String customerPassword;

    public Model() {
        // Default constructor required for Firebase
    }

    public Model(String customerEmail, String customerId, String customerName, String customerPassword) {
        this.customerEmail = customerEmail;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPassword = customerPassword;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

}
