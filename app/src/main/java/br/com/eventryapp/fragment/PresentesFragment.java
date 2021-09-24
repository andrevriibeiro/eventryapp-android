package br.com.eventryapp.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.com.eventryapp.R;
import br.com.eventryapp.adapter.PresentesAdapter;
import br.com.eventryapp.manager.EventoManager;
import br.com.eventryapp.manager.PresenteConvidadoManager;
import br.com.eventryapp.model.Evento;
import br.com.eventryapp.model.EventoPresente;
import br.com.eventryapp.model.Presente;
import br.com.eventryapp.service.listener.EventoServiceListener;
import br.com.eventryapp.service.listener.PresenteServiceListener;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class PresentesFragment extends Fragment {

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PresentesAdapter mPresentesAdapter;
    private Evento eventoInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_presentes, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.colorPrimary, R.color.black);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            eventoInfo = bundle.getParcelable("evento");
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.presentes_recyclerView);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.carregando_dados));
        progressDialog.show();

        PresenteConvidadoManager presenteConvidadoManager = new PresenteConvidadoManager(getContext());
        presenteConvidadoManager.getAllPresentesByEvento(eventoInfo.getId(), new PresenteServiceListener() {
            @Override
            public void onSuccess(List<Presente> presentes) {
                eventoInfo.getPresentes().clear();
                eventoInfo.getPresentes().addAll(presentes);
                mLayoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(mLayoutManager);

                mPresentesAdapter = new PresentesAdapter(eventoInfo);
                recyclerView.setAdapter(mPresentesAdapter);
                mPresentesAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFail(Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(view.getContext(), view.getContext().getString(R.string.error_connection), Toast.LENGTH_LONG).show();
            }
        });

        /**
         * A função abaixo é responsavel por adicionar novos Presentes na lista de Presentes do Evento
         *
         * */
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.novo_presente);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // custom dialog
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog_add_presente);

                Button mButtonSalvar = (Button) dialog.findViewById(R.id.button_salvar);
                Button mButtonCancelar = (Button) dialog.findViewById(R.id.button_cancelar);
                final EditText mEditTextNomePresente = (EditText) dialog.findViewById(R.id.edit_text_nome_presente);
                final EditText mEditTextDescPresente = (EditText) dialog.findViewById(R.id.edit_text_des_presente);

                mButtonSalvar.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {

                         final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                         progressDialog.setMessage(getString(R.string.add_presente));
                         progressDialog.show();

                         String nome_presente = mEditTextNomePresente.getText().toString();
                         String desc_presente = mEditTextDescPresente.getText().toString();

                         if(nome_presente.isEmpty()){
                             mEditTextNomePresente.setError(getString(R.string.error_field));
                         }else{
                             EventoPresente eventoPresente = new EventoPresente();
                             Presente novo_presente = new Presente();
                             novo_presente.setNomepresente(nome_presente);
                             novo_presente.setDescricao(desc_presente);
                             novo_presente.setConfirmado(false);
                             eventoPresente.setEvento(eventoInfo);
                             eventoPresente.setPresente(novo_presente);

                             EventoManager eventoManager = new EventoManager(v.getContext());
                             eventoManager.cadastrarPresente(eventoPresente, new EventoServiceListener() {

                                 @Override
                                 public void onSuccess(Evento evento) {
                                     progressDialog.dismiss();
                                     eventoInfo.getPresentes().clear();
                                     eventoInfo.getPresentes().addAll(evento.getPresentes());

                                     mLayoutManager = new LinearLayoutManager(getContext());
                                     recyclerView.setLayoutManager(mLayoutManager);

                                     mPresentesAdapter = new PresentesAdapter(eventoInfo);
                                     recyclerView.setAdapter(mPresentesAdapter);
                                     mPresentesAdapter.notifyDataSetChanged();

                                     dialog.dismiss();
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

                mButtonCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        /**
         *  Botão responavel pr chamar o metodo refresh que irá atualziar a lista de presentes
         *
         * */
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        return view;
    }

    /**
     * Metodo responsavel por atualizar a lista de presentes quando feito o swipe na listagem de presentes
     * */
    public void refresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PresenteConvidadoManager presenteConvidadoManager = new PresenteConvidadoManager(getContext());
                presenteConvidadoManager.getAllPresentesByEvento(eventoInfo.getId(), new PresenteServiceListener() {
                    @Override
                    public void onSuccess(List<Presente> presentes) {
                        eventoInfo.getPresentes().clear();
                        eventoInfo.getPresentes().addAll(presentes);
                        mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);

                        mPresentesAdapter = new PresentesAdapter(eventoInfo);
                        recyclerView.setAdapter(mPresentesAdapter);
                        mPresentesAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFail(Throwable t) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        },1000);
    }
}