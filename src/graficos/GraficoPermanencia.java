package graficos;

import dao.EntradaSaidaDAO;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GraficoPermanencia {
    LocalDate ldInicio;
    LocalDate ldFim;
    JFrame frame;

    public GraficoPermanencia(LocalDate ldInicio, LocalDate ldFim){
        this.ldInicio = ldInicio;
        this.ldFim = ldFim;
        execute();
    }

    private void execute() {
        XYChart chart = buildChart();
        frame = new SwingWrapper<>(chart).displayChart();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle("Gr�fico Perman�ncia");
    }

    private XYChart buildChart() {
        // DATA
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        String dataInicioForm = ldInicio.format(formatter);
        String dataFimForm = ldFim.format(formatter);

        XYChart chart = new XYChartBuilder()
                .width(600)
                .height(400)
                .title("Perman�ncia M�dia por Dia da Semana - " + dataInicioForm + " - " + dataFimForm)
                .yAxisTitle("Horas")
                .xAxisTitle("Dia da Semana")
                .build();

        // Series
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataInicioForm2 = ldInicio.format(formatter2);
        ldFim = ldFim.plusDays(1);
        String dataFimForm2 = ldFim.format(formatter2);
        //System.out.println(dataInicioForm2 + " -- " + dataFimForm2);

        // Valores
        List<Integer> xs = new ArrayList<>();
        int i=0;
        EntradaSaidaDAO entradaSaidaDAO = new EntradaSaidaDAO();

        List<String> datas = entradaSaidaDAO.buscarDatas(dataInicioForm2, dataFimForm2);
        List<Date> dates = new ArrayList<>();
        List<String> diasSemanas = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String data : datas) {
            try {
                i++;
                xs.add(i);

                Date dia = sdf.parse(data);
                dates.add(dia);

                Calendar cal = Calendar.getInstance();
                cal.setTime(dia);
                int day = cal.get(Calendar.DAY_OF_WEEK);
                if (day==1){
                    diasSemanas.add("Dom");
                } else if(day==2){
                    diasSemanas.add("Seg");
                } else if(day==3){
                    diasSemanas.add("Ter");
                } else if(day==4){
                    diasSemanas.add("Qua");
                } else if(day==5){
                    diasSemanas.add("Qui");
                } else if(day==6){
                    diasSemanas.add("Sex");
                } else if(day==7){
                    diasSemanas.add("Sab");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        List <Integer> ys = entradaSaidaDAO.buscarPermanencias(dataInicioForm2, dataFimForm2);

        /*
        System.out.println("Valores");
        for (Integer temp : ys) {
            System.out.println(temp);
        }
        System.out.println("Dias");
        for (String d: diasSemanas){
            System.out.println(d);
        }
		*/

        chart.addSeries("Tempo m�dio (em minutos)", xs, ys);

        // Apar�ncia
        chart.getStyler().setLegendVisible(false);

        String[] finalDiasSemana = diasSemanas.toArray(new String[0]);
        chart.getStyler().setxAxisTickLabelsFormattingFunction(d -> finalDiasSemana[d.intValue() - 1]);

        return chart;
    }
}
