package ainullov.kamil.com.shoeshop;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ManagerFragment extends Fragment implements View.OnClickListener {
    TextView tvOrderProduct;
    TextView tvProvider;
    TextView tvStorageContent;

    TextView tvSalesAccounting;
    TextView tvDayResults;
    TextView tvSalesVolume;
    TextView tvEmployeePerf;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvOrderProduct = (TextView) view.findViewById(R.id.tvOrderProduct);
        tvProvider = (TextView) view.findViewById(R.id.tvProvider);
        tvStorageContent = (TextView) view.findViewById(R.id.tvStorageContent);

        tvSalesAccounting = (TextView) view.findViewById(R.id.tvSalesAccounting);
        tvDayResults = (TextView) view.findViewById(R.id.tvDayResults);
        tvSalesVolume = (TextView) view.findViewById(R.id.tvSalesVolume);
        tvEmployeePerf = (TextView) view.findViewById(R.id.tvEmployeePerf);


        tvOrderProduct.setOnClickListener(this);
        tvProvider.setOnClickListener(this);
        tvStorageContent.setOnClickListener(this);

        tvSalesAccounting.setOnClickListener(this);
        tvDayResults.setOnClickListener(this);
        tvSalesVolume.setOnClickListener(this);
        tvEmployeePerf.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        OrderProductFragment orderProductFragment = new OrderProductFragment();
        FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.tvOrderProduct:
//                MainActivity.manTRUEwomanFALSE = true;
//                MainActivity.gender = "М";
                fTrans.replace(R.id.container, orderProductFragment);
//                getActivity().setTitle("М");
                fTrans.addToBackStack(null);
                break;
            case R.id.tvProvider:

                break;
            case R.id.tvStorageContent:

                break;

            case R.id.tvSalesAccounting:

                break;
            case R.id.tvDayResults:

                break;
            case R.id.tvSalesVolume:

                break;
            case R.id.tvEmployeePerf:

                break;
        }
        fTrans.commit();
    }
}