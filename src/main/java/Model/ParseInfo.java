package Model;

/**
 * Interface that Parser calls whenever new Channel
 * och Program is found.
 */
public interface ParseInfo{
    /**
     * @param channel new Instance of Channel.
     */
    void newChannel(Channel channel);

    /**
     * @param channel instance of Channel
     * @param program new Instance of Program
     */
    void newProgram(Channel channel, Program program);
}
