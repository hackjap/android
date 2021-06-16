package com.example.custom_exer;

public class Item {
    String subject;
    int progress;
    String updated;

    public Item(){

    }

    public Item(String details, int amount, String updated) {
        this.subject = details;
        this.progress = amount;
        this.updated = updated;
    }
}
