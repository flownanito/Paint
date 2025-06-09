
package mvc.modelo;

import java.sql.Timestamp;

class Dibujo {
    private int id;
    private Timestamp fechaCreacion;
    
    public Dibujo(int id, Timestamp fechaCreacion) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
    }
    
    public int getId() { return id; }
    public Timestamp getFechaCreacion() { return fechaCreacion; }
    
    @Override
    public String toString() {
        return "Dibujo #" + id + " - " + fechaCreacion.toString();
    }
}