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
                                productNote = "note",
                                productFramed = "framed",
                                productSold = "sold",
                                productPicturePath = "picturePath",

                                creationDate = "creationDate";

    private static final String NEW_TABLE_NAME = "productsTable2";

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
                            productHeight + " text, " +
                            productWidth + " text, " +
                            productDepth + " text, " +
                            productLocation + " text, " +
                            productPurchaseDate + " text, " +
                            productNote + " text, " +
                            productFramed + " text, " +
                            productSold + " text, " +
                            creationDate + " text, " +
                            productPicturePath + " text )";

        sqLiteDatabase.execSQL(sqlCreate);
    }

    public void upDateColumn() {
        //get old data
        ArrayList<Product> oldData = selectAll();

        //create new schema
        SQLiteDatabase database = getWritableDatabase();
        String sqlCreate = "create table " + NEW_TABLE_NAME + "( " +
                productID + " integer primary key autoincrement, " +
                productName + " text, " +
                productMedium + " text, " +
                productPurchasePrice + " text, " +
                productHeight + " text, " +
                productWidth + " text, " +
                productDepth + " text, " +
                productLocation + " text, " +
                productPurchaseDate + " text, " +
                productNote + " text, " +
                productFramed + " text, " +
                productSold + " text, " +
                creationDate + " text, " +
                productPicturePath + " text )";

        database.execSQL(sqlCreate);
        //transfer old data
        for( int i=0; i< oldData.size();i++) {
            Product product = oldData.get(i);
            String sqlInsert = "insert into " + NEW_TABLE_NAME +
                    " values( null, '" + product.getProductName() + "'," +
                    " '" +  product.getProductMedium() + "'," +
                    " '" +  product.getProductPurchasePrice() + "'," +
                    " '" +  product.getProductHeight() + "'," +
                    " '" +  product.getProductWidth() + "'," +
                    " '" +  product.getProductDepth() + "'," +
                    " '" +  product.getProductLocation() + "'," +
                    " '" +  product.getProductPurchaseDate() + "'," +
                    " '" +  product.getProductNote() + "'," +
                    " '" +  product.isProductFramed() + "'," +
                    " '" +  product.isProductSold() + "'," +
                    " '" +  product.getCreationDate() + "'," +
                    " '" +  product.getProductPicturePath() +
                    "' )";
            database.execSQL(sqlInsert);
        }
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
                            " '" +  product.getProductNote() + "'," +
                            " '" +  product.isProductFramed() + "'," +
                            " '" +  product.isProductSold() + "'," +
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

    //method to update a product using the id as entry
    public void updateProduct(int prodID, Product prod) {
        String setVals = productName + " = " + "'" + prod.getProductName() + "', " +
                         productMedium + " = " + "'" + prod.getProductMedium() + "', " +
                         productPurchasePrice + " = " + "'" + prod.getProductPurchasePrice() + "', " +
                         productHeight + " = " + "'" + prod.getProductHeight() + "', " +
                         productWidth + " = " + "'" + prod.getProductWidth() + "', " +
                         productDepth + " = " + "'" + prod.getProductDepth() + "', " +
                         productLocation + " = " + "'" + prod.getProductLocation() + "', " +
                         productPurchaseDate + " = " + "'" + prod.getProductPurchaseDate() + "', " +
                         productNote + " = " + "'" + prod.getProductNote() + "', " +
                         productFramed + " = " + "'" + prod.isProductFramed() + "', " +
                         productSold + " = " + "'" + prod.isProductSold() + "', " +
                         creationDate + " = " + "'" + prod.getCreationDate() + "', " +
                         productPicturePath + " = " + "'" + prod.getProductPicturePath() + "' ";
        //String sqlUpdate = "update " + TABLE_NAME + setVals + " where " + productID + " = " + prodID;
        String sqlUpdate = "UPDATE " + TABLE_NAME + " SET " + setVals + " WHERE " + productID + " = " + prodID;

        SQLiteDatabase database = getWritableDatabase(); // retrieve db

        //execute update
        database.execSQL(sqlUpdate);

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
            String currentPurchasePrice = String.valueOf(cursor.getFloat(3));
            String currentHeight = cursor.getString(4);
            String currentWidth = cursor.getString(5);
            String currentDepth = cursor.getString(6);
            String currentLocation = cursor.getString(7);
            String currentPurchaseDate = cursor.getString(8);
            String currentNote = cursor.getString(9);
            String currentFraming = cursor.getString(10);
            String currentSold = cursor.getString(11);
            Log.d(TAG, "selectAll: note="+ currentNote);
            Log.d(TAG, "selectAll currentFraming: "+ currentFraming);
            boolean currentIsFramed = Boolean.parseBoolean(currentFraming);
            boolean currentIsSold = Boolean.parseBoolean(currentSold);
            Log.d(TAG, "selectAll isframed?: " + currentIsFramed);
            String currentCreationDate = cursor.getString(12);
            String currentPicturePath = cursor.getString(13);

            //create a product obj
            //Product product = new Product(currentID, currentName, currentMedium, currentPurchasePrice, currentHeight, currentWidth, currentDepth, currentLocation, currentPurchaseDate, currentIsFramed, currentPicturePath, currentCreationDate);
            Product product = new Product(currentID, currentName, currentMedium, currentPurchasePrice, currentHeight, currentWidth, currentDepth, currentLocation, currentPurchaseDate, currentNote, currentIsFramed, currentIsSold, currentPicturePath, currentCreationDate);
            //add the product to  arrayList
            products.add(product);
        } //end while loop

        database.close();

        return products;
    }
}
