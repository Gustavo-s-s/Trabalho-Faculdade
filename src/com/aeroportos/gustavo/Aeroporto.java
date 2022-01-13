package com.aeroportos.gustavo;

import java.io.Serializable;
import java.util.Objects;

public class Aeroporto implements Serializable {

	private static final long serialVersionUID = 2L;
	
	private String codigoIATA;
	private String nome;
	private String pais;
	private double latitude;
	private double longitude;

	public Aeroporto(String codigoIATA, String nome, String pais, double latitude, double longitude) {
		this.codigoIATA = codigoIATA;
		this.nome = nome;
		this.pais = pais;
		this.latitude = latitude;
		this.longitude = longitude;
	}


	public String getCodigoIATA() {
		return codigoIATA;
	}

	public String getNome() {
		return nome;
	}

	public String getPais() {
		return pais;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {

		String texto = String.format("Nome: %s - Codigo IATA: %s - país: %s - Latitude: %f - Longitude: %f",
				getNome(), getCodigoIATA(), getPais(), getLatitude(), getLongitude());
		return   texto;
	}

	public String toStringPesquisaTrace(){
		String texto = String.format(" ----Aeroporto-----%nNome: %s%nCodigo IATA: %s%nPaís: %s%nLatitude: %f%nLongitude: %f%n",
				getNome(), getCodigoIATA(), getPais(), getLatitude(), getLongitude());
		return   texto;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Aeroporto aeroporto = (Aeroporto) o;
		return Double.compare(aeroporto.latitude, latitude) == 0 && Double.compare(aeroporto.longitude, longitude) == 0
				&& codigoIATA.equals(aeroporto.codigoIATA) && nome.equals(aeroporto.nome) && pais.equals(aeroporto.pais);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigoIATA, nome, pais, latitude, longitude);
	}
}
