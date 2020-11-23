package io.tecnodev.widgets;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class JanelaDataFragmento extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Calendar cal = Calendar.getInstance();

        int ano = cal.get(cal.YEAR);
        int mes = cal.get(cal.MONTH);
        int dia = cal.get(cal.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, ano, mes, dia);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Toast toast = Toast
                .makeText(getActivity(), "Data: " + dayOfMonth + "/" + (month + 1) + "/" + year + "", Toast.LENGTH_LONG);
        toast.show();
    }
}
