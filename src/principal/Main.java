package principal;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== DUNGEON DA PERDIÇÃO =====");
            System.out.println("Quatro heróis caíram em uma dungeon escura durante uma expedição na Floresta dos Sussurros.");
            System.out.println("Agora, cercados por criaturas sombrias, eles devem lutar para sobreviver e encontrar a saída!");
            System.out.println("1. Iniciar Jogo");
            System.out.println("2. Sair");
            System.out.print("Escolha: ");

            int escolha = scanner.nextInt();

            switch (escolha) {
                case 1:
                    iniciarJogo();
                    break;
                case 2:
                    System.out.println("Até a próxima aventura!");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void iniciarJogo() {
        Scanner scanner = new Scanner(System.in);
        int dificuldade;

        while (true) {
            System.out.print("\nSelecione a dificuldade (1-Fácil, 2-Médio, 3-Difícil): ");
            try {
                dificuldade = scanner.nextInt();
                if (dificuldade >= 1 && dificuldade <= 3) break;
                System.out.println("Dificuldade inválida! Escolha 1, 2 ou 3.");
            } catch (Exception e) {
                System.out.println("Entrada inválida! Digite um número.");
                scanner.next();
            }
        }

        Game jogo = new Game(dificuldade);
        jogo.selecionarHerois();
        jogo.iniciarJogo();

        System.out.println("\nDeseja jogar novamente? (S/N)");
        String replay = scanner.next();
        if (!replay.equalsIgnoreCase("S")) {
            System.out.println("Retornando ao menu principal...");
        }
    }
}
