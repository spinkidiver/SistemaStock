/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemastock.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sistemastock.SistemaStock;
import sistemastock.model.Accion;
import sistemastock.model.Categorias;
import sistemastock.model.Estado;
import sistemastock.model.TipoTexto;


public class CategoriasController implements DAO<Categorias, Integer>, Initializable{
    
    @FXML
    private TableView<Categorias> tvCategorias;
    
    @FXML
    private TableColumn<Categorias, String> tcNombre;

    @FXML
    private TableColumn<Categorias, Integer> tcId;
    
    @FXML
    private JFXTextField tfNombre, tfBusqueda, tfId;
    
    @FXML
    private Label lId, lNombre;

    @FXML
    private JFXButton btEdit, btDelete, btClear, btSave, btBuscar, btNew;    
    
    private int indexViejo = -1;
    
    private static Integer ID;
    private boolean isclickCntrl= false;

    
    Accion opcion = Accion.INIT;

    private static Controller<Categorias, Integer> controller;
    
    //Constructor
    public CategoriasController() {
        controller = new Controller<Categorias, Integer>(Categorias.class);
    }
    
    public Categorias getById(Integer id) {
        Categorias marcas = controller.getById(id);
        return marcas;
    }

    public List<Categorias> getAll() {
        List<Categorias> LMarcas = controller.getAll();
        return LMarcas;
    }

    public void persist(Categorias categoria) {
        controller.persist(categoria);
    }

    public void update(Categorias categoria) {
        controller.update(categoria);
    }

    public void deleteById(Integer id) {
        controller.delete(controller.getById(id));
    }

    public void delete(Categorias categoria) {
        controller.delete(categoria);
    }

    public void deleteAll() {
        controller.deleteAll();
    }
       
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ID = tvCategorias.getSelectionModel().getSelectedItems().size();
        
        setBotones(Accion.INIT);
        setTextFields(Estado.DESACTIVO);

        tvCategorias.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tvCategorias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> mostrarDetallesCategorias(newValue));

        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
                
        actualizarTablaCategorias();

        controller.limitarTextFields(tfId, TipoTexto.NUMERICO, 2, lId);
        controller.limitarTextFields(tfNombre, TipoTexto.ALFANUMERICO, 25, lNombre);
        

        //Setting controllers
        tvCategorias.setOnMouseClicked(this::handleTvClickReleased);
        tvCategorias.setOnKeyReleased(this::handleTvKeyReleased);
        btNew.setOnAction(this::handleBtNewAction);
        btEdit.setOnAction(this::handleBtEditAction);
        btDelete.setOnAction(this::handleBtDeleteAction);
        btSave.setOnAction(this::handleBtSaveAction);
        btClear.setOnAction(this::handleBtClearAction);
       
    }
    
    public void actualizarTablaCategorias() {
        tvCategorias.setItems(FXCollections.observableArrayList(getAll()));
    }
    
    private void handleTvKeyReleased(KeyEvent event) {

        if (event.getCode() == KeyCode.CONTROL) {
            event.consume();
            System.out.println("presion control");
            this.isclickCntrl = true;
        }

        setBotones(Accion.CLICKTV);

        int indexNuevo = tvCategorias.getSelectionModel().getSelectedIndex();

        int tamano = tvCategorias.getItems().size();

        int cantRowSelect = tvCategorias.getSelectionModel().getSelectedItems().size();

        System.out.println("indexViejo: " + indexViejo + " index Nuevo: " + indexNuevo + " tamaño: " + tamano + " cantRowSelect: " + cantRowSelect);

        if ((/*!isclickCntrl &&*/false && (indexViejo == indexNuevo) || (cantRowSelect != 1 && (indexNuevo == -1 /*|| indexNuevo == -1*/)))) {// || */tvUbicaciones.getSelectionModel().isSelected(indexViejo)) {
            System.out.println("borrar todo 1");
            tvCategorias.getSelectionModel().clearSelection();
            limpiarTextFields();
            setBotones(Accion.INIT);
            setTextFields(Estado.DESACTIVO);
            indexViejo = -1;

        } else if (cantRowSelect == 1 && indexNuevo >= 0 && indexNuevo < tamano) {
            System.out.println("seleccionar");
            mostrarDetallesCategorias(tvCategorias.getSelectionModel().getSelectedItem());
            indexViejo = indexNuevo;

        } else if (cantRowSelect != 1) {
            System.out.println("seleccion multiple");
            limpiarTextFields();
            setBotones(Accion.DELETE);
            setTextFields(Estado.DESACTIVO);
            indexViejo = indexNuevo;
        }
    }

    private void handleTvClickReleased(MouseEvent event) {
        setBotones(Accion.CLICKTV);

        int indexNuevo = tvCategorias.getSelectionModel().getSelectedIndex();

        int tamano = tvCategorias.getItems().size();

        int cantRowSelect = tvCategorias.getSelectionModel().getSelectedItems().size();

        System.out.println("indexViejo: " + indexViejo + " index Nuevo: " + indexNuevo + " tamaño: " + tamano + " cantRowSelect: " + cantRowSelect);

        if ((indexViejo == indexNuevo) || (cantRowSelect != 1 && (indexNuevo == -1))) {

            System.out.println("borrar todo 1");
            tvCategorias.getSelectionModel().clearSelection();
            limpiarTextFields();
            setBotones(Accion.INIT);
            setTextFields(Estado.DESACTIVO);
            indexViejo = -1;

        } else if (cantRowSelect == 1 && indexNuevo >= 0 && indexNuevo < tamano) {
            System.out.println("seleccionar");
            mostrarDetallesCategorias(tvCategorias.getSelectionModel().getSelectedItem());
            indexViejo = indexNuevo;

        } else if (cantRowSelect != 1) {
            System.out.println("seleccion multiple");
            limpiarTextFields();
            setBotones(Accion.DELETE);
            setTextFields(Estado.DESACTIVO);
            indexViejo = indexNuevo;
        }
    }
    
    private void mostrarDetallesCategorias(Categorias c) {
        tfId.textProperty().set(c.getId().toString());
        tfNombre.textProperty().set(c.getNombre());
    }
    
    private void handleBtNewAction(ActionEvent event) {
        opcion = Accion.NEW;
        setTextFields(Estado.ACTIVO);
        setBotones(Accion.NEW);
    }

    private void handleBtEditAction(ActionEvent event) {
        opcion = Accion.EDIT;
        setBotones(Accion.EDIT);
        setTextFields(Estado.ACTIVO);
    }

    private void handleBtSaveAction(ActionEvent event) {
        boolean reset= false;

        Categorias c = new Categorias();

        c.setId(Integer.parseInt(tfId.getText()));
        c.setNombre(tfNombre.getText());
      
        switch (opcion) {
            case NEW:
                if(validarDatos(c)){
                   c.setId(crearId());
                    agregarCategoria(c);
                    reset = true;
                }
                break;

            case EDIT:
                if(validarDatos(c)){
                    c.setId(Integer.parseInt(tfId.getText()));
                    editarCategoria(c);
                    reset = true;
                }
                break;

            default:
                System.out.println("Error en opcion");
                break;
        }
        
        if(reset){
            limpiarTextFields();
            setBotones(Accion.INIT);
            setTextFields(Estado.DESACTIVO);
            actualizarTablaCategorias();
            indexViejo = -1;
        }
    }
    
    private void agregarCategoria(Categorias c) {
        if(validarDatos(c))
            controller.persist(c);
    }

    public boolean validarDatos(Categorias c) {
        boolean flat= false;
        if((c.getNombre().toString().matches("[a-zA-Z]*") && c.getId().toString().matches("[\\d]*"))){
             flat = true;
             System.out.println("todo bn");
        }else{
            flat = false;
            System.out.println("Datos Mal formados");
        }
        
        return flat;
    }

    private void editarCategoria(Categorias c) {
        controller.update(c);
    }

    private Integer crearId() {
        this.ID += 1;
        
        return ID;
    }
    
    
    private void handleBtDeleteAction(ActionEvent event) {
        eliminarMarca(tvCategorias.getSelectionModel().getSelectedItems());
        limpiarTextFields();
        setBotones(Accion.INIT);
        setTextFields(Estado.DESACTIVO);
        actualizarTablaCategorias();
    }

    private void eliminarMarca(List<Categorias> categoria) {
        categoria.forEach(c -> controller.delete(c));
    }

    private void handleBtClearAction(ActionEvent event) {
        limpiarTextFields();
        setBotones(Accion.INIT);
        setTextFields(Estado.DESACTIVO);
        actualizarTablaCategorias();
        indexViejo = -1;
    }

    private void setTextFields(Estado estado) {
        tfId.setDisable(true);

        if (estado.getEstado()) {
            tfId.setDisable(false);
            tfNombre.setDisable(false);
            
        } else {
            tfId.setDisable(true);
            tfNombre.setDisable(true);
        }
    }

    public void setBotones(Accion action) {

        switch (action) {

            case INIT:
                btNew.setDisable(false);
                btEdit.setDisable(true);
                btDelete.setDisable(true);
                btSave.setDisable(true);
                break;

            case NEW:
                btNew.setDisable(true);
                btEdit.setDisable(true);
                btDelete.setDisable(true);
                btSave.setDisable(false);
                break;

            case CLICKTV:
                btNew.setDisable(true);
                btEdit.setDisable(false);
                btDelete.setDisable(false);
                btSave.setDisable(true);
                break;

            case EDIT:
                btNew.setDisable(true);
                btEdit.setDisable(true);
                btDelete.setDisable(false);
                btSave.setDisable(false);
                break;

            case DELETE:
                btNew.setDisable(true);
                btEdit.setDisable(true);
                btDelete.setDisable(false);
                btSave.setDisable(true);
                break;

            default:
                System.out.println("Error en Opcion");
                break;

        }
    }

    private void limpiarTextFields() {
        final String CLEAR = "";
        
        tfId.setText(CLEAR);
        tfNombre.setText(CLEAR);
        
        lId.setText(CLEAR);
        lNombre.setText(CLEAR);
        
    }

}
