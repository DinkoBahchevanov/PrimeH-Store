package com.company.products.perishable;

import java.time.LocalDate;

public class Food extends Perishable {

    private LocalDate expirationDate;
    private double weight = 1;

    // here we do not have weight
    public Food(String name, String brand, double price, LocalDate expirationDate, String type) {
        super(name, brand, price, type);
        this.expirationDate = expirationDate;
    }

    //here we add possible weight of product (vegetables or fruits for example)
    public Food(String name, String brand, double price, LocalDate expirationDate, String type, double weight) {
        super(name, brand, price, type);
        this.expirationDate = expirationDate;
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }
}
