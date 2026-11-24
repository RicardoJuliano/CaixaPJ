package br.com.ricardojuliano.caixapj.domain.model

import java.time.LocalDate

data class Lancamento(
    val id: Long,
    val descricao: String,
    val valorCentavos: Long,
    val tipo: TipoLancamento,
    val vencimento: LocalDate,
    val dataPagamento: LocalDate?
) {
    init {
        require(valorCentavos > 0) { "O valor deve ser maior que zero" }
    }
}
