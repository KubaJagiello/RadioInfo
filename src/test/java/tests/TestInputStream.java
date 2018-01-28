package tests;

import Model.InputStreamer;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class TestInputStream {

    private InputStreamer inputStreamer;

    @Before
    public void setUp(){
        inputStreamer = new InputStreamer();
    }

    @Test (expected = IOException.class)
    public void testSettingUpNonExistingStream() throws IOException {
        inputStreamer.setUpStreamForChannels("NonExisting");
    }

    @Test
    public void testSettingSverigesRadioStream() throws IOException {
        inputStreamer.setUpStreamForChannels("http://api.sr.se/api/v2/channels/");
    }

    @Test
    public void testSettingUpStreamWithFile() throws IOException {
        inputStreamer.setUpStreamForChannels( getClass().getResourceAsStream("/channels.xml"));
    }

    @Test (expected = IOException.class)
    public void testWithNonExistingFile() throws IOException {
        inputStreamer.setUpStreamForChannels(new FileInputStream("trolololo"));
    }

    @Test
    public void testGetInputForChannels(){

    }
}
