package mvc.vista;

import javax.swing.*;
import java.awt.*;

public class VistaPaint extends JFrame {
    public JButton botonPunto;
    public JButton botonLinea;
    public JButton botonCirculo;
    public JButton botonPoligonoRegular;
    public JButton botonPoligonoIrregular;
    public JButton botonColor;
    public JButton botonColorRelleno;
    public JSlider sliderLados;
    public JLabel etiquetaLados;
    public JCheckBox checkboxRelleno;
    public JButton botonGuardar;
    public JButton botonCargar;
    public JButton botonEliminar;
    public JButton botonGenerarSVG;
    public JPanel lienzo;

    public VistaPaint() {
        super("Paint - MVC");
        setLayout(null);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel de herramientas
        JPanel panelHerramientas = new JPanel();
        panelHerramientas.setLayout(null);
        panelHerramientas.setBounds(10, 10, 200, 540);
        add(panelHerramientas);

        // Botones de figuras
        botonPunto = new JButton("Punto");
        botonPunto.setBounds(10, 10, 180, 30);
        panelHerramientas.add(botonPunto);

        botonLinea = new JButton("Línea");
        botonLinea.setBounds(10, 50, 180, 30);
        panelHerramientas.add(botonLinea);

        botonCirculo = new JButton("Círculo");
        botonCirculo.setBounds(10, 90, 180, 30);
        panelHerramientas.add(botonCirculo);

        botonPoligonoRegular = new JButton("Polígono Regular");
        botonPoligonoRegular.setBounds(10, 130, 180, 30);
        panelHerramientas.add(botonPoligonoRegular);

        botonPoligonoIrregular = new JButton("Polígono Irregular");
        botonPoligonoIrregular.setBounds(10, 170, 180, 30);
        panelHerramientas.add(botonPoligonoIrregular);

        // Configuración de colores
        botonColor = new JButton("Color");
        botonColor.setBounds(10, 210, 85, 30);
        panelHerramientas.add(botonColor);

        botonColorRelleno = new JButton("Color Relleno");
        botonColorRelleno.setBounds(105, 210, 85, 30);
        panelHerramientas.add(botonColorRelleno);

        // Configuración de polígonos
        checkboxRelleno = new JCheckBox("Relleno");
        checkboxRelleno.setBounds(10, 250, 180, 30);
        panelHerramientas.add(checkboxRelleno);

        etiquetaLados = new JLabel("Lados: 5");
        etiquetaLados.setBounds(10, 290, 180, 20);
        panelHerramientas.add(etiquetaLados);

        sliderLados = new JSlider(3, 12, 5);
        sliderLados.setBounds(10, 310, 180, 50);
        panelHerramientas.add(sliderLados);

        // Operaciones CRUD
        botonGuardar = new JButton("Guardar Dibujo");
        botonGuardar.setBounds(10, 370, 180, 30);
        panelHerramientas.add(botonGuardar);

        botonCargar = new JButton("Cargar Dibujo");
        botonCargar.setBounds(10, 410, 180, 30);
        panelHerramientas.add(botonCargar);

        botonEliminar = new JButton("Eliminar Dibujo");
        botonEliminar.setBounds(10, 450, 180, 30);
        panelHerramientas.add(botonEliminar);

        botonGenerarSVG = new JButton("Generar SVG");
        botonGenerarSVG.setBounds(10, 490, 180, 30);
        panelHerramientas.add(botonGenerarSVG);

        // Lienzo
        lienzo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        lienzo.setBounds(220, 10, 560, 540);
        lienzo.setBackground(Color.WHITE);
        lienzo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        add(lienzo);

        setVisible(true);
    }
}