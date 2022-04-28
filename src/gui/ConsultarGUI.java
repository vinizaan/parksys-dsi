package gui;

import com.toedter.calendar.JDateChooser;
import graficos.GraficoFaturamentoDiario;
import graficos.GraficoPermanencia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class ConsultarGUI {

    private JButton btnConsFatDiario;
    private JButton btnConsPermanencia;
    private JLabel lblDataInicio;
    private JLabel lblDataFim;
    private JDateChooser dataInicio;
    private JDateChooser dataFim;
    private JButton btnSair;
    private JFrame frame;

    public ConsultarGUI() {
        criarComponentes();
    }

    private void criarComponentes() {
        frame = new JFrame("Consultas");
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

        btnConsFatDiario = new JButton("Consultar Faturamento Di�rio");
        btnConsFatDiario.addActionListener((ActionEvent e) -> {consFatDiarioFun();});

        btnConsPermanencia = new JButton("Consultar Perman�ncia");
        btnConsPermanencia.addActionListener((ActionEvent e) -> {consPermanenciaFun();});

        lblDataInicio = new JLabel("Data In�cio:");
        lblDataFim = new JLabel("Data Fim:");

        
        dataInicio = new JDateChooser();
        dataInicio.setDateFormatString("dd/MM/yyyy");
        dataInicio.setBounds(47, 18, 122, 20);
        dataFim = new JDateChooser();
        dataFim.setDateFormatString("dd/MM/yyyy");
        dataFim.setBounds(47, 18, 122, 20);

        btnSair = new JButton("Sair");
        btnSair.addActionListener((ActionEvent e) -> {confirmExit();});

        /* configura��o do layout */
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();

        /* Data Inicio */
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(lblDataInicio, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(dataInicio, gbc);

        /* Data Fim */
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(lblDataFim, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(dataFim, gbc);

        /* Faturamento Di�rio */
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnConsFatDiario, gbc);

        /* Permanencia */
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnConsPermanencia, gbc);

        /* SAIR */
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnSair, gbc);

        return panel;
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

    private void consPermanenciaFun() {

        try {
            Date dataInicioDate = dataInicio.getDate();
            Date dataFimDate = dataFim.getDate();

            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            String novaDataInicio = formatador.format(dataInicioDate);
            String novaDataFim = formatador.format(dataFimDate);

            String[] auxI = novaDataInicio.split("/");
            int diaI = Integer.parseInt(auxI[0]);
            int mesI = Integer.parseInt(auxI[1]);
            int anoI = Integer.parseInt(auxI[2]);
            String[] auxF = novaDataFim.split("/");
            int diaF = Integer.parseInt(auxF[0]);
            int mesF = Integer.parseInt(auxF[1]);
            int anoF = Integer.parseInt(auxF[2]);
            LocalDate ldInicio = LocalDate.of(anoI, mesI, diaI);
            LocalDate ldFim = LocalDate.of(anoF, mesF, diaF);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    if(ldInicio.isBefore(ldFim)){
                        LocalDate ldInicioDepois = ldInicio.plusDays(6);
                        if(ldInicioDepois.isEqual(ldFim)){
                            GraficoPermanencia graficoPermanencia = new GraficoPermanencia(ldInicio, ldFim);
                        } else {
                            JOptionPane.showMessageDialog(null, "Escolha um intervalo de 7 dias.");
                        }
                    } else if(ldInicio.isEqual(ldFim)){
                        JOptionPane.showMessageDialog(null, "As datas n�o podem ser iguais");
                    } else {
                        JOptionPane.showMessageDialog(null, "A Data In�cio n�o pode ser depois da Data Fim");
                    }

                }
            });
            t.start();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Voc� precisa digitar uma data v�lida para os dois campos.");
        }

    }

    private void consFatDiarioFun() {
        try {
            Date dataInicioDate = dataInicio.getDate();
            Date dataFimDate = dataFim.getDate();

            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
            String novaDataInicio = formatador.format(dataInicioDate);
            String novaDataFim = formatador.format(dataFimDate);

            String[] auxI = novaDataInicio.split("/");
            int diaI = Integer.parseInt(auxI[0]);
            int mesI = Integer.parseInt(auxI[1]);
            int anoI = Integer.parseInt(auxI[2]);
            String[] auxF = novaDataFim.split("/");
            int diaF = Integer.parseInt(auxF[0]);
            int mesF = Integer.parseInt(auxF[1]);
            int anoF = Integer.parseInt(auxF[2]);
            LocalDate ldInicio = LocalDate.of(anoI, mesI, diaI);
            LocalDate ldFim = LocalDate.of(anoF, mesF, diaF);


            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    GraficoFaturamentoDiario graficoFaturamentoDiario = new GraficoFaturamentoDiario(ldInicio, ldFim);
                }
            });
            t.start();
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "Voc� precisa digitar uma data v�lida para os dois campos.");
        }
    }
}
