/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;

import modelo.Tarea;
import excepciones.TareaException;

/**
 *
 * @author Nicolás Cáceres Latorre
 */
public class TareaControlador {
    
      /**
     * Crea y guarda una tarea en la base de datos.
     *
     * @param nombre      Nombre de la tarea.
     * @param descripcion Descripción de la tarea.
     * @param estado      Estado de la tarea (true para activa, false para inactiva).
     * @return true si la tarea se guardó correctamente, false en caso contrario.
     * @throws TareaException Si los datos son inválidos.
     */
    
    public boolean crearTarea(String nombre,String descripcion) throws TareaException{
    
        //Validar datos
        validarDatosTarea(nombre, descripcion);
        
        //Crear instancia del modelo
        Tarea tarea = new Tarea(nombre,descripcion,true);
        
        if(tarea.guardarTarea()){
            
            return true;
        }else{
            throw new TareaException("No se pudo guardar la tarea en la base de datos");
        }
    }
    
    /**
     * Método para buscar una tarea por ID.
     * @param id Identificador de la tarea.
     * @return Tarea encontrada o mensaje si no existe.
     */
    public Tarea buscarTarea(int id) {
        // Crear una instancia de Tarea
        Tarea tarea = new Tarea();

        // Llamar al método del modelo para buscar la tarea
        tarea = tarea.buscarTareaPorId(id);

        // Retornar la tarea encontrada o null si no existe
        return tarea;
    }
    
    public String actualizarTarea(int id,String nombre,String descripcion,boolean estado){
    
        //Creamos el objeto tarea con los datos entregados
        Tarea tarea = new Tarea(id,nombre,descripcion,estado);
        
        //Vamos a llamar el metodo para actualizar la tarea
        if(tarea.actualizarTarea()){
            return "Tarea editada satisfactoriamente";
        }else{
            return "No se pudo actualizar la tarea, revise los datos";
        }
        
    }
    
    /**
     * 
     * @return Lista de tareas
     * @throws TareaException  SI ocurre un error al obtener las tareas
     */
    public List<Tarea> listarTareas() throws TareaException{
        Tarea tarea = new Tarea();
        List<Tarea> tareas = tarea.listarTareas();
        
        if(tareas.isEmpty()){
            throw new TareaException("No se encontraron tareas en la base de datos");
        }
        
        return tareas;
    }
    
    private void validarDatosTarea(String nombre,String descripcion) throws TareaException{
        if(nombre == null || nombre.trim().isEmpty()){
            throw new TareaException("El nombre de la tarea no puede estar vacio");
        }
        if(descripcion == null || descripcion.trim().isEmpty()){
            throw new TareaException("La descripcion de la tarea no puede estar vacia");
        }
    }
    
}
