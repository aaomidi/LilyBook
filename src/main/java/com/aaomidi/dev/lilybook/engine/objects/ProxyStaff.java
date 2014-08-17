package com.aaomidi.dev.lilybook.engine.objects;


import lombok.Getter;

import java.util.HashSet;
import java.util.Iterator;

public class ProxyStaff implements Iterable<String> {
    @Getter
    private final HashSet<String> staff;
    @Getter
    private final String serverName;

    public ProxyStaff(String serverName) {
        staff = new HashSet<>();
        this.serverName = serverName;
    }

    public boolean addStaff(String staffName) {
        return staff.add(staffName);
    }

    public boolean removeStaff(String staffName) {
        return staff.remove(staffName);
    }

    public void resetStaff() {
        staff.clear();
    }

    public int getStaffCount() {
        return staff.size();
    }

    @Override
    public Iterator<String> iterator() {
        return staff.iterator();
    }
}
