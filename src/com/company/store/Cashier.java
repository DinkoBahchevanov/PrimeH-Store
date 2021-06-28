package com.company.store;

import com.company.products.Appliance;
import com.company.products.Clothes;
import com.company.products.Product;
import com.company.products.perishable.Food;
import com.company.products.perishable.Perishable;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.company.products.constants.ProductTypeConstants.*;

public class Cashier {

    private double priceWithoutDiscount;
    private double discount;
    private double total;
    private StringBuilder receipt = new StringBuilder();
    private LinkedHashMap<String, Double> quantityMap = new LinkedHashMap<>();
    private LinkedHashMap<String, Double> discountMap = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> percentDiscountMap = new LinkedHashMap<>();

    /**
     * Here we do buy all products and then print the receipt.
     * Finally we do empty the cart
     * @param cart - cart full with products
     * @param localDateTime - current time of the purchase
     */
    public void doPurchase(Cart cart, LocalDateTime localDateTime) {
        priceWithoutDiscount = 0;
        total = 0;
        discount = 0;

        //getting the year, month and day as LocalDate
        LocalDate currentTime = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth());
        List<Product> products = cart.getProducts();

        //buying every kind of product
        for ( Product product : products ) {
            switch (product.getType()) {
                case FOOD, BEVERAGE -> buyPerishable(product, currentTime);
                case CLOTHES -> buyClothes(product, currentTime);
                case APPLIANCE -> buyAppliance(product, currentTime);
            }
        }

        fillReceipt(cart, localDateTime);
        cart.emptyCart();
    }

    /**
     * Here we do fill the receipt(stringBuilder) and finally we print it.
     * @param cart - cart with all the products in it
     * @param localDateTime - current time of the purchase
     */
    private void fillReceipt(Cart cart, LocalDateTime localDateTime) {
        Set<Product> uniqueProducts = new LinkedHashSet<>();
        Set<String> names = new LinkedHashSet<>();

        //all product names(non-repeated)
        for ( int i = 0; i < cart.getProducts().size(); i++ ) {
            names.add(cart.getProducts().get(i).getName());
        }

        //if set with unique names contains the current product name,
        // we add current product to uniqueProducts
        for ( int i = 0; i < cart.getProducts().size(); i++ ) {
            if (names.contains(cart.getProducts().get(i).getName())) {
                uniqueProducts.add(cart.getProducts().get(i));
                names.remove(cart.getProducts().get(i).getName());
            }
        }

        //now uniqueProducts has only 1 product of its kind (if there are 3 milks, uniqueProducts will contain only 1)

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        String strLocalDate = localDateTime + "";
        LocalDateTime localDate = LocalDateTime.parse(strLocalDate, formatter);

        receipt.append("Date: ")
                .append(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDate))
                .append(System.lineSeparator())
                .append("---Products---")
                .append(System.lineSeparator())
                .append(System.lineSeparator());

        //df for quantity/weight of products
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);

        //filling the receipt with data
        for ( Product uniqueProduct : uniqueProducts ) {

            receipt.append(uniqueProduct.getName()).append(" - ").append(uniqueProduct.getBrand());

            //if product is Clothes type add the size and colour
            if (uniqueProduct.getType().equals(CLOTHES)) {
                receipt.append(" ")
                        .append(((Clothes) uniqueProduct).getSize())
                        .append(" ")
                        .append(((Clothes) uniqueProduct).getColor());
            }

            //if product is Appliance type add the model
            if (uniqueProduct.getType().equals(APPLIANCE)) {
                receipt.append(" ")
                        .append(((Appliance) uniqueProduct).getModel());
            }

            receipt.append(System.lineSeparator());

            receipt.append(df.format(quantityMap.get(uniqueProduct.getName())))
                    .append(" x ").append(String.format("$%.2f", uniqueProduct.getPrice())).append(" = $")
                    .append(String.format("%.2f", quantityMap.get(uniqueProduct.getName()) * uniqueProduct.getPrice()))
                    .append(System.lineSeparator());

            if (discountMap.get(uniqueProduct.getName()) != 0) {
                receipt.append("#discount ").append(percentDiscountMap.get(uniqueProduct.getName()))
                        .append("% -$").append(String.format("%.2f", discountMap.get(uniqueProduct.getName())))
                        .append(System.lineSeparator());
            }
            receipt.append(System.lineSeparator());
            receipt.append(System.lineSeparator());
        }

        receipt.append("-----------------------------------------------------------------------------------")
                .append(System.lineSeparator()).append("SUBTOTAL: $").append(String.format("%.2f", priceWithoutDiscount))
                .append(System.lineSeparator()).append("DISCOUNT: -$").append(String.format("%.2f", discount))
                .append(System.lineSeparator())
                .append(System.lineSeparator()).append("TOTAL: $").append(String.format("%.2f", total));

        //printing the receipt
        System.out.println(receipt.toString());
    }

    /**
     * Here we buy every product of type Appliance and make a discount if needed.
     *
     * With quantityMap for every kind of product we do add quantity + 1
     * and we will use it later to print the receipt with needed quantity for every product type
     *
     * With discountMap for every kind of product we do add the discount in $ that is made
     * and we will use it later to print the receipt with needed discount $ for every product type
     *
     * With percentDiscountMap for every kind of product we do add the percentage of the discount that is made
     * and we will use it later to print the receipt with needed percentage of discount $ for every product type
     *
     * @param product - Product of type Clothes to be bought
     * @param currentTime - current time of purchase
     */
    private void buyAppliance(Product product, LocalDate currentTime) {
        DayOfWeek dayOfWeek = currentTime.getDayOfWeek();

        if (!quantityMap.containsKey(product.getName())) {
            discountMap.put(product.getName(), 0.0);
            quantityMap.put(product.getName(), 0.0);
            percentDiscountMap.put(product.getName(), 0);
        }

        //if
        if ((dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) && product.getPrice() > 999) {
            discount += 0.05 * product.getPrice();
            discountMap.put(product.getName(), discountMap.get(product.getName()) + 0.05 * product.getPrice());
            percentDiscountMap.put(product.getName(), 5);
            quantityMap.put(product.getName(), quantityMap.get(product.getName()) + 1);
            total += product.getPrice() - product.getPrice() * 0.05;
            priceWithoutDiscount += product.getPrice();
            return;
        }
        quantityMap.put(product.getName(), quantityMap.get(product.getName()) + 1);
        total += product.getPrice();
        priceWithoutDiscount += product.getPrice();
    }

    /**
     * Here we buy every product of type Clothes and make a discount if needed.
     *
     * With quantityMap for every kind of product we do add quantity + 1
     * and we will use it later to print the receipt with needed quantity for every product type
     *
     * With discountMap for every kind of product we do add the discount in $ that is made
     * and we will use it later to print the receipt with needed discount $ for every product type
     *
     * With percentDiscountMap for every kind of product we do add the percentage of the discount that is made
     * and we will use it later to print the receipt with needed percentage of discount $ for every product type
     *
     * @param product - Product of type Clothes to be bought
     * @param currentTime - current time of purchase
     */
    private void buyClothes(Product product, LocalDate currentTime) {
        DayOfWeek dayOfWeek = currentTime.getDayOfWeek();

        if (!quantityMap.containsKey(product.getName())) {
            discountMap.put(product.getName(), 0.0);
            quantityMap.put(product.getName(), 0.0);
            percentDiscountMap.put(product.getName(), 0);
        }

        //if there is any discount
        if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
            discount += 0.10 * product.getPrice();
            discountMap.put(product.getName(), discountMap.get(product.getName()) + 0.10 * product.getPrice());
            percentDiscountMap.put(product.getName(), 10);
            quantityMap.put(product.getName(), quantityMap.get(product.getName()) + 1);
            total += product.getPrice() - product.getPrice() * 0.10;
            priceWithoutDiscount += product.getPrice();
            return;
        }

        //if there is no discount
        quantityMap.put(product.getName(), quantityMap.get(product.getName()) + 1);
        total += product.getPrice();
        priceWithoutDiscount += product.getPrice();
    }

    /**
     * Here we buy every perishable product and make a discount if needed.
     *
     * With quantityMap for every kind of product we do add quantity + 1
     * and we will use it later to print the receipt with needed quantity for every product type
     *
     * With discountMap for every kind of product we do add the discount in $ that is made
     * and we will use it later to print the receipt with needed discount $ for every product type
     *
     * With percentDiscountMap for every kind of product we do add the percentage of the discount that is made
     * and we will use it later to print the receipt with needed percentage of discount $ for every product type
     *
     * @param product - perishable product Food/Beverage
     * @param currentTime - current time of the purchase
     */
    private void buyPerishable(Product product, LocalDate currentTime) {
        LocalDate expirationDate = ((Perishable) product).getExpirationDate();
        double weight;

        if (!quantityMap.containsKey(product.getName())) {
            discountMap.put(product.getName(), 0.0);
            quantityMap.put(product.getName(), 0.0);
            percentDiscountMap.put(product.getName(), 0);
        }

        if (product instanceof Food) {
            weight = ((Food) product).getWeight();
            quantityMap.put(product.getName(), weight);
        } else weight = 1;


        //if there is any discount
        if (expirationDate.getDayOfYear() == currentTime.getDayOfYear()
                && expirationDate.getMonth() == currentTime.getMonth()
                && expirationDate.getDayOfMonth() - currentTime.getDayOfMonth() <= 5) {

            if (expirationDate.getDayOfMonth() - currentTime.getDayOfMonth() > 0) {
                discount += 0.10 * product.getPrice() * weight;
                discountMap.put(product.getName(), discountMap.get(product.getName()) + 0.10 * product.getPrice() * weight);
                percentDiscountMap.put(product.getName(), 10);
                total += product.getPrice() - 0.10 * product.getPrice() * weight;
            }
            priceWithoutDiscount += product.getPrice() * weight;
            total += (product.getPrice() * weight) / 2;
            discount += (product.getPrice() * weight) / 2;
            discountMap.put(product.getName(), discountMap.get(product.getName()) + (product.getPrice() * weight) / 2);
            percentDiscountMap.put(product.getName(), 50);
            return;
        }

        //if there is no discount
        quantityMap.put(product.getName(), quantityMap.get(product.getName()) + 1);
        total += product.getPrice() * weight;
        priceWithoutDiscount += product.getPrice() * weight;
    }
}
