package co.uniquindio.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("1001", "Ana Gomez", "3001112233", "ana@correo.com", "Calle 10 #5-20");
    }

    private Pedido nuevoPedido(String codigo) {
        return new Pedido(codigo, java.time.LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE);
    }

    @Test
    @DisplayName("El constructor guarda todos los datos del cliente")
    void guardaLosDatosDelCliente() {
        assertEquals("1001", cliente.getDocumento());
        assertEquals("Ana Gomez", cliente.getNombreCompleto());
        assertEquals("3001112233", cliente.getTelefono());
        assertEquals("ana@correo.com", cliente.getCorreo());
        assertEquals("Calle 10 #5-20", cliente.getDireccion());
    }

    @Test
    @DisplayName("Un cliente nuevo tiene el historial vacio")
    void historialInicialVacio() {
        assertNotNull(cliente.getHistorialPedidos());
        assertTrue(cliente.getHistorialPedidos().isEmpty());
    }

    @Test
    @DisplayName("agregarPedido registra el pedido en el historial")
    void agregarPedidoLoRegistra() {
        Pedido p = nuevoPedido("PED-001");
        cliente.agregarPedido(p);
        assertEquals(1, cliente.getHistorialPedidos().size());
        assertTrue(cliente.getHistorialPedidos().contains(p));
    }

    @Test
    @DisplayName("agregarPedido no duplica un pedido ya registrado")
    void agregarPedidoNoDuplica() {
        Pedido p = nuevoPedido("PED-001");
        cliente.agregarPedido(p);
        cliente.agregarPedido(p);
        assertEquals(1, cliente.getHistorialPedidos().size());
    }

    @Test
    @DisplayName("agregarPedido ignora un pedido nulo")
    void agregarPedidoNuloSeIgnora() {
        cliente.agregarPedido(null);
        assertTrue(cliente.getHistorialPedidos().isEmpty());
    }
}