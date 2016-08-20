package sistemastock.controller;
// Generated Jul 7, 2016 7:40:32 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Distribuidores generated by hbm2java
 */
public class Distribuidores  implements java.io.Serializable {


     private Integer id;
     private String nombre;
     private String email;
     private String tlf;
     private Set productoses = new HashSet(0);

    public Distribuidores() {
    }

    public Distribuidores(String nombre, String email, String tlf, Set productoses) {
       this.nombre = nombre;
       this.email = email;
       this.tlf = tlf;
       this.productoses = productoses;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTlf() {
        return this.tlf;
    }
    
    public void setTlf(String tlf) {
        this.tlf = tlf;
    }
    public Set getProductoses() {
        return this.productoses;
    }
    
    public void setProductoses(Set productoses) {
        this.productoses = productoses;
    }




}


