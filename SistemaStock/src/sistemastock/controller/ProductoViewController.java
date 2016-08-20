/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistemastock.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import sistemastock.SistemaStock;

/**
 *
 * @author Tke Kaiser
 */
public class ProductoViewController implements Initializable{
    
    @FXML
    private Tab tabUbicaciones, tabMarcas, tabCategorias;
	
    
    public void initialize(URL url, ResourceBundle rb){
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SistemaStock.class.getResource("view/UbicacionesView.fxml"));
            AnchorPane ubicacionesView = (AnchorPane) loader.load();

            
            tabUbicaciones.setContent(ubicacionesView);
           
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SistemaStock.class.getResource("view/MarcasView.fxml"));
            AnchorPane marcasView = (AnchorPane) loader.load();

            
            tabMarcas.setContent(marcasView);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(SistemaStock.class.getResource("view/CategoriasView.fxml"));
            AnchorPane categoriasView = (AnchorPane) loader.load();

            
            tabCategorias.setContent(categoriasView);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    
    }
}

