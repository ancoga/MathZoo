package com.weblab.comino.mathZoo;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class Main2Activity_Nivel1 extends AppCompatActivity {

    private TextView tv_name, tv_score;
    private ImageView iv_auno, iv_ados, iv_life, iv_signal;
    private EditText et_respuesta;
    private MediaPlayer mp, mp_great, mp_bad;
    private int failures = 0;

    int score, numRandom_one, numRandom_two, result, life = 3;
    String nombre_jugador, string_score, string_vidas;
    int number[] = new int [] {R.drawable.cero, R.drawable.uno, R.drawable.dos, R.drawable.tres, R.drawable.cuatro, R.drawable.cinco, R.drawable.seis, R.drawable.siete, R.drawable.ocho, R.drawable.nueve};
    int signal[] = new int [] {R.drawable.adicion, R.drawable.resta, R.drawable.multiplicacion};
    int iLife[] = new int []{R.drawable.tresvidas, R.drawable.dosvidas, R.drawable.unavida};
    Valoration userScore;

    // Create bbdd connection
    AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);

    private InterstitialAd mInterstitialAd;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2__nivel1);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Small Banner
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Fullscreen Banner
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        tv_name = (TextView) findViewById(R.id.textView_nombre);
        tv_score = (TextView) findViewById(R.id.textView_score);
        iv_life = (ImageView) findViewById(R.id.imageView_vidas);
        iv_auno = (ImageView) findViewById(R.id.imageView_num1);
        iv_ados = (ImageView) findViewById(R.id.imageView_num2);
        iv_signal = (ImageView) findViewById(R.id.imageView_signo);
        et_respuesta = (EditText) findViewById(R.id.editText_resultado);

        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_name.setText(nombre_jugador);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mp = MediaPlayer.create(this, R.raw.goats);
        mp.start();
        mp.setLooping(true);

        mp_great = MediaPlayer.create(this, R.raw.wonderful);
        mp_bad = MediaPlayer.create(this, R.raw.bad);

        // We check if there is a game saved and if affirmative we put the score collected in the database.
        if(admin.getExistScoreUser(nombre_jugador)){
            Valoration valoration = admin.getDataPlayer(nombre_jugador);
            score = valoration.getScore();
            tv_score.setText("Score: "+String.valueOf(valoration.getScore()));
        }
        // Part to draw number randomly.
        setNum();

    }
    // Function to draw the number randomly depending on the level of addition, subtraction, multiplication.

    /**
     * Method to represent random numbers for mathematical operations.
     * These operations are related with the level that the user has at the moment.
     * The operations are addition, subtraction, multiplication.
     */
    public void setNum (){

        int level = getLevel();
        int aleSignal;

        int numRandom_one = (int) (Math.random() * 10);
        int numRandom_two = (int) (Math.random() * 10);

        //Nivel 1 suma
        switch (level){
            case 1: //Level 1 addition
                Toast.makeText(this, R.string.level1, Toast.LENGTH_SHORT).show();
                result = numRandom_one + numRandom_two;
                while(result > 10){
                    numRandom_one = (int) (Math.random() * 10);
                    numRandom_two = (int) (Math.random() * 10);
                    result = numRandom_one + numRandom_two;
                }
                iv_signal.setImageResource(R.drawable.adicion);
                break;
            case 2: //Level 2 addition complex
                Toast.makeText(this, R.string.level2, Toast.LENGTH_SHORT).show();
                result = numRandom_one + numRandom_two;
                while(result > 19){
                    numRandom_one = (int) (Math.random() * 10);
                    numRandom_two = (int) (Math.random() * 10);
                    result = numRandom_one + numRandom_two;
                }
                iv_signal.setImageResource(R.drawable.adicion);
                break;
            case 3: //Level 3 subtraction
                Toast.makeText(this, R.string.level3, Toast.LENGTH_SHORT).show();
                result = numRandom_one - numRandom_two;
                while (result < 0) {
                    numRandom_one = (int) (Math.random() * 10);
                    numRandom_two = (int) (Math.random() * 10);
                    result = numRandom_one - numRandom_two;
                }
                iv_signal.setImageResource(R.drawable.resta);
                break;
            case 4: //Level 4 addition and subtraction
                Toast.makeText(this, R.string.level4, Toast.LENGTH_SHORT).show();
                aleSignal = (int) (Math.random() * 2);
                switch (aleSignal){
                    case 0:
                        result = numRandom_one + numRandom_two;
                        break;
                    case 1:
                        result = numRandom_one - numRandom_two;
                        while (result < 0) {
                            numRandom_one = (int) (Math.random() * 10);
                            numRandom_two = (int) (Math.random() * 10);
                            result = numRandom_one - numRandom_two;
                        }
                        break;
                }
                iv_signal.setImageResource(signal[aleSignal]);
                break;
            case 5: //Level 5 multiplications
                Toast.makeText(this, R.string.level5, Toast.LENGTH_SHORT).show();
                result = numRandom_one * numRandom_two;
                iv_signal.setImageResource(R.drawable.multiplicacion);
                break;
            case 6: //Level 6 All operations.
                Toast.makeText(this, R.string.level6, Toast.LENGTH_SHORT).show();
                aleSignal = (int) (Math.random() * 3);
                switch (aleSignal){
                    case 0:
                        result = numRandom_one + numRandom_two;
                        break;
                    case 1:
                        result = numRandom_one - numRandom_two;
                        while (result < 0) {
                            numRandom_one = (int) (Math.random() * 10);
                            numRandom_two = (int) (Math.random() * 10);
                            result = numRandom_one - numRandom_two;
                        }
                        break;
                    case 2:
                        result = numRandom_one * numRandom_two;
                        break;
                }
                iv_signal.setImageResource(signal[aleSignal]);
                break;
        }
        iv_auno.setImageResource(number[numRandom_one]);
        iv_ados.setImageResource(number[numRandom_two]);
    }


    /**
     * Method to know the user level according to his punctuation.
     * Variable score is global and tells us in what level the user is.
     * There are 6 levels according to the punctuation ranges.
     * @return int : level number
     */
    public int getLevel (){
        int level=0;
        //Level 1 addition
        if(score >= 0 && score < 10){
            level = 1;
        }
        //Level 2 addition complex
        else if(score >=10 && score < 20){
            level = 2;
        }
        //Level 3 subtraction
        else if(score >=20 && score < 30) {
            level = 3;
        }
        //Level 4 addition and subtraction
        else if(score >=30 && score < 40) {
            level = 4;
        }
        //Level 5 multiplications
        else if(score >=40 && score < 50) {
            level = 5;
        }
        //Level 6 All operations.
        else if(score >=50) {
            level = 6;
        }
        return level;
    }

    /**
     * Method that calculates if the answer given by the mathematical operation user is right or not.
     *
     * @param view
     */
    public void calculate(View view){
        String respuesta_string = et_respuesta.getText().toString();
        int respuesta_int = 0;
        if (!respuesta_string.trim().equals("")){
            respuesta_int = Integer.parseInt(respuesta_string);
            et_respuesta.setText("");
        }
        if(result == respuesta_int){
            score ++;
            mp_great.start();
        }else{
            mp_bad.start();
            failures ++;
            if(failures < 3){
                iv_life.setImageResource(iLife[failures]);
            }else{ // si pierde todas las vidas salga mensaje, se reinicie y se guarde la puntuación máx que ha conseguido.
                Toast.makeText(this, R.string.gameOver, Toast.LENGTH_SHORT).show();
                score = 0;
                failures = 0;
                iv_life.setImageResource(iLife[failures]);
            }
        }
        tv_score.setText("Score: "+score);
        setNum ();
    }

    /**
     * Method to save the user data in the database in the punctuation table.
     * Data saved are the name of the player and his punctuation.
     *
     * @param view
     */
    public void save(View view){
        // show publicity fullscreem.
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        userScore = new Valoration(nombre_jugador, score);
        if(admin.getExistScoreUser(nombre_jugador)){
            if(admin.UpdatePoints(userScore)){
                Toast.makeText(this,R.string.saveScoreOk, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,R.string.saveScoreFail, Toast.LENGTH_SHORT).show();
            }
        }else{
            if(admin.InsertPoints(userScore)){
                Toast.makeText(this,R.string.saveScoreOk, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,R.string.saveScoreFail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed(){
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        mp.stop();
        mp.release();
    }
    @Override
    protected void onStop(){
        super.onStop();
        mp.stop();
        mp.release();
    }
}

