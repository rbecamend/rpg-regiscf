package personagens;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import principal.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Barbaro extends Hero implements HabilidadeEspecial {
    private int ultimoDano;

    public Barbaro() {
        super("Bárbaro", 130, 160, 18, 25, 8, 14, 6, 10, 9, 13);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int chance = ThreadLocalRandom.current().nextInt(100);
        if (chance < 25 - alvo.getDestreza()/8) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 4);
        if (chance > 70) {
            dano *= 2;
            // Toma dano proporcional ao ataque
            setHp(getHp() - (int)(dano * 0.2));
            alvo.setHp(alvo.getHp() - dano);
            return ResultadoAtaque.CRITICAL_HIT;
        }

        alvo.setHp(alvo.getHp() - dano);
        return ResultadoAtaque.ACERTOU;
    }

    @Override
    public ResultadoAtaque usar(Player alvo, Log log) {
        log.registrar(getNome() + " entra em FÚRIA BÁRBARA!");

        // Aumenta ataque mas reduz defesa
        int ataqueAntes = getAtaque();
        int defesaAntes = getDefesa();

        setAtaque(getAtaque() + 15);
        setDefesa(Math.max(1, getDefesa() - 5));

        int dano = (int)(ataque * 2.2) - (alvo.getDefesa() / 4);
        alvo.setHp(alvo.getHp() - dano);
        ultimoDano = dano;

        log.registrar("Ataque aumentado de " + ataqueAntes + " para " + getAtaque() + "!");
        log.registrar("Defesa reduzida de " + defesaAntes + " para " + getDefesa() + "!");
        log.registrar("Causou " + dano + " de dano em " + alvo.getNome() + "!");

        return ResultadoAtaque.CRITICAL_HIT;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public void setDefesa(int defesa) {
        this.defesa = defesa;
    }
}
