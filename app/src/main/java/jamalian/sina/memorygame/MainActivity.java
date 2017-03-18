package jamalian.sina.memorygame;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.Window;
import android.view.WindowManager;

//public class MainActivity extends AppCompatActivity {
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        //final float scale = getResources().getDisplayMetrics().density;
        //System.out.println("SCALE: " + scale);

        // Get device details.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels/density;
        float dpWidth = outMetrics.widthPixels/density;

        //System.out.println("DENSITY: " + density);
        //System.out.println("DPHEIGHT: " + dpHeight);
        //System.out.println("DPWIDTH: " + dpWidth);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectDifficulty(View view) {
        // Start the game with an intent to a new activity.
        Intent intent = new Intent(this, SelectDifficulty.class);
        startActivity(intent);
    }

    public void showStats(View view) {
        // Start the game with an intent to a new activity.
        Intent intent = new Intent(this, Statistics.class);
        startActivity(intent);
    }

    /*public void startGame(View view) {
        // Start the game with an intent to a new activity.
        Intent intent = new Intent(this, StartGameActivity.class);
        intent.putExtra("level", 1);
        intent.putExtra("score", 0);
        startActivity(intent);
    }*/
}
