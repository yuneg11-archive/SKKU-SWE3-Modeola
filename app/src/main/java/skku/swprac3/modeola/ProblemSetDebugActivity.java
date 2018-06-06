package skku.swprac3.modeola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class ProblemSetDebugActivity extends AppCompatActivity {

    myDBHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_problem_set_debug);

        dbHelper = new myDBHelper(this);


        Button buttonProb1 = (Button) findViewById(R.id.buttonProblem1);

        buttonProb1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProblemSetActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}


