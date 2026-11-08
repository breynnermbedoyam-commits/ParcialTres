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
    /** Bebida: 10 % de incremento + $3.000 si requiere preparacion especial. */
    @Override
    public double calcularPrecioVenta() {
        double precio = getPrecioBase() * 1.10;
        if (requierePreparacionEspecial) {
            precio += 3000;
        }
        return precio;
    }

    /** Volumen 150-1500 ml; fria entre 0 y 10 C, caliente entre 55 y 85 C. */
    @Override
    public boolean cumpleCondicionesCalidad() {
        boolean volumenValido = volumenMl >= 150 && volumenMl <= 1500;
        boolean temperaturaValida = esFria
                ? (temperaturaServicio >= 0 && temperaturaServicio <= 10)
                : (temperaturaServicio >= 55 && temperaturaServicio <= 85);
        return volumenValido && temperaturaValida;
    }
}
