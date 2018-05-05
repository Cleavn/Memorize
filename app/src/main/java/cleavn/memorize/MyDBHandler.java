package cleavn.memorize;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import cleavn.memorize.Objects.Category;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "memorize.db";

    public static final String TABLE_CATEGORIES = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CATEGORYNAME = "categoryname";
    public static final String COLUMN_CATEGORYDESCR = "categorydescr";
    public static final String COLUMN_CATEGORYCOLOR = "categorycolor";

    public static final String TABLE_CARDS = "cards";
    //TODO: add columns for the cards-table

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CATEGORIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORYNAME + " TEXT, " +
                COLUMN_CATEGORYDESCR + " TEXT, " +
                COLUMN_CATEGORYCOLOR + " INTEGER " +
                ");";
        db.execSQL(query);

        //TODO: modify query
        String query2 = "CREATE TABLE " + TABLE_CARDS + "(" +
                COLUMN_ID +
                ");";
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        onCreate(db);
    }

    public void addCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORYNAME, category.getCategoryName());
        values.put(COLUMN_CATEGORYDESCR, category.getCategoryDescr());
        values.put(COLUMN_CATEGORYCOLOR, category.getColor());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CATEGORIES, null, values);
        db.close();
    }

    public void deleteCategory(String categoryName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CATEGORIES + " WHERE " + COLUMN_CATEGORYNAME + "=\"" + categoryName + "\";");
    }

    //TODO: reader
}
