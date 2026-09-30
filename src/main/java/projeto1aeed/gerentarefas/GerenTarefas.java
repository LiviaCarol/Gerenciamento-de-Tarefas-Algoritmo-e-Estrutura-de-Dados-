package projeto1aeed.gerentarefas;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class GerenTarefas {
    private static final GerenciadorTarefas gerenciador = new GerenciadorTarefas();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static void main(String[] args) {
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opcao: ");

            switch (opcao) {
                case 1 -> cadastrarTarefa();
                case 2 -> listarTarefas();
                case 3 -> buscarTarefa();
                case 4 -> atualizarTarefa();
                case 5 -> removerTarefa();
                case 0 -> System.out.println("\nSaindo da aplicacao. Ate mais!");
                default -> System.out.println("\n[X] Opcao invalida! Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("\n=================================");
        System.out.println("   GERENCIADOR DE TAREFAS");
        System.out.println("=================================");
        System.out.println("[1] Cadastrar nova tarefa");
        System.out.println("[2] Listar todas as tarefas");
        System.out.println("[3] Buscar tarefa por ID");
        System.out.println("[4] Atualizar tarefa");
        System.out.println("[5] Remover tarefa");
        System.out.println("[0] Sair");
        System.out.println("=================================");
    }

    private static void cadastrarTarefa() {
        System.out.println("\n--- Cadastrar Tarefa ---");
        System.out.print("Titulo: ");
        String titulo = scanner.nextLine();

        System.out.print("Prioridade (Baixa / Media / Alta): ");
        String prioridade = scanner.nextLine();

        LocalDate prazo = lerData("Prazo (dd/MM/yyyy ou Enter para nenhum): ");

        try {
            Tarefa t = gerenciador.cadastrar(titulo, prioridade, prazo);
            System.out.println("\n[✓] Tarefa cadastrada com sucesso! (ID: " + t.getId() + ")");
        } catch (IllegalArgumentException e) {
            System.out.println("\n[X] Erro ao cadastrar: " + e.getMessage());
        }
    }

    private static void listarTarefas() {
        System.out.println("\n--- Lista de Tarefas ---");
        if (gerenciador.estaVazio()) {
            System.out.println("Nenhuma tarefa cadastrada no momento.");
            return;
        }

        List<Tarefa> tarefas = gerenciador.listarTodas();
        for (Tarefa t : tarefas) {
            System.out.println(t);
        }
    }

    private static void buscarTarefa() {
        System.out.println("\n--- Buscar Tarefa ---");
        int id = lerInteiro("Informe o ID da tarefa: ");
        Tarefa t = gerenciador.buscarPorId(id);

        if (t != null) {
            System.out.println("\nTarefa encontrada:");
            System.out.println(t);
        } else {
            System.out.println("\n[X] Nenhuma tarefa encontrada com o ID " + id + ".");
        }
    }

    private static void atualizarTarefa() {
        System.out.println("\n--- Atualizar Tarefa ---");
        int id = lerInteiro("Informe o ID da tarefa a ser atualizada: ");

        Tarefa existente = gerenciador.buscarPorId(id);
        if (existente == null) {
            System.out.println("\n[X] Nenhuma tarefa encontrada com o ID " + id + ".");
            return;
        }

        System.out.println("Deixe o campo em branco caso não queira alterar seu valor atual.");
        
        System.out.print("Novo Titulo [" + existente.getTitulo() + "]: ");
        String titulo = scanner.nextLine();

        System.out.print("Nova Prioridade [" + existente.getPrioridades() + "]: ");
        String prioridade = scanner.nextLine();

        LocalDate prazo = lerData("Novo Prazo (dd/MM/yyyy) [deixe em branco para manter]: ");

        System.out.print("Status ([1] Concluida / [2] Pendente / [Enter] Manter atual): ");
        String statusInput = scanner.nextLine().trim();
        Boolean concluida = null;
        if (statusInput.equals("1")) concluida = true;
        else if (statusInput.equals("2")) concluida = false;

        boolean sucesso = gerenciador.atualizar(id, 
                titulo.isEmpty() ? null : titulo, 
                prioridade.isEmpty() ? null : prioridade, 
                prazo, 
                concluida);

        if (sucesso) {
            System.out.println("\n[✓] Tarefa atualizada com sucesso!");
        }
    }

    private static void removerTarefa() {
        System.out.println("\n--- Remover Tarefa ---");
        int id = lerInteiro("Informe o ID da tarefa que deseja remover: ");

        if (gerenciador.remover(id)) {
            System.out.println("\n[✓] Tarefa removida com sucesso!");
        } else {
            System.out.println("\n[X] Nenhuma tarefa encontrada com o ID " + id + ".");
        }
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("[X] Por favor, digite um número inteiro válido.");
            }
        }
    }

    private static LocalDate lerData(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            if (entrada.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(entrada, FORMATO_DATA);
            } catch (DateTimeParseException e) {
                System.out.println("[X] Formato de data inválido! Use o formato dd/MM/yyyy.");
            }
        }
    }
}
