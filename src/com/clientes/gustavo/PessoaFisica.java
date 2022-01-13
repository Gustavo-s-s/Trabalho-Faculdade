package com.clientes.gustavo;

public class PessoaFisica extends Cliente {

	private String cpf;

	public PessoaFisica(String nome, String email, String endereco, String cpf) {
		super(nome, email, endereco, 5);
		this.cpf = cpf;
	}

	public String getCpf() {
		return cpf;
	}

	@Override
	public String toString() {
		String txt = super.toString();

		return "----Pessoa-Fisica----" + txt + "cpf = " + cpf + "\n";
	}

}
