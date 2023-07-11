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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

    private String newName;
    private String newMedium;
    private String newPurchasePrice;
    private String newHeight;
    private String newWidth;
    private String newDepth;
    private String newLocation;
    private String newPurchaseDate;
    private String newNote;
    private String newFramed;
    private String newSold;
    private String newPicturePath;
    private String newCreationDate;
    private String newIsOnWebStore;
    private String newCategories;

    private String oldName;
    private String oldMedium;
    private String oldPurchasePrice;
    private String oldHeight;
    private String oldWidth;
    private String oldDepth;
    private String oldLocation;
    private String oldPurchaseDate;
    private String oldNote;
    private String oldFramed;
    private String oldSold;
    private String oldPicturePath;
    private String oldCreationDate;
    private String oldIsOnWebStore;
    private String oldCategories;

    private ImageButton choosePhoto;
    private ImageButton takePhoto;

    private DataBaseManager dataBaseManager;
    private int currentProdID;

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
        registerPictureLauncher();

        EditText editTextName = (EditText) view.findViewById(R.id.editItemNameET);
        EditText editTextMedium = (EditText) view.findViewById(R.id.editItemMediumET);
        EditText editTextpurchasePrice = (EditText) view.findViewById(R.id.editItemPurchasePriceET);
        EditText editTextH = (EditText) view.findViewById(R.id.editItemHET);
        EditText editTextW = (EditText) view.findViewById(R.id.editItemWET);
        EditText editTextD = (EditText) view.findViewById(R.id.editItemDET);
        EditText editTextLocation = (EditText) view.findViewById(R.id.editItemLocationET);
        EditText editTextpurchaseDate = (EditText) view.findViewById(R.id.editItemDateET);
        EditText editTextNote = (EditText) view.findViewById(R.id.editItemNoteET);
        EditText editTextCreationDate = (EditText) view.findViewById(R.id.editItemCreationDateET);
        EditText editTextCategories = (EditText) view.findViewById(R.id.editItemCategoriesET);

        CheckBox editItemFramed = (CheckBox) view.findViewById(R.id.editItemFramedCBX);
        CheckBox editItemSold = (CheckBox) view.findViewById(R.id.editItemSoldCBX);
        CheckBox editItemIsOnWebStore = (CheckBox) view.findViewById(R.id.editItemIsOnWebStoreCBX);

        choosePhoto = (ImageButton) view.findViewById(R.id.editItemBrowseIB);
        takePhoto = (ImageButton) view.findViewById(R.id.editItemPhotoIB);

        choosePhoto.setOnClickListener(new View.OnClickListener() {
            // handle selecting photo from own gallery and returning the URI
            @Override
            public void onClick(View view) {
                editItemimageChooser(view);
                Log.d(TAG, "image chooser edit item imgpath: " + imgPath);
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle taking photo and returning URI of photo
                takePhoto(view);
                Log.d(TAG, "takePhoto editItem imgpath: " + imgPath);
            }
        });

        Button editItemConfirmBTN = (Button) view.findViewById(R.id.editItemConfirmBTN);

        editItemConfirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // handle the on click for confirm btn in editItemFrag
                Log.d(TAG, "on edit item confirm imgpath: " + imgPath);

                //set new values
                newName = editTextName.getText().toString();
                newMedium = editTextMedium.getText().toString();
                newPurchasePrice = editTextpurchasePrice.getText().toString();
                newHeight = editTextH.getText().toString();
                newWidth = editTextW.getText().toString();
                newDepth = editTextD.getText().toString();
                newLocation = editTextLocation.getText().toString();
                newPurchaseDate = editTextpurchaseDate.getText().toString();
                newNote = editTextNote.getText().toString();
                newFramed = String.valueOf(editItemFramed.isChecked());
                newSold = String.valueOf(editItemSold.isChecked());
                newCreationDate = editTextCreationDate.getText().toString();
                newCategories = editTextCategories.getText().toString();
                newIsOnWebStore = String.valueOf(editItemIsOnWebStore.isChecked());
                newPicturePath = imgPath;

                setNewVals(); // save any new data to db
                Toast.makeText(getContext(), "Product updated!", Toast.LENGTH_SHORT).show();
                replaceFragment(new HomeFragment());
            }
        });

        //fill in the fields with product details for user to edit

        editItemViewModel.getProdID().observe(getViewLifecycleOwner(), item -> {
            // get prod id
            Log.d(TAG, "onCreateView: edit item got id " + item);
            currentProdID = Integer.parseInt(item);
        });

        editItemViewModel.getName().observe(getViewLifecycleOwner(), item -> {
            //transfer data from viewItemFragment
            Log.d(TAG, "ediItem got name: " + item);
            editTextName.setText(item);
            oldName = item;

        });

        editItemViewModel.getMedium().observe(getViewLifecycleOwner(), item -> {

            Log.d(TAG, "onCreate: item = " + item.toString());
            editTextMedium.setText(item);
            oldMedium = item;
        });

        editItemViewModel.getPurchasePrice().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            editTextpurchasePrice.setText(item);
            oldPurchasePrice = item;
        });

        editItemViewModel.getHeight().observe(getViewLifecycleOwner(), item -> {
            //
            Log.d(TAG, "onCreate: item = " + item.toString());
            //h = item;
            editTextH.setText(item);
            oldHeight = item;
        });

        editItemViewModel.getDepth().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            //d = item;
            editTextD.setText(item);
            oldDepth = item;
        });

        editItemViewModel.getLocation().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            editTextLocation.setText(item);
            oldLocation = item;
        });

        editItemViewModel.getWidth().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            //w = item;
            editTextW.setText(item);
            oldWidth = item;
        });

        editItemViewModel.getPurchaseDate().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            editTextpurchaseDate.setText(item);
            oldPurchaseDate = item;
        });

        editItemViewModel.getNote().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            editTextNote.setText(item);
            oldNote = item;
        });

        editItemViewModel.getProdUri().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            //pieceIV.setImageURI(Uri.parse(item));
            //TODO: this needs to check if a new uri has been selected
            oldPicturePath = item;

        });

        editItemViewModel.getProdCreationDate().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem prodCreationDate: item = " + item.toString());
            editTextCreationDate.setText(item);
            oldCreationDate = item;
        });

        editItemViewModel.getIsFramed().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem isFramed: item = " + item.toString());
            //framed.setText(item);
            editItemFramed.setChecked(Boolean.parseBoolean(item));
            oldFramed = item;

            //hwdCombo = h + " x " + w + " x " + d;
            //hwd.setText(hwdCombo);
            //product = new Product(pName, pMedium, pPurchasePrice, pHeight, pWidth, pDepth, pLocation, pPurchaseDate, pFramed, imgPath);

            /*if(!pName.isEmpty()) {

                dataBaseManager.insertProduct(product);
                Log.d(TAG, "onCreate: saved item path: " + product.getProductPicturePath());
            }*/
        });

        editItemViewModel.getIsSold().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem isSold: item = " + item.toString());
            editItemSold.setChecked(Boolean.parseBoolean(item));
            oldSold = item;
        });

        editItemViewModel.getCategories().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem categories: item = " + item);
            editTextCategories.setText(item);
            oldCategories = item;
        });

        editItemViewModel.getIsOnWebStore().observe(getViewLifecycleOwner(), item -> {
            Log.d(TAG, "viewItem isOnWebStore : item = " + item);
            editItemIsOnWebStore.setChecked(Boolean.parseBoolean(item));
            oldIsOnWebStore = item;
        });

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_edit_item, container, false);
        return view;
    }

    private void replaceFragment(Fragment fragment) {
        //FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private boolean checkIfChanged(String newVal, String oldVal) {
        //handle checking if values are different, set new value if different than old
        if(newVal == null) {
            return false;
        }

        // the two values are not the same
        return newVal.compareTo(oldVal) != 0;
    }

    private void setNewVals() {
        //handle comparing old and new data, set data that is different
        Product updatedProd;
        if(checkIfChanged(newName, oldName)) {
            //the result is not equal
            Log.d(TAG, "setNewVals: new name and old name are  not equal");
            if(!newName.isEmpty()) {
               oldName = newName; // check that newName is not empty before updating name
            }
        }

        if(checkIfChanged(newMedium, oldMedium)) {
            //old and new medium are note equal
            if(!newMedium.isEmpty()) {
                oldMedium = newMedium; // check that new medium isnt empty
            }
        }

       if(checkIfChanged(newPurchasePrice, oldPurchasePrice)) {
           //old and new purchase price are not equal
           if(!newPurchasePrice.isEmpty()) {
               oldPurchasePrice = newPurchasePrice; // check new isnt empty
           }
       }


        if(checkIfChanged(newNote, oldNote)) {
            //old and new Note are not equal
            if(!newNote.isEmpty()) {
                oldNote = newNote; // check new isnt empty
            }
        }

      if(checkIfChanged(newHeight, oldHeight)) {
          if(!newHeight.isEmpty()) {
              oldHeight = newHeight;
          }
      }

      if(checkIfChanged(newWidth, oldWidth)) {
          if(!newWidth.isEmpty()) {
              oldWidth = newWidth;
          }
      }

      if(checkIfChanged(newDepth, oldDepth)) {
          if(!newDepth.isEmpty()) {
              oldDepth = newDepth;
          }
      }

      if(checkIfChanged(newLocation, oldLocation)) {
          if(!newLocation.isEmpty()) {
              oldLocation = newLocation;
          }
      }

      if(checkIfChanged(newPurchaseDate, oldPurchaseDate)) {
          if(!newPurchaseDate.isEmpty()) {
              oldPurchaseDate = newPurchaseDate;
          }
      }

      if(checkIfChanged(newFramed, oldFramed)) {
          if(!newFramed.isEmpty()) {
              oldFramed = newFramed;
          }
      }

        if(checkIfChanged(newSold, oldSold)) {
            if(!newSold.isEmpty()) {
                oldSold = newSold;
            }
        }

      if(checkIfChanged(newPicturePath, oldPicturePath)) {
          if(!newPicturePath.isEmpty()) {
              Log.d(TAG, "setNewVals: picture path set to new pic path");
              oldPicturePath = newPicturePath;
          }
      }

      if(checkIfChanged(newCreationDate, oldCreationDate)) {
          if(!newCreationDate.isEmpty()) {
              oldCreationDate = newCreationDate;
          }
      }

        Log.d(TAG, "setNewVals: checking isonWebStore vals");
      if(checkIfChanged(newIsOnWebStore, oldIsOnWebStore)) {
          if(!newIsOnWebStore.isEmpty()) {
              oldIsOnWebStore = newIsOnWebStore;
          }
      }

        Log.d(TAG, "setNewVals: checking categories vals");
      if(checkIfChanged(newCategories, oldCategories)) {
          if(!newCategories.isEmpty()) {
              oldCategories = newCategories;
          }
      }

        updatedProd = new Product(oldName.replace("'","''"), oldMedium.replace("'","''"), oldPurchasePrice, oldHeight.replace("'","''"),
                                 oldWidth.replace("'","''"), oldDepth.replace("'","''"), oldLocation.replace("'","''"), oldPurchaseDate,
                                 oldNote.replace("'","''"), oldCategories.replace("'","''"), Boolean.parseBoolean(oldFramed), Boolean.parseBoolean(oldSold), Boolean.parseBoolean(oldIsOnWebStore), oldPicturePath, newCreationDate);
        updateProductDB(updatedProd); // commit changes to db
    }

    private void updateProductDB(Product pr) {
        // update product details in db
        dataBaseManager = new DataBaseManager(getContext());
        dataBaseManager.updateProduct(dataBaseManager.getNewTableName(), currentProdID, pr);
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