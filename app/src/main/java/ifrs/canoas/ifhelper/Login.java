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
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import ifrs.canoas.model.portal.LoginRetorno;

public class Login extends AppCompatActivity {

    private TextView mensagem;

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

        DownloadWebpageTask  tarefa = new DownloadWebpageTask();
        tarefa.execute(uri);
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        Log.d("DEBUG", "url: " + myurl);

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("DEBUG", "Resposta HTTP: " + response);

            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    public String readIt(InputStream stream) throws IOException {
        Reader reader = null;
        StringBuffer buffer = new StringBuffer();//Objeto de que vai armazenar o resultado
        reader = new InputStreamReader(stream, "UTF-8"); //Objeto leitor
        Reader in = new BufferedReader(reader); //Converte de input em buffer
        int ch;
        while ((ch = in.read()) > -1) {//Lendo Char por char
            buffer.append((char) ch);
        }
        in.close();
        return new String(buffer);
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                Log.e("Exception", e.toString());//Observe que aqui uso o log.e e não log.d
                return "Problema ao montar a requisição";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.d("teste", result);
            Gson g = new Gson();
            LoginRetorno retorno = g.fromJson(result.trim(), LoginRetorno.class);
            Log.d("Teste", retorno.toString());
            processaRetorno(retorno);
        }


    }

    private void processaRetorno(LoginRetorno retorno) {
        if (retorno.getToken() == "Nenhum") {
            mensagem.setText(retorno.getError());
        } else {
            //Vamos criar um bundle para passar as info para outra tela e como alternativa seria usar variável estática.
            Intent intent = new Intent(getApplicationContext(), ListarCurso.class);
            intent.putExtra("token", retorno.getToken());
            startActivity(intent);
        }
    }




}
