package co.uniquindio.edu;

public class Domicilio {

    private EmpresaDomicilio empresa;
    private double distanciaKm;
    private double costo;
    private boolean aceptado;

    public Domicilio(EmpresaDomicilio empresa, double distanciaKm) {
        this.empresa = empresa;
        this.distanciaKm = distanciaKm;
        this.costo = 0;
        this.aceptado = false;
    }

    public EmpresaDomicilio getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDomicilio empresa) {
        this.empresa = empresa;
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {
        this.distanciaKm = distanciaKm;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public boolean getAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }
}
