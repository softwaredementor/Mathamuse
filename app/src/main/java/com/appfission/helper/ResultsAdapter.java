package com.appfission.helper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appfission.mathamuse.R;

/**
 * Created by srikanthmannepalle on 2/12/17.
 */
public class ResultsAdapter extends ArrayAdapter<Result> {
    private Context mContext;
    private int resourceLayoutId;
    private Result[] mData;

    public ResultsAdapter(Context context, int resource, Result[] data) {
        super(context, resource, data);
        this.mContext = context;
        this.resourceLayoutId = resource;
        this.mData = data;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getCount() {
        super.getCount();
        Log.d(getClass().getName(), "Array size = " + mData.length);
        return mData.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        //inflate the layout for a single row
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        row = layoutInflater.inflate(resourceLayoutId, parent, false);

        //get a reference to the different view elements we wish to update
        TextView questionNumberView = (TextView) row.findViewById(R.id.questionNum);
        ImageView resultsView = (ImageView) row.findViewById(R.id.resultIcon);
        TextView correctAnswerView = (TextView) row.findViewById(R.id.correctAns);
        TextView incorrectAnswerView = (TextView) row.findViewById(R.id.incorrectAns);

        //get the data from the data array
        Result result = mData[position];
        Log.d(this.getClass().getName(), "Position = " + position + " processed");

        //setting the view to relfect the data we need to display
        questionNumberView.setText(result.mQuestionNumber);
        correctAnswerView.setText(String.valueOf(result.mCorrectAnswer));
        incorrectAnswerView.setText(String.valueOf(result.mYourAnswer));
        int resId = mContext.getResources().getIdentifier(result.mNameOfImage,
                "drawable", mContext.getPackageName());
        resultsView.setImageResource(resId);

        //return the row view
        return row;
    }
}
