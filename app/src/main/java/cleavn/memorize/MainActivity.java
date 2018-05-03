package cleavn.memorize;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Category> categories;
    private CategoryAdapter categoryAdapter;

    Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: FloatingActionButton/Toolbarbutton - to create Categories
        //TODO: FloatingActionButton/Toolbarbutton - to start Learningsession

        //dummycategories
        categories = new ArrayList<Category>();
        categories.add(new Category("Maths", "formular", Color.BLUE));
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

        //TODO: SQL

        categoryAdapter = new CategoryAdapter(this, categories);
        ListView listView = (ListView) findViewById(R.id.categoryListView);
        listView.setAdapter(categoryAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // TODO: Intent to ToonActivity
                    }
                }
        );
    }
}
