package commom_class;
import java.net.Socket;

public class Passageiro extends Usuario{


    public Passageiro(String nome, Socket cliente) {
        super(nome,cliente);
    }

    @Override
    public String toString(){
        return nome;

    }

}
