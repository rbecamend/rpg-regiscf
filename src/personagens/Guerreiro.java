package personagens;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import principal.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Guerreiro extends Hero implements HabilidadeEspecial {
    private int ultimoDano;

    public Guerreiro() {
        super("Guerreiro", 100, 130, 15, 20, 12, 18, 8, 12, 7, 10);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int hpAntes = alvo.getHp();
        int chance = ThreadLocalRandom.current().nextInt(100);

        if (chance < 15 - alvo.getDestreza()/10) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 3);
        dano = Math.max(1, dano);

        if (chance > 90) {
            dano *= 2;
            alvo.setHp(alvo.getHp() - dano);
            ultimoDano = dano;
            return ResultadoAtaque.CRITICAL_HIT;
        }

        alvo.setHp(alvo.getHp() - dano);
        ultimoDano = dano;
        return ResultadoAtaque.ACERTOU;
    }

    @Override
    public ResultadoAtaque usar(Player alvo, Log log) {
        log.registrar(getNome() + " usa FÚRIA DO GUERREIRO!");
        int dano = (int)(ataque * 2.5) - (alvo.getDefesa() / 2);
        dano = Math.max(5, dano);

        // Auto-buff de defesa
        int defesaAntes = defesa;
        defesa += 5;
        log.registrar("Defesa aumentada de " + defesaAntes + " para " + defesa + "!");

        alvo.setHp(alvo.getHp() - dano);
        ultimoDano = dano;
        return ResultadoAtaque.CRITICAL_HIT;
    }

    public int getUltimoDano() {
        return ultimoDano;
    }
}

