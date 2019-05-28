package pvt19grupp1.kunskapp.com.kunskapp.util;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pvt19grupp1.kunskapp.com.kunskapp.R;


public class CustomFragment extends Fragment {
    private String mText = "";
    public static CustomFragment createInstance(String txt)
    {
        CustomFragment fragment = new CustomFragment();
        fragment.mText = txt;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_sample,container,false);
        ((TextView) v.findViewById(R.id.textView)).setText(mText);
        return v;
    }

}