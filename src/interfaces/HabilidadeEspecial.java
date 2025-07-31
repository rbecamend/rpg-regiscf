package interfaces;

import enums.ResultadoAtaque;
import personagens.Player;
import principal.Log;

public interface HabilidadeEspecial {
    ResultadoAtaque usar(Player alvo, Log log);
}
