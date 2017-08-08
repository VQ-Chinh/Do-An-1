package appEnglish;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocFileXML {
	public static void main(String[] args) {
		try {

			File fXmlFile = new File("dataWords.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			// optional, but recommended
			// read this -
			// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("word");

			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {

				Node nNode = nList.item(temp);

				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {

					Element eElement = (Element) nNode;

					System.out.println("Name : " + eElement.getElementsByTagName("name").item(0).getTextContent());
					System.out.println("Spell : " + eElement.getElementsByTagName("spell").item(0).getTextContent());
					System.out.println("explan : " + eElement.getElementsByTagName("explan").item(0).getTextContent());
					System.out.println("explanVN : " + eElement.getElementsByTagName("explanVN").item(0).getTextContent());
					System.out.println("example : " + eElement.getElementsByTagName("example").item(0).getTextContent());
					System.out.println("exampleVN : " + eElement.getElementsByTagName("exampleVN").item(0).getTextContent());
					System.out.println("Lesson : " + eElement.getElementsByTagName("lesson").item(0).getTextContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
