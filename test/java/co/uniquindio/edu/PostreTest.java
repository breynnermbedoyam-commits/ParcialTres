package co.uniquindio.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostreTest {

    private Postre postre;

    @BeforeEach
    void setUp() {
        postre = new Postre("PO01", "Flan", "Flan de caramelo", 8000, 5, 150, 20, false);
    }

    @Test
    @DisplayName("Precio de venta con incremento del 15 % cuando no requiere refrigeracion")
    void calculaPrecioVentaSinRefrigeracion() {
        assertEquals(9200.0, postre.calcularPrecioVenta(), 0.001);
    }

    @Test
    @DisplayName("Precio de venta con 15 % mas 5 % adicional si requiere refrigeracion")
    void calculaPrecioVentaConRefrigeracion() {
        postre.setRequiereRefrigeracion(true);
        assertEquals(9600.0, postre.calcularPrecioVenta(), 0.001);
    }

    @Test
    @DisplayName("Postre refrigerado cumple calidad entre 0 y 6 grados")
    void postreRefrigeradoValidaTemperatura() {
        postre.setRequiereRefrigeracion(true);
        postre.setTemperaturaAlmacenamiento(4);
        assertTrue(postre.cumpleCondicionesCalidad());
        postre.setTemperaturaAlmacenamiento(7);
        assertFalse(postre.cumpleCondicionesCalidad());
    }

    @Test
    @DisplayName("Postre sin refrigeracion cumple calidad entre 15 y 30 grados")
    void postreSinRefrigeracionValidaTemperatura() {
        assertTrue(postre.cumpleCondicionesCalidad());
        postre.setTemperaturaAlmacenamiento(10);
        assertFalse(postre.cumpleCondicionesCalidad());
        postre.setTemperaturaAlmacenamiento(31);
        assertFalse(postre.cumpleCondicionesCalidad());
    }

    @Test
    @DisplayName("Rechaza el postre si el peso esta fuera de 80-500 g")
    void rechazaPesoFueraDeRango() {
        postre.setPesoGramos(50);
        assertFalse(postre.cumpleCondicionesCalidad());
        postre.setPesoGramos(600);
        assertFalse(postre.cumpleCondicionesCalidad());
    }
}