package sistemastock.model;
// Generated Aug 2, 2016 6:30:38 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Categorias  implements java.io.Serializable {

    
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private Set productoses = new HashSet(0);

    public Integer getId() {
        return id.get();
    }

    public void setId(Integer value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }
    

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String value) {
        nombre.set(value);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    
    public Set getProductoses() {
        return this.productoses;
    }
    
    public void setProductoses(Set productoses) {
        this.productoses = productoses;
    }




}


