/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectaco;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author LenovoLap
 */
public class Grafica {
    public void pastel(ArrayList < ArrayList<Object> > listaOfertas){
        
        
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        int size = listaOfertas.size();
        
        
        dataset.addValue((Integer)listaOfertas.get(0).get(1), "Productos", (String)listaOfertas.get(0).get(0));
        dataset.addValue((Integer)listaOfertas.get(1).get(1), "Productos", (String)listaOfertas.get(1).get(0));
        dataset.addValue((Integer)listaOfertas.get(2).get(1), "Productos", (String)listaOfertas.get(2).get(0));
        
        dataset.addValue((Integer)listaOfertas.get(listaOfertas.size()-2).get(1),"Productos", (String)listaOfertas.get(listaOfertas.size()-2).get(0));
        dataset.addValue((Integer)listaOfertas.get(listaOfertas.size()-1).get(1), "Productos", (String)listaOfertas.get(listaOfertas.size()-1).get(0));
        
        JFreeChart chart = ChartFactory.createBarChart(
    "Reporte de ventas", // El titulo de la gráfica
    "Producto", // Etiqueta de categoria
    "Cantidad vendida", // Etiqueta de valores
    dataset, // Datos
    PlotOrientation.VERTICAL, // orientacion
    true, // Incluye Leyenda
    true, // Incluye tooltips
    false // URLs?
        );
        ChartFrame frame = new ChartFrame("Graficador", chart);
        frame.pack();
        frame.setVisible(true);
                
    }
}
