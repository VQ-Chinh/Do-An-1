package SourceFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableDataCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class ThuThapDuLieu {
	public static void optionWebClient(WebClient webClient) {
		webClient.getOptions().setTimeout(120000);
		webClient.waitForBackgroundJavaScript(60000);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
	}

	public static void ghiFileDSCK(List<HtmlTableDataCell> dscophieu, List<HtmlAnchor> dslinkcophieu)
			throws ParserConfigurationException, SAXException, IOException {
		String filepath = "data/DsCK.xml";
		for (int i = 0; i < dscophieu.size(); i = i + 4) {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);

			Node rootnode = doc.getFirstChild();

			Element CK = doc.createElement("ChungKhoan");
			Attr attr = doc.createAttribute("MaChungKhoan");
			attr.setValue(dscophieu.get(i).asText());
			CK.setAttributeNode(attr);

			Element tenCT = doc.createElement("TenCT");
			tenCT.appendChild(doc.createTextNode(dscophieu.get(i + 1).asText()));
			CK.appendChild(tenCT);

			Element link = doc.createElement("Link");
			link.appendChild(doc.createTextNode("http://s.cafef.vn" + dslinkcophieu.get(i / 2).getAttribute("href")));
			CK.appendChild(link);

			Element san = doc.createElement("San");
			san.appendChild(doc.createTextNode(dscophieu.get(i + 3).asText()));
			CK.appendChild(san);

			rootnode.appendChild(CK);

			try {
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer;
				transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(filepath));
				transformer.transform(source, result);
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(dscophieu.get(i).asText() + " Done!");
		}
	}

	public static List<String> docFileDsCK() {

		String filepath = "data/DsCK.xml";
		
		File fXmlFile = new File(filepath);

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		List<String> list = new ArrayList<>();
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("ChungKhoan");

			System.out.println("----------------------------");

			Node nNode = nList.item(1);

			System.out.println("\nCurrent Element :" + nNode.getNodeName());

			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;
				// lay link lich su giao dich
				System.out.println("MaCK : " + eElement.getAttribute("MaChungKhoan"));
				System.out.println("Ten Cong Ty : " + eElement.getElementsByTagName("TenCT").item(0).getTextContent());
				System.out.println("Link :" + eElement.getElementsByTagName("Link").item(0).getTextContent());
				list.add(eElement.getAttribute("MaChungKhoan"));
			}

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

	public static void ghiFileLSGD(WebClient webClient) throws ParserConfigurationException, SAXException, IOException, TransformerException {

		// optional, but recommended
		// read this -
		// http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		List<String> list = docFileDsCK();
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("LichSuGiaoDich");
		doc.appendChild(rootElement);

		// staff elements
		

	

		// write the content into xml file
		
		// doc va ghi file xml
		String URL = "http://s.cafef.vn/Lich-su-giao-dich-" + list.get(0) + "-1.chn#data";
		HtmlPage page = webClient.getPage(URL);
		webClient.waitForBackgroundJavaScript(1000);
		System.out.println(page.asXml());
		
		List<HtmlAnchor> listAnchor = page.getByXPath("(//table[@class='CafeF_Paging']//tr/td/a)");
		// kiem tra du trang hay chua
		boolean key = true;
		while (key) {
			// lay thong tin trang
			List<HtmlTableDataCell> dsthongtin = page
					.getByXPath("(//div[@id='ctl00_ContentPlaceHolder1_ctl03_divHO']//tr)");
			List<HtmlTableDataCell> thongtin = page
					.getByXPath("(//div[@id='ctl00_ContentPlaceHolder1_ctl03_divHO']//tr[3]/td)");

			for (int i = 3; i < dsthongtin.size(); i++) {
				thongtin = page.getByXPath("(//div[@id='ctl00_ContentPlaceHolder1_ctl03_divHO']//tr[" + i + "]/td)");
				System.out.println(thongtin.get(0).asXml());
				
				Element giaodich = doc.createElement("GiaoDich");
				rootElement.appendChild(giaodich);

				

				// firstname elements
				Element ngay = doc.createElement("Ngay");
				ngay.appendChild(doc.createTextNode(thongtin.get(0).asText()));
				giaodich.appendChild(ngay);
				
				rootElement.appendChild(giaodich);
			}
			// chuyen trang
			key = false;
			for (HtmlAnchor htmlAnchor : listAnchor) {
				if (htmlAnchor.asText().equals(">")) {
					key = true;
					System.out.println(htmlAnchor.getAttribute("title"));
					page = htmlAnchor.click();
					webClient.waitForBackgroundJavaScript(2000);
					listAnchor = page.getByXPath("(//table[@class='CafeF_Paging']//tr/td/a)");
					break;
				}
			}
		}
		
		
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		File file =  new File("data/"+list.get(0)+".xml");
		file.createNewFile();
		StreamResult result = new StreamResult(file);

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		// List<HtmlTableDataCell> thongtin =
		// dsTrangLichSu.get(2).getByXPath("(//tr/td)");
		//
		// System.out.println(thongtin.get(0).asXml());

	}

	public static void LayDsCK() throws TransformerException {
		// link Lay tat ca danh sach co phieu
		String URL = "http://s.cafef.vn/du-lieu-doanh-nghiep.chn#data";

		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.NoOpLog");
		optionWebClient(webClient);
		try {
			HtmlPage page = webClient.getPage(URL);
			System.out.println(page.asXml());
			webClient.waitForBackgroundJavaScript(2000);

			// Lay the qua trang tiep theo
			List<HtmlAnchor> trangTiepTheo = page.getByXPath("(//td[@id='CafeF_ThiTruongNiemYet_Trang']//a)");
			List<HtmlTableDataCell> dscophieu = page.getByXPath("(//td[@id='CafeF_ThiTruongNiemYet_Content']//td)");
			List<HtmlAnchor> dslinkcophieu = page.getByXPath("(//td[@id='CafeF_ThiTruongNiemYet_Content']//a)");

			System.out.println();
			boolean next = true;
			int count = 1;

			// ghiFileDSCK(dscophieu, dslinkcophieu);
			ghiFileLSGD(webClient);
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

		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			LayDsCK();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
