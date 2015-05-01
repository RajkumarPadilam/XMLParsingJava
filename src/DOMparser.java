import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
 
public class DOMparser {
 
  public static void main(String argv[]) {
 
    try {
 
		File fXmlFile = new File("SigmodRecord.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
 
	
		doc.getDocumentElement().normalize();
	 
		//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		
		System.out.println("*****************************************************************************************************************************"); 
		System.out.println("---Print the titles of all articles in volume=13/number=4 whose one of the authors is David Maier---\n");
		NodeList nList = doc.getElementsByTagName("issue");
		NodeList articles=doc.getElementsByTagName("article");
		
		NodeList articleList=null;
	
		for (int temp = 0; temp < nList.getLength(); temp++) 
		{		 
		Node nNode = nList.item(temp);
		if (nNode.getNodeType() == Node.ELEMENT_NODE) 
			{ 
			Element eElement = (Element) nNode;
			if(eElement.getElementsByTagName("volume").item(0).getTextContent().equals("13"))
				if(eElement.getElementsByTagName("number").item(0).getTextContent().equals("4"))
					{articleList = eElement.getElementsByTagName("article");break;}
			}
		}
	
		for (int i = 0;(i < articleList.getLength()) && (articleList!=null); i++) 
		{
			Node article=articleList.item(i);
			if(article.getNodeType() == Node.ELEMENT_NODE)
			{
				Element eElement=(Element) article;
				NodeList author=eElement.getElementsByTagName("author");
				for(int j=0;j< author.getLength();j++){
					if(author.item(j).getTextContent().equals("David Maier"))
						{System.out.println(eElement.getElementsByTagName("title").item(0).getTextContent());break;}
				}
			}	
		}

		System.out.println("*****************************************************************************************************************************");
		System.out.println("---Print the author names off all articles whose title contains the word 'database' or 'Database'---\n");
		
		NodeList authors;
		for(int i=0;i<articles.getLength();i++)
		{
			Node article=articles.item(i);
			if(article.getNodeType()==Node.ELEMENT_NODE)
			{
				Element e=(Element) article;
				//System.out.println(e.getElementsByTagName("title").item(0).getTextContent()+" :"+e.getElementsByTagName("title").item(0).getTextContent().contains("database"));
				if( (e.getElementsByTagName("title").item(0).getTextContent().contains("database")) || (e.getElementsByTagName("title").item(0).getTextContent().contains("Database")) )
				{
					authors=e.getElementsByTagName("author");
					for(int j=0;j<authors.getLength();j++)
					{
						System.out.println(authors.item(j).getTextContent());
					}
				}
			}
		}
		
		System.out.println("*****************************************************************************************************************************");
		System.out.println("---Print the volume/number and the init/end pages of the article titled 'Research in Knowledge Base Management Systems'---\n");

	for(int i=0;i<articles.getLength();i++)
		{
			Node article=articles.item(i);
			if(article.getNodeType()==Node.ELEMENT_NODE)
			{
				Element e=(Element) article;
				if(e.getElementsByTagName("title").item(0).getTextContent().equals("Research in Knowledge Base Management Systems.") )
				{
					System.out.println("Title :"+e.getElementsByTagName("title").item(0).getTextContent());
					Element issueNode=(Element) e.getElementsByTagName("title").item(0).getParentNode().getParentNode().getParentNode();					
					System.out.println("Volume :"+issueNode.getElementsByTagName("volume").item(0).getTextContent());
					System.out.println("Number :"+issueNode.getElementsByTagName("number").item(0).getTextContent());
					System.out.println("initPage :"+e.getElementsByTagName("initPage").item(0).getTextContent());
					System.out.println("endPage :"+e.getElementsByTagName("endPage").item(0).getTextContent());
				
				}
			}
		}		
		
	System.out.println("*****************************************************************************************************************************");
	} catch (Exception e) {
	e.printStackTrace();
    }
  }
 
}