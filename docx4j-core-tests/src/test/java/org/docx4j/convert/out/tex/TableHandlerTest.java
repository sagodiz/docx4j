package org.docx4j.convert.out.tex;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.docx4j.wml.U;
import org.docx4j.wml.UnderlineEnumeration;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TableHandlerTest {

  private static final int INDEX_NOT_FOUND = -8;
  private static final String EMPTY = null;
  private TableHandler th = null;
  private Tbl tbl = null;
  private Writer output = null;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @Test
  public void testCreateTableHandler() {
    
    th = null;
    th = TableHandler.createTableHandler();
    assertNotNull(th);
    
    TableHandler th2 = TableHandler.createTableHandler();
    
    assertEquals(th, th2);
  }

  @Test
  public void testSetOutputNullOutput() {
    
    tbl = createTable(5, 1, null);
    th = TableHandler.createTableHandler();
    th.setOutput(null);
    
    try {
      th.handle(tbl, "");
      fail();
    } catch (Docx4JException e) {
      
    } catch (IOException e) {
      
    }
    
  }
 
  @Test
  public void testHandleOne() {
    output = new StringWriter();
    tbl = createTable(1, 1, null);
    th = TableHandler.createTableHandler();
    th.setOutput(output);
    
    
    String test = System.lineSeparator() + "\\begin{table}[!htb]"+System.lineSeparator()+"  \\begin{tabular}{|c|}"+System.lineSeparator()+"\\hline"+System.lineSeparator()+"     \\\\ "+System.lineSeparator()+"\\hline"+System.lineSeparator()+"  \\end{tabular}"+System.lineSeparator()+"\\label{table3}"+System.lineSeparator()+"\\end{table}";
    
    try {
      th.handle(tbl, "");
      
      if (StringUtils.difference(output.toString(), test) != StringUtils.EMPTY) { //so they are not equal
        //System.out.println(StringUtils.difference(output.toString(), test));
        fail();
      }
      
    } catch (Docx4JException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  
  @Test
  public void testHandleMultipleCell() {
    output = new StringWriter();
    tbl = createTable(1, 2, null);
    th = TableHandler.createTableHandler();
    th.setOutput(output);
    
    String test = System.lineSeparator() + "\\begin{table}[!htb]"+System.lineSeparator()+"  \\begin{tabular}{|c|c|}"+System.lineSeparator()+"\\hline"+System.lineSeparator()+"     &  \\\\ "+System.lineSeparator()+"\\hline"+System.lineSeparator()+"  \\end{tabular}"+System.lineSeparator()+"\\label{table1}"+System.lineSeparator()+"\\end{table}";
    
    try {
      th.handle(tbl, "");

      if (StringUtils.difference(output.toString(), test) != StringUtils.EMPTY) { //so they are not equal
        fail();
      }
      
    } catch (Docx4JException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  
  @Test
  public void testHandleMultipleCellMultipleRow() {
    output = new StringWriter();
    tbl = createTable(2, 2, null);
    th = TableHandler.createTableHandler();
    th.setOutput(output);
    
    String test = System.lineSeparator() + "\\begin{table}[!htb]"+System.lineSeparator()+"  \\begin{tabular}{|c|c|}"+System.lineSeparator()+"\\hline"+System.lineSeparator()+"     &  \\\\ \\hline"+System.lineSeparator()+"     &  \\\\ " + System.lineSeparator() +"\\hline"+System.lineSeparator()+"  \\end{tabular}"+System.lineSeparator()+"\\label{table2}"+System.lineSeparator()+"\\end{table}";
    
    try {
      th.handle(tbl, "");

      if (StringUtils.difference(output.toString(), test) != StringUtils.EMPTY) { //so they are not equal
        //System.out.println(StringUtils.difference(output.toString(), test));
        fail();
      }
      
    } catch (Docx4JException | IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  
  private Tbl createTable(int rows, int cols, String str) {
    Tbl table = new Tbl();
    
    Tc tc = new Tc();
    
    if ( null != str) {
      P p = new P();
      R r = createRun("", str);
      
      p.getContent().add(r);
      tc.getContent().add(new P());
    }
    Tr tr = new Tr();
    for ( int i = 0; i < cols; i++)
      tr.getContent().add(tc);
    
    for ( int i = 0; i < rows; i++)
      table.getContent().add(tr);
    
    return table;
  }
private R createRun(final String styleId, final String text) {
    
    org.docx4j.wml.ObjectFactory wmlObjectFactory = Context.getWmlObjectFactory();
    final R r = wmlObjectFactory.createR();

    final RPr rpr = wmlObjectFactory.createRPr();
    
    if ( null != styleId && styleId.contains("b") )
      rpr.setB(new BooleanDefaultTrue());
    if ( null != styleId && styleId.contains("i") )
      rpr.setI(new BooleanDefaultTrue());
    if ( null != styleId && styleId.contains("u") ) {
      
      U val = new U();
      val.setVal(UnderlineEnumeration.SINGLE);
      rpr.setU(val);
    
    }  
          
    r.setRPr(rpr);    

    final Text textElement = wmlObjectFactory.createText();
    textElement.setValue(text);
    final JAXBElement<Text> wrappedText = wmlObjectFactory.createRT(textElement);
    r.getContent().add(wrappedText);

    return r;
  }

}
