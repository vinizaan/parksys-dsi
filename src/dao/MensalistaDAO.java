package dao;

import modelo.Mensalista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MensalistaDAO {
	
	private ParkSysDataSource dataSource = new ParkSysDataSource();

	public void cadastraMensalista(Mensalista mensalista) throws DataAccessException {
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);

			try (
					PreparedStatement cadastro = conn
							.prepareStatement("INSERT INTO mensalista (cpf, nome, telefone, obs) "
									+ "VALUES (?, ?, ?, ?);");) {

				cadastro.setString(1, mensalista.getCpf());
				cadastro.setString(2, mensalista.getNome());
				cadastro.setString(3, mensalista.getTelefone());
				cadastro.setString(4, mensalista.getObs());
				cadastro.executeUpdate();

				conn.commit();

			} catch(SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public Mensalista findByCPF(String cpf) throws DataAccessException {
        Mensalista mensalista = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn
                     .prepareStatement("SELECT cpf, nome, telefone, saldo_atual, obs FROM mensalista WHERE cpf = ?;")) {
            ps.setString(1, cpf);

            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    mensalista = new Mensalista();
                    mensalista.setCpf(rs.getString("cpf"));
                    mensalista.setNome(rs.getString("nome"));
                    mensalista.setTelefone(rs.getString("telefone"));
                    mensalista.setSaldoAtual(rs.getInt("saldo_atual"));
                    mensalista.setObs(rs.getString("obs"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return mensalista;
    }

	public Mensalista findByTelefone(String telefone) throws DataAccessException {
        Mensalista mensalista = null;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn
                     .prepareStatement("SELECT cpf, nome, telefone, saldo_atual, obs FROM mensalista WHERE telefone = ?;")) {
            ps.setString(1, telefone);

            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    mensalista = new Mensalista();
                    mensalista.setCpf(rs.getString("cpf"));
                    mensalista.setNome(rs.getString("nome"));
                    mensalista.setTelefone(rs.getString("telefone"));
                    mensalista.setSaldoAtual(rs.getInt("saldo_atual"));
                    mensalista.setObs(rs.getString("obs"));
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }

        return mensalista;
    }
	
	public void atualizarSaldo(int saldo, String cpf) throws DataAccessException {
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);

			try (
					PreparedStatement cadastro = conn
							.prepareStatement("UPDATE mensalista SET saldo_atual = ? WHERE cpf = ?;");) {

				cadastro.setInt(1, saldo);
				cadastro.setString(2, cpf);
				cadastro.executeUpdate();

				conn.commit();

			} catch(SQLException e) {
				conn.rollback();
				throw e;
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
	}

	public String findByPlaca(String placa) throws DataAccessException {
		String cpf = null;

		try (Connection conn = dataSource.getConnection();
			 PreparedStatement ps = conn
					 .prepareStatement("SELECT cpf FROM mensalista WHERE cpf = (select cpf from veiculos_mensalista where placa = ?);")) {
			ps.setString(1, placa);

			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					cpf = rs.getString("cpf");
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return cpf;
	}


	
}


	