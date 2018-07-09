package com.example.amit.todoapps;

/**
 * Created by Amit on 7/18/2017.
 */

public class Product {
    String title,description,date1,date2;

    public Product(String title, String description, String date1, String date2) {
        this.setTitle(title);
        this.setDescription(description);
        this.setDate1(date1);
        this.setDate2(date2);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }
}
