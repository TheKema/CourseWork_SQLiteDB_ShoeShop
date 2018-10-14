package ainullov.kamil.com.shoeshop.user.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;

public class MainFragment extends Fragment implements View.OnClickListener {
    Button btnManMain;
    Button btnWomanMain;

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
        btnManMain = (Button) view.findViewById(R.id.btnManMain);
        btnWomanMain = (Button) view.findViewById(R.id.btnWomanMain);
        btnManMain.setOnClickListener(this);
        btnWomanMain.setOnClickListener(this);
        getActivity().setTitle("Обувной магазин");
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