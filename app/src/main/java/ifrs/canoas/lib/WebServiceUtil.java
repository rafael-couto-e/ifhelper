package ifrs.canoas.lib;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by marcio on 27/08/17.
 */

public class WebServiceUtil {

    public static String getContentAsString(String urlStr) throws IOException {
        URL url = null;
        InputStream is = null;
        try {
            try {
                url = new URL(urlStr);
            } catch (MalformedURLException e) {
                Log.e("SENHOR PROGRAMADOR", "Você fez caca verifique");
                e.printStackTrace();
            }
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

            return readIt(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    private static String readIt(InputStream stream) throws IOException {
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
}
