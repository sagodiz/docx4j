package org.docx4j.convert.out.tex;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase.PStyle;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.U;
import org.docx4j.wml.Text;
import org.docx4j.wml.UnderlineEnumeration;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;

import org.docx4j.openpackaging.exceptions.Docx4JException;

import org.docx4j.jaxb.Context;

import javax.xml.bind.JAXBElement;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;


public class PHandlerTest2 {
	private PHandler phandler = null;
	private P paragraph = null;
	private Writer output = null;

	private static int heading1Counter = 0;
	private static int heading2Counter = 0;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void testCreatePHandler() {
		phandler = null;
		phandler = PHandler.createPHandler();
	    assertNotNull(phandler);
	}

	
	@Test
	  public void testSetOutputNullOutput() {
	    
		phandler = null;
		phandler = PHandler.createPHandler();
		paragraph = null;
	    paragraph = createParagraph(null, "");
	    phandler.setOutput(null);
	    
	    try {
	      phandler.handle(paragraph, null, true);
	      fail();
	    } catch (Docx4JException e) {
	    	assertNotNull(e);
	    } catch (IOException e) {
	    	assertNotNull(e);
	    }
	    
	  }


	@Test
	public void testHandleHeading1() {
		output = null;
		output = new StringWriter();
		
		paragraph = null;
		paragraph = createParagraph("Heading1", "asd");
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "chapter" + "{" + "asd" + "}" + System.lineSeparator() + "\\label{" + "chapter" + ":" + this.heading1Counter++ + "}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleHeading2() {
		output = null;
		output = new StringWriter();
		
		paragraph = createParagraph("Heading2", "asd2");
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "section" + "{" + "asd2" + "}" + System.lineSeparator() + "\\label{" + "section" + ":" + this.heading2Counter++ + "}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleCenter() {
		output = null;
		output = new StringWriter();
		
		paragraph = null;
		paragraph = createParagraph("center", "asd2 asd");
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "{\\centering " + "asd2 asd" + "\\par}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleHeading1AndCenter() {
		output = null;
		output = new StringWriter();
		
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		
		paragraph = null;
		paragraph = createParagraph("Heading1", "asd1");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		
		paragraph = null;
		paragraph = createParagraph("center", "asd2");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "chapter" + "{" + "asd1" + "}" + System.lineSeparator() + "\\label{" + "chapter" + ":" + this.heading1Counter++ + "}" + System.lineSeparator() + System.lineSeparator() + "{\\centering " + "asd2" + "\\par}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleMultiHeading1() {
		output = null;
		output = new StringWriter();
		
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		
		paragraph = null;
		paragraph = createParagraph("Heading1", "asd1");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		
		paragraph = null;
		paragraph = createParagraph("Heading1", "asd2");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "chapter" + "{" + "asd1" + "}" + System.lineSeparator() + "\\label{" + "chapter" + ":" + this.heading1Counter++ + "}" + System.lineSeparator() + System.lineSeparator() + "\\" + "chapter" + "{" + "asd2" + "}" + System.lineSeparator() + "\\label{" + "chapter" + ":" + this.heading1Counter++ + "}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleHeading1AndHeading2() {
		output = null;
		output = new StringWriter();
		
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		
		paragraph = null;
		paragraph = createParagraph("Heading1", "asd1");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		
		paragraph = null;
		paragraph = createParagraph("Heading2", "asd2");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "chapter" + "{" + "asd1" + "}" + System.lineSeparator() + "\\label{" + "chapter" + ":" + this.heading1Counter++ + "}" + System.lineSeparator() + System.lineSeparator() + "\\" + "section" + "{" + "asd2" + "}" + System.lineSeparator() + "\\label{" + "section" + ":" + this.heading2Counter++ + "}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testSetOutput() {
		phandler = null;
		phandler = PHandler.createPHandler();
		paragraph = null;
	    paragraph = createParagraph(null, "");
		output = null;
		output = new StringWriter();
	    phandler.setOutput(output);
	    
	    try {
	      phandler.handle(paragraph, null, false);
	      assertEquals("", output.toString());
	    } catch (Docx4JException e) {
	    	fail(e.toString());
	    } catch (IOException e) {
	    	fail(e.toString());
	    }
	}
	
	@Test
	public void testPStyleHandler() {
		output = null;
		output = new StringWriter();
		
		paragraph = null;
		paragraph = createParagraph(null, "");
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		List<Object> paragraphElements = paragraph.getContent();
		try {
			phandler.pStyleHandler(paragraphElements, 1, "chapter");
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = "\\" + "chapter" + "{" + "" + "}" + System.lineSeparator() + "\\label{" + "chapter" + ":" + 1 + "}";
		
		assertEquals(test, output.toString());
	}

	@Test
	@Ignore
	public void testLinker() {
		fail("Not yet implemented");
	}

	private P createParagraph(String s, String str) {

		P p = new P();
		
	    if ( null != str) {
	    
	    	if(s != null) {
	    		if(s == "Heading1" || s == "Heading2" || s == "Heading3" || s == "Heading4" || s == "Subtitle") {
	  	    		  p.setPPr(new PPr());
	  	    		  p.getPPr().setPStyle(new PStyle());
	  	    		  p.getPPr().getPStyle().setVal(s);
	  	    	  } else {
	  	    		  if(s == "center" || s == "left" || s == "right")
	  	    		  p.setPPr(new PPr());
	  	    		  p.getPPr().setJc(new Jc());
	  	    		  if(s == "center") {
	  		    		  p.getPPr().getJc().setVal(JcEnumeration.CENTER);
	  		    	  }
	  		    	  if(s == "left") {
	  		    		  p.getPPr().getJc().setVal(JcEnumeration.LEFT);
	  		    	  }
	  		    	  if(s == "right") {
	  		    		  p.getPPr().getJc().setVal(JcEnumeration.RIGHT);
	  		    	  }
	  	    	  }
	    	}
	      
	      R r = createRun("", str);
	      p.getContent().add(r); 

	    }
	    
	    return p;
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
