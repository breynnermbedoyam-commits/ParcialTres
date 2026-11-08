package co.uniquindio.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    private Cliente cliente;
    private PlatoFuerte plato;
    private Bebida bebida;
    private Pedido pedido;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("1001", "Ana Gomez", "300", "ana@correo.com", "Calle 10");
        plato = new PlatoFuerte("PF01", "Churrasco", "Carne", 20000, 30, 500, TipoPreparacion.PLANCHA, true);
        bebida = new Bebida("BE01", "Jugo", "Natural", 5000, 10, 300, 5, true, false);
        pedido = new Pedido("PED-001", java.time.LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE);
    }

    @Test
    @DisplayName("El valor de los productos suma los subtotales de todos los detalles")
    void calculaValorDeProductos() {
        assertEquals(0.0, pedido.calcularValorProductos(), 0.001);
        pedido.agregarProducto(plato, 2);   // 2 x 24.000
        pedido.agregarProducto(bebida, 1);  // 1 x 5.500
        assertEquals(53500.0, pedido.calcularValorProductos(), 0.001);
    }

    @Test
    @DisplayName("El total de un pedido a domicilio incluye el costo del domicilio")
    void totalIncluyeCostoDeDomicilio() {
        pedido.setModalidad(ModalidadEntrega.DOMICILIO);
        pedido.agregarProducto(plato, 2);   // 48.000
        Domicilio d = new Domicilio(new RapidoExpress(), 10);
        d.solicitarServicio();              // 20.000
        pedido.setDomicilio(d);
        assertEquals(68000.0, pedido.calcularTotal(), 0.001);
    }

    @Test
    @DisplayName("Un pedido valido se confirma una sola vez")
    void confirmaUnaSolaVez() {
        pedido.agregarProducto(plato, 1);
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
        assertTrue(pedido.confirmar());
        assertEquals(EstadoPedido.CONFIRMADO, pedido.getEstado());
        assertFalse(pedido.confirmar());
    }

    @Test
    @DisplayName("No se confirma si un producto incumple la calidad o la cantidad es cero")
    void noConfirmaConProductoInvalidoOCantidadCero() {
        plato.setCoccionAprobada(false);
        pedido.agregarProducto(plato, 1);
        assertFalse(pedido.confirmar());
        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());

        Pedido otro = new Pedido("PED-002", java.time.LocalDate.now(), cliente, ModalidadEntrega.RESTAURANTE);
        otro.agregarProducto(bebida, 0);
        assertFalse(otro.confirmar());
    }

    @Test
    @DisplayName("Un pedido a domicilio solo se confirma cuando la empresa acepta el servicio")
    void domicilioDebeSerAceptado() {
        pedido.setModalidad(ModalidadEntrega.DOMICILIO);
        pedido.agregarProducto(plato, 1);
        assertFalse(pedido.confirmar());                       // sin domicilio

        Domicilio lejos = new Domicilio(new RapidoExpress(), 15);
        lejos.solicitarServicio();                              // rechazado
        pedido.setDomicilio(lejos);
        assertFalse(pedido.confirmar());

        Domicilio ok = new Domicilio(new EntregaSegura(), 15);
        ok.solicitarServicio();                                 // aceptado
        pedido.setDomicilio(ok);
        assertTrue(pedido.confirmar());
    }
}