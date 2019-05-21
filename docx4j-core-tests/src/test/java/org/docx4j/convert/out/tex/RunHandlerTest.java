package org.docx4j.convert.out.tex;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBElement;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;
import org.docx4j.wml.U;
import org.docx4j.wml.UnderlineEnumeration;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RunHandlerTest {

  private RunHandler rH = null;
  private R run = null;
  private Writer output = null;
  
 //inputs 
  private final String TEST_TEXT_SIMPLE = "Apple";
  private final String TEST_TEXT_ITALIC = "Apple";
  private final String TEST_TEXT_BOLD = "Apple";
  private final String TEST_TEXT_UNDERLINE = "Apple";
  private final String TEST_TEXT_BOLD_EMBEDS_ITALIC = "Apple";
  private final String TEST_TEXT_BOLD_EMBEDS_UNDERLINE = "Apple";  
  private final String TEST_TEXT_ITALIC_EMBEDS_UNDERLINE = "Apple";
//pt2
  private final String TEST_TEXT_BOLD_SPACE = "Apple Orange";
  private final String TEST_TEXT_ITALIC_SPACE = "Apple Orange";
  private final String TEST_TEXT_UNDERLINE_SPACE = "Apple Orange";
  private final String TEST_TEXT_BOLD_ACCENTS = "Áéűúóüöői";
  private final String TEST_TEXT_ITALIC_ACCENTS = "Áéűúóüöői";
  private final String TEST_TEXT_UNDERLINE_ACCENTS = "Áéűúóüöői";
  private final String TEST_TEXT_BOLD_ACCENTS_SPACE = "Áéűúóüöői Áéűúóüöői";
  private final String TEST_TEXT_ITALIC_ACCENTS_SPACE = "Áéűúóüöői Áéűúóüöői";
  private final String TEST_TEXT_UNDERLINE_ACCENTS_SPACE = "Áéűúóüöői Áéűúóüöői";
  private final String TEST_TEXT_BOLD_EMBEDS_ITALIC_EMBEDS_UNDERLINE_ACCENTS_SPACE = "Áéűúóüöői Áéűúóüöői";
    
//outputs  
  private final String TEST_TEXT_ITALIC_TEX = "\\textit{Apple}";
  private final String TEST_TEXT_BOLD_TEX = "\\textbf{Apple}";
  private final String TEST_TEXT_UNDERLINE_TEX = "\\underline{Apple}";
  private final String TEST_TEXT_BOLD_EMBEDS_ITALIC_TEX = "\\textbf{\\textit{Apple}}";
  private final String TEST_TEXT_ITALIC_EMBEDS_UNDERLINE_TEX = "\\textit{\\underline{Apple}}";
  private final String TEST_TEXT_BOLD_EMBEDS_UNDERLINE_TEX = "\\textbf{\\underline{Apple}}";
  
//pt2
  private final String TEST_TEXT_BOLD_SPACE_TEX = "\\textbf{Apple Orange}";
  private final String TEST_TEXT_ITALIC_SPACE_TEX = "\\textit{Apple Orange}";
  private final String TEST_TEXT_UNDERLINE_SPACE_TEX = "\\underline{Apple Orange}";
  private final String TEST_TEXT_BOLD_ACCENTS_TEX = "\\textbf{Áéűúóüöői}";
  private final String TEST_TEXT_ITALIC_ACCENTS_TEX = "\\textit{Áéűúóüöői}";
  private final String TEST_TEXT_UNDERLINE_ACCENTS_TEX = "\\underline{Áéűúóüöői}";
  private final String TEST_TEXT_BOLD_ACCENTS_SPACE_TEX = "\\textbf{Áéűúóüöői Áéűúóüöői}";
  private final String TEST_TEXT_ITALIC_ACCENTS_SPACE_TEX = "\\textit{Áéűúóüöői Áéűúóüöői}";
  private final String TEST_TEXT_UNDERLINE_ACCENTS_SPACE_TEX = "\\underline{Áéűúóüöői Áéűúóüöői}";
  private final String TEST_TEXT_BOLD_EMBEDS_ITALIC_EMBEDS_UNDERLINE_ACCENTS_SPACE_TEX = "\\textbf{\\textit{\\underline{Áéűúóüöői Áéűúóüöői}}}";
    
   
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }
  //#1 
    @Test
    public void createRunHandlerTest() {
      
      rH = RunHandler.createRunHandler();
      assertNotNull(rH);
    }
  
  //#2
    @Test
    public void setOutputTest() {
      
      run = createRun(null, this.TEST_TEXT_SIMPLE);
      
      rH = RunHandler.createRunHandler();
      rH.setOutput(null);
      
      try {
        output = new FileWriter("Test");
        
        rH.setOutput(output);
        rH.handle(run);
      }
      catch (IOException e) {
        fail();
      }
      catch (Docx4JException e) {
        fail();
      }
    }
    
    @Test
    public void handleTest() {
    
    rH = RunHandler.createRunHandler();
    run = createRun(null, this.TEST_TEXT_SIMPLE);
    //#3 check null writer passed condition
    
    rH.setOutput(null);
    try {
      rH.handle(run);
      fail();
    }
      catch(Docx4JException e){}
      catch(IOException e) {}
    
    //#4 - check simple text
   
    output = new StringWriter();
    
    rH.setOutput(output);

    try {
      
      rH.handle(run);
      
      if ( !TEST_TEXT_SIMPLE.equals(output.toString()) ) {
        fail();
      }
      } catch (Docx4JException e) {
        fail();
      } catch (IOException e) {
        fail();
    }
    //#5 - check italic
       
    run = createRun("i", this.TEST_TEXT_ITALIC);    
    output = new StringWriter();    
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if ( !TEST_TEXT_ITALIC_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
    
    //#6 - check bold
           
    run = createRun("b", this.TEST_TEXT_BOLD);    
    output = new StringWriter();    
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if ( !TEST_TEXT_BOLD_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
      
    //#7 - check underline  
    
    run = createRun("u", this.TEST_TEXT_UNDERLINE);   
    output = new StringWriter();
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_UNDERLINE_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
    
    //#8 - check bold embeds italic
  
    output = new StringWriter();
    run = createRun("i b", this.TEST_TEXT_BOLD_EMBEDS_ITALIC);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_BOLD_EMBEDS_ITALIC_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
    
    
    //#9 - check bold embeds underline
   
    output = new StringWriter();
    run = createRun("u b", this.TEST_TEXT_BOLD_EMBEDS_UNDERLINE);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_BOLD_EMBEDS_UNDERLINE_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
    
    //#10 - check italic embeds underline
   
    output = new StringWriter();
    run = createRun("u i", this.TEST_TEXT_ITALIC_EMBEDS_UNDERLINE);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_ITALIC_EMBEDS_UNDERLINE_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}

    //TODO continue from here
    
    //#11 - check bold space
    
    output = new StringWriter();
    run = createRun("b", this.TEST_TEXT_BOLD_SPACE);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_BOLD_SPACE_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}

    //#12 - check italic space
    
    output = new StringWriter();
    run = createRun("i", this.TEST_TEXT_ITALIC_SPACE);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_ITALIC_SPACE_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
    
 //#13 - check underline space
    
    output = new StringWriter();
    run = createRun("u", this.TEST_TEXT_UNDERLINE_SPACE);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_UNDERLINE_SPACE_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
    
  //#14 - check bold accents
    
    output = new StringWriter();
    run = createRun("b", this.TEST_TEXT_BOLD_ACCENTS);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_BOLD_ACCENTS_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}

    //#15 - check italic accents
    
    output = new StringWriter();
    run = createRun("i", this.TEST_TEXT_ITALIC_ACCENTS);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_ITALIC_ACCENTS_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
    
    //#16 - check underline accents space
    
    output = new StringWriter();
    run = createRun("u", this.TEST_TEXT_UNDERLINE_ACCENTS_SPACE);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_UNDERLINE_ACCENTS_SPACE_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
    
    //#17 - check bold accents space
    
    output = new StringWriter();
    run = createRun("b", this.TEST_TEXT_BOLD_ACCENTS_SPACE);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_BOLD_ACCENTS_SPACE_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}

    //#18 - check italic accents space
    
    output = new StringWriter();
    run = createRun("i", this.TEST_TEXT_ITALIC_ACCENTS_SPACE);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_ITALIC_ACCENTS_SPACE_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
    
    //#19 - check underline accents space
    
    output = new StringWriter();
    run = createRun("u", this.TEST_TEXT_UNDERLINE_ACCENTS_SPACE);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_UNDERLINE_ACCENTS_SPACE_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
    
    //#20 - check bold embeds italic embeds underline accents space
    
    output = new StringWriter();
    run = createRun("b i u", this.TEST_TEXT_BOLD_EMBEDS_ITALIC_EMBEDS_UNDERLINE_ACCENTS_SPACE);     
    rH.setOutput(output);

    try {    
      rH.handle(run);   
      System.out.println(output);      
    if (!TEST_TEXT_BOLD_EMBEDS_ITALIC_EMBEDS_UNDERLINE_ACCENTS_SPACE_TEX.equals(output.toString()) ) {
        fail();
    }}
    catch (Docx4JException e) {fail();}
    catch (IOException e) {fail();}
      
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
