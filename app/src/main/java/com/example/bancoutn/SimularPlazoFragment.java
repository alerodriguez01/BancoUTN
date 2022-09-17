package com.example.bancoutn;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;


import com.example.bancoutn.databinding.FragmentSimularPlazoBinding;
import com.example.bancoutn.utilities.Utilities;
import com.example.bancoutn.viewmodels.ConstituirPlazoViewModel;
import com.example.bancoutn.viewmodels.SimularPlazoViewModel;

public class SimularPlazoFragment extends Fragment {

    private FragmentSimularPlazoBinding binding;
    private SimularPlazoViewModel viewModel;
    private ConstituirPlazoViewModel viewModel2;

    private EditText tna_user, tea_user, capital_user;
    private SeekBar seekDias;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentSimularPlazoBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        tna_user = binding.editTNA;
        tea_user = binding.editTEA;
        capital_user = binding.editCapital;
        seekDias = binding.seekDias;

        viewModel = new ViewModelProvider(getActivity()).get(SimularPlazoViewModel.class);
        viewModel2 = new ViewModelProvider(getActivity()).get(ConstituirPlazoViewModel.class);

        tna_user.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(tna_user.hasFocus())
                {
                    if(tna_user.getText().length() > 0)
                    {
                        double tea = viewModel.calcularTEA(Utilities.editableToDouble(tna_user.getText()));
                        binding.editTEA.setText(Utilities.round2(tea));
                    } else tea_user.setText(null);
                    calcular();
                }
            }

        });

        //EditText de la tasa efectiva anual
        tea_user.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(tea_user.hasFocus())
                {
                    if(tea_user.getText().length() > 0)
                    {
                        double tna = viewModel.calcularTNA(Utilities.editableToDouble(tea_user.getText()));
                        binding.editTNA.setText(Utilities.round2(tna));
                    } else tna_user.setText(null);
                    calcular();
                }
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
                if(capital_user.hasFocus()) calcular();
            }
        });

        //SeekBar de los dias
        seekDias.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                if(progress < 1) seekBar.setProgress(1);
                calcular();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //Boton CONFIRMAR
        binding.botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /*
                NavDirections action = SimularPlazoFragmentDirections.actionSimularPlazoFragmentToConstituirPlazoFragment()
                        .setCapital(binding.editCapital.getText().toString())
                        .setDias(binding.seekDias.getProgress()*30);
                */
                viewModel2.capital = binding.editCapital.getText().toString();
                viewModel2.dias = binding.seekDias.getProgress()*30;
                NavDirections action = SimularPlazoFragmentDirections.actionSimularPlazoFragmentToConstituirPlazoFragment();
                Navigation.findNavController(v).navigate(action);
            }
        });

    }


    /*  La tasa nominal anual es una ganancia que se calcula sobre el monto invertido inicial,
        mientras que la tasa efectiva suma las ganancias mensuales al monto inicial, haciendo que
        los ingresos sean mayores pues se calculan sobre un capital mayor.
        La relacion entre tasa anual y efectiva esta dada por:
        TEA = (1 + TNA/100*12)**MESES
     */

    private void calcular()
    {
        //Calcula y actualiza los valores del simulador
        double capital = binding.editCapital.getText().length() > 0 ? Utilities.editableToDouble(binding.editCapital.getText()) : -1;
        double tna = binding.editTNA.getText().length() > 0 ? Utilities.editableToDouble(binding.editTNA.getText()) : -1;
        int meses = binding.seekDias.getProgress();
        int dias = meses*30;

        double intereses = 0;
        double monto_total = 0;
        double monto_total_anual = 0;

        if(capital > -1 && tna > -1)
        {
            intereses = viewModel.calcularIntereses(capital, tna, meses);
            monto_total = capital + intereses;
            monto_total_anual = capital + viewModel.calcularIntereses(capital,tna,12);
            binding.botonConfirmar.setEnabled(true);

        } else {
            binding.botonConfirmar.setEnabled(false);
            tna = 0;
            capital = 0;
        }

        binding.txtDiasSeek.setText(meses + " Meses (" + dias + " días)");
        binding.txtDiasSimulador.setText(dias + " días");
        binding.txtCapitalSimulador.setText("$ " + Utilities.round2(capital));
        binding.txtInteresesGanados.setText("$ " + Utilities.round2(intereses));
        binding.txtMontoTotal.setText("$ " + Utilities.round2(monto_total));
        binding.txtMontoTotalAnual.setText("$ " + Utilities.round2(monto_total_anual));


    }


}