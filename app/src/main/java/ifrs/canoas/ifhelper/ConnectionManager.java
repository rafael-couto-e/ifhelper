package ifrs.canoas.ifhelper;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConnectionManager {
    public static String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Limitar a 500 Caracteres lidos
        int len = 500;
        // Log.d("DEBUG", "url: " + myurl);

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

    private static String readIt(InputStream stream) throws IOException {
        Scanner s = new Scanner(stream).useDelimiter("\\A");

        return s.hasNext() ? s.next() : "";
    }
}
