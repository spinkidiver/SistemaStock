/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemastock.model;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sistemastock.SistemaStock;
import  sistemastock.model.TipoTexto;
import sistemastock.controller.MarcasController;

/**
 *
 * @author Tke Kaiser
 */
public class MiTextField extends JFXTextField{
   
    private enum Estado {
        ACTIVO(true), DESACTIVO(false);
        boolean estado;

        private Estado(boolean estado) {
            this.estado = estado;
        }

        public boolean getEstado() {
            return estado;
        }
    };

    
        
    
    public void limitarTextFields(JFXTextField tf, TipoTexto tipoDato, int LIMITE, Label label) {
        setFocusTextFields(tf, tipoDato, LIMITE, label);
        setKeyReleaseTextFields(tf, tipoDato, LIMITE);

    }

    public void setFocusTextFields(JFXTextField tf, TipoTexto tipoDato, int LIMITE, Label label) {
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

                label.setGraphic(new ImageView(new Image(SistemaStock.class.getResourceAsStream("icons/Cancel.png"))));
                label.setContentDisplay(ContentDisplay.RIGHT);

                }
        });

    }

    public void setKeyReleaseTextFields(JFXTextField tf, TipoTexto tipoDato, int LIMITE) {

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
    
    public boolean isAlfabetico(String cadena){
        boolean flat= false;
        flat = (cadena.matches("[a-zA-Z]*"));
        return flat;
    }
    
    
    public boolean isAlfanumerico(String cadena){
        boolean flat = false;
        flat = cadena.matches("[a-zA-Z\\d\\s]*");
        return flat;
    }
    
}
