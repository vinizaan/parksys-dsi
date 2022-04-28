package gui;

import dao.*;
import modelo.EntradaSaida;
import modelo.Mensalista;
import modelo.VeiculosMensalista;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class EntradaSaidaGUI {
    private JLabel lblPlacaInicio;
    private JLabel lblPlacaEntrada;
    private JLabel lblPlacaSaida;
    private JTextField txtPlacaInicio;
    private JTextField txtPlacaEntrada;
    private JTextField txtPlacaSaida;
    private JLabel lblDesc;
    private JTextField txtDesc;
    private JLabel lblHorarioEntEntrada;
    private JLabel lblHorarioEntSaida;
    private JTextField txtHorEntEntrada;
    private JTextField txtHorEntSaida;
    private JLabel lblHorarioSaida;
    private JTextField txtHorSaida;
    private JLabel lblPermanencia;
    private JTextField txtPermanencia;
    private JLabel lblDuracaoBlocoEntrada;
    private JLabel lblDuracaoBlocoSaida;
    private JTextField txtDuracaoBlocoEntrada;
    private JTextField txtDuracaoBlocoSaida;
    private JLabel lblQtdBlocoDisp;
    private JLabel lblQtdBlocoSaida;
    private JTextField txtQtdBlocoSaida;
    private JLabel lblPrecoBlocoEntrada;
    private JTextField txtPrecoBlocoEntrada;
    private JLabel lblPrecoBlocoSaida;
    private JTextField txtPrecoBlocoSaida;
    private JLabel lblTotal;
    private JTextField txtTotal;

    private JLabel lblMensalistaAvulsoEntrada;
    private JLabel lblMensalistaAvulsoSaida;

    private JButton btnConsultar;
    private JButton btnRegistrarEnt;
    private JButton btnRegistrarSaida;
    private JButton btnSairInicio;
    private JButton btnSairEntrada;
    private JButton btnSairSaida;


    private JPanel cards = new JPanel(new CardLayout());
    String panelInicio = "Card Inicial";
    String panelEntrada = "Card Entrada";
    String panelSaida = "Card Sa�da";
    int qtdVagas;

    private JFrame frame;

    public EntradaSaidaGUI() {
        criarComponentes();
    }

    private void criarComponentes() {
        frame = new JFrame("Entrada/Sa�da");
        frame.getContentPane().setLayout(new BorderLayout());
        JPanel panel = adicionarComponentes();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
//        frame.setSize(600,200);
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
        JPanel cardInicio = new JPanel();
        JPanel cardEntrada = new JPanel();
        JPanel cardSaida = new JPanel();

        lblPlacaInicio = new JLabel("Placa do Ve�culo: ");
        lblPlacaEntrada = new JLabel("Placa do Ve�culo: ");
        lblPlacaSaida = new JLabel("Placa do Ve�culo: ");
        try {
            txtPlacaInicio = new JFormattedTextField(new MaskFormatter("???A###"));
            txtPlacaEntrada = new JFormattedTextField(new MaskFormatter("???A###"));
            txtPlacaSaida = new JFormattedTextField(new MaskFormatter("???A###"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtPlacaInicio.setColumns(5);
        txtPlacaEntrada.setColumns(5);
        txtPlacaSaida.setColumns(5);
        txtPlacaInicio.setHorizontalAlignment(SwingConstants.RIGHT);
        txtPlacaEntrada.setHorizontalAlignment(SwingConstants.RIGHT);
        txtPlacaSaida.setHorizontalAlignment(SwingConstants.RIGHT);

        lblDesc = new JLabel("Descri��o do Ve�culo:");
        txtDesc = new JTextField(8);

        lblHorarioEntEntrada = new JLabel("Hor�rio de Entrada:");
        txtHorEntEntrada = new JTextField(8);
        lblHorarioEntSaida = new JLabel("Hor�rio de Entrada:");
        txtHorEntSaida = new JTextField(8);
        lblHorarioSaida = new JLabel("Hor�rio de Sa�da:");
        txtHorSaida = new JTextField(8);
        lblPermanencia = new JLabel("Tempo de Perman�ncia:");
        txtPermanencia = new JTextField(8);

        lblDuracaoBlocoEntrada = new JLabel("Dura��o do Bloco (horas)");
        txtDuracaoBlocoEntrada = new JTextField(8);
        lblDuracaoBlocoSaida = new JLabel("Dura��o do Bloco (horas)");
        txtDuracaoBlocoSaida = new JTextField(8);

        qtdVagas = buscarNumeroVagas();
        if(qtdVagas>0){
            lblQtdBlocoDisp = new JLabel("Quantidade de Blocos: "+qtdVagas);
        } else {
            lblQtdBlocoDisp = new JLabel("Estacionamento Cheio");
        }

        lblQtdBlocoSaida = new JLabel("Quantidade de Blocos:");
        txtQtdBlocoSaida = new JTextField(8);

        lblPrecoBlocoEntrada = new JLabel("Pre�o do Bloco:");
        txtPrecoBlocoEntrada = new JTextField(8);
        lblPrecoBlocoSaida = new JLabel("Pre�o do Bloco:");
        txtPrecoBlocoSaida = new JTextField(8);

        lblTotal = new JLabel("Total a pagar:");
        txtTotal = new JTextField(8);

        lblMensalistaAvulsoEntrada = new JLabel("Avulso");
        lblMensalistaAvulsoSaida = new JLabel("Avulso");

        btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener((ActionEvent e) -> {consultarPlaca();});

        btnSairInicio = new JButton("Sair");
        btnSairEntrada = new JButton("Sair");
        btnSairSaida = new JButton("Sair");
        btnSairInicio.addActionListener((ActionEvent e) -> {confirmExit();});
        btnSairEntrada.addActionListener((ActionEvent e) -> {confirmExit();});
        btnSairSaida.addActionListener((ActionEvent e) -> {confirmExit();});

        btnRegistrarEnt = new JButton("Registrar Entrada");
        btnRegistrarEnt.addActionListener((ActionEvent e) -> {registrarEntrada();});
        btnRegistrarSaida = new JButton("Registrar Saida");
        btnRegistrarSaida.addActionListener((ActionEvent e) -> {registrarSaida();});


        /* configura��o do layout */
        cardInicio.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        cardEntrada = montarPanelEntrada();
        cardSaida = montarPanelSaida();

        /* PLACA */
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        cardInicio.add(lblPlacaInicio, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        cardInicio.add(txtPlacaInicio, gbc);

        /* QUANTIDADE DE BLOCO */
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        cardInicio.add(lblQtdBlocoDisp, gbc);

        /* Consultar */
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        cardInicio.add(btnConsultar, gbc);

        /* SAIR Inicio */
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        cardInicio.add(btnSairInicio, gbc);

        cards.add(cardInicio, panelInicio);
        cards.add(cardEntrada, panelEntrada);
        cards.add(cardSaida, panelSaida);

        return cards;
    }

    private JPanel montarPanelEntrada() {
        JPanel panel = new JPanel();

        panel.setSize(1000,1000);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();

        /* DESCRI��O */
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblDesc, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtDesc, gbc);

        /* PLACA */
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblPlacaEntrada, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtPlacaEntrada, gbc);

        /* HORARIO DE ENTRADA */
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblHorarioEntEntrada, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(txtHorEntEntrada, gbc);

        /* DURA��O DO BLOCO */
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblDuracaoBlocoEntrada, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(txtDuracaoBlocoEntrada, gbc);

        /* PRE�O DO BLOCO */
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblPrecoBlocoEntrada, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtPrecoBlocoEntrada, gbc);

        /* MENSALISTA/AVULSO */
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lblMensalistaAvulsoEntrada, gbc);

        /* Bot�o Cadastar */
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnRegistrarEnt, gbc);

        /* Bot�o Saida */
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnSairEntrada, gbc);

        return panel;
    }

    private JPanel montarPanelSaida() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();

        /* PLACA */
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblPlacaSaida, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtPlacaSaida, gbc);

        /* MENSALISTA/AVULSO */
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lblMensalistaAvulsoSaida, gbc);


        /* Horario Entrada */
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblHorarioEntSaida, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtHorEntSaida, gbc);

        /* Horario Saida */
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblHorarioSaida, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtHorSaida, gbc);

        /* Tempo de Permanencia */
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblPermanencia, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtPermanencia, gbc);

        /* Duracao do Bloco */
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblDuracaoBlocoSaida, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtDuracaoBlocoSaida, gbc);

        /* Preco do Bloco */
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblPrecoBlocoSaida, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtPrecoBlocoSaida, gbc);

        /* Qtd de Blocos */
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblQtdBlocoSaida, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtQtdBlocoSaida, gbc);

        /* Total a pagar */
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblTotal, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtTotal, gbc);

        /* Registrar Saida */
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnRegistrarSaida, gbc);

        /* Sair */
        gbc.gridx = 3;
        gbc.gridy = 7;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnSairSaida, gbc);

        return panel;
    }

    private void registrarEntrada() {
        if (buscarNumeroVagas()==0){
            JOptionPane.showMessageDialog(null, "Estacionamento Cheio!");
            return;
        }

        CardLayout cl = (CardLayout)(cards.getLayout());

        String descricao = txtDesc.getText();
        String placa = txtPlacaEntrada.getText();

        Boolean mensalista = null;
        if (lblMensalistaAvulsoEntrada.getText().equals("Mensalista")){
            mensalista = true;
        } else {
            mensalista = false;
        }

        String dataHora = txtHorEntEntrada.getText();

        if (descricao == null || placa == null || dataHora == null || descricao.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos os campos obrigatorios devem ser preenchidos.");
            return;
        }

        EntradaSaida entradaSaida = new EntradaSaida();
        entradaSaida.setDescricaoVeiculo(descricao);
        entradaSaida.setPlaca(placa);
        entradaSaida.setMensalista(mensalista);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        dataHora = dateFormat.format(date);
        entradaSaida.setDataHoraEntrada(dataHora);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws DataAccessException {
                EntradaSaidaDAO dao = new EntradaSaidaDAO();
                dao.cadastraEntrada(entradaSaida);
                return null;
            }

            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(null, "Entrada Registrada com sucesso!");

                    cl.show(cards, panelInicio);
                    txtPlacaInicio.setText("");

                    qtdVagas = buscarNumeroVagas();

                    if (qtdVagas>0){
                        JOptionPane.showMessageDialog(null, "Vagas restantes: " + qtdVagas);
                        lblQtdBlocoDisp.setText("Quantidade de Blocos: " + qtdVagas);
                    } else {
                        JOptionPane.showMessageDialog(null, "Vagas restantes: " + qtdVagas);
                        lblQtdBlocoDisp.setText("Estacionamento Cheio");
                    }


                } catch (InterruptedException | ExecutionException e) {
                    JOptionPane.showMessageDialog(null, e, "Erro: ", JOptionPane.ERROR_MESSAGE);

                }
            }
        };

        worker.execute();
    }

    private void registrarSaida() {
        CardLayout cl = (CardLayout) (cards.getLayout());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dataSaida = new Date();
        final String dataHoraSaida = dateFormat.format(dataSaida);

        double precoTotal = Double.parseDouble(txtTotal.getText());
        String placa = txtPlacaSaida.getText();

        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws DataAccessException {
            	EntradaSaida entradaSaidaAux = new EntradaSaida();
            	EntradaSaida entradaSaida = new EntradaSaida();
            	EntradaSaidaDAO dao = new EntradaSaidaDAO();
            	entradaSaidaAux = dao.findByNumeroPlaca(placa);
                boolean check=false;

                try {
                	
                	String dataHoraEntrada = entradaSaidaAux.getDataHoraEntrada();
                    Date dataEntrada = null;
                    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    
                    try {
                        dataEntrada = formato.parse(dataHoraEntrada);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    entradaSaida = dao.findByNumeroPlacaSaida(placa, dataHoraEntrada);
                	entradaSaida.setPrecoTotal(precoTotal);
                    
                	long tempoPermanencia = dataSaida.getTime() - dataEntrada.getTime();
                    long tempoPermanenciaMin = tempoPermanencia / (60 * 1000);
                    if (tempoPermanenciaMin<1){
                        tempoPermanenciaMin=1;
                    }
                    MensalistaDAO mensalistaDAO = new MensalistaDAO();
                    String cpf = mensalistaDAO.findByPlaca(placa);
                    Mensalista mensalista = mensalistaDAO.findByCPF(cpf);
                    if (mensalista != null){
                        int saldoAtual = mensalista.getSaldoAtual();
                        if(saldoAtual>=tempoPermanenciaMin){
                            saldoAtual = (int) Math.floor(saldoAtual - tempoPermanenciaMin);
                            mensalistaDAO.atualizarSaldo(saldoAtual, mensalista.getCpf());
                        }
                        else
                        {
                        	check = true;
                        }
                    }
                    entradaSaida.setDataHoraSaida(dataHoraSaida);
                    entradaSaida.setTempoPermanencia((int)tempoPermanenciaMin);
                    
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
                
                if(check){
                    FazerPagamentoMensalGUI fazerPagamentoMensalGUI = new FazerPagamentoMensalGUI();
                } else {
                    dao.cadastraSaida(entradaSaida);
                    return true;
                }
                return false;
            }

            protected void done() {
                try {
                     if (get()) {
                        JOptionPane.showMessageDialog(null, "Sa�da finalizada com sucesso!");

                        cl.show(cards, panelInicio);

                        txtPlacaInicio.setText("");

                        qtdVagas = buscarNumeroVagas();

                         if (qtdVagas>0){
                             JOptionPane.showMessageDialog(null, "Vagas restantes: " + qtdVagas);
                             lblQtdBlocoDisp.setText("Quantidade de Blocos: " + qtdVagas);
                         } else {
                             JOptionPane.showMessageDialog(null, "Vagas restantes: " + qtdVagas);
                             lblQtdBlocoDisp.setText("Estacionamento Cheio");
                         }
                    }

                } catch (InterruptedException | ExecutionException e) {
                        JOptionPane.showMessageDialog(null, e, "Erro: ", JOptionPane.ERROR_MESSAGE);

                    }
                }
            };
        worker.execute();
    }

    private void consultarPlaca() {
        String numeroPlaca = txtPlacaInicio.getText();
        if (numeroPlaca.isBlank()) {
            JOptionPane.showMessageDialog(null,"O n�mero n�o pode ser nulo e ter menos que 7 caracteres.");
        } else {
            verificarPlaca(numeroPlaca, lblMensalistaAvulsoEntrada);
        }
    }

    private void verificarPlaca(String numeroPlaca, JLabel lblMensalistaAvulso) {
        CardLayout cl = (CardLayout)(cards.getLayout());

        SwingWorker<EntradaSaida, Void> worker = new SwingWorker<>() {
            @Override
            protected EntradaSaida doInBackground() throws DataAccessException {
                EntradaSaidaDAO entradaSaidaDAO = new EntradaSaidaDAO();
                EntradaSaida entradaSaida = entradaSaidaDAO.findByNumeroPlaca(numeroPlaca);
                if(entradaSaida == null)
           	 	{
                	return entradaSaida;
           	 	}
                else if(entradaSaida.getDataHoraEntrada() != null && entradaSaida.getDataHoraSaida() != null)
           	 	{
           		 	entradaSaida = null;
           	 	}
                else if(entradaSaida.getDataHoraEntrada()!=null && entradaSaida.getDataHoraSaida() == null)
                {
                	entradaSaida = entradaSaidaDAO.findByNumeroPlacaSaida(numeroPlaca, entradaSaida.getDataHoraEntrada());
                }

                return entradaSaida;
            }
            @Override
            protected void done() {
                try {
                    EntradaSaida entradaSaida = get();
                    if(entradaSaida == null)
                    {
                    	cl.show(cards, panelEntrada);
                        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO();

                        txtPlacaEntrada.setText(numeroPlaca);
                        txtPlacaEntrada.setHorizontalAlignment(JTextField.LEFT);
                        txtPlacaEntrada.setEditable(false);

                        DateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
                        Date date = new Date();
                        txtHorEntEntrada.setText(horaFormat.format(date));
                        txtHorEntEntrada.setEditable(false);

                        try {
                            txtDuracaoBlocoEntrada.setText(configuracaoDAO.getDuracaoBloco().toString());
                            txtPrecoBlocoEntrada.setText(Float.toString(configuracaoDAO.getPrecoBloco()));
                        } catch (DataAccessException e) {
                            e.printStackTrace();
                        }
                        txtDuracaoBlocoEntrada.setEditable(false);
                        txtPrecoBlocoEntrada.setEditable(false);

                        if(verificaMensalista(numeroPlaca)){
                            lblMensalistaAvulso.setText("Mensalista");
                            VeiculosMensalistaDAO veiculoMensalistaDAO = new VeiculosMensalistaDAO();
                            VeiculosMensalista veiculoMensalista = null;
                            try {
                                veiculoMensalista = veiculoMensalistaDAO.findByPlaca(numeroPlaca);
                                //System.out.println("E/S - "+ veiculoMensalista.getCpf());
                            } catch (DataAccessException e) {
                                e.printStackTrace();
                            }
                            txtDesc.setText(veiculoMensalista.getDescricao_veiculo());
                            txtDesc.setEditable(false);
                        } else {
                            lblMensalistaAvulso.setText("Avulso");
                        }

                    } else if(entradaSaida.getDataHoraSaida() == null){
                        cl.show(cards, panelSaida);

                        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO();

                        txtPlacaSaida.setText(numeroPlaca);
                        txtPlacaSaida.setHorizontalAlignment(JTextField.LEFT);
                        txtPlacaSaida.setEditable(false);

                        if(verificaMensalista(numeroPlaca)){
                            lblMensalistaAvulsoSaida.setText("Mensalista");
                        } else{
                            lblMensalistaAvulsoSaida.setText("Avulso");
                        }

                        DateFormat horaFormat = new SimpleDateFormat("HH:mm:ss");
                        String dataHora = entradaSaida.getDataHoraEntrada();
                        Date data = null;
                        String hora = null;
                        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            data = formato.parse(dataHora);
                            hora = horaFormat.format(data);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        txtHorEntSaida.setText(hora);
                        txtHorEntSaida.setEditable(false);

                        Date date = new Date();
                        txtHorSaida.setText(horaFormat.format(date));
                        txtHorSaida.setEditable(false);

                        try {
                            txtDuracaoBlocoSaida.setText(configuracaoDAO.getDuracaoBloco().toString());
                            txtPrecoBlocoSaida.setText(Float.toString(configuracaoDAO.getPrecoBloco()));
                        } catch (DataAccessException e) {
                            e.printStackTrace();
                        }
                        txtDuracaoBlocoSaida.setEditable(false);
                        txtPrecoBlocoSaida.setEditable(false);

                        Date dataSaida = date;
                        Date dataEntrada = data;
                        long tempoPermanencia = dataSaida.getTime() - dataEntrada.getTime();
                        long tempoPermanenciaMin = tempoPermanencia / (60 * 1000);
                        if (tempoPermanenciaMin<1){
                            tempoPermanenciaMin=1;
                        }
                        txtPermanencia.setText(tempoPermanenciaMin+" minutos");
                        txtPermanencia.setEditable(false);

                        float tempoDuracao = 60;
                        int numBlocos = (int)Math.ceil(tempoPermanenciaMin/tempoDuracao);
                        txtQtdBlocoSaida.setText(Integer.toString(numBlocos));
                        txtQtdBlocoSaida.setEditable(false);

                        double precoBloco = 0;
                        try {
                            precoBloco = configuracaoDAO.getPrecoBloco();
                        } catch (DataAccessException e) {
                            e.printStackTrace();
                        }
                        double precoTotal = precoBloco*numBlocos;
                        txtTotal.setText(Double.toString(precoTotal));
                        txtTotal.setEditable(false);
                    }
                } catch (InterruptedException | ExecutionException e) {
                    JOptionPane.showMessageDialog(frame, e, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

        };

        worker.execute();
    }

    private boolean verificaMensalista(String numeroPlaca) {
        MensalistaDAO mensalistaDAO = new MensalistaDAO();
        Mensalista mensalista;
        try {
            String cpf = mensalistaDAO.findByPlaca(numeroPlaca);
            if  (cpf == null || cpf.isEmpty() || cpf.isBlank())
            {
            	return false;
            }else{
                return true;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    private int buscarNumeroVagas() {
        int qtdTotal;
        int qtdRestante=0;

        ConfiguracaoDAO configuracaoDAO = new ConfiguracaoDAO();
        EntradaSaidaDAO entradaSaidaDAO = new EntradaSaidaDAO();
        try {
            qtdTotal = configuracaoDAO.getQtdVagas();
            qtdRestante= qtdTotal - entradaSaidaDAO.contaRegistrosBanco();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return qtdRestante;
    }

    private void confirmExit() {
        int answer = JOptionPane.showConfirmDialog(frame,
                "Se voc� sair do aplicativo, o registro de Entrada ou Sa�da atual ser� cancelado. Deseja continuar?",
                "Sair",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }
}
