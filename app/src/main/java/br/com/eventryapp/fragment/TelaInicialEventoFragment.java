package br.com.eventryapp.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import br.com.eventryapp.R;
import br.com.eventryapp.model.Evento;
import br.com.eventryapp.util.Functions;

public class TelaInicialEventoFragment extends Fragment {

    private TextView mTextViewCodigoEvento;
    private Button mButtonCompartrilhar;
    private Evento evento;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tela_inicial_evento, container, false);

        mTextViewCodigoEvento = (TextView) view.findViewById(R.id.text_view_codigo_evento);
        mButtonCompartrilhar = (Button) view.findViewById(R.id.btn_compartilhar);
        registerForContextMenu(mTextViewCodigoEvento);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            evento = bundle.getParcelable("evento");
        }

        mTextViewCodigoEvento.setText(evento.getCodigoevento().toString());

        mButtonCompartrilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Você está sendo convidado(a) para o evento: ");
                stringBuilder.append(evento.getEventonome());
                stringBuilder.append("\n");
                stringBuilder.append("Código do Evento: " + evento.getCodigoevento());
                stringBuilder.append("\n");
                stringBuilder.append("\n");
                stringBuilder.append("Acesse o aplicativo Eventry e saiba mais...");

                Functions.share(getActivity(), stringBuilder);
            }
        });

        return view;
    }
}
