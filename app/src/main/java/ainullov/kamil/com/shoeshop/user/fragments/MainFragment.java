package ainullov.kamil.com.shoeshop.user.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;

public class MainFragment extends Fragment implements View.OnClickListener {
    Button btnManMain;
    Button btnWomanMain;
    ImageView ivHeader;

//  Если понадобится поворот экрана (в MА, в OnCreate в этом случае переделать создание MainFragment)
//  @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setRetainInstance(true); }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivHeader = (ImageView) view.findViewById(R.id.ivHeader);
        btnManMain = (Button) view.findViewById(R.id.btnManMain);
        btnWomanMain = (Button) view.findViewById(R.id.btnWomanMain);
        btnManMain.setOnClickListener(this);
        btnWomanMain.setOnClickListener(this);
        getActivity().setTitle("Обувной магазин");

        // в MainFragment происходит очищение bakcstack
        FragmentManager fm = getActivity().getFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

//        Glide.with(getActivity()).load("https://openweathermap.org/img/w/10d.png").into(ivHeader);
//        Picasso.with(getActivity()).load("https://image.ibb.co/mMjRy0/3031245-1.jpg").into(ivHeader);

    }

    @Override
    public void onClick(View view) {
        ManFragment manFragment = new ManFragment();
        WomanFragment womanFragment = new WomanFragment();
        FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.btnManMain:
                MainActivity.manTRUEwomanFALSE = true;
                MainActivity.gender = "М";
                fTrans.replace(R.id.container, manFragment);
                getActivity().setTitle("М");
                fTrans.addToBackStack(null);
                break;
            case R.id.btnWomanMain:
                MainActivity.manTRUEwomanFALSE = false;
                MainActivity.gender = "Ж";
                getActivity().setTitle("Ж");
                fTrans.replace(R.id.container, womanFragment);
                fTrans.addToBackStack(null);
                break;
        }
        fTrans.commit();
    }
}