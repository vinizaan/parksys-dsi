package modelo;

public class Mensalista {
	private String cpf;
	private String nome;
	private String telefone;
	private int saldoAtual = 0;
	private String obs;
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public int getSaldoAtual() {
		return saldoAtual;
	}
	public void setSaldoAtual(int saldoAtual) {
		this.saldoAtual = saldoAtual;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
}
