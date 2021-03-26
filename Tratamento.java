import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Tratamento implements Runnable{

    private Socket cliente;

    public Tratamento(Socket cliente){
        this.cliente = cliente;
    }

    @Override
    public void run() {
        try{
            System.out.println("Cliente "+cliente.getInetAddress().getHostAddress());

            Scanner scanner =new Scanner(cliente.getInputStream());

            String nome = scanner.nextLine();
            
            while(scanner.hasNextLine()){ 
                System.out.println(nome+":"+scanner.nextLine());
            }

            scanner.close();
            cliente.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        
        
    }
}