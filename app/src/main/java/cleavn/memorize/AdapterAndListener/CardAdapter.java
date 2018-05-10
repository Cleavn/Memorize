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

import cleavn.memorize.Objects.Card;
import cleavn.memorize.R;

public class CardAdapter extends ArrayAdapter<Card> {

    public static class ViewHolder{
        TextView cardQuestion;
    }

    public CardAdapter(@NonNull Context context, ArrayList<Card> cards) {
        super(context, 0, cards);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        Card card = getItem(position);
        ViewHolder viewHolder;

        // Check if an existing view is being reused, otherwise inflate a new view from custom row layout
        if (convertView == null){
            // If we don't have a view that is being used, create one and make sure you create a viewholder along with it to save our view references to
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);

            // Set our views to our viewholder so that we no longer have to go back and use find view by id every time we have a new row
            viewHolder.cardQuestion = (TextView) convertView.findViewById(R.id.cardQuestionTextView);

            // Use set tag to remember our viewholder
            convertView.setTag(viewHolder);
        }else{
            // We already have a view so just go to our viewholder and grab the widgets from it
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cardQuestion.setText(card.getQuestion());

        return convertView;
    }
}
