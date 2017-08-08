package appEnglish;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlBold;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLDivElement;

public class LayDanhSachtu {

	public static void optionWebClient(WebClient webClient) {
		webClient.getOptions().setTimeout(120000);
		webClient.waitForBackgroundJavaScript(60000);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	}

	public static void LayDstu() throws TransformerException, ParserConfigurationException, SAXException, IOException {
		// link Lay tat ca danh sach tu
		String URL = "http://600tuvungtoeic.com/index.php?mod=lesson&id=";

		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.NoOpLog");
		optionWebClient(webClient);
		String filepath = "dataWords.xml";
		File fXmlFile = new File(filepath);
		fXmlFile.createNewFile();

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("Words");
		doc.appendChild(rootElement);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		DOMSource source = new DOMSource(doc);
		//
		StreamResult result = new StreamResult(fXmlFile);

		transformer.transform(source, result);
		try {
			for (int i = 1; i < 50; i++) {
				if (i == 12)
					i++;
				HtmlPage page = webClient.getPage(URL + i);
				//System.out.println(page.asXml());
				webClient.waitForBackgroundJavaScript(2000);

				// Lay the qua trang tiep theo
				List<HtmlSpan> span = page.getByXPath("(//div[@class='noidung']//span)");
				List<HTMLDivElement> divText = page
						.getByXPath("(//div[@class='noidung']/span/following-sibling::text())");
				List<HTMLDivElement> div = page.getByXPath("(//div[@class='noidung'])");

				List<HtmlBold> bold = page.getByXPath("(//div[@class='noidung'])/b");

				System.out.println(bold.get(0).asText());
				writeXml(span, divText, div, bold, i);
				// ghiFileDSCK(dscophieu, dslinkcophieu);
				// ghiFileLSGD(webClient);
				// while (next) {
				// next = false;
				//
				// //Lay danh sach co phieu
				//
				//
				//
				// for (int i = 0; i < trangTiepTheo.size(); i++) {
				// if (trangTiepTheo.get(i).asText().equals("Trang sau")) {
				// next = true;
				// count++;
				// System.out.println(trangTiepTheo.get(i).asXml());
				// page = trangTiepTheo.get(i).click();
				// webClient.waitForBackgroundJavaScript(1500);
				// trangTiepTheo =
				// page.getByXPath("(//td[@id='CafeF_ThiTruongNiemYet_Trang']//a)");
				// break;
				// }
				// }
				// }
				// System.out.println(count);
				// System.out.println(listAnchor1.get(0).asText());
				// HtmlPage pageall = listAnchor1.get(0).click();
				// webClient.waitForBackgroundJavaScript(2000);
				// System.out.println(pageall.asXml());
				// List<HtmlAnchor> listAnchor2 =
				// pageall.getByXPath("(//td[@id='CafeF_ThiTruongNiemYet_Content'])//a/@href");
				// System.out.println(listAnchor2.get(1).asXml());
				// HtmlPage page4 = listAnchor.get(18).click();
				// webClient.waitForBackgroundJavaScript(2000);
				// System.out.println(listAnchor.get(10).asXml());
				// webClient.waitForBackgroundJavaScript(2000);
				// List<HtmlElement> listDate =
				// page4.getByXPath("(//td[@class='Item_DateItem'])");
				// System.out.println(listDate.get(0).asText());
			}
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Done");
	}

	public static void writeXml(List<HtmlSpan> span, List<HTMLDivElement> divText, List<HTMLDivElement> div,
			List<HtmlBold> bold, int key)
			throws ParserConfigurationException, SAXException, IOException, TransformerException {

		String filepath = "dataWords.xml";

		File fXmlFile = new File(filepath);
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.parse(fXmlFile);
		;
		Node rootElement = doc.getFirstChild();
		;

		for (int j = 0; j < div.size(); j++) {

			Element words = doc.createElement("word");

			// name elements
			Element name = doc.createElement("name");
			String a = span.get(j * 5).asText() + "";
			name.appendChild(doc.createTextNode(a));
			words.appendChild(name);

			// spell elements
			Element spell = doc.createElement("spell");
			spell.appendChild(doc.createTextNode(span.get(j * 5 + 1).asText()));
			words.appendChild(spell);

			// explan elements
			Element explan = doc.createElement("explan");
			explan.appendChild(doc.createTextNode(divText.get(j * 9 + 2) + ""));
			words.appendChild(explan);

			// explanVN elements
			Element explanVN = doc.createElement("explanVN");
			explanVN.appendChild(doc.createTextNode(divText.get(j * 9 + 4) + ""));
			words.appendChild(explanVN);

			// example elements
			Element example = doc.createElement("example");
			example.appendChild(doc.createTextNode(divText.get(j * 9 + 6) + ""));
			words.appendChild(example);

			// exampleVN elements
			Element exampleVN = doc.createElement("exampleVN");
			exampleVN.appendChild(doc.createTextNode(bold.get(0).asText()));
			words.appendChild(exampleVN);

			// lesson elements
			Element lesson = doc.createElement("lesson");
			lesson.appendChild(doc.createTextNode(key + ""));
			words.appendChild(lesson);

			rootElement.appendChild(words);

		}

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();

		DOMSource source = new DOMSource(doc);
		//
		StreamResult result = new StreamResult(fXmlFile);

		transformer.transform(source, result);
		
	}

	public static void main(String[] args) throws TransformerException, ParserConfigurationException, SAXException {
		try {
			LayDstu();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
