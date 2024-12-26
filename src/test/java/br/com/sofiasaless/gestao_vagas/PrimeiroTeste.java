package br.com.sofiasaless.gestao_vagas;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PrimeiroTeste {

    @Test
    public void deve_ser_possivel_calcular_dois_numeros () {
        var result = calculate(3, 2);
    
        // estou dizendo que quero que ele me garanta que o metodo acima irá retornar a soma dos numeros acima será 5 
        assertEquals(result, 5);
    }

    @Test
    public void validar_valor_incorreto () {
        var result = calculate(2, 3);

        // espero que o resultado nao seja 4
        assertNotEquals(result, 4);
    }

    public static int calculate(int n1, int n2) {
        return n1 + n2;
    }

    
}