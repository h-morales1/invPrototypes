package h.morales.prototypea2;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ItemViewModel homeItemViewModel;

    public static int productID; // set after retrieving from previous fragment, used for deletion query

    DataBaseManager dataBaseManager;

    TextView title,
            medium,
            purchasePrice,
            hwd,
            location,
            purchaseDate,
            creationDate,
            note,
            framed,
            sold,
            onWebStore,
            categories;

    ImageView pieceIV;

    String hwdCombo = "";
    String h = "";
    String w = "";
    String d = "";

    public ViewItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewItemFragment newInstance(String param1, String param2) {
        ViewItemFragment fragment = new ViewItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        //turn on menu for edit and delete options
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_item, container, false);
        //return inflater.inflate(R.layout.fragment_view_item, container, false);

        title = (TextView) view.findViewById(R.id.titleTV);
        medium = (TextView) view.findViewById(R.id.mediumTV);
        purchasePrice = (TextView) view.findViewById(R.id.purchasePriceTV);
        hwd = (TextView) view.findViewById(R.id.HWDTV);
        location = (TextView) view.findViewById(R.id.locationTV);
        purchaseDate = (TextView) view.findViewById(R.id.purchaseDateTV);
        creationDate = (TextView) view.findViewById(R.id.creationDateTV);
        note = (TextView) view.findViewById(R.id.viewItemNoteTV);
        framed = (TextView) view.findViewById(R.id.framedTV);
        sold = (TextView) view.findViewById(R.id.viewItemSoldTV);
        onWebStore = (TextView) view.findViewById(R.id.viewItemIsOnWebStoreTV);
        categories = (TextView) view.findViewById(R.id.viewItemCategoriesTV);

        pieceIV = (ImageView) view.findViewById(R.id.pieceIV);


        //observe addItem fragment fields
        //homeItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        homeItemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        /*itemViewModel.getSelectedData().observe(this, item -> {
            //
            Log.d(TAG, "onCreate: item = " + item);
        });*/

        homeItemViewModel.getProdID().observe(getViewLifecycleOwner(),  item -> {
            //retrieve product ID to use in deletion query
            productID = Integer.parseInt(item);
            Log.d(TAG, "viewItem: id= " + item);

        });
        homeItemViewModel.getName().observe(getViewLifecycleOwner(), item -> {
            //
            Log.d(TAG, "onCreate: item = " + item.toString());
            title.setText(item);
            //pName = item;
        });

        homeItemViewModel.getMedium().observe(getViewLifecycleOwner(), item -> {

            Log.d(TAG, "onCreate: item = " + item.toString());
            medium.setText(item);
            //pMedium = item;
        });

        homeItemViewModel.getPurchasePrice().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            purchasePrice.setText(item);
            //pPurchasePrice = Float.parseFloat(item);
        });

        homeItemViewModel.getHeight().observe(getViewLifecycleOwner(), item -> {
            //
            Log.d(TAG, "onCreate: item = " + item.toString());
            h = item;
            //pHeight = Float.parseFloat(item);
        });

        homeItemViewModel.getDepth().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            d = item;
            //pDepth = Float.parseFloat(item);
        });

        homeItemViewModel.getLocation().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            location.setText(item);
            //pLocation = item;
        });

        homeItemViewModel.getWidth().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            w = item;
            //pWidth = Float.parseFloat(item);
        });

        homeItemViewModel.getPurchaseDate().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            purchaseDate.setText(item);
            //pPurchaseDate = item;
        });

        homeItemViewModel.getProdUri().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            pieceIV.setImageURI(Uri.parse(item));

        });

        homeItemViewModel.getProdCreationDate().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem prodCreationDate: item = " + item.toString());
            creationDate.setText(item);
        });

        homeItemViewModel.getNote().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem Note: item = " + item.toString());
            note.setText(item);
        });

        homeItemViewModel.getIsOnWebStore().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem isOnWebStore: item = " + item);
            onWebStore.setText(item.toString());
        });

        homeItemViewModel.getCategories().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem categories: item = " + item);
            categories.setText(item);
        });

        homeItemViewModel.getIsFramed().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem isFramed: item = " + item.toString());
            framed.setText(item);

            hwdCombo = h + " x " + w + " x " + d;
            hwd.setText(hwdCombo);
            //product = new Product(pName, pMedium, pPurchasePrice, pHeight, pWidth, pDepth, pLocation, pPurchaseDate, pFramed, imgPath);

            /*if(!pName.isEmpty()) {

                dataBaseManager.insertProduct(product);
                Log.d(TAG, "onCreate: saved item path: " + product.getProductPicturePath());
            }*/
        });

        homeItemViewModel.getIsSold().observe(getViewLifecycleOwner(), item -> {
            //
            Log.d(TAG, "viewItem isSold: item = " + item.toString());
            sold.setText(item);
        });

        return view;
    }

    // handle onClick for options menu in viewItem fragment
    // go to EditItem fragment or prompt for item deletion
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        dataBaseManager = new DataBaseManager(getContext());
        switch (item.getItemId()) {
            case R.id.view_item_option_edit:
                Toast.makeText(getContext(), "clicked edit", Toast.LENGTH_LONG).show();
                replaceFragment(new EditItemFragment());
                break;
            case R.id.view_item_option_delete:
                Toast.makeText(getContext(), "clicked delete", Toast.LENGTH_LONG).show();
                //delete entry using productID
                Log.d(TAG, "clicked Delete, what is product: " + productID);
                dataBaseManager.deleteItem(dataBaseManager.getNewTableName(), productID);
                replaceFragment(new HomeFragment());
                break;
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //override method to use specified options menu (sort by menu)
        //FragmentManager fragmentManager = getParentFragmentManager();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment frag = fragmentManager.findFragmentById(R.id.frame_layout);
        Log.d(TAG, "onCreateOptionsMenu: NAME:: " + frag.getId());
        MenuInflater menuInflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.view_item_options_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    // method to replace frame layout with particular fragment
    private void replaceFragment(Fragment fragment) {
        //FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}