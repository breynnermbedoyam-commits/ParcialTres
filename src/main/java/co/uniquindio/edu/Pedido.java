package co.uniquindio.edu;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {

    private String codigo;
    private LocalDate fecha;
    private ModalidadEntrega modalidad;
    private EstadoPedido estado;
    private Cliente cliente;
    private List<DetallePedido> detalles;
    private Domicilio domicilio;

    public Pedido(String codigo, LocalDate fecha, Cliente cliente, ModalidadEntrega modalidad) {
        this.codigo = codigo;
        this.fecha = fecha;
        this.cliente = cliente;
        this.modalidad = modalidad;
        this.estado = EstadoPedido.PENDIENTE;
        this.detalles = new ArrayList<>();
        this.domicilio = null;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ModalidadEntrega getModalidad() {
        return modalidad;
    }

    public void setModalidad(ModalidadEntrega modalidad) {
        this.modalidad = modalidad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
    public void agregarProducto(Producto producto, int cantidad) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        if (estado != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("Solo se pueden agregar productos a pedidos pendientes.");
        }
        detalles.add(new DetallePedido(producto, cantidad));
    }

    public double calcularValorProductos() {
        double total = 0;
        for (DetallePedido d : detalles) {
            total += d.calcularSubtotal();
        }
        return total;
    }

    /** Productos + costo del domicilio cuando corresponda. */
    public double calcularTotal() {
        double total = calcularValorProductos();
        if (modalidad == ModalidadEntrega.DOMICILIO && domicilio != null) {
            total += domicilio.getCosto();
        }
        return total;
    }


    public boolean confirmar() {
        if (estado != EstadoPedido.PENDIENTE || cliente == null || detalles.isEmpty()) {
            return false;
        }
        for (DetallePedido d : detalles) {
            if (d.getProducto() == null || d.getCantidad() <= 0
                    || !d.getProducto().cumpleCondicionesCalidad()) {
                return false;
            }
        }
        if (modalidad == ModalidadEntrega.DOMICILIO
                && (domicilio == null || !domicilio.getAceptado())) {
            return false;
        }
        estado = EstadoPedido.CONFIRMADO;
        return true;
    }

    public void marcarEntregado() {
        if (estado != EstadoPedido.CONFIRMADO) {
            throw new IllegalStateException("Solo un pedido confirmado puede marcarse como entregado.");
        }
        estado = EstadoPedido.ENTREGADO;
    }

    public void cancelar() {
        if (estado == EstadoPedido.ENTREGADO) {
            throw new IllegalStateException("Un pedido entregado no puede cancelarse.");
        }
        estado = EstadoPedido.CANCELADO;
    }

    @Override
    public String toString() {
        return codigo + " | " + fecha + " | " + modalidad + " | " + estado + " | $" + calcularTotal();
    }
}
