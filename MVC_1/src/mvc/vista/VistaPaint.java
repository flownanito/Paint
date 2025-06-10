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
        super("? Paint - MVC Estilo Moderno");
        setLayout(null);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Estilo general
        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);
        Color colorFondo = new Color(245, 245, 245);
        Color colorBoton = new Color(66, 133, 244);
        Color colorTexto = Color.WHITE;

        getContentPane().setBackground(colorFondo);

        // Panel de herramientas
        JPanel panelHerramientas = new JPanel(null);
        panelHerramientas.setBounds(10, 10, 200, 540);
        panelHerramientas.setBackground(new Color(230, 230, 230));
        panelHerramientas.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        add(panelHerramientas);

        botonPunto = crearBoton("Punto", fuente, colorBoton, colorTexto);
        botonPunto.setBounds(10, 10, 180, 30);
        panelHerramientas.add(botonPunto);

        botonLinea = crearBoton("Línea", fuente, colorBoton, colorTexto);
        botonLinea.setBounds(10, 50, 180, 30);
        panelHerramientas.add(botonLinea);

        botonCirculo = crearBoton("Círculo", fuente, colorBoton, colorTexto);
        botonCirculo.setBounds(10, 90, 180, 30);
        panelHerramientas.add(botonCirculo);

        botonPoligonoRegular = crearBoton("Polígono Regular", fuente, colorBoton, colorTexto);
        botonPoligonoRegular.setBounds(10, 130, 180, 30);
        panelHerramientas.add(botonPoligonoRegular);

        botonPoligonoIrregular = crearBoton("Polígono Irregular", fuente, colorBoton, colorTexto);
        botonPoligonoIrregular.setBounds(10, 170, 180, 30);
        panelHerramientas.add(botonPoligonoIrregular);

        botonColor = crearBoton("Color", fuente, new Color(52, 168, 83), colorTexto);
        botonColor.setBounds(10, 210, 85, 30);
        panelHerramientas.add(botonColor);

        botonColorRelleno = crearBoton("Color Relleno", fuente, new Color(234, 67, 53), colorTexto);
        botonColorRelleno.setBounds(105, 210, 85, 30);
        panelHerramientas.add(botonColorRelleno);

        checkboxRelleno = new JCheckBox("Relleno");
        checkboxRelleno.setBounds(10, 250, 180, 30);
        checkboxRelleno.setBackground(panelHerramientas.getBackground());
        checkboxRelleno.setFont(fuente);
        panelHerramientas.add(checkboxRelleno);

        etiquetaLados = new JLabel("Lados: 5");
        etiquetaLados.setBounds(10, 290, 180, 20);
        etiquetaLados.setFont(fuente);
        panelHerramientas.add(etiquetaLados);

        sliderLados = new JSlider(3, 12, 5);
        sliderLados.setBounds(10, 310, 180, 50);
        panelHerramientas.add(sliderLados);

        botonGuardar = crearBoton("Guardar Dibujo", fuente, colorBoton, colorTexto);
        botonGuardar.setBounds(10, 370, 180, 30);
        panelHerramientas.add(botonGuardar);

        botonCargar = crearBoton("Cargar Dibujo", fuente, colorBoton, colorTexto);
        botonCargar.setBounds(10, 410, 180, 30);
        panelHerramientas.add(botonCargar);

        botonEliminar = crearBoton("Eliminar Dibujo", fuente, new Color(219, 68, 55), colorTexto);
        botonEliminar.setBounds(10, 450, 180, 30);
        panelHerramientas.add(botonEliminar);

        botonGenerarSVG = crearBoton("Generar SVG", fuente, colorBoton, colorTexto);
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
        lienzo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        add(lienzo);

        setVisible(true);
    }

    private JButton crearBoton(String texto, Font fuente, Color fondo, Color textoColor) {
        JButton boton = new JButton(texto);
        boton.setFont(fuente);
        boton.setBackground(fondo);
        boton.setForeground(textoColor);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return boton;
    }
}
