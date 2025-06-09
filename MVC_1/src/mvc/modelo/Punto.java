// Punto.java
package mvc.modelo;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;

public class Punto extends Figura {
    Point punto;
    
    public Punto(Point punto, Color color) {
        super(color, null);
        this.punto = punto;
    }
    
    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(color);
        g.drawLine(punto.x, punto.y, punto.x, punto.y);
    }
    
    @Override
    public String toSVG() {
        return String.format("<circle cx=\"%d\" cy=\"%d\" r=\"1\" fill=\"rgb(%d,%d,%d)\"/>",
            punto.x, punto.y, color.getRed(), color.getGreen(), color.getBlue());
    }
}