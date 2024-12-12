/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Nicolás Cáceres Latorre
 */
public class Tarea {
    
    private int id;
    private String nombre;
    private String descripcion;
    private Boolean estado;
    
    public Tarea(){}
    
    public Tarea(String nombre,String descripcion,Boolean estado){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }
    
    public Tarea(int id,String nombre,String descripcion,Boolean estado){
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }
    
    public Tarea buscarTareaPorId(int id){
        
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Tarea tarea = null;
        
        try {
        // Establecer la conexión con la base de datos
        connection = Conexion.getConnection();

        // Consulta SQL para buscar la tarea por ID
        String sql = "SELECT * FROM tarea WHERE id = ?";

        // Preparar la declaración
        ps = connection.prepareStatement(sql);
        ps.setInt(1, id);

        // Ejecutar la consulta
        rs = ps.executeQuery();

        // Si se encuentra un resultado, crear el objeto Tarea
        if (rs.next()) {
            tarea = new Tarea();
            tarea.setId(rs.getInt("id"));
            tarea.setNombre(rs.getString("nombre"));
            tarea.setDescripcion(rs.getString("descripcion"));
            tarea.setEstado(rs.getBoolean("estado"));
        }
        } catch (SQLException ex) {
            System.out.println("Error en: " + ex.getMessage());
        } finally {
            try {
                // Cerrar los recursos
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (connection != null && !connection.isClosed()) connection.close();
            } catch (SQLException ex) {
                System.out.println("Error en: " + ex.getMessage());
            }
        }

        return tarea;
    }
    
    public boolean actualizarTarea(){
        
        Connection connection = null;
        PreparedStatement ps = null;
        
        try{
        
            //Establecer la conexión con la bd
            connection = Conexion.getConnection();
            
            //Preparar el Statement
            String url = "UPDATE tarea SET nombre = ?, descripcion = ? WHERE id = ?";
            
            ps = connection.prepareStatement(url);
            
            //Asignar los valores a los parametros
            ps.setString(1,this.nombre);
            ps.setString(2,this.descripcion);
            ps.setInt(3, this.id);
            
            //Ejecutamos la consulta
            int resultado = ps.executeUpdate();
            
            return resultado > 0;
            
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null,"Error en: "+ ex.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }finally{
            try{
            
                if(ps != null) ps.close();
                if(connection != null && !connection.isClosed()) connection.close();
            }catch(SQLException ex){
                System.out.println("Error en: "+ ex.getMessage());
            }
            
        }
    
    }
    
    /**
     * 
     * @return tareas
     * <> es parte del concepto genéricos en Java
     * Permite que colecciones como List,Set o Map trabajen con 
     * un tipo especifico de objetos
     * mejorando la claridad y seguridad del código
     */
    
    public List<Tarea> listarTareas(){
        Connection connection = null;
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        List<Tarea> tareas = new ArrayList<>();
        
        try{
        
            //Primero hago la conexion
            connection = Conexion.getConnection();
            
            String url = "SELECT * FROM tarea";
            ps = connection.prepareStatement(url);
            rs = ps.executeQuery();
            
            //Mientras haya algo mas 
            while(rs.next()){
                //Vamos a crear un objeto por cada uno
                Tarea tarea = new Tarea();
                tarea.setId(rs.getInt("id"));
                tarea.setNombre(rs.getString("nombre"));
                tarea.setDescripcion(rs.getString("descripcion"));
                tarea.setEstado(rs.getBoolean("estado"));
                //Finalmente guardo la tarea en mi lista
                tareas.add(tarea);
            }
            
        }catch(SQLException ex){
            System.out.println("Error en: " + ex.getMessage());
        } finally{
            try{
                if(rs != null) rs.close(); //Cierro mi lector
                if(ps != null) ps.close();
                if(connection != null && !connection.isClosed()) connection.close();
            }catch(SQLException ex){
                System.out.println("Error en: " + ex.getMessage());
            }
        }
        return tareas;
    }
    
    
    public boolean guardarTarea(){
    
        Connection connection = null;
        PreparedStatement ps = null;
        
        try{
            
            connection = Conexion.getConnection();
            
            String url = "INSERT INTO tarea(nombre,descripcion,estado) VALUES(?,?,?)";
            
            ps = connection.prepareStatement(url);
            
            ps.setString(1,this.nombre);
            ps.setString(2,this.descripcion);
            ps.setBoolean(3, true);
            
            int resultado = ps.executeUpdate();
            
            return resultado > 0;
            
        }catch(SQLException ex){
            System.out.println("Error en: " + ex.getMessage());
            return false;
        }finally{
            try{
                if(ps != null) ps.close();
                if(connection != null && !connection.isClosed()) connection.close();
            }catch(SQLException ex){
                System.out.println("Error en: " + ex.getMessage());
            }        
        }        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString(){
        return "Tarea = { id: "+ this.id + ", nombre: "+ this.nombre+", descripcion: "+ this.descripcion+", estado: "+ this.estado + "}";
    }
    
}
