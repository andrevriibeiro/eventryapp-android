package br.com.eventryapp.service.listener;

import java.util.List;

import br.com.eventryapp.model.Convidado;

public interface ConvidadoServiceListener {

    void onSuccess(List<Convidado> convidados);

    void onFail(Throwable t);
}
