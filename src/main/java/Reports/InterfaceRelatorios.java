package Reports;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import Reports.ExperimentalPhysicsReport1.ExperimentalPhysicsReport1Density;
import Reports.ExperimentalPhysicsReport2.ExperimentalPhysicsReport2LinearMoment;
import Reports.ExperimentalPhysicsReport3.ExperimentalPhysicsReportAtrictCoeficient;
import Reports.ExperimentalPhysicsReport4.ExperimentalPhysicsReport4Gravidade;
import Reports.ExperimentalPhysicsReport5.ReportNumber5CalculatorForConstantKCorrigida;
import Reports.ExperimentalPhysicsReport6.ReportNumber6CalculatorForConstantGamaSimplificado;
import Reports.ExperimentalPhysicsReport7.ReportNumber7CalcularInercia;

public class InterfaceRelatorios {

    private static String caminhoCSVSelecionado = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculadoras Física Experimental I");
        frame.setSize(450, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel labelRelatorio = new JLabel("Escolha o relatório:");
        labelRelatorio.setBounds(30, 20, 200, 20);
        frame.add(labelRelatorio);

        String[] relatorios = { "Relatório 1 - Densidade do Cilindro", "Relatorio 2 - Conservação de momento linear", "Relátorio 3 - Coef. de Atrito estático", "Relátorio 4 - Gravidade via Pêndulo", "Relátorio 5 - Lei de Hooke", "Relátorio 6 - Conservação de energia mecânica", "Relátorio 7 - Inércia" };
        JComboBox<String> comboRelatorio = new JComboBox<>(relatorios);
        comboRelatorio.setBounds(30, 45, 370, 30);
        frame.add(comboRelatorio);

        JButton botaoSelecionarCSV = new JButton("Selecionar CSV");
        botaoSelecionarCSV.setBounds(30, 90, 150, 30);
        frame.add(botaoSelecionarCSV);

        JLabel labelArquivo = new JLabel("Nenhum arquivo selecionado.");
        labelArquivo.setBounds(30, 125, 370, 20);
        frame.add(labelArquivo);

        JButton botaoGerar = new JButton("Gerar Relatório");
        botaoGerar.setBounds(200, 90, 150, 30);
        botaoGerar.setEnabled(false);
        frame.add(botaoGerar);

        JLabel status = new JLabel("");
        status.setBounds(30, 160, 370, 20);
        frame.add(status);

        botaoSelecionarCSV.addActionListener(e -> {
            FileDialog fileDialog = new FileDialog(frame, "Selecione o arquivo CSV", FileDialog.LOAD);
            fileDialog.setFile("*.csv"); // para mostrar apenas arquivos CSV
            fileDialog.setVisible(true);

            String directory = fileDialog.getDirectory();
            String file = fileDialog.getFile();

            if (file != null) {
                caminhoCSVSelecionado = directory + file;
                labelArquivo.setText("Arquivo: " + file);
                botaoGerar.setEnabled(true);
                status.setText("");
            }
        });


        botaoGerar.addActionListener(e -> {
            if (caminhoCSVSelecionado == null) {
                status.setText("Selecione um arquivo CSV primeiro.");
                return;
            }

            String relatorio = (String) comboRelatorio.getSelectedItem();

            try {
                // Redirecionar saída para capturar resultados
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PrintStream ps = new PrintStream(baos);
                PrintStream originalOut = System.out;
                System.setOut(ps);

                assert relatorio != null;

                if (relatorio.contains("Densidade")) {
                    ExperimentalPhysicsReport1Density.executar(caminhoCSVSelecionado);
                } else if (relatorio.contains("momento linear")){
                    ExperimentalPhysicsReport2LinearMoment.executar(caminhoCSVSelecionado);
                } else if (relatorio.contains("Coef. de Atrito estático")){
                    ExperimentalPhysicsReportAtrictCoeficient.executar(caminhoCSVSelecionado);
                } else if (relatorio.contains("Gravidade")){
                    ExperimentalPhysicsReport4Gravidade.executar(caminhoCSVSelecionado);
                } else if (relatorio.contains("Hooke")){
                    ReportNumber5CalculatorForConstantKCorrigida.executar(caminhoCSVSelecionado);
                } else if (relatorio.contains("mecânica")){
                    ReportNumber6CalculatorForConstantGamaSimplificado.executar(caminhoCSVSelecionado);
                } else if (relatorio.contains("Inercia")) {
                    ReportNumber7CalcularInercia.executar(caminhoCSVSelecionado);
                }
                else {
                    System.out.println("Relatório ainda não implementado.");
                }

                System.out.flush();
                System.setOut(originalOut); // restaurar

                // Mostrar resultados na nova janela
                String resultadoTexto = baos.toString("UTF-8");
                mostrarResultadoEmNovaJanela(resultadoTexto);
                status.setText("Relatório gerado com sucesso.");

            } catch (Exception ex) {
                ex.printStackTrace();
                status.setText("Erro ao executar relatório.");
            }
        });

        frame.setVisible(true);
    }

    private static void mostrarResultadoEmNovaJanela(String conteudo) {
        JFrame resultadoFrame = new JFrame("Resultado do Relatório");
        resultadoFrame.setSize(600, 500);
        resultadoFrame.setLocationRelativeTo(null);

        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);

        // Estilo para centralizar o texto
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(attrs, "Monospaced");
        StyleConstants.setFontSize(attrs, 14);

        // Aplicar estilo
        StyledDocument doc = textPane.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), conteudo, attrs);
            doc.setParagraphAttributes(0, doc.getLength(), attrs, false);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        JScrollPane scroll = new JScrollPane(textPane);
        resultadoFrame.add(scroll);

        resultadoFrame.setVisible(true);
    }

}

