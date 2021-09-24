package br.com.eventryapp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.eventryapp.R;
import br.com.eventryapp.manager.UsuarioManager;
import br.com.eventryapp.model.Usuario;
import br.com.eventryapp.service.listener.UsuarioServiceListener;
import br.com.eventryapp.util.Functions;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEditTextNomeCompleto;
    private EditText mEditTextEmail;
    private EditText mEditTextSenha;
    private EditText mEditTextConfirmaSenha;
    private Button mButtonCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditTextNomeCompleto = (EditText) findViewById(R.id.edit_text_nome_completo);
        mEditTextEmail= (EditText) findViewById(R.id.edit_text_email);
        mEditTextSenha = (EditText) findViewById(R.id.edit_text_senha);
        mEditTextConfirmaSenha = (EditText) findViewById(R.id.edit_text_confirme_senha);
        mButtonCadastrar = (Button) findViewById(R.id.button_register);

        mButtonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Functions.isconnected(getApplicationContext())) {
                    Toast.makeText(v.getContext(), v.getContext().getString(R.string.error_connection), Toast.LENGTH_LONG).show();
                } else {
                    String nome_completo = mEditTextNomeCompleto.getText().toString();
                    String email = mEditTextEmail.getText().toString();
                    String senha = mEditTextSenha.getText().toString();
                    String confirme_senha = mEditTextConfirmaSenha.getText().toString();

                    if (nome_completo.isEmpty() && email.isEmpty() && senha.isEmpty()) {
                        mEditTextNomeCompleto.setError(getString(R.string.error_field));
                        mEditTextEmail.setError(getString(R.string.error_field));
                        mEditTextSenha.setError(getString(R.string.error_field));
                    } else if (nome_completo.isEmpty()) {
                        mEditTextNomeCompleto.setError(getString(R.string.error_field));
                    } else if (email.isEmpty()) {
                        mEditTextEmail.setError(getString(R.string.error_field));
                    }else if(senha.isEmpty()){
                        mEditTextSenha.setError(getString(R.string.error_field));
                    }else if(confirme_senha.isEmpty()){
                        mEditTextConfirmaSenha.setError(getString(R.string.error_field));
                    } else if (!senha.equals(confirme_senha)){
                        mEditTextSenha.setError(getString(R.string.senha_diferente));
                        mEditTextConfirmaSenha.setError(getString(R.string.senha_diferente));
                    } else {
                        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                        progressDialog.setMessage(getString(R.string.message_register));
                        progressDialog.show();

                        Usuario usuario = new Usuario();
                        usuario.setNome(nome_completo);
                        usuario.setEmail(email);
                        usuario.setSenha(senha);

                        UsuarioManager usuarioManager = new UsuarioManager(getApplication());
                        usuarioManager.cadastrarUsuario(usuario, new UsuarioServiceListener() {

                            @Override
                            public void onSuccess(Response response) {
                            }

                            @Override
                            public void onSuccess(Usuario usuario) {
                                Functions.saveUsario(getApplicationContext(), usuario);
                                progressDialog.dismiss();

                                if(usuario.getId()!=null){
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                                    alert.setTitle(getString(R.string.message_alert));
                                    alert.setMessage(getString(R.string.usuario_ja_existe));
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
                                AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
                                alert.setTitle(getString(R.string.message_alert));
                                alert.setMessage(getString(R.string.usuario_ja_existe));
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
    }
}

