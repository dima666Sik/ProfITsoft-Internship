package ua.code.intership.proft.it.soft.service.generator;

import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.code.intership.proft.it.soft.model.StatisticsInfo;
import ua.code.intership.proft.it.soft.service.exception.FileGenerationException;
import ua.code.intership.proft.it.soft.service.statistic.PlanetStatisticsProcessor;
import ua.code.intership.proft.it.soft.service.statistic.StatisticsProcessor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Comparator;
import java.util.Set;

/**
 * XmlFileCreator is a class that implements the FileCreator interface and
 * provides functionality to generate XML files
 * based on statistics collected from JSON data.
 */
@Log4j2
public class XmlFileCreator implements FileCreator {
    private final StatisticsProcessor statisticsProcessor = PlanetStatisticsProcessor.getInstance();

    /**
     * Generates an XML file containing statistics data from collected data that
     * we can get access to by {@link StatisticsProcessor#getStatisticsSortedSet(Comparator)}.
     * The statistics data is sorted based on the number of repetitions of the specified attribute.
     *
     * @param pathToXmlDirectory the path to the directory where the XML file will be generated
     * @param attribute          the attribute to include in the file name and XML content
     * @return the generated XML file
     * @throws FileGenerationException if an error occurs while generating the XML file
     */
    @Override
    public File generate(String pathToXmlDirectory, String attribute) {
        try {
            Comparator<StatisticsInfo<? extends Comparable<?>>> comparator =
                    (el1, el2) -> el2.getNumberOfRepetitions() - el1.getNumberOfRepetitions();

            Set<StatisticsInfo<? extends Comparable<?>>> statisticsInfoSetByNumberOfRepetitions
                    = statisticsProcessor.getStatisticsSortedSet(comparator);

            statisticsProcessor.clearStatisticsSet();

            Document doc = createXmlDocument(statisticsInfoSetByNumberOfRepetitions);
            File xmlFile = writeXmlToFile(doc, pathToXmlDirectory, attribute);
            log.info("XML file generated successfully: {}", xmlFile.getPath());

            return xmlFile;
        } catch (Exception e) {
            log.error("Error generating XML file", e);
            throw new FileGenerationException("Error generating XML file", e);
        }
    }

    /**
     * Generates an XML document from the provided set of statistics information.
     *
     * @param statisticsInfoSet the set of statistics information
     * @return the generated XML document
     * @throws ParserConfigurationException if a DocumentBuilder cannot be created
     */
    private Document createXmlDocument(Set<StatisticsInfo<? extends Comparable<?>>> statisticsInfoSet) throws ParserConfigurationException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement("statistics");
        doc.appendChild(rootElement);

        for (StatisticsInfo<? extends Comparable<?>> statisticsInfo : statisticsInfoSet) {
            Element item = doc.createElement("item");
            Element value = doc.createElement("value");
            value.appendChild(doc.createTextNode(String.valueOf(statisticsInfo.getAttribute())));
            item.appendChild(value);
            Element count = doc.createElement("count");
            count.appendChild(doc.createTextNode(String.valueOf(statisticsInfo.getNumberOfRepetitions())));
            item.appendChild(count);
            rootElement.appendChild(item);
        }

        return doc;
    }

    /**
     * Writes the XML document to a file in the specified directory.
     *
     * @param doc                the XML document to write
     * @param pathToXmlDirectory the directory where the XML file will be generated
     * @param attribute          the attribute used for naming the XML file
     * @return the generated XML file
     * @throws TransformerException if an error occurs during the transformation
     */
    private File writeXmlToFile(Document doc, String pathToXmlDirectory, String attribute) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);

        File outputFile = new File(pathToXmlDirectory + File.separator + "statistics_by_" + attribute + ".xml");
        StreamResult result = new StreamResult(outputFile);
        transformer.transform(source, result);

        return outputFile;
    }

}
