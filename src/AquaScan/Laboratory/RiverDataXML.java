package AquaScan.Laboratory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class RiverDataXML {
    private static final String XML_FILE_PATH = "river_data.xml";

    public static void saveData(RiverList data) {
        XStream xstream = new XStream(new DomDriver());
        String xml = xstream.toXML(data);
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(XML_FILE_PATH), StandardCharsets.UTF_8))) {
            writer.write(xml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static RiverList loadData() {
        XStream xstream = new XStream(new DomDriver());
        File xmlFile = new File(XML_FILE_PATH);
        if (xmlFile.exists()) {
            try (Reader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(XML_FILE_PATH), StandardCharsets.UTF_8))) {
                return (RiverList) xstream.fromXML(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new RiverList();
    }
}
