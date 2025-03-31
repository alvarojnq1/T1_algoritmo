import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class TesteVetor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEscolha o tamanho do vetor:");
            System.out.println("1-100.000 elementos");
            System.out.println("2-200.000 elementos");
            System.out.println("3-400.000 elementos");
            System.out.println("4-800.000 elementos");
            System.out.println("5-1.600.000 elementos");
            System.out.println("6-Sair");
            System.out.print("Opção: ");

            int opcaoVetor = scanner.nextInt();

            if (opcaoVetor == 6) {
                System.out.println("Programa encerrado.");
                scanner.close();
                return;
            }

            int tamanho = obterTamanhoVetor(opcaoVetor);
            int repeticoes = (tamanho < 1000000) ? 30 : 10;

            System.out.println("\nEscolha o algoritmo de ordenação:");
            System.out.println("1-Bubble Sort");
            System.out.println("2-Selection Sort");
            System.out.println("3-Insertion Sort");
            System.out.println("4-Voltar");
            System.out.print("Opção: ");

            int opcaoOrdenacao = scanner.nextInt();
            if (opcaoOrdenacao == 4)
                continue;

            System.out.println("\nExecutando " + repeticoes + " repetições para vetor de " + tamanho + " elementos...");

            // Nome do arquivo baseado no tamanho e algoritmo
            String nomeArquivo = "estatisticas_" + tamanho + "_" +
                    getNomeAlgoritmo(opcaoOrdenacao) + ".txt";

            // Executa e salva as estatísticas
            executarESalvarEstatisticas(tamanho, repeticoes, opcaoOrdenacao, nomeArquivo);
        }
    }

    private static String getNomeAlgoritmo(int opcao) {
        switch (opcao) {
            case 1:
                return "BubbleSort";
            case 2:
                return "SelectionSort";
            case 3:
                return "InsertionSort";
            default:
                return "Outro";
        }
    }

    private static void executarESalvarEstatisticas(int tamanho, int repeticoes, int ordenacao, String nomeArquivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(nomeArquivo))) {
            writer.println("=== ESTATÍSTICAS DE EXECUÇÃO ===");
            writer.println("Tamanho do vetor: " + tamanho + " elementos");
            writer.println("Algoritmo de ordenação: " + getNomeAlgoritmo(ordenacao));
            writer.println("Número de repetições: " + repeticoes);
            writer.println("\n-----------------------------------");

            for (int i = 1; i <= repeticoes; i++) {
                writer.println("\n[EXECUÇÃO " + i + "]");

                NossoVetor vetor = new NossoVetor(tamanho);
                vetor.preencheVetor();

                Random random = new Random();
                int elementoAleatorio = vetor.getOcupacao() > 0 ? vetor.getVetor()[random.nextInt(vetor.getOcupacao())]
                        : 0;

                writer.println("\nElemento pesquisado: " + elementoAleatorio);

                // Bloco 1: Busca Linear
                vetor.buscaSimples(elementoAleatorio);
                writer.println("\nBUSCA LINEAR:");
                writer.println("Operações realizadas: " + vetor.getComparacoes());

                // Bloco 2: Ordenação
                vetor.resetContadores();
                // Substituição do switch por if-else
                if (ordenacao == 1) {
                    vetor.bubbleSort();
                    writer.println("\nBUBBLE SORT:");
                } else if (ordenacao == 2) {
                    vetor.selectionSort();
                    writer.println("\nSELECTION SORT:");
                } else if (ordenacao == 3) {
                    vetor.insertionSort();
                    writer.println("\nINSERTION SORT:");
                }
                writer.println("Operações realizadas: " + vetor.getComparacoes() + vetor.getTrocas());

                // Bloco 3: Busca Binária
                vetor.resetContadores();
                vetor.buscaBinaria(elementoAleatorio);
                writer.println("\nBUSCA BINÁRIA:");
                writer.println("Operações realizadas: " + vetor.getComparacoes());

                writer.println("\n-----------------------------------");

                // Pausa entre execuções
                if (i < repeticoes) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
            System.out.println("Estatísticas salvas em: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    private static int obterTamanhoVetor(int opcao) {
        switch (opcao) {
            case 1:
                return 100000;
            case 2:
                return 200000;
            case 3:
                return 400000;
            case 4:
                return 800000;
            case 5:
                return 1600000;
            default:
                return 100000;
        }
    }
}