package principal;

import enums.ResultadoAtaque;
import personagens.Hero;
import personagens.Monster;
import personagens.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Turno {
    private final List<Player> jogadores;
    private final Log log;

    public Turno(List<Player> jogadores, Log log) {
        this.jogadores = new ArrayList<>(jogadores);
        this.log = log;
        ordenarPorVelocidade();
    }

    private void ordenarPorVelocidade() {
        jogadores.sort(Comparator.comparingInt(Player::getVelocidade).reversed());
    }

public void executar(List<Hero> herois, List<Monster> monstros) {
    Scanner scanner = new Scanner(System.in);
    
    for (Player jogador : jogadores) {
        if (jogador.getHp() <= 0) continue;  // Ignora jogadores mortos
        
        if (jogador instanceof Hero hero) {
            if (monstros.isEmpty()) continue;

            System.out.println("\n\n----- Turno de " + hero.getNome() + " -----");
            System.out.println("Escolha um monstro para atacar:");

            // Exibir os monstros restantes
            for (int i = 0; i < monstros.size(); i++) {
                Monster monstro = monstros.get(i);
                if (monstro.getHp() > 0) {
                    System.out.println((i + 1) + ". " + monstro.getNome() + " - HP: " + monstro.getHp());
                }
            }
            
            int escolhaMonstro = -1;
            while (escolhaMonstro < 1 || escolhaMonstro > monstros.size()) {
                System.out.print("Digite o número do monstro a atacar: ");
                escolhaMonstro = scanner.nextInt();
                if (escolhaMonstro < 1 || escolhaMonstro > monstros.size() || monstros.get(escolhaMonstro - 1).getHp() <= 0) {
                    System.out.println("Escolha inválida! Tente novamente.");
                }
            }
            
            Monster alvo = monstros.get(escolhaMonstro - 1);
            System.out.println("Escolhido: " + alvo.getNome());

            // Escolher ataque
            System.out.println("Escolha o ataque para " + hero.getNome() + ":");
            System.out.println("1. Ataque Básico");
            System.out.println("2. Habilidade Especial");
            int escolhaAtaque = scanner.nextInt();

            // Realizar ataque
            ResultadoAtaque resultado = null;
            switch (escolhaAtaque) {
                case 1:
                    resultado = hero.realizarAtaque(alvo);
                    break;
                case 2:
                    // Chama a habilidade especial
                    break;
                default:
                    System.out.println("Opção inválida! Realizando ataque básico.");
                    resultado = hero.realizarAtaque(alvo);
                    break;
            }

            // Registrar resultado do ataque
            log.registrar(gerarMensagemAtaque(hero, alvo, resultado));

        } else if (jogador instanceof Monster monstro) {
            if (herois.isEmpty()) continue;

            // Turno do monstro (continua sendo automático)
            Player alvo = monstro.escolherAlvo(herois);
            if (alvo != null && alvo.getHp() > 0) {
                ResultadoAtaque resultado = monstro.realizarAtaque(alvo);
                log.registrar(gerarMensagemAtaque(monstro, alvo, resultado));
            }
        }
    }
}

    private String gerarMensagemAtaque(Player atacante, Player alvo, ResultadoAtaque resultado) {
        String mensagem = atacante.getNome() + " ataca " + alvo.getNome();
        return switch (resultado) {
            case ERROU -> mensagem + " e errou!";
            case ACERTOU -> mensagem + " e causou dano! HP restante: " + alvo.getHp();
            case CRITICAL_HIT -> mensagem + " com CRÍTICO! HP restante: " + alvo.getHp();
            default -> mensagem;
        };
    }
}
