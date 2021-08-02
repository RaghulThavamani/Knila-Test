package com.example.editor.abstarctClass;

import com.example.editor.model.Data;
import com.example.editor.model.User;

public abstract class UserData {

    static Data user;
    static boolean isEdit;

    public static boolean isIsEdit() {
        return isEdit;
    }

    public static void setIsEdit(boolean isEdit) {
        UserData.isEdit = isEdit;
    }

    public static Data getUser() {
        return user;
    }

    public static void setUser(Data user) {
        UserData.user = user;
    }
}
