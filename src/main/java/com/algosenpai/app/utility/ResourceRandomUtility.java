package com.algosenpai.app.utility;

import com.algosenpai.app.constant.ImagesEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ResourceRandomUtility {

    /**
     * Returns a string for the images map.
     * @param map container of the images.
     * @return file name of the image.
     */
    public static String randomResources(Map<ImagesEnum, String> map) {
        List<ImagesEnum> keysAsArray = new ArrayList<ImagesEnum>(map.keySet());
        Random r = new Random();
        return map.get(keysAsArray.get(r.nextInt(keysAsArray.size())));
    }
}
