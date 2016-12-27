package pt.ipp.estg.cmu.setup;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pt.ipp.estg.cmu.R;
import pt.ipp.estg.cmu.adapters.AdapterPageSetupAvatar;
import pt.ipp.estg.cmu.helpers.TextWatcherHelper;
import pt.ipp.estg.cmu.interfaces.AdapterPageSetupCallback;
import pt.ipp.estg.cmu.interfaces.FingerprintControllerCallback;
import pt.ipp.estg.cmu.security.FingerprintController;
import pt.ipp.estg.cmu.util.Util;

/**
 * Created by Navega on 12/27/2016.
 */

public class PageSetupFragment extends Fragment implements FingerprintControllerCallback {

    private int[] bgs = new int[]{R.drawable.img_setup_page_0,
            R.drawable.img_setup_page_1,
            R.drawable.img_setup_page_2};

    private FrameLayout centerLayout;
    private TextView title;
    private TextView description;
    private ImageView imageView;

    private AdapterPageSetupCallback mListener;

    //admin layout
    private LinearLayout mLayoutInfo;
    private LinearLayout mLayoutStatus;

    private int position;

    public PageSetupFragment() {
    }

    public static PageSetupFragment newInstance(int sectionNumber) {
        PageSetupFragment fragment = new PageSetupFragment();
        Bundle args = new Bundle();
        args.putInt(Util.ARG_SECTION, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(Util.ARG_SECTION);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_page_setup, container, false);

        centerLayout = (FrameLayout) rootView.findViewById(R.id.page_setup_layout);
        title = (TextView) rootView.findViewById(R.id.section_label);
        description = (TextView) rootView.findViewById(R.id.section_description);
        imageView = (ImageView) rootView.findViewById(R.id.section_img);


        switch (position) {
            case 0:
                title.setText(getActivity().getResources().getString(R.string.setup_page0_title));
                description.setText(getActivity().getResources().getString(R.string.setup_page0_subtitle));
                imageView.setBackgroundResource(bgs[position]);
                break;

            case 1:
                title.setText(getActivity().getResources().getString(R.string.setup_page1_title));
                description.setText(getActivity().getResources().getString(R.string.setup_page1_subtitle));
                imageView.setBackgroundResource(bgs[position]);
                break;

            case 2:
                title.setText(getActivity().getResources().getString(R.string.setup_page2_title));
                description.setText(getActivity().getResources().getString(R.string.setup_page2_subtitle));
                imageView.setBackgroundResource(bgs[position]);
                break;

            case 3:
                LinearLayout avatar_layout = (LinearLayout) inflater.inflate(R.layout.fragment_page_setup_avatar, null, false);
                centerLayout.removeAllViews();
                centerLayout.addView(avatar_layout);

                EditText mEditTextAvatar = (EditText) avatar_layout.findViewById(R.id.edit_nickname);
                mEditTextAvatar.addTextChangedListener(new TextWatcherHelper(getContext(), mListener, mEditTextAvatar));

                RecyclerView recyclerView = (RecyclerView) avatar_layout.findViewById(R.id.recycler_view);
                AdapterPageSetupAvatar adapter = new AdapterPageSetupAvatar(getContext(), mListener);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                recyclerView.setAdapter(adapter);

                break;

            case 4:
                LinearLayout admin_layout = (LinearLayout) inflater.inflate(R.layout.fragment_page_setup_admin, null, false);
                centerLayout.removeAllViews();
                centerLayout.addView(admin_layout);

                EditText mEditTextPin = (EditText) admin_layout.findViewById(R.id.edit_pin);
                mEditTextPin.addTextChangedListener(new TextWatcherHelper(getContext(), mListener, mEditTextPin));

                mLayoutInfo = (LinearLayout) admin_layout.findViewById(R.id.fingerprint_layout_info);
                mLayoutStatus = (LinearLayout) admin_layout.findViewById(R.id.fingerprint_layout_status);

                ImageView imageView = (ImageView) admin_layout.findViewById(R.id.fingerprint_icon_status);
                TextView textView = (TextView) admin_layout.findViewById(R.id.fingerprint_text_status);

                new FingerprintController(getContext(), imageView, textView, this);

                break;

        }
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (AdapterPageSetupCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void fingerprintAuthResult(boolean result) {
        mLayoutInfo.setVisibility(View.GONE);
    }
}