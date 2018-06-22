package cleavn.memorize.ActivitiesAndFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;

import cleavn.memorize.Objects.Category;
import cleavn.memorize.Utils.CardAdapter;
import cleavn.memorize.Utils.MyDbAdapter;
import cleavn.memorize.Utils.MyGestureListener;
import cleavn.memorize.Objects.Card;
import cleavn.memorize.R;

public class ToonActivity extends AppCompatActivity implements CardFragment.OnFragmentInteractionListener {

    private ArrayList<Category> categories;
    private ArrayList<Card> cards;
    public GestureDetector gestureDetector;
    private Card card;

    SwipeMenuListView listView;
    SwipeMenuCreator creator;

    Dialog addDialog, editDialog, lsDialog;
    ImageButton cardDialogCloseBtn, lsCloseCardButton, lsAddCategory;
    EditText cardQuestion, cardAnswer;
    Button cardDialogBtn, lsStartBtn;
    TextView cardDialogTitle;
    Spinner spinner, hourSpinner, minuteSpinner;

    private boolean mShowingBack;
    int categoryId, starttime;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.itemListView);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        FloatingActionButton fabPlay = findViewById(R.id.fabPlay);

        this.gestureDetector = new GestureDetector(this, new MyGestureListener(this));
        cards = new ArrayList<>();

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
                showStartLearningsessionDialog();
            }
        });

        /*
        //dummycards
        cards.add(new Card("P-Q-Formel","dummyformel", 0));
        cards.add(new Card("Test2","Testantwort", 0));
        */

        getCardEntriesFromCategory(categoryId);

        createSwipeMenu();
        listView.setMenuCreator(creator);
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        showEditDialog(position);
                        break;
                    case 1:
                        showDeleteDialog(cards.get(position).getId(), cards.get(position).getQuestion());
                        break;
                }
                // False closes the menu after selecting
                return false;
            }
        });
    }

    // Get all entries of table cards where categoryId == selected from MainActivity and set it in listview
    public void getCardEntriesFromCategory(int categoryId){
        MyDbAdapter dbAdapter = new MyDbAdapter(ToonActivity.this);
        dbAdapter.open();
        cards = dbAdapter.getAllCardsFromCategory(categoryId);
        dbAdapter.close();

        CardAdapter cardAdapter = new CardAdapter(this, cards);
        listView.setAdapter(cardAdapter);
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast toast = Toast.makeText(getApplicationContext(), "position: " + cards.get(position).getId(), Toast.LENGTH_LONG);
                        toast.show();
                        card = cards.get(position);
                        openFrontCardFragment(card);
                    }
                });
    }

    public void showAddDialog(){
        addDialog = new Dialog(this);
        addDialog.setContentView(R.layout.dialog_card);

        cardDialogCloseBtn = addDialog.findViewById(R.id.cardDialogCloseCardButton);
        cardQuestion = addDialog.findViewById(R.id.cardQuestion);
        cardAnswer = addDialog.findViewById(R.id.cardAnswer);
        cardDialogBtn = addDialog.findViewById(R.id.cardDialogBtn);

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

    public void showEditDialog(final int position) {
        editDialog = new Dialog(this);
        editDialog.setContentView(R.layout.dialog_card);

        cardDialogCloseBtn = editDialog.findViewById(R.id.cardDialogCloseCardButton);
        cardQuestion = editDialog.findViewById(R.id.cardQuestion);
        cardAnswer = editDialog.findViewById(R.id.cardAnswer);
        cardDialogBtn = editDialog.findViewById(R.id.cardDialogBtn);
        cardDialogTitle = editDialog.findViewById(R.id.cardDialogTitle);

        cardQuestion.setText(cards.get(position).getQuestion());
        cardAnswer.setText(cards.get(position).getAnswer());
        cardDialogBtn.setText(R.string.edit);
        cardDialogTitle.setText(R.string.card_dialog_title_edit);

        cardDialogCloseBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                editDialog.dismiss();
            }
        });

        // Takes values of edittext and updates entry with it
        cardDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDbAdapter dbAdapter = new MyDbAdapter(ToonActivity.this);
                dbAdapter.open();
                dbAdapter.updateCard(cards.get(position).getId(), cardQuestion.getText().toString(), cardAnswer.getText().toString(), categoryId);
                dbAdapter.close();

                Toast.makeText(getApplicationContext(), "Card " + cardQuestion.getText().toString() + " updated", Toast.LENGTH_SHORT).show();
                getCardEntriesFromCategory(categoryId);
                editDialog.dismiss();
            }
        });
        editDialog.show();
    }

    public void showDeleteDialog(final int cardId, final String question) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ToonActivity.this);
        builder.setTitle(R.string.delete_card_title);
        builder.setMessage("Do you really want to delete this Card: " + question);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDbAdapter dbAdapter = new MyDbAdapter(ToonActivity.this);
                dbAdapter.open();
                dbAdapter.deleteCard(cardId);
                dbAdapter.close();

                //Toast.makeText(getApplicationContext(), "Card " + question + " deleted", Toast.LENGTH_SHORT).show();
                getCardEntriesFromCategory(categoryId);
                dialog.dismiss();
            }
        });
        AlertDialog deleteDialog = builder.create();
        deleteDialog.show();
    }

    public void showStartLearningsessionDialog() {
        lsDialog = new Dialog(this);
        lsDialog.setContentView(R.layout.dialog_learningsession);

        spinner = lsDialog.findViewById(R.id.lsDialog_setCategorySpinner);
        lsCloseCardButton = lsDialog.findViewById(R.id.lsDialog_CloseCardButton);
        lsAddCategory = lsDialog.findViewById(R.id.lsDialog_addCategory);
        hourSpinner = lsDialog.findViewById(R.id.lsDialog_setTimeHour);
        minuteSpinner = lsDialog.findViewById(R.id.lsDialog_setTimeMinute);
        lsStartBtn = lsDialog.findViewById(R.id.lsDialog_Btn);

        // fill spinner with data
        MyDbAdapter dbAdapter = new MyDbAdapter(ToonActivity.this);
        dbAdapter.open();
        categories = dbAdapter.getAllCategories();
        dbAdapter.close();
        ArrayAdapter<Category> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        //TODO fill time spinner

        lsCloseCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lsDialog.dismiss();
            }
        });

        lsAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add additional Spinner for additional category
            }
        });

        lsStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ToonActivity.this, LearningsessionActivity.class);

                if(!spinner.isSelected()){
                    //TODO ERROR
                }else{
                    if(!hourSpinner.isSelected() && !minuteSpinner.isSelected()){
                        starttime = 0;
                    }
                    //TODO: set hours and minutes in time
                    //starttime = ;
                }
                // checks if category has cards
                if(cards.size() == 0){
                    // Error - no cards in category
                    AlertDialog.Builder builder = new AlertDialog.Builder(ToonActivity.this);
                    builder.setTitle(R.string.error_learningsessiondialog_title);
                    builder.setMessage("This Category has no Cards. \nPlease make sure to add Cards before starting the Learningsession.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog errorDialog = builder.create();
                    errorDialog.show();
                }else{
                    myIntent.putExtra("CategoryID", categories.get(spinner.getSelectedItemPosition()).getId()); //TODO: Check if its the correct category
                    myIntent.putExtra("Time", starttime);
                    startActivity(myIntent);
                }
            }
        });
        lsDialog.show();
    }

    // Creates the buttons for the swipemenu
    public void createSwipeMenu() {
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "edit" item
                SwipeMenuItem editItem = new SwipeMenuItem(
                        getApplicationContext());
                editItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                editItem.setWidth(200);
                editItem.setTitle("Edit");
                editItem.setTitleSize(18);
                editItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(editItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(200);
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
    }

    /**!
     * fragmentmethods
     */

    public void openFrontCardFragment(Card card) {
        CardFragment frontFragment = CardFragment.newInstance(card, "front", this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack("FRONTCARD_FRAGMENT");
        transaction.add(R.id.fragmentContainer, frontFragment, "FRONTCARD_FRAGMENT").commit();
    }

    //TODO: flip anpassen je nach richtung - links/rechts
    public void flipCard() {
        if(mShowingBack) {
            // pops backstack to last fragment instance
            getSupportFragmentManager().popBackStackImmediate();
            mShowingBack = false;
        } else {
            CardFragment backFragment = CardFragment.newInstance(card, "back", this);
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
