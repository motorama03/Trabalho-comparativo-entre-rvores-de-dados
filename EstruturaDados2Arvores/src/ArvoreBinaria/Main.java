package ArvoreBinaria;

import GeradorDeDados.ArrayGenerator;

public class Main {

	public static void main(String[] args) {
		
		
		ArrayGenerator geradorDados = new ArrayGenerator();
		int[] array = geradorDados.generateRandomArray(100000);
		array = geradorDados.generateSortedArray(100000);
		ArvoreBynary avb = new ArvoreBynary();
		long inicioInsercao = System.currentTimeMillis();
		for (int x = 0; x < array.length; x++) {
			avb.inserirSemRecursividade(x);
			System.out.println(x);
		}
		long finalInsercao = System.currentTimeMillis();
		double tempoExecucao3 = (finalInsercao - inicioInsercao);
		System.out.println("Tempo de execução para inserir "+ array.length +" : "+ tempoExecucao3/1000+" Segundos");
		long inicioBusca = System.currentTimeMillis();
		if(avb.valorExiste(2000000)) {
			System.out.println("O valor informado existe na lista");
		}else {
			System.out.println("O valor informado não existe na lista");
		}
		long finalBusca = System.currentTimeMillis();
		double tempoExecucao2 = (finalBusca - inicioBusca);
		long inicioExclusao = System.currentTimeMillis();
		System.out.println("Tempo de execução para buscar um valor que não existe na árvore "+ tempoExecucao2/1000+" Segundos");
		for (int x = 0; x < array.length; x++) {
			avb.remove(x);
			//System.out.println(x);
		}
		long fim = System.currentTimeMillis();
		double tempoExecucao = (fim - inicioExclusao);
		System.out.println("Tempo de execução para excluir "+ array.length +" : "+ tempoExecucao/1000+" Segundos");
	}
}
