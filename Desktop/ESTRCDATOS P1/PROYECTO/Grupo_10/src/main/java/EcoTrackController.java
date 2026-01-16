import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import java.io.File;
import java.time.LocalDate;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

/**
 * Controlador principal de la interfaz gráfica EcoTrack
 */
public class EcoTrackController {
    
    private EcoTrackSystem sistema;
    
    @FXML private TextField txtIdResiduo;
    @FXML private TextField txtNombreResiduo;
    @FXML private ComboBox<String> cmbTipoResiduo;
    @FXML private TextField txtPesoResiduo;
    @FXML private DatePicker dpFechaResiduo;
    @FXML private ComboBox<String> cmbZonaResiduo;
    @FXML private Slider sliderPrioridad;
    @FXML private Label lblPrioridad;
    
    @FXML private TableView<Residuo> tablaResiduos;
    @FXML private TableColumn<Residuo, String> colId;
    @FXML private TableColumn<Residuo, String> colNombre;
    @FXML private TableColumn<Residuo, String> colTipo;
    @FXML private TableColumn<Residuo, Double> colPeso;
    @FXML private TableColumn<Residuo, LocalDate> colFecha;
    @FXML private TableColumn<Residuo, String> colZona;
    @FXML private TableColumn<Residuo, Integer> colPrioridad;
    
    @FXML private ComboBox<String> cmbOrdenamiento;
    
    @FXML private TextField txtIdVehiculo;
    @FXML private TextField txtCapacidadVehiculo;
    @FXML private ComboBox<String> cmbZonaVehiculo;
    @FXML private Slider sliderPrioridadVehiculo;
    @FXML private Label lblPrioridadVehiculo;
    
    @FXML private TableView<Vehiculo> tablaVehiculos;
    @FXML private TableColumn<Vehiculo, String> colIdVehiculo;
    @FXML private TableColumn<Vehiculo, String> colZonaVehiculo;
    @FXML private TableColumn<Vehiculo, Double> colCapacidad;
    @FXML private TableColumn<Vehiculo, Double> colCarga;
    @FXML private TableColumn<Vehiculo, Integer> colPrioridadVehiculo;
    
    @FXML private Label lblProximoVehiculo;
    
    @FXML private ListView<String> listaReciclaje;
    @FXML private Label lblCantidadReciclaje;
    @FXML private TextArea txtProximoReciclaje;
    
    @FXML private TextField txtNombreZona;
    @FXML private TableView<Zona> tablaZonas;
    @FXML private TableColumn<Zona, String> colNombreZona;
    @FXML private TableColumn<Zona, Double> colRecolectados;
    @FXML private TableColumn<Zona, Double> colPendientes;
    @FXML private TableColumn<Zona, Double> colUtilidad;
    
    @FXML private Label lblZonaPrioritaria;
    
    @FXML private PieChart pieChartTipos;
    @FXML private BarChart<String, Number> barChartZonas;
    @FXML private Label lblPesoTotal;
    @FXML private Label lblTotalResiduos;
    
    private ObservableList<Residuo> listaResiduos;
    private ObservableList<Vehiculo> listaVehiculos;
    private ObservableList<Zona> listaZonas;
    
    @FXML
    public void initialize() {
        sistema = new EcoTrackSystem();
        
        inicializarTabResiduos();
        inicializarTabVehiculos();
        inicializarTabReciclaje();
        inicializarTabZonas();
        inicializarTabEstadisticas();
        
        cargarDatosIniciales();
    }
    
    private void inicializarTabResiduos() {
        cmbTipoResiduo.setItems(FXCollections.observableArrayList(
            "Organico", "Plastico", "Vidrio", "Electronico", "Metal", "Papel", "Carton"
        ));
        
        cmbOrdenamiento.setItems(FXCollections.observableArrayList(
            "Por Peso", "Por Tipo", "Por Prioridad Ambiental"
        ));
        cmbOrdenamiento.setValue("Por Peso");
        
        sliderPrioridad.valueProperty().addListener((obs, oldVal, newVal) -> {
            lblPrioridad.setText(String.valueOf(newVal.intValue()));
        });
        lblPrioridad.setText("1");
        
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaRecoleccion"));
        colZona.setCellValueFactory(new PropertyValueFactory<>("zona"));
        colPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridadAmbiental"));
        
        listaResiduos = FXCollections.observableArrayList();
        tablaResiduos.setItems(listaResiduos);
        
        dpFechaResiduo.setValue(LocalDate.now());
    }
    
    private void inicializarTabVehiculos() {
        sliderPrioridadVehiculo.valueProperty().addListener((obs, oldVal, newVal) -> {
            lblPrioridadVehiculo.setText(String.valueOf(newVal.intValue()));
        });
        lblPrioridadVehiculo.setText("1");
        
        colIdVehiculo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colZonaVehiculo.setCellValueFactory(new PropertyValueFactory<>("zonaAsignada"));
        colCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidadMaxima"));
        colCarga.setCellValueFactory(new PropertyValueFactory<>("cargaActual"));
        colPrioridadVehiculo.setCellValueFactory(new PropertyValueFactory<>("prioridad"));
        
        listaVehiculos = FXCollections.observableArrayList();
        tablaVehiculos.setItems(listaVehiculos);
    }
    
    private void inicializarTabReciclaje() {
        lblCantidadReciclaje.setText("0");
    }
    
    private void inicializarTabZonas() {
        colNombreZona.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colRecolectados.setCellValueFactory(new PropertyValueFactory<>("residuosRecolectados"));
        colPendientes.setCellValueFactory(new PropertyValueFactory<>("residuosPendientes"));
        
        colUtilidad.setCellValueFactory(cellData -> {
            double utilidad = cellData.getValue().calcularUtilidad();
            return new javafx.beans.property.SimpleDoubleProperty(utilidad).asObject();
        });
        
        listaZonas = FXCollections.observableArrayList();
        tablaZonas.setItems(listaZonas);
    }
    
    private void inicializarTabEstadisticas() {
        lblPesoTotal.setText("0.0 kg");
        lblTotalResiduos.setText("0");
        
        pieChartTipos.setLegendVisible(true);
        pieChartTipos.setLabelsVisible(true);
    }
    
    @FXML
    private void agregarResiduo() {
        try {
            String id = txtIdResiduo.getText().trim();
            String nombre = txtNombreResiduo.getText().trim();
            String tipo = cmbTipoResiduo.getValue();
            String pesoTexto = txtPesoResiduo.getText().trim();
            LocalDate fecha = dpFechaResiduo.getValue();
            String zona = cmbZonaResiduo.getValue();
            int prioridad = (int) sliderPrioridad.getValue();
            
            if (id.isEmpty()) {
                mostrarAlerta("Error", "El ID no puede estar vacio");
                return;
            }
            if (nombre.isEmpty()) {
                mostrarAlerta("Error", "El nombre no puede estar vacio");
                return;
            }
            if (tipo == null) {
                mostrarAlerta("Error", "Debe seleccionar un tipo de residuo");
                return;
            }
            if (pesoTexto.isEmpty()) {
                mostrarAlerta("Error", "Debe ingresar el peso");
                return;
            }
            if (zona == null) {
                mostrarAlerta("Error", "Debe seleccionar una zona");
                return;
            }
            
            double peso = Double.parseDouble(pesoTexto);
            
            if (peso <= 0) {
                mostrarAlerta("Error", "El peso debe ser mayor a 0");
                return;
            }
            
            sistema.registrarResiduo(id, nombre, tipo, peso, fecha, zona, prioridad);
            actualizarTablaResiduos();
            actualizarEstadisticas();
            actualizarZonas();
            actualizarZonaPrioritaria();
            limpiarCamposResiduo();
            
            mostrarInformacion("Exito", "Residuo agregado correctamente");
            
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El peso debe ser un numero valido (use punto para decimales)");
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al agregar residuo: " + e.getMessage());
        }
    }
    
    @FXML
    private void eliminarResiduo() {
        Residuo seleccionado = tablaResiduos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            String tipo = seleccionado.getTipo();
            double peso = seleccionado.getPeso();
            
            sistema.eliminarResiduo(seleccionado);
            
            sistema.getEstadisticasPorTipo().put(tipo, 
                sistema.getEstadisticasPorTipo().getOrDefault(tipo, 0.0) - peso);
            
            if (sistema.getEstadisticasPorTipo().get(tipo) <= 0) {
                sistema.getEstadisticasPorTipo().remove(tipo);
            }
            
            actualizarTablaResiduos();
            actualizarEstadisticas();
            actualizarZonas();
            mostrarInformacion("Exito", "Residuo eliminado");
        } else {
            mostrarAlerta("Error", "Seleccione un residuo de la tabla");
        }
    }
    
    @FXML
    private void ordenarResiduos() {
        String criterio = cmbOrdenamiento.getValue();
        
        switch (criterio) {
            case "Por Peso":
                sistema.ordenarPorPeso();
                break;
            case "Por Tipo":
                sistema.ordenarPorTipo();
                break;
            case "Por Prioridad Ambiental":
                sistema.ordenarPorPrioridad();
                break;
        }
        
        actualizarTablaResiduos();
    }
    
    @FXML
    private void enviarAReciclaje() {
        Residuo seleccionado = tablaResiduos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            String tipo = seleccionado.getTipo();
            double peso = seleccionado.getPeso();
            
            sistema.enviarACentroReciclaje(seleccionado);
            sistema.eliminarResiduo(seleccionado);
            
            sistema.getEstadisticasPorTipo().put(tipo, 
                sistema.getEstadisticasPorTipo().getOrDefault(tipo, 0.0) - peso);
            
            if (sistema.getEstadisticasPorTipo().get(tipo) <= 0) {
                sistema.getEstadisticasPorTipo().remove(tipo);
            }
            
            actualizarTablaResiduos();
            actualizarCentroReciclaje();
            actualizarEstadisticas();
            actualizarZonas();
            mostrarInformacion("Exito", "Residuo enviado a centro de reciclaje");
        } else {
            mostrarAlerta("Error", "Seleccione un residuo de la tabla");
        }
    }
    
    @FXML
    private void agregarVehiculo() {
        try {
            String id = txtIdVehiculo.getText().trim();
            String capacidadTexto = txtCapacidadVehiculo.getText().trim();
            String zona = cmbZonaVehiculo.getValue();
            int prioridad = (int) sliderPrioridadVehiculo.getValue();
            
            if (id.isEmpty()) {
                mostrarAlerta("Error", "El ID del vehiculo no puede estar vacio");
                return;
            }
            if (capacidadTexto.isEmpty()) {
                mostrarAlerta("Error", "Debe ingresar la capacidad");
                return;
            }
            if (zona == null) {
                mostrarAlerta("Error", "Debe seleccionar una zona");
                return;
            }
            
            double capacidad = Double.parseDouble(capacidadTexto);
            
            if (capacidad <= 0) {
                mostrarAlerta("Error", "La capacidad debe ser mayor a 0");
                return;
            }
            
            Vehiculo vehiculo = new Vehiculo(id, zona, capacidad, prioridad);
            sistema.agregarVehiculo(vehiculo);
            
            actualizarTablaVehiculos();
            limpiarCamposVehiculo();
            actualizarProximoVehiculo();
            
            mostrarInformacion("Exito", "Vehiculo agregado a la cola con prioridad " + prioridad);
            
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "La capacidad debe ser un numero valido");
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al agregar vehiculo: " + e.getMessage());
        }
    }
    
    @FXML
    private void despacharVehiculo() {
        Vehiculo vehiculo = sistema.despacharVehiculo();
        if (vehiculo != null) {
            actualizarTablaVehiculos();
            actualizarProximoVehiculo();
            mostrarInformacion("Despachado", "Vehiculo " + vehiculo.getId() + 
                             " despachado a zona: " + vehiculo.getZonaAsignada());
        } else {
            mostrarAlerta("Error", "No hay vehiculos en la cola");
        }
    }
    
    @FXML
    private void procesarReciclaje() {
        Residuo residuo = sistema.procesarDeReciclaje();
        if (residuo != null) {
            actualizarCentroReciclaje();
            mostrarInformacion("Procesado", String.format(
                "Residuo procesado exitosamente:\n\n" +
                "ID: %s\nNombre: %s\nTipo: %s\nPeso: %.2f kg",
                residuo.getId(), residuo.getNombre(), 
                residuo.getTipo(), residuo.getPeso()
            ));
        } else {
            mostrarAlerta("Error", "No hay residuos en el centro de reciclaje");
        }
    }
    
    @FXML
    private void agregarZona() {
        String nombre = txtNombreZona.getText().trim();
        if (!nombre.isEmpty()) {
            sistema.agregarZona(nombre);
            actualizarZonas();
            actualizarComboBoxZonas();
            txtNombreZona.clear();
            mostrarInformacion("Exito", "Zona agregada");
        } else {
            mostrarAlerta("Error", "Ingrese el nombre de la zona");
        }
    }
    
    @FXML
    private void actualizarZonaPrioritaria() {
        Zona zona = sistema.getZonaPrioritaria();
        if (zona != null) {
            lblZonaPrioritaria.setText(
                String.format("%s (Utilidad: %.2f)", zona.getNombre(), zona.calcularUtilidad())
            );
        } else {
            lblZonaPrioritaria.setText("No hay zonas registradas");
        }
    }
    
    @FXML
    private void guardarDatos() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar datos");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivos EcoTrack", "*.eco")
        );
        
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try {
                sistema.guardarDatos(file.getAbsolutePath());
                mostrarInformacion("Exito", "Datos guardados correctamente");
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al guardar: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void cargarDatos() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Cargar datos");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Archivos EcoTrack", "*.eco")
        );
        
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                sistema = EcoTrackSystem.cargarDatos(file.getAbsolutePath());
                actualizarTodo();
                mostrarInformacion("Exito", "Datos cargados correctamente");
            } catch (Exception e) {
                mostrarAlerta("Error", "Error al cargar: " + e.getMessage());
            }
        }
    }
    
    private void actualizarTablaResiduos() {
        listaResiduos.clear();
        if (!sistema.getResiduos().estaVacia()) {
            IteradorCircular<Residuo> it = sistema.getResiduos().iterador();
            while (it.hasNext()) {
                listaResiduos.add(it.next());
            }
        }
    }
    
    private void actualizarTablaVehiculos() {
        listaVehiculos.clear();
        listaVehiculos.addAll(sistema.getVehiculos().obtenerTodos());
    }
    
    private void actualizarCentroReciclaje() {
        listaReciclaje.getItems().clear();
        
        Pila<Residuo> centroTemp = new Pila<>();
        Pila<Residuo> centro = sistema.getCentroReciclaje();
        
        while (!centro.estaVacia()) {
            Residuo r = centro.desapilar();
            listaReciclaje.getItems().add(String.format("%s - %s (%s) - %.2f kg", 
                r.getId(), r.getNombre(), r.getTipo(), r.getPeso()));
            centroTemp.apilar(r);
        }
        
        while (!centroTemp.estaVacia()) {
            sistema.getCentroReciclaje().apilar(centroTemp.desapilar());
        }
        
        lblCantidadReciclaje.setText(String.valueOf(sistema.getCentroReciclaje().getTamanio()));
        
        Residuo proximo = sistema.verProximoEnReciclaje();
        if (proximo != null) {
            txtProximoReciclaje.setText(String.format(
                "ID: %s\nNombre: %s\nTipo: %s\nPeso: %.2f kg\nZona: %s\nPrioridad: %d",
                proximo.getId(), proximo.getNombre(), proximo.getTipo(), 
                proximo.getPeso(), proximo.getZona(), proximo.getPrioridadAmbiental()
            ));
        } else {
            txtProximoReciclaje.setText("No hay residuos en el centro");
        }
    }
    
    private void actualizarZonas() {
        listaZonas.clear();
        listaZonas.addAll(sistema.getZonas().values());
        actualizarZonaPrioritaria();
    }
    
    private void actualizarEstadisticas() {
        double pesoTotal = 0.0;
        int totalResiduos = 0;
        
        if (!sistema.getResiduos().estaVacia()) {
            IteradorCircular<Residuo> it = sistema.getResiduos().iterador();
            while (it.hasNext()) {
                Residuo r = it.next();
                pesoTotal += r.getPeso();
                totalResiduos++;
            }
        }
        
        lblPesoTotal.setText(String.format("%.2f kg", pesoTotal));
        lblTotalResiduos.setText(String.valueOf(totalResiduos));
        
        pieChartTipos.getData().clear();
        
        if (!sistema.getEstadisticasPorTipo().isEmpty()) {
            for (var entry : sistema.getEstadisticasPorTipo().entrySet()) {
                if (entry.getValue() > 0) {
                    PieChart.Data slice = new PieChart.Data(
                        entry.getKey() + " (" + String.format("%.1f", entry.getValue()) + " kg)", 
                        entry.getValue()
                    );
                    pieChartTipos.getData().add(slice);
                }
            }
        }
        
        if (barChartZonas != null) {
            barChartZonas.getData().clear();
            XYChart.Series<String, Number> seriesPendientes = new XYChart.Series<>();
            seriesPendientes.setName("Residuos Pendientes");
            
            XYChart.Series<String, Number> seriesRecolectados = new XYChart.Series<>();
            seriesRecolectados.setName("Residuos Recolectados");
            
            for (Zona zona : sistema.getZonas().values()) {
                seriesPendientes.getData().add(new XYChart.Data<>(
                    zona.getNombre(), 
                    zona.getResiduosPendientes()
                ));
                seriesRecolectados.getData().add(new XYChart.Data<>(
                    zona.getNombre(), 
                    zona.getResiduosRecolectados()
                ));
            }
            
            barChartZonas.getData().addAll(seriesPendientes, seriesRecolectados);
        }
    }
    
    private void actualizarProximoVehiculo() {
        Vehiculo proximo = sistema.verProximoVehiculo();
        if (proximo != null) {
            lblProximoVehiculo.setText(
                String.format("Proximo: %s - Zona: %s - Prioridad: %d", 
                    proximo.getId(), proximo.getZonaAsignada(), proximo.getPrioridad())
            );
        } else {
            lblProximoVehiculo.setText("No hay vehiculos en cola");
        }
    }
    
    private void actualizarComboBoxZonas() {
        ObservableList<String> zonas = FXCollections.observableArrayList(
            sistema.getZonas().keySet()
        );
        cmbZonaResiduo.setItems(zonas);
        cmbZonaVehiculo.setItems(zonas);
    }
    
    private void actualizarTodo() {
        actualizarTablaResiduos();
        actualizarTablaVehiculos();
        actualizarCentroReciclaje();
        actualizarZonas();
        actualizarEstadisticas();
        actualizarComboBoxZonas();
        actualizarProximoVehiculo();
    }
    
    private void limpiarCamposResiduo() {
        txtIdResiduo.clear();
        txtNombreResiduo.clear();
        cmbTipoResiduo.setValue(null);
        txtPesoResiduo.clear();
        dpFechaResiduo.setValue(LocalDate.now());
        cmbZonaResiduo.setValue(null);
        sliderPrioridad.setValue(1);
    }
    
    private void limpiarCamposVehiculo() {
        txtIdVehiculo.clear();
        txtCapacidadVehiculo.clear();
        cmbZonaVehiculo.setValue(null);
        sliderPrioridadVehiculo.setValue(1);
    }
    
    private void cargarDatosIniciales() {
        sistema.agregarZona("Centro");
        sistema.agregarZona("Norte");
        sistema.agregarZona("Sur");
        sistema.agregarZona("Este");
        sistema.agregarZona("Oeste");
        
        actualizarComboBoxZonas();
        actualizarZonas();
    }
    
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    private void mostrarInformacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}