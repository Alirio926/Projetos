/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.squallsoft.jtag.adapter;

/**
 *
 * @author aliri
 */
public class LogTree {
    private String name;
    private String flagIcon;

    LogTree(String name, String flagIcon) {
        this.name = name;
        this.flagIcon = flagIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlagIcon() {
        return flagIcon;
    }

    public void setFlagIcon(String flagIcon) {
        this.flagIcon = flagIcon;
    }
}

