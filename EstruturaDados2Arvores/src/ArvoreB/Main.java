package ArvoreB;

import GeradorDeDados.ArrayGenerator;

public class Main {

	public static void main(String[] args) {
		
		
		ArrayGenerator geradorDados = new ArrayGenerator();
		int[] array = geradorDados.generateSortedArray(100000);
		ArvoreB avl = new ArvoreB(1000000);
		long inicioInsercao = System.currentTimeMillis();
		for (int x = 0; x < array.length; x++) {
			avl.inserir(x);
		}
		long finalInsercao = System.currentTimeMillis();
		double tempoExecucao3 = (finalInsercao - inicioInsercao);
		System.out.println("Tempo de execução para inserir "+ array.length +" : "+ tempoExecucao3/1000+" Segundos");
		long inicioBusca = System.nanoTime();
		if(avl.buscar(2000000)) {
			System.out.println("O valor informado existe na lista");
		}else {
			System.out.println("O valor informado não existe na lista");
		}
		double finalBusca = System.nanoTime();
		double tempoExecucao2 = ((finalBusca - inicioBusca)*0.000001)/1000;
		double inicioExclusao = System.nanoTime();
		System.out.println("Tempo de execução para buscar um valor que não existe na árvore "+ tempoExecucao2/1000+" Segundos");
		for (int x = 0; x < array.length; x++) {
			avl.ExclusaoPorChave(x);
		}
		long fim = System.nanoTime();
		double tempoExecucao = ((fim - inicioExclusao)*0.000001)/1000;
		System.out.println("Tempo de execução para excluir "+ array.length +" : "+ tempoExecucao/1000+" Segundos");
	}
}

