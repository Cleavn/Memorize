package cleavn.memorize.AdapterAndListener;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import cleavn.memorize.Objects.Card;
import cleavn.memorize.Objects.Category;

public class MyDbAdapter {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "memorize.db";

    private static final String TABLE_CATEGORIES = "products";
    private static final String COLUMN_CATEGORYID = "_id";
    private static final String COLUMN_CATEGORYNAME = "categoryname";
    private static final String COLUMN_CATEGORYDESCR = "categorydescr";
    private static final String COLUMN_CATEGORYCOLOR = "categorycolor";

    private String[] allCategoryColumns = {COLUMN_CATEGORYID, COLUMN_CATEGORYNAME, COLUMN_CATEGORYDESCR, COLUMN_CATEGORYCOLOR};

    private static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES + "("
            + COLUMN_CATEGORYID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CATEGORYNAME + " TEXT NOT NULL, "
            + COLUMN_CATEGORYDESCR + " TEXT, "
            + COLUMN_CATEGORYCOLOR + " INTEGER);";

    private static final String TABLE_CARDS = "cards";
    private static final String COLUMN_CARDID = "_cid";
    private static final String COLUMN_CARDQUESTION = "question";
    private static final String COLUMN_CARDANSWER = "answer";
    private static final String COLUMN_CARDCATEGORYID = "categoryid";

    private String[] allCardColumns = {COLUMN_CARDID, COLUMN_CARDQUESTION, COLUMN_CARDANSWER, COLUMN_CARDCATEGORYID};

    private static final String CREATE_TABLE_CARDS = "CREATE TABLE " + TABLE_CARDS + "("
            + COLUMN_CARDID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CARDQUESTION + " TEXT NOT NULL, "
            + COLUMN_CARDANSWER + " TEXT NOT NULL, "
            + COLUMN_CARDCATEGORYID + " INTEGER NOT NULL);";

    private SQLiteDatabase sqlDB;
    private Context context;
    private MyDbHelper dbHelper;

    public MyDbAdapter(Context context) {
        this.context = context;
    }

    public MyDbAdapter open() {
        dbHelper = new MyDbHelper(context);
        sqlDB = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public void createCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORYNAME, category.getCategoryName());
        values.put(COLUMN_CATEGORYDESCR, category.getCategoryDescr());
        values.put(COLUMN_CATEGORYCOLOR, category.getColor());

        sqlDB.insert(TABLE_CATEGORIES, null, values);
    }

    public void deleteCategory(int idToDelete) {
        sqlDB.delete(TABLE_CATEGORIES, COLUMN_CATEGORYID + " = " + idToDelete, null);
    }

    public void updateCategory(int idToUpdate, String newName, String newDescr, int newColor){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORYNAME, newName);
        values.put(COLUMN_CATEGORYDESCR, newDescr);
        values.put(COLUMN_CATEGORYCOLOR, newColor);

        sqlDB.update(TABLE_CATEGORIES, values, COLUMN_CATEGORYID + " = " + idToUpdate, null);
    }

    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();

        //grab all categories and their data from the categories table
        Cursor cursor = sqlDB.query(TABLE_CATEGORIES, allCategoryColumns, null, null, null, null, null);

        for(cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()){
            Category category = cursorToCategory(cursor);
            categories.add(category);
        }
        cursor.close();
        return categories;
    }

    private Category cursorToCategory(Cursor cursor) {
        return new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
    }

    public void createCard(Card card) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_CARDQUESTION, card.getQuestion());
        values.put(COLUMN_CARDANSWER, card.getAnswer());
        values.put(COLUMN_CARDCATEGORYID, card.getCategoryId());

        sqlDB.insert(TABLE_CARDS, null, values);
    }

    public void deleteCard(int idToDelete) {
        sqlDB.delete(TABLE_CARDS, COLUMN_CARDID + " = " + idToDelete, null);
    }

    public void updateCard(int idToUpdate, String newQuestion, String newAnswer, int categoryId){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CARDQUESTION, newQuestion);
        values.put(COLUMN_CARDANSWER, newAnswer);
        values.put(COLUMN_CARDCATEGORYID, categoryId);

        sqlDB.update(TABLE_CARDS, values, COLUMN_CARDID + " = " + idToUpdate, null);
    }

    public ArrayList<Card> getAllCardsFromCategory(int categoryId) {
        ArrayList<Card> cards = new ArrayList<Card>();

        Cursor cursor = sqlDB.query(TABLE_CARDS, allCardColumns, COLUMN_CARDCATEGORYID + " = " + categoryId, null, null, null, null);
        if (cursor != null) {
            for(cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()){
                Card card = cursorToCard(cursor);
                cards.add(card);
            }
        }
        cursor.close();
        return cards;
    }

    private Card cursorToCard(Cursor cursor) {
        return new Card(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3));
    }

    private static class MyDbHelper extends SQLiteOpenHelper{

        public MyDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = CREATE_TABLE_CATEGORIES;
            db.execSQL(query);

            String query2 = CREATE_TABLE_CARDS;
            db.execSQL(query2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(MyDbAdapter.class.getName(), "Updating database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
            onCreate(db);
        }
    }
}
