package ifrs.canoas.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Locale;

import ifrs.canoas.ifhelper.R;

/**
 * Created by marcio on 06/08/17.
 */

public class MediaHarmonicaCalculator {
    private ArrayList<ItemMedia> notas = new ArrayList<>();
    private Context context;
    private double pesos;

    public MediaHarmonicaCalculator(Context context) {
        this.context = context;
        this.pesos = 0;
    }

    public void addNota(String label, double peso, double media) throws Exception{
        if (pesos + peso > 10)
            throw new Exception(
                String.format(
                    Locale.getDefault(), "%s. Atual: %.0f",
                    context.getString(R.string.erro_soma_pesos),
                    pesos
                )
            );

        notas.add(new ItemMedia(label, media, peso));

        pesos += peso;
    }

    public void clear(){
        this.notas.clear();
    }

    /**
     * Calcula a média harmônica
     *
     * @return
     * @throws Exception
     */
    public double calcula() throws Exception {
        double media;
        if(notas.size()>2){
            double notas = 0;

            for (ItemMedia i:this.notas){
                if (i.getNota() < 0)
                    i.setNota(0.0001);

                notas += i.getPeso()/i.getNota();
            }

            if (pesos != 10)
                throw new Exception(context.getString(R.string.erro_soma_pesos));

            media = pesos/notas;
        }else{
            throw new Exception(
                    String.format(
                            Locale.getDefault(),
                            "%s %d",
                            context.getString(R.string.erro_qtd_notas),
                            notas.size()
                    )
            );
        }

        return media;
    }
}
