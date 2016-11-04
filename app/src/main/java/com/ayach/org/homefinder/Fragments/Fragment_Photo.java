package com.ayach.org.homefinder.Fragments;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.ayach.org.homefinder.Page.PagePhoto;
import com.ayach.org.homefinder.R;
import com.ayach.org.homefinder.View.Add_Activity;
import com.tech.freak.wizardpager.ui.PageFragmentCallbacks;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Photo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 .
 */
public class Fragment_Photo extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String mKey;
    private PagePhoto mPage;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int RESULT_OK = 1;
    ImageView img1 ;
    ImageView img2;
    ImageView img3;
    ImageView img4;
    Bitmap bit1;
    Bitmap bit2;
    Bitmap bit3;
    Bitmap bit4;
    Button upload;
    private ImageButton btnNext;
    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>(4);
    ArrayList<Uri> mArrayUri=new ArrayList<Uri>(4);
    ArrayList<Bitmap> bitmapsnder = new ArrayList<Bitmap>();
    int PICK_IMAGES =1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private static final String NEW_IMAGE_URI ="";
    protected static final String ARG_KEY = "key";

    private PageFragmentCallbacks mCallbacks;

    private ImageView imageView;

    private Uri mNewImageUri;

    public static Fragment_Photo create(String key) {
        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);

        Fragment_Photo f = new Fragment_Photo();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = (PagePhoto) mCallbacks.onGetPage(mKey);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNewImageUri != null) {
            outState.putString(NEW_IMAGE_URI, mNewImageUri.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_fragment__photo, container, false);
        ((TextView) v.findViewById(android.R.id.title)).setText(mPage.getTitle());
        img1 = (ImageView)v.findViewById(R.id.Image1);
        img2 = (ImageView) v.findViewById(R.id.Image2);
        img3 = (ImageView) v.findViewById(R.id.Image3);
        img4 = (ImageView) v.findViewById(R.id.Image4);

        upload=(Button)v.findViewById(R.id.btn_Upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "You can upload 4 image!" , Toast.LENGTH_SHORT ).show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES);


            }
        });
        return  v ;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (!(activity instanceof PageFragmentCallbacks)) {
            throw new ClassCastException(
                    "Activity must implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    public ArrayList<Bitmap> sendImage (ArrayList <Uri> list)
    {
        ArrayList<Bitmap> bit =new ArrayList<Bitmap>();
        for(Uri ur : list){
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), ur);
                bit.add(bitmap);
            }
            catch (Exception e){
                System.out.println("ereur de chargement des photo");
            }
        }

        return  bit;
    }

    public void uplodimage(Intent data){




        if(data.getData()!=null){

            Uri mImageUri=data.getData();

        }else{
            if(data.getClipData()!=null){
                ClipData mClipData=data.getClipData();
                for(int i=0;i<mClipData.getItemCount();i++){

                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();
                    mArrayUri.add(uri);

                }
                System.out.println("Gallery pick success");





                Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                try {
                    switch (mArrayUri.size()){
                        case 1:
                            Uri uri1= mArrayUri.get(0);
                            bit1 =decodeUri(getActivity(), uri1, 200);
                            img1.setImageBitmap(bit1);
                            bitmapArray.add(bit1);
                            break;
                        case 2:
                            Uri uri11= mArrayUri.get(0);
                            Uri uri2= mArrayUri.get(1);
                            bit1 =decodeUri(getActivity(), uri11, 200);
                            img1.setImageBitmap(bit1);
                            bit2=decodeUri(getActivity(), uri2, 200);
                            img2.setImageBitmap(bit2);
                            bitmapArray.add(bit1);
                            bitmapArray.add(bit2);
                            break;
                        case 3:
                            Uri uri111= mArrayUri.get(0);
                            Uri uri21= mArrayUri.get(1);
                            Uri uri3= mArrayUri.get(2);
                            bit1 =decodeUri(getActivity(), uri111, 200);
                            img1.setImageBitmap(bit1);
                            bit2=decodeUri(getActivity(), uri21, 200);
                            img2.setImageBitmap(bit2);
                            bit3=decodeUri(getActivity(), uri3, 200);
                            img3.setImageBitmap(bit3);
                            bitmapArray.add(bit1);
                            bitmapArray.add(bit2);
                            bitmapArray.add(bit3);
                            break;
                        case 4:
                            Uri uri1111= mArrayUri.get(0);
                            Uri uri211= mArrayUri.get(1);
                            Uri uri31= mArrayUri.get(2);
                            Uri uri4= mArrayUri.get(3);
                            bit1 =decodeUri(getActivity(), uri1111, 200);
                            img1.setImageBitmap(bit1);
                            bit2=decodeUri(getActivity(), uri211, 200);
                            img2.setImageBitmap(bit2);
                            bit3=decodeUri(getActivity(), uri31, 200);
                            img3.setImageBitmap(bit3);
                            bit4=decodeUri(getActivity(), uri4, 200);
                            img4.setImageBitmap(bit4);
                            bitmapArray.add(bit1);
                            bitmapArray.add(bit2);
                            bitmapArray.add(bit3);
                            bitmapArray.add(bit4);
                            break;



                    }
                }
                catch (Exception e){

                    System.out.println("non non ");
                }


            }
            SendResult();

        }







    }
    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth
                , height_tmp = o.outHeight;
        int scale = 1;

        while(true) {
            if(width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uplodimage(data);
        mPage.getData().putString("photomake",
                "teste");
        mPage.notifyDataChanged();
    }

    private void SendResult() {
        System.out.println("image:" + bitmapArray.get(0));
        Add_Activity.photoBundle.putParcelableArrayList("Photo", bitmapArray);
        Log.d("Bundle", Add_Activity.photoBundle.toString());

    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
