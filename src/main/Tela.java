package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

import utils.LogicaDijkstraUtil;
import utils.Vertice;


public class Tela {
	
	public static void main(String[] args) {
		carregarMenuOpcoes();
	}
	
	/**
	 * 
	 * Carregar menu de opções, 
	 * definindo suas operações 
	 *
	 */
	private static void carregarMenuOpcoes() {
		var teclado = new Scanner(System.in);
		int opcao;
		do {
			exibirMenuPrincipal();
			opcao = teclado.nextInt();
			switch (opcao) {
			case 1: cadastrarVertice(teclado); break;
			case 2: calcularDistanciaMenor(teclado); break;
			case 3: cadastraConexoesVerticeExistente(teclado); break;
			case 4: excluiVertices(teclado); break;
			case 5: break;
			default:
				System.out.println("Opção digitada inválida!");
				break;
			}
		} while (opcao != 5);
		teclado.close();
	}
	
	/**
	 * 
	 * Exibição de menu
	 *
	 */
	private static void exibirMenuPrincipal() {
		System.out.println("----------Menu--------------");
		System.out.println("1 - Cadastrar vértices      ");
		System.out.println("2 - Calcular distância menor");
		System.out.println("3 - Cadastrar conexões      ");
		System.out.println("4 - Excluir conexão         ");
		System.out.println("5 - Sair                    ");
		System.out.println("----------------------------");
	}

	/**
	 * 
	 * Realiza o cadastramento de um novo vétice, pedindo 
	 * nome e suas conexões
	 *
	 * @param teclado
	 */
	private static void cadastrarVertice(Scanner teclado) {
		String nomeVertice;
		do {
			System.out.println("Digite o nome do vértice: ");
			nomeVertice = teclado.next();
		}while(existeNomeVertice(nomeVertice));
		Map<String, Double> conexoes = cadastraConexoes(teclado, nomeVertice);
		
		Vertice vertice = new Vertice(nomeVertice, conexoes);
		LogicaDijkstraUtil.getVertices().add(vertice);
	}

	/**
	 * 
	 * Verifica se o nome existe na lista de vértices
	 *
	 * @param nomeVertice
	 * @return se existe ou não 
	 */
	private static boolean existeNomeVertice(String nomeVertice) {
		for (Vertice vertice : LogicaDijkstraUtil.getVertices()) {
			if(vertice.getNome().equals(nomeVertice)) {
				System.out.println("O nome do vertice já existe!");
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Cadastra nova conexão, adicionando na
	 * lista passada como parâmetro
	 *
	 * @param teclado
	 * @param conexoes
	 * @return
	 */
	private static Map<String, Double> cadastrarConexao(Scanner teclado, Map<String, Double> conexoes, String nomeVertice) {
		String nomeConexao = "";
		Integer tamanhoListaVertice = mostrarVerticesExistentes();
		if(tamanhoListaVertice < 1) {
			return null;
		}
		if((tamanhoListaVertice == 1 && LogicaDijkstraUtil.getVertices().get(0).getNome().equals(nomeVertice))) {
			System.out.println("Só este vértice está cadastrado então não é possível cadastrar conexões!");
			return null;
		}
		
		do {
			System.out.println("Digite o nome do vértice: ");
			nomeConexao = teclado.next();
			
			if(nomeVertice.equals(nomeConexao)) {
				System.out.println("A vértice "+nomeVertice+" não pode ser uma conexão dele mesmo");
			}
			
		}while(verticeInvalido(nomeConexao) || 
			   nomeVertice.equals(nomeConexao) || 
			   conexaoJaExistente(conexoes, nomeConexao));
		
		System.out.println("Digite o peso da conexão: ");
		Double pesoConexao = teclado.nextDouble();

		conexoes.put(nomeConexao, pesoConexao);
		return conexoes;
	}
	
	/**
	 * 
	 * Mostrar vértices existentes na lista
	 *
	 */
	private static int mostrarVerticesExistentes() {
		List<Vertice> lVertice = LogicaDijkstraUtil.getVertices();
		System.out.println("-----------------------------");
		System.out.println("    Vértices - cadastrado    ");
		System.out.println("-----------------------------");
		Integer numIdx = 0;
		
		if(lVertice.size() < 1) {
			System.out.println("-----------------------------");
			System.out.println("  Nenhum vértice cadastrado  ");
			System.out.println("-----------------------------");
		}
		
		for (Vertice umVertice : lVertice) {
			numIdx++;
			System.out.println(numIdx+". - ["+umVertice.getNome()+"]");
		}
		System.out.println("-----------------------------");
		return lVertice.size();
	}

	/**
	 * 
	 * valida se conexão está dentro das vértices existentes
	 *
	 * @param nomeConexao
	 * @return conexão é válida ou não
	 */
	private static boolean verticeInvalido(String nomeConexao) {
		for (Vertice lVertice : LogicaDijkstraUtil.getVertices()) {
			if (lVertice.getNome().equals(nomeConexao)) {
				return false;
			}
		}
		System.out.println("Nome da vertice inexistente!");
		return true;
	}
	
	/**
	 * 
	 * realizar o cálculo de distância menor e 
	 * exibir a resposta na tela
	 *
	 */
	private static void calcularDistanciaMenor(Scanner teclado) {
		if(LogicaDijkstraUtil.getVertices().size() == 0) {
			System.out.println("-----------------------------");
			System.out.println("  Nenhum vértice cadastrado  ");
			System.out.println("-----------------------------");
			return;
		}
		String nomeOrigem = "";
		String nomeDestino = "";
		
		do {
			System.out.println("Informe vértice de origem: ");
			nomeOrigem = teclado.next();
		}while(verticeInvalido(nomeOrigem));
		
		do {
			System.out.println("Informe vértice de destino: ");
			nomeDestino = teclado.next();
		}while(verticeInvalido(nomeDestino));
		
		if(nomeOrigem.equals(nomeDestino)) {
			System.out.println("Vértices de origem e destino são iguais!");
		}
		
		Vertice verticeOrigem = LogicaDijkstraUtil.recuperarVertice(nomeOrigem);
		Vertice verticeDestino = LogicaDijkstraUtil.recuperarVertice(nomeDestino);
		
		String caminho = LogicaDijkstraUtil.recuperarCaminhoMenor(verticeOrigem, verticeDestino);
		System.out.println("----------------------------");
		System.out.println("Caminho: "+ caminho);
		System.out.println("----------------------------");
	}
	
	/**
	 * 
	 * Cadastrar nova conexão para o vértice apontado
	 * 
	 */
	private static void cadastraConexoesVerticeExistente(Scanner sTeclado) {
		if(LogicaDijkstraUtil.getVertices().size() == 0) {
			System.out.println("-----------------------------");
			System.out.println("  Nenhum vértice cadastrado  ");
			System.out.println("-----------------------------");
			return;
		}
		String sNomeConexao = "";
		do {
			System.out.println("Digite o nome do vértice que deseja realizar as conexões: ");
			sNomeConexao = sTeclado.next();
		}while(verticeInvalido(sNomeConexao));
		Map<String, Double> conexoes = cadastraConexoes(sTeclado, sNomeConexao);
		Vertice vertice = LogicaDijkstraUtil.recuperarVertice(sNomeConexao);
		vertice.getConexoes().putAll(conexoes);
	}
	
	/**
	 * 
	 * Cadastra as conexões do vértice
	 *
	 * @param sTeclado
	 * @param sNomeVertice
	 */
	private static Map<String, Double> cadastraConexoes(Scanner sTeclado, String sNomeVertice) {
		int opcao;
		Map<String, Double> conexoes = new HashMap<>();

		do {
			System.out.println("-----------Menu-----------");
			System.out.println("1 - Cadastrar nova conexão");
			System.out.println("2 - Voltar                ");
			System.out.println("--------------------------");
			opcao = sTeclado.nextInt();
			
			switch (opcao) {
			case 1: 
				conexoes = cadastrarConexao(sTeclado, conexoes, sNomeVertice); 
				if(conexoes == null) {
					opcao = 2;
				}
				break;
			case 2: break;
			default:
				System.out.println("Opção digitada inválida!");
				break;
			}
		} while (opcao != 2);
		return conexoes;
	}
	
	/**
	 * 
	 * Exclui vértice da lista 
	 *
	 * @param sTeclado
	 */
	private static void excluiVertices(Scanner sTeclado) {
		String sNomeConexao = "";
		if(mostrarVerticesExistentes() < 1) {
			return;
		}
		do {
			System.out.println("Digite o nome do vértice que deseja excluir: ");
			sNomeConexao = sTeclado.next();
		}while(verticeInvalido(sNomeConexao));
		
		if (LogicaDijkstraUtil.recuperarVertice(sNomeConexao) != null) {
			LogicaDijkstraUtil.getVertices().remove(LogicaDijkstraUtil.recuperarVertice(sNomeConexao));
		}
	}	
	
	/**
	 * 
	 * Verifica se conexão já foi adicionada
	 *
	 * @param conexoes
	 * @param nomeConexao
	 */
	private static boolean conexaoJaExistente(Map<String, Double> conexoes, String nomeConexao) {
		for (Entry<String, Double> umVertice : conexoes.entrySet()) {
			if(umVertice.getKey().contains(nomeConexao)){
				return true;
			}
		}
		return false;
	}
	
}