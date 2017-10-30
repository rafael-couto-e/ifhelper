package ifrs.canoas.ifhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import ifrs.canoas.ifhelper.portal.ListarMensagemActivity;
import ifrs.canoas.lib.NotificationTrigger;

public abstract class DefaultActivity extends AppCompatActivity {

    public void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO Estudar esse método e implementar a troca de idioma aqui conforme botão superior aqui.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), ViewCordinatesActivity.class));
            return true;
        }else if(id == R.id.mensagensMenu){
            return getMensagens();
        }

        return super.onOptionsItemSelected(item);
    }

    protected boolean getMensagens() {
        startActivity(new Intent(getApplicationContext(), ListarMensagemActivity.class));
        return true;
    }
}
