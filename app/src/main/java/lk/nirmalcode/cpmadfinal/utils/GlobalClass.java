package lk.nirmalcode.cpmadfinal.utils;

import android.app.Application;

import lk.nirmalcode.cpmadfinal.models.Staff;

public class GlobalClass extends Application {

    public final String TAG = "CpmadFinalLog";

    public final String REF_STAFF = "staffs";
    public final String REF_STUDENTS = "students";
    public final String REF_EXPENSES = "expenses";

    public final String REF_KEEP_LOGGED_STATUS = "KeepLoggedStatus";
    public final String REF_KEEP_LOGGED_USER_KEY = "KeepLoggedUserKey";
    public final String REF_KEEP_LOGGED_USER_PASSWORD = "KeepLoggedUserPassword";
    public final String REF_KEEP_LOGGED_ERROR = "KeepLoggedError";

    public Staff loggedUser;

    public Staff getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Staff loggedUser) {
        this.loggedUser = loggedUser;
    }
}