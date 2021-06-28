package com.company.products;

import java.time.LocalDate;

public class Appliance extends Product {

    private String model;
    private LocalDate productionDate;
    private double weight;

    public Appliance(String name, String brand, double price, String model,
                     LocalDate productionDate, double weight, String type) {
        super(name, brand, price, type);
        this.model = model;
        this.productionDate = productionDate;
        this.weight = weight;
    }

    public String getModel() {
        return model;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public double getWeight() {
        return weight;
    }
}
