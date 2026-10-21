package vista;

import controlador.ControladorResidentes;
import modelo.Residente;
import javax.swing.*;
import java.awt.*;

public class VentanaResidentes extends JFrame {
    
    private ControladorResidentes controlador;
    
    // Componentes de Residente
    private JTextField txtID, txtNombre, txtCasa;
    private JCheckBox chkSocio;
    private JButton btnRegistrar, btnBuscar, btnModificar, btnEliminar;
    private JTextArea areaResidentes;
    
    // Componentes de Vehículo
    private JTextField txtPlaca, txtMarca, txtModelo, txtColor;
    private JComboBox<String> cmbTipo;
    private JButton btnAgregarVehiculo, btnEliminarVehiculo;
    private JTextArea areaVehiculos;

    public VentanaResidentes() {
        controlador = new ControladorResidentes();
        
        setTitle("Gestión de Residentes y Vehículos");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2, 10, 10)); 
        
        // --- PANEL IZQUIERDO (RESIDENTES) ---
        JPanel panelIzq = new JPanel(new BorderLayout(5, 5));
        panelIzq.setBorder(BorderFactory.createTitledBorder("Datos del Residente"));
        
        JPanel formRes = new JPanel(new GridLayout(4, 2, 5, 5));
        formRes.add(new JLabel("ID:"));
        txtID = new JTextField(); formRes.add(txtID);
        formRes.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(); formRes.add(txtNombre);
        formRes.add(new JLabel("Casa/Lote:"));
        txtCasa = new JTextField(); formRes.add(txtCasa);
        formRes.add(new JLabel("¿Es Socio?:"));
        chkSocio = new JCheckBox(); formRes.add(chkSocio);
        
        JPanel botonesRes = new JPanel(new GridLayout(1, 4, 5, 5));
        btnRegistrar = new JButton("Registrar");
        btnBuscar = new JButton("Buscar");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        botonesRes.add(btnRegistrar); botonesRes.add(btnBuscar);
        botonesRes.add(btnModificar); botonesRes.add(btnEliminar);
        
        JPanel topIzq = new JPanel(new BorderLayout(5, 5));
        topIzq.add(formRes, BorderLayout.CENTER);
        topIzq.add(botonesRes, BorderLayout.SOUTH);
        
        areaResidentes = new JTextArea();
        areaResidentes.setEditable(false);
        
        panelIzq.add(topIzq, BorderLayout.NORTH);
        panelIzq.add(new JScrollPane(areaResidentes), BorderLayout.CENTER);
        
        // --- PANEL DERECHO (VEHÍCULOS) ---
        JPanel panelDer = new JPanel(new BorderLayout(5, 5));
        panelDer.setBorder(BorderFactory.createTitledBorder("Vehículos del Residente (Seleccionado)"));
        
        JPanel formVeh = new JPanel(new GridLayout(5, 2, 5, 5));
        formVeh.add(new JLabel("Placa:"));
        txtPlaca = new JTextField(); formVeh.add(txtPlaca);
        formVeh.add(new JLabel("Marca:"));
        txtMarca = new JTextField(); formVeh.add(txtMarca);
        formVeh.add(new JLabel("Modelo:"));
        txtModelo = new JTextField(); formVeh.add(txtModelo);
        formVeh.add(new JLabel("Color:"));
        txtColor = new JTextField(); formVeh.add(txtColor);
        formVeh.add(new JLabel("Tipo:"));
        cmbTipo = new JComboBox<>(new String[]{"Automóvil", "Motocicleta", "Pickup"});
        formVeh.add(cmbTipo);
        
        JPanel botonesVeh = new JPanel(new GridLayout(1, 2, 5, 5));
        btnAgregarVehiculo = new JButton("Agregar Vehículo");
        btnEliminarVehiculo = new JButton("Eliminar Vehículo");
        botonesVeh.add(btnAgregarVehiculo); botonesVeh.add(btnEliminarVehiculo);
        
        JPanel topDer = new JPanel(new BorderLayout(5, 5));
        topDer.add(formVeh, BorderLayout.CENTER);
        topDer.add(botonesVeh, BorderLayout.SOUTH);
        
        areaVehiculos = new JTextArea();
        areaVehiculos.setEditable(false);
        
        panelDer.add(topDer, BorderLayout.NORTH);
        panelDer.add(new JScrollPane(areaVehiculos), BorderLayout.CENTER);
        
        add(panelIzq);
        add(panelDer);

        // --- ACCIONES DE LOS BOTONES ---
        btnRegistrar.addActionListener(e -> {
            String msj = controlador.registrarResidente(txtID.getText().trim(), txtNombre.getText().trim(), txtCasa.getText().trim(), chkSocio.isSelected());
            JOptionPane.showMessageDialog(this, msj);
            actualizarListaResidentes();
        });
        
        btnBuscar.addActionListener(e -> {
            Residente r = controlador.buscarResidente(txtID.getText().trim());
            if (r != null) {
                txtNombre.setText(r.getNombre());
                txtCasa.setText(r.getCasa());
                chkSocio.setSelected(r.isEsSocio());
                actualizarListaVehiculos();
            } else {
                JOptionPane.showMessageDialog(this, "Residente no encontrado.");
            }
        });
        
        btnModificar.addActionListener(e -> {
            String msj = controlador.modificarResidente(txtID.getText().trim(), txtNombre.getText().trim(), txtCasa.getText().trim(), chkSocio.isSelected());
            JOptionPane.showMessageDialog(this, msj);
            actualizarListaResidentes();
        });
        
        btnEliminar.addActionListener(e -> {
            String msj = controlador.eliminarResidente(txtID.getText().trim());
            JOptionPane.showMessageDialog(this, msj);
            actualizarListaResidentes();
        });
        
        btnAgregarVehiculo.addActionListener(e -> {
            String msj = controlador.agregarVehiculo(txtPlaca.getText().trim(), txtMarca.getText().trim(), txtModelo.getText().trim(), txtColor.getText().trim(), cmbTipo.getSelectedItem().toString());
            JOptionPane.showMessageDialog(this, msj);
            actualizarListaVehiculos();
            limpiarCamposVehiculo();
        });
        
        btnEliminarVehiculo.addActionListener(e -> {
            String msj = controlador.eliminarVehiculo(txtPlaca.getText().trim());
            JOptionPane.showMessageDialog(this, msj);
            actualizarListaVehiculos();
            limpiarCamposVehiculo();
        });
    }
    
    private void actualizarListaResidentes() {
        areaResidentes.setText(controlador.listarResidentes());
    }
    
    private void actualizarListaVehiculos() {
        areaVehiculos.setText(controlador.listarVehiculos());
    }
    
    private void limpiarCamposVehiculo() {
        txtPlaca.setText("");
        txtMarca.setText("");
        txtModelo.setText("");
        txtColor.setText("");
    }
}