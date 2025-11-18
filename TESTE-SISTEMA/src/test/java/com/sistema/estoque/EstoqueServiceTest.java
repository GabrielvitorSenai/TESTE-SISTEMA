// src/test/java/com/sistema/estoque/EstoqueServiceTest.java
// Define o caminho lógico do arquivo, indicando que é um código de teste.
package com.sistema.estoque;
// Define o pacote Java onde esta classe de teste reside.

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
// Importa estaticamente todos os métodos de asserção (assertEquals, assertTrue, etc.) para uso direto.

@DisplayName("Testes de Sistema para EstoqueService")
// Define um nome de exibição mais amigável para toda a classe de teste.
public class EstoqueServiceTest {

    private EstoqueService estoqueService;
    // Declara a variável de instância para a classe de serviço (o sistema) a ser testada.
    
    private final String PRODUTO_A = "Caneta Azul";
    // Define uma constante para o nome do produto, usada em todos os testes.

    // O método @BeforeEach é executado antes de CADA método de teste
    @BeforeEach
    // Anotação que garante que este método será executado antes de cada @Test.
    void setup() {
        // Inicializa o serviço com um estado limpo antes de cada teste
        estoqueService = new EstoqueService();
        // Cria uma nova instância da classe EstoqueService para garantir isolamento (Princípio I).
        estoqueService.adicionarProduto(PRODUTO_A, 100);
        // Define o estado inicial do sistema: adiciona o produto com 100 unidades.
    }

    // 1. Teste de Fluxo Principal (Cenário de sucesso de ponta a ponta)
    @Test
    // Marca o método seguinte como um teste executável.
    @DisplayName("Deve realizar entrada e saída de estoque com sucesso")
    // Define o nome descritivo para este teste específico.
    void deveRealizarFluxoPrincipalComSucesso() {
        // Arrange (Preparação)
        // Seção para configurar variáveis e estado inicial.
        int quantidadeInicial = estoqueService.getQuantidadeAtual(PRODUTO_A);
        // Obtém o saldo atual do sistema (100).
        int entrada = 50;
        // Define o valor a ser adicionado.
        int saida = 20;
        // Define o valor a ser removido.
        int esperado = quantidadeInicial + entrada - saida;
        // Calcula o resultado final esperado (100 + 50 - 20 = 130).

        //--------------------------------------------------------------

        // Act (Ação - Simulação da transação completa do sistema)
        // Seção para executar o código do sistema que está sendo testado.
        boolean sucessoEntrada = estoqueService.entradaEstoque(PRODUTO_A, entrada);
        // Executa a primeira transação (entrada) e captura o resultado booleano.
        boolean sucessoSaida = estoqueService.saidaEstoque(PRODUTO_A, saida);
        // Executa a segunda transação (saída) e captura o resultado booleano.
        int quantidadeFinal = estoqueService.getQuantidadeAtual(PRODUTO_A);
        // Obtém o estado final do sistema após as transações.

        //--------------------------------------------------------------

        // Assert (Verificação)
        // Seção para verificar se o resultado real é igual ao resultado esperado.
        assertTrue(sucessoEntrada, "A entrada em estoque deve ser bem-sucedida.");
        // Verifica se a entrada em estoque retornou 'true' (sucesso).
        assertTrue(sucessoSaida, "A saída em estoque deve ser bem-sucedida.");
        // Verifica se a saída em estoque retornou 'true' (sucesso).
        assertEquals(esperado, quantidadeFinal, "A quantidade final deve ser 130.");
        // Verifica se a quantidade final do sistema é exatamente 130.
    }

    // 2. Teste de Cenário de Falha (Regra de negócio: não pode vender mais do que tem)
    @Test
    // Marca o método como um teste.
    @DisplayName("Não deve permitir a saída de estoque se não houver saldo suficiente")
    // Nome descritivo do teste de falha (regra de negócio violada).
    void naoDevePermitirSaidaSemSaldo() {
        // Arrange
        int quantidadeInicial = estoqueService.getQuantidadeAtual(PRODUTO_A);
        // Obtém o saldo atual (100) para verificação futura.
        int saidaExcedente = 150;
        // Define uma quantidade de saída que excede o estoque disponível.

        // Act
        boolean sucessoSaida = estoqueService.saidaEstoque(PRODUTO_A, saidaExcedente);
        // Tenta executar a transação que deve falhar (remover 150 de 100).
        int quantidadeFinal = estoqueService.getQuantidadeAtual(PRODUTO_A);
        // Obtém o estado final do estoque.

        // Assert
        assertFalse(sucessoSaida, "A saída de estoque não deve ser bem-sucedida.");
        // Verifica se a transação falhou (retornou 'false').
        assertEquals(quantidadeInicial, quantidadeFinal, "A quantidade deve permanecer 100.");
        // Verifica se o estoque não foi alterado e permaneceu o valor inicial (100).
    }

    // 3. Teste de Limite (Boundary Test)
    @Test
    // Marca o método como um teste.
    @DisplayName("Deve permitir zerar o estoque com uma saída exata")
    // Nome descritivo do teste de limite (saída igual ao saldo).
    void devePermitirSaidaExata() {
        // Arrange
        int saidaExata = estoqueService.getQuantidadeAtual(PRODUTO_A);
        // Define o valor de saída exatamente igual ao saldo atual (100).

        // Act
        boolean sucessoSaida = estoqueService.saidaEstoque(PRODUTO_A, saidaExata);
        // Executa a transação de saída exata (remover 100 de 100).
        int quantidadeFinal = estoqueService.getQuantidadeAtual(PRODUTO_A);
        // Obtém o estado final do estoque.

        // Assert
        assertTrue(sucessoSaida, "A saída exata deve ser bem-sucedida.");
        // Verifica se a transação de saída exata foi bem-sucedida (retornou 'true').
        assertEquals(0, quantidadeFinal, "A quantidade final deve ser zero.");
        // Verifica se o estoque final é 0.
    }
}