package cliente;

import java.io.Serializable;

public class UsuarioApp implements Serializable{
    
    String nome, placa;
    
    public UsuarioApp(String nome){
        this.nome = nome;
        this.placa = null;
    }

    public UsuarioApp(String nome, String placa){
        this.nome = nome;
        this.placa = placa;
    }

    public String getNome(){
        return this.nome;
    }
    public String getPlaca(){
        return this.placa;
    }

}
