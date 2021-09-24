package br.com.eventryapp.service;

import com.google.gson.internal.LinkedTreeMap;

import br.com.eventryapp.model.Usuario;
import br.com.eventryapp.util.Constants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface UsuarioServiceInterface {

    @POST(Constants.LOGIN)
    Call<Usuario> login(@Body Usuario usuario);


    @POST(Constants.CRIAR_USUARIO)
    Call<Usuario> create(@Body Usuario usuario);

    /*@POST(Constants.USUARIO + "/update")
    Call<LinkedTreeMap> update(@Body Usuario usuario);*/
}