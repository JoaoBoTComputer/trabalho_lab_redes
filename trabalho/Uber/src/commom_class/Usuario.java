package commom_class;

import java.io.Serializable;
import java.net.Socket;

public abstract class Usuario implements Serializable{
    Socket cliente;
    String nome;

    public Usuario(String nome,Socket cliente){
        this.nome = nome;
        this.cliente = cliente;
    }

    public Socket getCliente(){
        return this.cliente;
    }
    
    public String getNome() {
        return nome;
    }
}
