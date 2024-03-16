package model;

public class SearchCriteria {
    String category;
    String brand;

    public SearchCriteria(String category, String brand) {
        this.category = category;
        this.brand = brand;
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
}
