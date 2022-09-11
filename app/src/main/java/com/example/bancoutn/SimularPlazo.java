package com.example.bancoutn;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bancoutn.databinding.ActivitySimularPlazoBinding;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SimularPlazo extends AppCompatActivity {

    private ActivitySimularPlazoBinding binding;

    private Toolbar toolBar;
    private Button btnConfirmar;
    private EditText tna_user, tea_user, capital_user;
    private TextView plazo_sim, capital_sim, intereses_sim, monto_sim, monto_anual_sim, dias_seek;
    private SeekBar seekDias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySimularPlazoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Configurar la toolbar como barra de accion principal
        toolBar = binding.miToolbar.toolbarPropia;
        setSupportActionBar(toolBar);
        toolBar.setTitle("Simular plazo fijo");

        //Habilitar para que se pueda volver para atras desde la toolbar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Boton CONFIRMAR
        btnConfirmar = binding.botonConfirmar;
        btnConfirmar.setEnabled(false);
        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                //..
                setResult(1,i); //Ver que pasa con el deprecated de starActivityForResult
                finish();
            }
        });

        manejarInformacion();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed(); //Para cuando se seleccione la flecha atrás de la toolbar
                                // va a ejecutar lo mismo que si se presionara back
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void manejarInformacion(){

        //TextsViews de informacion
        plazo_sim = binding.txtDiasSimulador; // x dias
        capital_sim = binding.txtCapitalSimulador; // capital
        intereses_sim = binding.txtInteresesGanados; // intereses ganados
        monto_sim = binding.txtMontoTotal; // monto total
        monto_anual_sim = binding.txtMontoTotalAnual; //monto total anual

        dias_seek = binding.txtDiasSeek; // texto debajo de la seek

        //EditText de la tasa nominal anual
        tna_user = binding.editTNA;
        //EditText de la tasa efectiva anual
        tea_user = binding.editTEA;
        //EditText del capital a invertir
        capital_user = binding.editCapital;
        //SeekBar de los dias
        seekDias = binding.seekDias;

        tna_user.getText();
        //EditText de la tasa nominal anual
        tna_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Calcular
                double tna, tea, capital;
                try {
                    tna = Double.parseDouble(tna_user.getText().toString());
                } catch (Exception e){
                    tna = 0;
                }
                try {
                    tea = Double.parseDouble(tea_user.getText().toString());
                } catch (Exception e){
                    tea = 0;
                }
                try {
                    capital = Double.parseDouble(capital_user.getText().toString());
                } catch (Exception e){
                    capital = 0;
                }

                int meses = seekDias.getProgress();
                if(meses!=0) calcular(tna,tea,capital,meses);
                else calcular(0,0,0,1); //Para que muestre todos 0 y no de error de divison por cero
            }
        });

        //EditText de la tasa efectiva anual
        tea_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Calcular
                double tna, tea, capital;
                try {
                    tna = Double.parseDouble(tna_user.getText().toString());
                } catch (Exception e){
                    tna = 0;
                }
                try {
                    tea = Double.parseDouble(tea_user.getText().toString());
                } catch (Exception e){
                    tea = 0;
                }
                try {
                    capital = Double.parseDouble(capital_user.getText().toString());
                } catch (Exception e){
                    capital = 0;
                }

                int meses = seekDias.getProgress();
                if(meses!=0) calcular(tna,tea,capital,meses);
                else calcular(0,0,0,1); //Para que muestre todos 0 y no de error de divison por cero
            }
        });

        //EditText del capital a invertir
        capital_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Calcular
                double tna, tea, capital;
                try {
                    tna = Double.parseDouble(tna_user.getText().toString());
                } catch (Exception e){
                    tna = 0;
                }
                try {
                    tea = Double.parseDouble(tea_user.getText().toString());
                } catch (Exception e){
                    tea = 0;
                }
                try {
                    capital = Double.parseDouble(capital_user.getText().toString());
                } catch (Exception e){
                    capital = 0;
                }

                int meses = seekDias.getProgress();
                capital_sim.setText(""+round3Decimals(capital)); //Seteo de Capital: $
                if(meses!=0) calcular(tna,tea,capital,meses);
                else calcular(0,0,0,1); //Para que muestre todos 0 y no de error de divison por cero

            }
        });

        //SeekBar de los dias
        seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                dias_seek.setText(progress*30+" días"); //Seteo del texto debajo de la seekbar
                plazo_sim.setText(progress*30+" días"); //Seteo del Plazo: x dias
                //Calcular
                double tna, tea, capital;
                try {
                    tna = Double.parseDouble(tna_user.getText().toString());
                } catch (Exception e){
                    tna = 0;
                }
                try {
                    tea = Double.parseDouble(tea_user.getText().toString());
                } catch (Exception e){
                    tea = 0;
                }
                try {
                    capital = Double.parseDouble(capital_user.getText().toString());
                } catch (Exception e){
                    capital = 0;
                }

                int meses = seekDias.getProgress();
                if(meses!=0) calcular(tna,tea,capital,meses);
                else calcular(0,0,0,1); //Para que muestre todos 0 y no de error de divison por cero
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    private void calcular(double tna, double tea, double capital, int meses){
        //Calcula y actualiza los valores del simulador
            double intereses = (tna/100)/(12/meses) * capital;
            double monto_total = capital + intereses;
            double monto_total_anual = intereses*12 + capital;

            intereses_sim.setText(""+round3Decimals(intereses));
            monto_sim.setText(""+round3Decimals(monto_total));
            monto_anual_sim.setText(""+round3Decimals(monto_total_anual));

    }

    //Método para redondear a 3 decimales
    public static Double round3Decimals(Double value) {
        return new BigDecimal(value.toString()).setScale(3, RoundingMode.HALF_UP).doubleValue();
    }



}