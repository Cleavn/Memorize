package cleavn.memorize.Activities;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cleavn.memorize.Adapter.CardAdapter;
import cleavn.memorize.CardFragment;
import cleavn.memorize.Objects.Card;
import cleavn.memorize.R;

public class ToonActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, CardFragment.OnFragmentInteractionListener {

    private ArrayList<Card> cards;
    private ArrayList<Card> categoryCards;

    private GestureDetectorCompat gestureDetector;

    Card card;
    // Dialog myDialog;

    boolean mShowingBack;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gestureDetector = new GestureDetectorCompat(this,this);

        // myDialog = new Dialog(this);

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

                        card = cards.get(position);
                        openFrontCardFragment(card);

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

    public void openFrontCardFragment(Card card) {
        CardFragment frontFragment = CardFragment.newInstance(card, "front");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragmentContainer, frontFragment, "FRONTCARD_FRAGMENT").commit();
    }

    public void flipCard() {
        if(mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }else{
            CardFragment backFragment = CardFragment.newInstance(card, "back");
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.animator.card_flip_right_in, R.animator.card_flip_right_out, R.animator.card_flip_left_in, R.animator.card_flip_left_out);
            transaction.replace(R.id.fragmentContainer, backFragment);
            transaction.addToBackStack(null);
            transaction.add(R.id.fragmentContainer, backFragment, "BACKCARD_FRAGMENT").commit();

            mShowingBack = true;
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;

        /*
        // Define right swipe
        final int SWIPE_THRESHOLD = 100;
        final int SWIPE_VELOCITY_THRESHOLD = 100;

        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        // onSwipeRight
                        flipCard();
                    }
                    result = true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        */

        flipCard();
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        onBackPressed();
    }
}
