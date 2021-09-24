package br.com.eventryapp.adapter;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.eventryapp.R;

import br.com.eventryapp.manager.EventoManager;
import br.com.eventryapp.model.Evento;
import br.com.eventryapp.model.EventoPresente;
import br.com.eventryapp.model.Presente;
import br.com.eventryapp.service.listener.EventoServiceListener;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class PresentesAdapter extends RecyclerView.Adapter<PresentesAdapter.ViewHolder> {

    private Evento evento;

    public PresentesAdapter(Evento evento) {
        this.evento = evento;
    }

    @NonNull
    @Override
    public PresentesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.presente_item,
                parent, false);
        return new PresentesAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PresentesAdapter.ViewHolder holder, final int position) {
        final Presente presente = evento.getPresentes().get(position);

        holder.mTextViewNomePresente.setText(presente.getNomepresente());
        holder.mTextViewDescricaoPresente.setText(presente.getDescricao());

        holder.mImageViewRemovePresente.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EventoPresente eventoPresente = new EventoPresente();
                eventoPresente.setEvento(evento);
                eventoPresente.setPresente(presente);

                final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage(v.getContext().getString(R.string.removendo_presente));
                progressDialog.show();

                EventoManager eventoManager = new EventoManager(v.getContext());
                eventoManager.deletarEventoPresente(eventoPresente, new EventoServiceListener() {
                    @Override
                    public void onSuccess(Evento evento) {}

                    @Override
                    public void onSuccess(Response response) {
                        evento.getPresentes().remove(presente);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,evento.getPresentes().size());
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onSucess(ResponseBody responseBody) {
                    }

                    @Override
                    public void onSuccess(List<Evento> eventos) {}

                    @Override
                    public void onFail(Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return evento.getPresentes().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewNomePresente;
        public TextView mTextViewDescricaoPresente;
        public ImageView mImageViewRemovePresente;

        public ViewHolder(View itemView) {
            super(itemView);

            mImageViewRemovePresente = (ImageView) itemView.findViewById(R.id.image_view_remove);
            mTextViewNomePresente = (TextView) itemView.findViewById(R.id.text_view_nome_presente);
            mTextViewDescricaoPresente = (TextView) itemView.findViewById(R.id.text_view_descricao_presente);
        }
    }
}
