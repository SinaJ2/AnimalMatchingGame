package jamalian.sina.memorygame;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import java.util.logging.Handler;

//public class StartGameActivity extends AppCompatActivity {
public class StartGameActivity extends Activity {

    // Number of unique cards.
    int uniqueCards;

    // The number of rows and columns.
    //public static final int SIZE = 4;

    // The maximum card value that can be generated.
    //public static final int MAX_CARD = SIZE*SIZE/2;

    // Represents the cards placed in random order.
    //int[][] cards = new int[SIZE][SIZE];
    int[][] cards;

    int gameRows, gameCols;

    // Keeps track of how many of each card is generated.
    // 2 of each card should be generated.
    //int[] cardCount = new int[MAX_CARD];
    int[] cardCount;

    // Number of cards flipped.
    int cardsFlipped = 0;

    // Keeps track of previous flipped card.
    //int[] prevCard = new int[2];
    int prevRow = -1;
    int prevCol = -1;
    int prevCardId = -1;

    // Keep track of matches and tries made.
    int matchesMade = 0;
    int noOfTries = 0;

    // The current level.
    int level;

    // The total score.
    int score;

    // The round score.
    int roundScore = 0;

    // Bonuses.
    //int timeBonus = 0;
    int timeBonus;
    //int triesBonus = SIZE*SIZE;
    int triesBonus;

    // Round time.
    int roundTime;

    // Seconds remaining.
    int timeLeft;

    // The time limit per level.
    CountDownTimer timeLimit;

    // Animal sounds.
    SoundPool sp;
    //int soundIds[] = new int[MAX_CARD];
    int soundIds[];

    // 1 if player won, 0 if player lost.
    int win = 0;

    // 1 if high score updated, 0 if not.
    int hsUpdated = 0;

    // Font size scale factor.
    float scale = 1.0f;

    // Keeps track of whether timer is paused.
    int paused = 0;

    // Keeps track of timer time left.
    long millisLeft = 0;

    // Keep track of all cards.
    /*ImageButton card0 = (ImageButton)findViewById(R.id.card0);
    ImageButton card1 = (ImageButton)findViewById(R.id.card1);
    ImageButton card2 = (ImageButton)findViewById(R.id.card2);
    ImageButton card3 = (ImageButton)findViewById(R.id.card3);
    ImageButton card4 = (ImageButton)findViewById(R.id.card4);
    ImageButton card5 = (ImageButton)findViewById(R.id.card5);
    ImageButton card6 = (ImageButton)findViewById(R.id.card6);
    ImageButton card7 = (ImageButton)findViewById(R.id.card7);
    ImageButton card8 = (ImageButton)findViewById(R.id.card8);
    ImageButton card9 = (ImageButton)findViewById(R.id.card9);
    ImageButton card10 = (ImageButton)findViewById(R.id.card10);
    ImageButton card11 = (ImageButton)findViewById(R.id.card11);
    ImageButton card12 = (ImageButton)findViewById(R.id.card12);
    ImageButton card13 = (ImageButton)findViewById(R.id.card13);
    ImageButton card14 = (ImageButton)findViewById(R.id.card14);
    ImageButton card15 = (ImageButton)findViewById(R.id.card15);*/
    //ImageButton[] cardButtons = new ImageButton[MAX_CARD*2];
    ImageButton[] cardButtons;

    // For images.
    LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

///        ActionBar actionBar = getActionBar();
//        actionBar.hide();

        setContentView(R.layout.activity_start_game);

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
        // Get unique card count.
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            level = extras.getInt("level");
            score = extras.getInt("score");
            uniqueCards = extras.getInt("uniqueCards");
        }

        // Update number of games played.
        if (level == 1) {
            SharedPreferences gamesPlayed = this.getSharedPreferences("prefsKeyAMG", Context.MODE_PRIVATE);
            int timesPlayed;
            SharedPreferences.Editor editor = gamesPlayed.edit();

            if (uniqueCards == 4) {
                timesPlayed = gamesPlayed.getInt("easyPlayed", 0);
                timesPlayed++;
                editor.putInt("easyPlayed", timesPlayed);
            } else if (uniqueCards == 6) {
                timesPlayed = gamesPlayed.getInt("mediumPlayed", 0);
                timesPlayed++;
                editor.putInt("mediumPlayed", timesPlayed);
            } else {
                timesPlayed = gamesPlayed.getInt("hardPlayed", 0);
                timesPlayed++;
                editor.putInt("hardPlayed", timesPlayed);
            }
            editor.commit();
        }

        // Changes background based on level.
        //String bgnum = "R.drawable.bg" + Integer.toString(level);
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

        //layout.setBackgroundResource(R.drawable.ready);
        //layout.setBackground(getResources().getDrawable(R.drawable.bg1));
        //layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg1)); (For API <16)
        //this.findViewById(android.R.id.content).setBackgroundResource(R.drawable.bg1);

        // Initialize variables based on difficulty.
        gameRows = uniqueCards/2;
        gameCols = 4;
        cards = new int[gameRows][gameCols];
        cardCount = new int[uniqueCards];
        triesBonus = uniqueCards*2;
        timeBonus = level*300;
        //soundIds = new int[uniqueCards];
        soundIds = new int[8];
        cardButtons = new ImageButton[uniqueCards*2];

        // Set card counts to 0.
        for (int i = 0; i < uniqueCards; i++) {
            cardCount[i] = 0;
        }

        // Populate cards array with random numbers.
        for (int i = 0; i < gameRows; i++) {
            for (int j = 0; j < gameCols; j++) {
                // Generate a random card.
                int card = (int) (Math.random() * uniqueCards);
                //System.out.println("CARD: " + card);
                while (cardCount[card] >= 2) {
                    card = (int) (Math.random() * uniqueCards);
                    //System.out.println("CARD2: " + card);
                }

                cards[i][j] = card;
                cardCount[card]++;
                //System.out.println("CARD COUNT: " + cardCount[card]);
            }
        }

        // Store card buttons.
        for (int i = 0; i < uniqueCards*2; i++) {
            String cid = "card" + Integer.toString(i);
            int resID = getResources().getIdentifier(cid, "id", "jamalian.sina.memorygame");
            //cardButtons[i] = (ImageButton)findViewById(R.id.cid);
            cardButtons[i] = (ImageButton)findViewById(resID);
            //System.out.println(cid + " " + resID + " " + cardButtons[i]);
        }

        // Hide unused buttons.
        for (int i = uniqueCards*2; i < 16; i++) {
            String cid = "card" + Integer.toString(i);
            int resID = getResources().getIdentifier(cid, "id", "jamalian.sina.memorygame");
            ImageButton btn = (ImageButton)findViewById(resID);
            //btn.setVisibility(View.INVISIBILE);
            btn.setVisibility(View.GONE);
        }

        // Display the level and initial score.
        TextView leveltv = (TextView) findViewById(R.id.level);
        //TextView scoretv = (TextView) findViewById(R.id.score);
        leveltv.setText("Level: " + level);
        //leveltv.setTextSize(24.0f*scale);
        leveltv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28.0f*scale);
        //scoretv.setText("Score: "+roundScore);

        // Display the initial number of tries.
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("Tries: " + noOfTries);
        tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28.0f * scale);

        // Round time - 7 levels starting with 2:00, decreasing by 0:15 per level.
        roundTime = 136000 - (15000*level);
        millisLeft = roundTime;
        //roundTime = 20000;

        // Sets the time limit for the game.
        /*timeLimit = new CountDownTimer(roundTime, 1000) {
            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
                TextView timelimit = (TextView) findViewById(R.id.timelimit);
                int totalSeconds = (int)millisUntilFinished/1000;
                timeLeft = totalSeconds;
                int mins = totalSeconds/60;
                int secs = totalSeconds - (mins*60);

                if(secs < 10)
                    timelimit.setText("Time Remaining: "+mins+":0"+secs);
                else
                    timelimit.setText("Time Remaining: "+mins+":"+secs);
                timelimit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28.0f*scale);
            }
            public void onFinish() {
                TextView timelimit = (TextView) findViewById(R.id.timelimit);
                timelimit.setText("Time Remaining: 0:00");
                timelimit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28.0f * scale);
                timeBonus = 0;
                triesBonus = 0;
                win = 0;
                gameOver();
            }
        }.start();*/

        // Note: For API 21 and newer.
        /*AudioAttributes attrs = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        SoundPool sp = new SoundPool.Builder()
                .setMaxStreams(10)
                .setAudioAttributes(attrs)
                .build();*/

        sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

        // Let the user change volume of sound stream.
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // Load sounds.
        // this - the context
        soundIds[0] = sp.load(this, R.raw.sound0, 1);
        soundIds[1] = sp.load(this, R.raw.sound1, 1);
        soundIds[2] = sp.load(this, R.raw.sound2, 1);
        soundIds[3] = sp.load(this, R.raw.sound3, 1);
        soundIds[4] = sp.load(this, R.raw.sound4, 1);
        soundIds[5] = sp.load(this, R.raw.sound5, 1);
        soundIds[6] = sp.load(this, R.raw.sound6, 1);
        soundIds[7] = sp.load(this, R.raw.sound7, 1);

        // Display cards upside down.
        //setContentView(new CustomView(this));

        // Create bitmap and canvas to draw to.
        //Bitmap b = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        //Canvas c = new Canvas(b);

        // Draw image of card onto canvas.
        //Drawable d = ContextCompat.getDrawable(this, R.drawable.backcard);
        //d.setBounds(left, top, right, bottom);
        //d.setBounds(0, 0, 50, 50);
        //d.draw(c);

        // Set ImageView and layout.
        //ImageView imageView = new ImageView(this);
        //imageView.setImageBitmap(b);
        //RelativeLayout layout = new RelativeLayout(this);
        //RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT);
        //layout

        // Create a LinearLayout in which to add the ImageView.
        //mLinearLayout = new LinearLayout(this);

        // Instantiate an ImageView and define its properties.
        //ImageView i = new ImageView(this);
        //i.setImageResource(R.drawable.backcard);
        // Set the ImageView bounds to match the Drawable's dimensions.
        //i.setAdjustViewBounds(true);
        //i.setLayoutParams(new Gallery.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));


        /*System.out.println("PRINTING FINAL CARD COUNTS");
        for (int i = 0; i < MAX_CARD; i++) {
            System.out.println("INDEX: " + i + " COUNT: " + cardCount[i]);
        }

        System.out.println("PRINTING FINAL CARD ARRANGEMENT");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.println("INDEX: " + i + j + " CARD: " + cards[i][j]);
            }
        }*/

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
    protected void onPause() {
        super.onPause();
        paused = 1;
        timeLimit.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        paused = 0;

        // Sets the time limit for the game.
        timeLimit = new CountDownTimer(millisLeft, 1000) {
            public void onTick(long millisUntilFinished) {
                millisLeft = millisUntilFinished;
                TextView timelimit = (TextView) findViewById(R.id.timelimit);
                int totalSeconds = (int)millisUntilFinished/1000;
                timeLeft = totalSeconds;
                int mins = totalSeconds/60;
                int secs = totalSeconds - (mins*60);

                if(secs < 10)
                    timelimit.setText("Time Remaining: "+mins+":0"+secs);
                else
                    timelimit.setText("Time Remaining: "+mins+":"+secs);
                timelimit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28.0f*scale);
            }
            public void onFinish() {
                if (paused == 0) {
                    TextView timelimit = (TextView) findViewById(R.id.timelimit);
                    timelimit.setText("Time Remaining: 0:00");
                    timelimit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28.0f * scale);
                    timeBonus = 0;
                    triesBonus = 0;
                    win = 0;
                    gameOver();
                }
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
        timeLimit.cancel();
        super.onBackPressed();
        //this.finish();
    }

    public class CustomView extends View {
        public CustomView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            Drawable d = ContextCompat.getDrawable(this.getContext(), R.drawable.backcard);
            d.setBounds(0, 0, 50, 50);
            d.draw(canvas);
        }
    }

    // Flips the card to reveal it and compares to previously flipped card.
    public void flipCard(View v) {
        // Get view's id.
        String viewId = v.getResources().getResourceEntryName(v.getId());

        // Determine indices of card.
        final int row, col;

        if (viewId.equals("card0") || viewId.equals("card1") || viewId.equals("card2") || viewId.equals("card3"))
            row = 0;
        else if (viewId.equals("card4") || viewId.equals("card5") || viewId.equals("card6") || viewId.equals("card7"))
            row = 1;
        else if (viewId.equals("card8") || viewId.equals("card9") || viewId.equals("card10") || viewId.equals("card11"))
            row = 2;
        else
            row = 3;

        if (viewId.equals("card0") || viewId.equals("card4") || viewId.equals("card8") || viewId.equals("card12"))
            col = 0;
        else if (viewId.equals("card1") || viewId.equals("card5") || viewId.equals("card9") || viewId.equals("card13"))
            col = 1;
        else if (viewId.equals("card2") || viewId.equals("card6") || viewId.equals("card10") || viewId.equals("card14"))
            col = 2;
        else
            col = 3;

        // Same card being attempted to be flipped again.
        if (row == prevRow && col == prevCol)
            return;

        if (cardsFlipped == 2)
            return;

        // Reveal the card.
        ImageButton btn = (ImageButton)findViewById(v.getId());
        //btn.setImageResource(R.drawable.blankcard);
        int cardVal = cards[row][col];

        //sp.play(soundIds[0], 1.0f, 1.0f, 1, 0, 1.0f);
        if (cardVal == 0) {
            btn.setImageResource(R.drawable.card0);
            sp.play(soundIds[0], 1.0f, 1.0f, 1, 0, 1.0f);
        }
        else if (cardVal == 1) {
            btn.setImageResource(R.drawable.card1);
            sp.play(soundIds[1], 1.0f, 1.0f, 1, 0, 1.0f);
        }
        else if (cardVal == 2) {
            btn.setImageResource(R.drawable.card2);
            sp.play(soundIds[2], 1.0f, 1.0f, 1, 0, 1.0f);
        }
        else if (cardVal == 3) {
            btn.setImageResource(R.drawable.card3);
            sp.play(soundIds[3], 1.0f, 1.0f, 1, 0, 1.0f);
        }
        else if (cardVal == 4) {
            btn.setImageResource(R.drawable.card4);
            sp.play(soundIds[4], 1.0f, 1.0f, 1, 0, 1.0f);
        }
        else if (cardVal == 5) {
            btn.setImageResource(R.drawable.card5);
            sp.play(soundIds[5], 1.0f, 1.0f, 1, 0, 1.0f);
        }
        else if (cardVal == 6) {
            btn.setImageResource(R.drawable.card6);
            sp.play(soundIds[6], 1.0f, 1.0f, 1, 0, 1.0f);
        }
        else if (cardVal == 7) {
            btn.setImageResource(R.drawable.card7);
            sp.play(soundIds[7], 1.0f, 1.0f, 1, 0, 1.0f);
        }

        // Number of cards flipped.
        cardsFlipped++;

        // If 1 card flipped, store its details.
        if (cardsFlipped == 1) {
            prevRow = row;
            prevCol = col;
            prevCardId = v.getId();
            return;
        }

        // If 2 cards are flipped, compare them to see if it's a match.
        if (cardsFlipped == 2) {
            // Sleep for 2 seconds.
            /*try {
                Thread.sleep(2000);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
            }*/

            //SystemClock.sleep(2000);

            // Store card buttons.
            /*for (int i = 0; i < MAX_CARD; i++) {
                String cid = "card" + Integer.toString(i);
                int resID = getResources().getIdentifier(cid, "id", "jamalian.sina.memorygame");
                //cardButtons[i] = (ImageButton)findViewById(R.id.cid);
                cardButtons[i] = (ImageButton)findViewById(resID);
                System.out.println(cid + " " + resID + " " + cardButtons[i]);
            }*/

            final int vid = v.getId();

            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                public void run() {
                    ImageButton btn1 = (ImageButton) findViewById(vid);
                    ImageButton btn2 = (ImageButton) findViewById(prevCardId);

                    // Make all buttons unclickable until comparison is made.
                    for (int i = 0; i < uniqueCards*2; i++) {
                        ImageButton btn = cardButtons[i];
                        if (btn != null)
                            btn.setClickable(false);
                    }

                    // See if a match is made.
                    int match = 0;

                    // Pair of cards flipped over considered as a try.
                    noOfTries++;

                    TextView tv1 = (TextView) findViewById(R.id.tv1);
                    tv1.setText("Tries: " + noOfTries);
                    tv1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28.0f * scale);

                    // Cards match.
                    if (cards[row][col] == cards[prevRow][prevCol]) {
                        //btn1.setImageResource(R.drawable.blankcard);
                        //btn2.setImageResource(R.drawable.blankcard);
                        //btn1.setVisibility(View.GONE);
                        //btn2.setVisibility(View.GONE);
                        btn1.setVisibility(View.INVISIBLE);
                        btn2.setVisibility(View.INVISIBLE);

                        // Update matches made.
                        match = 1;
                        matchesMade++;
                        //TextView tv2 = (TextView) findViewById(R.id.tv2);
                        //tv2.setText("Matches: " + Integer.toString(matchesMade) + "/8");

                        // Award points for match.
                        roundScore += 100;
                        //TextView scoretv = (TextView) findViewById(R.id.score);
                        //scoretv.setText("Score: "+roundScore);
                    }
                    // Cards don't match.
                    else {
                        btn1.setImageResource(R.drawable.backcard);
                        btn2.setImageResource(R.drawable.backcard);
                    }

                    prevRow = -1;
                    prevCol = -1;
                    prevCardId = -1;

                    if (matchesMade == uniqueCards) {
                        //TextView win = (TextView) findViewById(R.id.win);
                        //win.setText("YOU WON!");
                        //timeLimit.cancel();
                        //timeBonus = (timeLeft*10)+(level*500);
                        int secondsTaken = ((roundTime/1000) - timeLeft);
                        timeBonus = timeBonus - (10*level*secondsTaken);
                        timeBonus = Math.max(timeBonus, 0);
                        triesBonus = Math.max(((triesBonus-noOfTries)*100), 0);
                        check();
                    }

                    // Make all unmatched buttons clickable again.
                    for (int i = 0; i < uniqueCards*2; i++) {
                        ImageButton btn = cardButtons[i];
                        if (btn == null)
                            continue;
                        else if (match == 1 && (btn.equals(btn1) || btn.equals(btn2)))
                            cardButtons[i] = null;
                        else
                            btn.setClickable(true);
                    }

                    cardsFlipped = 0;
                }
            }, 1500);
        }
    }

    // Starts a new game.
    public void startGame(View view) {
        sp.release();
        // Start the game with an intent to a new activity.
        Intent intent = new Intent(this, StartGameActivity.class);
        intent.putExtra("level", 1);
        intent.putExtra("score", 0);
        intent.putExtra("uniqueCards", uniqueCards);
        startActivity(intent);
        // Destroy current activity so you can't come back to it.
        finish();
    }

    // Check whether the player won or if they advance to the next level.
    public void check() {
        timeLimit.cancel();
        if (level == 7) {
            //TextView win = (TextView) findViewById(R.id.win);
            //win.setText("YOU WON!");
            win = 1;
            gameOver();
        }
        else {
            //level++;
            //roundScore += timeLeft;
            //score += roundScore;
            //TextView scoretv = (TextView) findViewById(R.id.score);
            //scoretv.setText("Score: "+roundScore);
            nextLevel();
        }
    }

    // Starts the next level.
    public void nextLevel() {
        score += roundScore;
        score += timeBonus;
        score += triesBonus;
        sp.release();
        // Go to the next level with an intent to a new activity.
        Intent intent = new Intent(this, NextLevel.class);
        intent.putExtra("level", level);
        intent.putExtra("score", score);
        intent.putExtra("roundScore", roundScore);
        intent.putExtra("timeBonus", timeBonus);
        intent.putExtra("triesBonus", triesBonus);
        intent.putExtra("uniqueCards", uniqueCards);
        intent.putExtra("win", win);
        startActivity(intent);
        // Destroy current activity so you can't come back to it.
        finish();
    }

    // Player won so check to see if high score needs to be updated.
    public void updateHighScore() {
        // Get the high score.
        SharedPreferences savedHighScore = this.getSharedPreferences("prefsKeyAMG", Context.MODE_PRIVATE);
        int highScore;

        if (uniqueCards == 4) {
            highScore = savedHighScore.getInt("easyHighScore", 0);
        }
        else if (uniqueCards == 6) {
            highScore = savedHighScore.getInt("mediumHighScore", 0);
        }
        else {
            highScore = savedHighScore.getInt("hardHighScore", 0);
        }

        // Player's score for the game.
        int playerScore = score+roundScore+timeBonus+triesBonus;

        // Update the high score.
        SharedPreferences.Editor editor = savedHighScore.edit();
        if (playerScore > highScore) {
            if (uniqueCards == 4) {
                editor.putInt("easyHighScore", playerScore);
            }
            else if (uniqueCards == 6) {
                editor.putInt("mediumHighScore", playerScore);
            }
            else {
                editor.putInt("hardHighScore", playerScore);
            }
            editor.commit();
            hsUpdated = 1;
        }

        //System.out.println("HIGH SCORE: " + highScore);
        //SharedPreferences.Editor editor = highScore.edit();
    }

    // Game is over, finalize player score.
    public void gameOver() {
        updateHighScore();
        score += roundScore;
        score += timeBonus;
        score += triesBonus;
        sp.release();
        Intent intent = new Intent(this, GameOver.class);
        intent.putExtra("level", level);
        intent.putExtra("score", score);
        intent.putExtra("roundScore", roundScore);
        intent.putExtra("timeBonus", timeBonus);
        intent.putExtra("triesBonus", triesBonus);
        intent.putExtra("uniqueCards", uniqueCards);
        intent.putExtra("win", win);
        intent.putExtra("hsUpdated", hsUpdated);
        startActivity(intent);
        // Destroy current activity so you can't come back to it.
        finish();




        //TextView win = (TextView) findViewById(R.id.win);
        //win.setText("YOU LOSE!");
        //finish();
    }
}

