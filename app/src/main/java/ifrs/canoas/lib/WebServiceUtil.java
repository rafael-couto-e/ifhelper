package ifrs.canoas.lib;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

import ifrs.canoas.ifhelper.R;


/**
 * Created by marcio on 27/08/17.
 */

public class WebServiceUtil {

    public static String getContentAsString(String urlStr) throws IOException {
        URL url = null;
        InputStream is = null;
        try {
            try {
                String urlFinal = URLEncoder.encode(urlStr, "utf-8");// Bug de caracteres especiais na URL
                //Créditos Cassiano
                url = new URL(urlStr);
            } catch (MalformedURLException e) {
                Log.e("SENHOR PROGRAMADOR", "Você fez caca verifique");
                e.printStackTrace();
            }
            trustEveryone();
            HttpsURLConnection conn;

            conn = (HttpsURLConnection) url.openConnection();

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

                conn.connect();


            int response = conn.getResponseCode();

            is = conn.getInputStream();

            return readIt(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }

    /**
     * Método ReadIt genérico permite ler um número ilimitado de caracteres.
     *
     * @param stream
     * @return String
     * @throws IOException
     */
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
        String ret = new String(buffer);
        return ret;
    }

    private static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new
                                                                  HostnameVerifier(){
                                                                      public boolean verify(String hostname, SSLSession session)
                                                                      {
                                                                          return true;
                                                                      }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new
                    X509TrustManager(){
                        public void checkClientTrusted(X509Certificate[] chain,
                                                       String authType) throws
                                CertificateException {}
                        public void checkServerTrusted(X509Certificate[] chain,
                                                       String authType) throws
                                CertificateException {}
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }


}
