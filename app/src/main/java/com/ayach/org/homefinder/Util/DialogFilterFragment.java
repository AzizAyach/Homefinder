package com.ayach.org.homefinder.Util;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appyvet.rangebar.RangeBar;
import com.ayach.org.homefinder.Entity.Price;
import com.ayach.org.homefinder.R;
import com.ayach.org.homefinder.View.Home_Activity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DialogFilterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DialogFilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogFilterFragment extends android.support.v4.app.DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText bed ;
    EditText room;
    EditText bathrom;
    RangeBar rangeprice;
    TextView minprice;
    TextView maxprice;
    Button submit;
    int bednumber = 1;
    int roomnumber = 1;
    int bethnumber = 1;
    int mi_price = 10;
    int ma_price = 1000;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DialogFilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DialogFilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DialogFilterFragment newInstance(String param1, String param2) {
        DialogFilterFragment fragment = new DialogFilterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dialog_filter, container, false);
        bed = (EditText) v.findViewById(R.id.bedfilter);
        room = (EditText) v.findViewById(R.id.roomfilter);
        bathrom = (EditText) v.findViewById(R.id.bathfilter);
        rangeprice=(RangeBar)v.findViewById(R.id.rangebar);
        minprice = (TextView) v.findViewById(R.id.pricemin);
        maxprice = (TextView) v.findViewById(R.id.pricemax);
        bathrom = (EditText) v.findViewById(R.id.bathfilter);
        submit = (Button) v.findViewById(R.id.applyfilter);
        getDialog().setTitle("Filter Search Results");

rangeprice.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
    @Override
    public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex, int rightPinIndex, String leftPinValue, String rightPinValue) {
        mi_price = Integer.parseInt(leftPinValue);
        ma_price = Integer.parseInt(rightPinValue);
        minprice.setText(leftPinValue);
        maxprice.setText(rightPinValue);
    }
});

submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (bed.getText().toString().trim().length() != 0)
        {
            bednumber = Integer.parseInt(bed.getText().toString());
        }
        if (room.getText().toString().trim().length() != 0)
        {
            roomnumber = Integer.parseInt(room.getText().toString());
        }
        if (bathrom.getText().toString().trim().length() != 0)
        {
            bethnumber = Integer.parseInt(bathrom.getText().toString());
        }
        Price price = new Price(bednumber,roomnumber, bethnumber, 0, mi_price,ma_price);
        Home_Activity.searchBunle.putParcelable("Search", price);
        dismiss();

    }
});
        return v ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
