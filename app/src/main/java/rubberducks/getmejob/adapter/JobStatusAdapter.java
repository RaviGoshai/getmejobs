package rubberducks.getmejob.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import rubberducks.getmejob.R;

/**
 * Created by ADMIN on 3/17/2018.
 */
public class JobStatusAdapter extends RecyclerView.Adapter<JobStatusAdapter.ViewHolder> {
    private final Context mContext;
    private List<String> mList;
    private String TAG = JobStatusAdapter.class.getSimpleName();
    private OnItemClickListener onItemClickListener;


    public JobStatusAdapter(Context mContext, List<String> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_status_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
      ///  m = mList.get(position);
        //TODO Fill in your logic for binding the view.
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return 15;
       /* if (mList == null) {
            return 0;
        }
        return mList.size();*/
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView venuename_txtview = null;

        public ViewHolder(View itemView) {
            super(itemView);
//            venuename_txtview = (TextView) itemView.findViewById(R.id.text);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            try {

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

}