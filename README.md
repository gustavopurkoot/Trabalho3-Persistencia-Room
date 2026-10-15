# Trabalho 2 – MAF (Mínimo Aplicativo Funcional)

App Android de saúde feito em Kotlin com Jetpack Compose, para a disciplina de Desenvolvimento Mobile. Ele evolui as 3 telas do Trabalho 1 para um app funcional, com navegação real, duas listas dinâmicas e telas de detalhes.

## Integrantes

- Gustavo Purkoot Ferreira
- Lucas Kirsten

## O que o app faz

O usuário consulta unidades de atendimento (hospitais e clínicas) e agenda consultas nelas.

- **Unidades:** adicionar, remover, ordenar por distância, ligar para a unidade e ver os detalhes.
- **Consultas:** agendar, remover, marcar como realizada e ver os detalhes.
- **Home:** atalhos de navegação, geração de token e acesso rápido à próxima consulta.
- **Notícias:** lista de notícias de saúde.

## Telas (7)

| Tela | Descrição |
|---|---|
| Home | Cartão virtual, favoritos e acesso rápido |
| Notícias | Lista de notícias de saúde |
| Unidades | Lista de unidades com formulário de cadastro |
| Detalhe da Unidade | Dados da unidade e total de consultas marcadas nela (valor calculado) |
| Consultas | Lista de consultas com `Checkbox` e remoção |
| Detalhe da Consulta | Dados da consulta combinados com os dados da unidade |
| Nova Consulta | Formulário de agendamento |

## Requisitos técnicos atendidos

- `NavHost` central em `AppNavigation.kt` e objeto `Rotas` com rotas nomeadas
- `NavigationBar` (BottomNavigation) com 4 abas
- `TopAppBar` com botão de voltar (`popBackStack`) nas telas de detalhe e formulário
- 2 data classes (`Unidade` e `Consulta`) em `Models.kt`
- 2 listas com `LazyColumn` + `Card`, usando `mutableStateListOf` no `ViewModel`
- Passagem de dados por argumento de rota (`detalhe_unidade/{id}` e `detalhe_consulta/{id}`)

## Tecnologias

- Kotlin
- Jetpack Compose
- Navigation Compose
- ViewModel

## Como rodar

1. Clone o repositório:
```bash
   git clone https://github.com/gustavopurkoot/trabalho2-maf-mobile.git
```
2. Abra a pasta no **Android Studio** e aguarde a sincronização do Gradle.
3. Conecte um celular com depuração USB ativada ou inicie um emulador.
4. Selecione a configuração `app` e clique em **Run**.

> Os dados ficam só na memória: ao fechar o app, o que foi adicionado ou removido é perdido.

## Estrutura do código

```
app/src/main/java/com/example/projeto_desenvolvimento_mobile/
├── MainActivity.kt          # ponto de entrada
├── TelaPrincipal.kt         # Scaffold + barra de navegação
├── AppNavigation.kt         # NavHost central
├── Rotas.kt                 # rotas nomeadas
├── BottomNavigation.kt      # barra inferior
├── Models.kt                # data classes
├── AppViewModel.kt          # listas e regras
├── Componentes.kt           # TopAppBar e botão reutilizáveis
└── Tela*.kt                 # as 7 telas
```