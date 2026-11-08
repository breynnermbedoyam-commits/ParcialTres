package co.uniquindio.edu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BebidaTest {

    private Bebida bebida;

    @BeforeEach
    void setUp() {
        bebida = new Bebida("BE01", "Jugo de mora", "Jugo natural", 5000, 10, 300, 5, true, false);
    }

    @Test
    @DisplayName("Precio de venta con incremento del 10 % sin preparacion especial")
    void calculaPrecioVentaSinPreparacionEspecial() {
        assertEquals(5500.0, bebida.calcularPrecioVenta(), 0.001);
    }

    @Test
    @DisplayName("Precio de venta con 10 % mas recargo fijo de $3.000 por preparacion especial")
    void calculaPrecioVentaConPreparacionEspecial() {
        bebida.setRequierePreparacionEspecial(true);
        assertEquals(8500.0, bebida.calcularPrecioVenta(), 0.001);
    }

    @Test
    @DisplayName("Bebida fria cumple calidad entre 0 y 10 grados")
    void bebidaFriaValidaTemperatura() {
        assertTrue(bebida.cumpleCondicionesCalidad());
        bebida.setTemperaturaServicio(11);
        assertFalse(bebida.cumpleCondicionesCalidad());
    }

    @Test
    @DisplayName("Bebida caliente cumple calidad entre 55 y 85 grados")
    void bebidaCalienteValidaTemperatura() {
        bebida.setEsFria(false);
        bebida.setTemperaturaServicio(70);
        assertTrue(bebida.cumpleCondicionesCalidad());
        bebida.setTemperaturaServicio(50);
        assertFalse(bebida.cumpleCondicionesCalidad());
    }

    @Test
    @DisplayName("Rechaza la bebida si el volumen esta fuera de 150-1500 ml")
    void rechazaVolumenFueraDeRango() {
        bebida.setVolumenMl(100);
        assertFalse(bebida.cumpleCondicionesCalidad());
        bebida.setVolumenMl(1600);
        assertFalse(bebida.cumpleCondicionesCalidad());
    }
}