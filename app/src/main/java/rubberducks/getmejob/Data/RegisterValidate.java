package rubberducks.getmejob.Data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ADMIN on 3/20/2018.
 */

public class RegisterValidate {
    @SerializedName("succes")
    private String message;
    @SerializedName("success")
    private boolean success;
    private ArrayList<String> data;

    public RegisterValidate(String message, boolean success, ArrayList<String> data){
        this.message= message;
        this.data= data;
        this.success= success;
    }

    public RegisterValidate(String message, boolean success){
        this.message= message;
        this.success= success;
    }

    public  RegisterValidate(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<String> getData() {
        return data;
    }
}
