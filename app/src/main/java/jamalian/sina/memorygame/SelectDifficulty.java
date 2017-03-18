package jamalian.sina.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

public class SelectDifficulty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_select_difficulty);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void startEasy(View view) {
        // Start the game with an intent to a new activity.
        Intent intent = new Intent(this, StartGameActivity.class);
        intent.putExtra("level", 1);
        intent.putExtra("score", 0);
        // Number of unique cards.
        intent.putExtra("uniqueCards", 4);
        startActivity(intent);
        finish();
    }

    public void startMedium(View view) {
        // Start the game with an intent to a new activity.
        Intent intent = new Intent(this, StartGameActivity.class);
        intent.putExtra("level", 1);
        intent.putExtra("score", 0);
        intent.putExtra("uniqueCards", 6);
        startActivity(intent);
        finish();
    }

    public void startHard(View view) {
        // Start the game with an intent to a new activity.
        Intent intent = new Intent(this, StartGameActivity.class);
        intent.putExtra("level", 1);
        intent.putExtra("score", 0);
        intent.putExtra("uniqueCards", 8);
        startActivity(intent);
        finish();
    }
}
