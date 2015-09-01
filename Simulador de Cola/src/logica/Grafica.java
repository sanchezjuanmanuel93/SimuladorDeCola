package logica;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Grafica {
	
	private JFreeChart grafico = null;
	private XYSeriesCollection datos = new XYSeriesCollection();
	private String titulo;
	private String tituloX;
	private String tituloY;
	
	public Grafica (String _titulo, String _tituloX, 
			String _tituloY, String _id,float[] _x, float[] _y)
	{
		titulo = _titulo;
		tituloX = _tituloX;
		tituloY = _tituloY;
		grafico = ChartFactory.createXYLineChart(titulo, tituloX, 
				tituloY, datos, PlotOrientation.HORIZONTAL, true, true, true);
		XYSeries serie = new XYSeries(_id);
		for (int i = 0; i< _x.length; i++)
		{
			serie.add((double)_x[i], (double)_y[i]);
		}
		datos.addSeries(serie);
	}
	
	public JPanel obtenerPanel()
	{
		return new ChartPanel(grafico);
	}
}
