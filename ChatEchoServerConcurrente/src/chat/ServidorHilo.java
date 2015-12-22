
package chat;

/**
 *
 * @author David Velasco
 */

import java.io.*;
import java.net.*;
import java.util.logging.*;

public class ServidorHilo extends Thread 
{
    private Socket socket;
    private int idSessio;
    PrintWriter    outC;
    BufferedReader inC;
            
    public ServidorHilo(Socket socketHilo, int idHilo) 
    {
        this.socket = socketHilo;
        this.idSessio = idHilo;
        
        try {
            outC  = new PrintWriter( socketHilo.getOutputStream(), true);
            inC   = new BufferedReader( new InputStreamReader(socketHilo.getInputStream()));  
        } 
        catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    @Override
    public void run() 
    {
        try {
            String mensaje;

            while ((mensaje = inC.readLine()) != null)
                {
                    outC.println(mensaje);
                    System.out.println("El cliente con idSesion "+this.idSessio+" envio : "+ mensaje);
                }
            
            } 
        catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            socket.close();
        } 
        catch (IOException ex) {
            Logger.getLogger(ServidorHilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}