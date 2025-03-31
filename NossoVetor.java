import java.util.Random;
import java.util.Scanner;

public class NossoVetor {
    private int ocupacao;
    private int[] vetor;
    private long comparacoes; // Contador de comparações
    private long trocas;      // Contador de trocas

    public NossoVetor(int tamanho) {
        vetor = new int[tamanho];
        ocupacao = 0;
        resetContadores();
    }

    public NossoVetor() {
        this(10);
    }

    private void resetContadores() {
        comparacoes = 0;
        trocas = 0;
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
        String s = "\nocupacao = " + ocupacao + "\n";
        for (int i = 0; i < ocupacao; i++) {
            s += vetor[i] + " ";
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
        for (int i = 0; i < ocupacao; i++)
            temp[i] = vetor[i];
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

    // Algoritmos de ordenação
    public void bubbleSort() {
        resetContadores();
        for (int vez = 1; vez < vetor.length; vez++) {
            for (int i = 0; i < vetor.length - vez; i++) {
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
        for (int i = 0; i < vetor.length - 1; ++i) {
            int min = i;
            for (int j = i + 1; j < vetor.length; ++j) {
                comparacoes++;
                if (vetor[j] < vetor[min]) {
                    min = j;
                }
            }
            trocas++;
            int x = vetor[i];
            vetor[i] = vetor[min];
            vetor[min] = x;
        }
        imprimirEstatisticasOrdenacao("Selection Sort");
    }

    public void insertionSort() {
        resetContadores();
        for (int j = 1; j < vetor.length; ++j) {
            int x = vetor[j];
            int i;
            for (i = j - 1; i >= 0; --i) {
                comparacoes++;
                if (vetor[i] > x) {
                    trocas++;
                    vetor[i + 1] = vetor[i];
                } else {
                    break;
                }
            }
            trocas++;
            vetor[i + 1] = x;
        }
        imprimirEstatisticasOrdenacao("Insertion Sort");
    }

    private void imprimirEstatisticasOrdenacao(String algoritmo) {
        System.out.println("\nEstatísticas de " + algoritmo + ":");
        System.out.println("Tamanho do vetor: " + vetor.length);
        System.out.println("Comparações: " + comparacoes);
        System.out.println("Trocas: " + trocas);
        System.out.println("Total de operações: " + (comparacoes + trocas) + "\n");
    }

    // Algoritmos de busca
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
        System.out.println("Elemento " + (encontrado ? "encontrado" : "não encontrado") + "\n");
    }

    // Menu de operações
    public int Menu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite a opção que deseja:\n1-Busca Linear\n2-Busca Binária\n3-Bubble Sort\n4-Selection Sort\n5-Insertion Sort\n6-Sair");
        int opcao = scanner.nextInt();

        Random random = new Random();
        int elementoAleatorio = vetor[random.nextInt(ocupacao)]; //gera um valor aleatório para buscar no vetor

        switch (opcao) {
            case 1:
                System.out.println("Buscando elemento aleatório: " + elementoAleatorio);
                long inicio = System.currentTimeMillis();
                boolean encontrado = contem(elementoAleatorio);
                long fim = System.currentTimeMillis();
                System.out.println("Elemento " + (encontrado ? "encontrado" : "não encontrado"));
                System.out.println("Tempo de busca linear: " + (fim - inicio) + "ms");
                break;
            case 2:
                System.out.println("Buscando elemento aleatório: " + elementoAleatorio);
                inicio = System.currentTimeMillis();
                try {
                    int indice = buscaBinaria(elementoAleatorio);
                    fim = System.currentTimeMillis();
                    System.out.println("Elemento encontrado no índice " + indice);
                } catch (ElementoNaoEncontradoException e) {
                    fim = System.currentTimeMillis();
                    System.out.println(e.getMessage());
                }
                System.out.println("Tempo de busca binária: " + (fim - inicio) + "ms");
                break;
            case 3:
                inicio = System.currentTimeMillis();
                bubbleSort();
                fim = System.currentTimeMillis();
                System.out.println("Vetor ordenado com Bubble Sort em " + (fim - inicio) + "ms");
                break;
            case 4:
                inicio = System.currentTimeMillis();
                selectionSort();
                fim = System.currentTimeMillis();
                System.out.println("Vetor ordenado com Selection Sort em " + (fim - inicio) + "ms");
                break;
            case 5:
                inicio = System.currentTimeMillis();
                insertionSort();
                fim = System.currentTimeMillis();
                System.out.println("Vetor ordenado com Insertion Sort em " + (fim - inicio) + "ms");
                break;
            case 6:
                System.out.println("Saindo...");
                return -1;
            default:
                System.out.println("Opção inválida");
        }

        return opcao;
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