package personagens;

import enums.ResultadoAtaque;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Player {
    protected String nome;
    protected int hp;
    protected int maxHp;
    protected int ataque;
    protected int defesa;
    protected int destreza;
    protected int velocidade;
    protected int ultimoDano;

    public Player(String nome, int minHp, int maxHp, int minAtaque, int maxAtaque,
                  int minDefesa, int maxDefesa, int minDestreza, int maxDestreza,
                  int minVelocidade, int maxVelocidade) {
        this.nome = nome;
        this.maxHp = randomInRange(minHp, maxHp);
        this.hp = this.maxHp;
        this.ataque = randomInRange(minAtaque, maxAtaque);
        this.defesa = randomInRange(minDefesa, maxDefesa);
        this.destreza = randomInRange(minDestreza, maxDestreza);
        this.velocidade = randomInRange(minVelocidade, maxVelocidade);
    }

    int randomInRange(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public String getNome() { return nome; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAtaque() { return ataque; }
    public int getDefesa() { return defesa; }
    public int getDestreza() { return destreza; }
    public int getVelocidade() { return velocidade; }

    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(hp, maxHp));
    }

    public int getUltimoDano() { return ultimoDano; }

    public void setAtaque(int ataque) {this.ataque = ataque;}

    public void setDefesa(int defesa) {this.defesa = defesa;}

    public abstract ResultadoAtaque realizarAtaque(Player alvo);

    @Override
    public String toString() {
        return String.format("%s - HP: %d/%d | ATK: %d | DEF: %d | DES: %d | VEL: %d",
                nome, hp, maxHp, ataque, defesa, destreza, velocidade);
    }
}
