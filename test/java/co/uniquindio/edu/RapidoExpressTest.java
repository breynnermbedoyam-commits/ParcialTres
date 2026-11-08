package co.uniquindio.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RapidoExpressTest {

    private RapidoExpress empresa;

    @BeforeEach
    void setUp() {
        empresa = new RapidoExpress();
    }

    @Test
    @DisplayName("Costo = $5.000 base + $1.500 por kilometro")
    void calculaCostoPorKilometro() {
        assertEquals(20000.0, empresa.calcularCosto(10), 0.001);
    }

    @Test
    @DisplayName("Acepta recorridos de hasta 12 km y rechaza los mayores")
    void aceptaHasta12Km() {
        assertTrue(empresa.aceptaDistancia(12));
        assertFalse(empresa.aceptaDistancia(12.1));
    }

    @Test
    @DisplayName("Rechaza distancias iguales a cero o negativas")
    void rechazaDistanciasNoPositivas() {
        assertFalse(empresa.aceptaDistancia(0));
        assertFalse(empresa.aceptaDistancia(-3));
    }

    @Test
    @DisplayName("aceptarServicio depende de la distancia permitida")
    void aceptarServicioSegunDistancia() {
        assertTrue(empresa.aceptarServicio(5));
        assertFalse(empresa.aceptarServicio(15));
    }

    @Test
    @DisplayName("Tiene el nombre y las tarifas por defecto del enunciado")
    void tieneValoresPorDefecto() {
        assertEquals("RapidoExpress", empresa.getNombre());
        assertEquals(5000.0, empresa.getTarifaBase(), 0.001);
        assertEquals(1500.0, empresa.getTarifaPorKm(), 0.001);
        assertEquals(12.0, empresa.getMaxKm(), 0.001);
    }
}