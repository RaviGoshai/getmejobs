package rubberducks.getmejob.Utils;

import android.content.Context;
import android.util.AttributeSet;

import rubberducks.getmejob.R;

/**
 * Created by ADMIN on 3/13/2018.
 */

public class checkbox extends android.support.v7.widget.AppCompatCheckBox {



    public checkbox(Context context, AttributeSet attrs) {
        super(context, attrs);
        //setButtonDrawable(new StateListDrawable());
    }
    @Override
    public void setChecked(boolean t){
        if(t)
        {
            this.setBackgroundResource(R.drawable.ic_check_circle);
        }
        else
        {
            this.setBackgroundResource(R.color.white);
        }
        super.setChecked(t);
    }
}
