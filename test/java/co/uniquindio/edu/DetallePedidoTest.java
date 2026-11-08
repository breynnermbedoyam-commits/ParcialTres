package co.uniquindio.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DetallePedidoTest {

    private PlatoFuerte plato;

    @BeforeEach
    void setUp() {
        plato = new PlatoFuerte("PF01", "Churrasco", "Carne", 20000, 30, 500, TipoPreparacion.PLANCHA, true);
    }

    @Test
    @DisplayName("Subtotal de un plato fuerte = precio de venta x cantidad")
    void subtotalDePlatoFuerte() {
        DetallePedido d = new DetallePedido(plato, 2);
        assertEquals(48000.0, d.calcularSubtotal(), 0.001);
    }

    @Test
    @DisplayName("Subtotal de una bebida con preparacion especial")
    void subtotalDeBebidaConRecargo() {
        Bebida b = new Bebida("BE01", "Limonada", "Especial", 5000, 10, 300, 5, true, true);
        DetallePedido d = new DetallePedido(b, 3);
        assertEquals(25500.0, d.calcularSubtotal(), 0.001);
    }

    @Test
    @DisplayName("Subtotal de un postre refrigerado")
    void subtotalDePostreRefrigerado() {
        Postre p = new Postre("PO01", "Tres leches", "Nevera", 8000, 5, 200, 4, true);
        DetallePedido d = new DetallePedido(p, 1);
        assertEquals(9600.0, d.calcularSubtotal(), 0.001);
    }

    @Test
    @DisplayName("Al cambiar la cantidad el subtotal se recalcula")
    void subtotalSeActualizaAlCambiarCantidad() {
        DetallePedido d = new DetallePedido(plato, 1);
        d.setCantidad(5);
        assertEquals(5, d.getCantidad());
        assertEquals(120000.0, d.calcularSubtotal(), 0.001);
    }

    @Test
    @DisplayName("Al cambiar el producto el subtotal usa el nuevo precio")
    void subtotalSeActualizaAlCambiarProducto() {
        DetallePedido d = new DetallePedido(plato, 2);
        Bebida b = new Bebida("BE01", "Jugo", "Natural", 5000, 10, 300, 5, true, false);
        d.setProducto(b);
        assertEquals(b, d.getProducto());
        assertEquals(11000.0, d.calcularSubtotal(), 0.001);
    }
}
