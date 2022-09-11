package com.example.bancoutn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bancoutn.databinding.ActivityConstituirPlazoBinding;

public class ConstituirPlazo extends AppCompatActivity {

    private ActivityConstituirPlazoBinding binding;

    private Toolbar toolBar;
    private Button btnConstituir, btnSimular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityConstituirPlazoBinding.inflate(getLayoutInflater());
        //setContentView(R.layout.activity_main);

        //Uso del View Binding
        setContentView(binding.getRoot()); // Funciona el binding pero se ve mal, no me toma el margen
                                            // del linear layout general, por eso anide uno más (layout principal)

        //Configurar la toolbar como barra de accion principal
        toolBar = binding.miToolbar.toolbarPropia;
        setSupportActionBar(toolBar);
        toolBar.setTitle("Constituir plazo fijo");



        //Boton CONSTITUIR deshabilitado al inicio de la actividad
        btnConstituir = binding.botonConstituir;
        btnConstituir.setEnabled(false);

        //Boton SIMULAR
        btnSimular = binding.botonSimular;
        btnSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ConstituirPlazo.this, SimularPlazo.class);
                //pasar nombre, apellido y moneda
                startActivityForResult(i, 10); //Ver

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        //Ver que pasa con el deprecated de starActivityForResult

        if (requestCode==10){
            //...
        }
        super.onActivityResult(requestCode,resultCode,data);

    }

}