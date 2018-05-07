package cleavn.memorize.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cleavn.memorize.Adapter.CategoryAdapter;
import cleavn.memorize.Objects.Category;
import cleavn.memorize.R;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Category> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: FloatingActionButton/Toolbarbutton - to create Categories
        //TODO: FloatingActionButton/Toolbarbutton - to start Learningsession

        //dummycategories
        categories = new ArrayList<Category>();
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

        //TODO: DB-Logic

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        ListView listView = (ListView) findViewById(R.id.itemListView);
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
    }
}
