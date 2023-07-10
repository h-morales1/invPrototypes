package h.morales.prototypea2;

import android.database.Observable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ItemViewModel  extends ViewModel {

    private final MutableLiveData<String> prodName = new MutableLiveData<String>();
    private final MutableLiveData<String> prodMedium = new MutableLiveData<String>();
    private final MutableLiveData<String> prodPurchasePrice = new MutableLiveData<String>();
    private final MutableLiveData<String> prodHeight = new MutableLiveData<String>();
    private final MutableLiveData<String> prodDepth = new MutableLiveData<String>();
    private final MutableLiveData<String> prodWidth = new MutableLiveData<String>();
    private final MutableLiveData<String> prodLocation = new MutableLiveData<String>();
    private final MutableLiveData<String> prodPurchaseDate = new MutableLiveData<String>();
    private final MutableLiveData<String> prodNote = new MutableLiveData<String>();
    private final MutableLiveData<String> prodIsFramed = new MutableLiveData<String>();
    private final MutableLiveData<String> prodSold = new MutableLiveData<String>();
    private final MutableLiveData<String> prodIsOnWebStore = new MutableLiveData<>();

    private final MutableLiveData<String> prodUri = new MutableLiveData<>();
    private final MutableLiveData<String> prodCreationDate = new MutableLiveData<>();
    private final MutableLiveData<String> prodCategories = new MutableLiveData<>();

    //this doesnt need to be saved to the db, its only useful to retrieve from the db to use
    // in other fragments like editItem
    private final MutableLiveData<String> prodID = new MutableLiveData<>();

    private final MutableLiveData<Boolean> saveToDB = new MutableLiveData<>();
    private final MutableLiveData<Boolean> saveToArchive = new MutableLiveData<>();

    public void setData(String pName, String pMedium, String pPurchasePrice, String pHeight, String pDepth
                        , String pWidth, String pLocation, String pPurchaseDate) {
        prodName.setValue(pName);
        prodMedium.setValue(pMedium);
        prodPurchasePrice.setValue(pPurchasePrice);
        prodHeight.setValue(pHeight);
        prodDepth.setValue(pDepth);
        prodWidth.setValue(pWidth);
        prodLocation.setValue(pLocation);
        prodPurchaseDate.setValue(pPurchaseDate);
    }

    //setters
    public void setName(String item) {
        prodName.setValue(item);
    }
    public void setMedium(String item) {
        prodMedium.setValue(item);
    }

    public void setPurchasePrice(String item) {
        prodPurchasePrice.setValue(item);
    }

    public void setHeight(String item) {
        prodHeight.setValue(item);
    }

    public void setDepth(String item) {
        prodDepth.setValue(item);
    }

    public void setWidth(String item) {
        prodWidth.setValue(item);
    }

    public void setLocation(String item) {
        prodLocation.setValue(item);
    }

    public void setPurchaseDate(String item) {
        prodPurchaseDate.setValue(item);
    }
    public void setNote(String item) { prodNote.setValue(item); }
    public void setProdCategories(String item) { prodCategories.setValue(item); }

    public void setIsFramed(String item) {
        prodIsFramed.setValue(item);
    }

    public void setSold(String item) {
        prodSold.setValue(item);
    }
    public void setProdIsOnWebStore(String item) { prodIsOnWebStore.setValue(item); }

    public void setProdUri(String item) {
        prodUri.setValue(item);
    }

    public void setProdCreationDate(String item) { prodCreationDate.setValue(item); }

    public void setProdID(String item) { prodID.setValue(item); }

    public void setSaveToDB(Boolean bool) { saveToDB.setValue(bool);}
    public void setSaveToArchive(Boolean bool) { saveToArchive.setValue(bool); }

    //getters

    public LiveData<String> getName() {
        return prodName;
    }
    public LiveData<String> getMedium() {
        return prodMedium;
    }


    public LiveData<String> getPurchasePrice() {
        return prodPurchasePrice;
    }
    public LiveData<String> getHeight() {
        return prodHeight;
    }
    public LiveData<String> getDepth() {
        return prodDepth;
    }
    public LiveData<String> getLocation() {
        return prodLocation;
    }
    public LiveData<String> getWidth() {
        return prodWidth;
    }
    public LiveData<String> getPurchaseDate() {
        return prodPurchaseDate;
    }
    public LiveData<String> getNote() { return prodNote; }
    public LiveData<String> getCategories() { return prodCategories; }

    public LiveData<String> getIsFramed() {
        return prodIsFramed;
    }
    public LiveData<String> getIsSold() { return prodSold; }
    public LiveData<String> getIsOnWebStore() { return prodIsOnWebStore; }

    public LiveData<Boolean> getSaveToDB() {
        return saveToDB;
    }
    public LiveData<Boolean> getSaveToArchive() { return saveToArchive; }

    public LiveData<String> getProdUri () {
        return prodUri;
    }

    public LiveData<String> getProdID() { return prodID; }

    public LiveData<String> getProdCreationDate () { return prodCreationDate; }

    public MutableLiveData<ArrayList<LiveData<String>>> getAll() {
        MutableLiveData<ArrayList<LiveData<String>>> prods = new MutableLiveData<ArrayList<LiveData<String>>>();
        ArrayList<LiveData<String>> product = new ArrayList<>();

        product.add(prodName);
        product.add(prodMedium);
        product.add(prodPurchasePrice);
        product.add(prodHeight);
        product.add(prodDepth);
        product.add(prodWidth);
        product.add(prodLocation);
        product.add(prodPurchaseDate);

        prods.setValue(product);

        return prods;

    }


}
