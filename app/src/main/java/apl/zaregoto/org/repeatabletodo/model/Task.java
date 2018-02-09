package apl.zaregoto.org.repeatabletodo.model;

import java.util.Calendar;
import java.util.Date;

public class Task {

    private String name;
    private String detail;

    private int repeatCount;
    private REPEAT_UNIT repeatUnit;

    private boolean repeat;

    private Date lastDate;


    public enum REPEAT_UNIT {
        DAILY("DAILY"),
        WEEKLY("WEEKLY"),
        MONTHLY("MONTHLY");

        private final String name;

        REPEAT_UNIT(String _name) {
            name = _name;
        }

        public String getName() {
            return name;
        }

        public static REPEAT_UNIT getUnitFromString(String str) {

            for (REPEAT_UNIT ru : REPEAT_UNIT.values()) {
                if (0 == ru.name.compareTo(str)) {
                    return ru;
                }
            }

            return null;
        }
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

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date date) {
        this.lastDate = date;
    }


    boolean lastDatePlusRepeatCountIsOver(Date now) {

        boolean ret = false;

        Calendar _lastDate;
        Date _limitDate = null;

        try {
            _lastDate = Calendar.getInstance();
            _lastDate.setTime(lastDate);

            switch (repeatUnit) {
                case DAILY:
                    _lastDate.add(Calendar.DATE, repeatCount);
                    _limitDate = _lastDate.getTime();
                    break;
                case WEEKLY:
                    _lastDate.add(Calendar.DATE, 7*repeatCount);
                    _limitDate = _lastDate.getTime();
                    break;
                case MONTHLY:
                    _lastDate.add(Calendar.MONTH, repeatCount);
                    _limitDate = _lastDate.getTime();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null != _limitDate && _limitDate.before(now)) {
            ret = true;
        }


        return ret;
    }

}
