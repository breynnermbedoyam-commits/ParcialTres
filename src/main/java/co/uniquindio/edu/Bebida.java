package co.uniquindio.edu;

public class Bebida extends Producto {

    private double volumenMl;
    private double temperaturaServicio;
    private boolean esFria;
    private boolean requierePreparacionEspecial;

    public Bebida(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, double volumenMl, double temperaturaServicio, boolean esFria, boolean requierePreparacionEspecial) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);
        this.volumenMl = volumenMl;
        this.temperaturaServicio = temperaturaServicio;
        this.esFria = esFria;
        this.requierePreparacionEspecial = requierePreparacionEspecial;
    }

    public double getVolumenMl() {
        return volumenMl;
    }

    public void setVolumenMl(double volumenMl) {
        this.volumenMl = volumenMl;
    }

    public double getTemperaturaServicio() {
        return temperaturaServicio;
    }

    public void setTemperaturaServicio(double temperaturaServicio) {
        this.temperaturaServicio = temperaturaServicio;
    }

    public boolean getEsFria() {
        return esFria;
    }

    public void setEsFria(boolean esFria) {
        this.esFria = esFria;
    }

    public boolean getRequierePreparacionEspecial() {
        return requierePreparacionEspecial;
    }

    public void setRequierePreparacionEspecial(boolean requierePreparacionEspecial) {
        this.requierePreparacionEspecial = requierePreparacionEspecial;
    }
}
