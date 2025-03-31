import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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
            
            System.out.println("\nExecutando " + repeticoes + " repetições automáticas para vetor de " + tamanho + " elementos...");
            
            // Executa automaticamente todas as operações
            executarTestesAutomaticos(tamanho, repeticoes);
        }
    }
    
    private static int obterTamanhoVetor(int opcao) {
        switch (opcao) {
            case 1: return 100000;
            case 2: return 200000;
            case 3: return 400000;
            case 4: return 800000;
            case 5: return 1600000;
            default: return 100000;
        }
    }
    
    private static void executarTestesAutomaticos(int tamanho, int repeticoes) {
        for (int i = 1; i <= repeticoes; i++) {
            System.out.println("\n--- Execução " + i + " de " + repeticoes + " ---");
            
            NossoVetor vetor = new NossoVetor(tamanho);
            vetor.preencheVetor();
            
            // Executa todas as operações automaticamente
            vetor.executarOperacoesAutomaticas();
            
            // Pausa entre execuções
            if (i < repeticoes) {
                try {
                    System.out.println("\nAguardando 2 segundos antes da próxima execução...");
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}