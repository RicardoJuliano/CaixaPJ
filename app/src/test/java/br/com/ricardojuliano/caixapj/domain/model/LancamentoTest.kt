package br.com.ricardojuliano.caixapj.domain.model

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class LancamentoTest {
    @Test
    fun `valor zero lanca erro`() {
        assertThrows(IllegalArgumentException::class.java) {
            Lancamento(
                id = 1,
                descricao = "Aluguel",
                valorCentavos = 0,
                tipo = TipoLancamento.A_PAGAR,
                vencimento = LocalDate.of(2026, 12, 10),
                dataPagamento = null
            )
        }
    }

    @Test
    fun `valor negativo lanca erro`() {
        assertThrows(IllegalArgumentException::class.java) {
            Lancamento(
                id = 1,
                descricao = "Aluguel",
                valorCentavos = -100,
                tipo = TipoLancamento.A_PAGAR,
                vencimento = LocalDate.of(2026, 12, 10),
                dataPagamento = null
            )
        }
    }

    @Test
    fun `valor positivo cria sem erros`() {
        val lancamento = Lancamento(
            id = 1,
            descricao = "Aluguel",
            valorCentavos = 1250,
            tipo = TipoLancamento.A_PAGAR,
            vencimento = LocalDate.of(2026, 12, 10),
            dataPagamento = null
        )
        assertEquals(1250L, lancamento.valorCentavos)
    }
}