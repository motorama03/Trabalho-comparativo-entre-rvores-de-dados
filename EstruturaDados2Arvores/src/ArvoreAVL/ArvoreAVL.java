package ArvoreAVL;

public class ArvoreAVL {
	private class Nodo {
        private int dado, altd, alte;
        private int copia; // Novo atributo para contagem de ocorrências
        private Nodo dir, esq;

        public Nodo(int dado) {
            this.dado = dado;
            dir = esq = null;
            altd = alte = 0;
            copia = 1; // Inicializa a contagem com 1 quando a chave é inserida pela primeira vez
        }
        
        private int obterAltura() {
            int alturaEsquerda = (esq != null) ? esq.obterAltura() : 0;
            int alturaDireita = (dir != null) ? dir.obterAltura() : 0;
            return Math.max(alturaEsquerda, alturaDireita) + 1;
        }
    }

    Nodo raiz;
    int primos;

    public ArvoreAVL() {
        raiz = null;
    }

    private int obterAlturaAVL(Nodo esq) {
        int alturaEsquerda = (raiz.esq != null) ? raiz.esq.obterAltura() : 0;
        int alturaDireita = (raiz.dir != null) ? raiz.dir.obterAltura() : 0;
        return Math.max(alturaEsquerda, alturaDireita) + 1;
    }
    
    public void inserir(int dado) {
        raiz = inserirDado(raiz, dado);
    }
    
    private Nodo inserirDado(Nodo raiz, int dado) {
        if (raiz == null) {
            raiz = new Nodo(dado);
            return raiz;
        }

        if (dado == raiz.dado) {
            raiz.copia++; // Aumenta a contagem se a chave já existe
        } else if (dado < raiz.dado) {
            raiz.esq = inserirDado(raiz.esq, dado);
            if (raiz.esq.altd > raiz.esq.alte) {
                raiz.alte = raiz.esq.altd + 1;
            } else {
                raiz.alte = raiz.esq.alte + 1;
            }
            raiz = balanceamento(raiz);
        } else if (dado > raiz.dado) {
            raiz.dir = inserirDado(raiz.dir, dado);
            if (raiz.dir.altd > raiz.dir.alte) {
                raiz.altd = raiz.dir.altd + 1;
            } else {
                raiz.altd = raiz.dir.alte + 1;
            }
            raiz = balanceamento(raiz);
        }
        return raiz;
    }
    
	private Nodo balanceamento (Nodo raiz) {
		int ĩ = raiz.altd - raiz.alte;
		int ĩSubArv;
		if (ĩ == 2) {
			ĩSubArv = raiz.dir.altd - raiz.dir.alte;
			if (ĩSubArv >=0) {
				raiz = rotacao_esquerda(raiz);
			} else {
				raiz.dir = rotacao_direita(raiz.dir);
				raiz = rotacao_esquerda(raiz);
			}
		} else if (ĩ == -2) {
			ĩSubArv = raiz.esq.altd - raiz.esq.alte;
		if (ĩSubArv <= 0) {
			raiz = rotacao_direita(raiz);
		} else {
			raiz.esq = rotacao_esquerda(raiz.esq);
			raiz = rotacao_direita(raiz);
			}
		}
		return raiz;
	}
	
	private Nodo rotacao_esquerda(Nodo raiz) {
		Nodo aux1, aux2;
		aux1 = raiz.dir;
		aux2 = raiz.esq;
		raiz.dir = aux2;
		aux1.esq = raiz;
		if (raiz.dir == null) {
			raiz.altd = 0;
		} else if (raiz.dir.alte > raiz.dir.altd) {
			raiz.altd = raiz.dir.alte + 1;
		} else {
			raiz.altd = raiz.dir.altd + 1;
		}
		if (aux1.esq.alte > aux1.esq.altd) {
			aux1.alte = aux1.esq.alte + 1;
		} else {
			aux1.alte = aux1.esq.altd + 1;
		}
		return aux1;
	}
	
	private Nodo rotacao_direita(Nodo raiz) {
	    if (raiz == null || raiz.esq == null) {
	        return raiz; // Verifique se raiz ou raiz.esq é nulo e retorne a raiz original nesses casos.
	    }

	    Nodo aux1 = raiz.esq;
	    Nodo aux2 = aux1.dir;
	    raiz.esq = aux2;
	    aux1.dir = raiz;

	    // Recalcula as alturas
	    raiz.alte = obterAlturaAVL(raiz.esq);
	    aux1.altd = obterAlturaAVL(raiz);
	    return aux1;
	}

	public void mostrarEmOrdem() {
	    mostrarEmOrdemComDuplicatas(raiz);
	}

	private void mostrarEmOrdemComDuplicatas(Nodo raiz) {
	    if (raiz != null) {
	        mostrarEmOrdemComDuplicatas(raiz.esq);

	        // Exibe a chave e a contagem de nós duplicados
	        System.out.println("Chave: " + raiz.dado + ", Cópias: " + raiz.copia);

	        mostrarEmOrdemComDuplicatas(raiz.dir);
	    }
	}

	
	public boolean eAvl(ArvoreAVL arvore) {
		if(verAvl(arvore.raiz))
			return true;
		else {
			return false;
		}
	}

	private  boolean verAvl(Nodo raiz) {
//		Chamada recursiva dos nós seguintes 
//		Comparação para verificar se a árvore está dentro da regra de altura dos "galhos"
		if (raiz != null) {
			verAvl(raiz.esq);
			if(raiz.altd - raiz.alte >= 2 || raiz.altd - raiz.alte <= -2){
				return false;
			}
		verAvl(raiz.dir);
		}
	return true;
	}

	public void retornaNodosPrimos() {
		if(raiz == null) {
			return;
		}
		else {
			System.out.println("Quantidade de primos presentes: "+retornaNodosPrimosValendo(raiz));
		}
		
	}
	private int retornaNodosPrimosValendo(Nodo raiz) {
		if(raiz == null) {
			return primos;
		}
//		Compara se o valor em questão é divisivel por 2 e por 3
		if(raiz.dado % 2 != 0 && raiz.dado % 3 != 0) {
			primos++;
//			Caso for é printado na tela
			System.out.println("Primo: "+raiz.dado);
		}else if(raiz.dado == 2 || raiz.dado == 3) {
			primos ++;
			System.out.println("Primo: "+raiz.dado);
		}
//		chamada recursiva para os nodos seguintes
		retornaNodosPrimosValendo(raiz.esq);
		retornaNodosPrimosValendo(raiz.dir);
		return primos;
	}
	
	public void retornaNodosMsmNivel(int nivel) {
	    int count = 0;
	    if (raiz == null) {
	        return;
	    } else {
	        retornaNosMsmNivel(raiz, nivel, count);
	    }
	}

	private void retornaNosMsmNivel(Nodo raiz, int nivel, int count) {
	    if (raiz == null) {
	        return;
	    }
	    if (count == nivel) {
	        System.out.println("O nodo " + raiz.dado + " pertence ao nivel " + nivel);
	    }
	    // Realize as chamadas recursivas antes de incrementar 'count'.
	    retornaNosMsmNivel(raiz.esq, nivel, count + 1);
	    retornaNosMsmNivel(raiz.dir, nivel, count + 1);
	}
	
	public int somaImpares() {
	    int nivelRaiz = 1; // O nível da raiz é considerado 1
	    return somaImparesNos(raiz, nivelRaiz);
	}
	
	private int somaImparesNos(Nodo raiz, int nivel) {
	    if (raiz == null) {
	        return 0;
	    }

	    // Verifica se o nível atual é ímpar e, se for, soma o valor do nó atual.
	    if (nivel % 2 != 0) {
	        return raiz.dado + somaImparesNos(raiz.esq, nivel + 1) + somaImparesNos(raiz.dir, nivel + 1);
	    } else {
	        // Caso contrário, continua a busca em ambos os filhos.
	        return somaImparesNos(raiz.esq, nivel + 1) + somaImparesNos(raiz.dir, nivel + 1);
	    }
	}
	
	public void remove(int chave) {
	    raiz = removeItem(raiz, chave);
	}

	private Nodo removeItem(Nodo raiz, int chave) {
	    if (raiz == null) {
	        return null;
	    }

	    if (chave < raiz.dado) {
	        raiz.esq = removeItem(raiz.esq, chave);
	    } else if (chave > raiz.dado) {
	        raiz.dir = removeItem(raiz.dir, chave);
	    } else {
	        if (raiz.copia > 1) {
	            // Se houver mais de uma cópia da chave, reduza a contagem
	            raiz.copia--;
	        } else {
	            if (raiz.esq == null) {
	                return raiz.dir;
	            } else if (raiz.dir == null) {
	                return raiz.esq;
	            } else {
	                Nodo next = findNext(raiz.dir);
	                raiz.dado = next.dado;
	                raiz.dir = removeItem(raiz.dir, next.dado);
	            }
	        }
	    }
	    return raiz;
	}
	
	private Nodo findNext(Nodo nodo) {
		while(nodo.esq != null) {
			nodo = nodo.esq;
		}
		return nodo;
	}
	
	public void inserirSemRecursao(int dado) {
        Nodo novoNodo = new Nodo(dado);

        if (raiz == null) {
            raiz = novoNodo;
            return;
        }

        Nodo atual = raiz;

        while (true) {
            if (dado < atual.dado) {
                if (atual.esq == null) {
                    atual.esq = novoNodo;
                    break;
                }
                atual = atual.esq;
            } else if (dado > atual.dado) {
                if (atual.dir == null) {
                    atual.dir = novoNodo;
                    break;
                }
                atual = atual.dir;
            } else {
                atual.copia++;
                break;
            }
        }

        atual = balanceamento(atual);
        System.out.println("Valor "+dado+" foi inserido.");
    }

	public boolean valorExiste(int valor) {
	    return valorExisteNaArvore(raiz, valor);
	}

	private boolean valorExisteNaArvore(Nodo nodo, int valor) {
	    while (nodo != null) {
	        if (valor == nodo.dado) {
	            return true; // Valor encontrado na árvore
	        } else if (valor < nodo.dado) {
	            nodo = nodo.esq;
	        } else {
	            nodo = nodo.dir;
	        }
	    }
	    return false; // Valor não encontrado na árvore
	}
	
//	private Nodo atualizarAlturaEBalancear(Nodo raiz) {
//	    int alturaEsquerda = (raiz.esq != null) ? raiz.esq.obterAltura() : 0;
//	    int alturaDireita = (raiz.dir != null) ? raiz.dir.obterAltura() : 0;
//	    raiz.alte = alturaEsquerda;
//	    raiz.altd = alturaDireita;
//
//	    int balanceamento = alturaEsquerda - alturaDireita;
//	    if (balanceamento > 1) {
//	        if (raiz.esq != null && raiz.dado < raiz.esq.dado) {
//	            return rotacao_direita(raiz);
//	        } else {
//	            raiz.esq = rotacao_esquerda(raiz.esq);
//	            return rotacao_direita(raiz);
//	        }
//	    } else if (balanceamento < -1) {
//	        if (raiz.dir != null && raiz.dado > raiz.dir.dado) {
//	            return rotacao_esquerda(raiz);
//	        } else {
//	            raiz.dir = rotacao_direita(raiz.dir);
//	            return rotacao_esquerda(raiz);
//	        }
//	    }
//	    return raiz;
//	}


}