package com.example.bancoutn.utilities;

import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

public abstract class PlazoAlertDialog {


    public static AlertDialog crear(FragmentActivity activity, String capital, String moneda, int dias, String nombre, String apellido)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Tu plazo fijo de " +  capital + " " + moneda + " por " + dias + " días ha sido constituido").setTitle("Felicitaciones " + nombre + " " + apellido + "!");
        builder.setPositiveButton("Piola!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        return builder.create();

    }

}
