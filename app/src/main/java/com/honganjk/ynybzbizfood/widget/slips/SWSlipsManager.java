package com.honganjk.ynybzbizfood.widget.slips;

public class SWSlipsManager {

    private SWSlipsLayout swSlipeLayout;
    private static SWSlipsManager SWSlipeManager = new SWSlipsManager();

    public static SWSlipsManager getInstance() {
        return SWSlipeManager;
    }

    public void setSwSlipeLayout(SWSlipsLayout swSlipeLayout) {
        this.swSlipeLayout = swSlipeLayout;
    }

    public void clear() {
        swSlipeLayout = null;
    }

    public void close() {
        if (swSlipeLayout != null) {
            swSlipeLayout.close();
        }
    }

    /**
     * if s==null means no item is open
     *
     * @return ture means open else close
     */
    public boolean haveOpened() {
        return swSlipeLayout != null;
    }

    /**
     * if s==null means no item is open
     *
     * @return true means two item is not the same one and one item is open
     */
    public boolean haveOpened(SWSlipsLayout s) {
        return swSlipeLayout != null && swSlipeLayout == s;
    }
}