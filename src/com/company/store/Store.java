package com.company.store;

import com.company.products.constants.Size;
import com.company.products.Appliance;
import com.company.products.Clothes;
import com.company.products.Product;
import com.company.products.perishable.Beverage;
import com.company.products.perishable.Food;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.company.products.constants.ProductTypeConstants.*;

public class Store {

    private Cashier cashier;
    private Cart cart;
    private final LocalDateTime localDateTime =
            LocalDateTime.of(LocalDate.of(2021,6,14), LocalTime.of(12,34,56));

    public Store() {
        cashier = new Cashier();
        cart = new Cart();
    }

    /**
     * Filling the cart with different products
     */
    public void fillCart() {
        addProductInTheCart(new Food("apples", "BrandA", 1.50, LocalDate.of(2021,6,14), FOOD, 2.45));
        addProductInTheCart(new Beverage("milk", "BrandM", 0.99, LocalDate.of(2022,2,2), BEVERAGE));
        addProductInTheCart(new Beverage("milk", "BrandM", 0.99, LocalDate.of(2022,2,2), BEVERAGE));
        addProductInTheCart(new Beverage("milk", "BrandM", 0.99, LocalDate.of(2022,2,2), BEVERAGE));
        addProductInTheCart(new Clothes("T-shirt", "BrandT", 15.99, Size.M, "violet", CLOTHES));
        addProductInTheCart(new Clothes("T-shirt", "BrandT", 15.99, Size.M, "violet", CLOTHES));
        addProductInTheCart(new Appliance("laptop", "BrandL", 2345, "ModelL", LocalDate.of(2021,3,3), 1.125, APPLIANCE));

        cashier.doPurchase(cart, localDateTime);
    }

    /**
     * Adding every product to the cart
     * @param product - current product to add in the cart
     */
    private void addProductInTheCart(Product product) {
        cart.addProduct(product);
    }
}
