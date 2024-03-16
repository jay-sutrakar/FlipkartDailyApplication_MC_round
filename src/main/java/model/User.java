package model;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String name;
    private String email;
    private double walletAmount;
    private Map<Item, Long> cart;

    public User(String name, String email, double walletAmount) {
        this.name = name;
        this.email = email;
        this.walletAmount = walletAmount;
        this.cart = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getWalletAmount() {
        return walletAmount;
    }

    public void setWalletAmount(double walletAmount) {
        this.walletAmount = walletAmount;
    }

    public Map<Item, Long> getCart() {
        return cart;
    }

    public void setCart(Map<Item, Long> cart) {
        this.cart = cart;
    }
}
