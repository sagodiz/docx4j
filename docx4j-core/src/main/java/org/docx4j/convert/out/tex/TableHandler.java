package org.docx4j.convert.out.tex;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.docx4j.wml.P;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Tr;
import org.docx4j.openpackaging.exceptions.Docx4JException;

public class TableHandler {
  
  private static TableHandler singleTableHandler = null;
  private Writer output = null;
  private PHandler pHandler = PHandler.createPHandler();
  private int tableNum = 0;
  
  /**
   * private constructor in order to make singletons
   */
  private TableHandler() {}
  
  /**
   * Method that creates an instance of this class. Returns an already created object or creates one.
   * @return An already created object or creates one
   */
  public static TableHandler createTableHandler() {
    
    if ( null == singleTableHandler ) {
      singleTableHandler = new TableHandler();
    }
    
    return singleTableHandler;
  }
  
  /**
   * This method sets the output. A singleton may used multiple times so output must be able to change.
   */
  public void setOutput(Writer out) {
    output = out;
  }
  
  /**
   * This method handles tables. Collects rows and cells, process them.
   * @param table The table found in docx represented as a Java object
   * @param tabular String that contains leading spaces for the element to have well formatted tex code.
   * @throws Docx4JException
   */
  public void handle(Tbl table, String tabular) throws Docx4JException, IOException {
    
    if ( null == output )
      throw new Docx4JException("Internal error: output not passed to TableHandler.");
    
    if ( null == tabular )
      tabular = new String();
    
    ++tableNum;
    output.write(System.lineSeparator());
    output.write(tabular + "\\begin{table}[!htb]");
    output.write(System.lineSeparator());
    
    List<Object> rows = table.getContent();
    
    boolean firstRow = true;
    
    for ( Object row : rows ) {
      
      //here add a new line and a hline according to the properties..
      if ( row instanceof Tr ) {
        //work for row formatting TrPr
        List<Object> cells = ((Tr)row).getContent();
        
        if ( firstRow ) {
          //here we have a list of cells in a row. count it
          //TODO: the borders and the alignments
          /*
           * \multicolumn{1}{|r|}{Item3}
           * Alignment change for single cell
           * */
          output.write(tabular + "  \\begin{tabular}{|");
          
          for ( int i = 0; i < cells.size(); i++ ) {
            //print the proper amount of cells
            output.write("c|");
          }
          output.write("}");
          
          firstRow = false;
          output.write(System.lineSeparator());
        }
        
        output.write("\\hline");  //TODO: property...
        output.write(System.lineSeparator());
        output.write(tabular + "    ");
        for ( int i = 0; i < cells.size(); i++ ) {
          
          Object cell = cells.get(i);

          if ( cell instanceof JAXBElement<?> && ((JAXBElement<?>)cell).getValue() instanceof Tc ) {
            
            List<Object> paragraphs = ((Tc)((JAXBElement<?>)cell).getValue()).getContent();
            for ( Object paragraph : paragraphs ) {

              if ( paragraph instanceof P )
                pHandler.handle((P)paragraph, "", false);
            }
          }
          
          //if it is not the last cell
          if ( i + 1 < cells.size() ) {
            output.write(" & ");
            
          }
        }
        output.write(" \\\\ ");
      }
      //some other handling of properties might done
    }
    //close it with the bottom line
    output.write(System.lineSeparator());
    output.write("\\hline");  //TODO: property...
    output.write(System.lineSeparator());
    
    output.write(tabular + "  \\end{tabular}");
    output.write(System.lineSeparator());
    //TODO:caption may added
    
    output.write(tabular + "\\label{table" + tableNum + "}");
    output.write(System.lineSeparator());
    output.write(tabular + "\\end{table}");
  }
}
