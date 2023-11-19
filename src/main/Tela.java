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
			case 4: excluiVertices(teclado); break;
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
		Map<String, Double> conexoes = cadastraConexoes(teclado, nomeVertice);
				
		Vertice vertice = new Vertice(nomeVertice, conexoes);
		LogicaDijkstraUtil.getVertices().add(vertice);
	}

	/**
	 * 
	 * Realizar conex�o reversa. 
	 * Ex. conex�o existe de A -> B, ent�o vai ser feito
	 * de B -> A
	 *
	 * @param nomeConexao
	 * @param nomeVertice
	 * @param pesoVertice
	 */
	private static void criarConexaoReversa(String nomeConexao, String nomeVertice, Double pesoVertice) {
		Vertice conexao = LogicaDijkstraUtil.recuperarVertice(nomeConexao);
		conexao.getConexoes().put(nomeVertice, pesoVertice);
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
	private static Map<String, Double> cadastrarConexao(Scanner teclado, Map<String, Double> conexoes, String nomeVertice) {
		String nomeConexao = "";
		Integer tamanhoListaVertice = mostrarVerticesExistentes();
		if(tamanhoListaVertice < 1) {
			return null;
		}
		if((tamanhoListaVertice == 1 && LogicaDijkstraUtil.getVertices().get(0).getNome().equals(nomeVertice))) {
			System.out.println("S� este v�rtice est� cadastrado ent�o n�o � poss�vel cadastrar conex�es!");
			return null;
		}
		
		do {
			System.out.println("Digite o nome do v�rtice: ");
			nomeConexao = teclado.next();
			
			if(nomeVertice.equals(nomeConexao)) {
				System.out.println("A v�rtice "+nomeVertice+" n�o pode ser uma conex�o dele mesmo");
			}
			
		}while(verticeInvalido(nomeConexao) || 
			   nomeVertice.equals(nomeConexao) || 
			   conexaoJaExistente(conexoes, nomeConexao, nomeVertice));
		
		System.out.println("Digite o peso da conex�o: ");
		Double pesoConexao = teclado.nextDouble();
		
		conexoes.put(nomeConexao, pesoConexao);
		criarConexaoReversa(nomeConexao, nomeVertice, pesoConexao);
		return conexoes;
	}
		
	/**
	 * 
	 * Mostrar v�rtices existentes na lista
	 *
	 */
	private static int mostrarVerticesExistentes() {
		List<Vertice> lVertice = LogicaDijkstraUtil.getVertices();
		System.out.println("-----------------------------");
		System.out.println("    V�rtices - cadastrado    ");
		System.out.println("-----------------------------");
		Integer numIdx = 0;
		
		if(lVertice.size() < 1) {
			System.out.println("-----------------------------");
			System.out.println("  Nenhum v�rtice cadastrado  ");
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
	 * valida se conex�o est� dentro das v�rtices existentes
	 *
	 * @param nomeConexao
	 * @return conex�o � v�lida ou n�o
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
	 * realizar o c�lculo de dist�ncia menor e 
	 * exibir a resposta na tela
	 *
	 */
	private static void calcularDistanciaMenor(Scanner teclado) {
		if(LogicaDijkstraUtil.getVertices().size() == 0) {
			System.out.println("-----------------------------");
			System.out.println("  Nenhum v�rtice cadastrado  ");
			System.out.println("-----------------------------");
			return;
		}
		String nomeOrigem = "";
		String nomeDestino = "";
		
		do {
			System.out.println("Informe v�rtice de origem: ");
			nomeOrigem = teclado.next();
		}while(verticeInvalido(nomeOrigem));
		
		do {
			System.out.println("Informe v�rtice de destino: ");
			nomeDestino = teclado.next();
		}while(verticeInvalido(nomeDestino));
		
		if(nomeOrigem.equals(nomeDestino)) {
			System.out.println("V�rtices de origem e destino s�o iguais!");
		}
		
		Vertice verticeOrigem = LogicaDijkstraUtil.recuperarVertice(nomeOrigem);
		Vertice verticeDestino = LogicaDijkstraUtil.recuperarVertice(nomeDestino);
		
		String caminho = LogicaDijkstraUtil.recuperarCaminhoMenor(verticeOrigem, verticeDestino);
		
		System.out.println("");
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
		if(LogicaDijkstraUtil.getVertices().size() == 0) {
			System.out.println("-----------------------------");
			System.out.println("  Nenhum v�rtice cadastrado  ");
			System.out.println("-----------------------------");
			return;
		}
		String sNomeConexao = "";
		do {
			System.out.println("Digite o nome do v�rtice que deseja realizar as conex�es: ");
			sNomeConexao = sTeclado.next();
		}while(verticeInvalido(sNomeConexao));
		Map<String, Double> conexoes = cadastraConexoes(sTeclado, sNomeConexao);
		Vertice vertice = LogicaDijkstraUtil.recuperarVertice(sNomeConexao);
		vertice.getConexoes().putAll(conexoes);
	}
	
	/**
	 * 
	 * Cadastra as conex�es do v�rtice
	 *
	 * @param sTeclado
	 * @param sNomeVertice
	 */
	private static Map<String, Double> cadastraConexoes(Scanner sTeclado, String sNomeVertice) {
		int opcao;
		Map<String, Double> conexoes = new HashMap<>();

		do {
			System.out.println("-----------Menu-----------");
			System.out.println("1 - Cadastrar nova conex�o");
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
				System.out.println("Op��o digitada inv�lida!");
				break;
			}
		} while (opcao != 2);
		return conexoes;
	}
	
	/**
	 * 
	 * Exclui v�rtice da lista 
	 *
	 * @param sTeclado
	 */
	private static void excluiVertices(Scanner sTeclado) {
		String sNomeConexao = "";
		if(mostrarVerticesExistentes() < 1) {
			return;
		}
		do {
			System.out.println("Digite o nome do v�rtice que deseja excluir: ");
			sNomeConexao = sTeclado.next();
		}while(verticeInvalido(sNomeConexao));
		
		if (LogicaDijkstraUtil.recuperarVertice(sNomeConexao) != null) {
			LogicaDijkstraUtil.getVertices().remove(LogicaDijkstraUtil.recuperarVertice(sNomeConexao));
		}
	}	
	
	/**
	 * 
	 * Verifica se conex�o j� foi adicionada
	 *
	 * @param conexoes
	 * @param nomeConexao
	 */
	private static boolean conexaoJaExistente(Map<String, Double> conexoes, String nomeConexao, String nomeVertice) {
		for (Entry<String, Double> umVertice : conexoes.entrySet()) {
			if(umVertice.getKey().contains(nomeConexao)){
				System.out.println("Conex�o j� existente");
				return true;
			}
		}
		Vertice vertice = LogicaDijkstraUtil.recuperarVertice(nomeVertice);
		for (Entry<String, Double> umVertice : vertice.getConexoes().entrySet()) {
			if(umVertice.getKey().contains(nomeConexao)){
				System.out.println("Conex�o j� existente");
				return true;
			}
		}
		return false;
	}
	
}