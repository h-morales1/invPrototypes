package h.morales.prototypea2;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditItemFragment extends Fragment {

    ActivityResultLauncher<Uri> takePictureLauncher;
    private static final int CAMERA_PERM_CODE = 1;
    Uri imageUri;
    private String imgPath;

    private ImageButton choosePhoto;

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

        choosePhoto = (ImageButton) view.findViewById(R.id.editItemBrowseIB);

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editItemimageChooser(view);
            }
        });

        Button editItemConfirmBTN = (Button) view.findViewById(R.id.editItemConfirmBTN);

        editItemConfirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle the on click for confirm btn in editItemFrag
                Toast.makeText(getContext(), "confirm button on click!", Toast.LENGTH_SHORT).show();
            }
        });

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

    private Uri createUri() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String tmstamp = DateFormat.getDateTimeInstance().toString();
        String imageFileName = "JPEG_" + timeStamp + "_.jpg";
        Log.d(TAG, "createUri, this is the filename string: " + timeStamp);
        //File imageFile = new File(getApplicationContext().getFilesDir(), "camera_photo.jpg");
        File imageFile = new File(getContext().getFilesDir(), imageFileName);
        return FileProvider.getUriForFile(getContext(), "com.h.morales.fileprovider", imageFile);
    }

    private void checkCameraPermsAndOpenCam() {
        //
        if(ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //request perms
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
        } else {
            imageUri = createUri();
            takePictureLauncher.launch(imageUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERM_CODE) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //takePictureLauncher.launch(imageUri);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_PERM_CODE);
            } else {
                //
            }
        }
    }

    private void registerPictureLauncher() {
        takePictureLauncher = registerForActivityResult(new ActivityResultContracts.TakePicture(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                try {
                    if(result) { //TODO there is a bug where when asked for perms, it doesnt save the URI of selected image
                        //testing
                        Log.d(TAG, "onActivityResult: PIC TEST" + imageUri.toString());
                        //imgPath = imageUri.toString();
                        //imageUri = createUri();

                        imgPath = imageUri.toString();
                        //product.prodUri = imageUri;
                    }
                } catch (Exception e) {
                    //
                }
            }
        });
    }


    // handle the onClick event for the cameraIB
    public void takePhoto(View view) {
        checkCameraPermsAndOpenCam();
        Log.d(TAG, "takePhoto: TEST");
    }

    // handle picking a photo from the gallery
    public void editItemimageChooser(View view) {
        Intent imgChooserIntent = new Intent();
        imgChooserIntent.setType("image/*");
        //imgChooserIntent.setAction(Intent.ACTION_GET_CONTENT);
        imgChooserIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        launchSomeActivity.launch(imgChooserIntent);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult( new ActivityResultContracts.StartActivityForResult(), result -> {
        //
        if(result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            //dosomething with URI here
            if(data != null && data.getData() != null) {
                Uri selectedImgUri = data.getData(); // uri of selected image

                getActivity().getContentResolver().takePersistableUriPermission(selectedImgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imgPath = selectedImgUri.toString();
                Log.d(TAG, "you selected this image!: " + selectedImgUri.toString());
            }
        }
    });

}