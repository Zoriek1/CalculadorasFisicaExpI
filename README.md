## Sobre o projeto

Esse é um projeto programado com o básico em Java, para aprendizado e ajuda acadêmica. Nenhum objeto para além deste é real. Sempre que aprender algo novo, irei atualizar o projeto!

No momento ele roda via interface gráfica em Swing. Você pode executar:
- via IDE (IntelliJ/VS Code) com JDK 17+
- via JAR executável
- via executável do Windows (.exe)

## Pré‑requisitos
- Java 17+ (JDK ou JRE) instalado para executar o programa, seja via IDE, via JAR ou via .exe.
- Para compilar localmente, é necessário o Maven 3.9+.

## Como executar no Windows (.exe)
O repositório já está configurado para gerar um executável do Windows usando Launch4j.

1. Compile os artefatos com Maven:
   - Gerar o JAR “fat” (com dependências):
     ```bash
     mvn -q -DskipTests package
     ```
   - Gerar o executável do Windows (.exe):
     ```bash
     mvn -q -Pwindows-exe com.akathist.maven.plugins.launch4j:launch4j-maven-plugin:1.7.25:launch4j
     ```
2. Após o build, copie estes dois arquivos da pasta `target/` para a mesma pasta no seu Windows:
   - `untitled2.exe`
   - `untitled2-1.0-jar-with-dependencies.jar`
3. Dê duplo clique em `untitled2.exe`. A interface Swing será aberta.

Observações:
- O `.exe` requer Java 17+ instalado no Windows.
- O `.exe` precisa que o JAR “untitled2-1.0-jar-with-dependencies.jar” esteja no mesmo diretório.

## Como executar via JAR
Sem usar o `.exe`, você pode rodar diretamente o JAR com dependências:
```bash
java -jar target/untitled2-1.0-jar-with-dependencies.jar
```

## Como executar via IDE
Abra o projeto na sua IDE com JDK 17+ e execute o método `main` em `Reports.InterfaceRelatorios`.

## Uso da interface
1. Abra o programa.
2. Escolha o relatório desejado no combo.
3. Clique em “Selecionar CSV” e aponte para seu arquivo `.csv` (apenas valores, limpo).
4. Clique em “Gerar Relatório”.
5. O resultado aparecerá em uma nova janela.

## Observações importantes
- Existem valores constantes em cada experimento que devem ser alterados diretamente no código conforme sua necessidade.
- Qualquer erro de compilação normalmente é resolvido deixando o CSV limpo, somente com os valores.

## Desenvolvimento
- Estrutura do ponto de entrada: `Reports.InterfaceRelatorios` (Swing GUI)
- Requisitos de compilação: JDK 17+, Maven 3.9+

## Dicas de Git
Se for clonar via Git:
```bash
git clone https://github.com/Zoriek1/CalculadorasFisicaExpI.git
```
Para dar certo, o nome da pasta deve ser `CalculadorasFisicaExpI` (não como subpasta dentro de outro `src/java`).
