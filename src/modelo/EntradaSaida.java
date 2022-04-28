package modelo;


public class EntradaSaida {
	
	private String descricaoVeiculo;
	private String placa;
	private boolean mensalista;
	private String dataHoraEntrada;
	private String dataHoraSaida;
	private int tempoPermanencia;
	private double precoTotal;

	public String getDescricaoVeiculo() {
		return descricaoVeiculo;
	}
	public void setDescricaoVeiculo(String descricaoVeiculo) {
		this.descricaoVeiculo = descricaoVeiculo;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public boolean isMensalista() {
		return mensalista;
	}
	public void setMensalista(boolean mensalista) {
		this.mensalista = mensalista;
	}

	public String getDataHoraEntrada() {
		return dataHoraEntrada;
	}
	public void setDataHoraEntrada(String dataHoraEntrada) {
		this.dataHoraEntrada = dataHoraEntrada;
	}
	public String getDataHoraSaida() {
		return dataHoraSaida;
	}
	public void setDataHoraSaida(String dataHoraSaida) {
		this.dataHoraSaida = dataHoraSaida;
	}
	
	
	public int getTempoPermanencia() {
		return tempoPermanencia;
	}
	public void setTempoPermanencia(int tempoPermanencia) {
		this.tempoPermanencia = tempoPermanencia;
	}
	public double getPrecoTotal() {
		return precoTotal;
	}
	public void setPrecoTotal(double precoTotal) {
		this.precoTotal = precoTotal;
	}
	
}
