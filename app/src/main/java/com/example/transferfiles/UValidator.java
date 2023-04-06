package com.example.transferfiles;

import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UValidator {

    public static boolean validateName(TextInputLayout editText, String errorMessage){
        String input = Objects.requireNonNull(editText.getEditText()).getText().toString().trim();
        //Pattern pattern = Pattern.compile("^[a-zA-Z]{3,20}$");
        Pattern pattern = Pattern.compile("^([a-záéíóúÁÉÍÓÚA-Z]+([ '-][a-záéíóúÁÉÍÓÚA-Z]+)*)");
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches() && !input.equals("")){
            editText.setErrorEnabled(false);
            return true;
        }else{
            editText.setError(errorMessage);
            return false;
        }
    }
    public static boolean validateLastName(TextInputLayout editText,String errorMessage){
        String input = Objects.requireNonNull(editText.getEditText()).getText().toString().trim();
        //Pattern pattern = Pattern.compile("^[a-zA-Z]{3,20}$");
        Pattern pattern = Pattern.compile("^([a-záéíóúÁÉÍÓÚA-Z]+([ '-][a-záéíóúÁÉÍÓÚA-Z]+)*)");
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches()  && !input.equals("")){
            editText.setErrorEnabled(false);
            return true;
        }else{
            editText.setError(errorMessage);
            return false;
        }
    }
    public static boolean validateDI(TextInputLayout editText,String errorMessage){
        String input = editText.getEditText().getText().toString();
        Pattern pattern = Pattern.compile("^[0-9]{8,10}$");
        Matcher matcher = pattern.matcher(input);
        if(matcher.matches() && !input.equals("")){
            return true;
        }else{
            editText.setError(errorMessage);
            return false;
        }
    }
    public static boolean validateUb(TextInputLayout editText,String ubicacion, String errorMessage){
        String input = editText.getEditText().getText().toString().trim();
        if(ubicacion.length()>0 && !input.isEmpty()){
            editText.setEnabled(true);
            return true;
        }else{
            editText.setError(errorMessage);
            return false;
        }
    }
}
