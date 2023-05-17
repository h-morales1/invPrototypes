package h.morales.prototypea2;

public class Product {
    String titleText;
    int productImage;

    public Product(String titleText, int productImage) { // TODO: needs to conform to db schema for each piece to be catalogued
        this.titleText = titleText;
        this.productImage = productImage;
    }

}
