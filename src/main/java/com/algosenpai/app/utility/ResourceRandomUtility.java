package com.algosenpai.app.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ResourceRandomUtility {

    /**
     * Returns a string from generic map for views, images, and sound.
     * @param map container of the for views, images, and sound.
     * @return file name of the for views, images, and sound.
     */
    public static <T> String randomResources(Map<T, String> map) {
        List<T> keysAsArray = new ArrayList<T>(map.keySet());
        Random r = new Random();
        return map.get(keysAsArray.get(r.nextInt(keysAsArray.size())));
    }

}
