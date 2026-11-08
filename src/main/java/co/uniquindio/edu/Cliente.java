package co.uniquindio.edu;

import java.util.ArrayList;
import java.util.List;

public class Cliente {

    private String documento;
    private String nombreCompleto;
    private String telefono;
    private String correo;
    private String direccion;
    private List<Pedido> historialPedidos;

    public Cliente(String documento, String nombreCompleto, String telefono, String correo, String direccion) {
        this.documento = documento;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.historialPedidos = new ArrayList<>();
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }

    public void setHistorialPedidos(List<Pedido> historialPedidos) {
        this.historialPedidos = historialPedidos;
    }
    /** Agrega un pedido confirmado al historial evitando repetidos. */
    public void agregarPedido(Pedido pedido) {
        if (pedido != null && !historialPedidos.contains(pedido)) {
            historialPedidos.add(pedido);
        }
    }

    @Override
    public String toString() {
        return documento + " - " + nombreCompleto;
    }
}
