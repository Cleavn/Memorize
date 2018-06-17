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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import cleavn.memorize.Objects.Card;
import cleavn.memorize.Utils.CategoryAdapter;
import cleavn.memorize.Utils.ColorPicker;
import cleavn.memorize.Utils.MyDbAdapter;
import cleavn.memorize.Objects.Category;
import cleavn.memorize.R;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Category> categories;
    private ArrayList<Card> cards;

    SwipeMenuListView listView;
    SwipeMenuCreator creator;

    Dialog addDialog, editDialog, lsDialog;
    ColorPicker colorDialog;
    ImageButton categoryDialogCloseBtn, lsCloseCardButton, lsAddCategory;
    View categoryColorView;
    EditText categoryDialogName, categoryDialogDescr;
    Button categoryDialogBtn, lsStartBtn;
    TextView categoryDialogTitle;
    Spinner spinner, hourSpinner, minuteSpinner;

    int starttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.itemListView);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        FloatingActionButton fabPlay = findViewById(R.id.fabPlay);

        //TODO: Change Toolbartitle according to where you are

        categories = new ArrayList<>();

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
                showStartLearningsessionDialog();
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

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
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

    public void showAddDialog(){
        addDialog = new Dialog(this);
        addDialog.setContentView(R.layout.dialog_category);

        categoryDialogCloseBtn = addDialog.findViewById(R.id.categoryDialogCloseCardButton);
        categoryColorView = addDialog.findViewById(R.id.categoryColorView);
        categoryDialogName = addDialog.findViewById(R.id.categoryDialogName);
        categoryDialogDescr = addDialog.findViewById(R.id.categoryDialogDescr);
        categoryDialogBtn = addDialog.findViewById(R.id.categoryDialogBtn);

        categoryDialogCloseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addDialog.dismiss();
            }
        });

        // Calls colorpicker and set backgroundcolor of view
        categoryColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: colorpicker
                colorDialog = new ColorPicker(getBaseContext());
                int color = colorDialog.getColor();
                categoryColorView.setBackgroundColor(color);
                colorDialog.show();
            }
        });

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

        categoryDialogCloseBtn = editDialog.findViewById(R.id.categoryDialogCloseCardButton);
        categoryColorView = editDialog.findViewById(R.id.categoryColorView);
        categoryDialogName = editDialog.findViewById(R.id.categoryDialogName);
        categoryDialogDescr = editDialog.findViewById(R.id.categoryDialogDescr);
        categoryDialogBtn = editDialog.findViewById(R.id.categoryDialogBtn);
        categoryDialogTitle = editDialog.findViewById(R.id.categoryDialogTitle);

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

        // Calls colorpicker and set backgroundcolor of view
        categoryColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: colorpicker
            }
        });

        // Takes values of edittext and updates entry with it
        categoryDialogBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                MyDbAdapter dbAdapter = new MyDbAdapter(MainActivity.this);
                dbAdapter.open();
                dbAdapter.updateCategory(categories.get(position).getId(), categoryDialogName.getText().toString(), categoryDialogDescr.getText().toString(), ((ColorDrawable) categoryColorView.getBackground()).getColor());
                dbAdapter.close();

                Toast.makeText(getApplicationContext(), "Category " + categoryDialogName.getText().toString() + " updated", Toast.LENGTH_SHORT).show();
                getCategoryEntries();
                editDialog.dismiss();
            }
        });
        editDialog.show();
    }

    public void showDeleteDialog(final int categoryId, final String categoryName){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.delete_category_title);
        builder.setMessage("Do you really want to delete this Category: " + categoryName);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDbAdapter dbAdapter = new MyDbAdapter(MainActivity.this);
                dbAdapter.open();
                dbAdapter.deleteCategory(categoryId);
                dbAdapter.close();

                Toast.makeText(getApplicationContext(), "Category " + categoryName + " deleted", Toast.LENGTH_SHORT).show();
                getCategoryEntries();
                dialog.dismiss();
            }
        });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
    }

    public void showStartLearningsessionDialog() {
        lsDialog = new Dialog(this);
        lsDialog.setContentView(R.layout.dialog_learningsession);

        spinner = lsDialog.findViewById(R.id.lsDialog_setCategorySpinner);
        lsCloseCardButton = lsDialog.findViewById(R.id.lsDialog_CloseCardButton);
        lsAddCategory = lsDialog.findViewById(R.id.lsDialog_addCategory);
        hourSpinner = lsDialog.findViewById(R.id.lsDialog_setTimeHour);
        minuteSpinner = lsDialog.findViewById(R.id.lsDialog_setTimeMinute);
        lsStartBtn = lsDialog.findViewById(R.id.lsDialog_Btn);

        // fill spinner with data
        ArrayAdapter<Category> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        //TODO fill time spinner

        lsCloseCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lsDialog.dismiss();
            }
        });

        lsAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add additional Spinner for additional category
            }
        });

        lsStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, LearningsessionActivity.class);

                if(!spinner.isSelected()){
                    //TODO ERROR
                }else{
                    if(!hourSpinner.isSelected() && !minuteSpinner.isSelected()){
                        starttime = 0;
                    }
                    //TODO: set hours and minutes in time
                    //starttime = ;
                }
                // checks if category has cards
                MyDbAdapter dbAdapter = new MyDbAdapter(MainActivity.this);
                dbAdapter.open();
                cards = dbAdapter.getAllCardsFromCategory(categories.get(spinner.getSelectedItemPosition()).getId());
                dbAdapter.close();
                if(cards.size() == 0){
                    // Error - no cards in category
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.error_learningsessiondialog_title);
                    builder.setMessage("This Category has no Cards. \nPlease make sure to add Cards before starting the Learningsession.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog errorDialog = builder.create();
                    errorDialog.show();
                }else{
                    myIntent.putExtra("CategoryID", categories.get(spinner.getSelectedItemPosition()).getId()); //TODO: Check if its the correct category
                    myIntent.putExtra("Time", starttime);
                    startActivity(myIntent);
                }
            }
        });
        lsDialog.show();
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
                editItem.setWidth(200);
                editItem.setTitle("Edit");
                editItem.setTitleSize(18);
                editItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(200);
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
    }
}
