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
    /** Plato fuerte: 20 % de incremento sobre el precio base. */
    @Override
    public double calcularPrecioVenta() {
        return getPrecioBase() * 1.20;
    }

    /** Peso 200-900 g, preparacion 10-60 min y coccion aprobada. */
    @Override
    public boolean cumpleCondicionesCalidad() {
        return pesoGramos >= 200 && pesoGramos <= 900
                && getTiempoPreparacion() >= 10 && getTiempoPreparacion() <= 60
                && coccionAprobada;
    }
}
