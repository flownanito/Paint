package mvc.modelo;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;

public class Linea extends Figura {
    Point inicio;
    Point fin;
    
    public Linea(Point inicio, Point fin, Color color) {
        super(color, null);
        this.inicio = inicio;
        this.fin = fin;
    }
    
    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(color);
        g.drawLine(inicio.x, inicio.y, fin.x, fin.y);
    }
    
    @Override
    public String toSVG() {
        return String.format("<line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"rgb(%d,%d,%d)\" stroke-width=\"1\"/>",
            inicio.x, inicio.y, fin.x, fin.y, color.getRed(), color.getGreen(), color.getBlue());
    }
}