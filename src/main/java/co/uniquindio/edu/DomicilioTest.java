package co.uniquindio.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomicilioTest {

    @Test
    @DisplayName("Solicitar servicio aceptado guarda el costo calculado")
    void solicitudAceptadaGuardaCosto() {
        Domicilio d = new Domicilio(new RapidoExpress(), 10);
        assertTrue(d.solicitarServicio());
        assertTrue(d.getAceptado());
        assertEquals(20000.0, d.getCosto(), 0.001);
    }

    @Test
    @DisplayName("Solicitar servicio con distancia no permitida es rechazado y no cobra")
    void solicitudRechazadaNoCobra() {
        Domicilio d = new Domicilio(new RapidoExpress(), 15);
        assertFalse(d.solicitarServicio());
        assertFalse(d.getAceptado());
        assertEquals(0.0, d.getCosto(), 0.001);
    }

    @Test
    @DisplayName("calcularCosto delega en la empresa (EntregaSegura con recargo a 6 km)")
    void calcularCostoDelegaEnLaEmpresa() {
        Domicilio d = new Domicilio(new EntregaSegura(), 6);
        assertEquals(16000.0, d.calcularCosto(), 0.001);
    }

    @Test
    @DisplayName("Un domicilio nuevo empieza sin aceptar y con costo cero")
    void estadoInicial() {
        Domicilio d = new Domicilio(new EntregaSegura(), 3);
        assertFalse(d.getAceptado());
        assertEquals(0.0, d.getCosto(), 0.001);
        assertEquals(3.0, d.getDistanciaKm(), 0.001);
    }

    @Test
    @DisplayName("Sin empresa asignada el servicio no puede aceptarse")
    void sinEmpresaNoSeAcepta() {
        Domicilio d = new Domicilio(null, 5);
        assertFalse(d.solicitarServicio());
        assertEquals(0.0, d.getCosto(), 0.001);
    }
}
