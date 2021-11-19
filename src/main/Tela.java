package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import utils.LogicaDijkstraUtil;
import utils.Vertice;

public class Tela {

	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);
		int opcao;

		do {

			System.out.println("----------Menu----------");
			System.out.println("1 - Cadastrar v�rtices");
			System.out.println("2 - Calcular dist�ncia menor");
			System.out.println("3 - Cadastrar conex�es");
			System.out.println("4 - Sair");
			System.out.println("------------------------");

			opcao = teclado.nextInt();

			switch (opcao) {
			case 1:
				cadastrarVertice(teclado);
				break;

			case 2:

				break;

			case 3:
				
				break;
			
			case 4:
				
				break;

			default:
				System.out.println("Op��o digitada inv�lida!");
				break;
			}

		} while (opcao != 4);

		teclado.close();
	}

	private static void cadastrarVertice(Scanner teclado) {
		
		String nomeVertice;
		do {
			
			System.out.println("Digite o nome do v�rtice: ");
			nomeVertice = teclado.next();
			
		}while(existeNomeVertice(nomeVertice));

		int opcao;

		List<Map<String, Double>> conexoes = new ArrayList<>();

		do {
			System.out.println("----------Menu----------");
			System.out.println("1 - Cadastrar nova conex�o");
			System.out.println("2 - Voltar");
			System.out.println("------------------------");
			opcao = teclado.nextInt();

			switch (opcao) {
			case 1:
				conexoes = cadastrarConexao(teclado, conexoes);
				break;
			case 2:
				break;

			default:
				System.out.println("Op��o digitada inv�lida!");
				break;
			}

		} while (opcao != 2);

		Vertice vertice = new Vertice(nomeVertice, conexoes);
		LogicaDijkstraUtil.getVertices().add(vertice);
	}

	private static boolean existeNomeVertice(String nomeVertice) {
		LogicaDijkstraUtil.getVertices();
		for (Vertice vertice : LogicaDijkstraUtil.getVertices()) {
			if(vertice.getNome().equals(nomeVertice)) {
				System.out.println("O nome do vertice j� existe!");
				return true;
			}
		}
		return false;
	}

	private static List<Map<String, Double>> cadastrarConexao(Scanner teclado, List<Map<String, Double>> conexoes) {
		System.out.println("Digite o nome do v�rtice: ");
		String nomeConexao = teclado.next();

		System.out.println("Digite o peso da conex�o: ");
		Double pesoConexao = teclado.nextDouble();

		Map<String, Double> conexao = new HashMap<>();

		conexao.put(nomeConexao, pesoConexao);
		conexoes.add(conexao);
		return conexoes;
	}

}