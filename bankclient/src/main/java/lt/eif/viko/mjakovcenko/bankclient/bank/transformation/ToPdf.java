package lt.eif.viko.mjakovcenko.bankclient.bank.transformation;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lt.eif.viko.mjakovcenko.bankclient.bank.client.BankClient;
import lt.eif.viko.mjakovcenko.bankclient.bank.wsdl.GetClientResponse;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.xmlgraphics.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * This class provides a method to convert XML documents into PDF format using XSLT.
 */
public class ToPdf {
    /**
     * Converts an XML file to an PDF file using an XSLT stylesheet.
     *  The method reads an XML file and applies an XSLT transformation defined in XSLT file.
     *  The result of the transformation is written to an PDF file.
     * @throws IOException if there is an error reading the XML file or writing the HTML file.
     * @throws TransformerException if an error occurs during the XSLT transformation process.
     * @throws  SAXException If any parse errors occur during the processing of the XML file.
     */
    public static void convertToPdf() throws Exception {
        BankClient bankClient = new BankClient();
        GetClientResponse response = bankClient.getClient("Name1");
        String fileName="Client.xml";

        convertResponseToXml(response,fileName);

        File xsltFile = new File("Client.xsl");
        StreamSource xmlSource = new StreamSource(fileName);
        FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

        String pdfFilePath = "src/main/resources/Client_1.pdf";
        File pdfFile = new File(pdfFilePath);

        try (OutputStream out = new FileOutputStream(pdfFile)) {
            if (pdfFile.exists()) {
                pdfFile.delete();
            }

            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(xmlSource, res);
        }
    }

    private static void convertResponseToXml(GetClientResponse response, String filePath) throws JAXBException, IOException {
        JAXBContext jaxbContext = JAXBContext.newInstance(GetClientResponse.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);


        File outputFile = new File(filePath);


        try (FileWriter fileWriter = new FileWriter(outputFile)) {
            marshaller.marshal(response, fileWriter);
        }
    }

}