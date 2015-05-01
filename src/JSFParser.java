import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class JSFParser {

		public static void main(String[] args) {
			
			String url="http://sandbox.api.ebaycommercenetwork.com/publisher/3.0/rest/GeneralSearch?apiKey=78b0db8a-0ee1-4939-a2f9-d3cd95ec0fcc&trackingId=7000610&keyword=laptop";
			DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
			
			try {
				DocumentBuilder builder=factory.newDocumentBuilder();
				Document doc= builder.parse(url);
				
				doc.getDocumentElement().normalize();
				
				NodeList nlist=doc.getElementsByTagName("product");
				int length=nlist.getLength();
				Node n;
				for(int i=0;i<nlist.getLength();i++)
				{
					n=nlist.item(i);
					
					if(n.getNodeType()==Node.ELEMENT_NODE)
					{
						Element el=(Element)n;
						System.out.println(el.getElementsByTagName("name").item(0).getTextContent());
						System.out.println(el.getElementsByTagName("sourceURL").item(0).getTextContent());
						System.out.println(el.getElementsByTagName("minPrice").item(0).getTextContent());
						System.out.println(el.getElementsByTagName("productOffersURL").item(0).getTextContent());
						System.out.println(el.getAttribute("id"));
					}
				}
				
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
