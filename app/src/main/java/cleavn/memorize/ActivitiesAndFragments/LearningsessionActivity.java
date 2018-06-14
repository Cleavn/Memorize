package cleavn.memorize.ActivitiesAndFragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import cleavn.memorize.Utils.MyDbAdapter;
import cleavn.memorize.Objects.Card;
import cleavn.memorize.R;
import cleavn.memorize.Utils.MyGestureListener;

public class LearningsessionActivity extends AppCompatActivity implements CardFragment.OnFragmentInteractionListener {

    private Card card;
    private ArrayList<Card> cards, cardsWorkingIteration;
    public GestureDetector gestureDetector;

    private boolean mShowingBack;
    int categoryId, cardid;
    int timer;

    RelativeLayout timerLayout;
    TextView qaText;
    Button correct, skip, wrong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learningsession_layout);

        timerLayout = findViewById(R.id.ls_timerlayout);
        qaText = findViewById(R.id.ls_qaText);
        correct = findViewById(R.id.ls_true);
        skip = findViewById(R.id.ls_skip);
        wrong = findViewById(R.id.ls_false);

        categoryId = getIntent().getExtras().getInt("CategoryID");
        timer = getIntent().getExtras().getInt("Time");
        cardsWorkingIteration = new ArrayList<>();
        this.gestureDetector = new GestureDetector(this, new MyGestureListener(this));

        // get cards for learningsession
        MyDbAdapter dbAdapter = new MyDbAdapter(LearningsessionActivity.this);
        dbAdapter.open();
        cards = dbAdapter.getAllCardsFromCategory(categoryId);
        dbAdapter.close();

        startLearningSession();

        //TODO: onClickListener for buttons
    }

    private void startLearningSession() {
        cardsWorkingIteration = cards;

        cardid = getRandomCard();
        //cardsWorkingIteration.remove(cardid);

        card = cards.get(cardid);
        openFrontCardFragment(card);
    }

    private int getRandomCard() {
        Random random = new Random();
        return random.nextInt(cardsWorkingIteration.size());
    }

    public void openFrontCardFragment(Card card) {
        CardFragment frontFragment = CardFragment.newInstance(card, "front");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("FRONTCARD_FRAGMENT");
        transaction.add(R.id.ls_fragmentcontainer, frontFragment, "FRONTCARD_FRAGMENT").commit();
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
            transaction.replace(R.id.ls_fragmentcontainer, backFragment, "BACKCARD_FRAGMENT").commit();
            mShowingBack = true;
        }
        Log.d("FLIP", "flipCard:" + mShowingBack);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        onBackPressed();
    }
}
