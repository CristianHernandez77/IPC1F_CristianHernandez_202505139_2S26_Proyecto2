package vista;

//------------
import javax.swing.JComponent;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Barra horizontal que muestra "ocupados / total" con un color según el nivel.
 * Es un componente propio: se dibuja sobrescribiendo paintComponent.
 */
public class BarraOcupacion extends JComponent {

    private static final int ANCHO_ETIQUETA = 90;
    private static final Color FONDO = new Color(225, 228, 232);
    private static final Color VERDE = new Color(76, 175, 80);
    private static final Color AMBAR = new Color(255, 179, 0);
    private static final Color ROJO = new Color(229, 57, 53);

    private final String etiqueta;
    private final String filas;   // letras que agrupa esta barra, ej. "A" o "ABC"
    private int ocupados;
    private int total;

    public BarraOcupacion(String etiqueta, String filas) {
        this.etiqueta = etiqueta;
        this.filas = filas;
        setPreferredSize(new Dimension(400, 28));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
    }

    public String getFilas() {
        return filas;
    }

    /** Se llama desde el hilo de Swing (EDT). Guarda los valores y vuelve a pintar. */
    public void setValores(int ocupados, int total) {
        this.ocupados = ocupados;
        this.total = total;
        repaint();
    }

    private Color colorSegunPorcentaje(int porcentaje) {
        if (porcentaje < 60) {
            return VERDE;
        }
        if (porcentaje < 90) {
            return AMBAR;
        }
        return ROJO;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int alto = getHeight();
        int xBarra = ANCHO_ETIQUETA;
        int anchoBarra = getWidth() - ANCHO_ETIQUETA - 4;
        int altoBarra = alto - 8;
        int yBarra = 4;

        int porcentaje = 0;
        if (total > 0) {
            porcentaje = ocupados * 100 / total;
        }

        // Etiqueta a la izquierda
        FontMetrics fm = g2.getFontMetrics();
        int yTexto = (alto + fm.getAscent() - fm.getDescent()) / 2;
        g2.setColor(Color.DARK_GRAY);
        g2.drawString(etiqueta, 4, yTexto);

        // Fondo de la barra
        g2.setColor(FONDO);
        g2.fillRoundRect(xBarra, yBarra, anchoBarra, altoBarra, 10, 10);

        // Parte llena, proporcional a la ocupación
        int anchoLleno = 0;
        if (total > 0) {
            anchoLleno = anchoBarra * ocupados / total;
        }
        if (anchoLleno > 0) {
            g2.setColor(colorSegunPorcentaje(porcentaje));
            g2.fillRoundRect(xBarra, yBarra, anchoLleno, altoBarra, 10, 10);
        }

        // Borde
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1f));
        g2.drawRoundRect(xBarra, yBarra, anchoBarra, altoBarra, 10, 10);

        // Texto centrado sobre la barra: "12/25 (48%)"
        String texto = ocupados + "/" + total + " (" + porcentaje + "%)";
        int xTexto = xBarra + (anchoBarra - fm.stringWidth(texto)) / 2;
        g2.setColor(Color.BLACK);
        g2.drawString(texto, xTexto, yTexto);

        g2.dispose();
    }
}
//------------