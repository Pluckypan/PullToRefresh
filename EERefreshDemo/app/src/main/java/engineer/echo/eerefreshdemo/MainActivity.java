package engineer.echo.eerefreshdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.xutils.view.annotation.Event;
import org.xutils.x;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);
    }

    @Event(value = {R.id.tvRecyclerView})
    private void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvRecyclerView:
                Intent intent = new Intent(this, RecyclerViewActivity.class);
                startActivity(intent);
                break;
        }
    }
}
