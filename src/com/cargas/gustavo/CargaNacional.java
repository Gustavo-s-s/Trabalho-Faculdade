package com.cargas.gustavo;

import com.aeroportos.gustavo.Aeroporto;
import com.clientes.gustavo.Cliente;

public class CargaNacional extends Carga {

    private double taxaISQN;

    public CargaNacional(int codigo, double altura, double largura, double profundidade, double peso, Cliente cliente,
                         double taxa, Aeroporto origem, Aeroporto destino) {
        super(codigo, altura, largura, profundidade, peso, cliente, origem, destino);
        this.taxaISQN = taxa;
        calculaValorFrete();
    }

    public double getTaxaISQN() {
        return taxaISQN;
    }

    private void calculaValorFrete() {
         super.valorFrete =  super.calculaValorFrete(this.origem, this.destino) + taxaISQN;
    }

    @Override
    public String toString() {
        return "\n-----Carga Nacional-----\n" + super.toString() + "\ntaxaISQN: " + taxaISQN + "\ncarga nacinal";
    }
}
