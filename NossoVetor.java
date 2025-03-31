import java.util.Random;

public class NossoVetor {
    private int ocupacao;
    private int[] vetor;
    private long comparacoes;
    private long trocas;

    public NossoVetor(int tamanho) {
        vetor = new int[tamanho];
        ocupacao = 0;
        resetContadores();
    }

    public NossoVetor() {
        this(10);
    }

    public void resetContadores() {
        comparacoes = 0;
        trocas = 0;
    }
    public int getOcupacao() {
        return ocupacao;
    }
    
    public int[] getVetor() {
        return vetor;
    }

    public long getComparacoes() {
        return comparacoes;
    }
    
    public long getTrocas() {
        return trocas;
    }

    public void insere(int i) {
        if (estaCheio())
            redimensiona(vetor.length * 2);
        vetor[ocupacao++] = i;
    }

    boolean estaCheio() {
        return ocupacao == vetor.length;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("\nocupacao = " + ocupacao + "\n");
        for (int i = 0; i < ocupacao; i++) {
            s.append(vetor[i]).append(" ");
        }
        return s + "\n";
    }

    public boolean estaVazio() {
        return ocupacao == 0;
    }

    public int remove() {
        if (estaVazio()) {
            throw new VetorVazioException("vetor vazio, não há o que remover");
        }
        int aux = vetor[--ocupacao];
        if (vetor.length >= 6 && ocupacao <= vetor.length / 4) {
            redimensiona(vetor.length / 2);
        }
        return aux;
    }

    private void redimensiona(int novoTamanho) {
        int[] temp = new int[novoTamanho];
        System.arraycopy(vetor, 0, temp, 0, ocupacao);
        vetor = temp;
    }

    public int getTamanho() {
        return vetor.length;
    }

    public void preencheVetor() {
        Random random = new Random();
        for (int i = 0; i < vetor.length; i++) {
            vetor[i] = random.nextInt(vetor.length * 10);
        }
        ocupacao = vetor.length;
    }

    public boolean contem(int elemento) {
        for (int i = 0; i < ocupacao; i++) {
            if (vetor[i] == elemento) {
                return true;
            }
        }
        return false;
    }

    public int indiceDe(int elemento) {
        if (estaVazio())
            throw new VetorVazioException("vetor vazio");
        for (int i = 0; i < ocupacao; i++) {
            if (vetor[i] == elemento) {
                return i;
            }
        }
        throw new ElementoNaoEncontradoException(elemento + " não encontrado");
    }

    public void bubbleSort() {
        resetContadores();
        for (int vez = 1; vez < ocupacao; vez++) {
            for (int i = 0; i < ocupacao - vez; i++) {
                comparacoes++;
                if (vetor[i] > vetor[i + 1]) {
                    trocas++;
                    int aux = vetor[i];
                    vetor[i] = vetor[i + 1];
                    vetor[i + 1] = aux;
                }
            }
        }
        imprimirEstatisticasOrdenacao("Bubble Sort");
    }

    public void selectionSort() {
        resetContadores();
        for (int i = 0; i < ocupacao - 1; i++) {
            int min = i;
            for (int j = i + 1; j < ocupacao; j++) {
                comparacoes++;
                if (vetor[j] < vetor[min]) {
                    min = j;
                }
            }
            trocas++;
            int temp = vetor[i];
            vetor[i] = vetor[min];
            vetor[min] = temp;
        }
        imprimirEstatisticasOrdenacao("Selection Sort");
    }

    public void insertionSort() {
        resetContadores();
        for (int j = 1; j < ocupacao; j++) {
            int chave = vetor[j];
            int i = j - 1;
            while (i >= 0) {
                comparacoes++;
                if (vetor[i] > chave) {
                    trocas++;
                    vetor[i + 1] = vetor[i];
                    i--;
                } else {
                    break;
                }
            }
            trocas++;
            vetor[i + 1] = chave;
        }
        imprimirEstatisticasOrdenacao("Insertion Sort");
    }

    private void imprimirEstatisticasOrdenacao(String algoritmo) {
        System.out.println("\nEstatísticas de " + algoritmo + ":");
        System.out.println("Tamanho do vetor: " + ocupacao);
        System.out.println("Comparações: " + comparacoes);
        System.out.println("Trocas: " + trocas);
        System.out.println("Total de operações: " + (comparacoes + trocas));
    }

    public int buscaSimples(int valorPesquisado) {
        resetContadores();
        if (estaVazio()) {
            throw new VetorVazioException("vetor vazio, não é possível realizar busca");
        }
        
        for (int i = 0; i < ocupacao; i++) {
            comparacoes++;
            if (vetor[i] == valorPesquisado) {
                imprimirEstatisticasBusca("Busca Linear", true);
                return i;
            }
        }
        imprimirEstatisticasBusca("Busca Linear", false);
        throw new ElementoNaoEncontradoException(valorPesquisado + " não encontrado");
    }

    public int buscaBinaria(int valorPesquisado) {
        resetContadores();
        if (estaVazio()) {
            throw new VetorVazioException("vetor vazio, não é possível realizar busca");
        }
        
        int inicio = 0;
        int fim = ocupacao - 1;
        
        while (inicio <= fim) {
            comparacoes++;
            int meio = (inicio + fim) / 2;
            
            if (vetor[meio] == valorPesquisado) {
                imprimirEstatisticasBusca("Busca Binária", true);
                return meio;
            }
            
            if (valorPesquisado > vetor[meio]) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }
        }
        
        imprimirEstatisticasBusca("Busca Binária", false);
        throw new ElementoNaoEncontradoException(valorPesquisado + " não encontrado");
    }

    private void imprimirEstatisticasBusca(String tipoBusca, boolean encontrado) {
        System.out.println("\nEstatísticas de " + tipoBusca + ":");
        System.out.println("Comparações realizadas: " + comparacoes);
        System.out.println("Elemento " + (encontrado ? "encontrado" : "não encontrado"));
    }

    public void executarOperacoesAutomaticas() {
        Random random = new Random();
        int elementoAleatorio = vetor[random.nextInt(ocupacao)];
        
        System.out.println("\nElemento aleatório selecionado: " + elementoAleatorio);
        
        // 1. Busca Linear
        System.out.println("\n[1] Executando Busca Linear...");
        long inicio = System.currentTimeMillis();
        try {
            buscaSimples(elementoAleatorio);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        long fim = System.currentTimeMillis();
        System.out.println("Tempo Busca Linear: " + (fim - inicio) + "ms");
        
        // 2. Ordenação + Busca Binária
        System.out.println("\n[2] Executando Insertion Sort + Busca Binária...");
        inicio = System.currentTimeMillis();
        insertionSort();
        try {
            buscaBinaria(elementoAleatorio);
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        fim = System.currentTimeMillis();
        System.out.println("Tempo total ordenação + busca: " + (fim - inicio) + "ms");
        
        // 3. Bubble Sort
        System.out.println("\n[3] Executando Bubble Sort...");
        inicio = System.currentTimeMillis();
        bubbleSort();
        fim = System.currentTimeMillis();
        System.out.println("Tempo Bubble Sort: " + (fim - inicio) + "ms");
        
        // 4. Selection Sort
        System.out.println("\n[4] Executando Selection Sort...");
        inicio = System.currentTimeMillis();
        selectionSort();
        fim = System.currentTimeMillis();
        System.out.println("Tempo Selection Sort: " + (fim - inicio) + "ms");
        
        // 5. Insertion Sort
        System.out.println("\n[5] Executando Insertion Sort...");
        inicio = System.currentTimeMillis();
        insertionSort();
        fim = System.currentTimeMillis();
        System.out.println("Tempo Insertion Sort: " + (fim - inicio) + "ms");
    }
}

class VetorVazioException extends RuntimeException {
    public VetorVazioException(String msg) {
        super(msg);
    }
}

class ElementoNaoEncontradoException extends RuntimeException {
    public ElementoNaoEncontradoException(String msg) {
        super(msg);
    }
}