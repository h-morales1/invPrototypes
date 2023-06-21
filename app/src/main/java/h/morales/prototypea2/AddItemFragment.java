package h.morales.prototypea2;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ItemViewModel itemViewModel;

    EditText addItemNameET,
            addItemMediumET,
            addItemPurchasePriceET,
            addItemHeightET,
            addItemDepthET,
            addItemLocationET,
            addItemWidthET,
            addItemCreationDateET,
            addItemPurchaseDateET;

    CheckBox addItemCBX; // framed or not cbx

    Button confirmBTN;

    ImageButton pickPhotoIB;

    public AddItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddItemFragment newInstance(String param1, String param2) {
        AddItemFragment fragment = new AddItemFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
       /* EditText addItemNameET = (EditText) view.findViewById(R.id.addItemNameET);
        confirmBTN = (Button) view.findViewById(R.id.addItemConfirmBT);
        confirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                Log.d(TAG, "onClick: testing within fragment ");
                Bundle result = new Bundle();
                result.putString("pname", addItemNameET.getText().toString());
                getParentFragmentManager().setFragmentResult("addItemResult", result);
            }
        });*/

        pickPhotoIB = view.findViewById(R.id.editItemFolderIB);

        itemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        confirmBTN = (Button) view.findViewById(R.id.addItemConfirmBT); // confirm button
        addItemCBX = (CheckBox) view.findViewById(R.id.addItemCBX);
        // all the edit text fields
        addItemNameET = (EditText) view.findViewById(R.id.addItemNameET);
        addItemMediumET = (EditText) view.findViewById(R.id.addItemMediumET);
        addItemPurchasePriceET = (EditText) view.findViewById(R.id.addItemPurchasePriceET);
        addItemHeightET = (EditText) view.findViewById(R.id.addItemHeightET);
        addItemDepthET = (EditText) view.findViewById(R.id.addItemDepthET);
        addItemLocationET = (EditText) view.findViewById(R.id.addItemLocationET);
        addItemWidthET = (EditText) view.findViewById(R.id.addItemWidthET);
        addItemCreationDateET = (EditText) view.findViewById(R.id.addItemCreationDateET);
        addItemPurchaseDateET = (EditText) view.findViewById(R.id.addItemDateET);


        /*pickPhotoIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
                // do something on click for the folder image button
                Toast.makeText(getContext(), "You want to pick a photo!", Toast.LENGTH_SHORT).show();
            }
        });*/

        //get strings
        /*String pName = addItemNameET.getText().toString();
        String pMedium = addItemMediumET.getText().toString();
        String pPurchasePrice = addItemPurchasePriceET.getText().toString();
        String pHeight = addItemHeightET.getText().toString();
        String pDepth = addItemDepthET.getText().toString();
        String pLocation = addItemLocationET.getText().toString();
        String pWidth = addItemWidthET.getText().toString();
        String pPurchaseDate = addItemPurchaseDateET.getText().toString();*/
        confirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pName = addItemNameET.getText().toString();
                String pMedium = addItemMediumET.getText().toString();
                String pPurchasePrice = addItemPurchasePriceET.getText().toString();
                String pHeight = addItemHeightET.getText().toString();
                String pDepth = addItemDepthET.getText().toString();
                String pLocation = addItemLocationET.getText().toString();
                String pWidth = addItemWidthET.getText().toString();
                String pPurchaseDate = addItemPurchaseDateET.getText().toString();
                String pCreationDate = addItemCreationDateET.getText().toString();
                String isFramed = String.valueOf(addItemCBX.isChecked());
                Log.d(TAG, "addItem is framed: " + isFramed);

                //transfer data from add item form to main activity for processing
                //itemViewModel.setData(pName, pMedium, pPurchasePrice, pHeight, pDepth, pLocation, pWidth, pPurchaseDate);
                if(checkField(pName) || checkField(pMedium) || checkField(pPurchasePrice) || checkField(pHeight)
                || checkField(pDepth) || checkField(pLocation) || checkField(pWidth) || checkField(pPurchaseDate)
                 ) {
                    //a field is empty, display error
                    Toast.makeText(getContext(), "Fill in all fields!", Toast.LENGTH_LONG).show();
                } else {
                    // no field is empty
                    itemViewModel.setName(pName);
                    itemViewModel.setMedium(pMedium);
                    itemViewModel.setPurchasePrice(pPurchasePrice);
                    itemViewModel.setHeight(pHeight);
                    itemViewModel.setDepth(pDepth);
                    itemViewModel.setLocation(pLocation);
                    itemViewModel.setWidth(pWidth);
                    itemViewModel.setPurchaseDate(pPurchaseDate);
                    itemViewModel.setProdCreationDate(pCreationDate);
                    itemViewModel.setIsFramed(isFramed);
                    itemViewModel.setSaveToDB(true); // save to db
                    Toast.makeText(getContext(), "Product Saved", Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
        //return inflater.inflate(R.layout.fragment_add_item, container, false);
    }

    private boolean checkField(String et) {
        return et.isEmpty();
    }
/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        confirmBTN = (Button) view.findViewById(R.id.addItemConfirmBT);
        confirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText addItemName = (EditText) view.findViewById(R.id.addItemNameET);
                itemViewModel.setData(addItemName.getText().toString());
            }
        });
    }*/

    /*private void imageChooser() {
        Intent imgChooserIntent = new Intent();
        imgChooserIntent.setType("image/*");
        imgChooserIntent.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(imgChooserIntent);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), result -> {
        //
        if(result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            //dosomething with URI here
            if(data != null && data.getData() != null) {
                Uri selectedImgUri = data.getData(); // uri of selected image
                Log.d(TAG, "you selected this image!: " + selectedImgUri.toString());
            }
        }
    });*/
}