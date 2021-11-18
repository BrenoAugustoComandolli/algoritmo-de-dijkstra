package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.Vertice;

public class Tela {
	
	public static void main(String[] args) {

		Scanner teclado = new Scanner(System.in);
		int opcao;
		
		do {
			
			System.out.println("----------Menu----------");
			System.out.println("1 - Cadastrar v�rtices");
			System.out.println("2 - Calcular dist�ncia menor");
			System.out.println("3 - Sair");
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
				
			default:
				System.out.println("Op��o digitada inv�lida!");
				break;
			}
			
		} while (opcao != 3);
		
		teclado.close();
	}
	
	private static void cadastrarVertice(Scanner teclado) {
		System.out.println("Digite o nome do v�rtice: ");
		String nomeVertice = teclado.next();
		
		int opcao;
		
		List<String> conexoes = new ArrayList<>();
		
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
		
		}while (opcao != 2);
			
		Vertice vertice = new Vertice(nomeVertice, conexoes);
	}
	
	private static List<String> cadastrarConexao(Scanner teclado, List<String> conexoes) {
		System.out.println("Digite o nome do v�rtice: ");
		String nomeConexao = teclado.next();
		conexoes.add(nomeConexao);
		return conexoes;
	}
	
}