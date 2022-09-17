package com.example.bancoutn.viewmodels;


import androidx.lifecycle.ViewModel;

public class ConstituirPlazoViewModel extends ViewModel {

    public String nombre;
    public String apellido;
    public String moneda;
    public String capital;
    public int dias;

    public ConstituirPlazoViewModel(){
        nombre = null;
        apellido = null;
        capital = null;
        dias = -1;
    }

}
