package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConfiguracaoDAO {
	private ParkSysDataSource dataSource = new ParkSysDataSource();

	public void atualizaConfiguracao(int duracaoBloco, float precoBloco, double  descontoMensalista, int qtdVagas, int minimoHoras) throws DataAccessException {
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);

			try (
					PreparedStatement update = conn
							.prepareStatement("UPDATE configuracao SET duracao_bloco = ?, preco_bloco = ?,"
									+ "desconto_mensalista = ?, qtd_vagas = ?, minimo_horas=?;");) {

				update.setInt(1, duracaoBloco);
				update.setFloat(2, precoBloco);
				update.setDouble(3, descontoMensalista);
				update.setInt(4, qtdVagas);
				update.setInt(5, minimoHoras);
			
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

	public void criaConfiguracao(int duracaoBloco, float precoBloco, double  descontoMensalista, int qtdVagas, int minimoHoras) throws DataAccessException {
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);

			try (
					PreparedStatement insert = conn
							.prepareStatement("insert into configuracao (duracao_bloco, preco_bloco,"
									+ "desconto_mensalista, qtd_vagas, minimo_horas) " +
									"VALUES (?,?,?,?,?);");) {

				insert.setInt(1, duracaoBloco);
				insert.setFloat(2, precoBloco);
				insert.setDouble(3, descontoMensalista);
				insert.setInt(4, qtdVagas);
				insert.setInt(5, minimoHoras);

				insert.executeUpdate();

				conn.commit();

			} catch(SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
	public void setQtdVagas(int qtdVagas) throws DataAccessException {
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (
					PreparedStatement update = conn
							.prepareStatement("UPDATE configuracao SET qtd_vagas = ?;");) {

				update.setInt(1, qtdVagas);
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
	
	public Integer getDuracaoBloco() throws DataAccessException {
		int duracaoBloco = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn
						.prepareStatement("select duracao_bloco from configuracao;")) {
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) { duracaoBloco = rs.getInt("duracao_bloco"); }
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return duracaoBloco;
	}
	
	public Float getPrecoBloco() throws DataAccessException {
		float precoBloco = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn
						.prepareStatement("select preco_bloco from configuracao;")) {
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) { precoBloco = rs.getFloat("preco_bloco"); }
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return precoBloco;
	}
	
	public Double getDescontoMensalista() throws DataAccessException {
		double descontoMensalista = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn
						.prepareStatement("select desconto_mensalista from configuracao;")) {
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) { descontoMensalista = rs.getDouble("desconto_mensalista"); }
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return descontoMensalista;
	}
	
	public Integer getQtdVagas() throws DataAccessException {
		int qtdVagas = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn
						.prepareStatement("select qtd_vagas from configuracao;")) {
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) { qtdVagas = rs.getInt("qtd_vagas"); }
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return qtdVagas;
	}
	
	public Integer getMinimoHoras() throws DataAccessException {
		int minimoHoras = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn
						.prepareStatement("select minimo_horas from configuracao;")) {
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) { minimoHoras = rs.getInt("minimo_horas"); }
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
		return minimoHoras;
	}

	public Boolean getConfiguracao() throws DataAccessException {
		int qtd = 0;
		try (Connection conn = dataSource.getConnection();
			 PreparedStatement ps = conn
					 .prepareStatement("select count(*) as config from configuracao;")) {
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					qtd = rs.getInt("config");
					if (qtd>0){
						return true;
					}
				}
			}
			return false;
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}
	
}
