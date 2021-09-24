package br.com.eventryapp.manager;

import android.content.Context;

import java.util.List;

import br.com.eventryapp.R;
import br.com.eventryapp.model.Convidado;
import br.com.eventryapp.model.Presente;
import br.com.eventryapp.service.EventoServiceInterface;
import br.com.eventryapp.service.ServiceGenerator;
import br.com.eventryapp.service.listener.ConvidadoServiceListener;
import br.com.eventryapp.service.listener.PresenteServiceListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenteConvidadoManager {

    private Context context;

    public PresenteConvidadoManager(Context context) {
        this.context = context;
    }

    public void getAllPresentesByEvento(int eventoID, final PresenteServiceListener listener) {
        final EventoServiceInterface eventoServiceInterface = ServiceGenerator.createService(EventoServiceInterface.class);
        Call<List<Presente>> call = eventoServiceInterface.getPresenteByEvento(eventoID);
        call.enqueue(new Callback<List<Presente>>() {

            @Override
            public void onResponse(Call<List<Presente>> call, Response<List<Presente>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Presente>> call, Throwable t) {
                context.getString(R.string.error_connection);
            }
        });
    }

    public void getAllConvidadosByEvento(int eventoID, final ConvidadoServiceListener listener) {
        final EventoServiceInterface eventoServiceInterface = ServiceGenerator.createService(EventoServiceInterface.class);
        Call<List<Convidado>> call = eventoServiceInterface.getConvidadoByEvento(eventoID);
        call.enqueue(new Callback<List<Convidado>>() {

            @Override
            public void onResponse(Call<List<Convidado>> call, Response<List<Convidado>> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<List<Convidado>> call, Throwable t) {
                context.getString(R.string.error_connection);
            }
        });
    }
}
