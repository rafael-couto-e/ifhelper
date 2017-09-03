package ifrs.canoas.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ifrs.canoas.ifhelper.R;
import ifrs.canoas.model.portal.Curso;

/**
 * Classe Adapter personalizada o android fornece alguns adapters mas que graça tem
 */
public class CursoAdapter extends BaseAdapter {

    private Context context;//Contexto da aplicação preciso para poder usar os recursos do android
    private List<Curso> list; //Minha Lista

    /**
     * Construtor forçando a bara para ter o contexto e uma lista
     *
     * @param context
     * @param list
     */
    public CursoAdapter(Context context, List<Curso> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return list.get(arg0).getId();
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.curso_elemento, null);

        final TextView titulo = (TextView) layout.findViewById(R.id.nomeCurso);
        TextView descricao = (TextView) layout.findViewById(R.id.descricaoCurso);

        final Curso curso = (Curso) this.getItem(position);
        titulo.setText(curso.getShortname());
        descricao.setText(curso.getSummary().substring(0, 100));

        Button bt = (Button) layout.findViewById(R.id.courseCompleteDescriptionBT);
        bt.setOnClickListener(new View.OnClickListener() {
            private boolean toogle = true;

            @Override
            public void onClick(View view) {
                if (toogle) {
                    titulo.setText(curso.getSummary());
                    toogle = false;
                } else {
                    curso.getSummary().substring(0, 100);
                    toogle = true;
                }
            }
        });

        return layout;
    }

}
