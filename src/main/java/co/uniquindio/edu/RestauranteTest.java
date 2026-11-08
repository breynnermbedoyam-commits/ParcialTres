import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RestauranteTest {

    private Restaurante restaurante;
    private Cliente cliente;
    private PlatoFuerte plato;
    private Bebida bebida;

    @BeforeEach
    void setUp() {
        restaurante = new Restaurante("Sabor & Tradicion", "900123456-7", "Armenia");
        cliente = new Cliente("1001", "Ana Gomez", "300", "ana@correo.com", "Calle 10");
        plato = new PlatoFuerte("PF01", "Churrasco", "Carne", 20000, 30, 500, TipoPreparacion.PLANCHA, true);
        bebida = new Bebida("BE01", "Jugo", "Natural", 5000, 10, 300, 5, true, false);
        restaurante.registrarCliente(cliente);
        restaurante.registrarProducto(plato);
        restaurante.registrarProducto(bebida);
    }

    @Test
    @DisplayName("No se pueden registrar dos productos con el mismo codigo")
    void noPermiteCodigosDeProductoDuplicados() {
        Postre repetido = new Postre("PF01", "Flan", "Flan", 8000, 5, 150, 20, false);
        assertFalse(restaurante.registrarProducto(repetido));
        assertEquals(2, restaurante.consultarMenu().size());
    }

    @Test
    @DisplayName("No se pueden registrar dos clientes con el mismo documento")
    void noPermiteDocumentosDeClienteRepetidos() {
        Cliente repetido = new Cliente("1001", "Otro Nombre", "311", "otro@correo.com", "Otra calle");
        assertFalse(restaurante.registrarCliente(repetido));
        assertEquals(1, restaurante.getClientes().size());
    }

    @Test
    @DisplayName("No confirma pedidos de clientes no registrados ni con productos fuera del menu")
    void rechazaClienteNoRegistradoOProductoFueraDelMenu() {
        Cliente noRegistrado = new Cliente("9999", "Visitante", "300", "v@correo.com", "Calle 1");
        Pedido p1 = restaurante.registrarPedido(noRegistrado, ModalidadEntrega.RESTAURANTE);
        p1.agregarProducto(plato, 1);
        assertFalse(restaurante.confirmarPedido(p1));

        PlatoFuerte externo = new PlatoFuerte("XX99", "Plato externo", "No esta en el menu", 20000, 30, 500, TipoPreparacion.ASADO, true);
        Pedido p2 = restaurante.registrarPedido(cliente, ModalidadEntrega.RESTAURANTE);
        p2.agregarProducto(externo, 1);
        assertFalse(restaurante.confirmarPedido(p2));
        assertEquals(EstadoPedido.PENDIENTE, p2.getEstado());
    }

    @Test
    @DisplayName("Estados: confirmado pasa a entregado y un cancelado no puede entregarse")
    void actualizaEstadosRespetandoLasReglas() {
        Pedido p1 = restaurante.registrarPedido(cliente, ModalidadEntrega.RESTAURANTE);
        p1.agregarProducto(plato, 1);
        assertTrue(restaurante.actualizarEstado(p1, EstadoPedido.CONFIRMADO));
        assertTrue(restaurante.actualizarEstado(p1, EstadoPedido.ENTREGADO));
        assertEquals(EstadoPedido.ENTREGADO, p1.getEstado());

        Pedido p2 = restaurante.registrarPedido(cliente, ModalidadEntrega.RESTAURANTE);
        p2.agregarProducto(bebida, 1);
        assertTrue(restaurante.actualizarEstado(p2, EstadoPedido.CANCELADO));
        assertFalse(restaurante.actualizarEstado(p2, EstadoPedido.ENTREGADO));
        assertEquals(EstadoPedido.CANCELADO, p2.getEstado());
    }

    @Test
    @DisplayName("El reporte excluye cancelados y pendientes y separa el valor de los domicilios")
    void reporteDeVentasExcluyeCanceladosYSeparaDomicilios() {
        // Pedido 1: a domicilio, confirmado -> 2 x 24.000 = 48.000 (+ 20.000 de domicilio)
        Pedido p1 = restaurante.registrarPedido(cliente, ModalidadEntrega.DOMICILIO);
        p1.agregarProducto(plato, 2);
        assertTrue(restaurante.solicitarDomicilio(p1, new RapidoExpress(), 10));
        assertTrue(restaurante.confirmarPedido(p1));

        // Pedido 2: confirmado y luego cancelado -> no cuenta
        Pedido p2 = restaurante.registrarPedido(cliente, ModalidadEntrega.RESTAURANTE);
        p2.agregarProducto(bebida, 1);
        restaurante.confirmarPedido(p2);
        restaurante.actualizarEstado(p2, EstadoPedido.CANCELADO);

        // Pedido 3: pendiente -> no cuenta
        Pedido p3 = restaurante.registrarPedido(cliente, ModalidadEntrega.RESTAURANTE);
        p3.agregarProducto(plato, 1);

        ReporteVentas reporte = restaurante.generarReporteVentas();
        assertEquals(48000.0, reporte.getIngresosRestaurante(), 0.001);
        assertEquals(20000.0, reporte.getIngresosDomicilios(), 0.001);
        assertEquals("PF01", reporte.getProductoMasVendido().getCodigo());
        assertEquals(2, reporte.getUnidadesProductoMasVendido());
        assertEquals("PlatoFuerte", reporte.getCategoriaMayorIngresos());
    }
}
