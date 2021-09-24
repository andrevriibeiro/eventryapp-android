package br.com.eventryapp.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.eventryapp.R;

import br.com.eventryapp.model.Convidado;

import static android.support.v4.content.ContextCompat.getColor;

public class ConvidadosAdapter extends RecyclerView.Adapter<ConvidadosAdapter.ViewHolder> {

    private static final String CONFIRMADO = "Confirmado";
    private static final String TALVEZ = "Talvez comparecerei";
    private static final String NAO_POSSO = "Não Poderei Comparecer";

    private List<Convidado> convidadoList;

    public ConvidadosAdapter(List<Convidado> convidadoList) {
        this.convidadoList = convidadoList;
    }

    @NonNull
    @Override
    public ConvidadosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.convidado_item,
                parent, false);
        return new ConvidadosAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConvidadosAdapter.ViewHolder holder, int position) {
        final Convidado convidado = convidadoList.get(position);

        holder.mTextViewNomeConvidado.setText(convidado.getUsuario().getNome());
        holder.mTextViewStatusConfirmacao.setText(convidado.getStatusconfirmacao());

        if(convidado.getStatusconfirmacao().equals(CONFIRMADO)){
            holder.mTextViewStatusConfirmacao.setTextColor(
                    getColor(holder.itemView.getContext(), R.color.light_green));
        }else if(convidado.getStatusconfirmacao().equals(NAO_POSSO)){
            holder.mTextViewStatusConfirmacao.setTextColor(
                    getColor(holder.itemView.getContext(), R.color.red));
        }else if(convidado.getStatusconfirmacao().equals(TALVEZ)){
            holder.mTextViewStatusConfirmacao.setTextColor(
                    getColor(holder.itemView.getContext(), R.color.blue_dark));
        }

    }

    @Override
    public int getItemCount() {
        return convidadoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextViewNomeConvidado;
        public TextView mTextViewStatusConfirmacao;

        public ViewHolder(View itemView) {
            super(itemView);

            mTextViewNomeConvidado = (TextView) itemView.findViewById(R.id.text_view_nome_convidado);
            mTextViewStatusConfirmacao = (TextView) itemView.findViewById(R.id.text_view_status_confirmacao);
        }
    }
}
