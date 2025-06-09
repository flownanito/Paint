package mvc.controlador;

import java.sql.Timestamp;

public class Dibujo {
    private final int id;
    private final Timestamp fechaCreacion;
    
    public Dibujo(int id, Timestamp fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
    }
    
    public int getId() {
        return id;
    }
    
    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }
    
    @Override
    public String toString() {
        return "Dibujo #" + id + " - " + fechaCreacion.toString();
    }
}