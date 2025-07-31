package personagens;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import principal.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Dragao extends Monster implements HabilidadeEspecial {
    private int ultimoDano;

    public Dragao() {
        super("Dragão", 140, 180, 20, 30, 15, 22, 8, 14, 8, 12);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int hpAntes = alvo.getHp();
        int chance = ThreadLocalRandom.current().nextInt(100);

        if (chance < 15 - alvo.getDestreza()/10) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 2);
        dano = Math.max(1, dano);

        if (chance > 85) {
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
        log.registrar(getNome() + " usa SOPRO DE FOGO INFERNAL!");
        int dano = (int)(ataque * 2.2) - (alvo.getDefesa() / 3);
        dano = Math.max(10, dano);

        // Queimadura contínua
        int queimadura = ThreadLocalRandom.current().nextInt(5, 11);
        log.registrar(alvo.getNome() + " sofre queimadura de " + queimadura + " por turno!");
        // (Sistema de status precisaria ser implementado)

        alvo.setHp(alvo.getHp() - dano);
        ultimoDano = dano;
        return ResultadoAtaque.CRITICAL_HIT;
    }

    public int getUltimoDano() {
        return ultimoDano;
    }
}