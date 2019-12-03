package como.example.noman.project;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;

public class AddHostel extends Fragment {

    static public Bitmap _bitmap;
    static public Uri _uri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_hostel, container, false);

        _bitmap = null;
        _uri = null;

        //v.findViewById(R.id.addHostel_choose_gal).setOnClickListener(new ClickListener(getActivity(), getContext()));

        v.findViewById(R.id.addHostel_choose_gal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImagesFromGallery();
            }
        });

        v.findViewById(R.id.addHostel_save).setOnClickListener(new ClickListener(getActivity(), getContext()));
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            switch (requestCode){
                case 1000:
                    Uri selectedImage = data.getData();
                    _uri = selectedImage;
                    try {
                        if (getActivity() != null){
                            _bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                            String t = "Choose a different image";
                            String t2 = "1 Image Selected";
                            ((Button)getActivity().findViewById(R.id.addHostel_choose_gal)).setText(t);
                            ((TextView)getActivity().findViewById(R.id.addHostel_upload_text)).setText(t2);
                            Toast.makeText(getActivity(), "Image selected", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Image Not Selected, Activity is NULL", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        Toast.makeText(getActivity(), "Image Not Selected, Error occurred", Toast.LENGTH_SHORT).show();
                        //Log.i("TAG", "Some exception " + e);
                    }
                    break;
                default:
                    Toast.makeText(getActivity(), "Image Not Selected, RequestCode not 1000", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        else{
            Toast.makeText(getActivity(), "Image Not Selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickImagesFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, 1000);
    }
}
