# CaixaPJ: progresso

App Android de fluxo de caixa para PJ (MEI, ME, EPP): contas a pagar e a receber,
projeção de saldo para 30, 60 e 90 dias e alerta antes de o caixa ficar negativo.
Funciona 100% offline. Trabalho em modo tutor: eu escrevo o código, o Claude guia e revisa.

**Meta final: publicar na Google Play Store na última fase.** Todo o projeto é feito
pensando nisso (ver "Rumo à Play Store" e a linha "Pensando na Play Store" em cada fase).

**Estudo junto com o projeto:** o roadmap de Kotlin (roadmap.sh) é o plano de estudo.
Cada fase lista os tópicos que ela cobre. Um tópico só vira `[x]` quando está aplicado
no meu código e eu consigo explicá-lo.

## Stack
Kotlin, Jetpack Compose, Material 3, MVVM (data, domain, ui), Room, Hilt,
Navigation Compose, WorkManager, Vico, java.time.
Dinheiro em centavos (`Long`), nunca `Double`.

## Decisões
- `minSdk 26`: o `java.time` só existe nativamente a partir da API 26.
- `local.properties` e `.idea/` fora do Git: têm caminhos e dados da minha máquina.
- Commits pequenos, uma mudança por commit.
- Regras de negócio em casos de uso puros, sem `android.*`, para testar fácil.
- Dinheiro sempre positivo; o `TipoLancamento` (a pagar ou a receber) define o sinal.
- Status do lançamento é calculado (`dataPagamento` vazio = em aberto), não é campo salvo.
- KSP 2.3.x (não amarrado à versão do Kotlin) por causa do Kotlin embutido do AGP 9.
- Keystore e senhas de assinatura nunca vão para o Git.

## Rumo à Play Store

Pendências a decidir cedo, porque ficam caras de mudar depois:
- [ ] **`applicationId` definitivo.** O `com.example.*` não é aceito pela Play Store.
      Trocar `com.example.caixapj` (e o `namespace`) por um id meu, uma única vez,
      enquanto o projeto ainda é pequeno. Pendente: escolher o nome.
- [ ] Nome do app e ícone definitivos (o ícone padrão do Android Studio não serve).
- [ ] Decidir `allowBackup` (o app guarda dados financeiros) e revisar `backup_rules.xml`
      e `data_extraction_rules.xml`.

Checklist de publicação (fechar na Fase 7; conferir as regras vigentes da Play Console na época):
- [ ] App Bundle (`.aab`) assinado com keystore de upload, com Play App Signing ativo
- [ ] `versionCode` e `versionName` por release; `targetSdk` dentro do exigido
- [ ] Release com R8 e regras de keep testadas (já há `optimization` ligado no release)
- [ ] Política de privacidade publicada em uma URL (o app trata dados financeiros)
- [ ] Formulário "Segurança dos dados" e declarações da Play Console (inclui a de recursos financeiros)
- [ ] Classificação de conteúdo
- [ ] Ficha da loja: descrição em pt-BR, ícone 512x512, imagem de destaque, capturas de tela
- [ ] Teste fechado com testadores, se a minha conta exigir (conferir número de testadores e dias)
- [ ] Release para produção e plano de atualização (migrações do banco desde a v1)

## Roadmap Kotlin: onde cada tópico entra
Fora de escopo no fim do arquivo (seção "Depois da v1.0").

---

## Fase 0: preparação (concluída)
Pronto quando: o CI fica verde num app vazio. **Feito em 2026-11-17.**

- [x] Projeto Android criado (Compose, Material 3, version catalog)
- [x] Repositório Git local e `.gitignore` corrigido (raiz ignora `.idea/` e `local.properties`)
- [x] Repositório público no GitHub
- [x] `minSdk` 24 para 26 (`java.time` nativo)
- [x] Hilt: plugins Hilt e KSP, bibliotecas `hilt-android` e `hilt-compiler` no `libs.versions.toml`
- [x] Estrutura de pacotes `data`, `domain`, `ui`
- [x] `CaixaPJApp` com `@HiltAndroidApp` e `@AndroidEntryPoint` na `MainActivity`
- [x] GitHub Actions: lint, testes e APK a cada push

Roadmap Kotlin:
- [x] Setting up the environment
- [x] Gradle e Gradle plugins
- [x] CI/CD tools
- [x] IDEs (Android Studio)
- [x] Understanding packages e importing packages

Para revisar: por que `hilt-compiler` usa `ksp(...)` e `hilt-android` usa `implementation(...)`.

---

## Fase 1: domínio e regras (TDD) (em andamento)
Modelos, recorrências, status, `ProjetarSaldo`, indicadores. Kotlin puro, sem `android.*`.

- [ ] Definir o `applicationId` definitivo (ver "Rumo à Play Store")
- [ ] `TipoLancamento` (enum) e `Lancamento` (data class) com `require(valorCentavos > 0)`
- [ ] Primeiro teste da regra do valor positivo
- [ ] Status do lançamento (em aberto, pago ou recebido, atrasado)
- [ ] Recorrências
- [ ] `ProjetarSaldo` para 30, 60 e 90 dias
- [ ] Indicadores

Roadmap Kotlin:
- [ ] Variables, `val` vs `var`
- [ ] Data types, type checks e type inference, comments
- [ ] Null safety: nullable vs non-nullable, operadores de checagem, safe casts
- [ ] Classes: defining classes, creating instances, properties, constructors
- [ ] Data classes, enum class, sealed class, interfaces
- [ ] Object declarations
- [ ] Visibility modifiers
- [ ] Conditional expressions: `if` e `when`
- [ ] Exceptions: throwing e catching (`require`)
- [ ] Funções: parameters, return, local e member functions
- [ ] Extension functions
- [ ] Lambdas e higher-order functions
- [ ] Collections: lists, sets, maps, read-only vs mutable
- [ ] Collection operations: filtering, transformations, grouping, ordering, aggregate, plus/minus
- [ ] Ranges e progressions (intervalo de dias)
- [ ] Sequences (geração de recorrências)
- [ ] Loops: `for`, `while`, `break` e `continue`
- [ ] Generics, type aliases, inline (value) class
- [ ] Date & time (`java.time`)
- [ ] Test library (`kotlin.test` e JUnit)

Pensando na Play Store: domínio puro e testado, valores formatados em pt-BR
(`NumberFormat` com `Locale("pt", "BR")`), sem texto fixo de interface no domínio.

---

## Fase 2: Room
Entities, DAOs, repositórios.

- [ ] Entities e DAOs
- [ ] Repositórios
- [ ] Conversores de tipo (`LocalDate`, enums)

Roadmap Kotlin:
- [ ] Properties, constructors e interfaces aplicados a entities e DAOs
- [ ] Generics
- [ ] Coroutines: suspending functions
- [ ] Asynchronous flow (`Flow`)
- [ ] Kotlin libraries: coroutines

Pensando na Play Store: `exportSchema` ligado e migrações desde a versão 1 do banco,
porque o usuário real não pode perder dados numa atualização.

---

## Fase 3: lançamentos na UI

- [ ] ViewModels com `@HiltViewModel`
- [ ] `UiState` por tela
- [ ] Formulário de lançamento e lista

Roadmap Kotlin:
- [ ] Higher-order functions e lambdas (Compose)
- [ ] Property delegates (`by`)
- [ ] Inheritance e abstract class (`ViewModel`)
- [ ] Sealed class para o estado da tela
- [ ] Extension functions na camada de UI
- [ ] Coroutines builders

Pensando na Play Store: todo texto em `strings.xml` (nada fixo no código), teclado numérico
nos campos de valor, `contentDescription` para acessibilidade, tema escuro.

---

## Fase 4: painel e projeção com gráfico

- [ ] Painel com saldo e vencimentos
- [ ] Gráfico da projeção com Vico (30, 60, 90 dias)

Roadmap Kotlin:
- [ ] Coroutines behavior
- [ ] Flow na tela
- [ ] Android Jetpack
- [ ] Android SDK

Pensando na Play Store: layout testado em telas pequenas e grandes, modo escuro,
edge-to-edge e orientação.

---

## Fase 5: notificações com WorkManager

- [ ] Worker que verifica a projeção e avisa antes do caixa ficar negativo
- [ ] Canal de notificação

Roadmap Kotlin:
- [ ] Coroutines: best practices
- [ ] Opt-in requirements

Pensando na Play Store: permissão `POST_NOTIFICATIONS` (Android 13+) pedida no momento certo,
WorkManager em vez de alarmes exatos, e funcionamento correto após reiniciar o aparelho.

---

## Fase 6: indicadores e exportação CSV

- [ ] Indicadores
- [ ] Exportação CSV

Roadmap Kotlin:
- [ ] I/O: creating files, writing e reading files, buffered streams
- [ ] Serialization

Pensando na Play Store: exportar com o seletor de arquivos ou o compartilhar do sistema,
sem pedir permissão de armazenamento.

---

## Fase 7: acabamento, publicação na Play Store e release v1.0

- [ ] Fechar o checklist de publicação (seção "Rumo à Play Store")
- [ ] README
- [ ] Build do `.aab` assinado e teste do release
- [ ] Publicação e release v1.0

Roadmap Kotlin:
- [ ] Documentation: KDoc e Dokka
- [ ] Build tools: Gradle (revisão)

---

## Depois da v1.0
(ideias novas entram aqui, não no escopo atual)

Do roadmap de Kotlin, fora do escopo agora (foco em Kotlin para Android):
- Kotlin Multiplatform e Compose Multiplatform
- Kotlin/Native, Kotlin/WASM, Kotlin/JavaScript e scripting
- Server-side: Ktor, Spring, Quarkus, Vert.x
- Data analysis: Kotlin Notebook, DataFrame, Kandy
- Competitive programming
- IA com Koog
- Maven, Build Tool API e TeamCity
- Interoperabilidade com Java, C e Swift/Objective-C
