package logica;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Archivos {

	private static Workbook libro = new HSSFWorkbook();
	private static final String rutaArchivo = System.getProperty("user.home")+"/SimulacionDecola.xls";
	private static File archivoXLS = new File(rutaArchivo);
	private static int contHoja = 0;
	private static Sheet hoja;
	private static String[] encabezado = {"En tiempo","Promedio clientes en cola", "Tiempo medio clientes en cola",  "Factor Servicio", "Promedio clientes en sistema", "Tiempo medio clientes en sistema"};
	private static int contador;
	
	public static void setContador(int cont){
		contador = cont;
	}
	
	public static void crearArchivo(){

		if(archivoXLS.exists())  hoja = libro.createSheet("Hoja "+contHoja);;
        try {
			archivoXLS.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void cerrarArchivo(){
        FileOutputStream archivo;
		try {
			archivo = new FileOutputStream(archivoXLS);
	        libro.write(archivo);
	        Desktop.getDesktop().open(archivoXLS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void escribirArchivo(List<Float> datos){
		
		
		try{
	        
	        
	        /*Utilizamos la clase Sheet para crear una nueva hoja de trabajo dentro del libro que creamos anteriormente*/
	        
	        
	        /*Hacemos un ciclo para inicializar los valores de 10 filas de celdas*/
	        
	            /*La clase Row nos permitirá crear las filas*/
	        	int f = contador;
	            Row fila = hoja.createRow(f);
	            
	            /*Cada fila tendrá 5 celdas de datos*/
	            for(int c=0;c<encabezado.length;c++){
	                Cell celda = fila.createCell(c);	                
	                if(f==0){
	                	hacerEncabezado();
	                }else{
	                    celda.setCellValue(datos.get(c));
	                }
	            }
	        
	        /*Escribimos en el libro*/
	        
	        /*Cerramos el flujo de datos*/

		}catch(Exception e){
			System.out.println("error: "+e.getMessage());
		}
    }
	
	public static void hacerEncabezado(){
		int i  = 0 ;
		Row fila = hoja.createRow(0);
		while(i < encabezado.length){
			Cell celda = fila.createCell(i);
			celda.setCellValue(encabezado[i]);	
			i++;
		}
	}

	
}
