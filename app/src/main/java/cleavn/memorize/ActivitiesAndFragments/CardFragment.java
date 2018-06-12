package cleavn.memorize.ActivitiesAndFragments;

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
    private OnFragmentInteractionListener mListener;

    private  RelativeLayout fragmentBackground;
    private TextView questionTextView;
    private ImageButton closeBtn;
    private CardView cardview;

    public CardFragment() {
        // Required empty public constructor
    }

    public static CardFragment newInstance(Card card, String cardsite) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();

        String text;

        if (cardsite == "front"){
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_fragment, container, false);

        questionTextView = (TextView) view.findViewById(R.id.qaText);
        closeBtn = (ImageButton) view.findViewById(R.id.closeCardButton);
        fragmentBackground = (RelativeLayout) view.findViewById(R.id.fragmentBackground);
        cardview = (CardView) view.findViewById(R.id.cardView);

        questionTextView.setText(mQuestion);

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pops backstack including last fragment instance
                getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

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
                ToonActivity parentActivity = (ToonActivity) getActivity();
                parentActivity.gestureDetector.onTouchEvent(event);

                return true;
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
