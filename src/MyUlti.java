import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.LogFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class MyUlti {
	
	
	public static void optionWebClient(WebClient webClient){
		
	}
	
	public static void getAllCompany(){
		String URL = "http://s.cafef.vn/du-lieu-doanh-nghiep.chn#data";
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log",
				"org.apache.commons.logging.impl.NoOpLog");
		webClient.getOptions().setTimeout(120000);
		webClient.waitForBackgroundJavaScript(60000);
		webClient.getOptions().setRedirectEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setUseInsecureSSL(true);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		optionWebClient(webClient);
		try {
			HtmlPage page = webClient.getPage(URL);
			webClient.waitForBackgroundJavaScript(3000);
			List<HtmlAnchor> listAnchor = page.getByXPath("//td[@id='CafeF_ThiTruongNiemYet_Trang']/a");
			
			for (HtmlAnchor htmlAnchor : listAnchor) {
				System.out.println(htmlAnchor.asXml());
			}
			HtmlPage page4 = listAnchor.get(0).click();
			webClient.waitForBackgroundJavaScript(5000);
			
			for(int i =0; i< 2; i++){
			List<HtmlElement> listDate = page4.getByXPath("(//td[@id='CafeF_ThiTruongNiemYet_TongSoTrang'])");
			System.out.println(listDate.get(0).asText());
			listAnchor = page4.getByXPath("//td[@id='CafeF_ThiTruongNiemYet_Trang']/a");
			page4 = listAnchor.get(1).click();
			webClient.waitForBackgroundJavaScript(5000);
			}

		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MyUlti.getAllCompany();
	}
}
