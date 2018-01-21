package apl.zaregoto.org.repeatabletodo.model;

public class Task {

    private String name;
    private String detail;

    private int repeatCount;
    private REPEAT_UNIT repeatUnit;

    private boolean repeat;

    public enum REPEAT_UNIT {
        DAILY,
        WEEKLY,
        MONTHLY,
    }

    private Task() {
        repeat = true;
    }

    public Task(String _name, String _detail, int _count, REPEAT_UNIT _unit) {
        this.name = _name;
        this.detail = _detail;
        this.repeatCount = _count;
        this.repeatUnit = _unit;
    }

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public REPEAT_UNIT getRepeatUnit() {
        return repeatUnit;
    }

    public boolean isRepeat() {
        return repeat;
    }
}
