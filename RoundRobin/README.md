# Escalonador Round Robin

Este é um programa simples de escalonamento de processos implementado em Java, utilizando o algoritmo Round Robin. O Round Robin é uma abordagem de escalonamento de CPU que atribui a cada processo um quantum de tempo para execução, passando para o próximo processo na fila após o término desse intervalo.

## Funcionalidades

- **Criar Novo Processo:** Permite ao usuário criar um novo processo, fornecendo o nome do processo e o tempo total de execução em segundos.
  
- **Verificar Tabela de Processos:** Exibe uma tabela que mostra o nome do processo, o tempo total de execução, o tempo restante e o status de cada processo na fila.

- **Executar Processos:** Executa os processos na fila utilizando o algoritmo Round Robin. Durante a execução, o programa permite adicionar novos processos.

- **Sair:** Encerra o programa.

## Como Usar

1. Ao iniciar o programa, o usuário é solicitado a informar o tamanho do quantum (em segundos).

2. O programa exibirá um menu com as opções mencionadas acima.

3. O usuário pode escolher as opções do menu para criar novos processos, verificar a tabela de processos, executar os processos ou sair do programa.

4. Durante a execução dos processos, o programa permite a adição de novos processos.

5. Ao escolher "Sair", o programa será encerrado.

## Exemplo

Suponha que você defina o quantum como 2 segundos e crie dois processos:

1. Processo A com tempo total de execução de 5 segundos.
2. Processo B com tempo total de execução de 8 segundos.

Ao executar os processos, o programa alternará entre os dois processos a cada 2 segundos até que todos os processos sejam concluídos.

## Observações

- O programa inclui proteções para entrada de dados, garantindo que valores inválidos não sejam aceitos.

- A tabela de processos é exibida de forma formatada para facilitar a leitura.

- A tela do console é limpa para melhorar a experiência do usuário.

## Requisitos

- Java Development Kit (JDK) instalado.

## Execução

1. Compile o programa utilizando um compilador Java.

   ```bash
   javac Programa/Escalonador.java
   ```

2. Execute o programa.

   ```bash
   java Programa.Escalonador
   ```

3. Siga as instruções no console para interagir com o escalonador.

---

**Autor:** Letícia Lindberght da Costa

**Data de Criação:** 18/11/2023

**Versão:** 1.0