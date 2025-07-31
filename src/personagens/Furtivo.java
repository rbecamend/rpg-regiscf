package personagens;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import principal.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Furtivo extends Hero implements HabilidadeEspecial {
    private int ultimoDano;

    public Furtivo() {
        super("Furtivo", 75, 95, 20, 25, 6, 11, 18, 25, 15, 20);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int chance = ThreadLocalRandom.current().nextInt(100);
        if (chance < 5 - alvo.getDestreza()/15) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 6);
        if (chance > 80) {
            dano *= 2;
            alvo.setHp(alvo.getHp() - dano);
            return ResultadoAtaque.CRITICAL_HIT;
        }

        alvo.setHp(alvo.getHp() - dano);
        return ResultadoAtaque.ACERTOU;
    }

    @Override
    public ResultadoAtaque usar(Player alvo, Log log) {
        log.registrar(getNome() + " executa um ATAQUE FURTIVO!");
        int dano = (int)(ataque * 3.0) - (alvo.getDefesa() / 6);
        dano = Math.max(15, dano);

        alvo.setHp(alvo.getHp() - dano);
        ultimoDano = dano;

        // Efeito adicional
        log.registrar("O ataque surpreendeu o alvo, causando dano crítico!");
        return ResultadoAtaque.CRITICAL_HIT;
    }
}
