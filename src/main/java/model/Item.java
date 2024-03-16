package model;

import repository.ItemRepository;

public class Item {
    private String category;
    private String brand;
    private double price;
    private double mrp;
    public Item(String category, String brand, long mrp, long price) {
        this.category = category;
        this.brand = brand;
        this.mrp = mrp;
        this.price = price;
    }
    public Item(Item i) {
        this.category = i.getCategory();
        this.price = i.getPrice();
        this.mrp = i.getMrp();
        this.brand = i.getBrand();
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(long mrp) {
        this.mrp = mrp;
    }
}
