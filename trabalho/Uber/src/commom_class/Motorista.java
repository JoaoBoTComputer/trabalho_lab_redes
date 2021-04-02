package commom_class;
import java.net.Socket;

public class Motorista extends Usuario{
    private String placa;


    public Motorista(String nome,String placa,Socket cliente){
        super(nome,cliente);
        this.placa = placa;

    }

    public String getPlaca() {
        return placa;
    }
}
