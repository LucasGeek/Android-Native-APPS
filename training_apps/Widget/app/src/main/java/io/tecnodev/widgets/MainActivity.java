package io.tecnodev.widgets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView txtResultado;
    CheckBox checkBoxAndroidB, checkBoxAndroidI, checkBoxAndroidA;
    RadioGroup radioGroupInfo;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exemplo_qualifier);
    }

    @Override
    public void onClick(View view) {
        String resultado = "Curso de interesse: ";

        txtResultado = (TextView) findViewById(R.id.txtResultado);
        checkBoxAndroidB = (CheckBox) findViewById(R.id.checkboxAndroidB);
        checkBoxAndroidI = (CheckBox) findViewById(R.id.checkboxAndroidI);
        checkBoxAndroidA = (CheckBox) findViewById(R.id.checkboxAndroidA);
        radioGroupInfo = (RadioGroup) findViewById(R.id.radioGroupInfo);

        if (checkBoxAndroidB.isChecked()) {
            resultado += checkBoxAndroidB.getText().toString() + ", ";
        }

        if (checkBoxAndroidI.isChecked()) {
            resultado += checkBoxAndroidI.getText().toString() + ", ";
        }

        if (checkBoxAndroidA.isChecked()) {
            resultado += checkBoxAndroidA.getText().toString() + ", ";
        }

        if(radioGroupInfo.getCheckedRadioButtonId() != -1) {
            RadioButton radioButtonSelecionado = (RadioButton) findViewById(radioGroupInfo.getCheckedRadioButtonId());

            resultado += "Receber mais informações? " + radioButtonSelecionado.getText().toString();
        }

        txtResultado.setText(resultado);
    }
}