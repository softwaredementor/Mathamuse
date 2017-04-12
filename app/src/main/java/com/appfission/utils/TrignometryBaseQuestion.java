package com.appfission.utils;

import com.appfission.mathamuse.GenerateQuestion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by srikanthmannepalle on 3/1/17.
 */

public class TrignometryBaseQuestion extends GenerateQuestion  {

    protected Map<Integer, String> sinMap;
    protected Map<Integer, String> cosMap;
    protected Map<Integer, String> tanMap;
    protected Map<Integer, String> cosecMap;
    protected Map<Integer, String> secMap;
    protected Map<Integer, String> cotMap;

    protected int[] anglesSet = new int[] {0, 30, 45, 60, 90, 120, 135, 150, 180, 210, 225, 240, 270, 300, 315, 330, 360};

    public TrignometryBaseQuestion() {

        sinMap = new HashMap<>();
        cosMap = new HashMap<>();
        tanMap = new HashMap<>();
        cosecMap = new HashMap<>();
        secMap = new HashMap<>();
        cotMap = new HashMap<>();

        initializeSinValues();
        initializeCosineValues();
        initializeCosValues();
        initializeSecantValues();
        initializeTanValues();
        initializeCotangentValues();
    }

    private void initializeSinValues() {
        sinMap.put(-30, "-1/2");
        sinMap.put(0, "0");
        sinMap.put(30, "1/2");
        sinMap.put(45, "1/SQRT(2)");
        sinMap.put(60, "SQRT(3)/2");
        sinMap.put(90, "1");
        sinMap.put(120, "SQRT(3)/(-2)");
        sinMap.put(135, "-1/SQRT(2)");
        sinMap.put(150, "-1/2");
        sinMap.put(180, "0");
        sinMap.put(210, "-1/2");
        sinMap.put(225, "-1/SQRT(2)");
        sinMap.put(240, "SQRT(3)/(-2)");
        sinMap.put(270, "-1");
        sinMap.put(300, "SQRT(3)/(-2)");
        sinMap.put(315, "1/SQRT(2)");
        sinMap.put(330, "-1/2");
        sinMap.put(360, "0");
    }

    private void initializeCosineValues() {
        cosecMap.put(-30, "-2");
//        cosecMap.put(0, "0"); undefined
        cosecMap.put(30, "2");
        cosecMap.put(45, "SQRT(2)");
        cosecMap.put(60, "2/SQRT(3)");
        cosecMap.put(90, "1");
        cosecMap.put(120, "-2/SQRT(3)");
        cosecMap.put(135, "-1*SQRT(2)");
        cosecMap.put(150, "-2");
//        cosecMap.put(180, "0"); undefined
        cosecMap.put(210, "-2");
        cosecMap.put(225, "-1*SQRT(2)");
        cosecMap.put(240, "-2/SQRT(3)");
        cosecMap.put(270, "-1");
        cosecMap.put(300, "-2/SQRT(3)");
        cosecMap.put(315, "SQRT(2)");
        cosecMap.put(330, "-2");
//        cosecMap.put(360, "0"); undefined
    }

    private void initializeCosValues() {
        cosMap.put(0, "1");
        cosMap.put(30, "SQRT(3)/2");
        cosMap.put(45, "1/SQRT(2)");
        cosMap.put(60, "1/2");
        cosMap.put(90, "0");
        cosMap.put(120, "-1/2");
        cosMap.put(135, "-1/SQRT(2)");
        cosMap.put(150, "SQRT(3)/(-2)");
        cosMap.put(180, "-1");
        cosMap.put(210, "SQRT(3)/(-2)");
        cosMap.put(225, "-1/SQRT(2)");
        cosMap.put(240, "-1/2");
        cosMap.put(270, "0");
        cosMap.put(300, "1/2");
        cosMap.put(315, "1/SQRT(2)");
        cosMap.put(330, "SQRT(3)/2");
        cosMap.put(360, "1");
    }

    private void initializeSecantValues() {
        secMap.put(0, "1");
        secMap.put(30, "2/SQRT(3)");
        secMap.put(45, "SQRT(2)");
        secMap.put(60, "2");
//        secMap.put(90, "0"); undefined
        secMap.put(120, "-2");
        secMap.put(135, "-1*SQRT(2)");
        secMap.put(150, "-2/SQRT(3)");
        secMap.put(180, "-1");
        secMap.put(210, "-2/SQRT(3)");
        secMap.put(225, "-1*SQRT(2)");
        secMap.put(240, "-2");
//        secMap.put(270, "0"); undefined
        secMap.put(300, "2");
        secMap.put(315, "SQRT(2)");
        secMap.put(330, "2/SQRT(3)");
        secMap.put(360, "1");
    }

    private void initializeTanValues() {
        tanMap.put(0, "0");
        tanMap.put(30, "1/SQRT(3)");
        tanMap.put(45, "1");
        tanMap.put(60, "SQRT(3)");
//        tanMap.put(90, "0"); undefined
        tanMap.put(120, "-1*SQRT(3)");
        tanMap.put(135, "-1");
        tanMap.put(150, "-1/SQRT(3)");
        tanMap.put(180, "0");
        tanMap.put(210, "1/SQRT(3)");
        tanMap.put(225, "1");
        tanMap.put(240, "SQRT(3)");
//        tanMap.put(270, "0"); undefined
        tanMap.put(300, "-1*SQRT(3)");
        tanMap.put(315, "-1");
        tanMap.put(330, "-1/SQRT(3)");
        tanMap.put(360, "0");
    }

    private void initializeCotangentValues() {
        cotMap.put(-30, "-1*SQRT(3)");
//        cotMap.put(0, "0"); undefined
        cotMap.put(30, "SQRT(3)");
        cotMap.put(45, "1");
        cotMap.put(60, "1/SQRT(3)");
        cotMap.put(90, "0");
        cotMap.put(120, "-1/SQRT(3)");
        cotMap.put(135, "-1");
        cotMap.put(150, "-1*SQRT(3)");
//        cotMap.put(180, "0"); undefined
        cotMap.put(210, "SQRT(3)");
        cotMap.put(225, "1");
        cotMap.put(240, "1/SQRT(3)");
        cotMap.put(270, "0");
        cotMap.put(300, "-1/SQRT(3)");
        cotMap.put(315, "-1");
        cotMap.put(330, "-1*SQRT(3)");
//        cotMap.put(360, "0"); undefined
    }
}
