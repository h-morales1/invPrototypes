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

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

    private ArrayList<Product> productArrayList;

    private String[] productTitle; // text strings for titles

    private int[] imageResourceID; // image resource ids

    private RecyclerView recyclerview;

    private DataBaseManager dataBaseManager; // handle db transactions

    ItemViewModel homeItemViewModel;

    Thread tr = null;
    Thread tr2 = null;

    RecyclerAdapter recyclerAdapter;

    CountDownLatch latch = new CountDownLatch(1);

    ArrayList<Product> prs;

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
        //prs = dataBaseManager.selectAll();
        //RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), prs, this); // TODO: will have to change this once adding in recyclerinterface

        //recyclerview =(RecyclerView) getActivity().findViewById(R.id.homeFragRecyclerView);
        //recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerview.setHasFixedSize(true);

        //RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), dataBaseManager.selectAll(), this); // TODO: will have to change this once adding in recyclerinterface
        //recyclerview.setAdapter(recyclerAdapter);
        //recyclerAdapter.updateData(prs);
        //Log.d(TAG, "onCreate in homefrag size of prs is : "+ prs.size());


        //TODO REMOVE THIS, THIS IS A TEST
        //Product prod = new Product("Sycamore park", "water color", 200.00f, 12.0f, 12.0f, 10.0f, "dekalb public library", "05/21/23", true, "test");
        //dataBaseManager.insertProduct(prod);
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
            case R.id.sort_by:
                Toast.makeText(getContext(), "clicked sort by", Toast.LENGTH_LONG).show();
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


        inflater.inflate(R.menu.top_options_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.home_search);
        SearchView searchView = (SearchView)  menuItem.getActionView();
        searchView.setQueryHint("Search by name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                recyclerAdapter.getFilter().filter(query);
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
        
        //dataInitialize();
        recyclerview = view.findViewById(R.id.homeFragRecyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        //DataBaseManager tester = new DataBaseManager(getContext());
        //prs = tester.selectAll();
        //Log.d(TAG, "onViewCreated PRS SIZE: " + prs.size());

        //RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), dataBaseManager.selectAll(), this); // TODO: will have to change this once adding in recyclerinterface
        recyclerAdapter = new RecyclerAdapter(getContext(), dataBaseManager.selectAll(), this); // TODO: will have to change this once adding in recyclerinterface

        //recyclerview = view.findViewById(R.id.homeFragRecyclerView);
        //Log.d(TAG, "onViewCreated PRS SIZE: " + prs.size());
        //recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerview.setHasFixedSize(true);

        //RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), dataBaseManager.selectAll(), this); // TODO: will have to change this once adding in recyclerinterface
        recyclerview.setAdapter(recyclerAdapter);
        //Log.d(TAG, "onViewCreated PRS SIZE: " + prs.size());
        recyclerAdapter.updateData(dataBaseManager.selectAll());

                //RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), productArrayList, this); // TODO: will have to change this once adding in recyclerinterface

    }

    private void dataInitialize() {
        // populate the recycler view with items
        // TODO: will need a function to select all from db and populate an array of products

        productArrayList = new ArrayList<>();
        productTitle = new String[] {
                getString(R.string.test_list_item0),
                getString(R.string.test_list_item1),
                getString(R.string.test_list_item2),
                getString(R.string.test_list_item3),
                getString(R.string.test_list_item4),
                getString(R.string.test_list_item5),
                getString(R.string.test_list_item6),
                getString(R.string.test_list_item7),
                getString(R.string.test_list_item8),
                getString(R.string.test_list_item9),
        };

        imageResourceID = new int[] {
                R.drawable.dudes_holly_hocks,
                R.drawable.backyard_monet,
                R.drawable.dancing_daisy,
                R.drawable.garden_findings,
                R.drawable.hello_hot_stuff,
                R.drawable.love_wins,
                R.drawable.sycamore_park,
                R.drawable.daily_pickens,
                R.drawable.stormy_skies,
                R.drawable.suck_it,
        };

        for(int i=0; i<productTitle.length;i++){
            Product product = new Product(productTitle[i], imageResourceID[i]);
            productArrayList.add(product);
        }
    }


    //handle onclick for the recycler
    @Override
    public void onItemClick(int post) {
        homeItemViewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);

        //Log.d(TAG, "onItemClick: you clicked recycler item: " + productArrayList.get(post).getProductName());
        Log.d(TAG, "onItemClick: you clicked recycler item: " + dataBaseManager.selectAll().get(post).getProductName());
        //Toast.makeText(getContext(), "You clicked on " + dataBaseManager.selectAll().get(post).getProductName(), Toast.LENGTH_LONG).show();

        //set proper details
        homeItemViewModel.setName(dataBaseManager.selectAll().get(post).getProductName());
        homeItemViewModel.setMedium(dataBaseManager.selectAll().get(post).getProductMedium());
        homeItemViewModel.setPurchasePrice(String.valueOf(dataBaseManager.selectAll().get(post).getProductPurchasePrice()));
        homeItemViewModel.setHeight(String.valueOf(dataBaseManager.selectAll().get(post).productHeight));
        homeItemViewModel.setDepth(String.valueOf(dataBaseManager.selectAll().get(post).productDepth));
        homeItemViewModel.setLocation(dataBaseManager.selectAll().get(post).getProductLocation());
        homeItemViewModel.setWidth(String.valueOf(dataBaseManager.selectAll().get(post).getProductWidth()));
        homeItemViewModel.setPurchaseDate(dataBaseManager.selectAll().get(post).getProductPurchaseDate());
        homeItemViewModel.setNote(dataBaseManager.selectAll().get(post).getProductNote());
        homeItemViewModel.setIsFramed(Boolean.toString(dataBaseManager.selectAll().get(post).isProductFramed()));
        homeItemViewModel.setSold(Boolean.toString(dataBaseManager.selectAll().get(post).isProductSold()));
        homeItemViewModel.setProdUri(dataBaseManager.selectAll().get(post).productPicturePath);
        homeItemViewModel.setProdCreationDate(dataBaseManager.selectAll().get(post).getCreationDate());
        homeItemViewModel.setProdID(dataBaseManager.selectAll().get(post).getProdID());
        homeItemViewModel.setSaveToDB(false); // dont need to save values

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, new ViewItemFragment());
        fragmentTransaction.commit();
    }
}