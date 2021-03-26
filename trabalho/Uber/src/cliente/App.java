package trabalho.Uber.src.cliente;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import trabalho.Uber.src.commom_class.Motorista;
import trabalho.Uber.src.commom_class.Passageiro;

public class App {
    static Scanner teclado = new Scanner(System.in);
    static int PORT = 4242;
    public static void main(String[] args){

        Object cliente = construirCliente();
        connectar(cliente);
      
    }


    static private Object construirCliente(){
        Object cliente;
        System.out.println("Sistema Uber inicializado!");
        System.out.print("Insera seu nome:");
        String nome = teclado.nextLine();
        System.out.print("Insira 0 - Motorista ou 1 - Passegeiro:");
        byte motoristaOuPassegeiro = teclado.nextByte();
        

        if(motoristaOuPassegeiro == 0){
            teclado.next();
            System.out.print("Insira a placa do carro:");
            String placa = teclado.nextLine();
            cliente = new Motorista(nome, placa);
        }
        else
            cliente = new Passageiro(nome);

        return cliente;

    }

    static private void connectar(Object obj){
        try{
            Socket cliente = new Socket("127.0.0.1",PORT);
            System.out.println("Cliente se conectou ao servidor");
            
            OutputStream saida = cliente.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(saida);
            objectOutputStream.writeObject(obj);

            String entrada = teclado.nextLine();
            while(entrada.compareTo("sair") != 0){
                entrada = teclado.nextLine();
            }
            objectOutputStream.close();
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
