package com.example.a1786;

import android.widget.EditText;

public class InputValidatorEditText {
    public static boolean isEditTextFilled(EditText editText) {
        // Kiểm tra xem EditText có được điền đầy đủ không
        return editText.getText().toString().trim().length() > 0 ;
    }

    public static boolean isEditTextLengthValid(EditText editText, int maxLength) {
        // Kiểm tra xem độ dài của EditText có hợp lệ không
        return editText.getText().toString().trim().length() <= maxLength;
    }

    public static boolean areAllEditTextsFilled(EditText... editTexts) {
        // Kiểm tra tất cả các EditText có được điền đầy đủ không
        for (EditText editText : editTexts) {
            if (!isEditTextFilled(editText)) {
                return false;
            }
        }
        return true;
    }
}
