package com.cargas.gustavo;

import com.aeroportos.gustavo.Aeroporto;
import com.clientes.gustavo.Cliente;

public class CargaInternacional extends Carga {

    private double taxaAlfandega;

    public CargaInternacional(int codigo, double altura, double largura, double profundidade, double peso, Cliente cliente,
                              double taxa, Aeroporto origem, Aeroporto destino) {
        super(codigo, altura, largura, profundidade, peso, cliente, origem, destino);
        this.taxaAlfandega = taxa;
        calculaValorFrete();
    }

    private void calculaValorFrete() {
        super.valorFrete =  super.calculaValorFrete(this.origem, this.destino) + taxaAlfandega;
    }

    public double getTaxaAlfandega() {
        return taxaAlfandega;
    }

    @Override
    public String toString() {
        return "\n-----Carga Internacional-----\n" + super.toString() + "\ntaxa de alfandega = " + taxaAlfandega + "\ncarga internacional" + "\nPais de origem = " + origem.getPais();
    }
}
