package ifrs.canoas.ifhelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ifrs.canoas.lib.ListaDisciplinaAdapter;
import ifrs.canoas.lib.WebServiceUtil;
import ifrs.canoas.model.Disciplina;
import ifrs.canoas.model.User;

public class ListarDisciplinasActivity extends AppCompatActivity {

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_disciplinas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        makeListView();
    }

    /**
     * Método que monta a list view
     */
    private void makeListView(){
        // User user =  (User) getIntent().getExtras().getSerializable("usuario"); Versão short
        list = (ListView) findViewById(R.id.listaDisciplinasLV);

        Intent it = getIntent();
        if (it != null) {//Checar se veio por intent
            Bundle dados = it.getExtras();
            if (dados != null && dados.getSerializable("usuario") != null) { //Checar se tem dados
                User user = (User) dados.getSerializable("usuario");

                new WebServiceCourses().execute(user);

            }
        }

    }

    private class WebServiceCourses extends AsyncTask<User, Void, String> {
        @Override
        protected String doInBackground(User... user) {
            try {
                return WebServiceUtil.getContentAsString("http://moodle.canoas.ifrs.edu.br/webservice/rest/server.php?wstoken=" +
                      user[0].getToken() +  "&wsfunction=core_enrol_get_users_courses&moodlewsrestformat=json&userid=" + user[0].getUserid());
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid." + e;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Gson g = new Gson();
            ArrayList<Disciplina> lista = g.fromJson(result.trim(), new TypeToken<List<Disciplina>>(){}.getType());

            //list.setAdapter(new ArrayAdapter<Disciplina>(getApplicationContext(), android.R.layout.simple_list_item_1, listaCursos));

            ListaDisciplinaAdapter ld = new ListaDisciplinaAdapter(lista, getApplicationContext());
            list.setAdapter(ld);
        }
    }

}
