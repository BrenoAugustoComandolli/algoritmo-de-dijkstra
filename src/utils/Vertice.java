package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Vertice {

	private String nome;
	private List<Map<String, Double>> conexoes = new ArrayList<>();

	public Vertice(String nome, List<Map<String, Double>> conexoes) {
		this.nome = nome;
		this.conexoes = conexoes;
	}

	public List<Map<String, Double>> getConexoes() {
		return conexoes;
	}

	public void setConexoes(List<Map<String, Double>> conexoes) {
		this.conexoes = conexoes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
		
}