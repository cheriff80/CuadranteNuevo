package com.example.cuadrante;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

public class Pantalla_inicio extends AppCompatActivity implements View.OnClickListener {

    private Button bt_inicio_inicio,bt_inicio_registro;
    private CalendarView cv_inicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_inicio);

        bt_inicio_inicio = findViewById(R.id.bt_inicio_inicio);
        bt_inicio_registro = findViewById(R.id.bt_inicio_registro);
        cv_inicio = findViewById(R.id.cv_calendario);

        bt_inicio_registro.setOnClickListener(this);
        bt_inicio_inicio.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if(i == R.id.bt_inicio_registro){
            Intent intent = new Intent(Pantalla_inicio.this, Registro.class);
            startActivity(intent);
        }
        if(i == R.id.bt_inicio_inicio){
            Intent intent = new Intent(Pantalla_inicio.this, PantallaAutenticacion.class);
            startActivity(intent);
        }
    }
}
