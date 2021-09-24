package br.com.eventryapp.manager;

import android.content.Context;

import java.util.List;

import br.com.eventryapp.R;
import br.com.eventryapp.model.Evento;
import br.com.eventryapp.model.EventoPresente;
import br.com.eventryapp.service.EventoServiceInterface;
import br.com.eventryapp.service.ServiceGenerator;
import br.com.eventryapp.service.listener.EventoServiceListener;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventoManager {

    private Context context;

    public EventoManager(Context context) {
        this.context = context;
    }

    public void cadastrarEvento(Evento evento, final EventoServiceListener listener) {
        final EventoServiceInterface eventoServiceInterface = ServiceGenerator.createService(EventoServiceInterface.class);
        Call<Evento> call = eventoServiceInterface.create(evento);
        call.enqueue(new Callback<Evento>() {

            @Override
            public void onResponse(Call<Evento> call, Response<Evento> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Evento> call, Throwable t) {
                context.getString(R.string.error_connection);
            }
        });
    }

    public void editarEvento(Evento evento, final EventoServiceListener listener) {
        final EventoServiceInterface eventoServiceInterface = ServiceGenerator.createService(EventoServiceInterface.class);
        Call<Evento> call = eventoServiceInterface.editar(evento);
        call.enqueue(new Callback<Evento>() {

            @Override
            public void onResponse(Call<Evento> call, Response<Evento> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Evento> call, Throwable t) {
                context.getString(R.string.error_connection);
            }
        });
    }

    public void cadastrarPresente(EventoPresente eventoPresente, final EventoServiceListener listener) {
        final EventoServiceInterface eventoServiceInterface = ServiceGenerator.createService(EventoServiceInterface.class);
        Call<Evento> call = eventoServiceInterface.createPresente(eventoPresente);
        call.enqueue(new Callback<Evento>() {

            @Override
            public void onResponse(Call<Evento> call, Response<Evento> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Evento> call, Throwable t) {
                context.getString(R.string.error_connection);
            }
        });
    }

    public void getEventosByUsuario(int usuarioId, final EventoServiceListener listener) {
        final EventoServiceInterface eventoServiceInterface = ServiceGenerator.createService(EventoServiceInterface.class);
        Call<List<Evento>> call = eventoServiceInterface.getEventosByUsuario(usuarioId);
        call.enqueue(new Callback<List<Evento>>() {

            @Override
            public void onResponse(Call<List<Evento>> call, Response<List<Evento>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Evento>> call, Throwable t) {
                context.getString(R.string.error_connection);
            }
        });
    }

    public void deletarEvento(int idEvento, final EventoServiceListener listener) {
        final EventoServiceInterface eventoServiceInterface = ServiceGenerator.createService(EventoServiceInterface.class);
        Call<ResponseBody> call = eventoServiceInterface.deletarEvento(idEvento);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                context.getString(R.string.error_connection);
            }
        });
    }


    public void deletarEventoPresente(EventoPresente eventoPresente, final EventoServiceListener listener) {
        final EventoServiceInterface eventoServiceInterface = ServiceGenerator.createService(EventoServiceInterface.class);
        Call<ResponseBody> call = eventoServiceInterface.deletarEventoPresente(eventoPresente);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                listener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                context.getString(R.string.error_connection);
            }
        });
    }
}
