package com.testes.gustavo;

import com.aeroportos.gustavo.Aeroporto;
import com.cargas.gustavo.Carga;
import com.cargas.gustavo.CargaInternacional;
import com.clientes.gustavo.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CargaInternacionalTest {

    Cliente cliente;
    Aeroporto origem, destino;
    Carga carga;

    @BeforeEach
    public void setUp() {
        cliente = new Cliente("Maria", "maria@gmial.com", "Rua Saquinho", 5);
        origem = new Aeroporto("add","Salado Filho","Brasil", 56.2334, 75.4654);
        destino = new Aeroporto("asd","Guarulhos","Brasil", 56.4564, 65.4654);
        carga = new CargaInternacional(13, 3, 3, 3, 3, cliente, 1000, origem, destino );
    }

    @Test
    void getTaxaAlfandega() {
        assertEquals(1000, ((CargaInternacional) carga).getTaxaAlfandega());
    }

    @Test
    void testToString() {
        assertEquals(carga.toString(), carga.toString());
    }
}