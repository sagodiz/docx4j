package org.docx4j.convert.out.tex;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.relationships.Relationship;
import org.docx4j.wml.P;
import org.docx4j.wml.R;

public class PHandler {

	private static PHandler singlePHandler = null;
	private Writer output = null;
	private RunHandler rHandler = RunHandler.createRunHandler();

	private int chapterNum = 0;
	private int sectionNum = 0;
	private int subsectionNum = 0;
	private int subsubsectionNum = 0;
	//private int subtitleNum = 0;

	/**
	 * private constructor in order to make singletons
	 */
	private PHandler() {
	}

	/**
	 * Method that creates an instance of this class. Returns an already created
	 * object or creates one.
	 * 
	 * @return An already created object or creates one
	 */
	public static PHandler createPHandler() {

		if (null == singlePHandler) {
			singlePHandler = new PHandler();
		}

		return singlePHandler;
	}

	/**
	 * This method sets the output. A singleton may used multiple times so output
	 * must be able to change.
	 */
	public void setOutput(Writer out) {
		output = out;
	}

	/**
	 * This method handles tables. Collects rows and cells, process them.
	 * 
	 * @param table     The table found in docx represented as a Java object
	 * @param tabular   String that contains leading spaces for the element to have
	 *                  well formatted tex code.
	 * @param isNewLine Sets if a new line should be inserted before paragraph.
	 * @throws Docx4JException
	 */

	public void handle(P paragraph, String tabular, boolean isNewLine) throws Docx4JException, IOException {

		if (null == output)
			throw new Docx4JException("Internal error: output is null");

		rHandler.setOutput(output);

		if (tabular == null) {
			tabular = new String();
		}

		if (isNewLine) {
			output.write(System.lineSeparator());
			output.write(System.lineSeparator());
		}

		List<Object> paragraphElements = paragraph.getContent();

		if (paragraph.getPPr() != null) {

			if (paragraph.getPPr().getPStyle() != null) {

				switch (paragraph.getPPr().getPStyle().getVal()) {

				case "Heading1":
					chapterNum = this.pStyleHandler(paragraphElements, chapterNum, "chapter");
					break;
				case "Heading2":
					sectionNum = this.pStyleHandler(paragraphElements, sectionNum, "section");
					break;
				case "Heading3":
					subsectionNum = this.pStyleHandler(paragraphElements, subsectionNum, "subsection");
					break;
				case "Heading4":
					subsubsectionNum = this.pStyleHandler(paragraphElements, subsubsectionNum, "subsubsection");
					break;
				/*case "Subtitle":
					subtitleNum = this.pStyleHandler(paragraphElements, subtitleNum, "subtitle");
					break;*/
				default:
					break;
				}
			} else {

				/*
				 * CENTERING
				 * 
				 * 
				 */

				if (paragraph.getPPr().getJc() != null) {

					if (paragraph.getPPr().getJc().getVal() != null) {

						if (paragraph.getPPr().getJc().getVal().value() != null) {

							if (paragraph.getPPr().getJc().getVal().value().equals("center")) {
								output.write("{\\centering ");
								for (Object r : paragraphElements) {

									if (r instanceof R) {
										rHandler.handle((R) r);
									}
								}
								
							} else if (paragraph.getPPr().getJc().getVal().value().equals("left")) {
								output.write("{\\raggedright ");
								for (Object r : paragraphElements) {

									if (r instanceof R) {
										rHandler.handle((R) r);
									}
								}

							} else if (paragraph.getPPr().getJc().getVal().value().equals("right")) {
								output.write("{\\raggedleft ");
								for (Object r : paragraphElements) {

									if (r instanceof R) {
										rHandler.handle((R) r);
									}
								}

							}
							output.write("\\par}");
						}

					}
				} else {

					linker(paragraphElements);

				}

//End of TODOS part
			}
		} else {

			linker(paragraphElements);
		}

	}
	/**
	 * 
	 * @param paragraphElements Elements of the paragraph (List<Object>)
	 * @param sum the counter variable of paragraph element type
	 * @param val string value for output
	 * @return new sum
	 * @throws IOException
	 * @throws Docx4JException
	 * @author Norbert Eszes
	 */
	int pStyleHandler(List<Object> paragraphElements, int sum, String val) throws IOException, Docx4JException {
		output.write("\\" + val + "{");
		for (Object r : paragraphElements) {

			if (r instanceof R) {
				rHandler.handle((R) r);
			}
		}
		output.write("}");
		output.write(System.lineSeparator());
		output.write("\\label{" + val + ":" + (sum++) + "}");

		return sum;
	}

	void linker(List<Object> paragraphElements) throws IOException, Docx4JException {
		for (Object element : paragraphElements) {
			if (element instanceof JAXBElement) {
				if (((JAXBElement<?>) element).getValue().getClass().getSimpleName().equals("Hyperlink")) {

					P.Hyperlink hyperlink = ((P.Hyperlink) ((JAXBElement<?>) element).getValue());

					Relationship rs = Docx2TexConverter.mainDocumentPart.getRelationshipsPart()
							.getRelationshipByID(hyperlink.getId());
					// output.write("\\hyperlink{");
					output.write("\\href{");
					output.write(rs.getTarget());
					output.write("}");
					output.write("{");
					/*
					 * for (Object textObj : textList) {
					 * 
					 * Object runObject = XmlUtils.unwrap(textObj); if (runObject instanceof R) {
					 * rHandler.handle((R) runObject); } }
					 */

					List<Object> runs = hyperlink.getContent();
					for (Object r : runs) {

						if (r instanceof R) {
							rHandler.handle((R) r);
						}
					}

					output.write("}");

				}
			} else {

				if (element instanceof R) {
					rHandler.handle((R) element);
				}
			}
		}
	}

}
