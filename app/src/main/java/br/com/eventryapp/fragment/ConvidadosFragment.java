package br.com.eventryapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import br.com.eventryapp.R;
import br.com.eventryapp.adapter.ConvidadosAdapter;
import br.com.eventryapp.manager.PresenteConvidadoManager;
import br.com.eventryapp.model.Convidado;
import br.com.eventryapp.model.Evento;
import br.com.eventryapp.model.Usuario;
import br.com.eventryapp.service.listener.ConvidadoServiceListener;

public class ConvidadosFragment extends Fragment {

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ConvidadosAdapter mConvidadosAdapter;
    private Evento evento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_convidados, container, false);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.colorPrimary, R.color.black);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            evento = bundle.getParcelable("evento");
        }

        carregarConvidados();

        recyclerView = (RecyclerView) view.findViewById(R.id.convidados_recyclerView);
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.carregando_dados));
        progressDialog.show();

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);

        mConvidadosAdapter = new ConvidadosAdapter(evento.getConvidados());
        recyclerView.setAdapter(mConvidadosAdapter);

        progressDialog.dismiss();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        return view;
    }

    public void refresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                carregarConvidados();
                mConvidadosAdapter = new ConvidadosAdapter(evento.getConvidados());
                recyclerView.setAdapter(mConvidadosAdapter);
                mSwipeRefreshLayout.setRefreshing(false);

                /*PresenteConvidadoManager presenteConvidadoManager = new PresenteConvidadoManager(getContext());
                presenteConvidadoManager.getAllConvidadosByEvento(evento.getId(), new ConvidadoServiceListener() {

                    @Override
                    public void onSuccess(List<Convidado> convidados) {
                        mLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(mLayoutManager);

                        mConvidadosAdapter = new ConvidadosAdapter(convidados);
                        recyclerView.setAdapter(mConvidadosAdapter);
                        mConvidadosAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFail(Throwable t) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });*/
            }
        },1000);
    }

    public void carregarConvidados(){
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNome("José Teixeira");

        Usuario usuario1 = new Usuario();
        usuario1.setId(2);
        usuario1.setNome("Isabelly");

        Usuario usuario2 = new Usuario();
        usuario2.setId(3);
        usuario2.setNome("Rodrigo");

        Usuario usuario3 = new Usuario();
        usuario3.setId(4);
        usuario3.setNome("Rebeca Oliveira");

        Usuario usuario4 = new Usuario();
        usuario4.setId(5);
        usuario4.setNome("Wagner da Silva Oliveira");

        Convidado convidado = new Convidado();
        convidado.setId(1);
        convidado.setUsuario(usuario);
        convidado.setStatusconfirmacao("Confirmado");

        Convidado convidado1 = new Convidado();
        convidado1.setId(2);
        convidado1.setUsuario(usuario1);
        convidado1.setStatusconfirmacao("Talvez comparecerei");

        Convidado convidado2 = new Convidado();
        convidado2.setId(3);
        convidado2.setUsuario(usuario2);
        convidado2.setStatusconfirmacao("Não Poderei Comparecer");

        Convidado convidado3 = new Convidado();
        convidado3.setId(4);
        convidado3.setUsuario(usuario3);
        convidado3.setStatusconfirmacao("Confirmado");

        Convidado convidado4 = new Convidado();
        convidado4.setId(5);
        convidado4.setUsuario(usuario4);
        convidado4.setStatusconfirmacao("Confirmado");

        List<Convidado> convidados = new ArrayList<>();
        convidados.add(convidado);
        convidados.add(convidado1);
        convidados.add(convidado2);
        convidados.add(convidado3);
        convidados.add(convidado4);

        evento.setConvidados(convidados);
    }
}
