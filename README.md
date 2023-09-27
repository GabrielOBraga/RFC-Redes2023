##  RFC-Redes2023

Projeto Redes - BCC-IFG 2023 (Aluno: Gabriel Oliveira Braga)
**Descrição**: Projeto de Curso Bacharelado de Ciências da Computação, Instituto Federal de Goiás - Campus Anápolis
**Disciplina**: Redes de Computadores - 02/2023
**Link**: https://github.com/GabrielOBraga/RFC-Redes2023

## Descrição do Projeto

# Trabalho sobre camada de aplicação.

**Resumo**: Desenvolver um cliente e um servidor ou híbrido (cliente/servidor) que realize um serviçodefinido por você. Utilize a linguagem Java.

**Detalhes**:
    1. Trabalho em dupla;
    2. Definição de um mini RFC sobre o protocolo definido, sintaxe e semântica, consultar exemplos de RFC: [Hypertext Markup Language - 2.0](https://datatracker.ietf.org/doc/html/rfc1866) (2 pontos)
        2.1 Seu RFC deverá descrever todas as palavras chaves utilizadas, sua sintaxe e semântica dentro doserviço desenvolvido por você. Mínimo de 20 palavras/tokens de controle. (1 pontos)
    3. Utilizar a abordagem de múltiplos clientes e um servidor. (3 pontos)
    4. Exibir durante uma apresentação de 20 minutos o mini RFC, o servidor e o cliente em operação. Permitir que o professor tenha uma cópia do cliente para conectar ao servidor simultaneamente. Os dois alunos devem apresentar e destacar sua contribuição no projeto. Apresentação é do sistema rodando e não de slides. (4 pontos)

**Data entrega e apresentação**: 27/10/2023

## Referencias Utilizadas

Essas são as classes e interfaces importadas no código:

    java.io.BufferedReader: Para ler os dados de entrada do cliente.
    java.io.IOException: Para tratar exceções de E/S (entrada/saída).
    java.io.InputStreamReader: Para criar um InputStreamReader a partir de uma InputStream.
    java.io.PrintWriter: Para escrever dados de saída para o cliente.
    java.net.ServerSocket: Para criar o socket do servidor e aguardar conexões dos clientes.
    java.net.Socket: Para representar o socket de comunicação entre o servidor e o cliente.
    java.util.ArrayList: Para armazenar a lista de clientes e membros do grupo.
    java.util.HashMap: Para armazenar os usuários logados e os grupos.
    java.util.List: Para representar listas de objetos.
    java.util.Map: Para representar mapeamentos de chave-valor.
