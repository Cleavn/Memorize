package cleavn.memorize.ActivitiesAndFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cleavn.memorize.Objects.Card;
import cleavn.memorize.R;

public class CardFragment extends Fragment {
    // the fragment initialization parameters
    private static final String TEXT = "text";
    private String mQuestion;
    private static ToonActivity toonActivity;
    private static LearningsessionActivity learningsessionActivity;

    View view;

    public CardFragment() {
        // Required empty public constructor
    }

    public static CardFragment newInstance(Card card, String cardsite, ToonActivity toonActivity) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        CardFragment.toonActivity = toonActivity;

        String text;

        if (cardsite.equals("front")){
            text = card.getQuestion();
        } else {
            text = card.getAnswer();
        }

        args.putString(TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    public static CardFragment newInstance(Card card, String cardsite, LearningsessionActivity learningsessionActivity) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        CardFragment.learningsessionActivity = learningsessionActivity;

        String text;

        if (cardsite.equals("front")){
            text = card.getQuestion();
        } else {
            text = card.getAnswer();
        }

        args.putString(TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestion = getArguments().getString(TEXT);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (toonActivity == getActivity()){
            view = inflater.inflate(R.layout.card_fragment_layout, container, false);
            RelativeLayout fragmentBackground = view.findViewById(R.id.fragmentBackground);
            CardView cardview = view.findViewById(R.id.cardView);

            // Click on background removes fragments
            fragmentBackground.setSoundEffectsEnabled(false);
            fragmentBackground.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    // pops backstack including last fragment instance
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
            });

            // onTouchListener for fragments TODO: Bug - background spins with card
            cardview.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    toonActivity.gestureDetector.onTouchEvent(event);

                    return true;
                }
            });
        } else if(learningsessionActivity == getActivity()){
            view = inflater.inflate(R.layout.card_fragment, container, false);
            CardView ls_cardView = view.findViewById(R.id.ls_CardView);

            ls_cardView.setOnTouchListener(new View.OnTouchListener(){
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    learningsessionActivity.gestureDetector.onTouchEvent(event);

                    return true;
                }
            });
        }

        TextView questionTextView = view.findViewById(R.id.qaText);
        ImageButton closeBtn = view.findViewById(R.id.closeCardButton);

        questionTextView.setText(mQuestion);

        if(learningsessionActivity == getActivity()){
            closeBtn.setVisibility(View.INVISIBLE);
        }else{
            closeBtn.setVisibility(View.VISIBLE);
        }
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pops backstack including last fragment instance
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
