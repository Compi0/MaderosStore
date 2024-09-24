/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectaco;
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import com.itextpdf.text.Document;  
import com.itextpdf.text.DocumentException;  
import com.itextpdf.text.Paragraph;  
import com.itextpdf.text.pdf.PdfWriter; 
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.ArrayList;
import java.util.Iterator;
/**
 *
 * @author LenovoLap
 */
public class ImprimirPDF {
    static int pdf = 0;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    
    public ImprimirPDF(int x){
        pdf = x;
    }
    
    public static void main(String[] args) {
       
    }  
    
    public void imprimir(ArrayList < ArrayList<Object> > listaProductos){
        
         
        try {

	Document document = new Document();
 
        String nombre = "C:\\Maderos\\Recibo"+pdf+".pdf";
        
	OutputStream outputStream = new FileOutputStream(new File(nombre));
        PdfWriter.getInstance(document, outputStream);
        document.open();
        addTitlePage(document);
        addContent(document, listaProductos);
        document.close();
        outputStream.close();
        abirPDF(nombre);
        } catch (Exception e) {
	e.printStackTrace();
        }
    }
    
        private static void addTitlePage(Document document)
                throws DocumentException {
            Paragraph preface = new Paragraph();
            addEmptyLine(preface, 1);
            preface.add(new Paragraph("Recibo de compra", catFont));
            addEmptyLine(preface, 1);
            document.add(preface);
        }

        private static void addContent(Document document,ArrayList < ArrayList<Object> > listaProductos) throws DocumentException {
            Anchor anchor = new Anchor("Recibo de compras del usuario: "+pdf, catFont);
            anchor.setName("Prueba 1");
            Paragraph preface = new Paragraph();
            addEmptyLine(preface, 5);
            // Second parameter is the number of the chapter
            Chapter catPart = new Chapter(new Paragraph(anchor), 1);
            /*
            Paragraph subPara = new Paragraph("Productos:", subFont);
            Section subCatPart = catPart.addSection(subPara);
            subPara = new Paragraph("Precio", subFont);
        
            subCatPart = catPart.addSection(subPara);*/
            /*
            subCatPart.add(new Paragraph("Paragraph 1"));
            subCatPart.add(new Paragraph("Paragraph 2"));
            subCatPart.add(new Paragraph("Paragraph 3"));
*/
            // add a list
            /*
            createList(subCatPart);
            Paragraph paragraph = new Paragraph();
            addEmptyLine(paragraph, 5);
            subCatPart.add(paragraph);
            */
            // add a table
            //createTable(subCatPart);
            document.add(preface);
            createTable(catPart, listaProductos);
            // now add all this to the document
            document.add(catPart);
        }

        private static void createTable(Section subCatPart,ArrayList < ArrayList<Object> > listaProductos)
                throws BadElementException {
            PdfPTable table = new PdfPTable(6);

            PdfPCell c1 = new PdfPCell(new Phrase("ID"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Producto"));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);

            c1 = new PdfPCell(new Phrase("Precio "));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Cantidad "));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Camtidad total vendida "));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            c1 = new PdfPCell(new Phrase("Cantidad total ganada "));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
            
            table.setHeaderRows(1);
            
            for (int i = 0; i < listaProductos.size(); i++) {
                table.addCell(Integer.toString((Integer)listaProductos.get(i).get(0)));
                table.addCell((String)listaProductos.get(i).get(1));
                table.addCell(Float.toString((Float)listaProductos.get(i).get(2)));
                table.addCell(Integer.toString((Integer)listaProductos.get(i).get(3)));
                table.addCell(Integer.toString((Integer)listaProductos.get(i).get(4)));
                table.addCell(Integer.toString((Integer)listaProductos.get(i).get(5)));
            }

            subCatPart.add(table);
        }

        private static void createList(Section subCatPart) {
            List list = new List(true, false, 10);
            list.add(new ListItem("First point"));
            list.add(new ListItem("Second point"));
            list.add(new ListItem("Third point"));
            subCatPart.add(list);
        }

        private static void addEmptyLine(Paragraph paragraph, int number) {
            for (int i = 0; i < number; i++) {
                paragraph.add(new Paragraph(" "));
            }
        }
        
        private static void abirPDF(String nom)throws DocumentException{
            try {

		if ((new File(nom)).exists()) {

			Process p = Runtime
			   .getRuntime()
			   .exec("rundll32 url.dll,FileProtocolHandler "+nom);
			p.waitFor();
				
		} else {

			System.out.println("File is not exists");
		}

		System.out.println("Done");

  	  } catch (Exception ex) {
		ex.printStackTrace();
	  }
        }
}
