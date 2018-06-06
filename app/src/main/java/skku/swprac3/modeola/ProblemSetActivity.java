package skku.swprac3.modeola;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ProblemSetActivity extends Activity {

    List listSchedule = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_prob);

        ListView listView;
        final ListViewAdapter adapter = new ListViewAdapter();

        Button buttonProb1 = (Button) findViewById(R.id.submitButton);

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        buttonProb1.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                String msg = "";
                for(int i = 0; i < adapter.selectedAnswer.size(); i++){
                    msg = msg + "\n" + (i + 1) + " " + adapter.selectedAnswer.get(i);
                }
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });

        //////////
        String probName = "Prob1";
        String[] str = new String[5];
        for(int i = 0; i < 5; i++){
            str[i] = "sample";
        }

        adapter.addProblem(probName, str);
        adapter.addProblem("Prob2", str);
        adapter.addProblem("Prob3", str);
        adapter.addProblem("Prob4", str);
        adapter.addProblem("Prob5", str);
        //////////

    }

    class ProblemItem {
        private int year;
        private int month;
        private int day;
        private int hour;
        private String where;
        private String who;
        private String what;
        private int weight;
        private boolean isAnswer;

        public ProblemItem() { }

        public ProblemItem(Schedule schedule, boolean isAnswer){
            this.year       = schedule.getYear();
            this.month      = schedule.getMonth();
            this.day        = schedule.getDay();
            this.hour       = schedule.getHour();
            this.where      = schedule.getWhere();
            this.who        = schedule.getWho();
            this.what       = schedule.getWhat();
            this.weight     = schedule.getWeight();
            this.isAnswer   = isAnswer;
        }

        /*
        This code is for local test, so maybe it's not used in product.

        public void setTuple(int year, int month, int day, String where, String who, String what, int weight, boolean isAnswer){
            this.year = year;
            this.month = month;
            this.day = day;
            this.where = where;
            this.who = who;
            this.what = what;
            this.weight = weight;
            this.isAnswer = isAnswer;
        }
        */

        public String getSentense(){
            return ("When " +
                    Integer.toString(year)  + "/" +
                    Integer.toString(month) + "/" +
                    Integer.toString(day)   + ", I will " +
                    what + " with " + who + " at " + where);
        }

        public int getYear(){
            return this.year;
        }

        public int getMonth(){
            return this.month;
        }

        public int getDay(){
            return this.day;
        }

        public String getWhere(){
            return this.where;
        }
        public String getWho(){
            return this.who;
        }
        public String getWhat(){
            return this.what;
        }
        public int getWeight(){
            return this.weight;
        }
        public boolean isAnswer(){
            return this.isAnswer;
        }

    }

}
