
package chat;

/**
 *
 * @author David Velasco
 *         Carlos Sampedro
 */

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class Servidor 
{
    public static void main(String args[]) throws IOException 
    {
        int portNumber = 5566;        
        
        System.out.print("Inicializando servidor... ");
        
        try 
        {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("\t[OK]");
            int idSession = 0;
            
            while (true) 
            {
                Socket socket;
                socket = serverSocket.accept();
                System.out.println("Nueva conexión entrante: "+socket);
                ((ServidorHilo) new ServidorHilo(socket, idSession)).start();
                idSession++;
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}