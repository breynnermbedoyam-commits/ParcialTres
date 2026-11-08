package co.uniquindio.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EntregaSeguraTest {

    private EntregaSegura empresa;

    @BeforeEach
    void setUp() {
        empresa = new EntregaSegura();
    }

    @Test
    @DisplayName("Costo sin recargo para 4 km: $7.000 + $1.000 por km")
    void costoSinRecargoPorDebajoDeCincoKm() {
        assertEquals(11000.0, empresa.calcularCosto(4), 0.001);
    }

    @Test
    @DisplayName("A exactamente 5 km no se aplica el recargo (debe superar 5 km)")
    void noAplicaRecargoEnExactamenteCincoKm() {
        assertEquals(12000.0, empresa.calcularCosto(5), 0.001);
    }

    @Test
    @DisplayName("Aplica recargo de $3.000 cuando la distancia supera 5 km")
    void aplicaRecargoSobreCincoKm() {
        assertEquals(18000.0, empresa.calcularCosto(8), 0.001);
    }

    @Test
    @DisplayName("Acepta recorridos de hasta 20 km y rechaza los mayores o iguales a cero")
    void aceptaHasta20Km() {
        assertTrue(empresa.aceptaDistancia(20));
        assertFalse(empresa.aceptaDistancia(20.1));
        assertFalse(empresa.aceptaDistancia(0));
    }

    @Test
    @DisplayName("aceptarServicio y nombre de la empresa")
    void aceptarServicioYNombre() {
        assertTrue(empresa.aceptarServicio(10));
        assertFalse(empresa.aceptarServicio(25));
        assertEquals("EntregaSegura", empresa.getNombre());
    }
}
