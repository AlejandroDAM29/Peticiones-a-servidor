
package client;

//Heredo de la clase Thread para poder usar los métodos de sincronización de hilos

import java.util.Scanner;


public class Main{
    public static void main(String[] args){
        
        //Objeto de la clase Scanner para introducir los datos que pide el servidor
        Scanner S = new Scanner(System.in);
        String mensaje;
        
        //Declaro clase Cliente
        Client mi_cliente = new Client();
        
        //Mensaje para indicar que me conecto a la red
        System.out.println("Cliente se conecta al servidor");
        
        //Petición para establecer la comunicación
        mi_cliente.conexion("");
        
        //Se introduce el nombre del usuario
        mi_cliente.conexion(new Scanner(System.in).nextLine());
        
        //Se indica el número de tareas que se quieren realizar
        String numero_tareas = new Scanner(System.in).nextLine();
        mi_cliente.conexion(numero_tareas);
        
        /*El bucle lanzará 2 peticiones por tarea: una para la descricpion
        de la tarea y otra para el estado.*/
        for(int i=0;i<Integer.parseInt(numero_tareas)*2;i++){
          mi_cliente.conexion(new Scanner(System.in).nextLine());
        }
        
        //Cliente realiza la petición para recibir toda la información
        mi_cliente.conexion("");
    }//Fin del método main
}



