package cliente;


import java.io.BufferedWriter;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketOption;
import java.util.Scanner;

import jdk.net.Sockets;


public class App  {

    //GERENCIAR PORTAS PARA PEER TO PEER PASSAGEIRO E MOTORISTA
    //POR CONTA DE ESTARMOS UTILIZANDO 


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
            esperarPassageiro();
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
                    int porta;
                    System.out.println("Motorista encontrado");
                    
                    nome = sCliIn.readObject().toString();
                    placa = sCliIn.readObject().toString();
                    enderecoIP = sCliIn.readObject().toString().replace("/","");
                    porta = Integer.parseInt(sCliIn.readObject().toString());
                    
                    System.out.println("Nome motorista: "+nome);
                    System.out.println("Placa carro: "+placa);


                
                    Socket coneccaoMotorista = new Socket(enderecoIP,porta);
                    System.out.println("Inicializou peer to peer com o motorista!"+enderecoIP+"Porta:"+porta);
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

    private void esperarPassageiro() throws IOException{
        
        ObjectInputStream sCliIn = new ObjectInputStream(cliente.getInputStream());
        String strMsg;
        
        try{
            strMsg = sCliIn.readObject().toString();

            if(strMsg.equals("200")){
                new Thread( new Runnable(){

                    @Override
                    public void run() {
                        ServerSocket servidorMotorista = null;
                        try {
                            servidorMotorista = new ServerSocket(0);
                            int portMotoristaServidor =  servidorMotorista.getLocalPort();

                            System.out.println("Servidor motorista escurando na porta: "+portMotoristaServidor);

                            ObjectOutputStream sendToUber = new ObjectOutputStream(cliente.getOutputStream());

                            // PrintWriter sendToUber = new PrintWriter(cliente.getOutputStream());

                            sendToUber.writeObject("200");
                            sendToUber.writeObject(portMotoristaServidor);
                            sendToUber.flush();

                            // BufferedWriter sendToUber = new BufferedWriter(new OutputStreamWriter(cliente.getOutputStream()));

                            Socket coneccaoPassageiro = servidorMotorista.accept();
                            System.out.println("Passageiro conectado!");


                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        finally{
                            try {
                                if(servidorMotorista !=null)
                                    servidorMotorista.close();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

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

