package ifrs.canoas.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ifrs.canoas.ifhelper.R;
import ifrs.canoas.model.Disciplina;

public class ListaDisciplinaAdapter extends BaseAdapter{

    private ArrayList<Disciplina> lista;
    private Context contexto;

    public ListaDisciplinaAdapter(ArrayList<Disciplina> lista, Context ct){
        this.lista = lista == null ? new ArrayList<Disciplina>() : lista;
        this.contexto = ct;
    }

    @Override
    public int getCount() {
        return this.lista.size();
    }

    @Override
    public Object getItem(int i) {
        return this.lista.get(i);
    }

    @Override
    public long getItemId(int i) {
        return (long) this.lista.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Disciplina disp = this.lista.get(i);

        LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.item_disciplina, null);

        TextView tw = (TextView) layout.findViewById(R.id.nome_disciplina);
        tw.setText(disp.getShortname());

        tw = (TextView) layout.findViewById(R.id.descricao_disciplina);
        tw.setText("blab bla bla ");

        return layout;
    }
}
