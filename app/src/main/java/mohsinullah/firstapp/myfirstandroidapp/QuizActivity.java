package mohsinullah.firstapp.myfirstandroidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true, false),
            new Question(R.string.question_oceans, true, false),
            new Question(R.string.question_mideast, false, false),
            new Question(R.string.question_africa, false, false),
            new Question(R.string.question_americas, true, false),
            new Question(R.string.question_asia, true, false),
    };

    private int mCurrentIndex = 0;
    private int mScore = 0;
    private int mNumberAnswered = 0;

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
        }
        mQuestionTextView =  findViewById(R.id.question_text_view);
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton =  findViewById(R.id.false_button);
        updateQuestion();

        Log.d(TAG,"Question answered is: " + mQuestionBank[mCurrentIndex].isAnswered());


        if (mTrueButton.isEnabled()) {
            mTrueButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    checkAnswer(true);
                    disableButtons();
                }
            });
            mFalseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAnswer(false);
                    disableButtons();
                }
            });
        }



        mNextButton =  findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();

            }
        });

        mQuestionTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevButton =  findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if (mCurrentIndex == 0){
                    mCurrentIndex = mQuestionBank.length - 1;
                } else {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                }
                updateQuestion();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }

    @Override
    public void onSaveInstanceState (Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX,mCurrentIndex);
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        Log.d(TAG,"Question " + mCurrentIndex + " answered: " + mQuestionBank[mCurrentIndex].isAnswered());
        disableButtons();
    }

    private void disableButtons() {
        mTrueButton.setEnabled(!(mQuestionBank[mCurrentIndex].isAnswered()));
        mFalseButton.setEnabled(!(mQuestionBank[mCurrentIndex].isAnswered()));
    }

    private void checkAnswer(boolean userPressedTrue) {
        mQuestionBank[mCurrentIndex].setAnswered(true);
        mNumberAnswered += 1;
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId;

        if (userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
            mScore += 1;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();

        if (mNumberAnswered == mQuestionBank.length) {
            Toast.makeText(this,
                    "You answered " + (int)(((float)mScore/mQuestionBank.length)*100) + "% correct",
                    Toast.LENGTH_LONG).show();
        }
    }


}
