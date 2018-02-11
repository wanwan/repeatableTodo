package org.zaregoto.apl.repeatabletodo.model;

import android.content.Context;
import android.util.Xml;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import static org.zaregoto.apl.repeatabletodo.model.TaskList.TASK_PARSER_STATUS.*;

public class TaskList {

    private String id = "";
    private ArrayList<Task> tasks;

    public static final String DEFAULT_TASKLIST_FILENAME = "tasklist.xml";

    private TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(String _id) {
        this();
        this.id = _id;
    }

    public void addTask(Task _task) {
        tasks.add(_task);
    }

    public String getId() {
        return id;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }


    public ArrayList<Todo> generateTodoList() {

        ArrayList<Todo> todoList = new ArrayList<>();
        Iterator<Task> it;
        Task task;
        Date date = new Date();

        if (null != tasks) {
            it = tasks.iterator();
            while (it.hasNext()) {
                task = it.next();
                Date lastDate = task.getLastDate();

                if (task.lastDatePlusRepeatCountIsOver(date)) {

                }

            }
        }

        return todoList;
    }


    public static TaskList readTaskListFromFile(Context context) {
        TaskList tasklist = null;

        try {
            tasklist = readTaskListFromFile(context, DEFAULT_TASKLIST_FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tasklist;
    }


    // TODO: parser 部を 内部クラスか, 独立したクラスに分離する
    // TODO: parser 部の実装を再考する. (とりあえず状態マシンで XmlPullParser を, 構文解析器にしているがもっといい使い方できないのか?)
    // TODO: xml に何らかのかたちで schema 制約をかけられないか?
    enum TASK_PARSER_STATUS {
        IDLE,
        TASK_NAME,
        TASK_DETAIL,
        TASK_REPEAT_COUNT,
        TASK_REPEAT_UNIT,
        TASK_REPEAT,
        TASK_LASTDATE,
    }

    public static TaskList readTaskListFromFile(Context context, String _fileName) throws IOException, XmlPullParserException {

        File dir = context.getFilesDir();
        File xmlFile = new File(dir, _fileName);
        FileInputStream fis;
        TaskList taskList = null;
        Task task = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

        String _name = null;
        String _detail = null;
        int _repeatCount = 0;
        Task.REPEAT_UNIT _repeatUnit = null;
        boolean _repeat = false;
        Date _lastDate = null;


        TASK_PARSER_STATUS parserStatus = IDLE;

        if (!xmlFile.exists() || !xmlFile.isFile()) {
            return null;
        }
        else {
            fis = new FileInputStream(xmlFile);
        }

        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(fis, null);

        int eventType;
        eventType = xmlPullParser.getEventType();
        while (XmlPullParser.END_DOCUMENT != eventType) {
            switch (eventType) {
                case XmlPullParser.END_DOCUMENT:
                    break;
                case XmlPullParser.START_DOCUMENT:
                    break;
                case XmlPullParser.START_TAG:
                    if (xmlPullParser.getName().equals("tasklist")) {
                        taskList = new TaskList();
                    }
                    else if (xmlPullParser.getName().equals("task")) {
                    }
                    else if (xmlPullParser.getName().equals("name")) {
                        parserStatus = TASK_NAME;
                    }
                    else if (xmlPullParser.getName().equals("detail")) {
                        parserStatus = TASK_DETAIL;
                    }
                    else if (xmlPullParser.getName().equals("repeatCount")) {
                        parserStatus = TASK_REPEAT_COUNT;
                    }
                    else if (xmlPullParser.getName().equals("repeatUnit")) {
                        parserStatus = TASK_REPEAT_UNIT;
                    }
                    else if (xmlPullParser.getName().equals("repeat")) {
                        parserStatus = TASK_REPEAT;
                    }
                    else if (xmlPullParser.getName().equals("lastDate")) {
                        parserStatus = TASK_LASTDATE;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    parserStatus = IDLE;
                    if (xmlPullParser.getName().equals("tasklist")) {
                    }
                    else if (xmlPullParser.getName().equals("task")) {
                        task = new Task(_name, _detail, _repeatCount, _repeatUnit);
                        taskList.addTask(task);
                    }
                    break;
                case XmlPullParser.TEXT:
                    switch (parserStatus) {
                        case TASK_NAME:
                            _name = xmlPullParser.getText();
                            break;
                        case TASK_DETAIL:
                            _detail = xmlPullParser.getText();
                            break;
                        case TASK_REPEAT_COUNT:
                            _repeatCount = Integer.parseInt(xmlPullParser.getText());
                            break;
                        case TASK_REPEAT_UNIT:
                            String _unit = xmlPullParser.getText();
                            _repeatUnit = Task.REPEAT_UNIT.getUnitFromString(_unit);
                            break;
                        case TASK_REPEAT:
                            _repeat = Boolean.parseBoolean(xmlPullParser.getText());
                            break;
                        case TASK_LASTDATE:
                            try {
                                _lastDate = sdf.parse(xmlPullParser.getText());
                            } catch (ParseException e) {
                                _lastDate = null;
                                e.printStackTrace();
                            }
                            break;
                    }
                    break;
            }
            eventType = xmlPullParser.next();
        }

        return taskList;
    }

}