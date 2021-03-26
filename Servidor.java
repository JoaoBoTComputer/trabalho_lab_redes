import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Servidor {


    public static void main(String[] args) throws IOException{
        int PORT = 4242;
        ServerSocket servidor = new ServerSocket(PORT);
        System.out.println("SERVIDOR RODANDO NA PORTA "+PORT);
        
        while(true){
            Socket cliente = servidor.accept();
            Tratamento tratamento = new Tratamento(cliente);
            Thread t = new Thread(tratamento);
            t.start();
        }
    }



}