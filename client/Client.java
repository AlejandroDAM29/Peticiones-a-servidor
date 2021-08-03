

package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;



public class Client {
    
    
    //Método para enviar y recibir información de la clase cliente
    public void conexion(String mensaje){
        
        //Campos del método
        final String HOST = "localhost";
        final int PUERTO = 9876;
        Socket puente;
        DataOutputStream salida_datos;
        DataInputStream entrada_datos;
        
        
        try {
            
            //Aquí hago referencia a la red local
            puente = new Socket(HOST,PUERTO);
           
            //Creación de flujo de entrada y salida de datos
            entrada_datos = new DataInputStream(puente.getInputStream());
            salida_datos = new DataOutputStream(puente.getOutputStream());
            
            //Escribo al servidor el mensaje que pasa el usuario al cliente
            salida_datos.writeUTF(mensaje);
            
            //Ahora leo el mensaje del servidor con la hora actual
            String lectura = entrada_datos.readUTF();
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
            System.out.println(lectura+" "+dtf2.format(LocalDateTime.now()));
            
            //Cierro la conexión del socket
            puente.close();
            
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//Fin del método conexion
    
}
