package personagens;

import enums.ResultadoAtaque;
import principal.Log;

import java.util.concurrent.ThreadLocalRandom;

public class Goblin extends Monster{
    private int ultimoDano;

    public Goblin() {
        super("Goblin", 40, 60, 10, 15, 4, 8, 12, 18, 12, 16);
    }

    @Override
    public ResultadoAtaque realizarAtaque(Player alvo) {
        int chance = ThreadLocalRandom.current().nextInt(100);
        if (chance < 30 - alvo.getDestreza()/10) {
            return ResultadoAtaque.ERROU;
        }

        int dano = ataque - (alvo.getDefesa() / 4);
        if (chance > 90) {
            dano *= 1.5;
            alvo.setHp(alvo.getHp() - dano);
            return ResultadoAtaque.CRITICAL_HIT;
        }

        alvo.setHp(alvo.getHp() - dano);
        return ResultadoAtaque.ACERTOU;
    }

    @Override
    public ResultadoAtaque usar(Player alvo, Log log) {
        log.registrar(getNome() + " se esconde nas sombras!");

        // Aumenta destreza e velocidade temporariamente
        int destrezaAntes = getDestreza();
        int velocidadeAntes = getVelocidade();

        setDestreza(getDestreza() + 10);
        setVelocidade(getVelocidade() + 5);

        log.registrar("Destreza aumentada de " + destrezaAntes + " para " + getDestreza() + "!");
        log.registrar("Velocidade aumentada de " + velocidadeAntes + " para " + getVelocidade() + "!");

        return realizarAtaque(alvo);
    }

    public void setDestreza(int destreza) {
        this.destreza = destreza;
    }

    public void setVelocidade(int velocidade) {
        this.velocidade = velocidade;
    }
}
