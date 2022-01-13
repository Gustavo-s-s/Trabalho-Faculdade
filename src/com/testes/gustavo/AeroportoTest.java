package com.testes.gustavo;

import com.aeroportos.gustavo.Aeroporto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AeroportoTest {

    Aeroporto aeroporto;

    @BeforeEach
    void setUp(){
        aeroporto = new Aeroporto("POA","Aeroporto Internacional de Porto Alegre-Salgado Filho","Brasil",-29.9935,-51.1754);
    }


    @Test
    void getCodigoIATATest() {
        assertEquals(aeroporto.getCodigoIATA(), "POA");
    }

    @Test
    void getNomeTest() {
        assertEquals(aeroporto.getNome(), "Aeroporto Internacional de Porto Alegre-Salgado Filho");
    }

    @Test
    void getPaisTest() {
        assertEquals(aeroporto.getPais(), "Brasil");
    }

    @Test
    void getLatitudeTest() {
        assertEquals(aeroporto.getLatitude(), -29.9935);
    }

    @Test
    void getLongitudeTest() {
        assertEquals(aeroporto.getLongitude(), -51.1754);
    }

    @Test
    void testEqualsTest() {
        Aeroporto aero =  new Aeroporto("POA","Aeroporto Internacional de Porto Alegre-Salgado Filho","Brasil",-29.9935,-51.1754);
        assertEquals(aeroporto, aero);
    }

    @Test
    void testHashCodeTest() {
        Aeroporto aero =  new Aeroporto("POA","Aeroporto Internacional de Porto Alegre-Salgado Filho","Brasil",-29.9935,-51.1754);
        assertEquals(aeroporto.hashCode(), aero.hashCode());
    }
}