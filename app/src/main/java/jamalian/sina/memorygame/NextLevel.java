package jamalian.sina.memorygame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class NextLevel extends Activity {

    // The current level.
    int level;

    // The total score.
    int score;

    // The round score, time bonus, and tries bonus.
    int roundScore, timeBonus, triesBonus, uniqueCards;

    float scale = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_next_level);

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

        TextView leveltv = (TextView) findViewById(R.id.level);
        TextView scoretv = (TextView) findViewById(R.id.score);
        TextView roundscoretv = (TextView) findViewById(R.id.roundscore);
        TextView timebonustv = (TextView) findViewById(R.id.timebonus);
        TextView triesbonustv = (TextView) findViewById(R.id.triesbonus);
        TextView levelscoretv = (TextView) findViewById(R.id.levelscore);
        leveltv.setText("Level: " + Integer.toString(level));
        leveltv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38.0f * scale);
        scoretv.setText("Total Score: " + Integer.toString(score));
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
        final Intent intent = new Intent(this, StartGameActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to start a new game?")
                .setPositiveButton("No", null)
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        intent.putExtra("level", 1);
                        intent.putExtra("score", 0);
                        intent.putExtra("uniqueCards", uniqueCards);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

    // Starts the next level.
    public void nextLevel(View view) {
        level++;
        Intent intent = new Intent(this, StartGameActivity.class);
        intent.putExtra("level", level);
        intent.putExtra("score", score);
        intent.putExtra("uniqueCards", uniqueCards);
        startActivity(intent);
        finish();
    }

    // Quits to the main menu.
    public void mainMenu(View view) {
        final Intent intent = new Intent(this, MainActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to quit?")
                .setPositiveButton("No", null)
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }
}
