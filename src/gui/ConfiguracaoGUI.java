package gui;

import dao.ConfiguracaoDAO;
import dao.DataAccessException;
import dao.EntradaSaidaDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutionException;

public class ConfiguracaoGUI {

    private JLabel lblDuracao;
    private JComboBox<String> cbxDuracoes;
    private JLabel lblPrecoBloco;
    private JTextField txtPrecoBloco;

    private JLabel lblDescontoMens;
    private JTextField txtDescontoMens;

    private JLabel lblQtdMinimaHoras;
    private JTextField txtQtdMinimaHoras;

    private JLabel lblQtdVagas;
    private JTextField txtQtdVagas;

    private JButton btnSalvar;
    private JButton btnSair;

    private JFrame frame;

    int tipo;

    public ConfiguracaoGUI(int tipo) {
        criarComponentes();
        this.tipo = tipo;
    }

    private void criarComponentes() {
        frame = new JFrame("Configurar Sistema");
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

        preencherTxt();
    }

    private JPanel adicionarComponentes() {
        JPanel panel = new JPanel();

        lblDuracao = new JLabel("Dura��o do Bloco");
        String duracoes[] = { "-" , "1 hora", "2 horas", "3 horas", "4 horas", "5 horas"};
        cbxDuracoes = new JComboBox<String>(duracoes);

        lblPrecoBloco = new JLabel("Pre�o do Bloco");
        txtPrecoBloco = new JTextField(8);

        lblDescontoMens = new JLabel("Desconto para Mensalistas (%)");
        txtDescontoMens = new JTextField(8);

        lblQtdMinimaHoras = new JLabel("Horas M�nimas");
        txtQtdMinimaHoras = new JTextField(8);

        lblQtdVagas = new JLabel("Quantidade de Vagas");
        txtQtdVagas = new JTextField(8);

        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener((ActionEvent e) -> {salvarConfiguracaoFun();});

        btnSair = new JButton("Sair");
        btnSair.addActionListener((ActionEvent e) -> {confirmExit();});

        /* configura��o do layout */
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();

        /* Dura��o do Bloco */
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(lblDuracao, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(cbxDuracoes, gbc);

        /* Pre�o do Bloco */
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(lblPrecoBloco, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(txtPrecoBloco, gbc);

        /* Desconto Mens */
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(lblDescontoMens, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(txtDescontoMens, gbc);

        /* QTD m�nima de horas */
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(lblQtdMinimaHoras, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(txtQtdMinimaHoras, gbc);

        /* QTD vagas */
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(lblQtdVagas, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(txtQtdVagas, gbc);

        /* Permanencia */
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnSalvar, gbc);

        /* SAIR */
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnSair, gbc);

        return panel;
    }

    private void preencherTxt() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws DataAccessException {
                ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO();
                int qtd = configuracaoDAO.getDuracaoBloco();

                if(qtd==1){
                    cbxDuracoes.setSelectedItem("1 hora");
                } else if(qtd==2){
                    cbxDuracoes.setSelectedItem("2 horas");
                } else if(qtd==3){
                    cbxDuracoes.setSelectedItem("3 horas");
                } else if(qtd==4){
                    cbxDuracoes.setSelectedItem("4 horas");
                } else if(qtd==5){
                    cbxDuracoes.setSelectedItem("5 horas");
                }
                txtPrecoBloco.setText(configuracaoDAO.getPrecoBloco().toString());
                Double descontoBanco = configuracaoDAO.getDescontoMensalista();
                Double descontoTxt=0.0;
                if(descontoBanco!=0){
                    descontoTxt = (1-descontoBanco)*100;
                }
                descontoTxt = Double.valueOf(Math.round(descontoTxt));
                txtDescontoMens.setText(descontoTxt.toString());
                txtQtdMinimaHoras.setText(configuracaoDAO.getMinimoHoras().toString());
                txtQtdVagas.setText(configuracaoDAO.getQtdVagas().toString());

                return null;
            }
            @Override
            protected void done() {
                try {
                    get();

                } catch (InterruptedException | ExecutionException e) {
                    JOptionPane.showMessageDialog(null, e, "Erro: ", JOptionPane.ERROR_MESSAGE);                }
            }
        };
        worker.execute();
    }

    private void salvarConfiguracaoFun() {
        Object duracao = cbxDuracoes.getSelectedItem();
        float precoBloco = Float.parseFloat(txtPrecoBloco.getText());
        double descontoTxt = Double.parseDouble(txtDescontoMens.getText());
        int qtdMin = Integer.parseInt(txtQtdMinimaHoras.getText());
        int qtdVagas = Integer.parseInt(txtQtdVagas.getText());

        EntradaSaidaDAO entradaSaidaDAO = new EntradaSaidaDAO();
        int qtdVagasUsadas = 0;
        try {
            qtdVagasUsadas = entradaSaidaDAO.contaRegistrosBanco();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        if (duracao.equals("-")) {
            JOptionPane.showMessageDialog(null, "Escolha as Dura��es de Blocos dispon�veis.");
            return;
        } else if (precoBloco <= 0) {
            JOptionPane.showMessageDialog(null, "Digite o Pre�o do Bloco maior que zero.");
            return;
        } else if (descontoTxt <= 0) {
            JOptionPane.showMessageDialog(null, "Digite o Desconto para Mensalistas maior que zero.");
            return;
        } else if (qtdVagas <= 0) {
            JOptionPane.showMessageDialog(null, "Digite a Quantidade de Vagas maior que zero.");
            return;
        } else if (qtdVagasUsadas > qtdVagas){
            JOptionPane.showMessageDialog(null, "Digite a Quantidade de Vagas maior que a quantidade de vagas em uso.");
            return;
        } else if(qtdMin<=0){
            JOptionPane.showMessageDialog(null, "Digite a Quantidade M�nima de Horas para Desconto maior que zero.");
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws DataAccessException {
                int duracaoInt=1; // padr�o
                if(duracao.equals("1 hora")){
                    duracaoInt = 1;
                } else if(duracao.equals("2 horas")){
                    duracaoInt = 2;
                } else if (duracao.equals("3 horas")){
                    duracaoInt = 3;
                } else if (duracao.equals("4 horas")){
                    duracaoInt = 4;
                } else if (duracao.equals("5 horas")){
                    duracaoInt = 5;
                }
                double descontoBanco = (100-descontoTxt)/100;
                //System.out.println(descontoBanco);
                ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO();
                if(tipo==1){
                    configuracaoDAO.atualizaConfiguracao(duracaoInt, precoBloco, descontoBanco, qtdVagas, qtdMin);
                } else if(tipo==2){
                    configuracaoDAO.criaConfiguracao(duracaoInt, precoBloco, descontoBanco, qtdVagas, qtdMin);
                }
                return null;
            }
            @Override
            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(null, "Configura��es alteradas com sucesso.");

                } catch (InterruptedException | ExecutionException e) {
                    JOptionPane.showMessageDialog(null, e, "Erro: ", JOptionPane.ERROR_MESSAGE);                }
            }
        };
        worker.execute();
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

}
