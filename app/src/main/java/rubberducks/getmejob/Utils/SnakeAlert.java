package rubberducks.getmejob.Utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import rubberducks.getmejob.R;


/**
 * Created by ADMIN on 1/19/2018.
 */

public class SnakeAlert {

    public static Snackbar setSnake(Context context, String msg, View view){
        Snackbar snackbar = Snackbar.make(view, ""+msg, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
     //   snackBarView.setBackgroundColor(context.getResources().getColor(R.color.text_color));
        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
       // textView.setTextColor(context.getResources().getColor(R.color.white));
        snackbar.show();
        return snackbar;
    }
}
