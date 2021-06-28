package com.company.store;

import com.company.products.Product;

import java.util.ArrayList;

public class Cart {

    private ArrayList<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public ArrayList<Product> getProducts() {
        return this.products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void emptyCart() {
        this.products.clear();
    }
}
