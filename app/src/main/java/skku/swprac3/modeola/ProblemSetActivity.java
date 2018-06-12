package skku.swprac3.modeola;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProblemSetActivity extends Activity {
    private static final int ITEM_VIEW_TYPE_MULTI_CHOICE = 0;
    private static final int ITEM_VIEW_TYPE_OX_CHOICE = 1;
    private List answerList = new ArrayList();

    private TimerTask second;
    int timer_sec,count;
    private TextView timer_text;
    private final Handler handler = new Handler();

    Button buttonProb1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_prob);

        final ListViewAdapter adapter = new ListViewAdapter();
        buttonProb1 = (Button) findViewById(R.id.submitButton);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        myDBHelper dbHelper = new myDBHelper(getApplicationContext());
        final Problems problems = dbHelper.getProblems(3, 2);

        for(int i = 0; i < 5; i++) {
            answerList.add(problems.getProblem(i).getAnswer()+1);
        }

        adapter.addProblem("OX 문제 1번)\n다음 일정의 옳고 그름을 판단하시오.", problems.getProblem(0).getContents(), ITEM_VIEW_TYPE_OX_CHOICE);
        adapter.addProblem("OX 문제 2번)\n다음 일정의 옳고 그름을 판단하시오.", problems.getProblem(1).getContents(), ITEM_VIEW_TYPE_OX_CHOICE);
        adapter.addProblem("OX 문제 3번)\n다음 일정의 옳고 그름을 판단하시오.", problems.getProblem(2).getContents(), ITEM_VIEW_TYPE_OX_CHOICE);
        adapter.addProblem("5지선다 문제 1번)\n다음 일정 중 올바른 일정을 고르시오.", problems.getProblem(3).getContents(), ITEM_VIEW_TYPE_MULTI_CHOICE);
        adapter.addProblem("5지선다 문제 2번)\n다음 일정 중 올바른 일정을 고르시오.", problems.getProblem(4).getContents(), ITEM_VIEW_TYPE_MULTI_CHOICE);


        buttonProb1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                // Build Extras
                ArrayList<String> contents = new ArrayList<>();
                ArrayList<String> contentsOriginal = new ArrayList<>();
                ArrayList<Integer> answers = (ArrayList<Integer>) answerList;
                ArrayList<Integer> types = new ArrayList<>();
                ArrayList<Integer> selects = adapter.selectedAnswer;
                for(int i = 0; i < 5; i++) {
                    for(String content : problems.getProblem(i).getContents()) {
                        contents.add(content);
                    }
                    for(String contentOriginal : problems.getProblem(i).getContentsOriginal()) {
                        contentsOriginal.add(contentOriginal);
                    }
                    types.add(problems.getProblem(i).getType());
                }

                // Intent
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putStringArrayListExtra("contents", contents);
                intent.putStringArrayListExtra("contentsOriginal", contentsOriginal);
                intent.putIntegerArrayListExtra("answers", answers);
                intent.putIntegerArrayListExtra("types", types);
                intent.putIntegerArrayListExtra("selects", selects);
                startActivity(intent);
                finish();
            }
        });

        timerStart();
    }

    public void timerStart() {
        timer_text = (TextView) findViewById(R.id.timer);
        timer_sec = 60;
        count = 0;
        Log.i("Test", "timerStart()");

        second = new TimerTask() {
            @Override
            public void run() {
                Log.i("Test", "TimerTask run : timer_sec = " + timer_sec);
                Update();
                timer_sec--;
                if(timer_sec == 0) {
                    buttonProb1.callOnClick();
                    finish();
                    second.cancel();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(second, 0, 1000);
    }

    protected void Update() {
        Runnable updater = new Runnable() {
            public void run() {
                if(timer_sec >= 0)
                    timer_text.setText(timer_sec + "초");
            }
        };
        handler.post(updater);
    }
}