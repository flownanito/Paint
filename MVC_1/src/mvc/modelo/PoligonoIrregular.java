// PoligonoIrregular.java
package mvc.modelo;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

public class PoligonoIrregular extends Figura {
    List<Point> puntos;
    
    public PoligonoIrregular(Color color, Color colorRelleno) {
        super(color, colorRelleno);
        this.puntos = new ArrayList<>();
    }
    
    public void agregarPunto(Point p) {
        puntos.add(p);
    }
    
    public boolean tieneLadosCruzados() {
        if (puntos.size() < 4) return false;
        
        for (int i = 0; i < puntos.size(); i++) {
            Point p1 = puntos.get(i);
            Point p2 = puntos.get((i + 1) % puntos.size());
            
            for (int j = i + 2; j < puntos.size(); j++) {
                Point p3 = puntos.get(j);
                Point p4 = puntos.get((j + 1) % puntos.size());
                
                if (seIntersectan(p1, p2, p3, p4)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean seIntersectan(Point p1, Point p2, Point p3, Point p4) {
        // Implementación del algoritmo de intersección de segmentos
        // (omitiendo por brevedad, pero sería similar a la solución de ecuaciones de rectas)
        return false;
    }
    
    @Override
    public void dibujar(Graphics2D g) {
        if (puntos.size() < 2) return;
        
        Polygon poligono = new Polygon();
        for (Point p : puntos) {
            poligono.addPoint(p.x, p.y);
        }
        
        if (colorRelleno != null && puntos.size() >= 3) {
            g.setColor(colorRelleno);
            g.fill(poligono);
        }
        
        g.setColor(color);
        g.drawPolyline(poligono.xpoints, poligono.ypoints, poligono.npoints);
        
        if (puntos.size() >= 3) {
            g.drawLine(puntos.get(puntos.size() - 1).x, puntos.get(puntos.size() - 1).y, 
                       puntos.get(0).x, puntos.get(0).y);
        }
    }
    
    @Override
    public String toSVG() {
        if (puntos.size() < 2) return "";
        
        StringBuilder svg = new StringBuilder();
        svg.append("<polyline points=\"");
        
        for (Point p : puntos) {
            svg.append(p.x).append(",").append(p.y).append(" ");
        }
        
        if (puntos.size() >= 3) {
            svg.append(puntos.get(0).x).append(",").append(puntos.get(0).y);
        }
        
        svg.append("\"");
        
        if (colorRelleno != null && puntos.size() >= 3) {
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