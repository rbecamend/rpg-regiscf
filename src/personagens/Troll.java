package personagens;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import principal.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Troll extends Monster implements HabilidadeEspecial {
    private int ultimoDano;

    public Troll() {
        super("Troll", 160, 200, 16, 22, 12, 18, 4, 8, 4, 7);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int chance = ThreadLocalRandom.current().nextInt(100);
        if (chance < 35 - alvo.getDestreza()/8) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 3);
        // Regeneração
        setHp(getHp() + randomInRange(5, 10));
        alvo.setHp(alvo.getHp() - dano);
        return ResultadoAtaque.ACERTOU;
    }

    @Override
    public ResultadoAtaque usar(Player alvo, Log log) {
        log.registrar(getNome() + " ativa sua REGENERAÇÃO RÁPIDA!");

        int cura = ThreadLocalRandom.current().nextInt(20, 35);
        int hpAntes = getHp();
        setHp(Math.min(getMaxHp(), getHp() + cura));
        int curado = getHp() - hpAntes;

        log.registrar(getNome() + " recupera " + curado + " pontos de vida!");
        return realizarAtaque(alvo);
    }
}
