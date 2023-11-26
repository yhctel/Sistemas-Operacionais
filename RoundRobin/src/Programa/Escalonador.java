package Programa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Classe principal do escalonador
public class Escalonador {
    private static List<Processo> tabelaProcessos = new ArrayList<>();
    private static int quantum;

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);

        // Loop principal do programa
        while (true) {
            limparTela();
            System.out.println("---Escalonamento - Round Robin---\n");

            // Adiciona proteção para entrada do tamanho do Quantum
            while (true) {
                try {
                    System.out.print("Informe o tamanho do Quantum (em segundos): ");
                    quantum = ler.nextInt();

                    if (quantum <= 0) {
                        System.out.println("O Quantum deve ser um número positivo maior que zero.");
                        continue;
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Por favor, informe um número válido para o Quantum.");
                    ler.nextLine(); // Limpar o buffer do scanner
                }
            }

            // Loop do menu
            while (true) {
                System.out.println("\nMENU:");
                System.out.println("1. Criar novo processo");
                System.out.println("2. Verificar tabela de processos");
                System.out.println("3. Executar processos");
                System.out.println("4. Sair\n");

                // Adiciona proteção para entrada do menu
                int opcao = 0;
                while (true) {
                    try {
                        opcao = ler.nextInt();
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Por favor, informe um número válido para a opção do menu.");
                        ler.nextLine(); // Limpar o buffer do scanner
                    }
                }

                switch (opcao) {
                    case 1:
                        limparTela();
                        criarProcesso(ler);
                        break;
                    case 2:
                        if (!tabelaProcessos.isEmpty()) {
                            mostrarTabelaProcessos(ler);
                        } else {
                            limparTela();
                            System.out.println("Nenhum processo adicionado.");
                            pausa(0.7);
                        }
                        break;
                    case 3:
                        if (!tabelaProcessos.isEmpty()) {
                            executarProcessos(ler);
                        } else {
                            limparTela();
                            System.out.println("Nenhum processo para executar.");
                            pausa(0.7);
                        }
                        break;
                    case 4:
                        System.out.println("Encerrando o programa.");
                        ler.close();
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida.");
                        pausa(0.7);
                }
            }
        }
    }

    // Método para criar um novo processo
    private static void criarProcesso(Scanner ler) {
        System.out.print("Nome do processo: ");
        String nome = ler.next();

        // Adiciona proteção para entrada do tamanho do processo
        int tempoTotal = 0;
        while (true) {
            try {
                System.out.print("Tamanho do processo (em segundos): ");
                tempoTotal = ler.nextInt();

                if (tempoTotal <= 0) {
                    System.out.println("O tamanho do processo deve ser um número positivo maior que zero.");
                    continue;
                }

                break;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, informe um número válido para o tamanho do processo.");
                ler.nextLine(); // Limpar o buffer do scanner
            }
        }

        // Verifica se o nome já está em uso e adiciona sufixo numérico, se necessário
        int sufixo = 1;
        String nomeOriginal = nome;
        while (nomeJaEstaEmUso(nome)) {
            nome = nomeOriginal + sufixo;
            sufixo++;
        }

        Processo novoProcesso = new Processo(nome, tempoTotal);
        tabelaProcessos.add(novoProcesso);
        System.out.println("Processo '" + nome + "' criado e pronto.");
        ler.nextLine();
    }

    // Método para verificar se o nome do processo já está em uso
    private static boolean nomeJaEstaEmUso(String nome) {
        for (Processo processo : tabelaProcessos) {
            if (processo.getNome().equals(nome)) {
                return true;
            }
        }
        return false;
    }

    // Método para executar os processos
    private static void executarProcessos(Scanner ler) {
        int currentIndex = 0;
        int processosEncerrados = 0;
        boolean adicionouNovoProcesso = false;

        // Loop de execução dos processos
        while (processosEncerrados < tabelaProcessos.size() || adicionouNovoProcesso) {
            if (!adicionouNovoProcesso && currentIndex < tabelaProcessos.size()) {
                Processo processo = tabelaProcessos.get(currentIndex);

                if (!processo.estaEncerrado()) {
                    processo.setStatus("Em Execução");
                    System.out.println("Processo '" + processo.getNome() + "' está em execução.");
                    pausa(0.7); // Adiciona uma pausa de 0.7 segundos

                    int tempoRestante = processo.executar(quantum);
                    mostrarTabelaProcessos(ler);

                    if (tempoRestante == 0) {
                        processosEncerrados++;
                    }

                    pausa(0.7); // Adiciona uma pausa de 0.7 segundos
                }

                currentIndex = (currentIndex + 1) % tabelaProcessos.size();
            }

            // Verifica se o usuário deseja adicionar um novo processo durante a execução
            System.out.println("Deseja adicionar um novo processo? (1 - Sim / 0 - Não)\n");
            int adicionarNovoProcesso = ler.nextInt();

            if (adicionarNovoProcesso == 1) {
                criarProcesso(ler);
                adicionouNovoProcesso = true;
            } else {
                adicionouNovoProcesso = false;
            }
        }

        System.out.println("Todos os processos foram encerrados.");
        System.out.println("Pressione ENTER para voltar.");
        ler.nextLine();
        ler.nextLine();
    }

    // Método para adicionar uma pausa de tempo
    private static void pausa(double segundos) {
        try {
            Thread.sleep((long) (segundos * 1000)); // Converte segundos para milissegundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para mostrar a tabela de processos
    private static void mostrarTabelaProcessos(Scanner ler) {
        if (tabelaProcessos.isEmpty()) {
            System.out.println("Nenhum processo adicionado.");
        } else {
            System.out.println("\nTabela de Processos:");
            System.out.println("--------------------------------------------------------------");
            System.out.printf("%-15s%-15s%-15s%-15s\n", "Nome", "Tempo Total", "Tempo Restante", "Status");
            System.out.println("--------------------------------------------------------------");

            for (Processo processo : tabelaProcessos) {
                String nome = processo.getNome();
                int tempoTotal = processo.getTempoTotal();
                int tempoRestante = processo.getTempoRestante();
                String status = processo.getStatus();

                System.out.printf("%-15s%-15s%-15s%-15s\n", nome, tempoTotal, tempoRestante, status);
            }

            System.out.println("--------------------------------------------------------------");
        }
    }

    // Método para limpar a tela do console
    private static void limparTela() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// Classe para representar um processo
class Processo {
    private String nome;
    private int tempoTotal;
    private int tempoRestante;
    private String status;

    // Construtor para inicializar um processo
    public Processo(String nome, int tempoTotal) {
        this.nome = nome;
        this.tempoTotal = tempoTotal;
        this.tempoRestante = tempoTotal;
        this.status = "Pronto";
    }

    // Métodos para obter informações sobre o processo
    public String getNome() {
        return nome;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    public int getTempoRestante() {
        return tempoRestante;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Verifica se o processo está encerrado
    public boolean estaEncerrado() {
        return tempoRestante == 0;
    }

    // Executa o processo por um quantum de tempo
    public int executar(int quantum) {
        if (tempoRestante <= quantum) {
            tempoRestante = 0;
            status = "Encerrado";
            return 0;
        } else {
            tempoRestante -= quantum;
            return tempoRestante;
        }
    }
}
