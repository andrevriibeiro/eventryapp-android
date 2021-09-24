package br.com.eventryapp.manager;

import android.content.Context;

import br.com.eventryapp.R;
import br.com.eventryapp.model.Usuario;
import br.com.eventryapp.service.ServiceGenerator;
import br.com.eventryapp.service.UsuarioServiceInterface;
import br.com.eventryapp.service.listener.UsuarioServiceListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioManager {

    private Context context;

    public UsuarioManager(Context context) {
        this.context = context;
    }

    public void login(Usuario usuario, final UsuarioServiceListener listener) {
        final UsuarioServiceInterface usuarioService = ServiceGenerator.createService(UsuarioServiceInterface.class);
        Call<Usuario> call = usuarioService.login(usuario);
        call.enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                listener.onSuccess(response);
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                context.getString(R.string.error_connection);
            }
        });
    }

    public void cadastrarUsuario(Usuario usuario, final UsuarioServiceListener listener) {
        final UsuarioServiceInterface usuarioService = ServiceGenerator.createService(UsuarioServiceInterface.class);
        Call<Usuario> call = usuarioService.create(usuario);
        call.enqueue(new Callback<Usuario>() {

            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                context.getString(R.string.error_connection);
            }
        });
    }
}
