package com.yishak.smm_assessment.model;

public class Investment {
    private String name;
    private int quantity;
    private double amount;


    public Investment(String name, int quantity, double amount){
        this.name = name;
        this.quantity = quantity;
        this.amount = amount;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public double getAmount(){
        return amount;
    }

    public String getStringAmount(){
        return String.format("R$ %,.2f", amount);
    }
}

