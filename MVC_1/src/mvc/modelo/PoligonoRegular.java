// PoligonoRegular.java
package mvc.modelo;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

public class PoligonoRegular extends Figura {
    Point centro;
    int lados;
    double radio;
    private List<Point> vertices;
    
    public PoligonoRegular(Point centro, int lados, double radio, Color color, Color colorRelleno) {
        super(color, colorRelleno);
        this.centro = centro;
        this.lados = lados;
        this.radio = radio;
        calcularVertices();
    }
    
    private void calcularVertices() {
        vertices = new ArrayList<>();
        double angulo = 2 * Math.PI / lados;
        
        for (int i = 0; i < lados; i++) {
            int x = (int) (centro.x + radio * Math.cos(i * angulo));
            int y = (int) (centro.y + radio * Math.sin(i * angulo));
            vertices.add(new Point(x, y));
        }
    }
    
    @Override
    public void dibujar(Graphics2D g) {
        Polygon poligono = new Polygon();
        for (Point p : vertices) {
            poligono.addPoint(p.x, p.y);
        }
        
        if (colorRelleno != null) {
            g.setColor(colorRelleno);
            g.fill(poligono);
        }
        g.setColor(color);
        g.draw(poligono);
    }
    
    @Override
    public String toSVG() {
        StringBuilder svg = new StringBuilder();
        svg.append("<polygon points=\"");
        
        for (Point p : vertices) {
            svg.append(p.x).append(",").append(p.y).append(" ");
        }
        
        svg.append("\"");
        
        if (colorRelleno != null) {
            svg.append(String.format(" fill=\"rgb(%d,%d,%d)\"", 
                colorRelleno.getRed(), colorRelleno.getGreen(), colorRelleno.getBlue()));
        } else {
            svg.append(" fill=\"none\"");
        }
        
        svg.append(String.format(" stroke=\"rgb(%d,%d,%d)\" stroke-width=\"1\"/>",
            color.getRed(), color.getGreen(), color.getBlue()));
        
        return svg.toString();
    }
}