package ifrs.canoas.ifhelper.notes;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ifrs.canoas.ifhelper.R;
import ifrs.canoas.lib.BancoHelper;
import ifrs.canoas.model.portal.Note;


/**
 * Created by VictorJr on 19/09/2017.
 */

public class NoteAdapter extends BaseAdapter {

    private Context context;
    private List<Note> list;
    private Note note;
    private NoteAdapter that;

    public NoteAdapter(Context context, List<Note> list) {
        this.context = context;
        this.list = list;
        that = this;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).getIdNota();
    }

    @Override
    public View getView(int position, View elemento, ViewGroup pai) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.elemento_note, null);

        TextView titulo = (TextView) layout.findViewById(R.id.notaTitulo);
        final TextView descricao = (TextView) layout.findViewById(R.id.notaTexto);

        final Note nota = (Note) this.getItem(position);
        titulo.setText(nota.getTitulo());

        String desc = nota.getTexto().replaceAll("\\<.*?>", "");//vai ter HTML na nota???

        int tam = desc.length() > 100 ? 100 : desc.length();

        final String finalDesc = desc.substring(0, tam) + "...";

        descricao.setText(finalDesc);

        Button btDelete = (Button) layout.findViewById(R.id.notaDelete);

        btDelete.setOnClickListener(
                new View.OnClickListener() {

                    private boolean toogle = true;//what isso não era para estar aqui heheh

                    @Override
                    public void onClick(View view) {
                        BancoHelper everynoteHelper = new BancoHelper(context);
                        Log.d("debug", "listener delete acionada!");
                        nota.delete(everynoteHelper);

                        //Sem isso não deleta
                        list.remove(nota);
                        that.notifyDataSetChanged();
                    }
                });

        Button btnEdit = (Button) layout.findViewById(R.id.notaEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //isso não deveria fazer isso deveria chamar uma activity
                BancoHelper everynoteHelper = new BancoHelper(context);
                int position = note.getIdNota();
                Cursor cursor = null;
                Log.d("debug", "listener delete acionada!");
                String codigo;
                cursor.moveToPosition(position);
                codigo = cursor.getString(cursor.getColumnIndexOrThrow(Note.NoteContract.NoteEntry._ID));
                note.setTitulo(layout.findViewById(R.id.notaTitulo).toString());
                note.setTexto(layout.findViewById(R.id.notaTexto).toString());
                note.update(everynoteHelper);
            }
        });



        return layout;
    }
}
