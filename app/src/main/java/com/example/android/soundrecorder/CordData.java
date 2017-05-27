package com.example.android.soundrecorder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengzhi on 2017/5/27.
 */

public class CordData {

    private List<Cording> names = new ArrayList<>();

    public List<Cording> getNames() {

        for (int i = 0; i < 20; i++) {

            Cording cording = new Cording("liubin##" + i);

            names.add(cording);
        }
        return names;
    }
}
