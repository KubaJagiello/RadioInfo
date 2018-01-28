package tests;

import Model.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TestParsingChannels implements ParseInfo {
    private Parser parser;
    private Channel channel;
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String CHANNEl_IMAGE = "image";

    @Before
    public void setUp() throws FileNotFoundException, LoadingXMLException {
        parser = new Parser(new InputStreamer());
        parser.setParseReceiver(this);
        parser.parseChannels(getClass().getResourceAsStream("/channels.xml"));
    }

    @Test
    public void testChannelID(){
        assertEquals("420", channel.getInfo(ID));
    }

    @Test
    public void testChannelName(){
        assertEquals("EXDE", channel.getInfo(NAME));
    }

    @Test
    public void testChannelImage(){
        assertEquals("http://static-cdn.sr.se/sida/images/132/2186745_512_512.jpg" , channel.getInfo(CHANNEl_IMAGE));
    }

    @Test
    public void testNonExistingImage(){
        assertEquals(null, channel.getInfo("TROLOLO"));
    }

    @Test
    public void testNonExistingChannelName(){
        assertEquals(null, channel.getInfo("I DONT EXIST"));
    }

    @Test
    public void testNonExistingID(){
        assertEquals(null, channel.getInfo("I DONT EXIST EITHER"));
    }

    @Test
    public void testGetChannelName(){
        assertEquals("EXDE", parser.getChannelName(channel));
    }

    @Test
    public void testGetChannelImage(){
        assertEquals(BufferedImage.class, parser.getChannelImage(channel).getClass());
    }

    @Override
    public void newChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void newProgram(Channel channel, Program program) {

    }
}
