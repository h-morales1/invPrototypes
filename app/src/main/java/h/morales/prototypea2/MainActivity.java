package h.morales.prototypea2;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

import h.morales.prototypea2.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ActivityResultLauncher<Uri> takePictureLauncher;
    private static final int CAMERA_PERM_CODE = 1;
    Uri imageUri;

    private DataBaseManager dataBaseManager;

    private ItemViewModel itemViewModel;

    private Product product;
    private String pName = "";
    private String pMedium;
    private String pPurchasePrice;
    private String pHeight;
    private String pWidth;
    private String pDepth;
    private String pLocation;
    private String pPurchaseDate;
    private String pCreationDate;
    private String pNote;
    private String pCategories;
    private boolean pFramed;
    private boolean pSold;
    private boolean pIsOnWebStore;
    private String imgPath;

    EditText addItemNameET,
             addItemMediumET,
             addItemPurchasePriceET,
             addItemHeightET,
             addItemDepthET,
             addItemLocationET,
             addItemWidthET,
             addItemPurchaseDateET;

    CheckBox addItemCBX; // framed or not cbx

    ImageButton addItemFolderIB, // two buttons for deciding where to get art pic from
                addItemCameraIB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //get db
        dataBaseManager = new DataBaseManager(this);

        //TODO only trigger migration if old db is NOT empty
        if(!dataBaseManager.selectAll(dataBaseManager.getOldTableName()).isEmpty()) {
            //old db is NOT empty, perform migration
            dataBaseManager.migrateData();
        } else {
            //dont perform migration
        }
        //

        //observe addItem fragment fields
        itemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        /*itemViewModel.getSelectedData().observe(this, item -> {
            //
            Log.d(TAG, "onCreate: item = " + item);
        });*/
        itemViewModel.getName().observe(this, item -> {
            //
            Log.d(TAG, "Main onCreate: item = " + item.toString());
            pName = item;
        });

        itemViewModel.getMedium().observe(this, item -> {

            Log.d(TAG, "onCreate: item = " + item.toString());
            pMedium = item;
        });

        itemViewModel.getPurchasePrice().observe(this, item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            pPurchasePrice = item;
        });

        itemViewModel.getHeight().observe(this, item -> {
            //
            Log.d(TAG, "onCreate: item = " + item.toString());
            pHeight = item;
        });

        itemViewModel.getDepth().observe(this, item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            pDepth = item;
        });

        itemViewModel.getLocation().observe(this, item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            pLocation = item;
        });

        itemViewModel.getWidth().observe(this, item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            //pWidth = Float.parseFloat(item);
            pWidth = item;
        });

       itemViewModel.getPurchaseDate().observe(this, item -> {
           Log.d(TAG, "onCreate: item = " + item.toString());
           pPurchaseDate = item;
       });

       itemViewModel.getNote().observe(this, item -> {
           Log.d(TAG, "onCreate: item = " + item.toString());
           pNote = item;
       });

       itemViewModel.getCategories().observe(this, item -> {
           Log.d(TAG, "onCreate: item = " + item);
           pCategories = item;
       });

       itemViewModel.getIsFramed().observe(this, item -> {
           Log.d(TAG, "onCreate: item = " + item.toString());
           pFramed = Boolean.parseBoolean(item);
           //product = new Product(pName, pMedium, pPurchasePrice, pHeight, pWidth, pDepth, pLocation, pPurchaseDate, pFramed, imgPath);

           /*if(!pName.isEmpty()) {

               dataBaseManager.insertProduct(product);
               Log.d(TAG, "onCreate: saved item path: " + product.getProductPicturePath());
           }*/
       });
        itemViewModel.getIsSold().observe(this, item -> {
            Log.d(TAG, "onCreate: item = " + item.toString());
            pSold = Boolean.parseBoolean(item);
            //product = new Product(pName, pMedium, pPurchasePrice, pHeight, pWidth, pDepth, pLocation, pPurchaseDate, pFramed, imgPath);

           /*if(!pName.isEmpty()) {

               dataBaseManager.insertProduct(product);
               Log.d(TAG, "onCreate: saved item path: " + product.getProductPicturePath());
           }*/
        });
        itemViewModel.getIsOnWebStore().observe(this, item -> {
            Log.d(TAG, "onCreate: item = " + item);
            pIsOnWebStore = Boolean.parseBoolean(item);
        });

        itemViewModel.getProdCreationDate().observe(this, item -> {
            Log.d(TAG, "mainActivity creationDate: item = " + item.toString());
            pCreationDate = item;
        });

       itemViewModel.getSaveToDB().observe(this, item -> {
           // only save items to db if flag is set
           if(!pName.isEmpty() && item) {

               product = new Product(pName, pMedium, pPurchasePrice, pHeight, pWidth, pDepth, pLocation, pPurchaseDate, pNote, pCategories, pFramed, pSold, pIsOnWebStore, imgPath, pCreationDate);
               dataBaseManager.insertProduct(dataBaseManager.getNewTableName(), product);
               Log.d(TAG, "onCreate: saved item path: " + product.getProductPicturePath());
           }
       });


        //show home fragment by default
        replaceFragment(new HomeFragment()); // TODO : return this to be HOMEFRAGMENT as default
        //replaceFragment(new ArchiveFragment()); // TODO : return this to be HOMEFRAGMENT as default
        registerPictureLauncher();



        // do something when a user selects one of the items on the bottom
        // nav bar
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.addItem:
                    replaceFragment(new AddItemFragment());
                    break;
                case R.id.editItem: // TODO this is actually archive
                    //replaceFragment(new EditItemFragment());
                    break;
                case R.id.exportData:
                    replaceFragment(new ExportCsvFragment());
                    break;
            }

            return true;
        });
    }



    // method to replace frame layout with particular fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }



    private Uri createUri() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String tmstamp = DateFormat.getDateTimeInstance().toString();
        String imageFileName = "JPEG_" + timeStamp + "_.jpg";
        Log.d(TAG, "createUri, this is the filename string: " + timeStamp);
        //File imageFile = new File(getApplicationContext().getFilesDir(), "camera_photo.jpg");
        File imageFile = new File(getApplicationContext().getFilesDir(), imageFileName);
        return FileProvider.getUriForFile(getApplicationContext(), "com.h.morales.fileprovider", imageFile);
    }

    private void checkCameraPermsAndOpenCam() {
        //
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //request perms
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
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
                        product.prodUri = imageUri;
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
    public void imageChooser(View view) {
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
                getContentResolver().takePersistableUriPermission(selectedImgUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                imgPath = selectedImgUri.toString();
                Log.d(TAG, "you selected this image!: " + selectedImgUri.toString());
            }
        }
    });
}