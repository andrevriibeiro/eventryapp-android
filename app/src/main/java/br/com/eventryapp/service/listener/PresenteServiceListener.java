package br.com.eventryapp.service.listener;

import java.util.List;

import br.com.eventryapp.model.Presente;

public interface PresenteServiceListener {

    void onSuccess(List<Presente> presentes);

    void onFail(Throwable t);
}
