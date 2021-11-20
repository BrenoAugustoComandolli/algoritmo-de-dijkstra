package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LogicaDijkstraUtil {

	private static List<Vertice> vertices = new ArrayList<>();

	public static List<Vertice> getVertices() {
		return vertices;
	}

	public static void setVertices(List<Vertice> vertices) {
		LogicaDijkstraUtil.vertices = vertices;
	}
	
	/**
	 * 
	 * Recupera o v�rtice do nome apontado
	 *
	 * @param sNomeConexao
	 * @return vertice
	 */
	public static Vertice recuperarVertice(String sNomeConexao) {
		for (Vertice lVertice : LogicaDijkstraUtil.getVertices()) {
			if (lVertice.getNome().equals(sNomeConexao)) {
				return lVertice;
			}
		}
		return null;
	}

	/**
	 * 
	 * Realiza c�lculo de caminho mais curto, retornando uma String com 
	 * a opera��o e os caminhos tomados
	 *
	 * @param verticeOrigem
	 * @param verticeDestino
	 * @return
	 */
	public static String recuperarCaminhoMenor(Vertice verticeOrigem, Vertice verticeDestino) {
		Vertice verticeMaisCurto = verticeOrigem;
		Double pesoMaisCurto = null;
		String caminhoSequencia = "[";
		String caminhoCalculo = "(";
		Double caminhoSoma = 0.0;
		String caminhoResultado = "";
		
		do {
			for (Map.Entry<String, Double> umaConexao : verticeMaisCurto.getConexoes().entrySet()) {
				if(pesoMaisCurto == null || umaConexao.getValue() < pesoMaisCurto) {
					verticeMaisCurto = recuperarVertice(umaConexao.getKey());
					pesoMaisCurto = umaConexao.getValue();
				}
			}
			caminhoSequencia += verticeMaisCurto.getNome();
			caminhoCalculo += pesoMaisCurto;
			if(!verticeMaisCurto.getNome().equals(verticeOrigem.getNome())) {
				caminhoSequencia += ",";
				caminhoCalculo += "+";
			}
			caminhoSoma+=pesoMaisCurto;
			
		}while(verticeMaisCurto.getNome().equals(verticeOrigem.getNome()));
		
		caminhoSequencia += "]";
		caminhoCalculo += ")";
		return caminhoResultado+=caminhoSequencia+caminhoCalculo+" = "+caminhoSoma; 
	}
	
}