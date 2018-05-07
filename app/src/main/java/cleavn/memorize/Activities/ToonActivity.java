package cleavn.memorize.Activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import cleavn.memorize.Adapter.CardAdapter;
import cleavn.memorize.Objects.Card;
import cleavn.memorize.R;

public class ToonActivity extends AppCompatActivity {

    private ArrayList<Card> cards;
    private ArrayList<Card> categoryCards;

    Dialog myDialog;
    TextView qaTextView;
    ImageButton btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDialog = new Dialog(this);

        int categoryId = getIntent().getExtras().getInt("CategoryID");

        //TODO: FloatingActionButton/Toolbarbutton - to create Categories
        //TODO: FloatingActionButton/Toolbarbutton - to start Learningsession

        //dummycards
        cards = new ArrayList<Card>();
        cards.add(new Card("P-Q-Formel","dummyformel", 0));
        cards.add(new Card("Test2","Testantwort", 0));

        //TODO: DB-Logic

        categoryCards = getCategoryCards(categoryId);
        CardAdapter cardAdapter = new CardAdapter(this, categoryCards);
        ListView listView = (ListView) findViewById(R.id.itemListView);
        listView.setAdapter(cardAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //TODO: Swipe-Flip animation for card/fragment
                        //TODO: Replace Dialog with two Fragments?
                        ShowPopup(); //parse question and answer
                    }
                });
    }

    private ArrayList<Card> getCategoryCards(int categoryId) {
        categoryCards = new ArrayList<Card>();

        for (int i = 0; i < cards.size(); i++){
            if (cards.get(i).getCategoryId() == categoryId){
                categoryCards.add(cards.get(i));
            }
        }
        return categoryCards;
    }

    public void ShowPopup(){
        myDialog.setContentView(R.layout.popup_card);
        qaTextView = (TextView) myDialog.findViewById(R.id.qaText);
        btnClose = (ImageButton) myDialog.findViewById(R.id.closeCardButton);

        //qaTextView.setText(qaText);

        btnClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
