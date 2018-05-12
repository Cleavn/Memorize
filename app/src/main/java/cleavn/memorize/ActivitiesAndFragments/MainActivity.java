package cleavn.memorize.ActivitiesAndFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import cleavn.memorize.AdapterAndListener.CategoryAdapter;
import cleavn.memorize.AdapterAndListener.MyDbAdapter;
import cleavn.memorize.Objects.Category;
import cleavn.memorize.R;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Category> categories;
    private CategoryAdapter categoryAdapter;

    SwipeMenuListView listView;
    SwipeMenuCreator creator;

    Dialog addDialog, editDialog;
    ImageButton categoryDialogCloseBtn;
    View categoryColorView;
    EditText categoryDialogName, categoryDialogDescr;
    Button categoryDialogBtn;
    TextView categoryDialogTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (SwipeMenuListView) findViewById(R.id.itemListView);
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        FloatingActionButton fabPlay = (FloatingActionButton) findViewById(R.id.fabPlay);

        categories = new ArrayList<Category>();

        // FloatingActionButton for adding new category
        fabAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

        // FloatingActionButton to start Learningsession
        fabPlay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //TODO: start Learningsession
            }
        });

        /*
        //dummycategories
        categories.add(new Category(0, "Maths", "formular", Color.BLUE));
        categories.add(new Category("English", "vocabulary", Color.YELLOW));
        categories.add(new Category("English: Business", "vocabulary", Color.YELLOW));
        categories.add(new Category("Spanish", "description"));
        categories.add(new Category("Geographics: Capital Cities", "description"));
        categories.add(new Category("Something unimportant", "description"));
        */
        getCategoryEntries();

        createSwipeMenu();
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Log.d("POSITION", "onMenuItemClick: " + position);
                switch (index){
                    case 0:
                        showEditDialog(position);
                        break;
                    case 1:
                        showDeleteDialog(categories.get(position).getId(), categories.get(position).getCategoryName());
                        break;
                }
                // False closes the menu after selecting
                return false;
            }
        });
    }

    // Get all entries of table categories and set it in listview
    public void getCategoryEntries(){
        MyDbAdapter dbAdapter = new MyDbAdapter(MainActivity.this);
        dbAdapter.open();
        categories = dbAdapter.getAllCategories();
        dbAdapter.close();

        categoryAdapter = new CategoryAdapter(this, categories);
        listView.setAdapter(categoryAdapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent myIntent = new Intent(MainActivity.this, ToonActivity.class);
                        myIntent.putExtra("CategoryID", categories.get(position).getId());
                        startActivity(myIntent);
                    }
                }
        );
    }

    // Shows dialog and logic for corresponding buttons
    public void showAddDialog(){
        addDialog = new Dialog(this);
        addDialog.setContentView(R.layout.dialog_category);
        categoryDialogCloseBtn = (ImageButton) addDialog.findViewById(R.id.categoryDialogCloseCardButton);
        categoryColorView = (View) addDialog.findViewById(R.id.categoryColorView);
        categoryDialogName = (EditText) addDialog.findViewById(R.id.categoryDialogName);
        categoryDialogDescr = (EditText) addDialog.findViewById(R.id.categoryDialogDescr);
        categoryDialogBtn = (Button) addDialog.findViewById(R.id.categoryDialogBtn);

        categoryDialogCloseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addDialog.dismiss();
            }
        });

        //TODO: custom spinner for categorycolor

        // Adds newly created category in db and refreshes the listview by calling getCategoryEntries()
        categoryDialogBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Category newCategory = new Category(categoryDialogName.getText().toString(), categoryDialogDescr.getText().toString(), Color.WHITE); //TODO: pass in color from spinner
                MyDbAdapter dbAdapter = new MyDbAdapter(MainActivity.this);
                dbAdapter.open();
                dbAdapter.createCategory(newCategory);
                dbAdapter.close();

                Toast.makeText(getApplicationContext(), "Category " + categoryDialogName.getText().toString() + " created", Toast.LENGTH_SHORT).show();
                getCategoryEntries();
                addDialog.dismiss();
            }
        });
        addDialog.show();
    }

    public void showEditDialog(final int position){
        editDialog = new Dialog(this);
        editDialog.setContentView(R.layout.dialog_category);
        categoryDialogCloseBtn = (ImageButton) editDialog.findViewById(R.id.categoryDialogCloseCardButton);
        categoryColorView = (View) editDialog.findViewById(R.id.categoryColorView);
        categoryDialogName = (EditText) editDialog.findViewById(R.id.categoryDialogName);
        categoryDialogDescr = (EditText) editDialog.findViewById(R.id.categoryDialogDescr);
        categoryDialogBtn = (Button) editDialog.findViewById(R.id.categoryDialogBtn);
        categoryDialogTitle = (TextView) editDialog.findViewById(R.id.categoryDialogTitle);

        categoryColorView.setBackgroundColor(categories.get(position).getColor());
        categoryDialogName.setText(categories.get(position).getCategoryName());
        categoryDialogDescr.setText(categories.get(position).getCategoryDescr());
        categoryDialogBtn.setText(R.string.edit);
        categoryDialogTitle.setText(R.string.category_dialog_title_edit);

        categoryDialogCloseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });

        //TODO: custom spinner for categorycolor

        // Takes values of edittext and updates entry with it
        categoryDialogBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MyDbAdapter dbAdapter = new MyDbAdapter(MainActivity.this);
                dbAdapter.open();
                dbAdapter.updateCategory(categories.get(position).getId(), categoryDialogName.getText().toString(), categoryDialogDescr.getText().toString(), categoryColorView.getDrawingCacheBackgroundColor());
                dbAdapter.close();

                Toast.makeText(getApplicationContext(), "Category " + categoryDialogName.getText().toString() + " updated", Toast.LENGTH_SHORT).show();
                getCategoryEntries();
                editDialog.dismiss();
            }
        });
        editDialog.show();
    }

    public void showDeleteDialog(final int categoryId, String categoryName){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.delete_category_title);
        builder.setMessage("Do you really want to delete this Category: " + categoryName);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDbAdapter dbAdapter = new MyDbAdapter(MainActivity.this);
                dbAdapter.open();
                dbAdapter.deleteCategory(categoryId);
                dbAdapter.close();
            }
        });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
    }

    // Creates the buttons for the swipemenu
    public void createSwipeMenu(){
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "edit" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                editItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                editItem.setWidth(170);
                editItem.setTitle("Edit");
                editItem.setTitleSize(18);
                editItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(170);
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
    }
}
