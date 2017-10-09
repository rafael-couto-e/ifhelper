package ifrs.canoas.ifhelper.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import ifrs.canoas.ifhelper.DefaultActivity;
import ifrs.canoas.ifhelper.R;
import ifrs.canoas.lib.BancoHelper;
import ifrs.canoas.model.portal.Note;

public class ListarNotesActivity extends DefaultActivity {
    private NoteAdapter adapter;
    private boolean fabExpanded = false;
    private LinearLayout fabText;
    private LinearLayout fabPhoto;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_notes);
        setToolbar();

        trataFloatButton();
        closeSubMenusFab();
        //listNotes(new BancoHelper(getApplicationContext()));

    }



    //Baseado em https://github.com/ptyagicodecamp/fab-submenu.git
    private void trataFloatButton(){
        fabPhoto = (LinearLayout) findViewById(R.id.fabPhotoLayout);
        fabText = (LinearLayout) findViewById(R.id.fabTextLayout);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });
    }

    //closes FAB submenus
    private void closeSubMenusFab(){
        fabPhoto.setVisibility(View.INVISIBLE);
        fabText.setVisibility(View.INVISIBLE);
        fab.setImageResource(R.drawable.ic_note_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        fabPhoto.setVisibility(View.VISIBLE);
        fabText.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fab.setImageResource(R.drawable.ic_close_24dp);
        fabExpanded = true;
    }

    public void noteText(View v){
        startActivity(new Intent(getApplicationContext(), ManageNoteActivity.class));
    }

    public void notePhoto(View v){
        startActivity(new Intent(getApplicationContext(), TakeNotePictureActivity.class));
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
