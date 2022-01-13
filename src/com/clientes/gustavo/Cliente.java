package com.clientes.gustavo;

import com.cargas.gustavo.Carga;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Cliente implements Serializable {

	private static final long serialVersionUID = 2L;
	protected String nome;
	protected String email;
	protected String endereco;
	private Set<Carga> cargas = new HashSet<>();
	private int qtdCargasLimite;
	private int posIndex;

	public Cliente(String nome, String email, String endereco, int quantidade) {
		this.nome = nome;
		this.email = email;
		this.endereco = endereco;
		this.posIndex = 0;
		this.qtdCargasLimite = quantidade;

	}

	public String getNome() {
		return nome;
	}


	@Override
	public String toString() {


		return "\n" + "nome = " + nome + "\nemail = " + email + "\nendereco = " + endereco + "\n";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Cliente cliente = (Cliente) o;
		return nome.equals(cliente.nome) && email.equals(cliente.email) && endereco.equals(cliente.endereco);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, email, endereco);
	}
}
