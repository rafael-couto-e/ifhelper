package ifrs.canoas.ifhelper;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.Scanner;

import ifrs.canoas.model.User;

public class Login extends AppCompatActivity {

    private TextView mensagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mensagem = (TextView) findViewById(R.id.tvError);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void login(View v)
    {
        Log.d("Teste","testando botão");
        EditText usuario =  (EditText) findViewById(R.id.login);
        EditText senha =  (EditText) findViewById(R.id.senha);
        mensagem.setText(null);

        String uri = "http://moodle.canoas.ifrs.edu.br/login/token.php";
        uri += "?username="+ usuario.getText().toString() +
                "&password=" + senha.getText().toString()  +
                "&service=moodle_mobile_app";

        WebServiceConsumer tarefa = new WebServiceConsumer() {
            @Override
            protected String doInBackground(String... urls) {
                try {
                    return ConnectionManager.downloadUrl(urls[0]);
                } catch (IOException e) {
                    return "Unable to retrieve web page. URL may be invalid." + e;
                }
            }

            // onPostExecute displays the results of the AsyncTask.
            @Override
            protected void onPostExecute(String result) {
                if (result.contains("error")) {
                    mensagem.setText(new Gson().fromJson(result, Response.class).getError());
                    return;
                }

                Response<User> response = new Response<>();
                Log.d("teste", result);
                Gson g = new Gson();
                User user = g.fromJson(result.trim(), User.class);
                response.setData(user);

                Intent i = new Intent(Login.this, HomeActivity.class);
                i.putExtra("token", response.getData().getToken());
                startActivity(i);
            }
        };
        tarefa.execute(uri);
    }
}
