package dao;

import modelo.VeiculosMensalista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeiculosMensalistaDAO {
	
	
	private ParkSysDataSource dataSource = new ParkSysDataSource();
	
	public void cadastraVeiculo(VeiculosMensalista veiculo) throws DataAccessException {
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (
					PreparedStatement update = conn
							.prepareStatement("INSERT INTO veiculos_mensalista "
									+ "(cpf, placa, descricao_veiculo) "
									+ "VALUES (?, ?, ?);");) {

				update.setString(1, veiculo.getCpf());
				update.setString(2, veiculo.getPlaca());
				update.setString(3, veiculo.getDescricao_veiculo());
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
	public VeiculosMensalista findByPlaca(String placa) throws DataAccessException {
		VeiculosMensalista veiculo = null;

		try (Connection conn = dataSource.getConnection();
			 PreparedStatement ps = conn
					 .prepareStatement("SELECT cpf, placa, descricao_veiculo FROM veiculos_mensalista WHERE placa = ?;")) {
			ps.setString(1, placa);

			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					veiculo = new VeiculosMensalista();
					veiculo.setCpf(rs.getString("cpf"));
					veiculo.setPlaca(rs.getString("placa"));
					veiculo.setDescricao_veiculo(rs.getString("descricao_veiculo"));
					System.out.println("Banco - "+rs.getString("cpf"));
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return veiculo;
	}
}
