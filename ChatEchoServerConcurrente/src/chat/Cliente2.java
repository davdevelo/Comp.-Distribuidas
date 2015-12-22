
package chat;

/**
 *
 * @author David Velasco
 *         Carlos Sampedro
 */

import java.io.*;
import java.net.*;

public class Cliente2 
{
    static String hostName = "localhost";
    static int portNumber = 5566;
    
    public static void main(String[] args) throws IOException
    {

        try ( Socket         echoSocket = new Socket(hostName, portNumber);
              PrintWriter    out        = new PrintWriter(echoSocket.getOutputStream(), true);
              BufferedReader in         = new BufferedReader( new InputStreamReader(echoSocket.getInputStream()));
              BufferedReader stdIn      = new BufferedReader( new InputStreamReader(System.in))                    )
           {
                String userInput;
                System.out.println("Ingrese el mensaje a enviar: " );
                while ((userInput = stdIn.readLine()) != null)
                {
                    out.println(userInput);
                    System.out.println("Recibo echo: " + in.readLine());
                }
            }
        catch (UnknownHostException e)
            {
                System.err.println("Don't know about host " );
                System.exit(1);
            }
        catch (IOException e)
            {
            System.err.println("Couldn't get I/O for the connection to " );
            System.exit(1);
            }
    }
}
