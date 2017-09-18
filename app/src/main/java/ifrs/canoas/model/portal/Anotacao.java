package ifrs.canoas.model.portal;

import java.util.Date;

import ifrs.canoas.lib.BancoHelper;

/**
 * Created by marcio on 17/09/17.
 */

public class Anotacao {
    private int id;
    private String titulo = "Sem Título";
    private String anotacao;
    private Date data;

    public Anotacao(String anotacao){
        this.anotacao = anotacao;
        this.data = new Date(System.currentTimeMillis());
    }

    public void insert(BancoHelper bd){

    }


}
