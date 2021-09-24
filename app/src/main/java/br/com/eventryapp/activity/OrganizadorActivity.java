package br.com.eventryapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.eventryapp.R;
import br.com.eventryapp.adapter.EventosAdapter;
import br.com.eventryapp.manager.EventoManager;
import br.com.eventryapp.model.Evento;
import br.com.eventryapp.model.Usuario;
import br.com.eventryapp.service.DatabaseSQLiteService;
import br.com.eventryapp.service.listener.EventoServiceListener;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class OrganizadorActivity extends AppCompatActivity {

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView recyclerView;
    private EventosAdapter mEventosAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Evento> eventoList = new ArrayList<>();
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizador);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.colorPrimary, R.color.black);

        recyclerView = (RecyclerView) findViewById(R.id.eventos_recyclerView);
        final ProgressDialog progressDialog = new ProgressDialog(OrganizadorActivity.this);
        progressDialog.setMessage(getString(R.string.carregando_dados));
        progressDialog.show();

        DatabaseSQLiteService dao = new DatabaseSQLiteService(OrganizadorActivity.this);
        usuario = dao.getUsuario();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        EventoManager eventoManager = new EventoManager((OrganizadorActivity.this));
        eventoManager.getEventosByUsuario(usuario.getId(), new EventoServiceListener() {

            @Override
            public void onSuccess(Evento evento) { }

            @Override
            public void onSuccess(Response response) {

            }

            @Override
            public void onSucess(ResponseBody responseBody) {

            }

            @Override
            public void onSuccess(List<Evento> eventos) {
                eventoList.addAll(eventos);

                mLayoutManager = new LinearLayoutManager(OrganizadorActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);

                mEventosAdapter = new EventosAdapter(eventoList);
                recyclerView.setAdapter(mEventosAdapter);

                progressDialog.dismiss();
            }

            @Override
            public void onFail(Throwable t) {
                Toast.makeText(OrganizadorActivity.this,
                        OrganizadorActivity.this.getString(R.string.error_connection),
                        Toast.LENGTH_LONG).show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.novo_evento);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrganizadorActivity.this,
                        CriarEventoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void refresh(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                EventoManager eventoManager = new EventoManager((OrganizadorActivity.this));
                eventoManager.getEventosByUsuario(usuario.getId(), new EventoServiceListener() {

                    @Override
                    public void onSuccess(Evento evento) { }

                    @Override
                    public void onSuccess(Response response) {

                    }

                    @Override
                    public void onSucess(ResponseBody responseBody) {

                    }

                    @Override
                    public void onSuccess(List<Evento> eventos) {
                        eventoList.clear();
                        eventoList.addAll(eventos);

                        mLayoutManager = new LinearLayoutManager(OrganizadorActivity.this);
                        recyclerView.setLayoutManager(mLayoutManager);

                        mEventosAdapter = new EventosAdapter(eventoList);
                        recyclerView.setAdapter(mEventosAdapter);
                        mEventosAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFail(Throwable t) {
                        Toast.makeText(OrganizadorActivity.this,
                                OrganizadorActivity.this.getString(R.string.error_connection),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        },1000);
    }
}
