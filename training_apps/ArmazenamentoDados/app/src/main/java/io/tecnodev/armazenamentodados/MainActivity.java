package io.tecnodev.armazenamentodados;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import io.tecnodev.armazenamentodados.R;

public class MainActivity extends AppCompatActivity {
    final String masc = "Masculino";
    final String fem = "Feminino";

    private TextView lblStatus;
    private EditText edtNome, edtIdade;
    private RadioButton radioMasculino, radioFeminino;
    private RadioGroup groupSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startAtributtes();
        //readPreferences();
        //readArquivoInterno();
        readArquivoExterno();
    }

    private void startAtributtes() {
        lblStatus = (TextView) findViewById(R.id.textViewStatus);

        edtNome = (EditText)  findViewById(R.id.editTextNome);
        edtIdade = (EditText)  findViewById(R.id.editTextIdade);

        radioMasculino = (RadioButton) findViewById(R.id.radioMasculino);
        radioFeminino = (RadioButton) findViewById(R.id.radioFeminino);

        groupSexo = (RadioGroup) findViewById(R.id.groupSexo);

        findViewById(R.id.btnSalvar).setOnClickListener(clickListenerExterno);
    }

    private void readPreferences() {
        SharedPreferences filePreferences = getPreferences(MODE_PRIVATE);

        String nome = filePreferences.getString("nome", "");
        String idade = filePreferences.getString("idade", "");
        String sexo = filePreferences.getString("sexo", masc);

        edtNome.setText(nome);
        edtIdade.setText(idade);

        if(sexo.equals(masc)) {
            radioMasculino.setChecked(true);
        } else if(sexo.equals(fem)){
            radioFeminino.setChecked(true);
        }
    }

    private void readArquivoInterno() {
        String nome = "";
        String idade = "";
        String sexo = "Masculino";

        try {
            File dir = getFilesDir();
            File file = new File(dir + "/dados.txt");

            if(file.exists()) {
                FileInputStream fis = openFileInput("dados.txt");
                byte[] buffer = new byte[(int) file.length()];

                while (fis.read(buffer) != -1) {
                    String texto = new String(buffer);

                    if(texto.indexOf("nome") != -1) {
                        int indice = texto.indexOf("=");
                        int indiceFinal = texto.indexOf("\n");

                        nome = texto.substring(indice + 1, indiceFinal);

                        texto = texto.substring(indiceFinal + 1);
                    }

                    if(texto.indexOf("idade") != -1) {
                        int indice = texto.indexOf("=");
                        int indiceFinal = texto.indexOf("\n");

                        idade = texto.substring(indice + 1, indiceFinal);

                        texto = texto.substring(indiceFinal + 1);
                    }

                    if(texto.indexOf("sexo") != -1) {
                        int indice = texto.indexOf("=");
                        int indiceFinal = texto.indexOf("\n");

                        sexo = texto.substring(indice + 1, indiceFinal);

                        texto = texto.substring(indiceFinal + 1);
                    }
                }
            }

        } catch (Exception e) {
            Log.e("Erro file: ", e.getMessage());
        }

        edtNome.setText(nome);
        edtIdade.setText(idade);

        if(sexo.equals(masc)) {
            radioMasculino.setChecked(true);
        } else if(sexo.equals(fem)){
            radioFeminino.setChecked(true);
        }
    }

    private void readArquivoExterno() {
        String nome = "";
        String idade = "";
        String sexo = "Masculino";

        try {
            String estado = Environment.getExternalStorageState();

            if(estado.equals(Environment.MEDIA_MOUNTED)) {
                File dir = getExternalFilesDir(null);
                File file = new File(dir + "/dados.txt");

                if(file.exists()) {
                    FileInputStream fis = new FileInputStream(file);
                    byte[] buffer = new byte[(int) file.length()];

                    while (fis.read(buffer) != -1) {
                        String texto = new String(buffer);

                        if(texto.indexOf("nome") != -1) {
                            int indice = texto.indexOf("=");
                            int indiceFinal = texto.indexOf("\n");

                            nome = texto.substring(indice + 1, indiceFinal);

                            texto = texto.substring(indiceFinal + 1);
                        }

                        if(texto.indexOf("idade") != -1) {
                            int indice = texto.indexOf("=");
                            int indiceFinal = texto.indexOf("\n");

                            idade = texto.substring(indice + 1, indiceFinal);

                            texto = texto.substring(indiceFinal + 1);
                        }

                        if(texto.indexOf("sexo") != -1) {
                            int indice = texto.indexOf("=");
                            int indiceFinal = texto.indexOf("\n");

                            sexo = texto.substring(indice + 1, indiceFinal);

                            texto = texto.substring(indiceFinal + 1);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("Erro file: ", e.getMessage());
        }

        edtNome.setText(nome);
        edtIdade.setText(idade);

        if(sexo.equals(masc)) {
            radioMasculino.setChecked(true);
        } else if(sexo.equals(fem)){
            radioFeminino.setChecked(true);
        }
    }

    private void salvarArquivoInterno(String nome, String sexo, String idade) {
        String dados = "";
        dados += "nome=" + nome;
        dados += "\n";
        dados += "idade=" + idade;
        dados += "\n";
        dados += "sexo=" + sexo;
        dados += "\n";

        try {
            FileOutputStream fos = openFileOutput("dados.txt", MODE_PRIVATE);
            fos.write(dados.getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e("Erro file: ", e.getMessage());
        }
    }

    private void salvarArquivoExterno(String nome, String sexo, String idade) {
        String dados = "";
        dados += "nome=" + nome;
        dados += "\n";
        dados += "idade=" + idade;
        dados += "\n";
        dados += "sexo=" + sexo;
        dados += "\n";

        try {
            String estado = Environment.getExternalStorageState();

            if(estado.equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(getExternalFilesDir(null), "/dados.txt");

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(dados.getBytes());
                fos.close();
            } else {
                Toast.makeText(this, "Não foi possível usar o cartão de memória => (" + estado + ")", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("Erro file: ", e.getMessage());
        }
    }

    private View.OnClickListener clickListenerInterno = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nome = edtNome.getText().toString();
            String idade = edtIdade.getText().toString();

            String sexo = "";

            if(groupSexo.getCheckedRadioButtonId() != -1) {
                if(groupSexo.getCheckedRadioButtonId() == R.id.radioMasculino) {
                    sexo = masc;
                } else if(groupSexo.getCheckedRadioButtonId() == R.id.radioFeminino) {
                    sexo = fem;
                }

                salvarArquivoInterno(nome, sexo, idade);

                lblStatus.setText("Status: Dados salvos - Interno!!");
            } else {
                Toast.makeText(MainActivity.this, "Marque alguma opção de sexo", Toast.LENGTH_LONG).show();
                return;
            }
        }
    };

    private View.OnClickListener clickListenerExterno = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nome = edtNome.getText().toString();
            String idade = edtIdade.getText().toString();

            String sexo = "";

            if(groupSexo.getCheckedRadioButtonId() != -1) {
                if(groupSexo.getCheckedRadioButtonId() == R.id.radioMasculino) {
                    sexo = masc;
                } else if(groupSexo.getCheckedRadioButtonId() == R.id.radioFeminino) {
                    sexo = fem;
                }

                salvarArquivoExterno(nome, sexo, idade);

                lblStatus.setText("Status: Dados salvos - Externo!!");
            } else {
                Toast.makeText(MainActivity.this, "Marque alguma opção de sexo", Toast.LENGTH_LONG).show();
                return;
            }
        }
    };

    private View.OnClickListener clickListenerSharedPreferences = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String nome = edtNome.getText().toString();
            String idade = edtIdade.getText().toString();

            String sexo = "";

            if(groupSexo.getCheckedRadioButtonId() != -1) {
                if(groupSexo.getCheckedRadioButtonId() == R.id.radioMasculino) {
                    sexo = masc;
                } else if(groupSexo.getCheckedRadioButtonId() == R.id.radioFeminino) {
                    sexo = fem;
                }

                SharedPreferences filePreferences = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = filePreferences.edit();

                editor.putString("nome", nome);
                editor.putString("idade", idade);
                editor.putString("sexo", sexo);

                editor.commit();

                lblStatus.setText("Status: Preferências salvas com sucesso!!");
            } else {
                Toast.makeText(MainActivity.this, "Marque alguma opção de sexo", Toast.LENGTH_LONG).show();
                return;
            }
        }
    };
}