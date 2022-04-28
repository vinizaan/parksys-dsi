package gui;

import dao.DataAccessException;
import dao.MensalistaDAO;
import modelo.Mensalista;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public class CadastrarMensalistaGUI {
    private JLabel lblCpf;
    private JTextField txtCpf;
    private JLabel lblNome;
    private JTextField txtNome;
    private JLabel lblTelefone;
    private JTextField txtTelefone;
    private JLabel lblObs;
    private JTextArea areaObs;
    private JButton btnCadastrar;
    private JButton btnSair;
    private JFrame frame;
    private DefaultStyledDocument doc;

    private JLabel remaningLabel = new JLabel();


    public CadastrarMensalistaGUI() {
        criarComponentes();
    }

    private void criarComponentes() {
        frame = new JFrame("Cadastrar Mensalista");

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

        lblCpf = new JLabel("CPF: ");
        try {
            txtCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtCpf.setHorizontalAlignment(SwingConstants.RIGHT);
        txtCpf.setBorder(new LineBorder(Color.GRAY));

        lblNome = new JLabel("Nome: ");
        txtNome = new JTextField();
        txtNome.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (txtNome.getText().length() >= 35 ) // limit textfield to 3 characters
                    e.consume();
                if (txtNome.getText().length() > 35){
                    JOptionPane.showMessageDialog(null, "O campo nome n�o pode ultrapassar 35 caracteres.");
                    txtNome.setText(txtNome.getText().substring(0,35));
                }
            }
        });
        txtNome.setHorizontalAlignment(SwingConstants.RIGHT);
        txtNome.setBorder(new LineBorder(Color.GRAY));

        lblTelefone = new JLabel("Telefone: ");
        try {
            txtTelefone = new JFormattedTextField(new MaskFormatter("(##)#####-####"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        txtTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
        txtTelefone.setBorder(new LineBorder(Color.GRAY));

        lblObs = new JLabel("Observa��o: ");
        areaObs = new JTextArea(6,30);
        areaObs.setAlignmentX(SwingConstants.RIGHT);
        areaObs.setLineWrap(true);
        areaObs.setBorder(new LineBorder(Color.GRAY));

        doc = new DefaultStyledDocument();
        doc.setDocumentFilter(new DocumentSizeFilter(150));
        doc.addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCount();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCount();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCount();
            }
        });
        areaObs.setDocument(doc);
        updateCount();

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener((ActionEvent e) -> {cadastrarMensalista();});

        btnSair = new JButton("Sair");
        btnSair.addActionListener((ActionEvent e) -> {confirmExit();});

        /* configura��o do layout */
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc;

        /* CPF */
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblCpf, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 245);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtCpf, gbc);

        /* NOME */
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblNome, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 40);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtNome, gbc);

        /* TELEFONE */
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblTelefone, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 245);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtTelefone, gbc);

        /* OBSERVA��O */
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        panel.add(lblObs, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(areaObs, gbc);

        /* REMAINING LABEL */
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 5, 0, 10);
        gbc.fill = GridBagConstraints.EAST;
        panel.add(remaningLabel, gbc);

        /* CADASTRAR */
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnCadastrar, gbc);

        /* SAIR */
        gbc.gridx = 6;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(btnSair, gbc);

        return panel;
    }

    private void updateCount() {
        remaningLabel.setText((150 - doc.getLength()) + "/150");
    }

    public boolean matchesOnlyText(String text) {
        return text.matches("[^\\d]+");
    }

    private void cadastrarMensalista(){
        String cpf = txtCpf.getText();
        String nome = txtNome.getText();
        String telefone = txtTelefone.getText();
        String obs = areaObs.getText();

        if (cpf == null || nome == null || telefone == null) {
            JOptionPane.showMessageDialog(null, "Todos os campos obrigatorios devem ser preenchidos.");
            return;
        } else if (!matchesOnlyText(nome)) {
            JOptionPane.showMessageDialog(null, "O campo nome n�o pode conter n�meros!");
            return;
        }
        MensalistaDAO dao = new MensalistaDAO();
        Mensalista mensalista = new Mensalista();
        mensalista.setCpf(cpf);
        mensalista.setNome(nome);
        mensalista.setTelefone(telefone);
        mensalista.setObs(obs);
        try {
			if(dao.findByCPF(cpf)!=null)
			{
				JOptionPane.showMessageDialog(null, "J� existe um mensalista com esse CPF!");
	            return;
			}
		} catch (DataAccessException e1) {
			e1.printStackTrace();
		}
        SwingWorker<Void, Void> worker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() throws DataAccessException {
                MensalistaDAO dao = new MensalistaDAO();
                
                dao.cadastraMensalista(mensalista);
                return null;
            }

            protected void done() {
                try {
                    get();
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                    txtCpf.setText("");
                    txtNome.setText("");
                    txtTelefone.setText("");
                    areaObs.setText("");

                    GerenciarVeiculosGUI gerenciarVeiculosGUI = new GerenciarVeiculosGUI();

                } catch (InterruptedException | ExecutionException e ) {
                	//System.out.println();
                    JOptionPane.showMessageDialog(null, e, "Erro: ", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
    }

    private void confirmExit() {
        int answer = JOptionPane.showConfirmDialog(frame,
                "Se voc� sair do aplicativo, o cadastro atual ser� cancelado. Deseja continuar?",
                "Sair",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            frame.dispose();
        }
    }

    class DocumentSizeFilter extends DocumentFilter {

        int maxCharacters;

        public DocumentSizeFilter(int maxChars) {
            maxCharacters = maxChars;
        }

        public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {
            if ((fb.getDocument().getLength() + str.length()) <= maxCharacters) {
                super.insertString(fb, offs, str, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {

            if ((fb.getDocument().getLength() + str.length() - length) <= maxCharacters) {
                super.replace(fb, offs, length, str, a);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }
    }
}