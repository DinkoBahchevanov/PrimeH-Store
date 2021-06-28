package com.company.products;

import com.company.products.constants.Size;

public class Clothes extends Product {

    private Size size;
    private String color;

    public Clothes(String name, String brand, double price, Size size, String color, String type) {
        super(name, brand, price, type);
        this.size = size;
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

}
