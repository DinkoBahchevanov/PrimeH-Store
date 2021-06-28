package com.company.products.perishable;

import java.time.LocalDate;

public class Beverage extends Perishable {

    private LocalDate expirationDate;

    public Beverage(String name, String brand, double price, LocalDate expirationDate, String type) {
        super(name, brand, price, type);
        this.expirationDate = expirationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }
}
