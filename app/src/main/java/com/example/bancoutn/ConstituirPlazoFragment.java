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
import android.widget.AdapterView;
import com.example.bancoutn.databinding.FragmentConstituirPlazoBinding;
import com.example.bancoutn.utilities.PlazoAlertDialog;
import com.example.bancoutn.viewmodels.ConstituirPlazoViewModel;

import java.util.Objects;

public class ConstituirPlazoFragment extends Fragment {

    private FragmentConstituirPlazoBinding binding;
    private ConstituirPlazoViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentConstituirPlazoBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view,savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(ConstituirPlazoViewModel.class);

        /*
        String capital = ConstituirPlazoFragmentArgs.fromBundle(getArguments()).getCapital();
        int dias = ConstituirPlazoFragmentArgs.fromBundle(getArguments()).getDias();
         */
        String capital = viewModel.capital;
        int dias = viewModel.dias;

        if(!Objects.equals(capital, "null") && dias > -1) binding.botonConstituir.setEnabled(true);

        String nombre = viewModel.nombre;
        String apellido = viewModel.apellido;

        if(!Objects.equals(nombre, null) && !Objects.equals(apellido, null)){
            binding.editNombre.setText(nombre);
            binding.editApellido.setText(apellido);
            binding.botonSimular.setEnabled(true);
        }

        binding.editNombre.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                binding.botonSimular.setEnabled(binding.editApellido.getText().length() > 0 && binding.editNombre.getText().length() > 0 );
            }
        });

        binding.editApellido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                binding.botonSimular.setEnabled(binding.editApellido.getText().length() > 0 && binding.editNombre.getText().length() > 0);
            }
        });

        binding.spMoneda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                viewModel.moneda = binding.spMoneda.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Boton SIMULAR
        binding.botonSimular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                viewModel.nombre = binding.editNombre.getText().toString();
                viewModel.apellido = binding.editApellido.getText().toString();
                NavDirections action = ConstituirPlazoFragmentDirections.actionConstituirPlazoFragmentToSimularPlazoFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        //Boton CONSTITUIR
        binding.botonConstituir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                PlazoAlertDialog.crear(getActivity(),capital,viewModel.moneda,dias,viewModel.nombre,viewModel.apellido).show();
            }
        });
    }

}