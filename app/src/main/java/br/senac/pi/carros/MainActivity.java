package br.senac.pi.carros;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.senac.pi.carros.domain.Carro;
import br.senac.pi.carros.domain.CarrosDb;

public class MainActivity extends AppCompatActivity {
    CarrosDb carrosDb;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carrosDb = new CarrosDb(this);

        findViewById(R.id.cad).setOnClickListener(cadastrarCarro());
        findViewById(R.id.btnList).setOnClickListener(listagemCarros());
        //code
        /*Button btnCadastro = (Button) findViewById(R.id.cad);
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtCarro = (EditText) findViewById(R.id.txtCarro);
                EditText edtFabricante = (EditText) findViewById(R.id.txtFabricante);
                String nameCarro = edtCarro.getText().toString();
                String nameFabricante = edtFabricante.getText().toString();
                Carro carro = new Carro();
                carro.setNome(nameCarro);
                carro.setMarca(nameFabricante);
                CarrosDb carrosDB = new CarrosDb(MainActivity.this);
                if (carrosDB.save(carro) != -1) {
                    Toast.makeText(MainActivity.this, getString(R.string.sucesso), Toast.LENGTH_LONG).show();
                    edtCarro.setText("");
                    edtFabricante.setText("");
                    edtCarro.requestFocus();
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        });*/
        // code
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //findViewById(R.id.btnList).setOnClickListener(listagemCarros());

    }

    private View.OnClickListener cadastrarCarro(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v){
                database = carrosDb.getWritableDatabase();
                EditText edtNomeCarro = (EditText) findViewById(R.id.txtCarro);
                EditText edtMarcaCarro = (EditText) findViewById(R.id.txtFabricante);
                String nomeCarro = edtNomeCarro.getText().toString();
                String marcaCarro = edtMarcaCarro.getText().toString();
                ContentValues values = new ContentValues();
                values.put("nome", nomeCarro);
                values.put("marca", marcaCarro);
                long id = database.insert("carro",null,values);
                if (id != 0){
                    Toast.makeText(getApplicationContext(),getString(R.string.sucesso), Toast.LENGTH_LONG).show();
                    edtNomeCarro.setText("");
                    edtMarcaCarro.setText("");
                    edtNomeCarro.requestFocus();
                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.error), Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private View.OnClickListener listagemCarros(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListarCarrosActivity.class);
                startActivity(intent);
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
