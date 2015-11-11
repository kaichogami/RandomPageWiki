import java.net.URL;
import java.net.URI;
import java.awt.Desktop;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.*;
import java.util.Scanner;

public class RandomPage
{
    static class Info
    {
        String[] ids;
        String[] titles;
    }


    public static Document getRandom()
    {
        try
        {
            URL random;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            random = new URL("https://en.wikipedia.org/w/api.php?action=query&list=random&rnlimit=20&format=xml");

            Document doc = db.parse(random.openStream());

            return doc;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Info parse(Document doc)
    {
        Info info = new Info();

        NodeList list = doc.getElementsByTagName("page");
        String[] ids = new String[100];
        String[] titles = new String[100];

        for(int i =0; i < list.getLength(); i++)
            {
                Node N = list.item(i);
                if (N.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element E = (Element) N;
                    ids[i] = E.getAttribute("id");
                    titles[i] = E.getAttribute("title");
                }
            }
        info.ids = ids;
        info.titles = titles;
        return info;
    }

    public static void choose(Info info)
    {   try
        {
            Scanner input = new Scanner(System.in);
            String choice;
            String base = "https://en.wikipedia.org/wiki?curid=";


            Console console = System.console();
            for(int i =0; i< 20; i++)
            {
                System.out.println("Do you want to read about:" + info.titles[i]);

                choice = input.nextLine();

                if(choice.equals("y") || choice.equals("yes"))
                {
                    Desktop.getDesktop().browse(new URI(base+info.ids[i]));
                    System.exit(0);
                }

                if(choice.equals("bye"))
                {
                    System.exit(0);
                }
             
            choose(parse(getRandom()));
             }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws IOException
    {   System.out.println("Generating random page list will take 2-3 seconds depending on internet speed.");
        System.out.println("y = yes, bye=exit, anything else = no");
        choose(parse(getRandom()));
    }
}
