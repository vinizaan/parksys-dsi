package gui;

import dao.ConfiguracaoDAO;
import dao.DataAccessException;
import dao.MensalistaDAO;
import dao.VeiculosMensalistaDAO;
import modelo.Mensalista;
import modelo.VeiculosMensalista;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public class GerenciarVeiculosGUI {

    private JFrame frame;
    private JLabel lblBusca;
    private JTextField txtBusca;
    private JLabel lblCPF;
    private JTextField txtCPF;
    private JLabel lblNome;
    private JTextField txtNome;
    private JLabel lblTelefone;
    private JTextField txtTelefone;
    private JLabel lblSaldoDinhero;
    private JTextField txtSaldoDinhero;
    private JLabel lblSaldoHoras;
    private JTextField txtSaldoHoras;
    private JLabel lblPlaca;
    private JTextField txtPlaca;
    private JLabel lblDescricao;
    private JTextField txtDescricao;
    private JButton btnConsultar;
    private JButton btnSalvar;
    private JButton btnSair;

    public GerenciarVeiculosGUI() {
        criarComponentes();
    }

    private void criarComponentes() {
        frame = new JFrame("Gerenciar Ve�culos");
        frame.getContentPane().setLayout(new BorderLayout());
        JPanel panel = adicionarComponentes();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmExit();
            }
        });
        frame.setVisible(true);
    }

    private void confirmExit() {
        int answer = JOptionPane.showConfirmDialog(frame,
                "Deseja sair?",
                "Sair",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }

    private JPanel adicionarComponentes() {
        JPanel panel = new JPanel();

        lblBusca = new JLabel("Buscar Mensalista");
        txtBusca = new JTextField(10);

        lblCPF = new JLabel("CPF");
        txtCPF = new JTextField(10);
        txtCPF.setEditable(false);

        lblNome = new JLabel("Nome");
        txtNome = new JTextField(13);
        txtNome.setEditable(false);

        lblTelefone = new JLabel("Telefone");
        txtTelefone = new JTextField(10);
        txtTelefone.setEditable(false);

        lblSaldoDinhero = new JLabel("Saldo (em reais)");
        txtSaldoDinhero = new JTextField(10);
        txtSaldoDinhero.setEditable(false);

        lblSaldoHoras = new JLabel("Saldo (em horas)");
        txtSaldoHoras = new JTextField(10);
        txtSaldoHoras.setEditable(false);

        lblPlaca = new JLabel("Placa");
        try {
            txtPlaca = new JFormattedTextField(new MaskFormatter("???A###"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtPlaca.setEditable(false);

        lblDescricao = new JLabel("Descri��o");
        txtDescricao = new JTextField(10);
        txtDescricao.setEditable(false);

        btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener((ActionEvent e) -> {consultarMensalista();});

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener((ActionEvent e) -> {salvarVeiculo();});
        btnSalvar.setEnabled(false);

        btnSair = new JButton("Sair");
        btnSair.addActionListener((ActionEvent e) -> {confirmExit();});

        /* configura��o do layout */
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        // Busca
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblBusca, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtBusca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblCPF, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtCPF, gbc);

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblNome, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtNome, gbc);

        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblTelefone, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(txtTelefone, gbc);

        // Saldo em reais
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblSaldoDinhero, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(txtSaldoDinhero, gbc);

        // Saldo em horas
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblSaldoHoras, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtSaldoHoras, gbc);

        // Placa
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblPlaca, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtPlaca, gbc);

        // Descricao
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblDescricao, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtDescricao, gbc);

        // BTN CONSULTAR
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(btnConsultar, gbc);

        // BTN Salvar
        gbc.gridx = 2;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(btnSalvar, gbc);

        // BTN Sair
        gbc.gridx = 4;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.WEST;
        panel.add(btnSair, gbc);

        return panel;
    }

    private void salvarVeiculo() {
        String placa = txtPlaca.getText();
        String descricao = txtDescricao.getText();
        String cpf = txtCPF.getText();
        
        if (placa.isBlank() || descricao.isEmpty()){
            JOptionPane.showMessageDialog(null, "Os campos de Placa e Descri��o s�o obrigat�rios.");
            return;
        }

        VeiculosMensalista veiculoMensalista = new VeiculosMensalista();
        veiculoMensalista.setCpf(cpf);
        veiculoMensalista.setPlaca(placa);
        veiculoMensalista.setDescricao_veiculo(descricao);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws DataAccessException {
                VeiculosMensalistaDAO veiculosMensalistaDAO = new VeiculosMensalistaDAO();
                veiculosMensalistaDAO.cadastraVeiculo(veiculoMensalista);
                return null;
            }
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(null, "Ve�culo salvo com sucesso!");
                    txtPlaca.setText("");
                    txtDescricao.setText("");
                } catch (InterruptedException | ExecutionException e) {
                    JOptionPane.showMessageDialog(null, e, "Erro: ", JOptionPane.ERROR_MESSAGE);                }
            }
        };
        worker.execute();
    }

    private void consultarMensalista() {
        String buscaText = txtBusca.getText();
        String regexCPF = "^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$";
        String regexCel = "^\\([0-9]{2}\\)[0-9]{5}\\-[0-9]{4}$";
        //String regexCel = "^\\([1-9]{2}\\)(?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}$";

        if (buscaText.isBlank()){
            JOptionPane.showMessageDialog(null, "O campo de busca n�o pode estar vazio.");
        } else if (buscaText.matches(regexCPF)){
            verificarMensalista(buscaText, 1);
        } else if (buscaText.matches(regexCel)){
            verificarMensalista(buscaText, 2);
        } else {
            JOptionPane.showMessageDialog(null, "Busque o Mensalista seguindo as express�es:\n\n" +
                    "-                         CPF:    XXX.XXX.XXX-XX \n" +
                    "- Telefone Celular:    (XX)XXXXX-XXXX");
        }
    }

    public void verificarMensalista(String buscaText, int tipo){
        SwingWorker<Mensalista, Void> worker = new SwingWorker<>() {
            @Override
            protected Mensalista doInBackground() throws DataAccessException {
                MensalistaDAO mensalistaDAO = new MensalistaDAO();
                Mensalista mensalista = null;
                if (tipo==1){
                    mensalista = mensalistaDAO.findByCPF(buscaText);
                } else if (tipo==2){
                    mensalista = mensalistaDAO.findByTelefone(buscaText);
                }
                return mensalista;
            }

            @Override
            protected void done() {
                try {
                    Mensalista mensalista = get();
                    if (mensalista==null){
                        JOptionPane.showMessageDialog(null, "Mensalista n�o encontrado.");
                    } else {
                        txtNome.setText(mensalista.getNome());
                        txtNome.setCaretPosition(0);
                        txtNome.setEditable(false);

                        txtCPF.setText(mensalista.getCpf());
                        txtCPF.setEditable(false);

                        txtTelefone.setText(mensalista.getTelefone());
                        txtTelefone.setEditable(false);

                        double saldoAtual = mensalista.getSaldoAtual()/60;
                        double saldoDinheiro=0;
                        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO();
                        try {
                            saldoDinheiro = configuracaoDAO.getPrecoBloco()*saldoAtual*configuracaoDAO.getDescontoMensalista();
                            saldoDinheiro = (double) Math.round(saldoDinheiro*100)/100;
                        } catch (DataAccessException e) {
                            e.printStackTrace();
                        }

                        txtSaldoDinhero.setText(String.valueOf(saldoDinheiro));
                        txtSaldoDinhero.setEditable(false);

                        txtSaldoHoras.setText(String.valueOf(saldoAtual));
                        txtSaldoHoras.setEditable(false);

                        btnSalvar.setEnabled(true);
                        txtPlaca.setEditable(true);
                        txtDescricao.setEditable(true);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    JOptionPane.showMessageDialog(frame, e, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }
}

