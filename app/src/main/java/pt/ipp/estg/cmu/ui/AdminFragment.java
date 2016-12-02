package pt.ipp.estg.cmu.ui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pt.ipp.estg.cmu.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AdminFragment extends Fragment {

    public AdminFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_admin, container, false);
    }
}
