package ifrs.canoas.ifhelper.portal;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import ifrs.canoas.ifhelper.DefaultActivity;
import ifrs.canoas.ifhelper.R;
import ifrs.canoas.ifhelper.geral.LoginActivity;
import ifrs.canoas.lib.MensagemAdapter;
import ifrs.canoas.lib.SharedPreferenceHelper;
import ifrs.canoas.lib.WebServiceUtil;
import ifrs.canoas.model.portal.Mensagem;
import ifrs.canoas.model.portal.User;

public class ListarMensagemActivity extends DefaultActivity {

    private User usuario;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_mensagem);

        recuperaDados();
        trataFloatButton();
        setToolbar();
    }


    /**
     *
     */
    private void recuperaDados() {

        try{//Qualquer falha manda para o login novamente deveria se estudar melhor mas para demonstração serve
            SharedPreferenceHelper sp = new SharedPreferenceHelper(this);
            String token = sp.readString("TOKEN", "SEM_TOKEN");
            if(token.equals("SEM_TOKEN")){
               throw new Exception("sem token");
            }else{
                int id = sp.readInt("ID_USER", 0);
                if(id>0){
                    this.usuario = new User();
                    this.usuario.setUserid(id);
                    this.usuario.setToken(token);
                    populaListaMensagens();
                }else{
                    loadUser(token);
                }
            }
        } catch (Exception e){
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }


    }

    private void geraLista(List<Mensagem> lista) {
        list = (ListView) findViewById(R.id.MensagemListView);
        MensagemAdapter ad = new MensagemAdapter(getApplicationContext(), lista);
        list.setAdapter(ad);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "Ver Mensagem completa", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void populaListaMensagens() {
        String url = "https://moodle.canoas.ifrs.edu.br/webservice/rest/server.php?" +
                "wstoken=" + this.usuario.getToken() + "&wsfunction=core_message_get_messages&moodlewsrestformat=json&read=1"
                + "&useridto=" + usuario.getUserid() +"&type=conversations";
        new ListMensagemWebService().execute(url);
    }

    private void loadUser(String token) {

        String url = "https://moodle.canoas.ifrs.edu.br/webservice/rest/server.php?" +
                "wstoken=" + token + "&wsfunction=core_webservice_get_site_info&moodlewsrestformat=json";
        new DadosDoUsuarioWebService().execute(url);

    }

    /**
     * Forma alternativa de tratar evento
     *
     *@param v
     */
    public void addMensagem(View v) {

    }

    private void trataFloatButton() {

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private class ListMensagemWebService extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                Log.i("URL", WebServiceUtil.getContentAsString(urls[0]));
                return WebServiceUtil.getContentAsString(urls[0]);

            } catch (IOException e) {
                Log.e("Exception", e.toString());//Observe que aqui uso o log.e e não log.d
                return "Problema ao montar a requisição";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            try {
                Gson parser = new Gson();

                JSONObject my_obj = new JSONObject(result);
                List<Mensagem> listaMensagens;
                listaMensagens = parser.fromJson(my_obj.getString("messages"), new TypeToken<List<Mensagem>>() {
                }.getType());
                geraLista(listaMensagens);
                Log.d("Mensagem", listaMensagens.get(0).toString());


            } catch (Exception e){
                Log.e("Erro",e.getMessage());
            }

            //getJSONArray("elenco")
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
            //Com o usuario posso pedir a lista das mensagens
            Gson g = new Gson();
            usuario = g.fromJson(result, User.class);
            SharedPreferenceHelper sp = new SharedPreferenceHelper(getApplicationContext());
            sp.save("ID_USER", usuario.getUserid());
            populaListaMensagens();
            //Observe que chamo aqui o populaListaMensagem somente assim tenho certeza que os dados estão disponíveis.
            // Lembre-se assincrono é um processo pararelo e não sequencial como algoritmos.
        }


    }

}
