/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anchorman.lunarcalendar.entities;

import com.anchorman.lunarcalendar.properties.Resource;

/**
 *
 * @author NamNT2
 */
public class Day {

    private String solarDay = Resource.EMPTY;
    private String lunarDay = Resource.EMPTY;
    private boolean hasEvent = false;
    private boolean hasEventSpecial = false;

    public boolean isHasEvent() {
        return hasEvent;
    }

    public void setHasEvent(boolean hasEvent) {
        this.hasEvent = hasEvent;
    }

    public String getLunarDay() {
        return lunarDay;
    }

    public void setLunarDay(String lunarDay) {
        this.lunarDay = lunarDay;
    }

    public String getSolarDay() {
        return solarDay;
    }

    public void setSolarDay(String solarDay) {
        this.solarDay = solarDay;
    }

    public boolean isHasEventSpecial() {
        return hasEventSpecial;
    }

    public void setHasEventSpecial(boolean hasEventSpecial) {
        this.hasEventSpecial = hasEventSpecial;
    }

    public Day() {
    }

    public Day(String solarDay) {
        this.solarDay = solarDay;
    }

    public Day(String solarDay, String lunarDay) {
        this.solarDay = solarDay;
        this.lunarDay = lunarDay;
    }
}
