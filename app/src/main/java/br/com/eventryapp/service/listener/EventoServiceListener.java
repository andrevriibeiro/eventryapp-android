package br.com.eventryapp.service.listener;

import java.util.List;
import br.com.eventryapp.model.Evento;
import okhttp3.ResponseBody;
import retrofit2.Response;

public interface EventoServiceListener {

    void onSuccess(Evento evento);

    void onSuccess(Response response);

    void onSucess(ResponseBody responseBody);

    void onSuccess(List<Evento> eventos);

    void onFail(Throwable t);
}
