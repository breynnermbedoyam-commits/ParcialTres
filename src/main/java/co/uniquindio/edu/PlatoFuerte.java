package co.uniquindio.edu;

public class PlatoFuerte extends Producto {

    private double pesoGramos;
    private TipoPreparacion tipoPreparacion;
    private boolean coccionAprobada;

    public PlatoFuerte(String codigo, String nombre, String descripcion, double precioBase, int tiempoPreparacion, double pesoGramos, TipoPreparacion tipoPreparacion, boolean coccionAprobada) {
        super(codigo, nombre, descripcion, precioBase, tiempoPreparacion);
        this.pesoGramos = pesoGramos;
        this.tipoPreparacion = tipoPreparacion;
        this.coccionAprobada = coccionAprobada;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setPesoGramos(double pesoGramos) {
        this.pesoGramos = pesoGramos;
    }

    public TipoPreparacion getTipoPreparacion() {
        return tipoPreparacion;
    }

    public void setTipoPreparacion(TipoPreparacion tipoPreparacion) {
        this.tipoPreparacion = tipoPreparacion;
    }

    public boolean getCoccionAprobada() {
        return coccionAprobada;
    }

    public void setCoccionAprobada(boolean coccionAprobada) {
        this.coccionAprobada = coccionAprobada;
    }
}
