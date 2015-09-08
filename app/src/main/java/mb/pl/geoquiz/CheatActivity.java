package mb.pl.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

    public static final String ANSWER = "pl.mb.geoquiz.answer";
    public static final String ANSWER_SHOWN = "pl.mb.geoquiz.answerShown";

    private final String CHEAT = "Cheat";

    private TextView answerText;
    private Button showAnswer;

    private boolean answerShown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        final boolean answer = getIntent().getBooleanExtra(ANSWER, false);
        answerText = (TextView) findViewById(R.id.answerTextView);

        showAnswer = (Button) findViewById(R.id.showAnswerButton);
        showAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerShown = true;
                setAnswerShownResult();
                if (answer) {
                    answerText.setText(R.string.true_button);
                } else {
                    answerText.setText(R.string.false_button);
                }
            }
        });

        if (savedInstanceState != null) {
            answerShown = savedInstanceState.getBoolean(CHEAT);
        }
        setAnswerShownResult();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(CHEAT, answerShown);
    }

    private void setAnswerShownResult(){
        Intent data = new Intent();
        data.putExtra(ANSWER_SHOWN, answerShown);
        setResult(RESULT_OK, data);
    }
}
