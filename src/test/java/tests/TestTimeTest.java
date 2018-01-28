package tests;
import Model.InputStreamer;
import Model.Parser;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class TestTimeTest {

    @Test
    public void testRightTimeNow(){
        Parser parser = new Parser(new InputStreamer());
        LocalDateTime time = LocalDateTime.now();
        assertEquals(true, parser.isRightTime(time));
    }

    @Test
    public void testRightTimePlus(){
        Parser parser = new Parser(new InputStreamer());
        LocalDateTime time = LocalDateTime.now().plusHours(11);
        assertEquals(true,parser.isRightTime(time));
    }

    @Test
    public void testRightTimeMinus(){
        Parser parser = new Parser(new InputStreamer());
        LocalDateTime time = LocalDateTime.now().minusHours(11);
        assertEquals(true,parser.isRightTime(time));
    }

    @Test
    public void testWrongTimeMinus(){
        Parser parser = new Parser(new InputStreamer());
        LocalDateTime time = LocalDateTime.now().minusHours(13);
        assertEquals(false,parser.isRightTime(time));
    }

    @Test
    public void testWrongTimePlus(){
        Parser parser = new Parser(new InputStreamer());
        LocalDateTime time = LocalDateTime.now().plusHours(13);
        assertEquals(false, parser.isRightTime(time));
    }

    @Test
    public void testHasAlreadyPassed(){
        Parser parser = new Parser(new InputStreamer());
        LocalDateTime time = LocalDateTime.now();
        assertEquals(true, parser.hasProgramAlreadyPassed(time.minusHours(1)));
    }

    @Test
    public void testHasNotPassedYet(){
        Parser parser = new Parser(new InputStreamer());
        LocalDateTime time = LocalDateTime.now();
        assertEquals(false, parser.hasProgramAlreadyPassed(time.plusHours(1)));
    }

}