package com.example.mobiiliprojekti;

public class Notes {
    private int id, image;
    private String title, desc;
    private double price, rating;

    public Notes(int id, String title, String desc, double rating, double price, int image) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }
}
