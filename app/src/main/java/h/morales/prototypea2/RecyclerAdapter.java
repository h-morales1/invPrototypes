package h.morales.prototypea2;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    Context context;
    ArrayList<Product> productArrayList;

    private final RecyclerViewInterface recyclerViewInterface;
    public RecyclerAdapter(Context context, ArrayList<Product> productArrayList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.recyclerViewInterface = recyclerViewInterface;
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
        diffResult.dispatchUpdatesTo(this);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView titleImage;
        TextView textView;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            titleImage = itemView.findViewById(R.id.title_image);
            textView = itemView.findViewById(R.id.itemText);

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
        }
        //
    }
}
