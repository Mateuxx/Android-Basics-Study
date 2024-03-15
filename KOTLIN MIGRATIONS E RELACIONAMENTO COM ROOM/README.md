# Kotlin Migrations

## Visão Geral

O Room é uma biblioteca de persistência de dados recomendada pelo Google para aplicativos Android. Ele oferece suporte à criação e gerenciamento de bancos de dados SQLite de forma mais fácil e eficiente.

## Migrations:
A cada mudança  No nosso room, ou seja, a cada adição de uma entidade ou algo assim precisamos mudar a versão do nosso room, por padrão ele vem como versão = 1.

![image](https://github.com/Mateuxx/Android-Basics-Study/assets/83120884/c6b0de36-c083-4aa4-983a-a7f1df463e30)


![image](https://github.com/Mateuxx/Android-Basics-Study/assets/83120884/e3ad1ead-3405-4fe0-a006-cf631a6383d2)

Este código usa fallbackToDestructiveMigration() ao construir o banco de dados usando o Room.databaseBuilder(). Aqui está uma explicação da chamada de migrações nesta parte do código:

O método fallbackToDestructiveMigration() é chamado para configurar o Room para permitir migrações destrutivas. Isso significa que se houver uma alteração no esquema do banco de dados que não seja compatível com a versão anterior, o Room irá descartar todo o banco de dados e recriá-lo do zero. Isso é útil durante o desenvolvimento, mas não é recomendado para produção, pois leva à perda de dados.

É importante notar que esse método não requer parâmetros. Ele simplesmente indica ao Room que migrações destrutivas são permitidas. Se você precisar de migrações não destrutivas, precisará fornecer migrações personalizadas usando addMigrations(), conforme explicado em outra parte do código ou do README.