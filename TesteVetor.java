import java.util.Scanner;

public class TesteVetor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Qual vetor você deseja?\n1-100K\n2-200K\n3-400K\n4-800K\n5-1,6M\n6-Sair");
            int opcaoVetor = scanner.nextInt();

            if (opcaoVetor == 6) {
                System.out.println("Programa encerrado.");
                return;
            }

            int tamanho = obterTamanhoVetor(opcaoVetor);
            NossoVetor vetor = new NossoVetor(tamanho);
            vetor.preencheVetor();

            boolean continuar = true;
            while (continuar) {
                int opcaoMenu = vetor.Menu();
                if (opcaoMenu == 6) {
                    continuar = false;
                }
            }
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
}
