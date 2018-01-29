package Model;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * Parses XML from inputStream and saves parsed information
 * in Program and Channel. You need to implement ParseInfo
 * to be able to get every channel and program that is parsed.
 */
public class Parser {

    /**
     * Interface that can be implemented to gain access to parsed
     * Program and Channel.
     */
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String CHANNEL_IMAGE = "image";
    private final static String PROGRAM_START = "starttimeutc";
    private final static String PROGRAM_END = "endtimeutc";
    private final static String PROGRAM_DESCRIPTION = "description";
    private final static String PROGRAM = "program";
    private final static String CHANNEL = "channel";
    private final static String PROGRAM_IMAGE = "imageurl";
    private final static String PROGRAM_EPISODE = "episodeid";
    private final static String PROGRAM_TITLE = "title";
    private final static String EPISODE_SCHEDULE = "scheduledepisode";
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.
            ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private ArrayList<Channel> channels = new ArrayList<>();
    private ParseInfo parseInfo;
    private InputStreamer inputStream;

    /**
     * Saves InputStreamer for later use.
     * @param inputStream
     */
    public Parser(InputStreamer inputStream){
        this.inputStream = inputStream;
    }

    /**
     * Parses channels and programs.
     */
    void update() throws LoadingXMLException, IOException {
        parseChannels(inputStream.getInputForChannels());
        updatePrograms();
    }

    /**
     * Saves interface that will receive information.
     * @param receiver
     */
    public void setParseReceiver(ParseInfo receiver){
        parseInfo = receiver;
    }

    /**
     * Loops through all channel ID's and parses programs.
     * It then sets up inputStream for programs that will
     * be played in 24 hours and programs that have been
     * played 24 hours ago.
     */
    private void updatePrograms() throws LoadingXMLException, IOException {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formatedDateTime;
        for(Channel channel : channels){

            formatedDateTime = localDateTime.format(formatter);
            inputStream.setUpStreamForPrograms(channel.getInfo(ID), formatedDateTime);
            parsePrograms(channel, inputStream.getInputForProgram());
            formatedDateTime = localDateTime.plusHours(24).format(formatter);
            inputStream.setUpStreamForPrograms(channel.getInfo(ID), formatedDateTime);
            parsePrograms(channel, inputStream.getInputForProgram());
            formatedDateTime = localDateTime.minusHours(48).format(formatter);
            inputStream.setUpStreamForPrograms(channel.getInfo(ID), formatedDateTime);
            parsePrograms(channel, inputStream.getInputForProgram());
        }
    }

    /**
     * Parses programs for given Channel
     * @param channel Channel to get programs for.
     * @param io XML to be read as InputStream.
     */
    public void parsePrograms(Channel channel, InputStream io) throws LoadingXMLException {
        Document doc = document(io);
        NodeList list = doc.getElementsByTagName(EPISODE_SCHEDULE);

        for(int i=0; i< list.getLength(); i++){
            Node node = list.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                Program program = new Program();
                try{
                    program.addInfo(ID, getAttribute(element, PROGRAM, ID));
                    program.addInfo(NAME, getAttribute(element, PROGRAM, NAME));
                    program.addInfo(PROGRAM_TITLE, getTag(element, PROGRAM_TITLE));
                    program.addInfo(PROGRAM_EPISODE, getTag(element, PROGRAM_EPISODE));
                    program.addInfo(PROGRAM_START, getTag(element, PROGRAM_START));
                    program.addInfo(PROGRAM_END, getTag(element, PROGRAM_END));
                    parseInfo.newProgram(channel, program);
                    program.addInfo(PROGRAM_IMAGE, getTag(element, PROGRAM_IMAGE));
                    program.addInfo(PROGRAM_DESCRIPTION, getTag(element, PROGRAM_DESCRIPTION));
                /* Exception ignored because not every program contains
                 * all of them tags above, but that does not matter.
                 */
                } catch (Exception ignored){}
            }
        }
    }

    /**
     * @param e Element node.
     * @param tagName Tag name to get attribute for.
     * @param attributeName attribute to be red from.
     * @return String that was obtained from attribute.
     */
    private String getAttribute(Element e, String tagName, String attributeName){
        String tmp = ((Element)e.getElementsByTagName(tagName).
                            item(0)).getAttribute(attributeName);
        return (tmp == null) ? "" : tmp;
    }

    /**
     * @param e Element node.
     * @param tagName tag name to get textContent from.
     * @return textContent from tag.
     */
    private String getTag(Element e, String tagName){
        String tmp = e.getElementsByTagName(tagName).item(0).getTextContent();
        return (tmp == null) ? "" : tmp;
    }

    /**
     * Parses Channels from given XML as inputStream.
     * @param io XML as inputStream.
     */
    public void parseChannels(InputStream io) throws LoadingXMLException {
        Document doc = document(io);
        NodeList list = doc.getElementsByTagName(CHANNEL);

        for(int i=0; i< list.getLength(); i++){
            Node node = list.item(i);
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) node;
                Channel channel = new Channel();
                channel.addInfo(ID, element.getAttribute(ID));
                channel.addInfo(NAME, element.getAttribute(NAME));
                channel.addInfo(CHANNEL_IMAGE, element.
                        getElementsByTagName(CHANNEL_IMAGE).item(0).getTextContent());
                parseInfo.newChannel(channel);
                channels.add(channel);
            }
        }
    }

    /**
     * Reads inputStream and creates document for it.
     * @param io inputStream
     * @return Document
     */
    private Document document(InputStream io) throws LoadingXMLException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document doc;

        try {
            db = dbf.newDocumentBuilder();
            doc = db.parse(io);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new LoadingXMLException("Could not parse correctly");
        }
        return doc;
    }

    /**
     * @param channel instance of channel
     * @return name given of Channel
     */
    public String getChannelName(Channel channel){ return channel.getInfo(NAME); }

    /**
     * @param channel instance of channel
     * @return Image for given channel
     */
    public Image getChannelImage(Channel channel){
        Image icon = null;
        try {
            URL url = new URL(channel.getInfo(CHANNEL_IMAGE));
            icon = ImageIO.read(url);
            //Is ignored because images are not necessary.
        } catch (IOException ignored) {}
        return icon;
    }

    /**
     * @param program instance pf Program
     * @return name of program
     */
    public String getProgramName(Program program){ return program.getInfo(NAME); }

    /**
     * @param program instance
     * @return start time for program as LocalDateTime
     */
    public LocalDateTime getProgramStartTime(Program program){
        return LocalDateTime.parse(program.getInfo(PROGRAM_START), FORMATTER);
    }

    /**
     * @param program instance of program
     * @return end time for program as LocalDateTime
     */
    public LocalDateTime getProgramEndTime(Program program){
        return LocalDateTime.parse(program.getInfo(PROGRAM_END), FORMATTER);
    }

    /**
     * @param program instance of Program
     * @return Title for given program as String
     */
    public String getProgramTitle(Program program){
        return program.getInfo(PROGRAM_TITLE);
    }

    /**
     * @param program instance of Program
     * @return Description for program as String
     */
    public String getProgramDescription(Program program){
        String description = program.getInfo(PROGRAM_DESCRIPTION);
        return (description == null) ? "" : description;
    }

    /**
     * @param program instance of Program
     * @return Image for given program
     */
    public Image getProgramImage(Program program){
        Image icon = null;
        try {
            URL url = new URL(program.getInfo(PROGRAM_IMAGE));
            icon = ImageIO.read(url);
            //Is ignored because Images are not necessary.
        } catch (IOException ignored) {}

        return icon;
    }

    /**
     * @param time LocalDateTime
     * @return false if given time is more than either 12 hours in the future or past.
     */
    public boolean isRightTime(LocalDateTime time){
        LocalDateTime now = LocalDateTime.now();
        String formatDateTime = now.format(FORMATTER);
        now = LocalDateTime.parse(formatDateTime, FORMATTER);
        return time.isAfter(now.minusHours(12)) && time.isBefore(now.plusHours(12));
    }

    /**
     * @param timeForProgram LocalDateTime for program
     * @return true if program has already been aired.
     */
    public boolean hasProgramAlreadyPassed(LocalDateTime timeForProgram){
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(timeForProgram);
    }
}
