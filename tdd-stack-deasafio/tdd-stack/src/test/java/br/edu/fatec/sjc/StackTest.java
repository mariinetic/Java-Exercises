package br.edu.fatec.sjc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StackTest {

    private CustomStack<Double> cut;

    @Mock
    private CalculableStrategy<Double> calculableStrategy;

    @BeforeEach
    void setUp() {
        lenient().when(calculableStrategy.calculateValue(anyDouble())).thenAnswer(invocation -> invocation.getArgument(0));
        cut = new CustomStack<>(5, calculableStrategy); 
    }

    @Test
    @DisplayName("Pilha vazia")
    void shouldCreateEmptyStack() {
        assertTrue(cut.isEmpty());
        assertEquals(0, cut.size());
    }

    @Test
    @DisplayName("Adicionar elementos na pilha")
    void shouldPushElements() throws StackFullException {
        cut.push(10.0);
        cut.push(20.0);
        assertEquals(2, cut.size());
        assertEquals(20.0, cut.top());
    }

    @Test
    @DisplayName("Remover elementos da pilha")
    void shouldPopElements() throws StackFullException, StackEmptyException {
        cut.push(10.0);
        cut.push(20.0);
        
        assertEquals(20.0, cut.pop());
        assertEquals(10.0, cut.pop());
        assertTrue(cut.isEmpty());
    }

    @Test
    @DisplayName("StackEmptyException quando remover da pilha vazia")
    void shouldThrowExceptionWhenEmpty() {
        assertThrows(StackEmptyException.class, () -> cut.pop());
    }

    @Test
    @DisplayName("StackFullException quando exceder o limite")
    void shouldThrowExceptionWhenFull() throws StackFullException {
        for (int i = 0; i < 5; i++) cut.push((double) i);
        assertThrows(StackFullException.class, () -> cut.push(6.0));
    }

    @Test
    @DisplayName("IndexOutOfBoundsException quando o topo de pilha estiver vazio")
    void shouldThrowExceptionOnTopEmpty() {
        assertThrows(IndexOutOfBoundsException.class, () -> cut.top());
    }

    @Test
    @DisplayName("Cálculo no push")
    void shouldUseStrategy() throws StackFullException {
        when(calculableStrategy.calculateValue(10.0)).thenReturn(100.0);
        cut.push(10.0);
        assertEquals(100.0, cut.top());
        verify(calculableStrategy).calculateValue(10.0);
    }
}
