package cliente;


import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class App  {

    //tipoUsuario=0 implica igual Motorista
    //tipoUsuario=1 implica igual usuario
    private byte tipoUsuario=1;
    private final int PORT = 4242;
    private final String SERVER_ADRESS = "127.0.0.1";
    private Socket cliente;


    UsuarioApp usuario;


    public static void main(String[] args) throws IOException{
        App app = new App();
        app.start(); 
    }
    
    private void start() throws IOException{
        construirUsuario();
        connectarServidor();
        enviarUsuarioServidor();

        if(tipoUsuario == 1)
            buscarMotorista();
        else{
            System.out.println("Aqui!");
            boolean teste = true;
            ObjectInputStream sCliIn = new ObjectInputStream(cliente.getInputStream());
            String strMsg;
            try {
                strMsg = sCliIn.readObject().toString();

                if(strMsg.equals("200")){
                    System.out.println("merda");
                    new Thread( new Runnable(){

                        @Override
                        public void run() {
                           System.out.println("thread1 do motorista que mantera a comunicação com Uber");
                            while(true);
                        }
                        
                    }).start();;

                    new Thread(new Runnable(){

                        @Override
                        public void run() {
                            System.out.println("thread1 do motorista que mantera a comunicação com Passageiro");
                            while(true);
                            
                        }
                        
                    }).start();

                    System.out.println("Essa thread é a principal e foi encerrada");
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
          
        }

    }

    private void enviarUsuarioServidor() throws IOException{
        ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
        saida.writeObject(usuario);
        saida.flush();    
    }

    private void buscarMotorista() throws IOException{
        ObjectInputStream sCliIn = new ObjectInputStream(cliente.getInputStream());
        
        System.out.println("Procurando Motorista!");
            try {
                String strMsg = sCliIn.readObject().toString();
                if(strMsg.equals("221")){
                    System.out.println("Nenhum motorista encontrado!");
                    sCliIn.close();
                    cliente.close();
                    
                }
                else if (strMsg.equals("200")){

                    String nome, placa, enderecoIP;
                    short porta;
                    System.out.println("Motorista encontrado");
                    
                    nome = sCliIn.readObject().toString();
                    placa = sCliIn.readObject().toString();
                    enderecoIP = sCliIn.readObject().toString();
                    porta = Short.parseShort(sCliIn.readObject().toString());
                    
                    System.out.println("Nome motorista: "+nome);
                    System.out.println("Placa carro: "+placa);
                }
                
            } catch (ClassNotFoundException e) {
                System.out.println("Erro ao receber mensagem do servidor!");
                e.printStackTrace();
            }
    }

    private void construirUsuario(){
        Scanner teclado = new Scanner(System.in);
        String nome,placa = null;
        byte motoristaOuPassegeiro;

        System.out.println("Sistema Uber inicializado!");
        System.out.print("Insera seu nome:");
        nome = teclado.nextLine();
        System.out.print("Insira 0 - Motorista ou 1 - Passegeiro:");
        motoristaOuPassegeiro = teclado.nextByte();
        
        if(motoristaOuPassegeiro == 0){
            teclado.nextLine();
            System.out.print("Insira a placa do carro:");
            placa = teclado.nextLine();
            tipoUsuario = 0;
            
        }
        usuario = (placa == null ? new UsuarioApp(nome): new UsuarioApp(nome,placa)); 
        
    }

    private void connectarServidor(){
        try{
            cliente = new Socket(SERVER_ADRESS,PORT);
            System.out.println("Cliente se conectou ao servidor");
        }
        catch(Exception e){
            System.out.println("Error: Cliente não conseguiu se conectar ao servidor!");
            e.printStackTrace();
        }
    }

}
