package ua.code.intership.proft.it.soft.service.generator;

import lombok.extern.log4j.Log4j2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.code.intership.proft.it.soft.model.dto.StatisticsDto;
import ua.code.intership.proft.it.soft.service.statistic.PlanetStatisticsProcessor;
import ua.code.intership.proft.it.soft.service.statistic.StatisticsProcessor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
public class XmlFileCreator<T extends File> implements FileGenerator<T> {
    private final StatisticsProcessor statisticsProcessor = PlanetStatisticsProcessor.getInstance();

    @Override
    public File generate(String pathToXmlDirectory, T[] files, int countThreads, String attribute) {
        ExecutorService executor = Executors.newFixedThreadPool(countThreads);
        CountDownLatch latch = new CountDownLatch(files.length);

        for (T file : files) {
            executor.execute(() -> {
            try {
                statisticsProcessor.collectStatistics(file, attribute);
                log.info("File {} was read and analyzed.", file.getName());
            } catch (FileNotFoundException e) {
                log.error("File {} was not found: ", file.getName(), e);
            } catch (IOException e) {
                log.error("File {} have problem with read .json file: ", file.getName(), e);
            } finally {
                    latch.countDown();
            }

            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("Interrupted while waiting for tasks to complete.", e);
            Thread.currentThread()
                  .interrupt();
        }

        executor.shutdown();

        return generateXmlFile(pathToXmlDirectory, attribute);
    }

    private File generateXmlFile(String pathToXmlDirectory, String attribute) {
        try {
            Set<StatisticsDto> statisticsDtoSet
                    = statisticsProcessor.getStatisticsSortedSet((el1, el2) -> el2.getNumberOfRepetitions() - el1.getNumberOfRepetitions());

            DocumentBuilderFactory
                    docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Create XML document
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("statistics");
            doc.appendChild(rootElement);

            // Add statistics items to the XML document
            for (StatisticsDto statisticsDto : statisticsDtoSet) {
                Element item = doc.createElement("item");

                // Value
                Element value = doc.createElement("value");
                value.appendChild(doc.createTextNode(String.valueOf(statisticsDto.getAttribute())));
                item.appendChild(value);

                // Count
                Element count = doc.createElement("count");
                count.appendChild(doc.createTextNode(String.valueOf(statisticsDto.getNumberOfRepetitions())));
                item.appendChild(count);

                rootElement.appendChild(item);
            }

            // Write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            File outputFile = new File(pathToXmlDirectory + File.separator + "statistics_by_" + attribute + ".xml");
            StreamResult result = new StreamResult(outputFile);
            transformer.transform(source, result);

            return outputFile;
        } catch (Exception e) {
            throw new RuntimeException("Error generating XML file", e);
        }
    }
}
