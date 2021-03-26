package servidor;

import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) throws Exception{
        //42 é a respota da vida, do universo e de todas as coisas  
        final short PORTA = 4242; 

        ServerSocket servidor = new ServerSocket(PORTA);

        System.out.println("Server running in port ["+PORTA+"]");
        
        while(true){
            Socket cliente = servidor.accept();
            Thread t = new Thread(new UberService(cliente));
            t.start();

        }


    }
}
