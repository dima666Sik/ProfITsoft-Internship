package ua.code.intership.proft.it.soft.springspaceinfohw2.service.report;

import org.springframework.data.domain.Page;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.exception.FileGenerationException;
import ua.code.intership.proft.it.soft.springspaceinfohw2.service.reflect.ClassMetadataProvider;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.function.IntFunction;

public class XmlReportCreator<T> implements ReportCreator<T> {
    private static final String XML_FILE_NAME = "planet_report_by_planetarySystemId";
    public static final String XML = ".xml";

    @Override
    public File createReport(IntFunction<Page<T>> pageFunction, String[] columnTitles) {
        try {
            int pageNumber = 0;
            boolean hasNextPage = true;

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("statistics");
            doc.appendChild(rootElement);

            while (hasNextPage) {
                Page<T> page = pageFunction.apply(pageNumber);

                for (T entity : page.getContent()) {
                    String[] fieldsDataFromGenericEntity = ClassMetadataProvider.extractFieldsDataFromGenericEntity(entity);

                    Element item = doc.createElement(entity.getClass()
                                                           .getSimpleName()
                                                           .toLowerCase());

                    if (fieldsDataFromGenericEntity.length != columnTitles.length)
                        throw new IllegalArgumentException("Count of fields data not coincide with count of column title!");

                    for (int i = 0; i < fieldsDataFromGenericEntity.length; i++) {
                        Element fieldElement = doc.createElement(columnTitles[i]);
                        fieldElement.appendChild(doc.createTextNode(fieldsDataFromGenericEntity[i]));
                        item.appendChild(fieldElement);
                    }

                }

                hasNextPage = page.hasNext();
                pageNumber++;
            }

            return transformToXmlFile(doc);
        } catch (IOException
                 | ParserConfigurationException
                 | TransformerException e) {
            throw new FileGenerationException("Failed to generate report by object", e);
        }
    }

    private File transformToXmlFile(Document doc) throws TransformerException, IOException {
        TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        File outputFile = File.createTempFile(XML_FILE_NAME, XML);
        StreamResult result = new StreamResult(outputFile);
        transformer.transform(source, result);

        return outputFile;
    }
}
