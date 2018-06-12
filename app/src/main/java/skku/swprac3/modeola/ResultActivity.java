package skku.swprac3.modeola;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        ArrayList<String> contents = intent.getStringArrayListExtra("contents");
        ArrayList<String> contentsOriginal = intent.getStringArrayListExtra("contentsOriginal");
        ArrayList<Integer> answers = intent.getIntegerArrayListExtra("answers");
        ArrayList<Integer> types = intent.getIntegerArrayListExtra("types");
        ArrayList<Integer> selects = intent.getIntegerArrayListExtra("selects");

        int correctCnt = 0;
        //String msg = "";
        for(int i = 0; i < selects.size(); i++){
            //msg = msg + "\n" + (i + 1) + " " + adapter.selectedAnswer.get(i);
            if(answers.get(i) == selects.get(i)){
                correctCnt++;
            }
        }
        //msg = msg + "\n맞춘 문제 수: " + correctCnt;
        //Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();

        TextView score = (TextView) findViewById(R.id.score);
        score.setText(String.format("%d점!", correctCnt*20));

        TextView OX_question_1 = (TextView) findViewById(R.id.OX_question_1);
        TextView OX_question_2 = (TextView) findViewById(R.id.OX_question_2);
        TextView OX_question_3 = (TextView) findViewById(R.id.OX_question_3);
        TextView OX_user_answer_1 = (TextView) findViewById(R.id.OX_user_answer_1);
        TextView OX_user_answer_2 = (TextView) findViewById(R.id.OX_user_answer_2);
        TextView OX_user_answer_3 = (TextView) findViewById(R.id.OX_user_answer_3);

        OX_question_1.setText(contents.get(0));
        OX_question_2.setText(contents.get(2));
        OX_question_3.setText(contents.get(4));
        OX_user_answer_1.setText(("제출 답안: " + (selects.get(0) == 1 ? 'O' : 'X') +  "\t실제 답안: " +
                                (selects.get(0) == 1 ? 'O' : 'X') + "\t" + (answers.get(0) == selects.get(0) ? "*맞음!*" : "*틀림ㅠ*")));
        OX_user_answer_2.setText(("제출 답안: " + (selects.get(2) == 1 ? 'O' : 'X') +  "\t실제 답안: " +
                                (selects.get(2) == 1 ? 'O' : 'X') + "\t" + (answers.get(2) == selects.get(2) ? "*맞음!*" : "*틀림ㅠ*")));
        OX_user_answer_3.setText(("제출 답안: " + (selects.get(4) == 1 ? 'O' : 'X') +  "\t실제 답안: " +
                                (selects.get(4) == 1 ? 'O' : 'X') + "\t" + (answers.get(4) == selects.get(4) ? "*맞음!*" : "*틀림ㅠ*")));

    }
}
