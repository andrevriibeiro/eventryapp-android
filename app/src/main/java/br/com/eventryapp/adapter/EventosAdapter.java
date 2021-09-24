package br.com.eventryapp.adapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.eventryapp.R;
import br.com.eventryapp.activity.OrganizadorEventoActivity;
import br.com.eventryapp.manager.EventoManager;
import br.com.eventryapp.model.Evento;
import br.com.eventryapp.service.listener.EventoServiceListener;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.ViewHolder> {

    private List<Evento> eventoList;

    public EventosAdapter(List<Evento> eventoList) {
        this.eventoList = eventoList;
    }

    @NonNull
    @Override
    public EventosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventos_item,
                parent, false);
        return new EventosAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final EventosAdapter.ViewHolder holder, final int position) {
        final Evento evento = eventoList.get(position);

        holder.mTextViewNomeEvento.setText(evento.getEventonome());
        holder.mTextViewDescricaoEvento.setText(evento.getDescricao());
        holder.mTextViewDataEvento.setText(evento.getDataevento() + " às " + evento.getHoraevento());
        holder.mTextViewLocalEvento.setText(evento.getLocalizacao());

        holder.mTextViewNomeEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OrganizadorEventoActivity.class);
                intent.putExtra("evento", evento);
                v.getContext().startActivity(intent);
            }
        });

        holder.mTextViewDescricaoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OrganizadorEventoActivity.class);
                intent.putExtra("evento", evento);
                v.getContext().startActivity(intent);
            }
        });

        holder.mTextViewLocalEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OrganizadorEventoActivity.class);
                intent.putExtra("evento", evento);
                v.getContext().startActivity(intent);
            }
        });

        holder.mTextViewDataEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OrganizadorEventoActivity.class);
                intent.putExtra("evento", evento);
                v.getContext().startActivity(intent);
            }
        });

        holder.imageViewCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OrganizadorEventoActivity.class);
                intent.putExtra("evento", evento);
                v.getContext().startActivity(intent);
            }
        });

        holder.imageViewRemover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Removendo...", Toast.LENGTH_LONG).show();
            }
        });

        holder.imageViewRemover.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                progressDialog.setMessage(v.getContext().getString(R.string.removendo_evento));
                progressDialog.show();

                EventoManager eventoManager = new EventoManager(v.getContext());
                eventoManager.deletarEvento(evento.getId(), new EventoServiceListener() {
                    @Override
                    public void onSuccess(Evento evento) {}

                    @Override
                    public void onSuccess(Response response) {
                        eventoList.remove(evento);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,eventoList.size());
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onSucess(ResponseBody responseBody) {}

                    @Override
                    public void onSuccess(List<Evento> eventos) {}

                    @Override
                    public void onFail(Throwable t) {

                    }
                });
            }
        });
    }

    public void setData(List<Evento> eventos) {
        this.eventoList = eventos;
        refreshData();
    }

    private void refreshData(){
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewRemove;
        public ImageView imageViewCard;
        public ImageView imageViewRemover;
        public TextView mTextViewNomeEvento;
        public TextView mTextViewDescricaoEvento;
        public TextView mTextViewDataEvento;
        public TextView mTextViewLocalEvento;

        public ViewHolder(View itemView) {
            super(itemView);

            imageViewRemover = (ImageView) itemView.findViewById(R.id.image_view_remove);
            imageViewCard = (ImageView) itemView.findViewById(R.id.image_view_card);
            imageViewRemover = (ImageView) itemView.findViewById(R.id.image_view_remove);
            mTextViewNomeEvento = (TextView) itemView.findViewById(R.id.text_view_nome_evento);
            mTextViewDescricaoEvento = (TextView) itemView.findViewById(R.id.text_view_descricao_evento);
            mTextViewDataEvento = (TextView) itemView.findViewById(R.id.text_view_data_evento);
            mTextViewLocalEvento = (TextView) itemView.findViewById(R.id.text_view_local_evento);
        }
    }
}
