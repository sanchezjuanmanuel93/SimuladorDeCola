package logica;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class Archivos {

	
	public static boolean escribirArchivo(int fila, int columna){
			
		
		
		return true;
	}
	
	public static void main(String[] args) throws Exception{
        /*La ruta donde se crear� el archivo*/
        String rutaArchivo = System.getProperty("user.home")+"/ejemploExcelJava.xls";
        /*Se crea el objeto de tipo File con la ruta del archivo*/
        File archivoXLS = new File(rutaArchivo);
        /*Si el archivo existe se elimina*/
        if(archivoXLS.exists()) archivoXLS.delete();
        /*Se crea el archivo*/
        archivoXLS.createNewFile();
        
        /*Se crea el libro de excel usando el objeto de tipo Workbook*/
        Workbook libro = new HSSFWorkbook();
        /*Se inicializa el flujo de datos con el archivo xls*/
        FileOutputStream archivo = new FileOutputStream(archivoXLS);
        
        /*Utilizamos la clase Sheet para crear una nueva hoja de trabajo dentro del libro que creamos anteriormente*/
        Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
        
        /*Hacemos un ciclo para inicializar los valores de 10 filas de celdas*/
        for(int f=0;f<10;f++){
            /*La clase Row nos permitir� crear las filas*/
            Row fila = hoja.createRow(f);
            
            /*Cada fila tendr� 5 celdas de datos*/
            for(int c=0;c<5;c++){
                /*Creamos la celda a partir de la fila actual*/
                Cell celda = fila.createCell(c);
                
                /*Si la fila es la n�mero 0, estableceremos los encabezados*/
                if(f==0){
                    celda.setCellValue("Encabezado #"+c);
                }else{
                    /*Si no es la primera fila establecemos un valor*/
                    celda.setCellValue("Valor celda "+c+","+f);
                }
            }
        }
        
        /*Escribimos en el libro*/
        libro.write(archivo);
        /*Cerramos el flujo de datos*/
        archivo.close();
        /*Y abrimos el archivo con la clase Desktop*/
        Desktop.getDesktop().open(archivoXLS);
    }
	
}
