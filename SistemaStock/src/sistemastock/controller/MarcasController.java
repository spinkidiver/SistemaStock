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
import sistemastock.model.Estado;
import sistemastock.model.Marcas;
import sistemastock.model.MiTextField;
import sistemastock.model.TipoTexto;

public class MarcasController implements DAO<Marcas, Integer>, Initializable {

    @FXML
    private TableView<Marcas> tvMarca;

    @FXML
    private TableColumn<Marcas, String> tcNombre;

    @FXML
    private TableColumn<Marcas, Integer> tcId;

    @FXML
    private JFXTextField tfNombre, tfBusqueda, tfId;

    @FXML
    private Label lId, lNombre;

    @FXML
    private JFXButton btEdit, btDelete, btClear, btSave, btBuscar, btNew;

    private int indexViejo = -1;
    private static Integer ID;
    private boolean isclickCntrl = false;

    Accion opcion = Accion.INIT;

    private static Controller<Marcas, Integer> controller;

    //Constructor
    public MarcasController() {
        controller = new Controller<Marcas, Integer>(Marcas.class);
    }

    @Override
    public Marcas getById(Integer id) {
        Marcas marcas = controller.getById(id);
        return marcas;
    }

    @Override
    public List<Marcas> getAll() {
        List<Marcas> LMarcas = controller.getAll();
        return LMarcas;
    }

    @Override
    public void persist(Marcas marcas) {
        controller.persist(marcas);
    }

    @Override
    public void update(Marcas marcas) {
        controller.update(marcas);
    }

    public void deleteById(Integer id) {
        controller.delete(controller.getById(id));
    }

    @Override
    public void delete(Marcas marcas) {
        controller.delete(marcas);
    }

    @Override
    public void deleteAll() {
        controller.deleteAll();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ID = tvMarca.getSelectionModel().getSelectedItems().size();

        setBotones(Accion.INIT);
        setTextFields(Estado.DESACTIVO);

        tvMarca.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tvMarca.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> mostrarDetallesMarcas(newValue));

        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());

        actualizarTablaMarcas();

        controller.limitarTextFields(tfId, TipoTexto.NUMERICO, 2, lId);
        controller.limitarTextFields(tfNombre, TipoTexto.ALFANUMERICO, 25, lNombre);

        //Setting controllers
        tvMarca.setOnMouseClicked(this::handleTvClickReleased);
        tvMarca.setOnKeyReleased(this::handleTvKeyReleased);
        btNew.setOnAction(this::handleBtNewAction);
        btEdit.setOnAction(this::handleBtEditAction);
        btDelete.setOnAction(this::handleBtDeleteAction);
        btSave.setOnAction(this::handleBtSaveAction);
        btClear.setOnAction(this::handleBtClearAction);

    }

    public void actualizarTablaMarcas() {
        tvMarca.setItems(FXCollections.observableArrayList(getAll()));
    }

    private void handleTvKeyReleased(KeyEvent event) {

        if (event.getCode() == KeyCode.CONTROL) {
            event.consume();
            System.out.println("presion control");
            this.isclickCntrl = true;
        }

        setBotones(Accion.CLICKTV);

        int indexNuevo = tvMarca.getSelectionModel().getSelectedIndex();

        int tamano = tvMarca.getItems().size();

        int cantRowSelect = tvMarca.getSelectionModel().getSelectedItems().size();

        System.out.println("indexViejo: " + indexViejo + " index Nuevo: " + indexNuevo + " tamaño: " + tamano + " cantRowSelect: " + cantRowSelect);

        if ((/*!isclickCntrl &&*/false && (indexViejo == indexNuevo) || (cantRowSelect != 1 && (indexNuevo == -1 /*|| indexNuevo == -1*/)))) {// || */tvUbicaciones.getSelectionModel().isSelected(indexViejo)) {
            System.out.println("borrar todo 1");
            tvMarca.getSelectionModel().clearSelection();
            limpiarTextFields();
            setBotones(Accion.INIT);
            setTextFields(Estado.DESACTIVO);
            indexViejo = -1;

        } else if (cantRowSelect == 1 && indexNuevo >= 0 && indexNuevo < tamano) {
            System.out.println("seleccionar");
            mostrarDetallesMarcas(tvMarca.getSelectionModel().getSelectedItem());
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

        int indexNuevo = tvMarca.getSelectionModel().getSelectedIndex();

        int tamano = tvMarca.getItems().size();

        int cantRowSelect = tvMarca.getSelectionModel().getSelectedItems().size();

        System.out.println("indexViejo: " + indexViejo + " index Nuevo: " + indexNuevo + " tamaño: " + tamano + " cantRowSelect: " + cantRowSelect);

        if ((indexViejo == indexNuevo) || (cantRowSelect != 1 && (indexNuevo == -1))) {

            System.out.println("borrar todo 1");
            tvMarca.getSelectionModel().clearSelection();
            limpiarTextFields();
            setBotones(Accion.INIT);
            setTextFields(Estado.DESACTIVO);
            indexViejo = -1;

        } else if (cantRowSelect == 1 && indexNuevo >= 0 && indexNuevo < tamano) {
            System.out.println("seleccionar");
            mostrarDetallesMarcas(tvMarca.getSelectionModel().getSelectedItem());
            indexViejo = indexNuevo;

        } else if (cantRowSelect != 1) {
            System.out.println("seleccion multiple");
            limpiarTextFields();
            setBotones(Accion.DELETE);
            setTextFields(Estado.DESACTIVO);
            indexViejo = indexNuevo;
        }
    }

    private void mostrarDetallesMarcas(Marcas m) {
        tfId.textProperty().set(m.getId().toString());
        tfNombre.textProperty().set(m.getNombre());
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
        boolean reset = false;

        Marcas m = new Marcas();

        m.setId(Integer.parseInt(tfId.getText()));
        m.setNombre(tfNombre.getText());

        switch (opcion) {
            case NEW:
                if (validarDatos(m)) {
                    m.setId(crearId());
                    agregarUbicacion(m);
                    reset = true;
                }
                break;

            case EDIT:
                if (validarDatos(m)) {
                    m.setId(Integer.parseInt(tfId.getText()));
                    editarMarca(m);
                    reset = true;
                }
                break;

            default:
                System.out.println("Error en opcion");
                break;
        }

        if (reset) {
            limpiarTextFields();
            setBotones(Accion.INIT);
            setTextFields(Estado.DESACTIVO);
            actualizarTablaMarcas();
            indexViejo = -1;
        }
    }

    private void agregarUbicacion(Marcas m) {
        if (validarDatos(m)) {
            controller.persist(m);
        }
    }

    public boolean validarDatos(Marcas m) {
        boolean flat = false;
        if ((m.getNombre().matches("[a-zA-Z]*") && m.getId().toString().matches("[\\d]*"))) {
            flat = true;
            System.out.println("todo bn");
        } else {
            flat = false;
            System.out.println("Datos Mal formados");
        }

        return flat;
    }

    private void editarMarca(Marcas m) {
        controller.update(m);
    }

    private Integer crearId() {
        this.ID += 1;

        return ID;
    }

    private void handleBtDeleteAction(ActionEvent event) {
        eliminarMarca(tvMarca.getSelectionModel().getSelectedItems());
        limpiarTextFields();
        setBotones(Accion.INIT);
        setTextFields(Estado.DESACTIVO);
        actualizarTablaMarcas();
    }

    private void eliminarMarca(List<Marcas> marcas) {
        marcas.forEach(m -> controller.delete(m));
    }

    private void handleBtClearAction(ActionEvent event) {
        limpiarTextFields();
        setBotones(Accion.INIT);
        setTextFields(Estado.DESACTIVO);
        actualizarTablaMarcas();
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
        lId.setGraphic(null);
        
        lNombre.setText(CLEAR);
        lNombre.setGraphic(null);

    }

}
