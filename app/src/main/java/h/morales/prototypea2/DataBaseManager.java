package h.morales.prototypea2;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseManager extends SQLiteOpenHelper {

    //db name, table name, columns
    private static final String DATA_BASE_NAME = "productsDB",
                                TABLE_NAME = "productsTable",
                                productID = "id",
                                productName = "name",
                                productMedium = "medium",
                                productPurchasePrice = "purchasePrice",
                                productHeight = "height",
                                productWidth = "width",
                                productDepth = "depth",
                                productLocation = "location",
                                productPurchaseDate = "purchaseDate",
                                productFramed = "framed",
                                productPicturePath = "picturePath",

                                creationDate = "creationDate";

    private static final int DATABASE_VERSION = 1;

    //constructor
    public DataBaseManager(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreate = "create table " + TABLE_NAME + "( " +
                            productID + " integer primary key autoincrement, " +
                            productName + " text, " +
                            productMedium + " text, " +
                            productPurchasePrice + " real, " +
                            productHeight + " real, " +
                            productWidth + " real, " +
                            productDepth + " real, " +
                            productLocation + " text, " +
                            productPurchaseDate + " text, " +
                            productFramed + " text, " +
                            creationDate + " text, " +
                            productPicturePath + " text )";

        sqLiteDatabase.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        //
        String sqlDrop = "drop table if exists " + TABLE_NAME;

        sqLiteDatabase.execSQL(sqlDrop);
        onCreate(sqLiteDatabase);
    }

    // method to handle inserting a product
    public void insertProduct(Product product) {
        Log.d(TAG, "insertProduct: isFramed: " + product.isProductFramed());
        String sqlInsert = "insert into " + TABLE_NAME +
                            " values( null, '" + product.getProductName() + "'," +
                            " '" +  product.getProductMedium() + "'," +
                            " '" +  product.getProductPurchasePrice() + "'," +
                            " '" +  product.getProductHeight() + "'," +
                            " '" +  product.getProductWidth() + "'," +
                            " '" +  product.getProductDepth() + "'," +
                            " '" +  product.getProductLocation() + "'," +
                            " '" +  product.getProductPurchaseDate() + "'," +
                            " '" +  product.isProductFramed() + "'," +
                            " '" +  product.getCreationDate() + "'," +
                            " '" +  product.getProductPicturePath() +
                            "' )";

        //retrieve a db and insert the product
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sqlInsert);

        //close db after querying
        database.close();
    }

    // method to delete item based on productID
    public void deleteItem(int id) {
        String sqlDelete = "delete from " + TABLE_NAME + " where " + productID + "='" + id + "'";

        SQLiteDatabase database = getWritableDatabase(); // retrieve db

        //execute deletion
        database.execSQL(sqlDelete);

        database.close();
    }

    // method to retrieve all the database entries
    public ArrayList<Product> selectAll() {
        String sqlSelect = "select * from " + TABLE_NAME;

        //retrieve the db
        SQLiteDatabase database = getWritableDatabase();

        //retrieve the info from the db, it will be stored in a cursor obj
        Cursor cursor = database.rawQuery(sqlSelect, null);

        //create the arrayList to hold the info
        ArrayList<Product> products = new ArrayList<>();

        //loop through the cursor obj and transfer values to the arrayList
        while(cursor.moveToNext()) {
            //get info from cursor
            String currentID = cursor.getString(0); // get ID
            String currentName = cursor.getString(1);
            String currentMedium = cursor.getString(2);
            float currentPurchasePrice = cursor.getFloat(3);
            float currentHeight = cursor.getFloat(4);
            float currentWidth = cursor.getFloat(5);
            float currentDepth = cursor.getFloat(6);
            String currentLocation = cursor.getString(7);
            String currentPurchaseDate = cursor.getString(8);
            String currentFraming = cursor.getString(9);
            Log.d(TAG, "selectAll currentFraming: "+ currentFraming);
            boolean currentIsFramed = Boolean.parseBoolean(currentFraming);
            Log.d(TAG, "selectAll isframed?: " + currentIsFramed);
            String currentCreationDate = cursor.getString(10);
            String currentPicturePath = cursor.getString(11);

            //create a product obj
            Product product = new Product(currentID, currentName, currentMedium, currentPurchasePrice, currentHeight, currentWidth, currentDepth, currentLocation, currentPurchaseDate, currentIsFramed, currentPicturePath, currentCreationDate);
            //add the product to  arrayList
            products.add(product);
        } //end while loop

        database.close();

        return products;
    }
}
