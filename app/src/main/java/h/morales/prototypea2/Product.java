package h.morales.prototypea2;

import android.net.Uri;

public class Product {
    String titleText;
    int productImage;

    String productName;
    String productMedium;
    String productPurchasePrice;
    String productHeight;
    String productWidth;
    String productDepth;
    String productLocation;
    String productPurchaseDate;
    String productNote;
    String productCategories;
    boolean productFramed;
    boolean productSold;
    boolean isOnWebStore;
    String productPicturePath;
    Uri prodUri;

    String creationDate;

    String prodID;

    boolean isSelected = false;

    public Product(String titleText, int productImage) { // TODO: needs to conform to db schema for each piece to be catalogued
        this.titleText = titleText;
        this.productImage = productImage;
    }

    public Product(String productName, String productMedium, String productPurchasePrice, String productHeight, String productWidth, String productDepth, String productLocation, String productPurchaseDate,String productNote, String productCategories, boolean productFramed, boolean productSold, boolean isOnWebStore, String productPicturePath,
                   String creationDate) {
        this.productName = productName;
        this.productMedium = productMedium;
        this.productPurchasePrice = productPurchasePrice;
        this.productHeight = productHeight;
        this.productWidth = productWidth;
        this.productDepth = productDepth;
        this.productLocation = productLocation;
        this.productPurchaseDate = productPurchaseDate;
        this.productNote = productNote;
        this.productCategories = productCategories;
        this.productFramed = productFramed;
        this.productSold = productSold;
        this.isOnWebStore = isOnWebStore;
        this.productPicturePath = productPicturePath;
        this.creationDate = creationDate;
    }

    public Product(String prodID, String productName, String productMedium, String productPurchasePrice, String productHeight, String productWidth, String productDepth, String productLocation, String productPurchaseDate, String productNote, String productCategories, boolean productFramed, boolean productSold, boolean isOnWebStore, String productPicturePath,
                   String creationDate) {
        this.prodID = prodID;
        this.productName = productName;
        this.productMedium = productMedium;
        this.productPurchasePrice = productPurchasePrice;
        this.productHeight = productHeight;
        this.productWidth = productWidth;
        this.productDepth = productDepth;
        this.productLocation = productLocation;
        this.productPurchaseDate = productPurchaseDate;
        this.productNote = productNote;
        this.productCategories = productCategories;
        this.productFramed = productFramed;
        this.productSold = productSold;
        this.isOnWebStore = isOnWebStore;
        this.productPicturePath = productPicturePath;
        this.creationDate = creationDate;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductMedium() {
        return productMedium;
    }

    public String getProductPurchasePrice() {
        return productPurchasePrice;
    }

    public String getProductHeight() {
        return productHeight;
    }

    public String getProductWidth() {
        return productWidth;
    }

    public String getProductDepth() {
        return productDepth;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public String getProductPurchaseDate() {
        return productPurchaseDate;
    }
    public String getProductNote() { return productNote; }
    public String getProductCategories() { return productCategories; }

    public boolean isProductFramed() {
        return productFramed;
    }
    public boolean isProductSold() {
        return productSold;
    }
    public boolean isOnWebStore() { return isOnWebStore;}

    public String getProductPicturePath() {
        return productPicturePath;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getProdID() { return prodID; }
}
