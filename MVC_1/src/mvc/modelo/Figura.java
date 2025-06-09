package mvc.modelo;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;

public abstract class Figura {
    protected Color color;
    protected Color colorRelleno;
    
    public Figura(Color color, Color colorRelleno) {
        this.color = color;
        this.colorRelleno = colorRelleno;
    }
    
    public abstract void dibujar(Graphics2D g);
    public abstract String toSVG();
}