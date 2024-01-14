package h.morales.prototypea2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExportCsvFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExportCsvFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Button exportBtn;
    private Button massExportBtn;
    EditText userEmail;

    public ExportCsvFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExportCsvFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExportCsvFragment newInstance(String param1, String param2) {
        ExportCsvFragment fragment = new ExportCsvFragment();
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

        View view = inflater.inflate(R.layout.fragment_export_csv, container, false);
        exportBtn = (Button) view.findViewById(R.id.exportCsvBTN);
        massExportBtn = (Button) view.findViewById(R.id.massExportBTN); // TODO this is just to test mass exporting db
        userEmail = (EditText) view.findViewById(R.id.exportCsvEmailET);

        DataBaseManager dataBaseManager = new DataBaseManager(getContext());
        //
        //String csv = (Environment.getExternalStorageDirectory().getAbsolutePath()+ "/ACsvFile.csv");
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String csv = (getContext().getFilesDir()+ "/" + timeStamp + "ACsvFile.csv");
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String csv = timeStamp + "_.csv";

        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usersEmail = userEmail.getText().toString();
                CSVWriter writer = null;
                try {
                    writer = new CSVWriter(new FileWriter(csv));
                    String[] header = {"id", "Name", "Medium", "Purchase Price", "Height", "Width", "Depth", "Location", "Purchase Date", "Note", "Framed", "Sold", "Creation Date", "Picture Path", "On Web Store", "Categories"};
                    writer.writeNext(header); // write header to file

                    //write all products to database

                    ArrayList<Product> arr = dataBaseManager.selectAll(dataBaseManager.getNewTableName());
                    for(int i=0; i< arr.size();i++){
                        Product prod = arr.get(i);
                        //get data from each product as strings
                        String[] data = {prod.getProdID(), prod.getProductName(), prod.getProductMedium(), prod.getProductPurchasePrice(), prod.getProductHeight(), prod.getProductWidth(), prod.getProductDepth(),
                                         prod.getProductLocation(), prod.getProductPurchaseDate(), prod.getProductNote(), String.valueOf(prod.isProductFramed()), String.valueOf(prod.isProductSold()), prod.getCreationDate(),
                                         prod.getProductPicturePath(), String.valueOf(prod.isOnWebStore()), prod.getProductCategories()};
                        writer.writeNext(data);
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //email file
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{usersEmail});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Main Database csv file");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "The attached file will contain all the products in the main database, archive not included. Simply open with Excel to view");

                File file = new File(csv);
                Uri uri = FileProvider.getUriForFile(getContext(), "com.h.morales.fileprovider", file);
                emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
                startActivity(Intent.createChooser(emailIntent, "Pick an email provider"));
            }
        });

        // Handle testing mass db exporting
        massExportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Product> productList = dataBaseManager.selectAll(dataBaseManager.getNewTableName());
                List<Uri> uris = new ArrayList<>();
                for(int i=0; i<2;i++) {
                    //DbWideExporter.renameFile(getContext(), productList.get(i));
                    //DbWideExporter.zipFile(getContext(), Uri.parse(productList.get(i).getProductPicturePath()) );
                    uris.add(Uri.parse(productList.get(i).getProductPicturePath()));

                    //DbWideExporter.copyFileFromUri(getContext(), Uri.parse(productList.get(i).getProductPicturePath()), productList.get(i));
                }
                DbWideExporter.zipFiles(getContext(), uris, "weems");

            }
        });
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_export_csv, container, false);
        return view;
    }
}