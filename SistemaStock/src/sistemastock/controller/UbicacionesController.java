package sistemastock.controller;

import sistemastock.model.Ubicaciones;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import sistemastock.model.TipoTexto;

public class UbicacionesController implements DAO<Ubicaciones, Integer>, Initializable {

    private int indexViejo = -1;
    
    Accion opcion = Accion.INIT;

    private static Controller<Ubicaciones, Integer> controller;

    @FXML
    private Button btBuscar, btNew, btEdit, btDelete, btSave, btClear;

    @FXML
    private JFXTextField tfEstante, tfBusqueda, tfPasillo, tfRepisa, tfId;

    @FXML
    private TableView<Ubicaciones> tvUbicaciones;

    @FXML
    private Label lId, lPasillo, lEstante, lRepisa;

    @FXML
    private TableColumn<Ubicaciones, String> tcId, tcPasillo, tcEstante;

    @FXML
    private TableColumn<Ubicaciones, Integer> tcRepisa;

    public Ubicaciones getById(Integer id) {
        Ubicaciones ubicaciones = controller.getById(id);
        return ubicaciones;
    }

    public List<Ubicaciones> getAll() {
        List<Ubicaciones> LUbicaciones = controller.getAll();
        return LUbicaciones;
    }

    //Constructor
    public UbicacionesController() {
        controller = new Controller<Ubicaciones, Integer>(Ubicaciones.class);
    }

    public void persist(Ubicaciones ubicaciones) {
        controller.persist(ubicaciones);
    }

    public void update(Ubicaciones ubicaciones) {
        controller.update(ubicaciones);
    }

    public void deleteById(Integer id) {
        controller.delete(controller.getById(id));
    }

    public void delete(Ubicaciones ubicaciones) {
        controller.delete(ubicaciones);
    }

    public void deleteAll() {
        controller.deleteAll();
    }

    public void initialize(URL url, ResourceBundle rb) {

        setBotones(Accion.INIT);
        setTextFields(Estado.DESACTIVO);

        tvUbicaciones.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tvUbicaciones.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> mostrarDetallesUbicaciones(newValue));

        tcId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        tcPasillo.setCellValueFactory(cellData -> cellData.getValue().pasilloProperty());
        tcEstante.setCellValueFactory(cellData -> cellData.getValue().estanteProperty());
        tcRepisa.setCellValueFactory(new PropertyValueFactory<>("repisa"));
        actualizarTablaUbicaciones();

        limitarTextFields(tfPasillo, TipoTexto.ALFABETICO, 2, lPasillo);
        limitarTextFields(tfEstante, TipoTexto.ALFABETICO, 2,lEstante);
        limitarTextFields(tfRepisa, TipoTexto.NUMERICO, 2, lRepisa);

        //Setting controllers
        tvUbicaciones.setOnMouseClicked(this::handleTvClickReleased);
        tvUbicaciones.setOnKeyReleased(this::handleTvKeyReleased);
        btNew.setOnAction(this::handleBtNewAction);
        btEdit.setOnAction(this::handleBtEditAction);
        btDelete.setOnAction(this::handleBtDeleteAction);
        btSave.setOnAction(this::handleBtSaveAction);
        btClear.setOnAction(this::handleBtClearAction);
    }
    
    public void limitarTextFields(JFXTextField tf, sistemastock.model.TipoTexto tipoDato, int LIMITE, Label label){
        controller.limitarTextFields(tfRepisa, TipoTexto.NUMERICO, 2, lRepisa);
    }

    public void actualizarTablaUbicaciones() {
        tvUbicaciones.setItems(FXCollections.observableArrayList(getAll()));
    }

    boolean isclickCntrl = false;

    private void handleTvKeyReleased(KeyEvent event) {

        if (event.getCode() == KeyCode.CONTROL) {
            event.consume();
            System.out.println("presion control");
            this.isclickCntrl = true;
        }

        setBotones(Accion.CLICKTV);

        int indexNuevo = tvUbicaciones.getSelectionModel().getSelectedIndex();

        int tamano = tvUbicaciones.getItems().size();

        int cantRowSelect = tvUbicaciones.getSelectionModel().getSelectedItems().size();

        System.out.println("indexViejo: " + indexViejo + " index Nuevo: " + indexNuevo + " tamaño: " + tamano + " cantRowSelect: " + cantRowSelect);

        if ((/*!isclickCntrl &&*/false && (indexViejo == indexNuevo) || (cantRowSelect != 1 && (indexNuevo == -1 /*|| indexNuevo == -1*/)))) {// || */tvUbicaciones.getSelectionModel().isSelected(indexViejo)) {
            System.out.println("borrar todo 1");
            tvUbicaciones.getSelectionModel().clearSelection();
            limpiarTextFields();
            setBotones(Accion.INIT);
            setTextFields(Estado.DESACTIVO);
            indexViejo = -1;

        } else if (cantRowSelect == 1 && indexNuevo >= 0 && indexNuevo < tamano) {
            System.out.println("seleccionar");
            mostrarDetallesUbicaciones(tvUbicaciones.getSelectionModel().getSelectedItem());
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

        int indexNuevo = tvUbicaciones.getSelectionModel().getSelectedIndex();

        int tamano = tvUbicaciones.getItems().size();

        int cantRowSelect = tvUbicaciones.getSelectionModel().getSelectedItems().size();

        System.out.println("indexViejo: " + indexViejo + " index Nuevo: " + indexNuevo + " tamaño: " + tamano + " cantRowSelect: " + cantRowSelect);

        if ((indexViejo == indexNuevo) || (cantRowSelect != 1 && (indexNuevo == -1))) {

            System.out.println("borrar todo 1");
            tvUbicaciones.getSelectionModel().clearSelection();
            limpiarTextFields();
            setBotones(Accion.INIT);
            setTextFields(Estado.DESACTIVO);
            indexViejo = -1;

        } else if (cantRowSelect == 1 && indexNuevo >= 0 && indexNuevo < tamano) {
            System.out.println("seleccionar");
            mostrarDetallesUbicaciones(tvUbicaciones.getSelectionModel().getSelectedItem());
            indexViejo = indexNuevo;

        } else if (cantRowSelect != 1) {
            System.out.println("seleccion multiple");
            limpiarTextFields();
            setBotones(Accion.DELETE);
            setTextFields(Estado.DESACTIVO);
            indexViejo = indexNuevo;
        }
    }

    private void mostrarDetallesUbicaciones(Ubicaciones u) {
        tfId.textProperty().set(u.getId());
        tfPasillo.textProperty().set(u.getPasillo());
        tfEstante.textProperty().set(u.getEstante());
        tfRepisa.textProperty().set(u.getRepisa().toString());
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

        Ubicaciones u = new Ubicaciones();

        u.setPasillo(tfPasillo.getText().toUpperCase());
        u.setEstante(tfEstante.getText().toUpperCase());
        u.setRepisa(Integer.parseInt(tfRepisa.getText().toUpperCase()));

        switch (opcion) {
            case NEW:
                if(validarDatos(u)){
                    u.setId(crearId(u));
                    agregarUbicacion(u);
                    reset = true;
                }
                break;

            case EDIT:
                if(validarDatos(u)){
                    u.setId(tfId.getText().toUpperCase());
                    editarUbicacion(u);
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
            actualizarTablaUbicaciones();
            indexViejo = -1;
        }
    }

    private void agregarUbicacion(Ubicaciones u) {
        if(validarDatos(u))
            controller.persist(u);
    }

    public boolean validarDatos(Ubicaciones u) {
        boolean flat= false;
        if((u.getPasillo().toString().matches("[a-zA-Z]*") && u.getEstante().toString().matches("[a-zA-Z]*") && u.getRepisa().toString().matches("[\\d]*"))){
             flat = true;
             System.out.println("todo bn");
        }else{
            flat = false;
            System.out.println("Datos Mal formados");
        }
        
        return flat;
    }

    private void editarUbicacion(Ubicaciones u) {
        controller.update(u);
    }

    private String crearId(Ubicaciones u) {
        String id = null;
        id = u.getPasillo() + u.getEstante() + u.getRepisa();
        id = id.toUpperCase();

        return id;
    }

    private void handleBtDeleteAction(ActionEvent event) {
        eliminarUbicacion(tvUbicaciones.getSelectionModel().getSelectedItems());
        limpiarTextFields();
        setBotones(Accion.INIT);
        setTextFields(Estado.DESACTIVO);
        actualizarTablaUbicaciones();
    }

    private void eliminarUbicacion(List<Ubicaciones> ubicaciones) {
        ubicaciones.forEach(u -> controller.delete(u));
    }

    private void handleBtClearAction(ActionEvent event) {
        limpiarTextFields();
        setBotones(Accion.INIT);
        setTextFields(Estado.DESACTIVO);
        actualizarTablaUbicaciones();
        indexViejo = -1;
    }

    private void limpiarTextFields() {
        final String CLEAR = "";
        tfId.setText(CLEAR);
        tfEstante.setText(CLEAR);
        tfPasillo.setText(CLEAR);
        tfRepisa.setText(CLEAR);

        lEstante.setText(CLEAR);
        lPasillo.setText(CLEAR);
        lRepisa.setText(CLEAR);
    }
    
    private void setTextFields(Estado estado) {
        tfId.setDisable(true);

        if (estado.getEstado()) {
            tfPasillo.setDisable(false);
            tfEstante.setDisable(false);
            tfRepisa.setDisable(false);
        } else {
            tfPasillo.setDisable(true);
            tfEstante.setDisable(true);
            tfRepisa.setDisable(true);
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
}
