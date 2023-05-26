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
    private final MutableLiveData<String> prodIsFramed = new MutableLiveData<String>();

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

    public void setIsFramed(String item) {
        prodIsFramed.setValue(item);
    }

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

    public LiveData<String> getIsFramed() {
        return prodIsFramed;
    }

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
