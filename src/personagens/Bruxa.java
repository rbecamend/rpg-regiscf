package personagens;

import enums.ResultadoAtaque;
import interfaces.HabilidadeEspecial;
import principal.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Bruxa extends Monster implements HabilidadeEspecial {
    private int ultimoDano;

    public Bruxa() {
        super("Bruxa", 70, 100, 18, 25, 8, 12, 14, 20, 10, 14);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int hpAntes = alvo.getHp();
        int chance = ThreadLocalRandom.current().nextInt(100);

        if (chance < 20 - alvo.getDestreza()/10) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 4);
        if (chance > 80) {
            dano = (int)(ataque * 0.8) + (ataque - alvo.getDefesa() / 2);
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
        log.registrar(getNome() + " lança MALDIÇÃO DA FRAQUEZA!");
        int reducao = ThreadLocalRandom.current().nextInt(3, 8);

        // Reduz ataque do alvo
        int ataqueAntes = alvo.getAtaque();
        alvo.setAtaque(Math.max(1, alvo.getAtaque() - reducao));
        log.registrar(alvo.getNome() + " perde " + reducao + " pontos de ataque!");

        // Ataque normal após a maldição
        return realizarAtaque(alvo);
    }

    public int getUltimoDano() {
        return ultimoDano;
    }
}