package org.docx4j.convert.out.tex;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Docx2TexTest {

  
  private Docx2TexConverter c = null;
  
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @Test
  public void testDocx2TexConverter() {
    c = new Docx2TexConverter();
    assertNotNull(c);
  }
  
  @Test
  public void testDocx2TexConverterWPMLS() {
    try {
      c = null;
      c = new Docx2TexConverter(createDoc(), "Test");
      assertNotNull(c);
    } catch (Docx4JException e) {
      // TODO Auto-generated catch block
      fail();
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      fail();
      e.printStackTrace();
    }
    assertNotNull(c);
  }

  @Test
  public void testSetOutPutFileString() {
    c = new Docx2TexConverter();
    try {
      c.setOutPutFile("Test");
    } catch (Docx4JException e) {
      fail();
      e.printStackTrace();
    } catch (IOException e) {
      fail();
      e.printStackTrace();
    }
  }

  @Test
  public void testSetOutPutFileFile() {
    c = new Docx2TexConverter();
    try {
      File f = new File("Test");
      c.setOutPutFile(f);
    } catch (Docx4JException e) {
      fail();
      e.printStackTrace();
    } catch (IOException e) {
      fail();
      e.printStackTrace();
    }
  }


  
  @Test
  public void testConvertLinxPath() {
    
    File f = new File("src"+File.separator+"test"+File.separator+"java"+File.separator+"org"+File.separator+"docx4j"+File.separator+"convert"+File.separator+"out"+File.separator+"tex"+File.separator+"Test.tex");
    
    try {
       c = new Docx2TexConverter("src"+File.separator+"test"+File.separator+"java"+File.separator+"org"+File.separator+"docx4j"+File.separator+"convert"+File.separator+"out"+File.separator+"tex"+File.separator+"Test.docx", f);
    } catch (Docx4JException e) {
      // TODO Auto-generated catch block
      fail(e.toString());
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      fail(e.toString());
      e.printStackTrace();
    } catch (Exception e) {
      fail(e.toString());
      // TODO: handle exception
    }
    
  }
  
  
  private WordprocessingMLPackage createDoc() {
    WordprocessingMLPackage wordPackage = null;
    try {
      wordPackage = WordprocessingMLPackage.createPackage();
    } catch (InvalidFormatException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    MainDocumentPart mainDocumentPart = wordPackage.getMainDocumentPart();
    mainDocumentPart.addStyledParagraphOfText("Title", "Hello World!");
    mainDocumentPart.addParagraphOfText("Welcome To rf2");
    
    return wordPackage;
  }
  

}
