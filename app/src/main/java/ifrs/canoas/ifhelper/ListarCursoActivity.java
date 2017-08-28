package ifrs.canoas.ifhelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import ifrs.canoas.lib.WebServiceUtil;
import ifrs.canoas.model.portal.User;

public class ListarCursoActivity extends AppCompatActivity {

    private String token;
    private User usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_curso);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recuperaDados();
        trataFloatButton();

    }

    private void recuperaDados() {
        Intent it = getIntent();
        if (it != null) {//Checar se veio por intent
            Bundle dados = it.getExtras();
            if (dados != null && dados.getString("token") != null) { //Checar se tem dados
                this.token = dados.getString("token");
                //Inicialmente preciso dos dados do user
                loadUser();
                return ;
            }
        }
        //TODO o correto é tratar essas possíveis exceções um exemplo pode ser abrir o LoginActivity novamente
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    private void populaListaCursos() {

        String url = "https://moodle.canoas.ifrs.edu.br/webservice/rest/server.php?" +
                "wstoken=" + this.token + "&wsfunction=core_webservice_get_site_info&moodlewsrestformat=json";
        new DadosDoUsuarioWebService().execute(url);
    }

    private void loadUser() {
        //Observe que ainda é possível obter o token de forma estática
        Log.d("INFo", User.token);
        String url = "https://moodle.canoas.ifrs.edu.br/webservice/rest/server.php?" +
                "wstoken=" + this.token + "&wsfunction=core_webservice_get_site_info&moodlewsrestformat=json";
        new DadosDoUsuarioWebService().execute(url);

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

    private class ListCursosWebService extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return WebServiceUtil.getContentAsString(urls[0]);
            } catch (IOException e) {
                Log.e("Exception", e.toString());//Observe que aqui uso o log.e e não log.d
                return "Problema ao montar a requisição";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("teste", result);
        }


    }

    private class DadosDoUsuarioWebService extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return WebServiceUtil.getContentAsString(urls[0]);
            } catch (IOException e) {
                Log.e("Exception", e.toString());//Observe que aqui uso o log.e e não log.d
                return "Problema ao montar a requisição";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            Log.d("teste", result);
            //Com o usuario posso pedir a lista dos seus cursos
        }


    }

}
