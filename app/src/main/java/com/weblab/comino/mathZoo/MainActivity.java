package com.weblab.comino.mathZoo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre;
    private TextView tv_bestScore;
    private MediaPlayer mp;
    private Valoration valoration;

    int num_aleatorio = (int) (Math.random() * 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        et_nombre = (EditText) findViewById(R.id.txt_nombre);
        tv_bestScore = (TextView) findViewById(R.id.textView_bestScore);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        //We create the conection to the database.
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);
        //Call the database to get the player with the highest score.
        valoration = admin.getBestPlayer();
        if(valoration != null)
            tv_bestScore.setText("Record: " + valoration.getScore() + " de " + valoration.getName());
        //Loads the sound that the application will have and reproduces it in a cyclic way (repetitions)
        mp = MediaPlayer.create(this, R.raw.alphabet_song);
        mp.start();
        mp.setLooping(true);
    }

    /**
     * Method to start playing.
     * Where the user has to put the name of the player to start that will take him to the game screen.
     * Otherwise a message will appear informing
     * @param view
     */
    public void jugar(View view){
        String nombre = et_nombre.getText().toString();

        if(!nombre.equals("")){
            mp.stop();
            mp.release();

            Intent intent = new Intent(this, Main2Activity_Nivel1.class);
            intent.putExtra("jugador", nombre);
            startActivity(intent);
            //finish();
        }else{
            Toast.makeText(this, R.string.errMsg, Toast.LENGTH_SHORT).show();

            // ABRE EL TECLADO PARA EMPEZAR A ESCRIBIR EN EL et_nombre COGE EL FOCO.
            et_nombre.requestFocus();
            InputMethodManager imm = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et_nombre, InputMethodManager.SHOW_IMPLICIT);
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
}
