package tests;

import Model.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestParsingPrograms implements ParseInfo {
    private final static String ID = "id";
    private final static String NAME = "name";
    private final static String PROGRAM_IMAGE = "imageurl";
    private final static String PROGRAM_EPISODE = "episodeid";
    private final static String PROGRAM_TITLE = "title";
    private final static String PROGRAM_START = "starttimeutc";
    private final static String PROGRAM_END = "endtimeutc";
    private final static String PROGRAM_DESCRIPTION = "description";
    private Parser parser;
    private Program program;

    @Before
    public void setUp() throws FileNotFoundException, LoadingXMLException {
        parser = new Parser(new InputStreamer());
        parser.setParseReceiver(this);
        parser.parsePrograms(new Channel(), getClass().getResourceAsStream("/program.xml"));
    }

    @Test
    public void testGetProgramName(){
        assertEquals("Kappa", parser.getProgramName(program));
    }

    @Test
    public void testGetProgramTitle(){
        assertEquals("VeryNiceProgram", parser.getProgramTitle(program));
    }

    @Test
    public void testGetDescription(){
        assertEquals("SpamKappa", parser.getProgramDescription(program));
    }

    @Test
    public void testGetImage(){
        assertEquals(BufferedImage.class, parser.getProgramImage(program).getClass());
    }


    @Test
    public void testGetProgramStartTime(){
        String startTime = "2017-12-06T23:00:00Z";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime formatDateTime = LocalDateTime.parse(startTime, formatter);
        assertEquals(formatDateTime, parser.getProgramStartTime(program));
    }

    @Test
    public void testGetProgramEndTime(){
        String endTime = "2017-12-06T23:02:00Z";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        LocalDateTime formatDateTime = LocalDateTime.parse(endTime, formatter);
        assertEquals(formatDateTime, parser.getProgramEndTime(program));
    }

    @Test
    public void testProgramID(){
        assertEquals("123", program.getInfo(ID));
    }

    @Test
    public void testProgramName(){
        assertEquals("Kappa", program.getInfo(NAME));
    }

    @Test
    public void testProgramStartTime(){
        assertEquals("2017-12-06T23:00:00Z", program.getInfo(PROGRAM_START));
    }

    @Test
    public void testProgramEndTime(){
        assertEquals("2017-12-06T23:02:00Z", program.getInfo(PROGRAM_END));
    }

    @Test
    public void testProgramImage(){
        assertEquals("http://static-cdn.sr.se/sida/images/4540/3634468_2048_1152.jpg?preset=api-default-square"
                , program.getInfo(PROGRAM_IMAGE));
    }

    @Test
    public void testProgramDescription(){
        assertEquals("SpamKappa", program.getInfo(PROGRAM_DESCRIPTION));
    }

    @Test
    public void testProgramGetTitle(){
        assertEquals("VeryNiceProgram", program.getInfo(PROGRAM_TITLE));
    }

    @Test
    public void testProgramEpisode(){
        assertEquals("420", program.getInfo(PROGRAM_EPISODE));
    }




    @Override
    public void newChannel(Channel channel) {

    }

    @Override
    public void newProgram(Channel channel, Program program) {
        this.program = program;
    }
}
