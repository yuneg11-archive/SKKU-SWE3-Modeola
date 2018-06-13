package skku.swprac3.modeola;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        String OX_msg_1;
        if(selects.get(0) == 1) OX_msg_1 = "선택: O";
        else if(selects.get(0) == 2) OX_msg_1 = "선택: X";
        else OX_msg_1 = "선택: _";
        OX_msg_1 += "      답: " + (answers.get(0) == 1 ? 'O' : 'X') + "        " + (answers.get(0) == selects.get(0) ? "*맞음!*" : "*틀림ㅠ*");
        OX_user_answer_1.setText(OX_msg_1);

        String OX_msg_2;
        if(selects.get(1) == 1) OX_msg_2 = "선택: O";
        else if(selects.get(1) == 2) OX_msg_2 = "선택: X";
        else OX_msg_2 = "선택: _";
        OX_msg_2 += "      답: " + (answers.get(1) == 1 ? 'O' : 'X') + "        " + (answers.get(1) == selects.get(1) ? "*맞음!*" : "*틀림ㅠ*");
        OX_user_answer_2.setText(OX_msg_2);

        String OX_msg_3;
        if(selects.get(2) == 1) OX_msg_3 = "선택: O";
        else if(selects.get(2) == 2) OX_msg_3 = "선택: X";
        else OX_msg_3 = "선택: _";
        OX_msg_3 += "      답: " + (answers.get(2) == 1 ? 'O' : 'X') + "        " + (answers.get(2) == selects.get(2) ? "*맞음!*" : "*틀림ㅠ*");
        OX_user_answer_3.setText(OX_msg_3);

        TextView Multi_question_1_content_1 = (TextView) findViewById(R.id.Multiple_question_1_content_1);
        TextView Multi_question_1_content_2 = (TextView) findViewById(R.id.Multiple_question_1_content_2);
        TextView Multi_question_1_content_3 = (TextView) findViewById(R.id.Multiple_question_1_content_3);
        TextView Multi_question_1_content_4 = (TextView) findViewById(R.id.Multiple_question_1_content_4);
        TextView Multi_question_1_content_5 = (TextView) findViewById(R.id.Multiple_question_1_content_5);
        TextView Multi_question_2_content_1 = (TextView) findViewById(R.id.Multiple_question_2_content_1);
        TextView Multi_question_2_content_2 = (TextView) findViewById(R.id.Multiple_question_2_content_2);
        TextView Multi_question_2_content_3 = (TextView) findViewById(R.id.Multiple_question_2_content_3);
        TextView Multi_question_2_content_4 = (TextView) findViewById(R.id.Multiple_question_2_content_4);
        TextView Multi_question_2_content_5 = (TextView) findViewById(R.id.Multiple_question_2_content_5);
        TextView Multi_user_answer_1 = (TextView) findViewById(R.id.Multiple_user_answer_1);
        TextView Multi_user_answer_2 = (TextView) findViewById(R.id.Multiple_user_answer_2);

        Multi_question_1_content_1.setText("1. " + contents.get(6));
        Multi_question_1_content_2.setText("2. " + contents.get(7));
        Multi_question_1_content_3.setText("3. " + contents.get(8));
        Multi_question_1_content_4.setText("4. " + contents.get(9));
        Multi_question_1_content_5.setText("5. " + contents.get(10));

        Multi_question_2_content_1.setText("1. " + contents.get(11));
        Multi_question_2_content_2.setText("2. " + contents.get(12));
        Multi_question_2_content_3.setText("3. " + contents.get(13));
        Multi_question_2_content_4.setText("4. " + contents.get(14));
        Multi_question_2_content_5.setText("5. " + contents.get(15));

        String Multi_msg_1 = "선택: " + selects.get(3).toString() + "      답: " + answers.get(3).toString() + "        " + (answers.get(3) == selects.get(3) ? "*맞음!*" : "*틀림ㅠ*");
        Multi_user_answer_1.setText(Multi_msg_1);

        String Multi_msg_2 = "선택: " + selects.get(4).toString() + "      답: " + answers.get(4).toString() + "        " + (answers.get(4) == selects.get(4) ? "*맞음!*" : "*틀림ㅠ*");
        Multi_user_answer_2.setText(Multi_msg_2);

        Button button = (Button) findViewById(R.id.result_button_ok);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
