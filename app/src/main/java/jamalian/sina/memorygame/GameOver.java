package jamalian.sina.memorygame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GameOver extends Activity {

    // The current level.
    int level;

    // The total score.
    int score;

    // The round score, time bonus, and tries bonus.
    int roundScore, timeBonus, triesBonus, uniqueCards, win, hsUpdated;

    float scale = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_over);

        // Get device details.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels/density;
        float dpWidth = outMetrics.widthPixels/density;

        // A height of 640.0f is used as the baseline.
        scale = dpHeight/640.0f;

        // Update the level and score.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            level = extras.getInt("level");
            score = extras.getInt("score");
            roundScore = extras.getInt("roundScore");
            timeBonus = extras.getInt("timeBonus");
            triesBonus = extras.getInt("triesBonus");
            uniqueCards = extras.getInt("uniqueCards");
            win = extras.getInt("win");
            hsUpdated = extras.getInt("hsUpdated");
        }

        RelativeLayout layout = (RelativeLayout)findViewById(R.id.rel);

        if (level == 1)
            layout.setBackgroundResource(R.drawable.bg1);
        else if (level == 2)
            layout.setBackgroundResource(R.drawable.bg2);
        else if (level == 3)
            layout.setBackgroundResource(R.drawable.bg3);
        else if (level == 4)
            layout.setBackgroundResource(R.drawable.bg4);
        else if (level == 5)
            layout.setBackgroundResource(R.drawable.bg5);
        else if (level == 6)
            layout.setBackgroundResource(R.drawable.bg6);
        else if (level == 7)
            layout.setBackgroundResource(R.drawable.bg7);

        TextView hstv = (TextView) findViewById(R.id.highscore);
        if (hsUpdated == 1) {
            hstv.setText("NEW HIGH SCORE!");
            hstv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32.0f * scale);
        }
        else {
            hstv.setVisibility(View.GONE);
        }

        TextView gotv = (TextView) findViewById(R.id.gameover);
        TextView leveltv = (TextView) findViewById(R.id.level);
        TextView scoretv = (TextView) findViewById(R.id.score);
        TextView roundscoretv = (TextView) findViewById(R.id.roundscore);
        TextView timebonustv = (TextView) findViewById(R.id.timebonus);
        TextView triesbonustv = (TextView) findViewById(R.id.triesbonus);
        TextView levelscoretv = (TextView) findViewById(R.id.levelscore);

        if (win == 1)
            gotv.setText("YOU WIN!");
        else
            gotv.setText("YOU LOSE!");
        gotv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38.0f * scale);

        leveltv.setText("Levels Completed: " + Integer.toString(level - 1 + win));
        leveltv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);
        scoretv.setText("Final Score: " + Integer.toString(score));
        scoretv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32.0f * scale);
        roundscoretv.setText("Round Score: " + Integer.toString(roundScore));
        roundscoretv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);
        timebonustv.setText("Time Bonus: " + Integer.toString(timeBonus));
        timebonustv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);
        triesbonustv.setText("Tries Bonus: " + Integer.toString(triesBonus));
        triesbonustv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);
        int levelScore = roundScore+timeBonus+triesBonus;
        levelscoretv.setText("Level Score: " + Integer.toString(levelScore));
        levelscoretv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);

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

    // Starts a new game.
    public void newGame(View view) {
        Intent intent = new Intent(this, StartGameActivity.class);
        intent.putExtra("level", 1);
        intent.putExtra("score", 0);
        intent.putExtra("uniqueCards", uniqueCards);
        startActivity(intent);
        finish();
    }

    // Quits to the main menu.
    public void mainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
