package com.arty.domino.game;

public class Domino {
    private int line;
    private int num;
    private int idView;
    private int value;
    private int skin;
    private boolean isRecumbent;
    private boolean isOpen;
    private boolean isRemoved;
    private boolean wasClosed;

    public Domino() {
        this.value = -1;
        this.skin = -1;
        this.isRecumbent = false;
        this.isOpen = false;
        this.isRemoved = false;
        this.wasClosed = true;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getIdView() {
        return idView;
    }

    public void setIdView(int idView) {
        this.idView = idView;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getSkin() {
        return skin;
    }

    public void setSkin(int skin) {
        this.skin = skin;
    }

    public boolean isRecumbent() {
        return isRecumbent;
    }

    public void setRecumbent(boolean recumbent) {
        isRecumbent = recumbent;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public boolean wasClosed() {
        return wasClosed;
    }

    public void setWasClosed(boolean wasOpened) {
        this.wasClosed = wasOpened;
    }
}
