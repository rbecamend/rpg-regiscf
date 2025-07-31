package personagens;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import principal.Log;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Mago extends Hero implements HabilidadeEspecial {
    private int ultimoDano;

    public Mago() {
        super("Mago", 70, 90, 20, 28, 5, 10, 10, 15, 8, 12);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int hpAntes = alvo.getHp();
        int chance = ThreadLocalRandom.current().nextInt(100);

        if (chance < 20 - alvo.getDestreza()/10) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 4);
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
        log.registrar(getNome() + " lança BOLA DE FOGO!");
        int dano = (int)(ataque * 1.8) - (alvo.getDefesa() / 4);
        dano = Math.max(8, dano);

        alvo.setHp(alvo.getHp() - dano);
        ultimoDano = dano;

        // Efeito visual adicional
        log.registrar("Uma explosão flamejante atinge " + alvo.getNome() + "!");
        return ResultadoAtaque.CRITICAL_HIT;
    }

    public int getUltimoDano() {
        return ultimoDano;
    }
}