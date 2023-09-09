package h.morales.prototypea2;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Locale;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> implements Filterable {

    Context context;
    ArrayList<Product> productArrayList;
    ArrayList<Product> productArrayList2;

    ArrayList<Product> multiEditList;

    private final RecyclerViewInterface recyclerViewInterface;
    public RecyclerAdapter(Context context, ArrayList<Product> productArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.productArrayList2 = new ArrayList<>(productArrayList);
        this.recyclerViewInterface = recyclerViewInterface;
        this.multiEditList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = productArrayList.get(position);
        holder.textView.setText(product.getProductName());
        holder.itemPurchasePrice.setText("$" + String.valueOf(product.getProductPurchasePrice()));
        if(product.isProductSold()) {
            //set to gray if sold
            holder.itemSold.setColorFilter(context.getResources().getColor(R.color.black));
        } else {
            // set to green if not sold
            holder.itemSold.setColorFilter(context.getResources().getColor(R.color.teal_700));
        }

        //Uri imgUri = Uri.parse(product.productPicturePath);
        //holder.titleImage.setImageURI(null);
        //holder.titleImage.setImageURI(Uri.parse(product.productPicturePath));
        Glide.with(context).load(Uri.parse(product.productPicturePath)).into(holder.titleImage);
        Log.d(TAG, "onBindViewHolder: recycler debug, name and path: " +product.getProductName() + " path: " + product.getProductPicturePath());
        //holder.titleImage.setImageResource(product.productImage);
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public void updateData(ArrayList<Product> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ProductDiffCallback(productArrayList, newData));
        productArrayList.clear();
        productArrayList.addAll(newData);
        //notifyDataSetChanged();

        Log.d(TAG, "updateData: seeing if updatedata is CALLED: size: " + productArrayList.size());
        Log.d(TAG, "updateData: newData: size: " + newData.size());
        //Log.d(TAG, "updateData:  productname: " + productArrayList.get(0).getProductName());
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    public Filter decideFilter(int sortType) {
       if(sortType == 0) {
           Log.d(TAG, "decideFilter: chose name");
           return nameFilter;
       } else {
           Log.d(TAG, "decideFilter: chose category");
          return categoryFilter;
       }
    }

    private final Filter nameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Product> filteredNameList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                filteredNameList.addAll(productArrayList);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for(Product prods : productArrayList) {
                    if(prods.getProductName().toLowerCase().contains(filterPattern)) {
                        Log.d(TAG, "performFiltering: product name: " + prods.getProductName());
                        filteredNameList.add(prods); // matching result so add to arraylist
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredNameList;
            results.count = filteredNameList.size();

            return results;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            productArrayList2.clear();
            productArrayList2.addAll((ArrayList)filterResults.values);
            updateData(productArrayList2);
            notifyDataSetChanged();

        }
    };

    //filter for category searching

    private final Filter categoryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Product> filteredCategoryList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0) {
                filteredCategoryList.addAll(productArrayList); // just return the old list
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                //iterate through list and collect matching queries
                for(Product prods : productArrayList) {
                    if(prods.getProductCategories().toLowerCase().contains(filterPattern)) {
                        filteredCategoryList.add(prods); // match found, add to list
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredCategoryList;
            results.count = filteredCategoryList.size();
            return results;
            //return null;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productArrayList2.clear();
            productArrayList2.addAll((ArrayList)results.values);
            updateData(productArrayList2);

        }
    };


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView titleImage;
        TextView textView;
        TextView itemPurchasePrice;
        ImageView itemSold;
        ImageView itemSelectedMark;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            titleImage = itemView.findViewById(R.id.title_image);
            textView = itemView.findViewById(R.id.itemText);
            itemPurchasePrice = itemView.findViewById(R.id.itemPurchasePriceTV);
            itemSold = itemView.findViewById(R.id.itemSoldIV);
            itemSelectedMark = itemView.findViewById(R.id.itemSelectedIV);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null) {
                        //
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    if(recyclerViewInterface != null) {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION) {

                            //Toast.makeText(v.getContext(), "LONG CLICK", Toast.LENGTH_LONG).show();
                            recyclerViewInterface.onLongClick(pos, itemSelectedMark);
                        }
                    }

                    return false;
                }
            });

        }
        //
    }
}
