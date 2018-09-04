package ifrs.canoas.ifhelper;

import android.os.AsyncTask;

import java.io.IOException;

public class WebServiceConsumer extends AsyncTask<String, Void, String> {
    private ExecutionListener listener;

    public WebServiceConsumer(ExecutionListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... strings) {
        try{
            return ConnectionManager.downloadUrl(strings[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid." + e;
        }
    }

    @Override
    protected void onPostExecute(String s) {
        listener.onPostExecute(s);
    }
}
