
package server;

public class Tarea {
    
    //Campos de clase
    private String descripción,estado;
    private int numero_tarea;
    
    //Constructor de clase
    public Tarea(String descripción,String estado){
        this.descripción =descripción;
        this.estado = estado;
      
    }

    
    /*MÉTODOS GET
    ---------------------------------------------------------------------------*/
    public String getDescripcion() {
        return descripción;
    }

    public String getEstado() {
        return estado;
    }
    
    
    
    
}
