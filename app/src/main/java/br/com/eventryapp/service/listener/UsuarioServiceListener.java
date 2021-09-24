package br.com.eventryapp.service.listener;

import br.com.eventryapp.model.Usuario;
import retrofit2.Response;

public interface UsuarioServiceListener {

    void onSuccess(Response response);

    void onSuccess(Usuario usuario);

    void onFail(Throwable t);
}