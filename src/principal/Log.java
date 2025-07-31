package principal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private final List<String> entradas = new ArrayList<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public void registrar(String mensagem) {
        String timestamp = LocalDateTime.now().format(formatter);
        entradas.add("[" + timestamp + "] " + mensagem);
    }

    public void imprimirLog() {
        System.out.println("\n===== LOG COMPLETO DA BATALHA =====");
        entradas.forEach(System.out::println);
    }

    public List<String> getLogs() {
        return Collections.unmodifiableList(entradas);
    }
}
