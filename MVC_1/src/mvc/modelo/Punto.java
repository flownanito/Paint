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
    int radio = 2; // Puedes ajustar este valor para que sea más o menos gordo
    g.fillOval(punto.x - radio, punto.y - radio, radio * 2, radio * 2);
}

    
@Override
public String toSVG() {
    int radio = 5;
    return String.format("<circle cx=\"%d\" cy=\"%d\" r=\"%d\" fill=\"rgb(%d,%d,%d)\"/>",
        punto.x, punto.y, radio, color.getRed(), color.getGreen(), color.getBlue());
}

}