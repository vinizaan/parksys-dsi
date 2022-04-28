package dao;

import modelo.FazerPagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FazerPagamentoDAO {

private ParkSysDataSource dataSource = new ParkSysDataSource();
	
	public void registraPagamento(FazerPagamento pagamento) throws DataAccessException {
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (
					PreparedStatement update = conn
							.prepareStatement("INSERT INTO pagamento_mensalista "
									+ "(cpf, data_pagamento, valor_pago) "
									+ "VALUES (?, ?, ?);");) {

				update.setString(1, pagamento.getCpf());
				update.setString(2, pagamento.getDataPagamento());
				update.setDouble(3, pagamento.getValorPago());
				update.executeUpdate();

				conn.commit();

			} catch(SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
}
