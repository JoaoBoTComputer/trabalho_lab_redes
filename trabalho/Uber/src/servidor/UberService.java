package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import cliente.UsuarioApp;
import commom_class.Motorista;
import commom_class.Passageiro;



public class UberService implements Runnable{
    Socket cliente;
    static Queue<Motorista> filaMotoristas = new LinkedList<Motorista>();

    UberService(Socket cliente)
        {this.cliente = cliente;}

    @Override
    public void run() {
        try{
            System.out.println("Cliente "+cliente.getInetAddress().getHostAddress());
            ObjectInputStream objectInputStream = new ObjectInputStream(cliente.getInputStream());
            Object object = objectInputStream.readObject();

            UsuarioApp usuario = (UsuarioApp) object;

            if(usuario.getPlaca() == null){
                System.out.println("É passageiro!");
                Passageiro passageiro = new Passageiro(usuario.getNome(),cliente);
                buscarMotoristas(passageiro);

            }else{
                System.out.println("É motorista!");
                Motorista motorista = new Motorista(usuario.getNome(), usuario.getPlaca(),cliente);
                filaMotoristas.add(motorista);
            }        

        }
        catch(IOException e ){e.printStackTrace();} 
        catch (ClassNotFoundException e) {e.printStackTrace();}   
    }

    private void buscarMotoristas(Passageiro passageiro) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
        out.flush();

        if(filaMotoristas.isEmpty()){
            out.writeObject("221");
            out.flush();
            out.close();
            cliente.close();
        }
        else{
            Motorista motorista = filaMotoristas.poll();
            out.writeObject("200");
            out.writeObject(motorista.getNome());
            out.writeObject(motorista.getPlaca());
            out.writeObject(motorista.getCliente().getLocalSocketAddress());
            out.writeObject(motorista.getCliente().getLocalPort());
            out.flush();
            out.close();

            ObjectOutputStream outMotorista = new ObjectOutputStream(motorista.getCliente().getOutputStream());
            
            outMotorista.writeObject("200");
            outMotorista.writeObject("Nome passageiro :"+passageiro.getNome());
            outMotorista.flush();
            

        }
            
    }

}
