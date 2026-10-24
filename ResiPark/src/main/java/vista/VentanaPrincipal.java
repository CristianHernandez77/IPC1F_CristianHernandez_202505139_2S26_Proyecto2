package vista;

import javax.swing.*;

import controlador.ControladorEntrada;
import modelo.Pila;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaPrincipal extends JFrame {
    private static final Color COLOR_LIBRE = new Color(170, 220, 170);
    private static final Color COLOR_OCUPADO = new Color(235, 140, 140);

    private JTextField txtPlaca;
    private JCheckBox chkSocio;
    private JButton btnEnviar;
    private JButton btnPrueba;
    private JTextArea areaCola;
    private JLabel lblGarita1;
    private JLabel lblGarita2;
    private JPanel panelParqueo;
    //--------------------------------------------------------------------------------
    // Componentes de la salida
    private JComboBox<String> cmbEstacionados;
    private JButton btnSalida;
    private JTextArea areaColaSalida;
    private JLabel lblGaritaSalida;
    private ControladorEntrada controladorEntrada;
    //--------------------------------------------------------------------------------

    // Arreglo simple con las 150 etiquetas, para buscarlas por id
    private JLabel[] etiquetas = new JLabel[150];
    private int totalEtiquetas = 0;

    public VentanaPrincipal() {
        setTitle("Ejemplo 2: Parqueo con listas circulares");
        //setSize(1300, 650);
        setSize(1700,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panelNorte = new JPanel();

        panelNorte.add(new JLabel("Placa:"));

        txtPlaca = new JTextField(10);
        panelNorte.add(txtPlaca);

        chkSocio = new JCheckBox("Socio");
        panelNorte.add(chkSocio);

        btnEnviar = new JButton("Enviar a cola");
        panelNorte.add(btnEnviar);

        btnPrueba = new JButton("Enviar 10 de prueba");
        panelNorte.add(btnPrueba);

        JButton btnResidentes =new JButton("Gestionar Residentes");

        btnResidentes.addActionListener(e -> {
        VentanaResidentes ventana = new VentanaResidentes();

        ventana.setVisible(true);
        });

        panelNorte.add(btnResidentes);

        JButton btnEventos =new JButton("Ver Eventos");

        btnEventos.addActionListener(e -> {
        VentanaEventos ventana =new VentanaEventos(Pila.global);

        ventana.setVisible(true);
        });

        panelNorte.add(btnEventos);

        JButton btnVisitante =
        new JButton("Registrar Visitante");

        btnVisitante.addActionListener(e -> {
        if (controladorEntrada == null) {
        JOptionPane.showMessageDialog(this,"El controlador de entrada aún no está disponible.");
            return;
        }

        VentanaVisitante ventana = new VentanaVisitante(controladorEntrada);

        ventana.setVisible(true);}); 

        panelNorte.add(btnVisitante);

        add(panelNorte, BorderLayout.NORTH);
        // Centro: el parqueo (el controlador agrega las áreas)
        panelParqueo = new JPanel();
        panelParqueo.setLayout(new BoxLayout(panelParqueo, BoxLayout.Y_AXIS));
        add(panelParqueo, BorderLayout.CENTER);

        // Este: la cola de entrada
        areaCola = new JTextArea();
        areaCola.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaCola);
        scroll.setBorder(BorderFactory.createTitledBorder("Cola de entrada"));
        scroll.setPreferredSize(new Dimension(180, 0));
        // Antes:
        // add(scroll, BorderLayout.EAST);
        //--------------------------------------------------------------------------------
        // La cola de salida va debajo de la cola de entrada
        areaColaSalida = new JTextArea();
        areaColaSalida.setEditable(false);
        JScrollPane scrollSalida = new JScrollPane(areaColaSalida);
        scrollSalida.setBorder(BorderFactory.createTitledBorder("Cola de salida"));

        JPanel panelEste = new JPanel(new GridLayout(2, 1, 0, 5));
        panelEste.setPreferredSize(new Dimension(180, 0));
        panelEste.add(scroll);
        panelEste.add(scrollSalida);
        add(panelEste, BorderLayout.EAST);
        //--------------------------------------------------------------------------------

        // Sur: estado de las garitas
        // Antes:
        // JPanel panelSur = new JPanel(new GridLayout(2, 1));
        JPanel panelSur = new JPanel(new GridLayout(3, 1));
        lblGarita1 = new JLabel("Garita 1: Libre");
        lblGarita2 = new JLabel("Garita 2: Libre");
        panelSur.add(lblGarita1);
        panelSur.add(lblGarita2);
        //--------------------------------------------------------------------------------
        // Fila de salida: elegir un vehículo estacionado y ver la garita de salida
        JPanel panelSalida = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSalida.add(new JLabel("Estacionados:"));
        cmbEstacionados = new JComboBox<>();
        cmbEstacionados.setPreferredSize(new Dimension(120, 25));
        panelSalida.add(cmbEstacionados);
        btnSalida = new JButton("Solicitar salida");
        panelSalida.add(btnSalida);
        lblGaritaSalida = new JLabel("Garita de salida: Libre");
        panelSalida.add(lblGaritaSalida);
        panelSur.add(panelSalida);
        //--------------------------------------------------------------------------------
        add(panelSur, BorderLayout.SOUTH);

    }

    // Dibuja un área del parqueo como cuadrícula: una etiqueta por espacio
    public void construirArea(String titulo, String[] ids, int columnas) {
        JPanel area = new JPanel(new GridLayout(0, columnas, 2, 2));
        area.setBorder(BorderFactory.createTitledBorder(titulo));
        for (int i = 0; i < ids.length; i++) {
            JLabel etiqueta = new JLabel("", SwingConstants.CENTER);
            etiqueta.setName(ids[i]);
            etiqueta.setOpaque(true);
            etiqueta.setFont(new Font("SansSerif", Font.PLAIN, 10));
            etiqueta.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            area.add(etiqueta);
            etiquetas[totalEtiquetas] = etiqueta;
            totalEtiquetas++;
            pintarEspacio(ids[i], null);
        }
        panelParqueo.add(area);
    }

    // placa == null significa libre
    public void pintarEspacio(String id, String placa) {
        for (int i = 0; i < totalEtiquetas; i++) {
            if (etiquetas[i].getName().equals(id)) {
                if (placa == null) {
                    etiquetas[i].setText(id);
                    etiquetas[i].setBackground(COLOR_LIBRE);
                } else {
                    etiquetas[i].setText("<html><center>" + id + "<br>" + placa + "</center></html>");
                    etiquetas[i].setBackground(COLOR_OCUPADO);
                }
                return;
            }
        }
    }

    //Panel ocupacion en vivo, a la izquierda de la ventana
    public void agregarPanelOcupacion(ConsultaOcupacion fuente){

        PanelOcupacion panel = new PanelOcupacion(fuente);
        panel.setPreferredSize(new Dimension(380,0));

        add(panel,BorderLayout.WEST);

    }

    public void setAccionEnviar(ActionListener accion) {
        btnEnviar.addActionListener(accion);
        txtPlaca.addActionListener(accion);
    }

    public void setAccionPrueba(ActionListener accion) {
        btnPrueba.addActionListener(accion);
    }

    public String getPlaca() {
        return txtPlaca.getText().trim();
    }

    public boolean isSocio() {
        return chkSocio.isSelected();
    }

    public void limpiarPlaca() {
        txtPlaca.setText("");
    }

    public void mostrarCola(String texto) {
        areaCola.setText(texto);
    }

    public void mostrarGarita(int numero, String texto) {
        if (numero == 1) {
            lblGarita1.setText("Garita 1: " + texto);
        } else {
            lblGarita2.setText("Garita 2: " + texto);
        }
    }

    //--------------------------------------------------------------------------------
    public void setAccionSalida(ActionListener accion) {
        btnSalida.addActionListener(accion);
    }

    // Placa seleccionada en la lista de estacionados (null si no hay ninguna)
    public String getPlacaSalida() {
        return (String) cmbEstacionados.getSelectedItem();
    }

    // Vuelve a llenar la lista sin perder la selección del usuario
    public void mostrarEstacionados(String[] placas) {
        Object seleccionado = cmbEstacionados.getSelectedItem();
        cmbEstacionados.removeAllItems();
        for (int i = 0; i < placas.length; i++) {
            cmbEstacionados.addItem(placas[i]);
        }
        if (seleccionado != null) {
            cmbEstacionados.setSelectedItem(seleccionado);
        }
    }

    public void mostrarColaSalida(String texto) {
        areaColaSalida.setText(texto);
    }

    public void mostrarGaritaSalida(String texto) {
        lblGaritaSalida.setText("Garita de salida: " + texto);
    }
    //--------------------------------------------------------------------------------

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    public void conectarControladorEntrada(
        ControladorEntrada controlador
    ) {
        this.controladorEntrada = controlador;
    }
}