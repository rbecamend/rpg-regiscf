package personagens;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import principal.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Esqueleto extends Monster implements HabilidadeEspecial {
    private int ultimoDano;

    public Esqueleto() {
        super("Esqueleto", 50, 80, 12, 18, 6, 10, 8, 14, 7, 11);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int chance = ThreadLocalRandom.current().nextInt(100);
        if (chance < 25 - alvo.getDestreza()/10) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 3);
        alvo.setHp(alvo.getHp() - dano);
        return ResultadoAtaque.ACERTOU;
    }

    @Override
    public ResultadoAtaque usar(Player alvo, Log log) {
        log.registrar(getNome() + " solta um GRITO ASSUSTADOR!");

        // Reduz ataque e defesa do alvo
        int ataqueAntes = alvo.getAtaque();
        int defesaAntes = alvo.getDefesa();

        alvo.setAtaque(Math.max(1, alvo.getAtaque() - 5));
        alvo.setDefesa(Math.max(1, alvo.getDefesa() - 3));

        log.registrar(alvo.getNome() + " perde 5 de ataque (de " + ataqueAntes + " para " + alvo.getAtaque() + ")");
        log.registrar(alvo.getNome() + " perde 3 de defesa (de " + defesaAntes + " para " + alvo.getDefesa() + ")");

        return realizarAtaque(alvo);
    }
}
