package h.morales.prototypea2;

import android.net.Uri;

public class Product {
    String titleText;
    int productImage;

    String productName;
    String productMedium;
    float productPurchasePrice;
    float productHeight;// TODO: change product HWD to strings
    float productWidth;
    float productDepth;
    String productLocation;
    String productPurchaseDate;
    boolean productFramed;
    String productPicturePath;
    Uri prodUri;

    String creationDate;

    public Product(String titleText, int productImage) { // TODO: needs to conform to db schema for each piece to be catalogued
        this.titleText = titleText;
        this.productImage = productImage;
    }

    public Product(String productName, String productMedium, float productPurchasePrice, float productHeight, float productWidth, float productDepth, String productLocation, String productPurchaseDate, boolean productFramed, String productPicturePath,
                   String creationDate) {
        this.productName = productName;
        this.productMedium = productMedium;
        this.productPurchasePrice = productPurchasePrice;
        this.productHeight = productHeight;
        this.productWidth = productWidth;
        this.productDepth = productDepth;
        this.productLocation = productLocation;
        this.productPurchaseDate = productPurchaseDate;
        this.productFramed = productFramed;
        this.productPicturePath = productPicturePath;
        this.creationDate = creationDate;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductMedium() {
        return productMedium;
    }

    public float getProductPurchasePrice() {
        return productPurchasePrice;
    }

    public float getProductHeight() {
        return productHeight;
    }

    public float getProductWidth() {
        return productWidth;
    }

    public float getProductDepth() {
        return productDepth;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public String getProductPurchaseDate() {
        return productPurchaseDate;
    }

    public boolean isProductFramed() {
        return productFramed;
    }

    public String getProductPicturePath() {
        return productPicturePath;
    }

    public String getCreationDate() {
        return creationDate;
    }
}
