package pe.netdreams.invasive_pollution.Model;

public class Nave {
    public int id;
    public String nombre;
    public int recurso;
    public int blindaje;

    public int vida;
    public int cadencia;
    public int precio;

    public Nave(int id, String nombre, int recurso, int blindaje, int vida, int cadencia, int precio) {
        this.id = id;
        this.nombre = nombre;
        this.recurso = recurso;
        this.blindaje = blindaje;
        this.vida = vida;
        this.cadencia = cadencia;
        this.precio = precio;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getCadencia() {
        return cadencia;
    }

    public void setCadencia(int cadencia) {
        this.cadencia = cadencia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRecurso() {
        return recurso;
    }

    public void setRecurso(int recurso) {
        this.recurso = recurso;
    }

    public int getBlindaje() {
        return blindaje;
    }

    public void setBlindaje(int blindaje) {
        this.blindaje = blindaje;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
