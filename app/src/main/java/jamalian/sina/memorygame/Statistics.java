package jamalian.sina.memorygame;

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
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Statistics extends AppCompatActivity {

    float scale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_statistics);

        // Get device details.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels/density;
        float dpWidth = outMetrics.widthPixels/density;

        // A height of 640.0f is used as the baseline.
        scale = dpHeight/640.0f;

        // Show updated statistics.
        SharedPreferences savedStats = this.getSharedPreferences("prefsKeyAMG", Context.MODE_PRIVATE);

        TextView statstv = (TextView) findViewById(R.id.stats);
        TextView easytv = (TextView) findViewById(R.id.easy);
        TextView mediumtv = (TextView) findViewById(R.id.medium);
        TextView hardtv = (TextView) findViewById(R.id.hard);
        statstv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 38.0f * scale);
        easytv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32.0f * scale);
        mediumtv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32.0f * scale);
        hardtv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32.0f * scale);

        TextView easygptv = (TextView) findViewById(R.id.easygp);
        TextView easyhstv = (TextView) findViewById(R.id.easyhs);
        TextView mediumgptv = (TextView) findViewById(R.id.mediumgp);
        TextView mediumhstv = (TextView) findViewById(R.id.mediumhs);
        TextView hardgptv = (TextView) findViewById(R.id.hardgp);
        TextView hardhstv = (TextView) findViewById(R.id.hardhs);

        int easygp = savedStats.getInt("easyPlayed", 0);
        int easyhs = savedStats.getInt("easyHighScore", 0);
        int mediumgp = savedStats.getInt("mediumPlayed", 0);
        int mediumhs = savedStats.getInt("mediumHighScore", 0);
        int hardgp = savedStats.getInt("hardPlayed", 0);
        int hardhs = savedStats.getInt("hardHighScore", 0);

        easygptv.setText("Games Played: " + Integer.toString(easygp));
        easygptv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);
        easyhstv.setText("High Score: " + Integer.toString(easyhs));
        easyhstv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);

        mediumgptv.setText("Games Played: " + Integer.toString(mediumgp));
        mediumgptv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);
        mediumhstv.setText("High Score: " + Integer.toString(mediumhs));
        mediumhstv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);

        hardgptv.setText("Games Played: " + Integer.toString(hardgp));
        hardgptv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);
        hardhstv.setText("High Score: " + Integer.toString(hardhs));
        hardhstv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);

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

    // Resets the stats.
    public void resetStats(View view) {
        final SharedPreferences savedStats = this.getSharedPreferences("prefsKeyAMG", Context.MODE_PRIVATE);
        final Intent intent = new Intent(this, Statistics.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to reset your statistics?")
                .setPositiveButton("No", null)
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = savedStats.edit();
                        editor.putInt("easyPlayed", 0);
                        editor.putInt("easyHighScore", 0);
                        editor.putInt("mediumPlayed", 0);
                        editor.putInt("mediumHighScore", 0);
                        editor.putInt("hardPlayed", 0);
                        editor.putInt("hardHighScore", 0);
                        editor.commit();

                        startActivity(intent);
                        finish();
                    }
                        })
                .show();

        //AlertDialog dialog = builder.create();
        //dialog.show();
        //TextView dtv = (TextView)dialog.findViewById(android.R.id.message);
        //dtv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24.0f * scale);
    }
}
