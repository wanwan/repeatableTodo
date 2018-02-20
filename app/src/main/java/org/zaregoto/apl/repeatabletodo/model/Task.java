package org.zaregoto.apl.repeatabletodo.model;

import android.content.Context;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.zaregoto.apl.repeatabletodo.db.TaskDB;
import org.zaregoto.apl.repeatabletodo.util.Utilities;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Task implements Serializable {

    private int id;
    private String name;
    private String detail;
    private int repeatCount;
    private REPEAT_UNIT repeatUnit;
    private boolean repeatFlag;
    private boolean enableTask;
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


    // TODO: 最終的に id を DB から採番して取得するようにするので本コンストラクタは削除予定
    public Task(String _name, String _detail, int _repeatCount, REPEAT_UNIT _repeatUnit, boolean _repeatFlag, boolean _enableTask, Date _lastDate) {
        this.name = _name;
        this.detail = _detail;
        this.repeatCount = _repeatCount;
        this.repeatUnit = _repeatUnit;
        this.repeatFlag = _repeatFlag;
        this.enableTask = _enableTask;
        this.lastDate = _lastDate;
    }


    public Task(int _id, String _name, String _detail, int _repeatCount, REPEAT_UNIT _repeatUnit, boolean _repeatFlag, boolean _enableTask, Date _lastDate) {
        this.id = _id;
        this.name = _name;
        this.detail = _detail;
        this.repeatCount = _repeatCount;
        this.repeatUnit = _repeatUnit;
        this.repeatFlag = _repeatFlag;
        this.enableTask = _enableTask;
        this.lastDate = _lastDate;
    }


    public int getId() {
        return id;
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

    public boolean isRepeatFlag() {
        return repeatFlag;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

    public void setRepeatUnit(REPEAT_UNIT repeatUnit) {
        this.repeatUnit = repeatUnit;
    }

    public void setRepeatFlag(boolean repeatFlag) {
        this.repeatFlag = repeatFlag;
    }

    public void setLastDate(Date date) {
        this.lastDate = date;
    }

    public boolean isEnableTask() {
        return enableTask;
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

    public void toXMLElement(Document doc, Element parent) {

        Element elm;
        Text txt;
        Element root;

        root = doc.createElement("task");

        elm = doc.createElement("id");
        txt = doc.createTextNode(String.valueOf(getId()));
        elm.appendChild(txt);
        root.appendChild(elm);

        elm = doc.createElement("name");
        txt = doc.createTextNode(getName());
        elm.appendChild(txt);
        root.appendChild(elm);

        elm = doc.createElement("detail");
        txt = doc.createTextNode(getDetail());
        elm.appendChild(txt);
        root.appendChild(elm);

        elm = doc.createElement("repeatCount");
        txt = doc.createTextNode(String.valueOf(getRepeatCount()));
        elm.appendChild(txt);
        root.appendChild(elm);

        elm = doc.createElement("repeatUnit");
        txt = doc.createTextNode(getRepeatUnit().getName());
        elm.appendChild(txt);
        root.appendChild(elm);

        elm = doc.createElement("repeatFlag");
        txt = doc.createTextNode(String.valueOf(isRepeatFlag()));
        elm.appendChild(txt);
        root.appendChild(elm);

        elm = doc.createElement("enableTask");
        txt = doc.createTextNode(String.valueOf(isEnableTask()));
        elm.appendChild(txt);
        root.appendChild(elm);

        elm = doc.createElement("lastDate");
        txt = doc.createTextNode(Utilities.dateToStr(getLastDate()));
        elm.appendChild(txt);
        root.appendChild(elm);

        parent.appendChild(root);
    }

    public static Task generateTask(Context context, String name, String detail, int repeatCount, REPEAT_UNIT repeatUnit, boolean repeatFlag, boolean enableTask, Date lastDate) {

        int id;
        Task task;

        id = TaskDB.insertNewTask(context, name, detail, repeatCount, repeatUnit, repeatFlag, lastDate, enableTask);
        task = new Task(id, name, detail, repeatCount, repeatUnit, repeatFlag, enableTask, lastDate);
        return task;
    }



}
