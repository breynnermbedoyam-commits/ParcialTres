package co.uniquindio.edu;

import java.util.Map;

public class ReporteVentas {

    private double ingresosRestaurante;
    private double ingresosDomicilios;
    private Producto productoMasVendido;
    private int unidadesProductoMasVendido;
    private String categoriaMayorIngresos;
    private Map<String, Double> ingresosPorCategoria;

    public ReporteVentas(double ingresosRestaurante, double ingresosDomicilios, Producto productoMasVendido, int unidadesProductoMasVendido, String categoriaMayorIngresos, Map<String, Double> ingresosPorCategoria) {
        this.ingresosRestaurante = ingresosRestaurante;
        this.ingresosDomicilios = ingresosDomicilios;
        this.productoMasVendido = productoMasVendido;
        this.unidadesProductoMasVendido = unidadesProductoMasVendido;
        this.categoriaMayorIngresos = categoriaMayorIngresos;
        this.ingresosPorCategoria = ingresosPorCategoria;
    }

    public double getIngresosRestaurante() {
        return ingresosRestaurante;
    }

    public void setIngresosRestaurante(double ingresosRestaurante) {
        this.ingresosRestaurante = ingresosRestaurante;
    }

    public double getIngresosDomicilios() {
        return ingresosDomicilios;
    }

    public void setIngresosDomicilios(double ingresosDomicilios) {
        this.ingresosDomicilios = ingresosDomicilios;
    }

    public Producto getProductoMasVendido() {
        return productoMasVendido;
    }

    public void setProductoMasVendido(Producto productoMasVendido) {
        this.productoMasVendido = productoMasVendido;
    }

    public int getUnidadesProductoMasVendido() {
        return unidadesProductoMasVendido;
    }

    public void setUnidadesProductoMasVendido(int unidadesProductoMasVendido) {
        this.unidadesProductoMasVendido = unidadesProductoMasVendido;
    }

    public String getCategoriaMayorIngresos() {
        return categoriaMayorIngresos;
    }

    public void setCategoriaMayorIngresos(String categoriaMayorIngresos) {
        this.categoriaMayorIngresos = categoriaMayorIngresos;
    }

    public Map<String, Double> getIngresosPorCategoria() {
        return ingresosPorCategoria;
    }

    public void setIngresosPorCategoria(Map<String, Double> ingresosPorCategoria) {
        this.ingresosPorCategoria = ingresosPorCategoria;
    }
    @Override
    public String toString() {
        return "REPORTE DE VENTAS\n"
                + "Ingresos del restaurante: $" + ingresosRestaurante + "\n"
                + "Cobrado por domicilios (no es ingreso del restaurante): $" + ingresosDomicilios + "\n"
                + "Producto mas vendido: " + (productoMasVendido == null ? "N/A"
                : productoMasVendido.getNombre() + " (" + unidadesProductoMasVendido + " unidades)") + "\n"
                + "Categoria con mayores ingresos: " + (categoriaMayorIngresos == null ? "N/A" : categoriaMayorIngresos) + "\n"
                + "Ingresos por categoria: " + ingresosPorCategoria;
    }
}
