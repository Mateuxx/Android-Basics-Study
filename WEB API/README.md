# Web API

- Este projeto simula o google keep: adicionar, remover e editar uma nota por meio de uma api.
- Como o room apenas deixa salvo no bd local do device, quando precisamos guardar informações
  indenpendete do device que estamos utilizando.

## Intent

No contexto de desenvolvimento Android, "extras" são dados adicionais que podem ser anexados a uma
Intent para transmitir informações entre diferentes componentes da aplicação, como atividades (
Activities) ou serviços (Services).

- **Intent**: Um objeto Intent pode ser usado para iniciar uma nova atividade ou serviço, ou para
  transmitir uma mensagem para um BroadcastReceiver.
- **Extras**: São pares chave-valor que podem ser anexados à Intent. Eles são usados para passar
  dados
  adicionais que a atividade ou serviço de destino pode precisar.

### Comunicação com WEB api

- **Web API**: Comunicação entre uma interface, porem web. Uma lib ou qualquer coisa nesse sentido
  Serviços online, comunicação entre esses serviços online.
- Garantir que os dados fique acessiveis
- Trafegar dados em diferentes dispositvos
  ![img.png](img.png)

### Rest

- Trasnferencia Representacional de Estado
- Padrão para fazer essa comunicação entre os dispositivos
- Formato Jason

**Outras APIS públicas** https://github.com/public-apis/public-apis

### API 
- A API utilizada nesse projeto eh uma local desenvolvida em Spring Boot