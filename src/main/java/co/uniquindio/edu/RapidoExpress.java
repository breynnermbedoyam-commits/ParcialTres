package co.uniquindio.edu;

public class RapidoExpress implements EmpresaDomicilio {

    private double tarifaBase;
    private double tarifaPorKm;
    private double maxKm;

    public RapidoExpress() {
        this.tarifaBase = 5000;
        this.tarifaPorKm = 1500;
        this.maxKm = 12;
    }

    public double getTarifaBase() {
        return tarifaBase;
    }

    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }

    public double getTarifaPorKm() {
        return tarifaPorKm;
    }

    public void setTarifaPorKm(double tarifaPorKm) {
        this.tarifaPorKm = tarifaPorKm;
    }

    public double getMaxKm() {
        return maxKm;
    }

    public void setMaxKm(double maxKm) {
        this.maxKm = maxKm;
    }

    @Override
    public String getNombre() {
        return "RapidoExpress";
    }

    @Override
    public boolean aceptaDistancia(double km) {
        return km > 0 && km <= maxKm;
    }

    /** $5.000 base + $1.500 por kilometro. */
    @Override
    public double calcularCosto(double km) {
        return tarifaBase + (tarifaPorKm * km);
    }

    @Override
    public boolean aceptarServicio(double km) {
        return aceptaDistancia(km);
    }

}
