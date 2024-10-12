package com.example.lanzarundado;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView tv1 = findViewById(R.id.textView1);
        ImageView img1 = findViewById(R.id.imageView1);

        Button btnThrow = findViewById(R.id.btn_throw);
        btnThrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //reseting btn && textView
                btnThrow.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cust_fucsia));
                tv1.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));
                btnThrow.setText("lanzar");


                int rdInt = getRandDiceValue();
                tv1.setText(String.valueOf(rdInt));
                img1.setImageResource(provideImageResolurceID(rdInt));

                if(rdInt == 1){
                    btnThrow.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cust_red));
                    btnThrow.setText("Error");
                    tv1.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.cust_red));
                }
            }
        });


        Toast.makeText(this, "Pulsa LANZAR para empezar...", Toast.LENGTH_SHORT).show();


    }
    private int getRandDiceValue(){
        Random random = new Random();
        return random.nextInt(6)+1;
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