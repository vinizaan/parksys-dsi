package dao;

import modelo.EntradaSaida;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EntradaSaidaDAO {
	
private ParkSysDataSource dataSource = new ParkSysDataSource();
	
	public void cadastraEntrada(EntradaSaida entradasaida) throws DataAccessException {
		
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			
				try (
						PreparedStatement cadastroEntrada = conn
								.prepareStatement("INSERT INTO entrada_saida "
										+ "(descricao_veiculo, placa, mensalista, data_hora_entrada) "
										+ "VALUES (?, ?, ?, ?);");) {
					
						
					cadastroEntrada.setString(1, entradasaida.getDescricaoVeiculo());
					cadastroEntrada.setString(2, entradasaida.getPlaca());
					cadastroEntrada.setBoolean(3, entradasaida.isMensalista());
					cadastroEntrada.setString(4, entradasaida.getDataHoraEntrada());
					
					
					cadastroEntrada.executeUpdate();				
					
					conn.commit();
	
				} catch(SQLException e) {
					conn.rollback();
					throw e;
				}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
			
	}
	
public void cadastraSaida(EntradaSaida entradasaida) throws DataAccessException {
		
		try (Connection conn = dataSource.getConnection()) {
			conn.setAutoCommit(false);
			try (
						
						PreparedStatement registraSaida = conn
						.prepareStatement("UPDATE entrada_saida SET data_hora_saida = ?, tempo_permanencia = ?, preco_total = ?"
								+ " WHERE placa = ? AND data_hora_entrada = ?;");) {
					

						registraSaida.setString(1, entradasaida.getDataHoraSaida());
						registraSaida.setInt(2, entradasaida.getTempoPermanencia());
						registraSaida.setDouble(3, entradasaida.getPrecoTotal());
						registraSaida.setString(4, entradasaida.getPlaca());
						registraSaida.setString(5, entradasaida.getDataHoraEntrada());
						
						registraSaida.executeUpdate();				
						
						conn.commit();
	
				} catch(SQLException e) {
					conn.rollback();
					throw e;
				}
				
			
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}
			
	}
	public Integer contaRegistrosBanco() throws DataAccessException {
		int cont = 0;

		try (Connection conn = dataSource.getConnection();
				PreparedStatement ps = conn
						.prepareStatement("select count(*) from entrada_saida where data_hora_saida IS null;")) {

			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					cont = rs.getInt("count(*)");
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return cont;
	}

	public EntradaSaida findByNumeroPlaca(String placa) throws DataAccessException {
		EntradaSaida entradaSaida = null;

		try (Connection conn = dataSource.getConnection();
			 PreparedStatement ps = conn
					 .prepareStatement("select descricao_veiculo, placa, mensalista, data_hora_entrada, data_hora_saida,"
					 		+ " tempo_permanencia, preco_total from entrada_saida where placa = ?  order by data_hora_entrada DESC LIMIT 1;")) {
			ps.setString(1, placa);

			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					entradaSaida = new EntradaSaida();
					entradaSaida.setDescricaoVeiculo(rs.getString("descricao_veiculo"));
					entradaSaida.setPlaca(rs.getString("placa"));
					entradaSaida.setMensalista(rs.getBoolean("mensalista"));
					entradaSaida.setDataHoraEntrada(rs.getString("data_hora_entrada"));
					entradaSaida.setDataHoraSaida(rs.getString("data_hora_saida"));
					entradaSaida.setTempoPermanencia(rs.getInt("tempo_permanencia"));
					entradaSaida.setPrecoTotal(rs.getDouble("preco_total"));
					
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return entradaSaida;
	}


	public EntradaSaida findByNumeroPlacaSaida(String placa, String dataHoraEntrada) throws DataAccessException {
		EntradaSaida entradaSaida = null;
		
		try (Connection conn = dataSource.getConnection();
			
			 PreparedStatement ps = conn
					 .prepareStatement("select descricao_veiculo, placa, mensalista, data_hora_entrada, data_hora_saida,"
					 		+ " tempo_permanencia, preco_total from entrada_saida "
					 		+ "where placa = ? AND data_hora_saida is null AND data_hora_entrada = ?;")) {
			ps.setString(1, placa);
			ps.setString(2, dataHoraEntrada);
			

			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					entradaSaida = new EntradaSaida();
					entradaSaida.setDescricaoVeiculo(rs.getString("descricao_veiculo"));
					entradaSaida.setPlaca(rs.getString("placa"));
					entradaSaida.setMensalista(rs.getBoolean("mensalista"));
					entradaSaida.setDataHoraEntrada(rs.getString("data_hora_entrada"));
					entradaSaida.setDataHoraSaida(rs.getString("data_hora_saida"));
					entradaSaida.setTempoPermanencia(rs.getInt("tempo_permanencia"));
					entradaSaida.setPrecoTotal(rs.getDouble("preco_total"));
					
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException(e);
		}

		return entradaSaida;
	}

    public List<Integer> buscarPermanencias(String dataInicio, String dataFinal) {
		List<Integer> dados = new ArrayList<>();

		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("Select AVG(tempo_permanencia) AS media from entrada_saida "
					+ "where data_hora_saida > ? and data_hora_saida < ? "
					+ "group by DATE_FORMAT(data_hora_saida, \"%d\") order by data_hora_saida asc;");
			ps.setString(1, dataInicio);
			ps.setString(2, dataFinal);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				int tempoPermanencia = rs.getInt("media");
				if (tempoPermanencia != 0){
					dados.add(tempoPermanencia);
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

		return dados;
    }
    
    
    public List<Float> buscarFaturamento(String dataInicio, String dataFinal) {
		List<Float> dados = new ArrayList<>();
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("Select AVG(preco_total) AS media from entrada_saida "
					+ "where data_hora_saida > ? and data_hora_saida < ? "
					+ "group by DATE_FORMAT(data_hora_saida, \"%d\") order by data_hora_saida asc;");
			ps.setString(1, dataInicio);
			ps.setString(2, dataFinal);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				float faturamento = rs.getFloat("media");
				if (faturamento != 0){
					dados.add(faturamento);
				}
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return dados;
    }
    
    public List<String> buscarDatas(String dataInicio, String dataFinal) {
		List<String> dados = new ArrayList<>();
		try {
			Connection conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement("Select DATE_FORMAT(data_hora_saida, \"%Y-%m-%d\") AS data from entrada_saida \n"
					+ "	where data_hora_saida > ? and data_hora_saida < ? \n"
					+ "		group by DATE_FORMAT(data_hora_saida, \"%d\") order by data_hora_saida asc; ");
			ps.setString(1, dataInicio);
			ps.setString(2, dataFinal);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				String data = rs.getString("data");
				if (data != null){
					dados.add(data);
				}
				
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return dados;
    }
}