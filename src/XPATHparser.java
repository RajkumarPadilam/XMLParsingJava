
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
 



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
 



import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class XPATHparser {
    public static void main(String[] args) {
 
        try {
            FileInputStream file = new FileInputStream(new File("SigmodRecord.xml"));
                 
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
             
            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
             
            Document xmlDocument = builder.parse(file);
 
            XPath xPath =  XPathFactory.newInstance().newXPath();
 
            System.out.println("*****************************************************************************************************************************"); 
    		System.out.println("---Print the titles of all articles in volume=13/number=4 whose one of the authors is David Maier---\n");
            String expression = "/SigmodRecord/issue[volume=13 and number=4]/articles/article[authors/author='David Maier']/title";
            System.out.println(expression);
            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                System.out.println(nodeList.item(i).getFirstChild().getNodeValue()); 
            }
 
          
            System.out.println("*****************************************************************************************************************************");
    		System.out.println("---Print the author names off all articles whose title contains the word 'database' or 'Database'---\n");
            String expression2 = "/SigmodRecord/issue/articles/article[title[contains(.,'database') or contains(.,'Database')]]/authors/author";
            System.out.println(expression2);
            NodeList nodeList2 = (NodeList) xPath.compile(expression2).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int i = 0; i < nodeList2.getLength(); i++) {
                System.out.println(nodeList2.item(i).getFirstChild().getNodeValue()); 
            }

            System.out.println("*****************************************************************************************************************************");
    		System.out.println("---Print the volume/number and the init/end pages of the article titled 'Research in Knowledge Base Management Systems'---\n");
    		
            String expression3 = "/SigmodRecord/issue/articles/article[title[contains(.,'Research in Knowledge Base Management Systems')]]";
            System.out.println(expression3);
            Node node = (Node) xPath.compile(expression3).evaluate(xmlDocument, XPathConstants.NODE);
            
            if(node!=null){
            Node issue1= node.getParentNode().getParentNode();
            if(issue1!=null){            
            	NodeList issue1Childs=issue1.getChildNodes();
            	for (int i = 0; i < issue1Childs.getLength(); i++) { 
            		if(issue1Childs.item(i).getNodeName().equals("volume"))
            		System.out.println("volume :"+issue1Childs.item(i).getFirstChild().getNodeValue());
            		if(issue1Childs.item(i).getNodeName().equals("number"))
                		{System.out.println("number :"+issue1Childs.item(i).getFirstChild().getNodeValue());break;}
            		}
            	}
		            NodeList articleChilds=node.getChildNodes();
		        	for (int i = 0; i < articleChilds.getLength(); i++) {
		        		if(articleChilds.item(i).getNodeName().equals("initPage"))
		            		System.out.println("initPage :"+articleChilds.item(i).getFirstChild().getNodeValue());
		        		if(articleChilds.item(i).getNodeName().equals("endPage"))
		            		{System.out.println("endPage :"+articleChilds.item(i).getFirstChild().getNodeValue());break;}
		        	}
            }
            System.out.println("********************************************************************************************************");
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }       
    }
}
