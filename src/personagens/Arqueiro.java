package personagens;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import principal.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Arqueiro extends Hero implements HabilidadeEspecial {
    private int ultimoDano;

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

    @Override
    public ResultadoAtaque usar(Player alvo, Log log) {
        log.registrar(getNome() + " dispara uma CHUVA DE FLECHAS!");
        int dano = (int)(ataque * 2.0) - (alvo.getDefesa() / 5);
        dano = Math.max(10, dano);

        alvo.setHp(alvo.getHp() - dano);
        ultimoDano = dano;

        // Efeito adicional
        int flechasAdicionais = ThreadLocalRandom.current().nextInt(1, 4);
        log.registrar(flechasAdicionais + " flechas adicionais atingem o alvo!");

        return ResultadoAtaque.CRITICAL_HIT;
    }

}
