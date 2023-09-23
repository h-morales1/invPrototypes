package h.morales.prototypea2;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements RecyclerViewInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerview;

    private DataBaseManager dataBaseManager; // handle db transactions

    ItemViewModel homeItemViewModel;

    RecyclerAdapter recyclerAdapter;
    private ArrayList<Product> listOfProds; // hold all products for recycler

    private int userSelection = 0; // determine what attribute to search for, name, category, etc
    private MenuItem multiSearch;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        //initiate databasemanager
        dataBaseManager = new DataBaseManager(getContext());
        listOfProds = dataBaseManager.selectAll(dataBaseManager.getNewTableName()); // get list of all products

        setHasOptionsMenu(true); // allow options menu here
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    //
    // handle on click for sort options menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_by_name:
                Toast.makeText(getContext(), "Searching name", Toast.LENGTH_LONG).show();
                userSelection = 0;
                break;
            case R.id.search_by_category:
                Toast.makeText(getContext(), "Searching category", Toast.LENGTH_LONG).show();
                userSelection = 1;
                break;
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //override method to use specified options menu (sort by menu)
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment frag = fragmentManager.findFragmentById(R.id.frame_layout);
        Log.d(TAG, "onCreateOptionsMenu: NAME:: " + frag.getId());
        MenuInflater menuInflater = getActivity().getMenuInflater();


        inflater.inflate(R.menu.top_options_menu, menu);

        multiSearch = menu.findItem(R.id.home_multi_edit); // edit multiple items icon

        MenuItem menuItem = menu.findItem(R.id.home_search); // home search button
        SearchView searchView = (SearchView)  menuItem.getActionView();
        searchView.setQueryHint("Search by name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // use userSelection to determine what to search for
                recyclerAdapter.decideFilter(userSelection).filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    //

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerview = view.findViewById(R.id.homeFragRecyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);


        recyclerAdapter = new RecyclerAdapter(getContext(), listOfProds, this); // populate recycler with products


        recyclerview.setAdapter(recyclerAdapter);

        listOfProds = dataBaseManager.selectAll(dataBaseManager.getNewTableName()); // update arraylist
        recyclerAdapter.updateData(listOfProds); // update adapter


    }

    //handle onclick for the recycler
    @Override
    public void onItemClick(int post) {
        homeItemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

        //set proper details UPDATED
        homeItemViewModel.setName(recyclerAdapter.productArrayList.get(post).getProductName());
        homeItemViewModel.setMedium(recyclerAdapter.productArrayList.get(post).getProductMedium());
        homeItemViewModel.setPurchasePrice(String.valueOf(recyclerAdapter.productArrayList.get(post).getProductPurchasePrice()));
        homeItemViewModel.setHeight(String.valueOf(recyclerAdapter.productArrayList.get(post).productHeight));
        homeItemViewModel.setDepth(String.valueOf(recyclerAdapter.productArrayList.get(post).productDepth));
        homeItemViewModel.setLocation(recyclerAdapter.productArrayList.get(post).getProductLocation());
        homeItemViewModel.setWidth(String.valueOf(recyclerAdapter.productArrayList.get(post).getProductWidth()));
        homeItemViewModel.setPurchaseDate(recyclerAdapter.productArrayList.get(post).getProductPurchaseDate());
        homeItemViewModel.setNote(recyclerAdapter.productArrayList.get(post).getProductNote());
        homeItemViewModel.setIsFramed(Boolean.toString(recyclerAdapter.productArrayList.get(post).isProductFramed()));
        homeItemViewModel.setSold(Boolean.toString(recyclerAdapter.productArrayList.get(post).isProductSold()));
        homeItemViewModel.setProdUri(recyclerAdapter.productArrayList.get(post).productPicturePath);
        homeItemViewModel.setProdCreationDate(recyclerAdapter.productArrayList.get(post).getCreationDate());
        homeItemViewModel.setProdIsOnWebStore(Boolean.toString(recyclerAdapter.productArrayList.get(post).isOnWebStore()));
        homeItemViewModel.setProdCategories(recyclerAdapter.productArrayList.get(post).getProductCategories());
        homeItemViewModel.setProdID(recyclerAdapter.productArrayList.get(post).getProdID());
        homeItemViewModel.setSaveToDB(false); // dont need to save values

        //go to viewItem fragment
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new ViewItemFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onLongClick(int post, ImageView checkMark) {
        homeItemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        Product selectedProd = recyclerAdapter.productArrayList.get(post);

        // logic to handle selecting / unselecting a product
        if(selectedProd.isSelected) {
            //prod was selected so unselect it
            checkMark.setVisibility(View.INVISIBLE);
            selectedProd.isSelected = false;
            boolean selectedExists = recyclerAdapter.multiEditList.contains(selectedProd); // check if its in selected array list before removing
            if(selectedExists) {
                // the selected product exists in array, we can remove it
                recyclerAdapter.multiEditList.remove(selectedProd);

                if(recyclerAdapter.multiEditList.isEmpty()) {
                    // if the selected items list is empty then remove the button for multi-selection editing
                    multiSearch.setVisible(false);
                }
            }
        } else {
            // prod was not selected, so select it
            selectedProd.isSelected = true;
            checkMark.setVisibility(View.VISIBLE);
            boolean selectedExists = recyclerAdapter.multiEditList.contains(selectedProd); // check if its in selected array list before removing
            if(!selectedExists) {
                //selected product does not exist in array, we can add it
                recyclerAdapter.multiEditList.add(recyclerAdapter.productArrayList.get(post)); // add selected product to selected arraylist

                // show the multi-select edit button if there are items selected
                multiSearch.setVisible(true);
            }
        }
        //recyclerAdapter.multiEditList.add(recyclerAdapter.productArrayList.get(post)); // add selected product to selected arraylist
        Toast.makeText(getContext(), "Items: " + recyclerAdapter.multiEditList.size(), Toast.LENGTH_LONG).show(); // TODO you can remove this
    }
}