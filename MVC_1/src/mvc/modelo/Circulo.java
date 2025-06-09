// Circulo.java
package mvc.modelo;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class Circulo extends Figura {
    Point centro;
    double radio;
    
    public Circulo(Point centro, double radio, Color color, Color colorRelleno) {
        super(color, colorRelleno);
        this.centro = centro;
        this.radio = radio;
    }
    
    @Override
    public void dibujar(Graphics2D g) {
        if (colorRelleno != null) {
            g.setColor(colorRelleno);
            g.fill(new Ellipse2D.Double(centro.x - radio, centro.y - radio, radio * 2, radio * 2));
        }
        g.setColor(color);
        g.draw(new Ellipse2D.Double(centro.x - radio, centro.y - radio, radio * 2, radio * 2));
    }
    
    @Override
    public String toSVG() {
        if (colorRelleno != null) {
            return String.format("<circle cx=\"%d\" cy=\"%d\" r=\"%.2f\" fill=\"rgb(%d,%d,%d)\" stroke=\"rgb(%d,%d,%d)\" stroke-width=\"1\"/>",
                centro.x, centro.y, radio, colorRelleno.getRed(), colorRelleno.getGreen(), colorRelleno.getBlue(),
                color.getRed(), color.getGreen(), color.getBlue());
        } else {
            return String.format("<circle cx=\"%d\" cy=\"%d\" r=\"%.2f\" fill=\"none\" stroke=\"rgb(%d,%d,%d)\" stroke-width=\"1\"/>",
                centro.x, centro.y, radio, color.getRed(), color.getGreen(), color.getBlue());
        }
    }
}