package trabalho.Uber.src.servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import trabalho.Uber.src.commom_class.Motorista;
import trabalho.Uber.src.commom_class.Passageiro;

public class UberService implements Runnable{
    Socket cliente;
    static Queue<Motorista> filaMotoristas = new LinkedList<Motorista>();

    static Queue<Passageiro> filaPassageiros = new LinkedList<Passageiro>();

    UberService(Socket cliente)
        {this.cliente = cliente;}

    @Override
    public void run() {
        try{
            System.out.println("Cliente "+cliente.getInetAddress().getHostAddress());
            ObjectInputStream objectInputStream = new ObjectInputStream(cliente.getInputStream());
            Object object = objectInputStream.readObject();

            if(object.getClass().getSimpleName().equalsIgnoreCase("Passageiro")){
                Passageiro passageiro = (Passageiro) object;
                System.out.println(passageiro.toString());
                filaPassageiros.add(passageiro);
                System.out.println("Número de passageiros na fila:"+filaPassageiros.size());
            }

        }
        catch(IOException e ){e.printStackTrace();} 
        catch (ClassNotFoundException e) {e.printStackTrace();}   
    }

    private void buscarMotoristas(Passageiro passageiro){
    

    }

}



            // System.out.println(object.getClass().getSimpleName());

            // System.out.println(objectInputStream.readObject().toString());

            // Scanner scanner =new Scanner(cliente.getInputStream());

            // String nome = scanner.nextLine();
            // byte passageiroOuMotorista = scanner.nextByte();

            // System.out.println(passageiroOuMotorista);

            // if(passageiroOuMotorista == 0){
            //     System.out.println("É motorista!");
            //     PrintStream saida = new PrintStream(cliente.getOutputStream());
            //     saida.println("enviado do servidor");
            // }
                            
            // while(scanner.hasNextLine()){
            //     // Motorista a = scanner.nextLine(); 
            //     // System.out.println();
            // }

            // scanner.close();
            // cliente.close();