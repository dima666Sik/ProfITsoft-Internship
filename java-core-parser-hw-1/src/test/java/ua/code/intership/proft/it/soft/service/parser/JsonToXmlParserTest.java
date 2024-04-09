package ua.code.intership.proft.it.soft.service.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.code.intership.proft.it.soft.model.attribute.CoreAttribute;

import static ua.code.intership.proft.it.soft.service.util.constant.FileConstant.PATH_TO_JSON_FILE_LIST;
import static ua.code.intership.proft.it.soft.service.util.constant.FileConstant.PATH_TO_XML_FILE_LIST;


class JsonToXmlParserTest {
    private FileParser fileParser;

    @BeforeEach
    void setUp() {
        fileParser = new JsonToXmlParser();
    }

    @Test
    void testParse() {
        Assertions.assertEquals(null, fileParser.parse(PATH_TO_JSON_FILE_LIST, PATH_TO_XML_FILE_LIST, CoreAttribute.ATMOSPHERIC_COMPOSITION));
    }
}