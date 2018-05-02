package skku.swprac3.modeola;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class AddTodoActivity extends Activity {

    // Views
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        // ToolBar
        toolbar = (Toolbar)findViewById(R.id.addTodoToolbar);
        toolbar.setTitle("Add Todo");
    }
}
