package com.clientes.gustavo;

public class PessoaJuridica extends Cliente {

	private String cnpj;

	private String nomeFantasia;

	public PessoaJuridica(String nome, String email, String endereco, String cnpj, String nomeFantasia) {
		super(nome, email, endereco, 5);
		this.cnpj = cnpj;
		this.nomeFantasia = nomeFantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	@Override
	public String toString() {
		String txt =  super.toString();
		return "----PessoaJuridica---- " + txt +
				"cnpj = " + cnpj + "\n" +
				"nomeFantasia = " + nomeFantasia + "\n";
	}
}
