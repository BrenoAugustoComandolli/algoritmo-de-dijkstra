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
	 * Recupera o vértice do nome apontado
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
	 * Realiza cálculo de caminho mais curto, retornando uma String com 
	 * a operação e os caminhos tomados
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
				if(pesoMaisCurto == null || (umaConexao.getValue() < pesoMaisCurto && !caminhoJaUsado(umaConexao.getKey(), caminhoSequencia))) {
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
	
	/**
	 * 
	 * Caminho de vértice já tomado na rota, até o vértice final 
	 *
	 * @param umaConexao
	 * @param caminhoSequencia
	 * @return contem no caminho
	 */
	private static boolean caminhoJaUsado(String umaConexao, String caminhoSequencia) {
		if(caminhoSequencia.contains(umaConexao)) {
			return true;
		}
		return false;
	}
	
}