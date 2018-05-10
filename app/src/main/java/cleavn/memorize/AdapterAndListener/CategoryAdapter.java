package cleavn.memorize.AdapterAndListener;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cleavn.memorize.Objects.Category;
import cleavn.memorize.R;

public class CategoryAdapter extends ArrayAdapter<Category> {

    public static class ViewHolder{
        TextView categoryName;
        TextView categoryDescr;
        View categoryColor;
    }

    public CategoryAdapter(Context context, ArrayList<Category> categories){
        super(context, 0, categories);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        Category category = getItem(position);
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate a new view from custom row layout
        if (convertView == null){
            // If we don't have a view that is being used, create one and make sure you create a viewholder along with it to save our view references to
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_item, parent, false);

            // Set our views to our viewholder so that we no longer have to go back and use find view by id every time we have a new row
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.categoryNameTextView);
            viewHolder.categoryDescr = (TextView) convertView.findViewById(R.id.categoryDescrTextView);
            viewHolder.categoryColor = (View) convertView.findViewById(R.id.categoryColorView);

            // Use set tag to remember our viewholder
            convertView.setTag(viewHolder);
        }else{
            // We already have a view so just go to our viewholder and grab the widgets from it
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.categoryName.setText(category.getCategoryName());
        viewHolder.categoryDescr.setText(category.getCategoryDescr());
        viewHolder.categoryColor.setBackgroundColor(category.getColor());

        return convertView;
    }
}
