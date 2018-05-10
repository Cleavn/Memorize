package cleavn.memorize.ActivitiesAndFragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cleavn.memorize.AdapterAndListener.CategoryAdapter;
import cleavn.memorize.AdapterAndListener.MyDbAdapter;
import cleavn.memorize.Objects.Category;
import cleavn.memorize.R;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Category> categories;
    private Dialog addDialog;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.itemListView);
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        FloatingActionButton fabPlay = (FloatingActionButton) findViewById(R.id.fabPlay);

        categories = new ArrayList<Category>();
        addDialog = new Dialog(this);

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
        categories.add(new Category("Cat1", "description"));
        categories.add(new Category("Cat2", "description"));
        categories.add(new Category("Cat3", "description"));
        categories.add(new Category("Cat4", "description", Color.GREEN));
        categories.add(new Category("Cat5", "description"));
        categories.add(new Category("Cat6", "description"));
        categories.add(new Category("Cat7", "description"));
        categories.add(new Category("Cat8", "description"));
        categories.add(new Category("Cat9", "description"));
        */

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
                        myIntent.putExtra("CategoryID", id); //TODO: extract categoryID and parse here
                        startActivity(myIntent);
                    }
                }
        );

        //TODO: listView.setOnFlingListener - fling to show two buttons "edit" and "delete"
        //TODO: Deletebutton - pops dialog for confirmation
    }

    public void showAddDialog(){
        addDialog.setContentView(R.layout.dialog_category);
        //TODO: finish addDialog
    }
}
