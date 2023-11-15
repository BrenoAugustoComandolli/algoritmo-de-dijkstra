package utils;

import java.util.ArrayList;
import java.util.List;

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
		Integer tamanhoListaVertices = LogicaDijkstraUtil.getVertices().size();
		String[][] valoresCaminho = new String[tamanhoListaVertices+1][tamanhoListaVertices+1];
		String[][] chavesCaminho = new String[tamanhoListaVertices+1][tamanhoListaVertices+1];
		String caminho = "";
		adicionarLegendas(valoresCaminho, chavesCaminho);
		aplicarValoresIniciais(valoresCaminho, chavesCaminho, verticeOrigem);
		recuperarCaminhoMaisCurto(verticeOrigem, verticeDestino, valoresCaminho, chavesCaminho);
		mostrarResultadoCalculo(valoresCaminho, chavesCaminho);
		caminho = recuperarCaminhoPercorrido(valoresCaminho, chavesCaminho, verticeOrigem, verticeDestino, caminho);
		return caminho;
	}
	
	private static String recuperarCaminhoPercorrido(String[][] valoresCaminho, String[][] chavesCaminho, 
			                                       Vertice verticeOrigem, Vertice verticeDestino, String caminho) {
		Double somaPeso = 0.0;
		Integer menorColuna = null;
		StringBuilder caminhosUsados = new StringBuilder();
		StringBuilder pesosUsados = new StringBuilder();
		Double antigoMenorValor = 0.0;
		
		caminhosUsados.append("[");
		pesosUsados.append("[");
		
		Vertice verticeAtual = verticeDestino;
		
		while(!verticeAtual.getNome().equals(verticeOrigem.getNome())) {
			for (int linha = 1; linha < chavesCaminho.length; linha++) {
				if(chavesCaminho[linha][0].equals(verticeAtual.getNome())) {
					Double menorValor = null;
					for (int coluna = 1; coluna < valoresCaminho.length; coluna++) {
						String pesoStr = valoresCaminho[linha][coluna];
						if(!pesoStr.equals("X")) {
							if(menorValor == null || Double.parseDouble(pesoStr) < menorValor) {
								menorValor = Double.parseDouble(pesoStr);
								menorColuna = coluna;
							}
						}
					}
					if(verticeAtual.getNome().equals(verticeDestino.getNome())) {
						somaPeso = menorValor;
					}else {
						if(verticeAtual.getNome().equals(verticeOrigem.getNome())) {
							pesosUsados.insert(1, (antigoMenorValor-menorValor));
						}else {
							pesosUsados.insert(1, ","+(antigoMenorValor-menorValor));
						}
					}
					String verticeCaminho = chavesCaminho[linha][menorColuna];
					caminhosUsados.insert(1, verticeCaminho);
					if(!verticeCaminho.equals(verticeOrigem.getNome())) {
						if(!verticeAtual.getNome().equals(verticeDestino.getNome())){
							caminhosUsados.insert(2,",");
						}
					}
					verticeAtual = recuperarVertice(verticeCaminho);
					if(verticeAtual.getNome().equals(verticeOrigem.getNome())) {
						caminhosUsados.insert(2,",");
					}
					antigoMenorValor = menorValor;
				}
			}
		}
		
		if(antigoMenorValor != 0.0) {
			pesosUsados.insert(1, antigoMenorValor);
		}
	
		caminhosUsados.append(","+verticeDestino.getNome());
		caminhosUsados.append("]");
		pesosUsados.append("]");
		return caminhosUsados.toString()+" "+pesosUsados.toString()+" = "+somaPeso;
	}
	
	/**
	 * 
	 * Aplicar valores inciais para espa�os vazios
	 *
	 * @param valoresCaminho
	 * @param chavesCaminho
	 * @param verticeOrigem
	 */
	private static void aplicarValoresIniciais(String[][] valoresCaminho, String[][] chavesCaminho, Vertice verticeOrigem) {
		for (int linha = 0; linha < chavesCaminho.length; linha++) {
			if(verticeOrigem.getNome().equals(chavesCaminho[linha][0])) {
				for (int coluna = 1; coluna < chavesCaminho.length; coluna++) {
					if(linha == 1 && coluna == 1) {
						valoresCaminho[linha][coluna] = "0.0";
						chavesCaminho[linha][coluna] = verticeOrigem.getNome();
					}else {
						valoresCaminho[linha][coluna] = "X";
						chavesCaminho[linha][coluna] = "X";   
					}
				}
			}else {
				if(linha > 1) {
					for (int coluna = 1; coluna < chavesCaminho.length; coluna++) {
						valoresCaminho[linha][coluna] = "X";
						chavesCaminho[linha][coluna] = "X";   
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * Realiza o passo a passo de c�lculos utilizados
	 * at� preencher matrizes
	 *
	 * @param verticeOrigem
	 * @param verticeDestino
	 * @param valoresCaminho
	 * @param chavesCaminho
	 */
	private static void recuperarCaminhoMaisCurto(Vertice verticeOrigem, Vertice verticeDestino, 
			                                      String[][] valoresCaminho, String[][] chavesCaminho) {
		List<String> ignorados = new ArrayList<>();
		Vertice verticeAtual = verticeOrigem;
		for(int numPasso = 1; numPasso < chavesCaminho.length; numPasso++){
			 verticeAtual = calcularPesosVerticeAtual(verticeAtual, numPasso, valoresCaminho, chavesCaminho, ignorados, verticeOrigem);
		     if(verticeAtual == null) {
		    	 break;
		     }
		}
	}
	
	/**
	 * 
	 * <Descreva o metodo de forma breve e objetiva>
	 *
	 * @param verticeAtual
	 * @param numPasso
	 * @param valoresCaminho
	 * @param chavesCaminho
	 * @return
	 */
	private static Vertice calcularPesosVerticeAtual(Vertice verticeAtual, Integer numPasso, String[][] valoresCaminho, 
			                                         String[][] chavesCaminho, List<String> ignorados, Vertice verticeOrigem){
		for(int linha = 1; linha < chavesCaminho.length; linha++){
			String conexao = chavesCaminho[linha][0];
			if(verticeAtual.getConexoes().get(conexao) != null && !conexao.equals(verticeOrigem.getNome())) {
				Double pesoAtual = recuperarPesoDistancia(verticeAtual, conexao, linha, valoresCaminho, chavesCaminho);
				Double proximoPeso = recuperarProximoPeso(pesoAtual, linha, valoresCaminho, chavesCaminho);
				valoresCaminho[linha][numPasso] = String.valueOf(proximoPeso);
				chavesCaminho[linha][numPasso] = verticeAtual.getNome();
			}
		}
		
		if(verticeAtual.getNome() != null) {
			ignorados.add(verticeAtual.getNome());
		}
		
		Vertice proximoVertice = null;
		String proximoVerticeStr = buscarProximaLinhaCalculo(numPasso, valoresCaminho, chavesCaminho, ignorados);
		
		if(proximoVerticeStr != "X") {
			proximoVertice = recuperarVertice(proximoVerticeStr);
		}
		return proximoVertice;
	}
	
	/**
	 * 
	 * Busca o v�rtice para pr�xima s�rie de c�lculos 
	 *
	 * @param numPasso
	 * @param valoresCaminho
	 * @param chavesCaminho
	 * @return menorChave
	 */
	private static String buscarProximaLinhaCalculo(Integer numPasso, String[][] valoresCaminho,
			                                        String[][] chavesCaminho, List<String> ignorados) {
		Double menorCaminho = null;
		String menorChave = "";
		for(int linha = 1; linha < valoresCaminho.length; linha++){
			String pesoStr = valoresCaminho[linha][numPasso];
			String chaveStr = chavesCaminho[linha][0];
			if(!pesoStr.equals("X") && !isIgnorado(chaveStr, ignorados) && 
			   (menorCaminho == null || Double.parseDouble(pesoStr) < menorCaminho)) {
				menorCaminho = Double.parseDouble(pesoStr);
				menorChave = chavesCaminho[linha][0];
			}
		}
		if(menorChave.equals("")) {
			menorChave = "X";
		}
		return menorChave;
	}
	
	/**
	 * 
	 * Verificar se o item j� foi feito o c�lculo
	 *
	 * @param chaveStr
	 * @param ignorados
	 * @return isIgnorado
	 */
	private static boolean isIgnorado(String chaveStr, List<String> ignorados) {
		for (String umIgnorado : ignorados) {
			if(umIgnorado.equals(chaveStr)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * Recuperar dist�ncia at� a conex�o, 
	 * somando os caminhos anteriores
	 *
	 * @param verticeAtual
	 * @param conexao
	 * @param linha
	 * @param valoresCaminho
	 * @param chavesCaminho
	 * @return distancia
	 */
	private static Double recuperarPesoDistancia(Vertice verticeAtual, String conexao, Integer linha,
											     String[][] valoresCaminho, String[][] chavesCaminho) {
		Double pesoSemPassosAnteriores = verticeAtual.getConexoes().get(conexao);
		return pesoSemPassosAnteriores + recuperaPesoMenorAteConexaoAtual(valoresCaminho, chavesCaminho, verticeAtual);
	}
	
	/**
	 * 
	 * Recupera o menor peso at� o v�rtice, comparando 
	 * o peso atual, aos pesos j� cadastrados
	 *
	 * @param pesoAtual
	 * @param linha
	 * @param valoresCaminho
	 * @param chavesCaminho
	 * @return menorPeso
	 */
	private static Double recuperarProximoPeso(Double pesoAtual, Integer linha, String[][] valoresCaminho, String[][] chavesCaminho) {
		Double menorPeso = pesoAtual;
		for (int coluna = 1; coluna < chavesCaminho.length; coluna++) {
			String pesoComparacao = valoresCaminho[linha][coluna];
			if(!pesoComparacao.equals("X")) {
				if(Double.parseDouble(pesoComparacao) < menorPeso) {
					menorPeso = Double.parseDouble(pesoComparacao);
				}
			}
		}
		return menorPeso;
	}
	
	/**
	 * 
	 * Recuperar peso menor at� a conex�o atual
	 *
	 * @param valoresCaminho
	 * @param chavesCaminho
	 * @param linha
	 * @param verticeOrigem
	 * @return somaCaminhos
	 */
	private static Double recuperaPesoMenorAteConexaoAtual(String[][] valoresCaminho, String[][] chavesCaminho, 
			                                               Vertice verticeAtual) {
		Double menorCaminho = null;
		for (int linha = 1; linha < chavesCaminho.length; linha++) {
			if(chavesCaminho[linha][0].equals(verticeAtual.getNome())) {
				for (int coluna = 1; coluna < chavesCaminho.length; coluna++) {
					String pesoComparacao = valoresCaminho[linha][coluna];
					if(!pesoComparacao.equals("X")) {
						if(menorCaminho == null || Double.parseDouble(pesoComparacao) < menorCaminho) {
							menorCaminho = Double.parseDouble(valoresCaminho[linha][coluna]);
						}
					}
				}
			}
		}
		return menorCaminho;
	}

	/**
	 * 
	 * Adiciona legendas na tabela. Ex. A - B - C
	 *
	 * @param lCaminhos
	 */
	private static void adicionarLegendas(String[][] valoresCaminho, String[][] chavesCaminho) {
		List<Vertice> lVertices = LogicaDijkstraUtil.getVertices();
		
		chavesCaminho[0][0] = "";
		valoresCaminho[0][0] = "LEG";
		
		for (int linha = 1; linha < chavesCaminho.length; linha++) {
			chavesCaminho[linha][0] = lVertices.get(linha-1).getNome();
			valoresCaminho[linha][0] = "";
		}
		
		for (int coluna = 1; coluna < chavesCaminho.length; coluna++) {
			chavesCaminho[0][coluna] = "PES"+coluna;
			valoresCaminho[0][coluna] = "";
		}
	}
	
	/**
	 * 
	 * Monta a linha de tamanho da tabela
	 *
	 * @return linha
	 */
	public static String recuperarLinhaTabela() {
		Integer qtdVertices = LogicaDijkstraUtil.getVertices().size();
		Integer count = 0;
		String linha = "";
		while(count <= qtdVertices) {
			linha+="-----------";
			count++;
		}
		return linha;
	}
	
	/**
	 * 
	 * Exibi��o de resultados do c�lculo
	 *
	 * @param valoresCaminho
	 * @param chavesCaminho
	 */
	private static void mostrarResultadoCalculo(String[][] valoresCaminho, String[][] chavesCaminho) {
		String linhaTabela = recuperarLinhaTabela();
		System.out.println(linhaTabela);
		System.out.println("Resolu��o do algoritmo:  ");
		System.out.println(linhaTabela);
		
		for (int linha = 0; linha < chavesCaminho.length; linha++) {
			if(linha > 0) {
				System.out.println("");
			}
			System.out.println(linhaTabela);
			for (int coluna = 0; coluna < valoresCaminho.length; coluna++) {
				
				if(linha == 0 && coluna == 0){
					System.out.print("| ("+valoresCaminho[linha][coluna]+") |");
				}else if(coluna == 0) {
					System.out.print("| ("+chavesCaminho[linha][coluna]+") |");
			    }else if(linha != 0) {
					System.out.print("| ("+valoresCaminho[linha][coluna]+", "+chavesCaminho[linha][coluna]+") |");
				}else {
					System.out.print("| ("+chavesCaminho[linha][coluna]+") |");
				}
			}
		}
		System.out.println("");
		System.out.println(linhaTabela);
	}
	
}