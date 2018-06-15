package cleavn.memorize.ActivitiesAndFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import cleavn.memorize.Utils.MyDbAdapter;
import cleavn.memorize.Objects.Card;
import cleavn.memorize.R;
import cleavn.memorize.Utils.MyGestureListener;

public class LearningsessionActivity extends AppCompatActivity implements CardFragment.OnFragmentInteractionListener {

    private Card card;
    private ArrayList<Card> cards, cardsIteration;
    public GestureDetector gestureDetector;

    private boolean mShowingBack;
    int categoryId, position;
    int timer;

    RelativeLayout timerLayout;
    TextView lsTimer;
    Button correct, skip, wrong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learningsession_layout);

        timerLayout = findViewById(R.id.ls_timerlayout);
        lsTimer = findViewById(R.id.ls_Timer);
        correct = findViewById(R.id.ls_true);
        skip = findViewById(R.id.ls_skip);
        wrong = findViewById(R.id.ls_false);

        categoryId = getIntent().getExtras().getInt("CategoryID");
        timer = getIntent().getExtras().getInt("Time"); // in ms
        cardsIteration = new ArrayList<>();
        this.gestureDetector = new GestureDetector(this, new MyGestureListener(this));

        // get cards for learningsession
        MyDbAdapter dbAdapter = new MyDbAdapter(LearningsessionActivity.this);
        dbAdapter.open();
        cards = dbAdapter.getAllCardsFromCategory(categoryId);
        dbAdapter.close();

        cardsIteration.addAll(cards);
        nextCard();

        // timerlayout pauses the learningsession and shows menu
        timerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseLearningsession();
            }
        });

        // correct-button getsNext card and add +1 to statistics of current cardid
        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add +1 to statistics of current cardid
                //cardsIteration.get(position).getId();

                if(cardsIteration.isEmpty()) {
                    cardsIteration.addAll(cards);
                }
                nextCard();
            }
        });

        // wrong-button getsNext card and add +1 to statistics of current cardid
        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add +1 to statistics of current cardid
                //cardsIteration.get(position).getId();

                if(cardsIteration.isEmpty()) {
                    cardsIteration.addAll(cards);
                }
                nextCard();
            }
        });

        // skip-button getsNext card
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cardsIteration.isEmpty()) {
                    cardsIteration.addAll(cards);
                }
                nextCard();
            }
        });
    }

    private void nextCard() {
        position = getRandom();
        card = cardsIteration.get(position);
        cardsIteration.remove(position);

        openFrontCardFragment(card);
    }

    private int getRandom() {
        Random random = new Random();
        return random.nextInt(cardsIteration.size());
    }

    private void pauseLearningsession(){
        //TODO: pause timer
        //TODO: set gray overlay
        //TODO: options menu - resume, show statistic, quit
    }

    private void quitLearningsession(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LearningsessionActivity.this);
        builder.setTitle(R.string.quit_learningsession_title);
        builder.setMessage("Do you really want to quit this Learningsession?");
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDbAdapter dbAdapter = new MyDbAdapter(LearningsessionActivity.this);
                finish();
                dialog.dismiss();
            }
        });
        AlertDialog quitDialog = builder.create();
        quitDialog.show();
    }

    public void openFrontCardFragment(Card card) {
        CardFragment frontFragment = CardFragment.newInstance(card, "front", this);
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
            CardFragment backFragment = CardFragment.newInstance(card, "back", this);
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

    @Override
    public void onBackPressed() {
        //TODO: Stop time as long as dialog
        //TODO: dialog
        quitLearningsession();
    }
}
