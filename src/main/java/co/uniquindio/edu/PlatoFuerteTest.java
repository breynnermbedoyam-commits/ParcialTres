package co.uniquindio.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlatoFuerteTest {

    private PlatoFuerte plato;

    @BeforeEach
    void setUp() {
        plato = new PlatoFuerte("PF01", "Churrasco", "Carne a la plancha", 20000, 30, 500, TipoPreparacion.PLANCHA, true);
    }

    @Test
    @DisplayName("El precio de venta tiene un incremento del 20 % sobre el precio base")
    void calculaPrecioVentaConIncrementoDel20() {
        assertEquals(24000.0, plato.calcularPrecioVenta(), 0.001);
    }

    @Test
    @DisplayName("Un plato dentro de todos los rangos cumple la calidad (incluye valores limite)")
    void cumpleCalidadConValoresValidosYLimites() {
        assertTrue(plato.cumpleCondicionesCalidad());
        plato.setPesoGramos(200);
        plato.setTiempoPreparacion(10);
        assertTrue(plato.cumpleCondicionesCalidad());
        plato.setPesoGramos(900);
        plato.setTiempoPreparacion(60);
        assertTrue(plato.cumpleCondicionesCalidad());
    }

    @Test
    @DisplayName("Rechaza el plato si el peso esta fuera de 200-900 g")
    void rechazaPesoFueraDeRango() {
        plato.setPesoGramos(199);
        assertFalse(plato.cumpleCondicionesCalidad());
        plato.setPesoGramos(901);
        assertFalse(plato.cumpleCondicionesCalidad());
    }

    @Test
    @DisplayName("Rechaza el plato si el tiempo de preparacion esta fuera de 10-60 min")
    void rechazaTiempoFueraDeRango() {
        plato.setTiempoPreparacion(9);
        assertFalse(plato.cumpleCondicionesCalidad());
        plato.setTiempoPreparacion(61);
        assertFalse(plato.cumpleCondicionesCalidad());
    }

    @Test
    @DisplayName("Rechaza el plato si la coccion no fue aprobada")
    void rechazaCoccionNoAprobada() {
        plato.setCoccionAprobada(false);
        assertFalse(plato.cumpleCondicionesCalidad());
    }
}
