package ArvoreB;

public class ArvoreB {
    public class Nodo {
        int[] chave;
        int t; // Grau mínimo (define o range para o número de chaves)
        Nodo[] arrayFilho; // Array de filhos
        int n; // Número atual de chaves
        boolean folha; // Verdadeiro quando o nó é folha. Falso caso contrário

        public Nodo(int t, boolean leaf) {
            this.t = t;
            this.folha = leaf;
            this.chave = new int[2 * t - 1];
            this.arrayFilho = new Nodo[2 * t];
            this.n = 0;  // Inicialmente não há chaves nem filhos
        }
        
        public int Encontrar(int k) {
            for (int i = 0; i < this.n; i++) {
              if (this.chave[i] == k) {
                return i;
              }
            }
            return -1;
          }
    }

    private Nodo raiz;
    private int T;  // Grau mínimo

    public ArvoreB(int t) {
        this.raiz = null;
        this.T = t;
    }
    
    // Método de exclusão
    public void ExclusaoPorChave(int chave) {
    	Nodo x = raiz;
        int pos;
        while (x != null) {
          pos = x.Encontrar(chave);

          if (pos != -1) {
            if (x.folha) {
              int i = 0;
              for (i = 0; i < x.n; i++) {
            	    if (x.chave[i] == chave) {
            	        break;
            	    }
            	}
            	if (i < x.n) {
            	    for (; i < x.n - 1; i++) {
            	        x.chave[i] = x.chave[i + 1];
            	    }
            	    x.n--;
            	}
              break;
            } else {
              x = x.arrayFilho[pos];
            }
          } else {
            pos = 0;

            while (pos < x.n && x.chave[pos] < chave) {
              pos++;
            }
            x = x.arrayFilho[pos];
          }
        }

        if (x != null && x == raiz && x.n == 0) {
          raiz = x.arrayFilho[0];
        }
      }
    
    // Método para inserção
    public void inserir(int k) {
        Nodo r = raiz;
        if (r == null) {
            raiz = new Nodo(T, true);
            raiz.chave[0] = k;
            raiz.n = 1;
        } else {
            if (r.n == 2 * T - 1) {
                Nodo s = new Nodo(T, false);
                s.arrayFilho[0] = r;
                divideFilho(s, 0, r);
                int i = 0;
                if (s.chave[0] < k) {
                    i++;
                }
                inserirNonCheio(s.arrayFilho[i], k);
                raiz = s;
            } else {
                inserirNonCheio(r, k);
            }
        }
    }

    // Divide o filho x de y em dois filhos
    private void divideFilho(Nodo x, int i, Nodo y) {
        Nodo z = new Nodo(y.t, y.folha);
        z.n = T - 1;

        for (int j = 0; j < T - 1; j++) {
            z.chave[j] = y.chave[j + T];
        }
        if (!y.folha) {
            for (int j = 0; j < T; j++) {
                z.arrayFilho[j] = y.arrayFilho[j + T];
            }
        }
        y.n = T - 1;

        for (int j = x.n; j >= i + 1; j--) {
            x.arrayFilho[j + 1] = x.arrayFilho[j];
        }
        x.arrayFilho[i + 1] = z;

        for (int j = x.n - 1; j >= i; j--) {
            x.chave[j + 1] = x.chave[j];
        }
        x.chave[i] = y.chave[T - 1];

        x.n = x.n + 1;
    }

    // Inserir a chave k na árvore
    void inserirNonCheio(Nodo x, int k) {
        int i = x.n - 1;

        if (x.folha) {
            while (i >= 0 && k < x.chave[i]) {
                x.chave[i + 1] = x.chave[i];
                i--;
            }
            x.chave[i + 1] = k;
            x.n = x.n + 1;
        } else {
            while (i >= 0 && k < x.chave[i]) {
                i--;
            }
            i++;

            if (x.arrayFilho[i].n == 2 * T - 1) {
                divideFilho(x, i, x.arrayFilho[i]);

                if (k > x.chave[i]) {
                    i++;
                }
            }
            inserirNonCheio(x.arrayFilho[i], k);
        }
    }

    
 // Método para buscar uma chave na árvore B
    public boolean buscar(int k) {
        return buscarChave(raiz, k);
    }

    private boolean buscarChave(Nodo x, int k) {
        if (x == null) {
            return false;
        }

        int i = 0;
        while (i < x.n && k > x.chave[i]) {
            i++;
        }

        if (i < x.n && k == x.chave[i]) {
            return true;
        } else if (x.folha) {
            return false;
        } else {
            return buscarChave(x.arrayFilho[i], k);
        }
    }

    // Método para excluir uma chave da árvore B
    public void excluir(int k) {
        excluirChave(raiz, k);
    }

    private void excluirChave(Nodo x, int k) {
        if (x == null) {
            return;
        }

        int i = 0;
        while (i < x.n && k > x.chave[i]) {
            i++;
        }

        if (i < x.n && k == x.chave[i]) {
            // Caso 1: A chave k está na página x
            excluirCaso1(x, i);
        } else {
            // Caso 2: A chave k não está na página x
            excluirCaso2(x, i, k);
            excluirChave(x.arrayFilho[i], k);
        }
    }

    private void excluirCaso1(Nodo x, int i) {
        // Caso 1: A chave k está na página x
        if (x.folha) {
            // Caso 1a: A página x é uma folha
            for (int j = i; j < x.n - 1; j++) {
                x.chave[j] = x.chave[j + 1];
            }
            x.n--;
        } else {
            // Caso 1b: A página x não é uma folha
            int k1 = x.chave[i];
            if (x.arrayFilho[i].n >= T) {
                // Caso 1b(i): Se o filho y que precede k1 na página x tem pelo menos t chaves
                int predecessor = obterChavePredecessora(x.arrayFilho[i]);
                excluirChave(x.arrayFilho[i], predecessor);
                x.chave[i] = predecessor;
            } else if (x.arrayFilho[i + 1].n >= T) {
                // Caso 1b(ii): Se y tem menos de t chaves, mas o filho z que segue k1 na página x tem pelo menos t chaves
                int successor = obterChaveSucessora(x.arrayFilho[i + 1]);
                excluirChave(x.arrayFilho[i + 1], successor);
                x.chave[i] = successor;
            } else {
                // Caso 1b(iii): Se tanto y quanto z têm t-1 chaves
                merge(x, i);
                excluirChave(x.arrayFilho[i], k1);
            }
        }
    }

    private int obterChavePredecessora(Nodo x) {
        while (!x.folha) {
            x = x.arrayFilho[x.n];
        }
        return x.chave[x.n - 1];
    }

    private int obterChaveSucessora(Nodo x) {
        while (!x.folha) {
            x = x.arrayFilho[0];
        }
        return x.chave[0];
    }

    private void excluirCaso2(Nodo x, int i, int k) {
        if (x.arrayFilho[i].n < T) {
            if (i > 0 && x.arrayFilho[i - 1].n >= T) {
                reforcarFilhoAnterior(x, i);
            } else if (i < x.n && x.arrayFilho[i + 1].n >= T) {
                reforcarFilhoSeguinte(x, i);
            } else if (i < x.n) {
                // Se ambos os filhos têm menos de t chaves, faça a fusão
                merge(x, i);
            } else {
                merge(x, i - 1);
            }
        }
    }

    private void reforcarFilhoAnterior(Nodo x, int i) {
	        if (i > 0) {
	            Nodo child = x.arrayFilho[i];
	            Nodo sibling = x.arrayFilho[i - 1];
		        for (int j = child.n - 1; j >= 0; j--) {
		            child.chave[j + 1] = child.chave[j];
		        }
		        if (!child.folha) {
		            for (int j = child.n; j >= 0; j--) {
		                child.arrayFilho[j + 1] = child.arrayFilho[j];
		            }
		        }
		
		        child.chave[0] = x.chave[i - 1];
		        if (!x.folha) {
		            child.arrayFilho[0] = sibling.arrayFilho[sibling.n];
		        }
		
		        x.chave[i - 1] = sibling.chave[sibling.n - 1];
		
		        child.n++;
		        sibling.n--;
	        }
    }

    private void reforcarFilhoSeguinte(Nodo x, int i) {
        Nodo child = x.arrayFilho[i];
        Nodo sibling = x.arrayFilho[i + 1];

        child.chave[child.n] = x.chave[i];

        if (!child.folha) {
            child.arrayFilho[child.n + 1] = sibling.arrayFilho[0];
        }

        x.chave[i] = sibling.chave[0];

        for (int j = 1; j < sibling.n; j++) {
            sibling.chave[j - 1] = sibling.chave[j];
        }

        if (!sibling.folha) {
            for (int j = 1; j <= sibling.n; j++) {
                sibling.arrayFilho[j - 1] = sibling.arrayFilho[j];
            }
        }

        child.n++;
        sibling.n--;
    }

    private void merge(Nodo x, int i) {
        Nodo child = x.arrayFilho[i];
        Nodo sibling = x.arrayFilho[i + 1];

        child.chave[T - 1] = x.chave[i];

        for (int j = 0; j < T - 1; j++) {
            child.chave[T + j] = sibling.chave[j];
        }

        if (!child.folha) {
            for (int j = 0; j < T; j++) {
                child.arrayFilho[T + j] = sibling.arrayFilho[j];
            }
        }

        for (int j = i + 1; j < x.n; j++) {
            x.chave[j - 1] = x.chave[j];
        }

        for (int j = i + 2; j <= x.n; j++) {
            x.arrayFilho[j - 1] = x.arrayFilho[j];
        }

        child.n += sibling.n + 1;
        x.n--;
    }
}