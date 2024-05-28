# Tech Challenge

Este projeto foi desenvolvido com o Framework Spring na linguagem JAVA e banco de dados Mysql.

A porta 8080 é destinada a API RESTful, e a porta 3306 para o banco de dados.

### Subindo o ambiente

Certifique-se que as portas 8080 e 3306 estão disponíveis para subir os containers Docker.

Para subir os containers rode o seguinte comando:

``` docker-compose up --build -d ```

Após o build e o up dos containers a aplicação estará disponível em:

http://localhost:8080/

### Documentação

A aplicação possuí um Swagger para documentar as endpoints criadas para o projeto.

Você pode acessar o Swagger pelo link:

http://localhost:8080/tech-challenge-01/swagger-ui/index.html

A Collection do **postman** com todas as endpoints necessárias, esta no diretório .postman na raiz do projeto.

Você poderá fazer o download da Collection <a target="_blank" href="/.postman/Tech Challenge.postman_collection.json" target="blank">aqui</a> e as Environment <a target="_blank" href="/.postman/Tech Challenge.postman_environment.json">aqui</a>.

As environment é importante para o fluxo da API, pois os IDs do cliente, combo, pedido são armazenados nas variáveis de ambiente para facilitar o uso.

### Event Storm

O Event Storm desenvolvido e utilizado para o desenvolvimento deste projeto esta no link abaixo:

https://miro.com/welcomeonboard/dDduRUxZVVo1SWtzSWdVZVBwTU5SUmdNOE83MGc2SVUxV3gzd2kxNTlUM081MHEwODByRHFraG1XUXFCRUFUQXwzNDU4NzY0NTIyNzA2NTA5MDU0fDI=?share_link_id=191676060471

### Fluxo da API

Ao subir o container o banco de dados será automaticamente criado com produtos de exemplo já cadastrados.

- (optional) Cadastrar Produtos
- Cadastrar o Cliente
- Criar um Combo
- Adicionar os Produtos ao Combo
- Criar o Pedido
- Realizar o Pagamento

## Linguagem Ubíqua

**Cliente**, quem faz o pedido

**CPF**, identificador do cliente, quando informado.

**Código do pedido**, cada pedido é identificado de forma única.

**Item**, opções disponíveis para o cliente, cada item, possui um nome, uma descrição e um tipo.

**Tipo**, Lanche, Acompanhamento, Bebida, Sobremesa

**Combo**, conjunto de itens de um cliente, cada combo pode ter apenas um item de cada tipo, o combo é criado quando o cliente é identificado.

**Pedido**, é a formalização de um combo depois que o cliente confirmou o pagamento.

**Acompanhamento do pedido**, define a situação que o pedido está, as opções disponíveis são: Recebido, Em Preparação, Pronto e Finalizado.

**Acompanhamento do pedido, Recebido**, significa que o sistema recebeu o pedido do cliente, ele foi validado e já foi pago.

**Acompanhamento do pedido, Em Preparação**, significa que o pedido foi enviado para a cozinha e já está na fila de preparação

**Acompanhamento do pedido, Pronto**, significa que o pedido foi concluído pela cozinha, o atendente é avisado e informa na tela que o pedido está disponível para retirada.

**Acompanhamento do pedido, Finalizado**, significa que o pedido foi entregue ao cliente pelo atendente.

**QRCode**, um código de barras no formato 2d gerado pela plataforma de pagamento, que permite o pagamento.

