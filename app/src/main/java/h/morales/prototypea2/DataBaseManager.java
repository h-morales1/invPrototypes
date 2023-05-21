package h.morales.prototypea2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
                                productPicturePath = "picturePath";

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
                            productFramed + " integer, " +
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
}
