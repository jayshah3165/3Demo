package com.expandablerecyclerviewwebservice;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by EchoIT on 7/26/2018.
 */

public class MobileOS extends ExpandableGroup<Phone> {

public MobileOS(String title,List<Phone> items){
        super(title,items);
        }
}
