package trabalho.Uber.src.commom_class;

import java.io.Serializable;

public class Passageiro implements Serializable{
    private String nome;

    public Passageiro(String nome) {
        this.setNome(nome);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public String toString(){
        return nome;

    }

}
