package com.example.practica_4_cronometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    long elapsedTime = 0;
    Handler handler = new Handler();
    private TextView tv_tiempo;
    private Button btn_1, btn_2;
    private  ViewGroup ly_vueltas;

    private int segundos = 0;
    private int minutos = 0;
    private  int horas = 0;

    private boolean iniciado = false;
    private boolean pausado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_tiempo= (TextView) findViewById(R.id.tv_tiempo);
        btn_1 = (Button) findViewById(R.id.btn_uno);
        btn_2 = (Button) findViewById(R.id.btn_2);
        ly_vueltas = (ViewGroup) findViewById(R.id.ly_vueltas);

        Runnable updateTimer = new Runnable() {
            @Override
            public void run() {

                segundos++;

                if(segundos == 60){
                    segundos = 0;
                    minutos++;
                }

                if(minutos == 60){
                    minutos = 0;
                    horas++;
                }

                String tiempo = String.format("%02d:%02d:%02d", horas, minutos, segundos);

                tv_tiempo.setText(tiempo);
                handler.postDelayed(this, 1000);
            }
        };


        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!iniciado){
                    handler.postDelayed(updateTimer, 1000);
                    btn_1.setText("Pausar");
                    btn_2.setText("Vuelta");
                    iniciado = true;
                    pausado = false;
                }else{
                    handler.removeCallbacks(updateTimer);
                    btn_1.setText("Reanudar");
                    btn_2.setText("Reiniciar");
                    iniciado = false;
                    pausado = true;
                }
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pausado){
                    segundos = 0;
                    minutos = 0;
                    horas = 0;
                    tv_tiempo.setText("00:00.00");
                    btn_1.setText("Iniciar");
                    btn_2.setText("Vuelta");
                    ly_vueltas.removeAllViews();
                    pausado = false;
                }else{
                    LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                    int id = R.layout.activity_vuelta;
                    RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(id, null, false);

                    TextView tv_vuelta = (TextView) relativeLayout.findViewById(R.id.tv_vuelta);
                    tv_vuelta.setText(tv_tiempo.getText());

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    relativeLayout.setPadding(5, 0, 5, 10);
                    relativeLayout.setLayoutParams(params);
                    ly_vueltas.addView(relativeLayout);
                }
            }
        });

    }

}