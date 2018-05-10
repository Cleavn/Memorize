package cleavn.memorize.ActivitiesAndFragments;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

    private boolean mShowingBack;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gestureDetector = new GestureDetector(this, new MyGestureListener(this));

        //TODO: FloatingActionButton/Toolbarbutton - to create Categories
        //TODO: FloatingActionButton/Toolbarbutton - to start Learningsession

        /*
        //dummycards
        cards.add(new Card("P-Q-Formel","dummyformel", 0));
        cards.add(new Card("Test2","Testantwort", 0));
        */

        int categoryId = getIntent().getExtras().getInt("CategoryID");
        MyDbAdapter dbAdapter = new MyDbAdapter(ToonActivity.this);
        dbAdapter.open();
        cards = dbAdapter.getAllCardsFromCategory(categoryId);
        dbAdapter.close();

        cardAdapter = new CardAdapter(this, cards);
        ListView listView = (ListView) findViewById(R.id.itemListView);
        listView.setAdapter(cardAdapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //TODO: Swipe-Flip animation for card/fragment
                        //TODO: Replace Dialog with two Fragments?

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
