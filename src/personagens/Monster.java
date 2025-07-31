package personagens;

import enums.ResultadoAtaque;
import principal.Log;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public abstract class Monster extends Player{
    public Monster(String nome, int minHp, int maxHp, int minAtaque, int maxAtaque,
                   int minDefesa, int maxDefesa, int minDestreza, int maxDestreza,
                   int minVelocidade, int maxVelocidade) {
        super(nome, minHp, maxHp, minAtaque, maxAtaque, minDefesa, maxDefesa,
                minDestreza, maxDestreza, minVelocidade, maxVelocidade);
    }

    public Player escolherAlvo(List<Hero> herois) {
        // Combina diferentes estratégias de IA
        Random rand = new Random();
        int estrategia = rand.nextInt(3);

        switch (estrategia) {
            case 0: // Menor HP
                return herois.stream()
                        .filter(h -> h.getHp() > 0)
                        .min(Comparator.comparingInt(Player::getHp))
                        .orElse(null);
            case 1: // Menor defesa
                return herois.stream()
                        .filter(h -> h.getHp() > 0)
                        .min(Comparator.comparingInt(Player::getDefesa))
                        .orElse(null);
            default: // Aleatório
                List<Hero> vivos = herois.stream()
                        .filter(h -> h.getHp() > 0)
                        .collect(Collectors.toList());
                if (vivos.isEmpty()) return null;
                return vivos.get(rand.nextInt(vivos.size()));
        }
    }

    public abstract ResultadoAtaque usar(Player alvo, Log log);
}
