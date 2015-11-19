package br.senac.pi.carros;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import br.senac.pi.carros.domain.Carro;
import br.senac.pi.carros.domain.CarrosDb;

public class ListarCarrosActivity extends AppCompatActivity {

    private CursorAdapter dataSource;
    private SQLiteDatabase database;
    private static final String campos[] = {"nome","marca","_id"};
    ListView listView;
    CarrosDb carrosDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_carros);
        //MyCode
        listView = (ListView) findViewById(R.id.listView);
        carrosDb = new CarrosDb(this);
        database = carrosDb.getWritableDatabase();
        findViewById(R.id.btnListar).setOnClickListener(listar());
        listView.setOnItemClickListener(deletarItem());
        //MyCode
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
    }
    //MyCode
    private View.OnClickListener listar(){
        return new View.OnClickListener(){
            @Override
        public void onClick(View v){
                Cursor carros = database.query("carro", campos, null, null, null,null,null);
                if (carros.getCount() > 0){
                    dataSource = new SimpleCursorAdapter(ListarCarrosActivity.this,R.layout.row, carros, campos,new int[] {R.id.txtCarro, R.id.txtFabricante});
                    listView.setAdapter(dataSource);
                }else{
                    Toast.makeText(ListarCarrosActivity.this,getString(R.string.zero_registros), Toast.LENGTH_LONG).show();
                }
            }
        };

    }
    //Recupera item do banco pelo id e deleta
    private AdapterView.OnItemClickListener deletarItem(){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final long itemSelecinado = id;
                final int posicao = position;
                AlertDialog.Builder builder = new AlertDialog.Builder(ListarCarrosActivity.this);
                builder.setTitle("Pergunta");
                builder.setMessage("O quê deseja fazer?");
                builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String codigo;
                        Carro c = new Carro();
                        Cursor carro = database.query("carro",campos,null,null,null,null,null);
                        carro.moveToPosition(posicao);
                        codigo = carro.getString(carro.getColumnIndexOrThrow("_id"));
                        Intent intent = new Intent(getApplicationContext(),AlteraCarroActivity.class);
                        intent.putExtra("id",codigo);
                        startActivity(intent);
                        finish();
                        //Toast.makeText(ListarCarrosActivity.this, "Clicou em Editar", Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.i("carro", "ID do item Selecionado: " + itemSelecinado);
                        Carro carro = new Carro();
                        carro.setId(itemSelecinado);
                        carrosDb.delete(carro);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        };
    }

}
