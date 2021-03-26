package trabalho.Uber.src.commom_class;

import java.io.Serializable;

public class Motorista implements Serializable{
    private String nome;
    private String placa;

    public Motorista(String nome,String placa){
        this.nome = nome;
        this.placa = placa;
    }

    public String getPlaca() {
        return placa;
    }

    public String getNome() {
        return nome;
    }
  


}
