import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args){

        int PORT = 4242;
        
        try{
            Socket cliente = new Socket("127.0.0.1",PORT);
            System.out.println("Cliente se conectou ao servidor");
            
            PrintStream saida = new PrintStream(cliente.getOutputStream());

            Scanner teclado = new Scanner(System.in);
            
            while(teclado.hasNextLine()){
                saida.println(teclado.nextLine());    
            }
            saida.close();
            teclado.close();
            cliente.close();

        }
        catch(Exception e){
            System.out.println("Error: Cliente não conseguiu se conectar ao servidor!");
            e.printStackTrace();
        }
    }
}
