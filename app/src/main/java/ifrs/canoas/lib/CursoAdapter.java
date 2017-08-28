package ifrs.canoas.lib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.List;

import ifrs.canoas.ifhelper.R;
import ifrs.canoas.model.portal.Curso;

public class CursoAdapter extends BaseAdapter {
    private Context context;
    private List<Curso> list;

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
        return 1;
        //return list.get(arg0).getId();
    }

    @Override
    public View getView(int position, View arg1, ViewGroup arg2) {
        final int auxPosition = position;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.curso_elemento, null);


        return layout;
    }

}
