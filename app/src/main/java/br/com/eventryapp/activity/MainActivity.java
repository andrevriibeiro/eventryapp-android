package br.com.eventryapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.eventryapp.R;
import br.com.eventryapp.model.Usuario;
import br.com.eventryapp.util.Functions;

public class MainActivity extends AppCompatActivity {

    private Button mButtonOrganizador;
    private Button mButtonConvidado;
    private Button mButtonSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Usuario usuario = Functions.getUsuarioLogado(MainActivity.this);

        if (usuario.getId()==null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        mButtonOrganizador = (Button) findViewById(R.id.button_organizador);
        mButtonConvidado = (Button) findViewById(R.id.button_convidado);
        mButtonSair = (Button) findViewById(R.id.button_sair);

        mButtonOrganizador.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrganizadorActivity.class);
                startActivity(intent);
            }
        });

        mButtonConvidado.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ConvidadoActivity.class);
                startActivity(intent);
            }
        });

        mButtonSair.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Functions.deletaUsuario(getApplicationContext());
                Usuario u = Functions.getUsuarioLogado(getApplicationContext());
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
