package com.cargas.gustavo;

import com.aeroportos.gustavo.Aeroporto;
import com.clientes.gustavo.Cliente;
import com.clientes.gustavo.PessoaFisica;
import com.clientes.gustavo.PessoaJuridica;

import java.io.Serializable;
import java.util.Objects;

public abstract class Carga implements Serializable {

	private static final long serialVersionUID = 2L;
	protected int codigo;
	protected double altura;
	protected double largura;
	protected double profundidade;
	protected double peso;
	protected double valorFrete;
	protected String situacao;
	protected Cliente cliente;
	protected Aeroporto origem;
	protected Aeroporto destino;

	public Carga(int codigo, double altura, double largura, double profundidade, double peso, Cliente cliente,
				 Aeroporto origem, Aeroporto destino) {
		this.codigo = codigo;
		this.altura = altura;
		this.largura = largura;
		this.profundidade = profundidade;
		this.peso = peso;
		this.cliente = cliente;
		this.origem = origem;
		setDestino(destino);
		this.valorFrete = calculaValorFrete(origem, destino);
		setSituacao("Pendente");
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public double calculaValorBase() {
		return altura * largura * profundidade * peso * 10;
	}


	public double calculaDistanciaKm() {
		if(origem != null && destino != null) {
			double primeiratLatitude = origem.getLatitude();
			double primeiraLongitude = origem.getLongitude();
			double segundaLatitude = destino.getLatitude();
			double segundaLongitude = destino.getLongitude();

			// Conversão de graus pra radianos das latitudes
			double primeiraLat = Math.toRadians(primeiratLatitude);
			double segundaLat = Math.toRadians(segundaLatitude);

			// Diferença das longitudes
			double deltaLongitudeInRad = Math.toRadians(segundaLongitude
					- primeiraLongitude);

			// Cálcula da distância entre os pontos
			return (Math.acos(Math.cos(primeiraLat) * Math.cos(segundaLat)
					* Math.cos(deltaLongitudeInRad) + Math.sin(primeiraLat)
					* Math.sin(segundaLat))
					* 6371) / 100;
		}
		return 0;
	}

	public double calculaValorFrete(Aeroporto origem, Aeroporto destino) {
		if (cliente instanceof PessoaJuridica) {
			return (calculaValorBase() * calculaDistanciaKm()) * 0.3;
		}
		return (calculaValorBase() * calculaDistanciaKm());
	}

	public boolean verificaSituacaoCargaParaCancelar() {
		if (getSituacao().equals("Pendente") || getSituacao().equals("em transporte")) {
			setSituacao("cancelada");
			return true;
		} else {
			return false;
		}
	}

	public boolean verificaSituacaoCargaParaTrasportar() {
		if (getSituacao().equals("Pendente")) {
			setSituacao("em transporte");
			return true;
		} else {
			return false;
		}
	}

	public boolean verificaSituacaoCargaParaEntrega() {
		if (getSituacao().equals("em transporte")) {
			setSituacao("entregue");
			return true;
		} else {
			return false;
		}
	}

	public void setSituacao(String novaSituacao) {
		this.situacao = novaSituacao;
	}

	public int getCodigo() {
		return codigo;
	}


	public String getSituacao() {
		return situacao;
	}

	public void setDestino(Aeroporto destino) {
		if(destino != null){
			if (!destino.getPais().equalsIgnoreCase("Brasil")) {
				throw new NullPointerException("Seu aeroporto de destino não pode ser Internacional");
			}
		}
		this.destino = destino;
	}

	public Aeroporto getOrigem() {
		return origem;
	}

	public Aeroporto getDestino() {
		return destino;
	}

	@Override
	public String toString() {
		String clientes = (cliente instanceof PessoaFisica) ? "cpf: " + ((PessoaFisica)cliente).getCpf() : "cnpj: " + ((PessoaJuridica)cliente).getCnpj();
		String texto = String.format("codigo: %d %naltura: %.2f %nlargura: %.2f %nprofundidade: %.2f %npeso: %.2f %n" +
						"valor do frete: %.2f %nsituação: %s %norigem: %s %ndestino: %s %ncliente: %s %n%s", getCodigo(), this.altura,
				this.largura, this.profundidade, this.peso, this.valorFrete, getSituacao(), origem.getNome(),
				this.destino.getNome(), this.cliente.getNome(), clientes);

		return texto;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Carga carga = (Carga) o;
		return codigo == carga.codigo && Double.compare(carga.altura, altura) == 0 && Double.compare(carga.largura, largura) == 0
				&& Double.compare(carga.profundidade, profundidade) == 0 && Double.compare(carga.peso, peso) == 0
				&& Double.compare(carga.valorFrete, valorFrete) == 0 && situacao.equals(carga.situacao) && cliente.equals(carga.cliente)
				&& origem.equals(carga.origem) && destino.equals(carga.destino);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, altura, largura, profundidade, peso, valorFrete, situacao, cliente, origem, destino);
	}
}
