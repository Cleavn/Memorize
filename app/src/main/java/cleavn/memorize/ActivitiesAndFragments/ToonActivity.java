package cleavn.memorize.ActivitiesAndFragments;

import android.app.Dialog;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import cleavn.memorize.AdapterAndListener.CardAdapter;
import cleavn.memorize.AdapterAndListener.MyDbAdapter;
import cleavn.memorize.AdapterAndListener.MyGestureListener;
import cleavn.memorize.Objects.Card;
import cleavn.memorize.R;

public class ToonActivity extends AppCompatActivity implements CardFragment.OnFragmentInteractionListener {

    private ArrayList<Card> cards;
    public GestureDetector gestureDetector;
    private Card card;
    private CardAdapter cardAdapter;

    SwipeMenuListView listView;

    Dialog addDialog;
    ImageButton cardDialogCloseBtn;
    EditText cardQuestion, cardAnswer;
    Button cardDialogBtn;

    private boolean mShowingBack;
    int categoryId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (SwipeMenuListView) findViewById(R.id.itemListView);
        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        FloatingActionButton fabPlay = (FloatingActionButton) findViewById(R.id.fabPlay);
        this.gestureDetector = new GestureDetector(this, new MyGestureListener(this));

        cards = new ArrayList<Card>();

        categoryId = getIntent().getExtras().getInt("CategoryID");

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
        //dummycards
        cards.add(new Card("P-Q-Formel","dummyformel", 0));
        cards.add(new Card("Test2","Testantwort", 0));
        */

        getCardEntriesFromCategory(categoryId);
    }

    public void getCardEntriesFromCategory(int categoryId){
        MyDbAdapter dbAdapter = new MyDbAdapter(ToonActivity.this);
        dbAdapter.open();
        cards = dbAdapter.getAllCardsFromCategory(categoryId);
        dbAdapter.close();

        cardAdapter = new CardAdapter(this, cards);
        listView.setAdapter(cardAdapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        card = cards.get(position);
                        openFrontCardFragment(card);
                    }
                });
    }

    public void openFrontCardFragment(Card card) {
        CardFragment frontFragment = CardFragment.newInstance(card, "front");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("FRONTCARD_FRAGMENT");
        transaction.add(R.id.fragmentContainer, frontFragment, "FRONTCARD_FRAGMENT").commit();
    }

    public void showAddDialog(){
        addDialog = new Dialog(this);
        addDialog.setContentView(R.layout.dialog_card);
        cardDialogCloseBtn = (ImageButton) addDialog.findViewById(R.id.cardDialogCloseCardButton);
        cardQuestion = (EditText) addDialog.findViewById(R.id.cardQuestion);
        cardAnswer = (EditText) addDialog.findViewById(R.id.cardAnswer);
        cardDialogBtn = (Button) addDialog.findViewById(R.id.cardDialogBtn);

        cardDialogCloseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                addDialog.dismiss();
            }
        });

        cardDialogBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Card newCard = new Card(cardQuestion.getText().toString(), cardAnswer.getText().toString(), categoryId);
                MyDbAdapter dbAdapter = new MyDbAdapter(ToonActivity.this);
                dbAdapter.open();
                dbAdapter.createCard(newCard);
                dbAdapter.close();

                Toast.makeText(getApplicationContext(), "Card " + cardQuestion.getText().toString() + " created", Toast.LENGTH_SHORT).show();
                getCardEntriesFromCategory(categoryId);
                addDialog.dismiss();
            }
        });
        addDialog.show();
    }

    //TODO: flip anpassen je nach richtung - links/rechts
    public void flipCard() {
        if(mShowingBack) {
            getSupportFragmentManager().popBackStackImmediate();
            mShowingBack = false;
        } else {
            CardFragment backFragment = CardFragment.newInstance(card, "back");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in, R.animator.card_flip_left_out);
            transaction.addToBackStack("BACKCARD_FRAGMENT");
            transaction.replace(R.id.fragmentContainer, backFragment, "BACKCARD_FRAGMENT").commit();
            mShowingBack = true;
        }
        Log.d("FLIP", "flipCard:" + mShowingBack);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        onBackPressed();
    }
}
