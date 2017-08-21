package ifrs.canoas.ifhelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ListarCurso extends AppCompatActivity {

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_curso);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recuperaDados();
        populaListaCursos();
        trataFloatButton();
    }

    private void recuperaDados() {
        Intent it = getIntent();
    }

    private void populaListaCursos() {

    }

    /**
     * Forma alternativa de tratar evento
     *
     * @param v
     */
    public void addCurso(View v) {

    }

    private void trataFloatButton() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Desafio fazer uma tela como a do wats listando os cursos disponíveis para poder matricular-se
            }
        });

    }

}
