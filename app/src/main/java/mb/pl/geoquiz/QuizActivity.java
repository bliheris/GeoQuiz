package mb.pl.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    private static final String TAG = QuizActivity.class.getSimpleName();
    private static final String INDEX = "index";
    private static final String CHEATER = "cheater";

    private Button trueButton;
    private Button falseButton;
    private Button prevButton;
    private Button nextButton;
    private Button cheatButton;
    private TextView questionText;

    private Question[] questionsBank = new Question[]{
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };
    private int index = 0;
    private boolean isCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        questionText = (TextView) findViewById(R.id.question_text);
        questionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextQuestion();
            }
        });

        trueButton = (Button) findViewById(R.id.trueButton);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        falseButton = (Button) findViewById(R.id.falseButton);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        prevButton = (Button) findViewById(R.id.previousButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPreviousQuestion();
            }
        });

        nextButton = (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNextQuestion();
            }
        });

        cheatButton = (Button) findViewById(R.id.cheatButton);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QuizActivity.this, CheatActivity.class);
                boolean answer = questionsBank[index].getAnswer();
                intent.putExtra(CheatActivity.ANSWER, answer);
                startActivityForResult(intent, 0);

            }
        });

        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(INDEX);
            isCheater = savedInstanceState.getBoolean(CHEATER);
        }

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        isCheater = data.getBooleanExtra(CheatActivity.ANSWER_SHOWN, false);
    }

    private void loadPreviousQuestion() {
        index--;
        if(index < 0) index = questionsBank.length - 1;
        updateQuestion();
    }

    private void loadNextQuestion() {
        index = (index + 1) % questionsBank.length;
        isCheater = false;
        updateQuestion();
    }

    private void checkAnswer(boolean userPressed) {
        boolean answer = questionsBank[index].getAnswer();
        int messageId = 0;
        if(isCheater) {
            messageId = R.string.judgment;
        }else{
          messageId = (userPressed == answer) ?
                  R.string.correct : R.string.incorrect;
        }
        showToast(messageId);

    }

    private void updateQuestion() {
        questionText.setText(questionsBank[index].getQuestionId());
    }

    private void showToast(int stringId) {
        Toast.makeText(QuizActivity.this, stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState(Bundle) called");
        outState.putInt(INDEX, index);
        outState.putBoolean(CHEATER, isCheater);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
