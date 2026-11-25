# CaixaPJ

App Android de fluxo de caixa para pequenas empresas (MEI, ME e EPP). A ideia é simples: registrar contas a pagar e a receber, projetar o saldo para os próximos 30, 60 e 90 dias e avisar antes de o caixa ficar negativo. Tudo funciona offline, sem conta e sem servidor.

> **Status: em desenvolvimento (Fase 1 de 7).** Hoje existe o domínio do app (modelo de lançamento com regra de valor) com testes unitários e integração contínua rodando. As telas, o banco de dados e a projeção de saldo ainda estão por vir. O plano completo, fase a fase, está em [PROGRESSO.md](PROGRESSO.md).

## Por que existe

Pequenos negócios costumam controlar o caixa em caderno ou planilha e só percebem o buraco quando ele já abriu. O CaixaPJ responde uma pergunta só: *vou ter dinheiro para pagar as contas das próximas semanas?* Eu trabalhei quase 3 anos com carteiras PJ em crédito, então esse é um problema que conheço de perto.

## O que já funciona

- Modelo `Lancamento` (descrição, valor, tipo, vencimento e data de pagamento) em Kotlin puro, sem dependência do Android.
- Valor em centavos (`Long`), nunca `Double`, para evitar erro de arredondamento com dinheiro.
- Regra de negócio validada no próprio modelo: o valor precisa ser maior que zero. O tipo (`A_PAGAR` ou `A_RECEBER`) define o sinal.
- Testes unitários da regra de valor (zero, negativo e positivo).
- GitHub Actions: a cada push roda lint, testes unitários e monta o APK de debug.

## Roadmap

| Fase | O que entrega | Situação |
|------|---------------|----------|
| 0 | Projeto, CI verde, estrutura de pacotes e Hilt | Concluída |
| 1 | Domínio: status, recorrências, projeção de saldo e indicadores (com TDD) | Em andamento |
| 2 | Persistência com Room | Planejada |
| 3 | Lista e formulário de lançamentos | Planejada |
| 4 | Painel de saldo e gráfico de projeção | Planejada |
| 5 | Alerta de caixa negativo com WorkManager | Planejada |
| 6 | Indicadores e exportação em CSV | Planejada |
| 7 | Acabamento e publicação na Google Play | Planejada |

## Stack

| Parte | Tecnologia |
|-------|------------|
| Linguagem | Kotlin |
| Interface | Jetpack Compose com Material 3 |
| Injeção de dependência | Hilt (com KSP) |
| Datas | `java.time` |
| Testes | JUnit |
| CI | GitHub Actions |
| Previstas | Room, Navigation Compose, WorkManager e Vico (gráficos) |

## Arquitetura

Camadas `data`, `domain` e `ui`, no padrão MVVM. As regras de negócio ficam em casos de uso puros, sem `android.*`, para serem testadas rápido e sem emulador.

```
app/src/main/java/br/com/ricardojuliano/caixapj/
├── domain/model/   Lancamento e TipoLancamento
├── ui/theme/       tema Material 3
├── CaixaPJApp.kt   Application com Hilt
└── MainActivity.kt
```

## Decisões

- **minSdk 26**, porque o `java.time` só existe nativamente a partir dessa API.
- **Dinheiro em centavos** (`Long`) e sempre positivo; o tipo do lançamento define se entra ou sai.
- **Status calculado**, não salvo: lançamento sem data de pagamento está em aberto.
- **Commits pequenos**, uma mudança por commit, com testes antes do código nas regras de negócio.
- Chaves de assinatura e senhas nunca entram no Git.

## Como rodar

Requisitos: Android Studio recente e um emulador ou aparelho com Android 8.0 (API 26) ou mais novo.

```bash
git clone https://github.com/RicardoJuliano/CaixaPJ.git
```

Abra a pasta no Android Studio, espere o sync do Gradle e rode o módulo `app`. Pelo terminal:

```bash
./gradlew testDebugUnitTest   # testes unitários
./gradlew assembleDebug       # gera o APK de debug
```

## Sobre o projeto

Projeto de portfólio que estou construindo em modo tutor com o Claude: eu escrevo o código e a IA guia e revisa. O objetivo final é publicar o app na Google Play.

Autor: [Ricardo Juliano](https://www.linkedin.com/in/ricardo-juliano)
