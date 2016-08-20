/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemastock.controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import sistemastock.SistemaStock;

/**
 * FXML Controller class
 *
 * @author Tke Kaiser
 */
public class RootLayoutController  {

    private SistemaStock sistemaStock;
    
    @FXML
    private BorderPane bordePane;
            
    @FXML
    private Tab tab;
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    private double anchoPantalla = screenSize.getWidth();
    double altoPantalla = screenSize.getHeight();
    
    private double anchoVentana = anchoPantalla * 0.70;
    double altoVentana = altoPantalla * 0.70;
    
    
    //@Override
    public void initialize() {
        bordePane.setPrefWidth(anchoVentana);
        bordePane.setPrefHeight(altoVentana);
        
        //tab.setContent();
    }    

    
    
     public double getAnchoPantalla() {
        return anchoPantalla;
    }

    public double getAltoPantalla() {
        return altoPantalla;
    }

    public double getAnchoVentana() {
        return anchoVentana;
    }

    public double getAltoVentana() {
        return altoVentana;
    }
  

    public void setSistemaStock(SistemaStock sistemaStock) {
        this.sistemaStock = sistemaStock;
    }
    
    
   
    
}
