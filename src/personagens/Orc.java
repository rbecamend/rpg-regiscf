package personagens;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import principal.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Orc extends Monster implements HabilidadeEspecial {
    private int ultimoDano;

    public Orc() {
        super("Orc", 90, 120, 14, 20, 10, 15, 6, 10, 5, 8);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int chance = ThreadLocalRandom.current().nextInt(100);
        if (chance < 20 - alvo.getDestreza()/10) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 3);
        alvo.setHp(alvo.getHp() - dano);
        return ResultadoAtaque.ACERTOU;
    }

    @Override
    public ResultadoAtaque usar(Player alvo, Log log) {
        log.registrar(getNome() + " desfere um GOLPE ESMAGADOR!");

        int dano = (int)(ataque * 2.5) - (alvo.getDefesa() / 2);
        dano = Math.max(15, dano);

        alvo.setHp(alvo.getHp() - dano);
        ultimoDano = dano;

        log.registrar("O golpe violento causa " + dano + " de dano!");
        return ResultadoAtaque.CRITICAL_HIT;
    }
}
