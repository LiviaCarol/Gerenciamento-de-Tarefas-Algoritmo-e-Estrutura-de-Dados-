package projeto1aeed.gerentarefas;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GerenciadorTarefas {
    private final Map<Integer, Tarefa> tarefas = new LinkedHashMap<>();
    private int contadorId = 1;
    
    public Tarefa cadastrar(String titulo, String prioridades, LocalDate prazo){
        if (titulo == null || titulo.trim().isEmpty()){
            throw new IllegalArgumentException("O título da tarefa não pode está vazio");
        }
        int novoId = contadorId++;
        Tarefa novaTarefa = new Tarefa(novoId, titulo.trim(), prioridades, prazo, false);
        tarefas.put(novoId, novaTarefa);
        return novaTarefa;
    }
    public List<Tarefa> listarTodas(){
        return new ArrayList<>(tarefas.values());
    }
    public Tarefa buscarPorId(int id){
        return tarefas.get(id);
    }
    public boolean atualizar(int id, String novoTitulo, String novaPrioridade, LocalDate novoPrazo,
            Boolean concluida){
        Tarefa tarefa = tarefas.get(id);
        if (tarefa == null){
            return false;
        }
        if (novoTitulo != null && !novoTitulo.trim().isEmpty()){
            tarefa.setTitulo(novoTitulo.trim());
        }
        if (novaPrioridade != null && !novaPrioridade.trim().isEmpty()){
            tarefa.setPrioridades(novaPrioridade.trim());
        }
        if (novoPrazo != null){
            tarefa.setPrazo(novoPrazo);
        }
        if (concluida != null){
            tarefa.setConcluida(concluida);
        }
        return true;
    }
    public boolean remover(int id){
        return tarefas.remove(id) != null;
    }
    public boolean estaVazio(){
        return tarefas.isEmpty();
    }
}
