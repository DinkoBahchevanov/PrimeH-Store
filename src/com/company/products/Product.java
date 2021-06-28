package com.company.products;

public abstract class Product {

    private String name;
    private String brand;
    private double price;
    private final String type;

    public Product(String name, String brand, double price, String type) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }
}
