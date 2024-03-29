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
import android.content.Intent;
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
    private float pPurchasePrice;
    private String pHeight;
    private String pWidth;
    private String pDepth;
    private String pLocation;
    private String pPurchaseDate;
    private String pCreationDate;
    private String pNote;
    private boolean pFramed;
    private boolean pSold;
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
            pPurchasePrice = Float.parseFloat(item);
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

        itemViewModel.getProdCreationDate().observe(this, item -> {
            Log.d(TAG, "mainActivity creationDate: item = " + item.toString());
            pCreationDate = item;
        });

       itemViewModel.getSaveToDB().observe(this, item -> {
           // only save items to db if flag is set
           if(!pName.isEmpty() && item) {

               product = new Product(pName, pMedium, pPurchasePrice, pHeight, pWidth, pDepth, pLocation, pPurchaseDate, pNote, pFramed, pSold, imgPath, pCreationDate);
               dataBaseManager.insertProduct(product);
               Log.d(TAG, "onCreate: saved item path: " + product.getProductPicturePath());
           }
       });

        //show home fragment by default
        replaceFragment(new HomeFragment());
        //imageUri = createUri();
        registerPictureLauncher();
        /*product = new Product(pName, pMedium, pPurchasePrice, pHeight, pWidth, pDepth, pLocation, pPurchaseDate, pFramed, imgPath);
        if(!pName.isEmpty()) {

            dataBaseManager.insertProduct(product);
        }*/
        ArrayList<Product> ps = dataBaseManager.selectAll();
        //Log.d(TAG, "onCreate: ps: " + ps.get(0).getProductName());
        Log.d(TAG, "onCreate: ps: " + ps.size());
        /*if(imageUri != null) {
            product = new Product(pName, pMedium, pPurchasePrice, pHeight, pWidth, pDepth, pLocation, pPurchaseDate, pFramed, imgPath);
            dataBaseManager.insertProduct(product);
            ArrayList<Product> ps = dataBaseManager.selectAll();
            if(ps.size() > 0) {
                Log.d(TAG, "onCreate: ps: " + ps.get(0).getProductName());
            }
        }*/

        /*addItemNameET = findViewById(R.id.addItemNameET);
        addItemMediumET = findViewById(R.id.addItemMediumET);
        addItemPurchasePriceET = findViewById(R.id.addItemPurchasePriceET);
        addItemHeightET = findViewById(R.id.addItemHeightET);
        addItemDepthET = findViewById(R.id.addItemDepthET);
        addItemLocationET = findViewById(R.id.addItemLocationET);
        addItemWidthET = findViewById(R.id.addItemWidthET);
        addItemPurchaseDateET = findViewById(R.id.addItemDateET);

        addItemCBX = findViewById(R.id.addItemCBX);*/


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
                case R.id.editItem:
                    //replaceFragment(new EditItemFragment());
                    break;
            }

            return true;
        });
    }

    // handle on click for top options menu SORTING
    /*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by:
                Toast.makeText(this, "clicked sort by", Toast.LENGTH_LONG).show();
                break;
        }
        //return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // TODO: remove it if you have provlems
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment frag = fragmentManager.findFragmentById(R.id.frame_layout);
        Log.d(TAG, "onCreateOptionsMenu: NAME:: " + frag.getId());
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_options_menu, menu);
        return true;
    }
    */

    // method to replace frame layout with particular fragment
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    // handle addItemConfirmBT button click
    /*public void onConfirm(View view) {
        //
        String pname = addItemNameET.getText().toString();
        if(checkField(addItemNameET) || checkField(addItemMediumET) || checkField(addItemPurchasePriceET) || checkField(addItemHeightET) || checkField(addItemWidthET) || checkField(addItemDepthET) || checkField(addItemLocationET) || checkField(addItemPurchaseDateET) ) {
            // means something was empty
            Toast.makeText(this, "You must fill in all fields!", Toast.LENGTH_LONG).show();
        } else {
            //String pname = addItemNameET.getText().toString();
            String pmedium = addItemMediumET.getText().toString();
            float pPurchasePrice = Float.parseFloat(addItemPurchasePriceET.getText().toString());
            float pheight = Float.parseFloat(addItemHeightET.getText().toString());
            float pwidth = Float.parseFloat(addItemWidthET.getText().toString());
            float pdepth = Float.parseFloat(addItemDepthET.getText().toString());
            String pLocation = addItemLocationET.getText().toString();
            String pPurchaseDate = addItemPurchaseDateET.getText().toString();
            boolean isFramed = addItemCBX.isChecked();

            // create product from data in form
            Product product = new Product(pname, pmedium, pPurchasePrice, pheight, pwidth, pdepth, pLocation, pPurchaseDate, isFramed, imageUri.getPath());

            // check to make sure that the image is actually selected
            if (imageUri != null) {
                dataBaseManager.insertProduct(product);
            } else {
                Toast.makeText(this, "You must select a picture for the art piece", Toast.LENGTH_LONG).show();
            }

        }

        Log.d(TAG, "onConfirm: you clicked confirm!");
    }


    // returns true if empty
    private boolean checkField(EditText et) {
        return (et.getText().toString().isEmpty());
    }*/

    /*private File createImageFile() throws IOException {
        //create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        //save a file: path for use with ACTION_VIEW intents
        String currentPhotoPath = image.getAbsolutePath();
        return image;
    }*/

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