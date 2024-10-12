package com.example.lanzarundado;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lanzarundado.databinding.ActivityMainBinding;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private boolean currPlayer1 = true;

    private static final String ZERO = "0";

    private static final int LONG = 5000;
    private static final int MID = 3000;
    private static final int SHORT = 1500;

    private int [] currentPlayers = {0,0};
    private int [] globalPlayers= {0,0};

    View leftPanel;
    View rightPanel;
    TextView acumP1;
    TextView acumP2;
    TextView currP1;
    TextView currP2;
    Button btnThrow;
    Button btnHold;
    Button btnNewGame;
    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView((binding = ActivityMainBinding.inflate(getLayoutInflater())).getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Paneles coloreables
        leftPanel = (View) findViewById(R.id.leftView);
        rightPanel = (View) findViewById(R.id.rightView);

        //Acumulados
        acumP1 = (TextView) findViewById(R.id.tvAcumP1);
        acumP2 = (TextView) findViewById(R.id.tvAcumP2);

        //Globales
        currP1 = (TextView) findViewById(R.id.tv_curr_p1);
        currP2 = (TextView) findViewById(R.id.tv_curr_p2);

        //Botones
        btnThrow = (Button) findViewById(R.id.btn_throw);
        btnHold = (Button) findViewById(R.id.btn_hold);
        btnNewGame = (Button) findViewById(R.id.btn_newGame);

        //Imagen dado
        img1 = (ImageView) findViewById(R.id.imgDice);

        //Inicializar componentes
        leftPanel.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cust_grey_selected));

        btnThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int rdInt = getRandDiceValue();

                img1.setImageResource(provideImageResolurceID(rdInt));

                if(rdInt == 1){
                    if(currPlayer1){
                        acumP1.setText(ZERO);
                        currP1.setText(ZERO);
                    }else{
                        acumP2.setText(ZERO);
                        currP2.setText(ZERO);
                    }

                    img1.setImageResource(R.drawable.dice_random);
                    changeCurrentPlayer();
                    Toast.makeText(MainActivity.this, " MALA SUERTE :( ", Toast.LENGTH_SHORT).show();


                }else{
                    if(currPlayer1){
                        int current = Integer.parseInt(currP1.getText().toString());
                        currP1.setText(String.valueOf(current+rdInt+30)); //TODO QUITAR + 30
                    }else{
                        int current = Integer.parseInt(currP2.getText().toString());
                        currP2.setText(String.valueOf(current+rdInt+30)); //TODO QUITAR + 30
                    }
                }

                int p1Score = Integer.parseInt(currP1.getText().toString()) + Integer.parseInt(acumP1.getText().toString());
                int p2Score = Integer.parseInt(currP2.getText().toString()) + Integer.parseInt(acumP2.getText().toString());

                if(p1Score >= 100){
                    Toast.makeText(MainActivity.this, "¡Jugador P1 GANADOR!", Toast.LENGTH_SHORT).show();
                    resetGame();
                }else if(p2Score >= 100){
                    Toast.makeText(MainActivity.this, "¡Jugador P2 GANADOR!", Toast.LENGTH_SHORT).show();
                    resetGame();
                }
            }
        });

        btnNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
                Toast.makeText(MainActivity.this, "Pulsa LANZAR para empezar...", Toast.LENGTH_SHORT).show();

            }
        });


        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currPlayer1){
                    int acumulated = Integer.parseInt(acumP1.getText().toString());
                    int current = Integer.parseInt(currP1.getText().toString());

                    acumP1.setText(String.valueOf(acumulated+current));
                    currP1.setText(ZERO);

                }else{
                    int acumulated = Integer.parseInt(acumP2.getText().toString());
                    int current = Integer.parseInt(currP2.getText().toString());

                    acumP2.setText(String.valueOf(acumulated+current));
                    currP2.setText(ZERO);
                }

                changeCurrentPlayer();
                img1.setImageResource(R.drawable.dice_random);
            }
        });


        Toast.makeText(this, "Pulsa LANZAR para empezar...", Toast.LENGTH_SHORT).show();


    }
    private int getRandDiceValue(){
        Random random = new Random();
        return random.nextInt(6)+1;
    }


    public void resetGame(){
        acumP1.setText(ZERO);
        acumP2.setText(ZERO);
        currP1.setText(ZERO);
        currP2.setText(ZERO);

        leftPanel.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cust_grey_selected));
        rightPanel.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cust_grey_nonselected));

        img1.setImageResource(R.drawable.dice_random);
        currPlayer1 = true;




    }

    private void changeCurrentPlayer(){
        this.currPlayer1 = !this.currPlayer1;

        if(this.currPlayer1){
            leftPanel.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cust_grey_selected));
            rightPanel.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cust_grey_nonselected));
        }else{
            rightPanel.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cust_grey_selected));
            leftPanel.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cust_grey_nonselected));
        }
    }

    private int provideImageResolurceID(int numDice){
        switch (numDice){
            case 1:
                return R.drawable.dice_one;
            case 2:
                return R.drawable.dice_two;
            case 3:
                return R.drawable.dice_three;
            case 4:
                return R.drawable.dice_four;
            case 5:
                return R.drawable.dice_five;
            case 6:
                return R.drawable.dice_six;
            default:
                return -1;
        }

    }


}