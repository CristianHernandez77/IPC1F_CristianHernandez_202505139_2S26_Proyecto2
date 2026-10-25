package vista;

import javax.swing.*; 
import java.awt.*;
import modelo.Pila;

public class VentanaEventos extends JFrame{
    private Pila historial;
    private JTextArea areaEventos;
    private Timer temporizador;

    public VentanaEventos(Pila historial){
        this.historial = historial;
        setTitle("Historial de Eventos (Pila LIFO)");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        areaEventos = new JTextArea();
        areaEventos.setEditable(false);
        areaEventos.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scroll = new JScrollPane(areaEventos);
        scroll.setBorder(BorderFactory.createTitledBorder("Eventos (Más reciente primero)"));
        add(scroll, BorderLayout.CENTER);
        temporizador = new Timer(10000, e -> actualizarVista());
        temporizador.start();
        actualizarVista();
    }

        private void actualizarVista() {
        if (historial != null) {
            areaEventos.setText(historial.listarInverso());
        }
    }
        public void detener() {
        temporizador.stop();
    }
}
