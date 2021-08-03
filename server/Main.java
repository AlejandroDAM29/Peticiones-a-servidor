
package server;

import java.io.IOException;

public class Main{

   //Lanzo una excepcion "IOException" porque trabajo con entrada_salida de datos
   public static void main(String[] args)  throws IOException{
        
        
        //Declaro clase servidor
        Server ser = new Server();
        
        //Mensaje para mostrar que servidor se va a iniciar
        System.out.println("Inicio servidor");
        
        //Inicio el servidor
        ser.conexion();
        
        //Cierro la conexión del servidor        
        ser.cerrarConexion();
    
    }
    
}
