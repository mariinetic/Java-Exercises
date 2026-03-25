package br.edu.fatec.sjc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckoutServiceTest {

    @Mock
    private EstoqueService estoque;

    @Mock
    private PagamentoGateway gateway;

    @InjectMocks
    private CheckoutService checkout;

    private Pedido pedido;

    @BeforeEach
    void preparacao() {
        pedido = new Pedido("Tinta Rosa", 1, 100.0);
    }

    @Test
    @DisplayName("Cenário A: Sucesso")
    void sucesso() {
        when(estoque.temEstoque("Tinta Rosa", 1)).thenReturn(true);
        when(gateway.processar(100.0)).thenReturn(true);

        boolean resultado = checkout.finalizarCompra(pedido);

        assertTrue(resultado);
        verify(estoque).baixarEstoque("Tinta Rosa", 1);
    }

    @Test
    @DisplayName("Cenário B: Falha - Sem estoque")
    void semEstoque() {
        when(estoque.temEstoque("Tinta Rosa", 1)).thenReturn(false);

        boolean resultado = checkout.finalizarCompra(pedido);

        assertFalse(resultado);
        verify(gateway, never()).processar(anyDouble());
    }

    @Test
    @DisplayName("Cenário C: Falha - Pagamento negado")
    void pagamentoNegado() {
        when(estoque.temEstoque("Tinta Rosa", 1)).thenReturn(true);
        when(gateway.processar(100.0)).thenReturn(false);

        boolean resultado = checkout.finalizarCompra(pedido);

        assertFalse(resultado);
        verify(estoque, never()).baixarEstoque(anyString(), anyInt());
    }

    @Test
    @DisplayName("Cenário D (Extra): Pedido com valor zero")
    void valorZero() {
        Pedido pZero = new Pedido("Tinta Preta", 1, 0.0);
        when(estoque.temEstoque("Tinta Preta", 1)).thenReturn(true);
        when(gateway.processar(0.0)).thenReturn(true);

        assertTrue(checkout.finalizarCompra(pZero));
    }

    @Test
    @DisplayName("Cenário E (Extra): Grande quantidade")
    void grandeQtd() {
        Pedido pGrande = new Pedido("Tinta Verde", 999, 500.0);
        when(estoque.temEstoque("Tinta Verde", 999)).thenReturn(true);
        when(gateway.processar(500.0)).thenReturn(true);

        assertTrue(checkout.finalizarCompra(pGrande));
        verify(estoque).baixarEstoque("Tinta Verde", 999);
    }
}
