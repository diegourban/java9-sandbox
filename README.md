# java9-sandbox

Testando features do JDK 9

https://openjdk.java.net/projects/jdk9/

## JEP 200: The Modular JDK (Jigsaw)

https://openjdk.java.net/jeps/200

O Objetivo foi dividir o JDK em módulos que podem ser combinados em tempo de compilação, build e runtime.

Para ver o sistema de módulos do JDK: http://cr.openjdk.java.net/~mr/jigsaw/ea/module-summary.html

Abaixo estão exemplos de utilização de módulos extraído de https://openjdk.java.net/projects/jigsaw/quick-start

### Greetings
O primeiro exemplo é um módulo chamado `com.greetings` que printa "Greetings!".
O módulo consiste em dois arquivos: a declaração do módulo (module-info.java) e a classe principal.

Por convenção, o código-fonte do módulo fica em um diretório que é o nome do módulo.
- src/com.greetings/com/greetings/Main.java
- src/com.greetings/module-info.java

O código-fonte é compilado no diretório mods/com.greetings:
  
Criando a pasta mods para compilar o código-fonte: `mkdir -p mods/com.greetings`

Compilando o módulo:
```
javac -d mods/com.greetings \
        src/com.greetings/module-info.java \
        src/com.greetings/com/greetings/Main.java
```
          
Executando o módulo:
`java --module-path mods -m com.greetings/com.greetings.Main`

A opção `--module-path` é o path do módulo, contém um ou mais diretórios que contém módulos. 
A opção `-m` especifica o módulo principal, o valor depois da barra é o nome da classe principal do módulo.
 
### Greetings World

Esse segundo exemplo atualiza a declaração do módulo para definir a dependência do módulo org.astro.
O módulo org.astro exporta o pacote org.astro.

Os módulos são compilados um de cada vez.
O comando javac para compilar o módulo com.greetings especifica o path do módulo para que a referência para o módulo org.astro e o tipo exportado possam ser resolvidos.

Criando as pastas mods
`mkdir -p mods/org.astro mods/com.greetings`

Compilando org.astro:
```
javac -d mods/org.astro \
        src/org.astro/module-info.java src/org.astro/org/astro/World.java
```

Compilando com.greetings:
```
javac --module-path mods -d mods/com.greetings \
        src/com.greetings/module-info.java src/com.greetings/com/greetings/Main.java
```

Para rodar é da mesma forma do primeiro exemplo:
`java --module-path mods -m com.greetings/com.greetings.Main`

### Compilação multi-módulo

n the previous example then module com.greetings and module org.astro were compiled separately. 
It is also possible to compile multiple modules with one javac command:

No exemplo anterior os módulos com.greetings e org.astro foram compilados separadamente.
Também é possível compilar muúltiplos módulos com um comando javac:

Criando a pasta mods:
`mkdir mods`

Compilando todos os módulos:
`javac -d mods --module-source-path src $(find src -name "*.java")`

Listando arquivos na pasta mods:
`find mods -type f`

## JEP 110: HTTP/2 Client (Incubator)

https://openjdk.java.net/jeps/110

Nova API HTTP que implementa HTTP/2 e WebSocket que pode substituir a API HttpURLConnection.
A API estará disponível no modulo incubator.

