package co.uniquindio.edu;

public class EntregaSegura implements EmpresaDomicilio {

    private double tarifaBase;
    private double tarifaPorKm;
    private double recargo;
    private double maxKm;

    public EntregaSegura() {
        this.tarifaBase = 7000;
        this.tarifaPorKm = 1000;
        this.recargo = 3000;
        this.maxKm = 20;
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

    public double getRecargo() {
        return recargo;
    }

    public void setRecargo(double recargo) {
        this.recargo = recargo;
    }

    public double getMaxKm() {
        return maxKm;
    }

    public void setMaxKm(double maxKm) {
        this.maxKm = maxKm;
    }
}
