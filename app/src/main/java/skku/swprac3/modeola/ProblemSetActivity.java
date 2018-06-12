package skku.swprac3.modeola;

import android.app.Activity;
import android.os.Bundle;
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

}
