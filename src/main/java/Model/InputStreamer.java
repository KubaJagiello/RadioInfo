package Model;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Acts as adapter between Information and Parser by creating inputStream
 * from given URL.
 */
public class InputStreamer {

    private String optionForChannels = "";
    private String optionForPrograms = "";
    private String programsURL = "";
    private InputStream programs;
    private InputStream channels;

    public InputStreamer(){

    }

    /**
     * Sets up URL that will be used to parse programs.
     * @param id
     */
    public void setUpStreamForPrograms(String id, String date) throws IOException {
        URL url = new URL(programsURL + id + optionForPrograms + "&date=" + date);
        setUpStreamForPrograms(url.openStream());
    }

    /**
     * Saves given inputStream.
     * @param io inputStream
     */
    public void setUpStreamForPrograms(InputStream io){
        programs = io;
    }

    /**
     * Sets up URL that will be used to parse channels.
     * @param s
     */
    public void setUpStreamForChannels(String s) throws IOException {
        try {
            URL url = new URL(s + optionForChannels);
            setUpStreamForChannels(url.openStream());
        } catch (IOException e) {
            throw new IOException();
        }
    }

    /**
     * Saves given inputStream.
     * @param io inputStream
     */
    public void setUpStreamForChannels(InputStream io){
        channels = io;
    }

    /**
     * Let you add option for URL that will be appended at the end
     * of URL for channels
     * @param s option in form of String
     */
    public void setOptionForChannels(String s){
        optionForChannels = s;
    }

    /**
     * Let you add option for URL that will be appended at the end
     * of URL for programs
     * @param s option in form of String
     */
    public void setOptionForPrograms(String s){
        optionForPrograms = s;
    }

    /**
     * Saves URL for programs in form of String.
     * @param s
     */
    public void addURLforPrograms(String s){
        programsURL = s;
    }

    /**
     * @return inputStream for program.
     */
    public InputStream getInputForProgram(){
        return programs;
    }

    /**
     * @return inputStream for channels.
     */
    public InputStream getInputForChannels(){
        return channels;
    }
}
