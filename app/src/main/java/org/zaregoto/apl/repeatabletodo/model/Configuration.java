package org.zaregoto.apl.repeatabletodo.model;

public class Configuration {

    private int updateTimeHour;
    private int updateTimeMin;
    private boolean todoCronFlag;

    private Configuration() {
    }

    public Configuration (int updateTimeHour, int updateTimeMin, boolean todoCronFlag) {
        this.updateTimeHour = updateTimeHour;
        this.updateTimeMin = updateTimeMin;
        this.todoCronFlag = todoCronFlag;
    }

    public int getUpdateTimeHour() {
        return updateTimeHour;
    }

    public void setUpdateTimeHour(int updateTimeHour) {
        this.updateTimeHour = updateTimeHour;
    }

    public int getUpdateTimeMin() {
        return updateTimeMin;
    }

    public void setUpdateTimeMin(int updateTimeMin) {
        this.updateTimeMin = updateTimeMin;
    }

    public boolean isTodoCronFlag() {
        return todoCronFlag;
    }

    public void setTodoCronFlag(boolean todoCronFlag) {
        this.todoCronFlag = todoCronFlag;
    }
}
