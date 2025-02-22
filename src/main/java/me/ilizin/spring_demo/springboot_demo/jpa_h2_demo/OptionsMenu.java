package me.ilizin.spring_demo.springboot_demo.jpa_h2_demo;

public enum OptionsMenu {
    CREATE_TASK,
    UPDATE_TASK,
    DELETE_TASK,
    READ_TASK_BY_ID,
    READ_TASK_BY_WORD,
    LIST_ALL_TASKS,
    DELETE_ALL_TASKS,
    EXIT;

    public static OptionsMenu get(String value) {
        switch (value) {
            case "1": return CREATE_TASK;
            case "2": return UPDATE_TASK;
            case "3": return DELETE_TASK;
            case "4": return READ_TASK_BY_ID;
            case "5": return READ_TASK_BY_WORD;
            case "6": return LIST_ALL_TASKS;
            case "7": return DELETE_ALL_TASKS;
            case "8": return EXIT;
            default: return null;
        }
    }
}
