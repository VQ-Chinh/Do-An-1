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
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptJobManager;

public class test {
	public static void main(String[] args) {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_45);
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

		try {
			HtmlPage page = webClient.getPage("http://s.cafef.vn/Lich-su-giao-dich-HVN-1.chn#data");
			webClient.waitForBackgroundJavaScript(2000);
			System.out.println(page.asXml());

			List<HtmlAnchor> listAnchor = page.getByXPath("(//table[@class='CafeF_Paging']//tr/td/a)");

			boolean key = true;
			while (key) {
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
		}
	}
}
