package personagens;

import enums.ResultadoAtaque;

import java.util.concurrent.ThreadLocalRandom;

public class Arqueiro extends Hero{
    public Arqueiro() {
        super("Arqueiro", 80, 100, 18, 24, 8, 12, 15, 20, 12, 16);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int chance = ThreadLocalRandom.current().nextInt(100);
        if (chance < 10 - alvo.getDestreza()/12) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 5);
        if (chance > 95) {
            dano *= 3;
            alvo.setHp(alvo.getHp() - dano);
            return ResultadoAtaque.CRITICAL_HIT;
        }

        alvo.setHp(alvo.getHp() - dano);
        return ResultadoAtaque.ACERTOU;
    }
}
