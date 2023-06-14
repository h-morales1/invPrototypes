package h.morales.prototypea2;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ItemViewModel editItemViewModel;

    public EditItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditItemFragment newInstance(String param1, String param2) {
        EditItemFragment fragment = new EditItemFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_edit_item, container, false);
        editItemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

        EditText editTextName = (EditText) view.findViewById(R.id.editItemNameET);
        EditText editTextMedium = (EditText) view.findViewById(R.id.editItemMediumET);
        EditText editTextpurchasePrice = (EditText) view.findViewById(R.id.editItemPurchasePriceET);
        EditText editTextH = (EditText) view.findViewById(R.id.editItemHET);
        EditText editTextW = (EditText) view.findViewById(R.id.editItemWET);
        EditText editTextD = (EditText) view.findViewById(R.id.editItemDET);
        EditText editTextLocation = (EditText) view.findViewById(R.id.editItemLocationET);
        EditText editTextpurchaseDate = (EditText) view.findViewById(R.id.editItemDateET);
        EditText editTextCreationDate = (EditText) view.findViewById(R.id.editItemCreationDateET);

        CheckBox editItemFramed = (CheckBox) view.findViewById(R.id.editItemFramedCBX);
        CheckBox editItemSold = (CheckBox) view.findViewById(R.id.editItemSoldCBX);

        //fill in the fields with product details for user to edit

        editItemViewModel.getName().observe(getViewLifecycleOwner(), item -> {
            //transfer data from viewItemFragment
            Log.d(TAG, "ediItem got name: " + item);
            editTextName.setText(item);

        });

        editItemViewModel.getMedium().observe(getViewLifecycleOwner(), item -> {

            Log.d(TAG, "onCreate: item = " + item.toString());
            editTextMedium.setText(item);
            //pMedium = item;
        });

        editItemViewModel.getPurchasePrice().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            editTextpurchasePrice.setText(item);
            //pPurchasePrice = Float.parseFloat(item);
        });

        editItemViewModel.getHeight().observe(getViewLifecycleOwner(), item -> {
            //
            Log.d(TAG, "onCreate: item = " + item.toString());
            //h = item;
            editTextH.setText(item);
            //pHeight = Float.parseFloat(item);
        });

        editItemViewModel.getDepth().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            //d = item;
            editTextD.setText(item);
            //pDepth = Float.parseFloat(item);
        });

        editItemViewModel.getLocation().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            editTextLocation.setText(item);
            //pLocation = item;
        });

        editItemViewModel.getWidth().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            //w = item;
            editTextW.setText(item);
            //pWidth = Float.parseFloat(item);
        });

        editItemViewModel.getPurchaseDate().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            editTextpurchaseDate.setText(item);
            //pPurchaseDate = item;
        });

        editItemViewModel.getProdUri().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            //pieceIV.setImageURI(Uri.parse(item));
            //TODO: this needs to check if a new uri has been selected

        });

        editItemViewModel.getProdCreationDate().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem prodCreationDate: item = " + item.toString());
            editTextCreationDate.setText(item);
        });

        editItemViewModel.getIsFramed().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem isFramed: item = " + item.toString());
            //framed.setText(item);
            editItemFramed.setChecked(Boolean.getBoolean(item));

            //hwdCombo = h + " x " + w + " x " + d;
            //hwd.setText(hwdCombo);
            //product = new Product(pName, pMedium, pPurchasePrice, pHeight, pWidth, pDepth, pLocation, pPurchaseDate, pFramed, imgPath);

            /*if(!pName.isEmpty()) {

                dataBaseManager.insertProduct(product);
                Log.d(TAG, "onCreate: saved item path: " + product.getProductPicturePath());
            }*/
        });

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_edit_item, container, false);
        return view;
    }
}