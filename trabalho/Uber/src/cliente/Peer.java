package cliente;

import java.io.IOException;
import java.net.ServerSocket;

public class Peer implements Runnable {
    UsuarioApp UsuarioApp;
    ServerSocket serverSocket;
    short PORTA = 4041;

    public Peer(UsuarioApp usuarioApp){
        this.UsuarioApp = usuarioApp;
        
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(this.PORTA);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
}
