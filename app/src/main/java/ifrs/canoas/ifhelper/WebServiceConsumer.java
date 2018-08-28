package ifrs.canoas.ifhelper;

import android.os.AsyncTask;

import java.io.IOException;

public abstract class WebServiceConsumer extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... strings) {
        try{
            return ConnectionManager.downloadUrl(strings[0]);
        } catch (IOException e) {
            return "Unable to retrieve web page. URL may be invalid." + e;
        }
    }

    @Override
    protected abstract void onPostExecute(String s);
}
