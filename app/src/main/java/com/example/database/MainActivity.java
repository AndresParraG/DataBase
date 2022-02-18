package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemSelectedListener {

    private Button crear;
    private Button veure;
    private Button elim;
    private EditText titEdit;
    private EditText comEdit;
    private EditText titView;
    private EditText comView;
    private Spinner spinner;
    private ArrayAdapter spinnerAdapter;
    private ArrayList<Comentari> listaComments;
    private Comentari c;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titEdit = (EditText) findViewById(R.id.titolNouComentari);
        comEdit = (EditText) findViewById(R.id.textNouComentari);
        titView = (EditText) findViewById(R.id.titView);
        comView = (EditText) findViewById(R.id.commView);
        titView.setEnabled(false);
        comView.setEnabled(false);

        crear = (Button) findViewById(R.id.crear);
        veure = (Button) findViewById(R.id.veure);
        elim = (Button) findViewById(R.id.eliminar);
        crear.setOnClickListener(this);
        veure.setOnClickListener(this);
        elim.setOnClickListener(this);

        db = new DataBase(this);

        spinner = (Spinner) findViewById(R.id.spinner);
        listaComments = db.getComentaris();

        spinnerAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, listaComments);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.crear:
                db.addComment(titEdit.getText().toString(), comEdit.getText().toString());
                listaComments = db.getComentaris();
                spinnerAdapter = new ArrayAdapter(this,
                        android.R.layout.simple_spinner_dropdown_item, listaComments);
                spinner.setAdapter(spinnerAdapter);
                titEdit.setText("");
                comEdit.setText("");
                break;
            case R.id.veure:
                if (c != null) {
                    titView.setText(c.getTitol());
                    comView.setText(c.getComment());
                }
                break;
            case R.id.eliminar:
                if (c != null) {
                    db.removeProdct(c.getId());
                    listaComments = db.getComentaris();
                    spinnerAdapter = new ArrayAdapter(this,
                            android.R.layout.simple_spinner_dropdown_item, listaComments);
                    spinner.setAdapter(spinnerAdapter);
                    titView.setText("");
                    comView.setText("");
                    c = null;

                }
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.spinner) {
            if (listaComments.size() > 0) {
                c = listaComments.get(position);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}