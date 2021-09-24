package br.com.eventryapp.service;

import java.util.List;

import br.com.eventryapp.model.Convidado;
import br.com.eventryapp.model.Evento;
import br.com.eventryapp.model.EventoPresente;
import br.com.eventryapp.model.Presente;
import br.com.eventryapp.util.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EventoServiceInterface {

    @POST(Constants.CRIAR_EVENTO)
    Call<Evento> create(@Body Evento evento);

    @POST(Constants.EDITAR_EVENTO)
    Call<Evento> editar(@Body Evento evento);

    @POST(Constants.CRIAR_EVENTO_PRESENTE)
    Call<Evento> createPresente(@Body EventoPresente eventoPresente);

    @GET(Constants.PRESENTE_BY_EVENTO)
    Call<List<Presente>> getPresenteByEvento(@Query("id") int usuarioId);

    @GET(Constants.CONVIDADO_BY_EVENTO)
    Call<List<Convidado>> getConvidadoByEvento(@Query("id") int usuarioId);

    @GET(Constants.GET_EVENTOS_USUARIO)
    Call<List<Evento>> getEventosByUsuario(@Query("id") int usuarioId);

    @GET(Constants.DELETAR_EVENTO)
    Call<ResponseBody> deletarEvento(@Query("id") int idEvento);

    @POST(Constants.DELETAR_EVENTO_PRESENTE)
    Call<ResponseBody> deletarEventoPresente(@Body EventoPresente eventoPresente);
}
