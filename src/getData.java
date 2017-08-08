import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class getData {

	
	public static void main(String[] args) {

		String link = "http://priceonline.hsc.com.vn/Default.aspx?lang=vn#";
		
		URL url;
		StringBuilder content = new StringBuilder();
		try {
			url = new URL(link);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = br.readLine();
			
			
			while (line != null){
				content.append("\n"+line);
				line = br.readLine();
			}
			br.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(content);
	}
}
