package com.mperfit.perfit.ui.imagechooser.imagechooser.album;

/**
 * Description:
 */
public abstract class AbsAlbumEntry {

    private int max=9;
    private int min=1;
    private static boolean isCrop;

    public void setMax(int max){
        this.max=max;
    }

    public void setMin(int min){
        this.min=min;
    }

    public void setCrop(boolean isCrop){
        this.isCrop=isCrop;
    }

    public int getMax(){
        return this.max;
    }

    public int getMin(){
        return this.min;
    }

    public static boolean isCrop(){
        return isCrop;
    }

}
