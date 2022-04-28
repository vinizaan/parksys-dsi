package graficos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.EntradaSaidaDAO;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import javax.swing.*;

/*
 * Implementação baseada no exemplo disponível em:
 * https://github.com/knowm/XChart/blob/develop/xchart-demo/src/main/java/org/knowm/xchart/demo/charts/date/DateChart05.java
 */
public class GraficoFaturamentoDiario {
	JFrame frame;
	LocalDate ldInicio;
	LocalDate ldFim;

	public GraficoFaturamentoDiario(LocalDate ldInicio, LocalDate ldFim){
		this.ldInicio = ldInicio;
		this.ldFim = ldFim;
		execute();
	}

	private void execute() {
		XYChart chart = buildChart();
		frame = new SwingWrapper<>(chart).displayChart();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Gr�fico Faturamento Di�rio");
	}
	
	private XYChart buildChart() {
		XYChart chart = new XYChartBuilder()
				.width(600)
				.height(400)
				.title("Faturamento Di�rio")
				.yAxisTitle("R$")
				.build();
		
		// Series

		// DATA
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ldFim = ldFim.plusDays(1);
		String dataInicioForm2 = ldInicio.format(formatter2);
		String dataFimForm2 = ldFim.format(formatter2);

		// VALORES
		EntradaSaidaDAO entradaSaidaDAO = new EntradaSaidaDAO();

		//System.out.println("Datas Param: " + dataInicioForm2 + " - " + dataFimForm2);

		List<String> datas = entradaSaidaDAO.buscarDatas(dataInicioForm2, dataFimForm2);
        List <Float> ys = entradaSaidaDAO.buscarFaturamento(dataInicioForm2, dataFimForm2);
		List <Date> xs = new ArrayList<>();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//System.out.println("\n\nPrint DATAS");
		for (String d : datas) {
			try {
				Date dia = sdf.parse(d);
				xs.add(dia);
				//System.out.println(d);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		/*
		System.out.println("\n\n Print XS");
		for (Date d: xs){
			System.out.println(d);
		}
		*/
		
		chart.addSeries("Faturamento di�rio", xs, ys);

		// Aparência
		chart.getStyler().setLegendVisible(false);
		
		/*
		 * Alterando marcações (ticks) no eixo X, para exibir nomes dos dias da semana.
		 * Implementação baseada no exemplo disponível em:
		 * https://github.com/knowm/XChart/blob/36977a3dc5be67085a086528b84dae67856ba38b/xchart-demo/src/main/java/org/knowm/xchart/demo/charts/date/DateChart06.java#L63
		 */
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM");
		chart.getStyler().setxAxisTickLabelsFormattingFunction(obj -> formatador.format(obj));
		
		return chart;
	}
}
