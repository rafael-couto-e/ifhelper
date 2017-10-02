package ifrs.canoas.ifhelper.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import ifrs.canoas.ifhelper.DefaultActivity;
import ifrs.canoas.ifhelper.R;
import ifrs.canoas.lib.BancoHelper;
import ifrs.canoas.model.portal.Note;

public class ListarNotesActivity extends DefaultActivity {
    private NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_notes);
        setToolbar();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ManageNoteActivity.class));
            }
        });

        //listNotes(new BancoHelper(getApplicationContext()));

    }

    //Baseado em https://ptyagicodecamp.github.io/creating-sub-menuitems-for-fab-floating-action-button.html
    private void floatButtonCardView(){

    }

    private void listNotes(BancoHelper helper) {
        ListView notasAdicionadas = (ListView) findViewById(R.id.notasListView);

        if (Note.getAll(helper) != null) {
            this.adapter =  new NoteAdapter(getApplicationContext(), Note.getAll(helper));
        }else{
            this.adapter =  new NoteAdapter(getApplicationContext(), new ArrayList<Note>());
        }
        notasAdicionadas.setAdapter(this.adapter);

    }

    @Override
    protected void onResume(){
        super.onResume();
        listNotes(new BancoHelper(getApplicationContext()));
    }



}
