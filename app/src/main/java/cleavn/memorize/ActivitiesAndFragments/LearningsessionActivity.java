package cleavn.memorize.ActivitiesAndFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Random;

import cleavn.memorize.AdapterAndListener.MyDbAdapter;
import cleavn.memorize.Objects.Card;
import cleavn.memorize.R;

public class LearningsessionActivity extends AppCompatActivity {

    ArrayList<Card> cards, cardsNextIteration;

    int categoryId, cardid;
    Time timer;

    RelativeLayout timerLayout;
    TextView qaText;
    Button correct, skip, wrong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.learningsession_layout);

        categoryId = getIntent().getExtras().getInt("CategoryID");
        //TODO get timer Intent
        cardsNextIteration = new ArrayList<>();

        MyDbAdapter dbAdapter = new MyDbAdapter(LearningsessionActivity.this);
        dbAdapter.open();
        cards = dbAdapter.getAllCardsFromCategory(categoryId);
        dbAdapter.close();

        cardid = getRandomCard();

    }

    private int getRandomCard() {
        int random;

        random = new Random().nextInt(cards.size());
        cardsNextIteration.add(cards.get(random));


        return random;
    }
}
