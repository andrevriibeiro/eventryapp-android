package br.com.eventryapp.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.eventryapp.R;
import br.com.eventryapp.manager.EventoManager;
import br.com.eventryapp.model.Evento;
import br.com.eventryapp.service.listener.EventoServiceListener;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class EditarEventoFragment extends Fragment {

    private Evento evento;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_editar_evento, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            evento = bundle.getParcelable("evento");
        }

        parsearEndereco();

        mEditTextNomeEvento = (EditText) view.findViewById(R.id.edit_text_nome_evento);
        mEditTextDescricaoEvento = (EditText) view.findViewById(R.id.edit_text_desc_evento);
        mEditTextLocalizacao = (EditText) view.findViewById(R.id.edit_text_localização);
        mEditTextDataEvento = (EditText)  view.findViewById(R.id.edit_text_data_evento);
        mEditTextHoraEvento = (EditText) view.findViewById(R.id.edit_text_hora_evento);
        mButtonSalvar = (Button) view.findViewById(R.id.button_salvar);

        if(evento!=null){
            mEditTextNomeEvento.setText(evento.getEventonome());
            mEditTextDescricaoEvento.setText(evento.getDescricao());
            mEditTextLocalizacao.setText(evento.getLocalizacao());
            mEditTextDataEvento.setText(evento.getDataevento());
            mEditTextHoraEvento.setText(evento.getHoraevento());
            evento.getConvidados().clear();
        }

        mEditTextLocalizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // custom dialog
                final Dialog dialog = new Dialog(v.getContext());
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
                final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage(getString(R.string.message_register));
                progressDialog.show();

                evento.setEventonome(mEditTextNomeEvento.getText().toString());
                evento.setDescricao(mEditTextDescricaoEvento.getText().toString());
                evento.setLocalizacao(mEditTextLocalizacao.getText().toString());
                evento.setDataevento(mEditTextDataEvento.getText().toString());
                evento.setHoraevento(mEditTextHoraEvento.getText().toString());

                EventoManager eventoManager = new EventoManager(v.getContext());
                eventoManager.editarEvento(evento, new EventoServiceListener() {

                    @Override
                    public void onSuccess(Evento evento) {
                        progressDialog.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                        alert.setTitle(getString(R.string.evento_atualizado));
                        alert.setMessage(R.string.evento_info_atualiado);
                        alert.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alert.show();
                    }

                    @Override
                    public void onSuccess(Response response) {}

                    @Override
                    public void onSucess(ResponseBody responseBody) {}

                    @Override
                    public void onSuccess(List<Evento> eventos) {}

                    @Override
                    public void onFail(Throwable t) {}
                });
            }
        });

        return view;
    }

    private void parsearEndereco(){
        String localizacao [] = evento.getLocalizacao().split(",");
        String numero_bairro [] = localizacao[1].split(" - ");
        endereco = localizacao[0];
        numero = numero_bairro[0];
        bairro = numero_bairro[1];
        cidade = localizacao[2];
    }
}
