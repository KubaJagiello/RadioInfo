package Model;

import java.util.HashMap;


/**
 * Stores information of parsed Channel in form of info.
 */
public class Channel {

    private HashMap<String, String> info;

    public Channel(){
        info = new HashMap<>();
    }

    /**
     * Adds info to info
     * @param key name of object.
     * @param info object to be stored.
     */
    public void addInfo(String key, String info){
        this.info.put(key, info);
    }

    /**
     * Returns info from info.
     * @param key name of object.
     * @return object that was stored.
     */
    public String getInfo(String key){
        return info.get(key);
    }
}
