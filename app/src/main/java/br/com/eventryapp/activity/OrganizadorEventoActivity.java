package br.com.eventryapp.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.eventryapp.R;
import br.com.eventryapp.fragment.AvaliacoesFragment;
import br.com.eventryapp.fragment.ConvidadosFragment;
import br.com.eventryapp.fragment.EditarEventoFragment;
import br.com.eventryapp.fragment.PresentesFragment;
import br.com.eventryapp.fragment.TelaInicialEventoFragment;
import br.com.eventryapp.model.Evento;

public class OrganizadorEventoActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Evento eventoInfo = new Evento();
    final Fragment telaInicialEventoFragment = new TelaInicialEventoFragment();
    final Fragment editarEventoFragment = new EditarEventoFragment();
    final Fragment presentesFragment = new PresentesFragment();
    final Fragment convidadosFragment = new ConvidadosFragment();
    final Fragment avaliacoesFragment = new AvaliacoesFragment();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = telaInicialEventoFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fm.beginTransaction().hide(active).show(telaInicialEventoFragment).commit();
                    active = telaInicialEventoFragment;
                    return true;

                case R.id.navigation_editar_eventos:

                    fm.beginTransaction().hide(active).show(editarEventoFragment).commit();
                    active = editarEventoFragment;
                    return true;

                case R.id.navigation_lista_presentes:
                    fm.beginTransaction().hide(active).show(presentesFragment).commit();
                    active = presentesFragment;
                    return true;

                case R.id.navigation_convidados:
                    fm.beginTransaction().hide(active).show(convidadosFragment).commit();
                    active = convidadosFragment;
                    return true;

                case R.id.navigation_avalicacoes:
                    fm.beginTransaction().hide(active).show(avaliacoesFragment).commit();
                    active = avaliacoesFragment;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizador_evento);

        /*passanfo informações do evento para cada fragment*/
        eventoInfo = getIntent().getParcelableExtra("evento");
        Bundle bundle = new Bundle();
        bundle.putParcelable("evento", eventoInfo);
        editarEventoFragment.setArguments(bundle);
        presentesFragment.setArguments(bundle);
        convidadosFragment.setArguments(bundle);
        avaliacoesFragment.setArguments(bundle);
        telaInicialEventoFragment.setArguments(bundle);

        fm.beginTransaction().add(R.id.main_container, avaliacoesFragment, "5").hide(avaliacoesFragment).commit();
        fm.beginTransaction().add(R.id.main_container, convidadosFragment, "4").hide(convidadosFragment).commit();
        fm.beginTransaction().add(R.id.main_container, presentesFragment, "3").hide(presentesFragment).commit();
        fm.beginTransaction().add(R.id.main_container,editarEventoFragment, "2").hide(editarEventoFragment).commit();
        fm.beginTransaction().add(R.id.main_container, telaInicialEventoFragment, "1").commit();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
