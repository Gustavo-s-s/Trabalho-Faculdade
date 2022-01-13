package com.testes.gustavo;

import com.aeroportos.gustavo.Aeroporto;
import com.cargas.gustavo.Carga;
import com.clientes.gustavo.Cliente;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CargaTest {
    Aeroporto aero2 = new Aeroporto("add","Salado Filho","Brasil", 56.2334, 75.4654);
    Aeroporto aero = new Aeroporto("asd","Guarulhos","Brasil", 56.4564, 65.4654);
    Cliente c = new Cliente("Maria", "maria@gmial.com", "Rua Saquinho", 5);
    private Carga cc;

    @BeforeEach
    void setUp() {
        cc = new Carga(12, 22, 22, 22, 22, c, aero, aero2 ) {
        };
    }

    @Test
    void calculaValorBase() {
        cc.calculaValorBase();
        Assertions.assertEquals(cc.calculaValorBase(), cc.calculaValorBase());
    }

    @Test
    void calculaDistanciaKm() {
        Assertions.assertEquals(6.161875313522891,cc.calculaDistanciaKm());
    }

    @Test
    void calculaValorFrete() {
        Assertions.assertEquals(1.4434562634446185E7, cc.calculaValorFrete(aero,aero2));
    }

    @Test
    void verificaSituacaoCargaParaCancelar() {
        Assertions.assertEquals(true, cc.verificaSituacaoCargaParaCancelar());
    }

    @Test
    void verificaSituacaoCargaParaTrasportar() {
        Assertions.assertEquals(true, cc.verificaSituacaoCargaParaTrasportar());
        Assertions.assertEquals("em transporte", cc.getSituacao());
    }

    @Test
    void verificaSituacaoCargaParaEntrega() {
        Assertions.assertEquals(false, cc.verificaSituacaoCargaParaEntrega());
        Assertions.assertEquals("Pendente", cc.getSituacao());
        cc.verificaSituacaoCargaParaTrasportar();

        Assertions.assertEquals(true, cc.verificaSituacaoCargaParaEntrega());
        Assertions.assertEquals("entregue", cc.getSituacao());

    }

    @Test
    void testToString() {
        cc.toString();
        Assertions.assertEquals(cc.toString(), cc.toString());

    }
}