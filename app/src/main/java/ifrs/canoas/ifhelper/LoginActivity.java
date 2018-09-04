package ifrs.canoas.ifhelper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ifrs.canoas.lib.WebServiceUtil;
import ifrs.canoas.model.Curso;
import ifrs.canoas.model.Token;
import ifrs.canoas.model.User;

public class LoginActivity extends AppCompatActivity {

    private TextView mensagem;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mensagem = (TextView) findViewById(R.id.message);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void login(View v)
    {
        Log.d("Teste","testando botão");
        EditText usuario =  (EditText) findViewById(R.id.login);
        EditText senha =  (EditText) findViewById(R.id.senha);

        String uri = "http://moodle.canoas.ifrs.edu.br/login/token.php";
        uri += "?username="+ usuario.getText().toString() +
                "&password=" + senha.getText().toString()  +
                "&service=moodle_mobile_app";

        WebServiceConsumer tarefa = new WebServiceConsumer();
        tarefa.execute(uri);
    }

    /**
     * 1 - Argumento de entrada para o Async task
     * 2 -
     * 3 - Retorno do método doInBackground
     */                                                 //1       2      3
    private class WebServiceConsumer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                //Primeira requisição login
                String result =  WebServiceUtil.getContentAsString(urls[0]);
                Gson g = new Gson();
                Token token = g.fromJson(result.trim(), Token.class);
                if(token.getToken().isEmpty()){
                    return "Usuario e senha invalidos";
                }else{
                    //Segunda requisição objeto completo com token
                    result =  WebServiceUtil.getContentAsString("http://moodle.canoas.ifrs.edu.br/webservice/rest/server.php?wstoken=" + token.getToken() + "&wsfunction=core_webservice_get_site_info&moodlewsrestformat=json");
                    g = new Gson();
                    user = g.fromJson(result.trim(), User.class);
                    user.setToken(token.getToken());
                    Log.d("oi", user.toString());
                    return "Login correto";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid." + e;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            System.out.println("OBAAAAA");
            mensagem.setText(result);
            WebServiceCourses web = new WebServiceCourses();
            web.execute("http://moodle.canoas.ifrs.edu.br/webservice/rest/server.php?wstoken=" + user.getToken() + "&wsfunction=core_enrol_get_users_courses&moodlewsrestformat=json&userid="+ user.getUserid());
        }
    }


    private class WebServiceCourses extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return WebServiceUtil.getContentAsString(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid." + e;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Gson g = new Gson();
            ArrayList<Curso> listaCursos = g.fromJson(result.trim(), new TypeToken<List<Curso>>(){}.getType());
            Log.d("oi", listaCursos.toString());
            mensagem.setText(" Logado com sucesso" );
        }
    }

}
