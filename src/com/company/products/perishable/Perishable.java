package com.company.products.perishable;

import com.company.products.Product;

import java.time.LocalDate;

public abstract class Perishable extends Product {

    public Perishable(String name, String brand, double price, String type) {
        super(name, brand, price, type);
    }

    public abstract LocalDate getExpirationDate();
}
