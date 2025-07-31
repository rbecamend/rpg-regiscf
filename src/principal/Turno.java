package principal;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import personagens.Hero;
import personagens.Monster;
import personagens.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

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
            if (jogador.getHp() <= 0) {
                log.registrar(jogador.getNome() + " está incapacitado e não pode agir!");
                continue;
            }

            if (jogador instanceof Hero hero) {
                if (monstros.isEmpty()) continue;

                System.out.println("\n\n----- Turno de " + hero.getNome() + " -----");
                System.out.println("HP: " + hero.getHp() + "/" + hero.getMaxHp());
                System.out.println("Escolha um monstro para atacar:");

                // Exibir monstros com índice
                for (int i = 0; i < monstros.size(); i++) {
                    Monster m = monstros.get(i);
                    if (m.getHp() > 0) {
                        System.out.printf("%d. %s - HP: %d/%d%n",
                                i+1, m.getNome(), m.getHp(), m.getMaxHp());
                    }
                }

                // Seleção de monstro
                Monster alvo = null;
                while (alvo == null) {
                    System.out.print("Digite o número do monstro: ");
                    try {
                        int escolha = scanner.nextInt() - 1;
                        if (escolha >= 0 && escolha < monstros.size()) {
                            alvo = monstros.get(escolha);
                            if (alvo.getHp() <= 0) {
                                System.out.println("Este monstro já está derrotado!");
                                alvo = null;
                            }
                        } else {
                            System.out.println("Número inválido!");
                        }
                    } catch (Exception e) {
                        System.out.println("Entrada inválida!");
                        scanner.next(); // Limpar buffer
                    }
                }

                System.out.println("Alvo selecionado: " + alvo.getNome());

                // Seleção de ação
                System.out.println("Escolha a ação:");
                System.out.println("1. Ataque Básico");
                System.out.println("2. Habilidade Especial");
                System.out.print("Opção: ");

                ResultadoAtaque resultado = null;
                try {
                    int acao = scanner.nextInt();

                    switch (acao) {
                        case 1:
                            resultado = hero.realizarAtaque(alvo);
                            break;

                        case 2:
                            if (hero instanceof HabilidadeEspecial) {
                                resultado = ((HabilidadeEspecial) hero).usar(alvo, log);
                            } else {
                                System.out.println("Habilidade não disponível! Usando ataque básico.");
                                resultado = hero.realizarAtaque(alvo);
                            }
                            break;

                        default:
                            System.out.println("Opção inválida! Usando ataque básico.");
                            resultado = hero.realizarAtaque(alvo);
                    }
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage() + " Usando ataque básico.");
                    resultado = hero.realizarAtaque(alvo);
                }

                // Registrar e mostrar resultado
                String mensagem = gerarMensagemAtaque(hero, alvo, resultado);
                log.registrar(mensagem);
                System.out.println("\n>>> " + mensagem);

            } else if (jogador instanceof Monster monstro) {
                if (herois.isEmpty()) continue;

                log.registrar("\n----- Turno de " + monstro.getNome() + " -----");
                Player alvo = monstro.escolherAlvo(herois);

                if (alvo != null && alvo.getHp() > 0) {
                    ResultadoAtaque resultado;

                    // 40% de chance de usar habilidade especial
                    if (ThreadLocalRandom.current().nextInt(100) < 40 &&
                            monstro instanceof HabilidadeEspecial) {

                        resultado = ((HabilidadeEspecial) monstro).usar(alvo, log);
                    } else {
                        resultado = monstro.realizarAtaque(alvo);
                    }

                    String mensagem = gerarMensagemAtaque(monstro, alvo, resultado);
                    log.registrar(mensagem);
                    System.out.println("\n>>> " + mensagem);
                }
            }

            // Pausa para leitura
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private String gerarMensagemAtaque(Player atacante, Player alvo, ResultadoAtaque resultado) {
        int hpAntes = alvo.getHp() + atacante.getUltimoDano(); // Recupera HP antes do ataque

        String mensagem = String.format("%s ataca %s: ", atacante.getNome(), alvo.getNome());

        return switch (resultado) {
            case ERROU -> mensagem + "ERROU!";
            case ACERTOU -> String.format("%sACERTOU! %d de dano. HP: %d/%d",
                    mensagem, atacante.getUltimoDano(),
                    alvo.getHp(), alvo.getMaxHp());
            case CRITICAL_HIT -> String.format("%sCRÍTICO! %d de dano. HP: %d/%d",
                    mensagem, atacante.getUltimoDano(),
                    alvo.getHp(), alvo.getMaxHp());
            default -> mensagem + "Ação desconhecida";
        };
    }
}