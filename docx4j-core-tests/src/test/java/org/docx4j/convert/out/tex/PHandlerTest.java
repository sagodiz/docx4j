package org.docx4j.convert.out.tex;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBElement;

import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;
import org.docx4j.wml.U;
import org.docx4j.wml.UnderlineEnumeration;
import org.docx4j.wml.PPrBase.PStyle;
import org.junit.BeforeClass;
import org.junit.Test;

public class PHandlerTest {

	private PHandler PHandler = null;
	private Writer output = null;
	
	private PHandler phandler = null;
	private P paragraph = null;
	
	private static int heading3Counter = 0;
	private static int heading4Counter = 0;
	
	private String exampleText = "exampleText";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void createPHandlerTest() {

		PHandler = PHandler.createPHandler();
		assertNotNull(PHandler);
	}

	@Test
	public void testHandleHeading3() {
		output = null;
		output = new StringWriter();
		
		paragraph = null;
		paragraph = createParagraph("Heading3", this.exampleText);
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
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "subsection" + "{" + this.exampleText + "}" + System.lineSeparator() + "\\label{" + "subsection" + ":" + this.heading3Counter++ + "}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleHeading4() {
		output = null;
		output = new StringWriter();
		
		paragraph = createParagraph("Heading4", this.exampleText);
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
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "subsubsection" + "{" + this.exampleText + "}" + System.lineSeparator() + "\\label{" + "subsubsection" + ":" + this.heading4Counter++ + "}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleLeft() {
		output = null;
		output = new StringWriter();
		
		paragraph = null;
		paragraph = createParagraph("left", "");
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
	   
		String test = System.lineSeparator() + System.lineSeparator() + "{\\raggedright " + "\\par}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleRight() {
		output = null;
		output = new StringWriter();
		
		paragraph = null;
		paragraph = createParagraph("right", "");
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
	   
		String test = System.lineSeparator() + System.lineSeparator() + "{\\raggedleft " + "\\par}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleHeading3AndRight() {
		output = null;
		output = new StringWriter();
		
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		
		paragraph = null;
		paragraph = createParagraph("Heading3", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		
		paragraph = null;
		paragraph = createParagraph("right", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "subsection" + "{" + "}" + System.lineSeparator() + "\\label{" + "subsection" + ":" + this.heading3Counter++ + "}" + System.lineSeparator() + System.lineSeparator() + "{\\raggedleft " + "\\par}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleHeading3AndRightWithText() {
		output = null;
		output = new StringWriter();
		
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		
		paragraph = null;
		paragraph = createParagraph("Heading3", this.exampleText);
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		
		paragraph = null;
		paragraph = createParagraph("right", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "subsection" + "{" + this.exampleText + "}" + System.lineSeparator() + "\\label{" + "subsection" + ":" + this.heading3Counter++ + "}" + System.lineSeparator() + System.lineSeparator() + "{\\raggedleft " + "\\par}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleHeading3AndLeft() {
		output = null;
		output = new StringWriter();
		
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		
		paragraph = null;
		paragraph = createParagraph("Heading3", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		
		paragraph = null;
		paragraph = createParagraph("left", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "subsection" + "{" + "}" + System.lineSeparator() + "\\label{" + "subsection" + ":" + this.heading3Counter++ + "}" + System.lineSeparator() + System.lineSeparator() + "{\\raggedright " + "\\par}";
		
		assertEquals(test, output.toString());
	}

	@Test
	public void testHandleHeading3AndLeftWithText() {
		output = null;
		output = new StringWriter();
		
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		
		paragraph = null;
		paragraph = createParagraph("Heading3", this.exampleText);
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		
		paragraph = null;
		paragraph = createParagraph("left", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "subsection" + "{" + this.exampleText + "}" + System.lineSeparator() + "\\label{" + "subsection" + ":" + this.heading3Counter++ + "}" + System.lineSeparator() + System.lineSeparator() + "{\\raggedright " + "\\par}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleHeading4AndRight() {
		output = null;
		output = new StringWriter();
		
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		
		paragraph = null;
		paragraph = createParagraph("Heading4", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		
		paragraph = null;
		paragraph = createParagraph("right", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "subsubsection" + "{" + "}" + System.lineSeparator() + "\\label{" + "subsubsection" + ":" + this.heading4Counter++ + "}" + System.lineSeparator() + System.lineSeparator() + "{\\raggedleft " + "\\par}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleHeading4AndRightWithText() {
		output = null;
		output = new StringWriter();
		
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		
		paragraph = null;
		paragraph = createParagraph("Heading4", this.exampleText);
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		
		paragraph = null;
		paragraph = createParagraph("right", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "subsubsection" + "{" + this.exampleText + "}" + System.lineSeparator() + "\\label{" + "subsubsection" + ":" + this.heading4Counter++ + "}" + System.lineSeparator() + System.lineSeparator() + "{\\raggedleft " + "\\par}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleHeading4AndLeft() {
		output = null;
		output = new StringWriter();
		
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		
		paragraph = null;
		paragraph = createParagraph("Heading4", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		
		paragraph = null;
		paragraph = createParagraph("left", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "subsubsection" + "{" + "}" + System.lineSeparator() + "\\label{" + "subsubsection" + ":" + this.heading4Counter++ + "}" + System.lineSeparator() + System.lineSeparator() + "{\\raggedright " + "\\par}";
		
		assertEquals(test, output.toString());
	}
	
	@Test
	public void testHandleHeading4AndLeftWithText() {
		output = null;
		output = new StringWriter();
		
		phandler = null;
		phandler = PHandler.createPHandler();
		phandler.setOutput(output);
		
		paragraph = null;
		paragraph = createParagraph("Heading4", this.exampleText);
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
		
		paragraph = null;
		paragraph = createParagraph("left", "");
		
		try {
			phandler.handle(paragraph, null, true);
		} catch (Docx4JException e) {
			fail(e.toString());
		} catch (IOException e) {
			fail(e.toString());
		}
	   
		String test = System.lineSeparator() + System.lineSeparator() + "\\" + "subsubsection" + "{" + this.exampleText + "}" + System.lineSeparator() + "\\label{" + "subsubsection" + ":" + this.heading4Counter++ + "}" + System.lineSeparator() + System.lineSeparator() + "{\\raggedright " + "\\par}";
		
		assertEquals(test, output.toString());
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
