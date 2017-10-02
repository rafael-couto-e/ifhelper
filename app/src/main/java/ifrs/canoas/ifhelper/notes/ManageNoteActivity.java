package ifrs.canoas.ifhelper.notes;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ifrs.canoas.ifhelper.DefaultActivity;
import ifrs.canoas.ifhelper.R;
import ifrs.canoas.lib.BancoHelper;
import ifrs.canoas.model.portal.Note;

public class ManageNoteActivity extends DefaultActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_note);
        setToolbar();
    }

    public void saveNote(View view) {
        BancoHelper helper = new BancoHelper(getApplicationContext());
        EditText title = (EditText) findViewById(R.id.txtTitulo);
        EditText text = (EditText) findViewById(R.id.txtDescricao);
        EditText discipline = (EditText) findViewById(R.id.txtDisciplina);
        Note note = new Note();
        note.setData(new Date().getTime());//Salvando diretamente o número que representa a data
        note.setDisciplina(discipline.getText().toString());
        note.setTexto(text.getText().toString());
        note.setTitulo(title.getText().toString());
        if (!(title.getText().toString().isEmpty() || text.getText().toString().isEmpty() || discipline.getText().toString().isEmpty()))
            note.insert(helper);
       Log.d("DEBUG", "Notas " + Note.getAll(helper).toString() + " " + Note.getAll(helper).size());
        finish();//Voltando para a listView tirando a atividade da pilha
    }

}
