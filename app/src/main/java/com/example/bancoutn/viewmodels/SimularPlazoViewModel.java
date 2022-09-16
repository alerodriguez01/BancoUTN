package com.example.bancoutn.viewmodels;

import androidx.lifecycle.ViewModel;

public class SimularPlazoViewModel extends ViewModel {


    public double calcularTEA(double tna)
    {
        return Math.pow((1 + tna/1200),12)*100 - 100;
    }

    public double calcularTNA(double tea)
    {
        return 1200*(Math.pow((1 + tea/100),1f/12)-1);
    }

    public double calcularIntereses(double monto, double tna, int meses)
    {
        return monto * (Math.pow((1 + tna/1200),meses) - 1);
    }

}
