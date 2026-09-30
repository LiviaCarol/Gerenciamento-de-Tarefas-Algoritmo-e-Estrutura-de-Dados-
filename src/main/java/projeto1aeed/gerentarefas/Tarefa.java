package projeto1aeed.gerentarefas;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Tarefa {
    private final int id;
    private String titulo;
    private String prioridades;
    private LocalDate prazo;
    private boolean concluida;
    
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Tarefa(int id, String titulo, String prioridades, LocalDate prazo, boolean concluida) {
        this.id = id;
        this.titulo = titulo;
        this.prioridades = prioridades;
        this.prazo = prazo;
        this.concluida = false;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPrioridades() {
        return prioridades;
    }

    public void setPrioridades(String prioridades) {
        this.prioridades = prioridades;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }
    
    @Override
    public String toString(){
        String statusStr = concluida ? "[X] Concluida" : "[] Pendente";
        String prazoStr = prazo != null ? prazo.format(FORMATO_DATA) : "Sem prazo";
        return String.format("ID: %-4d | %-20s | Prioridade: %-6s | Prazo: %-10s | Status: %s",
                id, titulo, prioridades, prazoStr, statusStr);
    }
}
