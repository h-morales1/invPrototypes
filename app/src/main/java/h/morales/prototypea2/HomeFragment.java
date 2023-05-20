package h.morales.prototypea2;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

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
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    //
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
        //FragmentManager fragmentManager = getParentFragmentManager();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Fragment frag = fragmentManager.findFragmentById(R.id.frame_layout);
        Log.d(TAG, "onCreateOptionsMenu: NAME:: " + frag.getId());
        MenuInflater menuInflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.top_options_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    //

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        dataInitialize();
        recyclerview = view.findViewById(R.id.homeFragRecyclerView);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setHasFixedSize(true);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getContext(), productArrayList); // TODO: will have to change this once adding in recyclerinterface
        recyclerview.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
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
}