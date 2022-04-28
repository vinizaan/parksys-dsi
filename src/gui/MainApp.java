package gui;

import dao.ConfiguracaoDAO;
import dao.DataAccessException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainApp {
    private JButton btnCadastrarMens;
    private JButton btnEntSaida;
    private JButton btnConsultar;
    private JButton btnSair;
    private JButton btnGerVeic;
    private JButton btnFazerPagMens;
    private JButton btnConfigurar;

    private JFrame frame;

    public MainApp() {
        criarComponentes();
    }

    private void criarComponentes() {
        frame = new JFrame("App");
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

    private JPanel adicionarComponentes() {
        JPanel panel = new JPanel();

        btnCadastrarMens = new JButton("Cadastrar Mensalista");
        btnCadastrarMens.addActionListener((ActionEvent e) -> {cadastrarMensalistaFun();});

        btnEntSaida = new JButton("Entrada/Sa�da");
        btnEntSaida.addActionListener((ActionEvent e) -> {entSaidaFun();});

        btnConsultar = new JButton("Consultar Gr�ficos");
        btnConsultar.addActionListener((ActionEvent e) -> {consultarFun();});

        btnGerVeic = new JButton("Gerenciar Ve�culos");
        btnGerVeic.addActionListener((ActionEvent e) -> {gerenciarVeiFun();});

        btnFazerPagMens = new JButton("Fazer Pagamento Mensal");
        btnFazerPagMens.addActionListener((ActionEvent e) -> {fazerPagamentoMensalFun();});

        btnConfigurar = new JButton("Configurar Sistema");
        btnConfigurar.addActionListener((ActionEvent e) -> {configurarSistFun();});

        btnSair = new JButton("Sair");
        btnSair.addActionListener((ActionEvent e) -> {confirmExit();});

        /* configura��o do layout */
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        /* CADASTRARMENSALISTA */
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnCadastrarMens, gbc);

        /* ENTRADA/SA�DA */
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnEntSaida, gbc);

        /* Gerenciar Veiculos */
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnGerVeic, gbc);

        /* Fazer Pagamento Mensal */
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnFazerPagMens, gbc);

        /* Consultar */
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnConsultar, gbc);

        /* Configurar Sistema */
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnConfigurar, gbc);

        /* SAIR */
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnSair, gbc);

        return panel;
    }

    private void configurarSistFun() {
        ConfiguracaoGUI configuracaoGUI = new ConfiguracaoGUI(1);
    }

    private void gerenciarVeiFun() {
        GerenciarVeiculosGUI gerenciarVeiculosGUI = new GerenciarVeiculosGUI();
    }

    private void consultarFun() {
        ConsultarGUI consultarGUI = new ConsultarGUI();
    }

    private void entSaidaFun() {
        EntradaSaidaGUI entradaSaidaGUI = new EntradaSaidaGUI();
    }

    private void cadastrarMensalistaFun() {
        CadastrarMensalistaGUI cadastrarMensalistaGUI = new CadastrarMensalistaGUI();
    }

    private void fazerPagamentoMensalFun(){
        FazerPagamentoMensalGUI fazerPagamentoMensalGUI = new FazerPagamentoMensalGUI();
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

    private static boolean verificarConfig() throws DataAccessException {
        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO();
        boolean existe = configuracaoDAO.getConfiguracao();
        return existe;
    }

    public static void main(String[] args) {
        boolean existe = false;
        try {
            existe = verificarConfig();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        if(!existe){
            MainApp mainApp = new MainApp();
            ConfiguracaoGUI configuracaoGUI = new ConfiguracaoGUI(2);
        } else {
            MainApp mainApp = new MainApp();
        }

    }
}
