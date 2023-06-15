package h.morales.prototypea2;

import androidx.recyclerview.widget.DiffUtil;
import java.util.ArrayList;

/*
    Handle comparing array of old products versus a new array
    and update in as little steps the recycler view as possible,
    limiting lag
 */
public class ProductDiffCallback extends DiffUtil.Callback {
    private ArrayList<Product> oldList;
    private ArrayList<Product> newList;

    public ProductDiffCallback(ArrayList<Product> oldList, ArrayList<Product> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Product oldProduct = oldList.get(oldItemPosition);
        Product newProduct = newList.get(newItemPosition);
        // Compare the unique identifiers of the products
        return oldProduct.getProdID().equals(newProduct.getProdID());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Product oldProduct = oldList.get(oldItemPosition);
        Product newProduct = newList.get(newItemPosition);
        // Compare all relevant fields of the products
        return oldProduct.getProductName().equals(newProduct.getProductName())
                && oldProduct.getProductMedium().equals(newProduct.getProductMedium())
                && oldProduct.getProductPurchasePrice() == newProduct.getProductPurchasePrice();
        // Compare other fields here...
    }
}

