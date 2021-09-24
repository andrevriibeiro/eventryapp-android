package br.com.eventryapp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.eventryapp.R;
import br.com.eventryapp.manager.UsuarioManager;
import br.com.eventryapp.model.Usuario;
import br.com.eventryapp.service.listener.UsuarioServiceListener;
import br.com.eventryapp.util.Functions;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText mEditTextEmail = (EditText) findViewById(R.id.edit_text_email);
        final EditText mEditTextPassword = (EditText) findViewById(R.id.edit_text_password);

        Button buttonEnter = (Button) findViewById(R.id.button_enter);

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Functions.isconnected(getApplicationContext())) {
                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.error_connection), Toast.LENGTH_LONG).show();
                } else {
                    String email = mEditTextEmail.getText().toString();
                    String password = mEditTextPassword.getText().toString();
                    if (email.isEmpty() && password.isEmpty()) {
                        mEditTextEmail.setError(getString(R.string.error_field));
                        mEditTextPassword.setError(getString(R.string.error_field));
                    } else if (email.isEmpty()) {
                        mEditTextEmail.setError(getString(R.string.error_field));
                    } else if (password.isEmpty()) {
                        mEditTextPassword.setError(getString(R.string.error_field));
                    } else {
                        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                        progressDialog.setMessage(getString(R.string.message_login));
                        progressDialog.show();

                        Usuario usuario = new Usuario();
                        usuario.setEmail(email);
                        usuario.setSenha(password);

                        UsuarioManager usuarioManager = new UsuarioManager(getApplication());
                        usuarioManager.login(usuario, new UsuarioServiceListener() {

                            @Override
                            public void onSuccess(Usuario u) {}

                            @Override
                            public void onSuccess(Response response) {
                                Usuario usuario = null;

                                if(response.code()==202){
                                    usuario = (Usuario) response.body();
                                    Functions.saveUsario(getApplicationContext(), usuario);

                                    progressDialog.dismiss();

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);

                                }else{
                                    progressDialog.dismiss();
                                    AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                                    alert.setTitle(getString(R.string.message_alert));
                                    alert.setMessage(R.string.incorrect_login);
                                    alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    });
                                    alert.show();
                                }
                            }

                            @Override
                            public void onFail(Throwable t) {
                                progressDialog.dismiss();
                                AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
                                alert.setTitle(getString(R.string.message_alert));
                                alert.setMessage(R.string.incorrect_login);
                                alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                alert.show();
                            }
                        });
                    }
                }
            }
        });

        TextView textViewCreateAccount = (TextView) findViewById(R.id.text_view_register);

        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
