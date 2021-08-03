
package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {    
    
    //Campos de clase
    ServerSocket servidor = null;
    String lectura;
    
    
    //Método usado para cerrar la sesión una vez se hayan enviado todos los mensajes
    public void cerrarConexion() throws IOException{
        servidor.close();
    }
    
    
   //Método principal, el cual se encargará de establecer la comunicación con el cliente 
   public void conexion(){    
         
     //Campos del método  
     Socket puente;
     DataInputStream entrada_datos;
     DataOutputStream salida_datos;
     final int PUERTO = 9876;
     int i=0,contador=0,numeroCliente=0,contador_tareas=0;
     boolean descripcion_estado = true,carga_datos = false, bucle = true;
     Tarea[] misTareas=null;
     String descripcion=null,datos_envio="\nREGISTRO DE TAREAS\n";
     
      try {
            /*Pongo el servidor a la escucha. Con el bucle while siempre estará
            escuchando hasta que se le indique lo contrario*/
            servidor = new ServerSocket(PUERTO);
      
           while(bucle){     
             
                //Acepto la información que me llega del cliente
                puente = servidor.accept();
                
                //Creo los flujos de entrada y salida de datos
                entrada_datos = new DataInputStream(puente.getInputStream());
                salida_datos = new DataOutputStream(puente.getOutputStream());
                
                /*Aquí el servidor se va a quedar a la espera de que el cliente le
                mande información. Con DateTimeFormatter indico la hora actual para
                tener una idea más clara del orden de la secuencia de mensajes*/
                lectura = entrada_datos.readUTF();
                DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                System.out.println(lectura+" "+dtf2.format(LocalDateTime.now()));
                
                /*Array que determinará qué mensaje lanzará el servidor al cliente
                dependiendo de en qué vuelta de bucle se encuente. Esto se verá gracias
                a la variable "i"*/
                String[] peticiones_servidor = {"Dime tu nombre",
                    "Hola "+lectura+". ¿Cuántas tareas quieres realizar?",
                    "Ha seleccionado "+lectura+" tareas a realizar:"};
                
                
                /*Cuando la "i" llega a 2, coincidirá con la vuelta de bucle en que la
                variable lectura, que es la que lee los datos del cliente, lea el número
                de tareas que el cliente quiere realizar*/
                if(i==2){
                    numeroCliente = Integer.parseInt(lectura);
                    
                    /*Una vez que tengo el número de tareas, ya puedo crear un
                    array con el tamaño que indica el cliente para almacenar las tareas*/
                    misTareas=new Tarea[numeroCliente+1];
                }
                
                
                /*Si el booleano "carga_datos" es positivo, significa que ya se habrán
                cargado en memoria los datos suficientes para construir un objeto de
                tipo Tarea. Se van introduciendo estos objetos en el array*/
                if(carga_datos){
                    misTareas[contador_tareas] = new Tarea(descripcion,lectura);
                    contador_tareas++;
                    carga_datos=false;
                }
                
                
                /*Dependiendo de la vuelta de bucle en la que nos encontremos, el flujo
                de datos entrará en una opción u otra de estos condicionales. A continuación
                explico cada condición*/
                
                if(i<2){
                /*Se ejecutarán los mensajes del array "peticiones_servidor" hasta que el
                usuario introduzca su nombre*/
                salida_datos.writeUTF(peticiones_servidor[i]);
                }else if(contador==numeroCliente){
                    
                    /*Si el cliente ya ha rellenado todas las tareas, se le mandará
                    un mensaje para advertir que se le enviarán los datos*/
                    System.out.println(misTareas[contador-1].getDescripcion());
                    salida_datos.writeUTF("\nEnviando los datos de las tareas...");
                    //Duermo 3 segundos el programa para simular que se están preparando los datos
                    sleep(3000);
                    contador++;
                }else if(contador==numeroCliente+1){
                    
                    /*Después de enviar el mensaje de preparación de los datos entrará
                    en esta condición. Se guardan los datos del array que contiene las
                    tareas en un String. Luego se escriben al cliente.*/
                     for(int j=0;j<misTareas.length-1;j++){
                         datos_envio +="\nTarea "
                                 +(j+1)+": "
                                 +misTareas[j].getDescripcion()+". Estado: "
                                 +misTareas[j].getEstado();
                     }//Fin de bucle for
                     salida_datos.writeUTF(datos_envio+"\n\nHora envío lista tareas:");
                     /*El valor false le indica al bucle que no debe seguir, ya 
                     que todo está hecho. Provocará que el servidor deje de estar
                     a la escucha*/
                     bucle = false;
                }else if(descripcion_estado){
                    
                     /*Se le pide al cliente la descripcion de la tarea. Se cambia el
                     valor del booleano "descripcion_estado para el servidor haga peticiones
                     alternativas entre la descripcion de la tarea y su estado en cada
                     vuelta de bucle*/
                     salida_datos.writeUTF("Tarea "+(contador+1)+"\nPor favor, describe la tarea:");
                     descripcion_estado = false;
                }else if(!descripcion_estado){
                    
                     //Se pide al cliente que describa el estado de la tarea
                     descripcion = lectura;
                     salida_datos.writeUTF("Escribe el estado de la tarea "+(contador+1));
                     descripcion_estado = true;
                     /*Esto indica que se tienen los datos suficientes para crear
                     un objeto de la clase tarea*/
                     carga_datos = true;
                     /*Este contador da número a cada tarea y controla que se hayan
                     rellenado todas antes de enviar el mensaje al cliente con todos
                     los datos del arary misTareas*/
                     contador++;
                 }
                
              
                /*El contador se suma para que el programa identifique el momento
                en el que se encuentra la conversación servidor-cliente*/
                i++;
                
                //Se cierra el puente de la conexión con el cliente
                puente.close();
           }//Fin del bucle while
                        
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
      
   }//Fin de método conexion
   
}
