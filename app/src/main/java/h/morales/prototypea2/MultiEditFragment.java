package h.morales.prototypea2;

import android.os.Bundle;

import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MultiEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText location;

    private Button saveBtn;

    public MultiEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MultiEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MultiEditFragment newInstance(String param1, String param2) {
        MultiEditFragment fragment = new MultiEditFragment();
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

        ItemViewModel homeview = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        View view = inflater.inflate(R.layout.fragment_multi_edit, container, false);
        saveBtn = (Button) view.findViewById(R.id.multiEditBTN);
        location = (EditText) view.findViewById(R.id.multiEditLocationET);
        ArrayList<Product> selectedItems = new ArrayList<>();


        homeview.getSelectedProducts().observe( getViewLifecycleOwner(), item -> {
            //fill array of products
            if(!item.isEmpty()) {
                selectedItems.addAll(item);
            }

        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = selectedItems.size(); // amount of products updated
                String new_loc = location.getText().toString(); // get location from EditText
                updateDb(selectedItems, new_loc); // call function to update db
                Toast.makeText(getContext(), "Updated " + x + " locations", Toast.LENGTH_LONG).show();
                // change fragment back to home
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
                fragmentTransaction.commit();
            }
        });

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_multi_edit, container, false);
        return view;
    }

    /*
    Handle updating the db with a new location for multiple products

    NOTE: only check on location string is if it is empty
     */
    public void updateDb(ArrayList<Product> items, String location) {
        DataBaseManager dataBaseManager = new DataBaseManager(getContext());

        if(!location.isEmpty()) {
            // make sure the location is not empty
            for (Product prod: items) {
                // update location for each product in obj

                prod.productLocation = location;
                // update location in database
                dataBaseManager.updateProduct(dataBaseManager.getNewTableName(), Integer.parseInt(prod.prodID), prod);

            }
        } else {
            // alert user that the location was empty
            Toast.makeText(getContext(), "Location was empty!", Toast.LENGTH_LONG).show();
        }

    }


}