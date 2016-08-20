/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemastock.controller;

import com.jfoenix.controls.JFXTextField;
import java.io.Serializable;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import sistemastock.SistemaStock;
import sistemastock.model.Accion;
import sistemastock.model.Estado;

/**
 *
 * @author Tke Kaiser
 */

    public class Controller<T, Id extends Serializable> implements DAO<T, Id> {
        
    private Button btBuscar, btNew, btEdit, btDelete, btSave, btClear;
 
    private final Class<T> clazz;
    private Session currentSession;
    private Transaction currentTransaction;
     
    public Controller(Class<T> clazz) {
        this.clazz = clazz;
    
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return currentSession;
    }
    public Session openCurrentSessionwithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
        return currentSession;
    }   
    public void closeCurrentSession() {
        currentSession.close();
    }
    public void closeCurrentSessionwithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }   
    private static SessionFactory getSessionFactory() {
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
        return sessionFactory;
    }
 
    public Session getCurrentSession() {
        return currentSession;
    }
    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }
    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }
    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }
    
    
    public void persist(T t) {
        openCurrentSessionwithTransaction();
        getCurrentSession().save(t);
        closeCurrentSessionwithTransaction();
    }
 
    public T getById(Id id) {
        openCurrentSession();
        @SuppressWarnings("unchecked")
        T t = (T) getCurrentSession().get(clazz, id);
        closeCurrentSession();
        return t;
    }
 
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        openCurrentSession();
        List<T> list = (List<T>) getCurrentSession().createQuery("from " + clazz.getName()).list();
        closeCurrentSession();
        return list;
    }
 
    public void update(T t) {
        openCurrentSessionwithTransaction();
        getCurrentSession().update(t);
        closeCurrentSessionwithTransaction();
    }
 
    public void delete(T t) {
        openCurrentSessionwithTransaction();
        getCurrentSession().delete(t);
        closeCurrentSessionwithTransaction();
    }
     
    public void deleteAll() {
        openCurrentSessionwithTransaction();
        List<T> people = getAll();
        for (T t : people) {
            delete(t);
        }
        closeCurrentSessionwithTransaction();
    }
    
    
    public void limitarTextFields(JFXTextField tf, sistemastock.model.TipoTexto tipoDato, int LIMITE, Label label) {
        setFocusTextFields(tf, tipoDato, LIMITE, label);
        setKeyReleaseTextFields(tf, tipoDato, LIMITE);

    }

    public void setFocusTextFields(JFXTextField tf, sistemastock.model.TipoTexto tipoDato, int LIMITE, Label label) {
        tf.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {

            String exit = "";

            if (!newValue) {
                if (tf.getText().isEmpty()) {
                    exit += "No ha introducido datos";
                } else if (tf.getText().length() > LIMITE) {
                    exit += "Contiene mas de " + LIMITE + " carasteres. length: " + tf.getText().length();

                } else {

                    switch (tipoDato) {
                        case ALFABETICO:
                            if (!isAlfabetico(tf.getText())) {
                                exit += "No son Datos Alfabeticos";
                            }
                            break;

                        case NUMERICO:
                            if (!isNumerico(tf.getText())) {
                                exit += "No son datos numericos";
                            }
                            break;

                        case ALFANUMERICO:
                            if (!isAlfanumerico(tf.getText())) {
                                exit += "No son Datos Alfanumericos.";
                            }
                            break;

                        default:

                    }
                }
                
                label.setText(exit);
                label.setContentDisplay(ContentDisplay.RIGHT);
                if(!exit.isEmpty()){
                    label.setGraphic(new ImageView(new Image(SistemaStock.class.getResourceAsStream("icons/Cancel.png"))));
                } else{
                    label.setGraphic(new ImageView(new Image(SistemaStock.class.getResourceAsStream("icons/Accept.png"))));
                }
            }
        });

    }

    public void setKeyReleaseTextFields(JFXTextField tf, sistemastock.model.TipoTexto tipoDato, int LIMITE) {

        tf.setOnKeyTyped(event -> {

            char c = (char) event.getCharacter().charAt(0);

            tf.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                if (newValue.intValue() > oldValue.intValue()) {
                    if (tf.getText().length() > LIMITE) {
                        tf.setText(tf.getText().substring(0, LIMITE));
                    }
                }

            });
            switch (tipoDato) {
                case ALFABETICO:
                    if (c == 'º' || c == 'ª' || c == 'ç' || c == 'Ç' || !Character.isLetter(c)) {
                        event.consume();
                    }
                    break;

                case NUMERICO:
                    if (!Character.isDigit(c) || (tf.getText().length() == 0 && c == '0')) {
                        event.consume();
                    }
                    break;

                case ALFANUMERICO:
                    String ch = String.valueOf(c);
                    if (!ch.matches("[a-zA-Z\\d\\s]")) {
                        event.consume();
                    }
                    break;

                default:
                    System.out.println("Error en opcion");
                    break;
            }

        });

    }

    private static boolean isNumerico(String cadena) {
        boolean flat = false;
        flat = cadena.matches("[\\d]*");
        return flat;
    }

    public boolean isAlfabetico(String cadena) {
        boolean flat = false;
        flat = (cadena.matches("[a-zA-Z]*"));
        return flat;
    }

    public boolean isAlfanumerico(String cadena) {
        boolean flat = false;
        flat = cadena.matches("[a-zA-Z\\d\\s]*");
        return flat;
    }
    
 
   /* public void printToJson(List<T> l) {
         
        int size = l.size();
        System.out.println("[");
        for (T i : l) {
            System.out.println(((Model) i).toJson());
            if (--size != 0){System.out.print(",");}
        }
        System.out.print("]");          
    }*/
}