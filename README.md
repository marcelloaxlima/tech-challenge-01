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

