package personagens;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import principal.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Demonio extends Monster implements HabilidadeEspecial {
    private int ultimoDano;

    public Demonio() {
        super("Demônio", 120, 150, 22, 28, 14, 20, 12, 18, 12, 16);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int chance = ThreadLocalRandom.current().nextInt(100);
        if (chance < 15 - alvo.getDestreza()/10) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 3);
        if (chance > 75) {
            // Ataque de fogo que queima por vários turnos
            dano *= 1.8;
            alvo.setHp(alvo.getHp() - dano);
            return ResultadoAtaque.CRITICAL_HIT;
        }

        alvo.setHp(alvo.getHp() - dano);
        return ResultadoAtaque.ACERTOU;
    }

    @Override
    public ResultadoAtaque usar(Player alvo, Log log) {
        log.registrar(getNome() + " invoca o INFERNO!");

        int dano = (int)(ataque * 2.5) - (alvo.getDefesa() / 3);
        dano = Math.max(20, dano);

        alvo.setHp(alvo.getHp() - dano);
        ultimoDano = dano;

        // Efeito adicional de queimadura
        int queimadura = ThreadLocalRandom.current().nextInt(5, 11);
        log.registrar(alvo.getNome() + " sofre queimadura de " + queimadura + " por turno!");

        return ResultadoAtaque.CRITICAL_HIT;
    }
}
