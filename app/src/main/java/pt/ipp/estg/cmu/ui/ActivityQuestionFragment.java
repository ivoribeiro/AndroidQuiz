package pt.ipp.estg.cmu.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Random;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.interfaces.ClickQuestionListener;
import pt.ipp.estg.cmu.util.UtilUI;

/**
 * A placeholder fragment containing a simple view.
 */
public class ActivityQuestionFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ClickQuestionListener mListener;
    private int index;

    //layout
    private static int NUMBER_GAME_BUTTONS = 15;
    private LinearLayout mAnswerLayout;
    private TableLayout mTableLayout;
    private ImageView mImageView;
    private BottomNavigationView mBottomNavigation;


    //strings
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String mCorrectAnswer;
    private String mCorrectAnswerConcat;
    private String mRandomGameString;

    private String mUserCorrectAnswer;

    private int mCorrectAnswerSize;
    private int mAtualCorrectIndex;


    public ActivityQuestionFragment() {
    }

    public static ActivityQuestionFragment newInstance(int index, String correctAnswer, String concat) {
        Bundle args = new Bundle();
        args.putString("CORRECT", correctAnswer);
        args.putString("CONCAT", concat);
        args.putInt("INDEX", index);
        ActivityQuestionFragment fragment = new ActivityQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCorrectAnswer = getArguments().getString("CORRECT");
        mCorrectAnswerConcat = getArguments().getString("CONCAT");
        index = getArguments().getInt("INDEX");

        mUserCorrectAnswer = "";
        mAtualCorrectIndex = 0;
        mCorrectAnswerSize = mCorrectAnswerConcat.length();
        String abc = removeStringFromString(mCorrectAnswerConcat, alphabet);

        int saltSize = NUMBER_GAME_BUTTONS - mCorrectAnswerSize;
        String saltString = generateString(abc, saltSize);

        saltString = saltString + mCorrectAnswerConcat;
        mRandomGameString = generateString(saltString, NUMBER_GAME_BUTTONS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_question, container, false);

        mAnswerLayout = (LinearLayout) view.findViewById(R.id.answer_layout);
        mTableLayout = (TableLayout) view.findViewById(R.id.game_table_layout);

        mImageView = (ImageView) view.findViewById(R.id.question_image);
        switch (index) {
            case 0:
                mImageView.setBackground(getActivity().getResources().getDrawable(R.drawable.question_1));
                break;
            case 1:
                mImageView.setBackground(getActivity().getResources().getDrawable(R.drawable.question_2));
                break;
        }

        mBottomNavigation = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        mBottomNavigation.setOnNavigationItemSelectedListener(this);

        createLayout();

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (ActivityQuestion) context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                break;
            case R.id.action_reset:
                createLayout();
                break;
            case R.id.action_level:
                break;
        }
        return false;
    }

    private void createLayout() {
        buildLayoutGame();
        buildLayoutAnswer();
        mUserCorrectAnswer = "";
        mAtualCorrectIndex = 0;
    }


    private void buildLayoutAnswer() {
        mAnswerLayout.removeAllViews();
        int index = 0;
        for (int i = 0; i < mCorrectAnswer.length(); ++i) {
            if (mCorrectAnswer.charAt(i) == ' ') {
                mAnswerLayout.addView(UtilUI.newView(getActivity()));
            } else {
                Button b = UtilUI.newButton(getActivity(), ' ');
                b.setId(index++);
                mAnswerLayout.addView(b);
            }
        }
    }

    private void buildLayoutGame() {
        mTableLayout.removeAllViews();

        TableRow row = new TableRow(getActivity());
        TableRow.LayoutParams params = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1.0f);

        for (int i = 0; i < mRandomGameString.length(); ++i) {

            final Button b = UtilUI.newButton(getActivity(), mRandomGameString.charAt(i));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //int indexChar = mCorrectAnswerConcat.indexOf(b.getText().toString());
                    Button b2 = (Button) mAnswerLayout.findViewById(mAtualCorrectIndex);
                    if (b2 != null) {
                        b2.setText(b.getText());
                        b.setVisibility(View.INVISIBLE);
                        ++mAtualCorrectIndex;
                        mUserCorrectAnswer = mUserCorrectAnswer + b.getText();
                        checkIfIsFinished();

                    }
                }
            });

            row.addView(b, params);
            if (i == 4 || i == 9 || i == 14) {
                mTableLayout.addView(row);
                row = new TableRow(getActivity());
            }
        }
    }

    private void checkIfIsFinished() {
        if (mUserCorrectAnswer.equals(mCorrectAnswerConcat)) {
            mListener.setCorrectAnswer();
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////// STRINGS MANIPULATION
    private static String generateString(String characters, int length) {
        Random rng = new Random();

        ArrayList<Character> result = new ArrayList<>();
        while (result.size() < length) {
            char ch = characters.charAt(rng.nextInt(characters.length()));
            if (!result.contains(ch)) {
                result.add(ch);
            }
        }

        String text = "";
        for (int i = 0; i < result.size(); ++i) {
            text = text + result.get(i);
        }
        return text;
    }

    private String removeStringFromString(String answer, String abc) {
        for (int i = 0; i < answer.length(); ++i) {
            if (abc.contains(answer.charAt(i) + "")) {
                abc = deleteCharAt(abc, abc.indexOf(answer.charAt(i)));
            }
        }
        return abc;
    }

    private static String deleteCharAt(String strValue, int index) {
        return strValue.substring(0, index) + strValue.substring(index + 1);
    }

}
