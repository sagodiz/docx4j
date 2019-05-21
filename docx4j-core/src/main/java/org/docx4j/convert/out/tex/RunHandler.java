package org.docx4j.convert.out.tex;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import javax.xml.bind.JAXBElement;

import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.wml.Br;
import org.docx4j.wml.CTFootnotes;
import org.docx4j.wml.CTFtnEdn;
import org.docx4j.wml.R;
import org.docx4j.wml.Text;


/**
 * This class handles the R elements.
 * Prints out the texts within the formatting tex commands if exists. 
 * */
public class RunHandler {
  
  private static RunHandler singleRunHandler = null;
  private Writer output = null;
  
  /**
   * private constructor in order to make singletons
   * */
  private RunHandler() {}
  
  /**
   * Method that creates an instance of this class. Returns an already created object or creates one.
   * @return An already created object or creates one
   * */
  public static RunHandler createRunHandler() {
    
    if ( null == singleRunHandler ) {
      singleRunHandler = new RunHandler();
    }
    
    return singleRunHandler;
  }
  
  /**
   * This method sets the output. A singleton may used multiple times so output must be able to change.
   * */
  public void setOutput(Writer out) {
    output = out;
  }
  
  /**
   * This method handles runs. Handles formating(bald, italic, underlined) and footnotes.
   * @author Lajkó Márk
   * @param run The run found in docx represented as a Java object
   * @throws Docx4JException
   * */
  public void handle(R run) throws Docx4JException, IOException {

    if ( null == output )
      throw new Docx4JException("Internal error: output not passed to RunHandler.");
    
    if ( null == run ) {
    	throw new Docx4JException("Internal error: run is null");
    }
    
    //++runNum;
    
    int closingBracketNum = 0;
    if (run.getRPr() != null && run.getRPr().getB() != null && run.getRPr().getB().isVal()) {
    	output.write("\\textbf{");
    	closingBracketNum++;
	}
    
    if (run.getRPr() != null && run.getRPr().getI() != null && run.getRPr().getI().isVal()) {
    	output.write("\\textit{");
    	closingBracketNum++;
    } 
    
    if (run.getRPr() != null && run.getRPr().getU() != null && run.getRPr().getU().getVal() != null && run.getRPr().getU().getVal().value() != null && run.getRPr().getU().getVal().value().equals( "single") ) {
    	output.write("\\underline{");
    	closingBracketNum++;
    }
    
    if ( null != run.getRPr() && null != run.getRPr().getRStyle() && null != run.getRPr().getRStyle().getVal() && run.getRPr().getRStyle().getVal().contains(Labels.FOOTNOTE_STYLE)) {
    	  	
        handleFootnote(run);
    }
        
    List<Object> runTextList = run.getContent();
    
    for (Object o:runTextList) {
    	
    	if (o instanceof JAXBElement && ((JAXBElement<?>) o).getDeclaredType() == Text.class) {
    		
    		output.write(escape((((Text) ((JAXBElement<?>) o).getValue()).getValue())));
    	}    	
    	else if ( o instanceof Br ) {
    	  
    	  if ( null != ((Br)o).getType() && ((Br)o).getType().value().equals(Labels.PAGE) ) {
    	    
    	    output.write("\\newpage");
    	  }
    	}
    }
        
    for (int i = 0; i < closingBracketNum; i++) {
    	output.write("}");
	}

  }
  
  //----------------private methods--------------
  
  /**
   * @author Haller Peter
   * @param  run The run thats footnote should be extracted.
   * @return footnote The command and text of the footnote for tex.
   * @throws Docx4JException 
   * @throws IOException 
   * */
  private String handleFootnote(R run) throws Docx4JException, IOException {

    //Get the ID of the footnote of this Run.
    
    List<Object> list = TraversalUtil.getChildrenImpl(run);
    int idToSearch = -1;
    for (Object obj : list) {
      
        Object textObject = XmlUtils.unwrap(obj);
        String className = textObject.getClass().getName();
        
        if(className.equals("org.docx4j.wml.CTFtnEdnRef")){
          idToSearch = ((org.docx4j.wml.CTFtnEdnRef)textObject).getId().intValue();
        }
        
    }      

    if ( idToSearch < 0 )
      throw new Docx4JException("Footnote ID is invalid: " + idToSearch);
    
    
	  String footnote = "";
	  
	  //Get footnote references and search for the one that is needed.
	  
	  CTFootnotes ftNotes = Docx2TexConverter.mainDocumentPart.getFootnotesPart().getJaxbElement();
	  List<CTFtnEdn> myNodeList = ftNotes.getFootnote();
	  
	  for(CTFtnEdn ctFtNode: myNodeList){
	    if ( ctFtNode.getId().intValue() == idToSearch ) {
	      List<Object> paraList = TraversalUtil.getChildrenImpl(ctFtNode);
	      
	      for (Object paraObj : paraList) {
	        if(paraObj.getClass().getName().equals("org.docx4j.wml.P")){
	          List<Object> paraWRList = TraversalUtil.getChildrenImpl(paraObj);
	          for (Object paraWR : paraWRList) {
	            if (paraWR.getClass().getName().equalsIgnoreCase("org.docx4j.wml.R")) {
	              List<Object> textList = TraversalUtil.getChildrenImpl(paraWR);
	              for (Object textObj : textList) {
	                Object textObject = XmlUtils.unwrap(textObj);
	                String className = textObject.getClass().getName();
	                if(className.equals("org.docx4j.wml.Text")){
	                  footnote += ((Text)textObject).getValue();
                  }
                  if ((className.contains("org.docx4j.wml.R$Sym"))) {
                    footnote += ((R.Sym) textObject).getChar();
                  }
	              }
	            }
	          }
	        }
	      }
	      break; //only one id ought to be here, so if it is found no more iteration is needed.
	    }
	  }
	  
	  footnote = "\\footnote{" + escape(footnote) + "}";

	  output.write(footnote);
	  
    return footnote;
  }
  
  private String escape(String string2escape) throws IOException {
    
    String escaped = string2escape;
    
    if ( string2escape.contains("\\") ) {
      escaped = string2escape.replace("\\", "\\textbackslash");
    }
    
    if ( escaped.contains("_") ) {
      escaped = escaped.replace("_", "\\_");
    }
   
    //other elements may appear to escape
    
    return escaped;
  }
}
