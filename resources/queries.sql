
--selects dos dados de configuração:
	select duracao_bloco from configuracao;
	select preco_bloco from configuracao;
	select desconto_mensalista from configuracao;
	select qtd_vagas from configuracao;
	select minimo_horas from configuracao;
	
--consulta para conferir se existe uma configuração criada
	select count(*) as config from configuracao;

--selects pra tabela de entrada_saida:
	--consulta para contar quantos carros estão estacionados no momento 
		select count(*) from entrada_saida where data_hora_saida IS null
	--busca os dados de entrada e saida pela placa 
		select descricao_veiculo, placa, mensalista, data_hora_entrada, data_hora_saida, tempo_permanencia, 
			preco_total from entrada_saida where placa = ?  order by data_hora_entrada DESC LIMIT 1;			
	--busca os dados de carros que estão estacionados pela placa 
		select descricao_veiculo, placa, mensalista, data_hora_entrada, data_hora_saida, tempo_permanencia, preco_total 
			from entrada_saida where placa = ? AND data_hora_saida is null AND data_hora_entrada = ?;			
	--busca para consultar a média de tempo permanência num período de dias para alimentar o gráfico 
		Select AVG(tempo_permanencia) AS media from entrada_saida where data_hora_saida > ? and data_hora_saida < ? 
			group by DATE_FORMAT(data_hora_saida, "%d") order by data_hora_saida asc; 			
	--busca para consultar a média de preço total num período de dias para alimentar o gráfico 	
		Select AVG(preco_total) AS media from entrada_saida where data_hora_saida > ? and data_hora_saida < ? 
			group by DATE_FORMAT(data_hora_saida, "%d") order by data_hora_saida asc; 			
	--busca as datas formatadas num período de dias para alimentar o gráfico 
		Select DATE_FORMAT(data_hora_saida, "%Y-%m-%d") AS data from entrada_saida where data_hora_saida > ? and data_hora_saida < ? 
			group by DATE_FORMAT(data_hora_saida, "%d") order by data_hora_saida asc; 
		
--selects pra tabela de mensalista:	
	--busca dados do mensalista usando o cpf
		SELECT cpf, nome, telefone, saldo_atual, obs FROM mensalista WHERE cpf = ?;
	--busca dados do mensalista usando o telefone
		SELECT cpf, nome, telefone, saldo_atual, obs FROM mensalista WHERE telefone = ?;		
	--busca cpf do mensalista usando a placa do veiculo
		SELECT cpf FROM mensalista WHERE cpf = (select cpf from veiculos_mensalista where placa = ?);















