package servidor;

import java.io.Serializable;

public abstract class ClienteUber implements Serializable{
    private String nome;
    
    public ClienteUber(String nome){
        this.setNome(nome);
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void executarServico(){};
}
