package com.testes.gustavo;

import com.clientes.gustavo.Cliente;
import com.clientes.gustavo.PessoaFisica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PessoaFisicaTest {

    Cliente cliente;

    @BeforeEach
    public void setUp(){
        cliente = new PessoaFisica("Marcelo", "marcelo@gmail.com", "Rua Aparecida", "999888777-98");
    }

    @Test
    void getCpf() {
        assertEquals("999888777-98", ((PessoaFisica)cliente).getCpf());
    }

    @Test
    void testToString() {
        assertEquals(cliente.toString(), cliente.toString());
    }
}