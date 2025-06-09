package mvc.modelo;

import java.sql.*;
import java.util.*;
import java.awt.Color;
import java.awt.Point;
import mvc.controlador.Dibujo;

public class FiguraDAO {
    private Connection conectar() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/paint", "root", "");
    }

    public void guardarDibujo(List<Figura> figuras) throws SQLException {
        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);
            
            // Guardar el dibujo
            try (PreparedStatement psDibujo = conn.prepareStatement(
                "INSERT INTO dibujos (fecha_creacion) VALUES (NOW())", 
                Statement.RETURN_GENERATED_KEYS)) {
                
                psDibujo.executeUpdate();
                
                int dibujoId = -1;
                try (ResultSet rs = psDibujo.getGeneratedKeys()) {
                    if (rs.next()) {
                        dibujoId = rs.getInt(1);
                    }
                }
                
                if (dibujoId == -1) {
                    conn.rollback();
                    throw new SQLException("No se pudo obtener el ID del dibujo");
                }
                
                // Guardar cada figura
                for (Figura figura : figuras) {
                    guardarFigura(conn, dibujoId, figura);
                }
                
                conn.commit();
            }
        }
    }

    private void guardarFigura(Connection conn, int dibujoId, Figura figura) throws SQLException {
        String tipo = figura.getClass().getSimpleName();
        String datos = obtenerDatosFigura(figura);
        String color = colorToHex(figura.color);
        String colorRelleno = figura.colorRelleno != null ? colorToHex(figura.colorRelleno) : null;
        
        String sql = "INSERT INTO figuras (dibujo_id, tipo, datos, color, color_relleno) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, dibujoId);
            ps.setString(2, tipo);
            ps.setString(3, datos);
            ps.setString(4, color);
            ps.setString(5, colorRelleno);
            ps.executeUpdate();
        }
    }

    private String obtenerDatosFigura(Figura figura) {
        if (figura instanceof Punto p) {
            return p.punto.x + "," + p.punto.y;
        } else if (figura instanceof Linea l) {
            return l.inicio.x + "," + l.inicio.y + ";" + l.fin.x + "," + l.fin.y;
        } else if (figura instanceof Circulo c) {
            return c.centro.x + "," + c.centro.y + ";" + c.radio;
        } else if (figura instanceof PoligonoRegular pr) {
            return pr.centro.x + "," + pr.centro.y + ";" + pr.lados + ";" + pr.radio;
        } else if (figura instanceof PoligonoIrregular pi) {
            StringBuilder sb = new StringBuilder();
            for (Point p : pi.puntos) {
                sb.append(p.x).append(",").append(p.y).append(";");
            }
            return sb.toString();
        }
        throw new IllegalArgumentException("Tipo de figura no soportado: " + figura.getClass().getName());
    }

    public List<Figura> cargarDibujo(int dibujoId) throws SQLException {
        List<Figura> figuras = new ArrayList<>();
        
        String sql = "SELECT tipo, datos, color, color_relleno FROM figuras WHERE dibujo_id = ?";
        
        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, dibujoId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Figura figura = crearFiguraDesdeResultSet(rs);
                    if (figura != null) {
                        figuras.add(figura);
                    }
                }
            }
        }
        
        return figuras;
    }

    private Figura crearFiguraDesdeResultSet(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        String datos = rs.getString("datos");
        Color color = hexToColor(rs.getString("color"));
        Color colorRelleno = rs.getString("color_relleno") != null ? 
            hexToColor(rs.getString("color_relleno")) : null;
        
        switch (tipo) {
            case "Punto":
                String[] coordsPunto = datos.split(",");
                return new Punto(
                    new Point(Integer.parseInt(coordsPunto[0]), Integer.parseInt(coordsPunto[1])),
                    color);
                
            case "Linea":
                String[] partesLinea = datos.split(";");
                String[] inicio = partesLinea[0].split(",");
                String[] fin = partesLinea[1].split(",");
                return new Linea(
                    new Point(Integer.parseInt(inicio[0]), Integer.parseInt(inicio[1])),
                    new Point(Integer.parseInt(fin[0]), Integer.parseInt(fin[1])),
                    color);
                
            case "Circulo":
                String[] partesCirculo = datos.split(";");
                String[] centro = partesCirculo[0].split(",");
                double radio = Double.parseDouble(partesCirculo[1]);
                return new Circulo(
                    new Point(Integer.parseInt(centro[0]), Integer.parseInt(centro[1])),
                    radio, color, colorRelleno);
                
            case "PoligonoRegular":
                String[] partesPolReg = datos.split(";");
                String[] centroPol = partesPolReg[0].split(",");
                int lados = Integer.parseInt(partesPolReg[1]);
                double radioPol = Double.parseDouble(partesPolReg[2]);
                return new PoligonoRegular(
                    new Point(Integer.parseInt(centroPol[0]), Integer.parseInt(centroPol[1])),
                    lados, radioPol, color, colorRelleno);
                
            case "PoligonoIrregular":
                PoligonoIrregular pi = new PoligonoIrregular(color, colorRelleno);
                String[] puntos = datos.split(";");
                for (String punto : puntos) {
                    if (!punto.isEmpty()) {
                        String[] coord = punto.split(",");
                        pi.agregarPunto(new Point(Integer.parseInt(coord[0]), Integer.parseInt(coord[1])));
                    }
                }
                return pi;
                
            default:
                throw new IllegalArgumentException("Tipo de figura desconocido: " + tipo);
        }
    }

    public List<Dibujo> listarDibujos() throws SQLException {
        List<Dibujo> dibujos = new ArrayList<>();
        
        String sql = "SELECT id, fecha_creacion FROM dibujos ORDER BY fecha_creacion DESC";
        
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                dibujos.add(new Dibujo(rs.getInt("id"), rs.getTimestamp("fecha_creacion")));
            }
        }
        
        return dibujos;
    }

    public void eliminarDibujo(int dibujoId) throws SQLException {
        try (Connection conn = conectar()) {
            conn.setAutoCommit(false);
            
            try (PreparedStatement psFiguras = conn.prepareStatement(
                "DELETE FROM figuras WHERE dibujo_id = ?")) {
                psFiguras.setInt(1, dibujoId);
                psFiguras.executeUpdate();
            }
            
            try (PreparedStatement psDibujo = conn.prepareStatement(
                "DELETE FROM dibujos WHERE id = ?")) {
                psDibujo.setInt(1, dibujoId);
                psDibujo.executeUpdate();
            }
            
            conn.commit();
        }
    }

    private String colorToHex(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

    private Color hexToColor(String hex) {
        return new Color(
            Integer.parseInt(hex.substring(1, 3), 16),
            Integer.parseInt(hex.substring(3, 5), 16),
            Integer.parseInt(hex.substring(5, 7), 16));
    }
}