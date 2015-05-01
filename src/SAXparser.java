import java.util.ArrayList;
import java.util.ListIterator;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
 
public class SAXparser {
	
	static ArrayList<String> question1=new ArrayList<String>();
	static ArrayList<String> question2=new ArrayList<String>();
	static ArrayList<String> question3=new ArrayList<String>();
 
   public static void main(String argv[]) {
 
    try {
 
	SAXParserFactory factory = SAXParserFactory.newInstance();
	SAXParser saxParser = factory.newSAXParser();
 
	DefaultHandler handler = new DefaultHandler() {
		
	
	
	boolean bvolume = false;
	boolean bnumber = false;
	
	boolean canReadTitle=false;
	boolean canReadAuthor=false;
	boolean canReadEnd=false;
	
	boolean authorsElementStarted=false;
	boolean success=false;
	
	boolean btitle = false;
	boolean bauthors = false;
	boolean bauthor=false;
	boolean init=false;
	boolean	end=false;
	
	String printableTitle=null;
	String volumeStore,numberStore,initStore,endStore;
 
	public void startElement(String uri, String localName,String qName, 
                Attributes attributes) throws SAXException {
 
		//System.out.println("Start Element :" + qName);
 
		if (qName.equalsIgnoreCase("volume")) {
			bvolume = true;
		}
 
		if (qName.equalsIgnoreCase("number")) {
			bnumber = true;
		}
 
		if (qName.equalsIgnoreCase("title")) {
			btitle = true;
		}
		
		if (qName.equalsIgnoreCase("title")) {
			btitle = true;
		}
		if (qName.equalsIgnoreCase("author")) {
			bauthor = true;
		}
		if (qName.equalsIgnoreCase("initPage")) {
			init = true;
		}
		if (qName.equalsIgnoreCase("endPage")) {
			end = true;
		}
 
		if (qName.equalsIgnoreCase("authors")) {
			bauthors = true;
			if(!authorsElementStarted)
				authorsElementStarted=true;
		}
 
	}
 
	public void endElement(String uri, String localName,
		String qName) throws SAXException {
 		if (qName.equalsIgnoreCase("issue")) {
			if(canReadTitle)
				canReadTitle=false;			
		}
 		if (qName.equalsIgnoreCase("SigmodRecord")) {
 			printAnswers();
 		}
 
	}
 
	public void characters(char ch[], int start, int length) throws SAXException { 
		if (bvolume) {
			if(new String(ch,start,length).equals("13"))
				canReadTitle=true;
			
			volumeStore=new String(ch,start,length);
			bvolume=false;
		}
 
		if (bnumber) {
			if(!((new String(ch,start,length).equals("4"))&&(canReadTitle)) )
				canReadTitle=false;
			numberStore=new String(ch,start,length);
			bnumber=false;
		}
 
		if (btitle) {
			if(canReadTitle)
				printableTitle=new String(ch,start,length);
			
			if( (new String(ch,start,length).contains("database")) || (new String(ch,start,length).contains("Database")) )
				canReadAuthor=true;
			else
				canReadAuthor=false;
			
			if(new String(ch,start,length).contains("Research in Knowledge Base Management Systems"))
					canReadEnd=true;
			
			btitle=false;
		}
 
		if (bauthor) {
			if(new String(ch,start,length).equals("David Maier") )
				{
					if(canReadTitle)
					{
						question1.add(printableTitle);
						//System.out.println(printableTitle);
					}	
				}
			if(canReadAuthor)
			{
				question2.add(new String(ch, start, length));
				//System.out.println(new String(ch, start, length));				
			}
			bauthor=false;
		}
		if(init){
			if(canReadEnd)
				initStore=new String(ch,start,length);
			init=false;
		}
		if(end){
			if(canReadEnd)
			{
				endStore=new String(ch,start,length); canReadEnd=false;
				question3.add("Volume :"+volumeStore);
				question3.add("Number :"+numberStore);
				question3.add("initPage :"+initStore);
				question3.add("endPage :"+endStore);
			}	
			end=false;
		}
	 }
  };
 
       saxParser.parse("SigmodRecord.xml", handler);
 
     } catch (Exception e) {
       e.printStackTrace();
     }
 
   }
   
   public static void printAnswers()
   {
	   ListIterator iterator;
	   System.out.println("*****************************************************************************************************************************"); 
	   System.out.println("---Print the titles of all articles in volume=13/number=4 whose one of the authors is David Maier---\n");
	   
	   iterator=question1.listIterator();
	   while(iterator.hasNext())
		   System.out.println(iterator.next());
	   
	System.out.println("\n*****************************************************************************************************************************");
	System.out.println("---Print the author names off all articles whose title contains the word 'database' or 'Database'---\n");
	
		iterator=question2.listIterator();
		while(iterator.hasNext())
		System.out.println(iterator.next());
		
	System.out.println("\n*****************************************************************************************************************************");
	System.out.println("---Print the volume/number and the init/end pages of the article titled 'Research in Knowledge Base Management Systems'---\n");

		iterator=question3.listIterator();
		while(iterator.hasNext())
		System.out.println(iterator.next());
   }
 
}