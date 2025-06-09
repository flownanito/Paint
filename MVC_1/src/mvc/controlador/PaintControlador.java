package mvc.controlador;

import mvc.modelo.*;
import mvc.vista.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.List;
import java.util.ArrayList;

public class PaintControlador {
    private final FiguraDAO dao = new FiguraDAO();
    private final VistaPaint vista;
    private List<Figura> figuras;
    private Figura figuraTemporal;
    private Point primerClick;
    private String figuraSeleccionada = "Punto";
    private Color colorSeleccionado = Color.BLACK;
    private Color colorRelleno = null;
    private int ladosPoligono = 5;
    private boolean relleno = false;

    public PaintControlador() {
        vista = new VistaPaint();
        figuras = new ArrayList<>(); // Cambiado de dao.listar() a nueva lista
        
        configurarListeners();
        actualizarLienzo();
    }

    private void configurarListeners() {
        vista.botonPunto.addActionListener(e -> figuraSeleccionada = "Punto");
        vista.botonLinea.addActionListener(e -> figuraSeleccionada = "Linea");
        vista.botonCirculo.addActionListener(e -> figuraSeleccionada = "Circulo");
        vista.botonPoligonoRegular.addActionListener(e -> figuraSeleccionada = "PoligonoRegular");
        vista.botonPoligonoIrregular.addActionListener(e -> figuraSeleccionada = "PoligonoIrregular");
        
        vista.sliderLados.addChangeListener(e -> {
            ladosPoligono = vista.sliderLados.getValue();
            vista.etiquetaLados.setText("Lados: " + ladosPoligono);
        });
        
        vista.checkboxRelleno.addActionListener(e -> {
            relleno = vista.checkboxRelleno.isSelected();
        });
        
        vista.botonColor.addActionListener(e -> {
            colorSeleccionado = JColorChooser.showDialog(vista, "Seleccione color", colorSeleccionado);
        });
        
        vista.botonColorRelleno.addActionListener(e -> {
            colorRelleno = JColorChooser.showDialog(vista, "Seleccione color de relleno", 
                colorRelleno != null ? colorRelleno : Color.WHITE);
        });
        
        vista.lienzo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manejarClick(e);
            }
        });
        
        vista.botonGuardar.addActionListener(e -> guardarDibujo());
        vista.botonCargar.addActionListener(e -> cargarDibujo());
        vista.botonEliminar.addActionListener(e -> eliminarDibujo());
        vista.botonGenerarSVG.addActionListener(e -> generarSVG());
    }

    private void manejarClick(MouseEvent e) {
        if (primerClick == null) {
            primerClick = e.getPoint();
            
            if (figuraSeleccionada.equals("PoligonoIrregular")) {
                figuraTemporal = new PoligonoIrregular(colorSeleccionado, colorRelleno);
                ((PoligonoIrregular)figuraTemporal).agregarPunto(primerClick);
            }
        } else {
            Point segundoClick = e.getPoint();
            
            switch (figuraSeleccionada) {
                case "Punto":
                    figuras.add(new Punto(primerClick, colorSeleccionado));
                    break;
                case "Linea":
                    figuras.add(new Linea(primerClick, segundoClick, colorSeleccionado));
                    break;
                case "Circulo":
                    double radio = primerClick.distance(segundoClick);
                    figuras.add(new Circulo(primerClick, radio, colorSeleccionado, relleno ? colorRelleno : null));
                    break;
                case "PoligonoRegular":
                    double radioPol = primerClick.distance(segundoClick);
                    figuras.add(new PoligonoRegular(primerClick, ladosPoligono, radioPol, colorSeleccionado, relleno ? colorRelleno : null));
                    break;
                case "PoligonoIrregular":
                    ((PoligonoIrregular)figuraTemporal).agregarPunto(segundoClick);
                    if (e.getClickCount() == 2) {
                        if (!((PoligonoIrregular)figuraTemporal).tieneLadosCruzados()) {
                            figuras.add(figuraTemporal);
                        } else {
                            JOptionPane.showMessageDialog(vista, "Los lados del polígono no pueden cruzarse", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        figuraTemporal = null;
                        primerClick = null;
                    }
                    break;
            }
            
            if (!figuraSeleccionada.equals("PoligonoIrregular")) {
                primerClick = null;
            }
            
            actualizarLienzo();
        }
    }

    private void actualizarLienzo() {
        Graphics2D g2d = (Graphics2D) vista.lienzo.getGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, vista.lienzo.getWidth(), vista.lienzo.getHeight());
        
        for (Figura figura : figuras) {
            figura.dibujar(g2d);
        }
        
        if (figuraTemporal != null) {
            figuraTemporal.dibujar(g2d);
        }
    }

    private void guardarDibujo() {
        try {
            dao.guardarDibujo(figuras);
            JOptionPane.showMessageDialog(vista, "Dibujo guardado en la base de datos", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarDibujo() {
        try {
            List<Dibujo> dibujos = dao.listarDibujos();
            if (!dibujos.isEmpty()) {
                Dibujo seleccionado = (Dibujo) JOptionPane.showInputDialog(
                    vista,
                    "Seleccione un dibujo:",
                    "Cargar dibujo",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    dibujos.toArray(new Dibujo[0]),
                    dibujos.get(0));
                
                if (seleccionado != null) {
                    figuras = dao.cargarDibujo(seleccionado.getId());
                    actualizarLienzo();
                }
            } else {
                JOptionPane.showMessageDialog(vista, "No hay dibujos guardados", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al cargar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarDibujo() {
        try {
            List<Dibujo> dibujos = dao.listarDibujos();
            if (!dibujos.isEmpty()) {
                Dibujo seleccionado = (Dibujo) JOptionPane.showInputDialog(
                    vista,
                    "Seleccione un dibujo a eliminar:",
                    "Eliminar dibujo",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    dibujos.toArray(new Dibujo[0]),
                    dibujos.get(0));
                
                if (seleccionado != null) {
                    dao.eliminarDibujo(seleccionado.getId());
                    JOptionPane.showMessageDialog(vista, "Dibujo eliminado", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "No hay dibujos guardados", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generarSVG() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".svg")) {
                    filePath += ".svg";
                }
                
                StringBuilder svg = new StringBuilder();
                svg.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
                svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"")
                   .append(vista.lienzo.getWidth()).append("\" height=\"")
                   .append(vista.lienzo.getHeight()).append("\">\n");
                
                for (Figura figura : figuras) {
                    svg.append(figura.toSVG()).append("\n");
                }
                
                svg.append("</svg>");
                
                try (java.io.FileWriter writer = new java.io.FileWriter(filePath)) {
                    writer.write(svg.toString());
                    JOptionPane.showMessageDialog(vista, "Archivo SVG generado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al generar SVG: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}