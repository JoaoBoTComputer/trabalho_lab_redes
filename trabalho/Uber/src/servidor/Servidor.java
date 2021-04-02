package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    //42 é a respota da vida, do universo e de todas as coisas  
    private final short PORTA = 4242;
    private ServerSocket servidor;

    public static void main(String[] args) throws Exception{
        Servidor servidor = new Servidor();
        servidor.start();
        servidor.esperarClientesLoop();

    }

    private void esperarClientesLoop() throws IOException{
        while(true){
            Socket cliente = servidor.accept();
            Thread thread = new Thread(new UberService(cliente));
            thread.start();
        }
    }

    private void start(){
        try {
            this.servidor = new ServerSocket(this.PORTA);
            System.out.println("Servidor rodando na porta:"+this.PORTA);
        } catch (IOException e) {
            System.out.println("Falha na inicialização do servidor!");
            e.printStackTrace();
        }
    }
}
