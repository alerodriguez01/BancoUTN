package com.example.bancoutn.utilities;

import android.text.Editable;

public abstract class Utilities {

    public static String round2(Double valor)
    {
        float f = valor.floatValue();
        return String.format("%.02f", f);
    }

    public static Double editableToDouble(Editable valor)
    {
        return Double.parseDouble(String.valueOf(valor));
    }
}
