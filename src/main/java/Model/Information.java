package Model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that acts as a bridge between Parser and controller.
 * You gain access to parsed information by implementing these
 * two interfaces.
 */
public class Information implements ParseInfo {

    private HashMap<Channel, ArrayList<Program>> programsByChannel;
    private ArrayList<Channel> channels;
    private HashMap<String, Channel> channelsByName;
    private HashMap<String, Program> programsByName;
    private Parser parser;

    /**
     * Creates new InputStream and sends that to new Parser.
     * @throws LoadingXMLException exception
     * @throws IOException exception
     */
    public Information() throws LoadingXMLException, IOException {
        InputStreamer input = new InputStreamer();
        input.setOptionForChannels("?pagination=false");
        input.setOptionForPrograms("&pagination=false");
        try {
            input.setUpStreamForChannels("http://api.sr.se/api/v2/channels/");
            input.addURLforPrograms("http://api.sr.se/api/v2/scheduledepisodes?channelid=");
        } catch (IOException e) {
            throw new LoadingXMLException("Could not connect to Sveriges Radio API");
        }
        programsByChannel = new HashMap<>();
        channels = new ArrayList<>();
        channelsByName = new HashMap<>();
        programsByName = new HashMap<>();
        parser = new Parser(input);
        parser.setParseReceiver(this);
        parser.update();
    }

    /**
     * Loads loadInfo interface with parsed image and name for channel.
     * @param load interface
     */
    public void getChannels(LoadInfo load){
        for(Channel channel : channels) {
            Object[] objects = {parser.getChannelImage(channel),
                                parser.getChannelName(channel)};
            load.loadInfo(objects);
        }
    }

    /**
     * Loads loadPrograms interface with parsed name, start and end time for
     * program.
     * @param load interface
     * @param channelName name of Channel to load programs from.
     */
    public void getProgramForChannel(LoadInfo load, String channelName){
        for(Program program : programsByChannel.get(
                                    channelsByName.get(channelName))){
            if(parser.isRightTime(parser.getProgramStartTime(program)) ||
                    parser.isRightTime(parser.getProgramEndTime(program))){
                Object[] objects = {parser.getProgramName(program),
                                    parser.getProgramStartTime(program),
                                    parser.getProgramEndTime(program)};
                load.loadInfo(objects);
            }
        }
    }

    /**
     * Loads loadInfo interface with parsed title and description for program.
     * @param load interface
     * @param programName name of program to load description from.
     */
    public void getProgramDescription(LoadInfo load, String  programName){
        Object[] objects = {parser.getProgramTitle(programsByName.get(programName))};
        load.loadInfo(objects);
        objects = new Object[]{parser.getProgramDescription(programsByName.get(programName))};
        load.loadInfo(objects);
    }

    /**
     * Loads loadInfo interface with parsed image for program.
     * @param load interface
     * @param programName name of program to load image from.
     */
    public void getProgramImage(LoadInfo load, String programName){
        Object[] objects = {parser.getProgramImage(
                                programsByName.get(programName))};
        load.loadInfo(objects);
    }

    /**
     * Overrides interface in Parser to save parsed instance of Channel
     * @param channel instance of class Channel
     */
    @Override
    public void newChannel(Channel channel) {
        channels.add(channel);
        channelsByName.put(parser.getChannelName(channel), channel);
        programsByChannel.put(channel, new ArrayList<>());
    }

    /**
     * Overrides interface in Parser to save parsed instance of Program
     * @param channel instance of class Program
     */
    @Override
    public void newProgram(Channel channel, Program program) {
        programsByChannel.get(channel).add(program);
        programsByName.put(parser.getProgramName(program), program);
    }
}
