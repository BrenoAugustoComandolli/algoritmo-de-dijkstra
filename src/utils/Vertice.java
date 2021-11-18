package utils;

import java.util.ArrayList;
import java.util.List;

public class Vertice {

	private String nome;
	private List<String> conexoes = new ArrayList<>();

	public Vertice(String nome, List<String> conexoes) {
		this.nome = nome;
		this.conexoes = conexoes;
	}

	public List<String> getConexoes() {
		return conexoes;
	}

	public void setConexoes(List<String> conexoes) {
		this.conexoes = conexoes;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
		
	
}