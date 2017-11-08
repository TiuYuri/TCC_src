package com.tiyuri.periocularrecognition;

import android.util.Log;
import android.widget.EditText;

import com.tiyuri.periocularrecognition.DAO.User;

public class FormHelper {

    private final String histogram1;
    private final String histogram2;
    private final String histogram3;
    private EditText fieldName;

    public FormHelper(FormActivity activity){
        fieldName = (EditText) activity.findViewById( R.id.form_name);
        histogram1 = activity.histogram1;
        histogram2 = activity.histogram2;
        histogram3 = activity.histogram3;
    }

    public User getUser(FormActivity activity){
        User user = new User();
        user.setName(fieldName.getText().toString());
        user.setPhoto1Lbp(activity.histogram1);
        user.setPhoto2Lbp(activity.histogram2);
        user.setPhoto3Lbp(activity.histogram3);
        return user;
    }
}
