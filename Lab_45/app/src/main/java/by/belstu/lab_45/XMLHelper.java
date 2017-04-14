package by.belstu.lab_45;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Владислав on 25.10.2016.
 */
public class XMLHelper {
    public Student[] array;
    int size;


    public void WriteToFile(ArrayList<Student> stud, String filename) {

        size = stud.size ();
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder ().newDocument ();

            Element persons = document.createElement ("persons");
            document.appendChild (persons);

            for (int i = 0; i < stud.size (); i++) {

                Element students = document.createElement ("student");
                persons.appendChild (students);

                Attr id = document.createAttribute ("id");
                id.setTextContent (String.valueOf (i));
                students.setAttributeNode (id);

                Element name = document.createElement ("name");
                name.setTextContent (stud.get (i).getName ());
                students.appendChild (name);

                Element surname = document.createElement ("surname");
                surname.setTextContent (stud.get (i).getSurname ());
                students.appendChild (surname);

                Element date = document.createElement ("date");
                date.setTextContent (String.valueOf (stud.get (i).getDate ()));
                students.appendChild (date);

            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer ();
            DOMSource source = new DOMSource (document);
            StreamResult result = new StreamResult (new File(System.getProperty ("user.dir") + File.separator + filename));

            transformer.transform (source, result);
            System.out.println ("Document saved!");


        } catch (ParserConfigurationException e) {
            e.printStackTrace ();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace ();
        } catch (TransformerException e) {
            e.printStackTrace ();
        }
    }

    public  void ReadToArr (String filename)
    {
        array = new Student[4];
        try {

            String n = null;
            String sn = null;
            String d = null;
            final File xmlFile = new File(System.getProperty("user.dir") + File.separator + filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);

            doc.getDocumentElement().normalize();

            // Получаем все узлы с именем "student"
            NodeList nodeList = doc.getElementsByTagName("student");

            for (int i = 0; i < array.length; i++)
            {

                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType())
                {
                    Element element = (Element) node;
                    String id = element.getAttribute("id");
                    n = element.getElementsByTagName("name").item(0).getTextContent();
                    sn = element.getElementsByTagName("surname").item(0).getTextContent();
                    d = element.getElementsByTagName("date").item(0).getTextContent();
                }

                array[i] = new Student (n,sn,d);
            }

        } catch (ParserConfigurationException | SAXException
                | IOException ex) {
            Logger.getLogger(Student.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    public  ArrayList<Student> convertArrayToList()
    {
        ArrayList<Student> xmlOutputStudents = new ArrayList<Student> (Arrays.asList(array));

        return  xmlOutputStudents;
    }




}
