package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import utils.LogicaDijkstraUtil;
import utils.Vertice;


public class Tela {
	
	public static void main(String[] args) {
		carregarMenuOpcoes();
	}
	
	/**
	 * 
	 * Carregar menu de op��es, 
	 * definindo suas opera��es 
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
			case 4: excluiConexoes(teclado); break;
			case 5: break;
			default:
				System.out.println("Op��o digitada inv�lida!");
				break;
			}
		} while (opcao != 5);
		teclado.close();
	}
	
	/**
	 * 
	 * Exibi��o de menu
	 *
	 */
	private static void exibirMenuPrincipal() {
		System.out.println("----------Menu--------------");
		System.out.println("1 - Cadastrar v�rtices      ");
		System.out.println("2 - Calcular dist�ncia menor");
		System.out.println("3 - Cadastrar conex�es      ");
		System.out.println("4 - Excluir conex�o         ");
		System.out.println("5 - Sair                    ");
		System.out.println("----------------------------");
	}

	/**
	 * 
	 * Realiza o cadastramento de um novo v�tice, pedindo 
	 * nome e suas conex�es
	 *
	 * @param teclado
	 */
	private static void cadastrarVertice(Scanner teclado) {
		String nomeVertice;
		do {
			System.out.println("Digite o nome do v�rtice: ");
			nomeVertice = teclado.next();
		}while(existeNomeVertice(nomeVertice));
		cadastraConexoes(teclado, nomeVertice);
	}

	/**
	 * 
	 * Verifica se o nome existe na lista de v�rtices
	 *
	 * @param nomeVertice
	 * @return se existe ou n�o 
	 */
	private static boolean existeNomeVertice(String nomeVertice) {
		for (Vertice vertice : LogicaDijkstraUtil.getVertices()) {
			if(vertice.getNome().equals(nomeVertice)) {
				System.out.println("O nome do vertice j� existe!");
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * Cadastra nova conex�o, adicionando na
	 * lista passada como par�metro
	 *
	 * @param teclado
	 * @param conexoes
	 * @return
	 */
	private static Map<String, Double> cadastrarConexao(Scanner teclado, Map<String, Double> conexoes) {
		String nomeConexao = "";
		mostrarVerticesExistentes();
		do {
			System.out.println("Digite o nome do v�rtice: ");
			nomeConexao = teclado.next();
		}while(verticeValido(nomeConexao));
		
		System.out.println("Digite o peso da conex�o: ");
		Double pesoConexao = teclado.nextDouble();

		conexoes.put(nomeConexao, pesoConexao);
		return conexoes;
	}
	
	/**
	 * 
	 * Mostrar v�rtices existentes na lista
	 *
	 */
	private static void mostrarVerticesExistentes() {
		List<Vertice> lVertice = LogicaDijkstraUtil.getVertices();
		System.out.println("-----------------------------");
		System.out.println("    V�rtices - cadastrado    ");
		System.out.println("-----------------------------");
		Integer numIdx = 0;
		for (Vertice umVertice : lVertice) {
			numIdx++;
			System.out.println(numIdx+". - ["+umVertice.getNome()+"]");
		}
		System.out.println("-----------------------------");
	}

	/**
	 * 
	 * valida se conex�o est� dentro das v�rtices existentes
	 *
	 * @param nomeConexao
	 * @return conex�o � v�lida ou n�o
	 */
	private static boolean verticeValido(String nomeConexao) {
		for (Vertice lVertice : LogicaDijkstraUtil.getVertices()) {
			if (!lVertice.getNome().equals(nomeConexao)) {
				System.out.println("Nome da vertice inexistente!");
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * realizar o c�lculo de dist�ncia menor e 
	 * exibir a resposta na tela
	 *
	 */
	private static void calcularDistanciaMenor(Scanner teclado) {
		String nomeOrigem = "";
		String nomeDestino = "";
		
		do {
			System.out.println("Informe v�rtice de origem: ");
			nomeOrigem = teclado.next();
		}while(verticeValido(nomeOrigem));
		
		do {
			System.out.println("Informe v�rtice de destino: ");
			nomeDestino = teclado.next();
		}while(verticeValido(nomeDestino));
		
		if(nomeOrigem.equals(nomeDestino)) {
			System.out.println("V�rtices de origem e destino s�o iguais!");
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
	 * Cadastrar nova conex�o para o v�rtice apontado
	 * 
	 */
	private static void cadastraConexoesVerticeExistente(Scanner sTeclado) {
		String sNomeConexao = "";
		mostrarVerticesExistentes();
		do {
			System.out.println("Digite o nome do v�rtice que deseja realizar as conex�es: ");
			sNomeConexao = sTeclado.next();
		}while(verticeValido(sNomeConexao));
		cadastraConexoes(sTeclado, sNomeConexao);
	}
	
	/**
	 * 
	 * Cadastra as conex�es do v�rtice
	 *
	 * @param sTeclado
	 * @param sNomeVertice
	 */
	private static void cadastraConexoes(Scanner sTeclado, String sNomeVertice) {
		int opcao;
		Map<String, Double> conexoes = new HashMap<>();

		do {
			System.out.println("----------Menu------------");
			System.out.println("1 - Cadastrar nova conex�o");
			System.out.println("2 - Voltar                ");
			System.out.println("--------------------------");
			opcao = sTeclado.nextInt();
			
			switch (opcao) {
			case 1: conexoes = cadastrarConexao(sTeclado, conexoes); break;
			case 2: break;
			default:
				System.out.println("Op��o digitada inv�lida!");
				break;
			}
		} while (opcao != 2);

		Vertice vertice = new Vertice(sNomeVertice, conexoes);
		LogicaDijkstraUtil.getVertices().add(vertice);
	}
	
	/**
	 * 
	 * Exclui v�rtice da lista 
	 *
	 * @param sTeclado
	 */
	private static void excluiConexoes(Scanner sTeclado) {
		String sNomeConexao = "";
		mostrarVerticesExistentes();
		do {
			System.out.println("Digite o nome do v�rtice que deseja realizar as conex�es: ");
			sNomeConexao = sTeclado.next();
		}while(verticeValido(sNomeConexao));
		
		if (LogicaDijkstraUtil.recuperarVertice(sNomeConexao) != null) {
			LogicaDijkstraUtil.getVertices().remove(LogicaDijkstraUtil.recuperarVertice(sNomeConexao));
		}
	}	
	
}