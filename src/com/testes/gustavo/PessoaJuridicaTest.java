package com.testes.gustavo;
import com.clientes.gustavo.Cliente;
import com.clientes.gustavo.PessoaJuridica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaJuridicaTest {

    Cliente cliente;

    @BeforeEach
    public void setUp() {
        cliente = new PessoaJuridica("Maria", "maria@gmial.com", "Rua Saquinho", "00.000.000/0001-00", "Maria Super");
    }

    @Test
    void getCnpj() {
        assertEquals( "00.000.000/0001-00" ,((PessoaJuridica)cliente).getCnpj() );
    }

    @Test
    void getNomeFantasia() {
        assertEquals("Maria Super", ((PessoaJuridica)cliente).getNomeFantasia());
    }

    @Test
    void testToString() {
        assertEquals(cliente.toString(), cliente.toString());
    }
}