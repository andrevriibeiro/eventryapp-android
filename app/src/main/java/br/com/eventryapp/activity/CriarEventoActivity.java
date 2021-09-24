package br.com.eventryapp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;
import java.util.List;

import br.com.eventryapp.R;
import br.com.eventryapp.manager.EventoManager;
import br.com.eventryapp.model.Evento;
import br.com.eventryapp.service.listener.EventoServiceListener;
import br.com.eventryapp.util.Functions;
import br.com.eventryapp.util.Mask;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CriarEventoActivity extends AppCompatActivity {

    private EditText mEditTextNomeEvento;
    private EditText mEditTextDescricaoEvento;
    private EditText mEditTextLocalizacao;
    private EditText mEditTextDataEvento;
    private EditText mEditTextHoraEvento;
    private Button mButtonSalvar;
    private String endereco = "";
    private String bairro = "";
    private String cidade = "";
    private String numero = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_evento);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditTextNomeEvento = (EditText) findViewById(R.id.edit_text_nome_evento);
        mEditTextDescricaoEvento = (EditText) findViewById(R.id.edit_text_desc_evento);
        mEditTextLocalizacao = (EditText) findViewById(R.id.edit_text_localização);
        mEditTextDataEvento = (EditText)  findViewById(R.id.edit_text_data_evento);
        mEditTextHoraEvento = (EditText) findViewById(R.id.edit_text_hora_evento);
        mButtonSalvar = (Button) findViewById(R.id.button_register);

        mEditTextDataEvento.addTextChangedListener(Mask.insert("##/##/####", mEditTextDataEvento));
        //mEditTextHoraEvento.addTextChangedListener(Mask.insert("##:##", mEditTextHoraEvento));

        mEditTextLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // custom dialog
                final Dialog dialog = new Dialog(CriarEventoActivity.this);
                dialog.setContentView(R.layout.dialog_busca_endereco);

                Button mButtonSalvar = (Button) dialog.findViewById(R.id.button_salvar);
                Button mButtonCancelar = (Button) dialog.findViewById(R.id.button_cancelar);
                final EditText mEditTextEndereco = (EditText) dialog.findViewById(R.id.edit_text_endereco);
                final EditText mEditTextBairro = (EditText) dialog.findViewById(R.id.edit_text_bairro);
                final EditText mEditTextCidade = (EditText) dialog.findViewById(R.id.edit_text_cidade);
                final EditText mEditTextNumero = (EditText) dialog.findViewById(R.id.edit_text_numero);

                if(endereco!=null){
                    mEditTextEndereco.setText(endereco);
                    mEditTextBairro.setText(bairro);
                    mEditTextCidade.setText(cidade);
                    mEditTextNumero.setText(numero);
                }

                mButtonSalvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        endereco = mEditTextEndereco.getText().toString();
                        bairro = mEditTextBairro.getText().toString();
                        cidade = mEditTextCidade.getText().toString();
                        numero = mEditTextNumero.getText().toString();

                        if(endereco.isEmpty() && bairro.isEmpty() && cidade.isEmpty() && numero.isEmpty()) {
                            mEditTextEndereco.setError(getString(R.string.error_field));
                            mEditTextBairro.setError(getString(R.string.error_field));
                            mEditTextCidade.setError(getString(R.string.error_field));
                            mEditTextNumero.setError(getString(R.string.error_field));

                        }else if(endereco.isEmpty()) {
                            mEditTextEndereco.setError(getString(R.string.error_field));
                        }else if(bairro.isEmpty()) {
                            mEditTextBairro.setError(getString(R.string.error_field));
                        }else if(cidade.isEmpty()) {
                            mEditTextCidade.setError(getString(R.string.error_field));

                        }else if(numero.isEmpty()){
                            mEditTextNumero.setError(getString(R.string.error_field));
                        }else{
                            mEditTextLocalizacao.setText(endereco + ", " +
                                    "nº: " + numero + " - " + bairro + ", "+ cidade);

                            dialog.dismiss();

                        }
                    }
                });

                mButtonCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        mButtonSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /** Validando de os campos foram preenchidos */
                if(mEditTextNomeEvento.getText().toString().matches("") &&
                   mEditTextDescricaoEvento.getText().toString().matches("") &&
                   mEditTextLocalizacao.getText().toString().matches("") &&
                   mEditTextDataEvento.getText().toString().matches("") &&
                   mEditTextHoraEvento.getText().toString().matches("")){
                    mEditTextNomeEvento.setError(getString(R.string.error_field));
                    mEditTextDescricaoEvento.setError(getString(R.string.error_field));
                    mEditTextLocalizacao.setError(getString(R.string.error_field));
                    mEditTextDataEvento.setError(getString(R.string.error_field));
                    mEditTextHoraEvento.setError(getString(R.string.error_field));
                } else if(mEditTextNomeEvento.getText().toString().matches("")){
                    mEditTextNomeEvento.setError(getString(R.string.error_field));
                }else if(mEditTextDescricaoEvento.getText().toString().matches("")){
                    mEditTextDescricaoEvento.setError(getString(R.string.error_field));
                }else if(mEditTextLocalizacao.getText().toString().matches("")){
                    mEditTextLocalizacao.setError(getString(R.string.error_field));
                }else if(mEditTextDataEvento.getText().toString().matches("")){
                    mEditTextDataEvento.setError(getString(R.string.error_field));
                }else if(mEditTextHoraEvento.getText().toString().matches("")){
                    mEditTextHoraEvento.setError(getString(R.string.error_field));
                }else{
                    final ProgressDialog progressDialog = new ProgressDialog(CriarEventoActivity.this);
                    progressDialog.setMessage(getString(R.string.message_register));
                    progressDialog.show();

                    Evento evento = new Evento();

                    evento.setEventonome(mEditTextNomeEvento.getText().toString());
                    evento.setDescricao(mEditTextDescricaoEvento.getText().toString());
                    evento.setLocalizacao(mEditTextLocalizacao.getText().toString());
                    evento.setUsuario(Functions.getUsuarioLogado(CriarEventoActivity.this));
                    evento.setCodigoevento(Functions.geneateEventoCodigo());
                    evento.setDataevento(mEditTextDataEvento.getText().toString());
                    evento.setHoraevento(mEditTextHoraEvento.getText().toString());

                    EventoManager eventoManager = new EventoManager(CriarEventoActivity.this);
                    eventoManager.cadastrarEvento(evento, new EventoServiceListener() {

                        @Override
                        public void onSuccess(Evento evento) {
                            finish();
                            progressDialog.dismiss();
                            Intent intent = new Intent(getApplicationContext(), OrganizadorActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onSuccess(Response response) {

                        }

                        @Override
                        public void onSucess(ResponseBody responseBody) {

                        }

                        @Override
                        public void onSuccess(List<Evento> eventos) {}

                        @Override
                        public void onFail(Throwable t) {

                        }
                    });

                }
            }
        });
    }
}