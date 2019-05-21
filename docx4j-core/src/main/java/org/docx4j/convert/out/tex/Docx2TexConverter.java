/**
 * 
 */
package org.docx4j.convert.out.tex;

import java.io.File;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.P;
import org.docx4j.wml.Tbl;


/**
 * @author Zoltán Ságodi
 * This class represents a converter from docx to tex.
 * It can load a file or use an already loaded file as an object (org.docx4j.openpackaging.packages.WordprocessingMLPackage).
 */
public class Docx2TexConverter {
  
  private WordprocessingMLPackage document = null;
  private Writer output = null;
  private TableHandler tableHandler = TableHandler.createTableHandler();
  private PHandler pHandler = PHandler.createPHandler();
  static MainDocumentPart mainDocumentPart = null;
  
  //-------------------constructors------------------
  /**
   * Default constructor for the Docx2TexConverter.
   * If this constructor is used the user must load the file manually using the load method and add the output file.
   * */
  public Docx2TexConverter() {
  }
  
  /**
   * Constructor that gets a WordprocessingMLPackage representing the file to be converted and a file to output tex file.
   * @param document The document represented by a WordprocessingMLPackage object. This document is to be converted.
   * @param output File that should contain the converted output.
   * @throws Docx4JException 
   * @throws IOException 
   */
  public Docx2TexConverter(WordprocessingMLPackage document, File output) throws Docx4JException, IOException {
    this.document = document;
    setOutPutFile(output);
  }
  
  /**
   * Constructor that gets a WordprocessingMLPackage representing the file to be converted and a file to output tex file.
   * @param document The document represented by a WordprocessingMLPackage object. This document is to be converted.
   * @param outputPath String that contains the path to the output file.
   * @throws Docx4JException 
   * @throws IOException 
   */
  public Docx2TexConverter(WordprocessingMLPackage document, String outputPath) throws Docx4JException, IOException {
    this.document = document;
    setOutPutFile(outputPath);
  }
  
  /**
   * Constructor that gets a String.
   * @param pathToFile String parameter that contains the path to the docx file to be converted.
   * @param output File that should contain the converted output.
   * @throws Docx4JException 
   * @throws Exception 
   * */
  public Docx2TexConverter(String pathToFile, File output) throws Docx4JException, Exception {
    load(pathToFile);
    setOutPutFile(output);
  }
  
  /**
   * Constructor that gets a String.
   * @param pathToFile String parameter that contains the path to the docx file to be converted.
   * @param outputPath String that contains the path to the output file.
   * @throws Exception 
   * */
  public Docx2TexConverter(String pathToFile, String outputPath) throws Exception {
    load(pathToFile);
    setOutPutFile(outputPath);
  }
  
  /**
   * Constructor that gets a File.
   * @param fileToLoad File object that loads the docx file to be converted.
   * @param output File that should contain the converted output.
   * @throws Docx4JException 
   * @throws IOException 
   * */
  public Docx2TexConverter(File fileToLoad, File output) throws Docx4JException, IOException {
    load(fileToLoad);
    setOutPutFile(output);
  }
  
  /**
   * Constructor that gets a File.
   * @param fileToLoad File object that loads the docx file to be converted.
   * @param outputPath String that contains the path to the output file.
   * @throws Exception 
   * */
  public Docx2TexConverter(File fileToLoad, String output) throws Exception {
    load(fileToLoad);
    setOutPutFile(output);
  }
  
  //--------------------public methods----------------
  
  /**
   * This method sets the output file. This file should contain the tex code.
   * @param pathToOutput String that contains the path to the tex file.
   * @throws Docx4JException 
   * @throws IOException 
   * */
  public void setOutPutFile(String pathToOutput ) throws Docx4JException, IOException {
    if ( null == pathToOutput || pathToOutput.isEmpty() )
      throw new Docx4JException("Ouput file must be specified.");
    
    output = new FileWriter(pathToOutput);
  }
  
  /**
   * This method sets the output file. This file should contain the tex code.
   * @param pathToOutput String that contains the path to the tex file.
   * @throws Docx4JException 
   * @throws IOException 
   * */
  public void setOutPutFile(File output ) throws Docx4JException, IOException {
    if ( null == output )
      throw new Docx4JException("Ouput file can not be null.");
    
    this.output = new FileWriter(output);
  }
  
  /**
   * This method loads a file represented by its path.
   * @param pathToFile the file that should be loaded.
   * @throws Exception
   * @throws Docx4JException
   * */
  public void load(String pathToFile) throws Docx4JException, Exception {
    
    if ( null == pathToFile )
      throw new Exception("Path to file is null");
    
    if ( pathToFile.isEmpty() )
      throw new Exception("Empty string for path to file.");
    
    //TODO: might check if it is a valid path.
    
    File file = new File(pathToFile);
    load(file);
  }
  
  /**
   * This method loads a file represented by a Java object.
   * @param fileToLoad the file that should be loaded.
   * @throws Docx4JException
   * */
  public void load(File fileToLoad) throws Docx4JException {
    
    if ( null == fileToLoad ) 
      throw new Docx4JException("File must not be null.");
    
    document = Docx4J.load(fileToLoad);
  }
  
  /**
   * This method converts the loaded docx document to the tex format.
   * @throws Docx4JException 
   * @throws IOException 
   * */
  public void convert() throws Docx4JException, IOException {
    
    if ( null == document )
      throw new Docx4JException("The document must not be null.");
    
    if ( null == output )
      throw new Docx4JException("The output file must be specified.");
  
    mainDocumentPart = document.getMainDocumentPart();
    List<Object> list = mainDocumentPart.getContents().getBody().getContent();
    
    output.write("\\documentclass[12pt]{book}");
    output.write(System.lineSeparator());
    output.write("\\usepackage{hyperref}");
    output.write(System.lineSeparator());
    
    //TODO:usepackage{}
    
    output.write("\\begin{document}");
    
    for ( Object bodyElement : list ) {

      if ( bodyElement instanceof P ) {
        pHandler.setOutput(output);
        pHandler.handle((P) bodyElement, null, true);
      }
      if ( bodyElement instanceof JAXBElement<?> && ((JAXBElement<?>)bodyElement).getValue() instanceof Tbl ) {
        tableHandler.setOutput(output);
        tableHandler.handle((Tbl)((JAXBElement<?>)bodyElement).getValue(), "");
      }
      
      /*
       * Other elements specified in the xsd could be loaded. Future work.
       * */
    }
    
    output.write(System.lineSeparator());
    output.write("\\end{document}");
    output.close();
  }
}
