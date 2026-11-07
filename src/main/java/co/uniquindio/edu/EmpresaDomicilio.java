package co.uniquindio.edu;

public interface EmpresaDomicilio {

    String getNombre();

    /** Verifica que la distancia sea mayor que cero y este dentro del maximo permitido. */
    boolean aceptaDistancia(double km);

    /** Costo del domicilio para la distancia indicada. */
    double calcularCosto(double km);

    /** La empresa acepta (true) o rechaza (false) el servicio. */
    boolean aceptarServicio(double km);

}
