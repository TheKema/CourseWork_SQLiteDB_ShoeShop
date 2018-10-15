package ainullov.kamil.com.shoeshop.manager;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ainullov.kamil.com.shoeshop.MainActivity;
import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.login.LoginFragment;
import ainullov.kamil.com.shoeshop.manager.dayResults.DayResultsFragment;
import ainullov.kamil.com.shoeshop.manager.employeePerf.EmployeePerfFragment;
import ainullov.kamil.com.shoeshop.manager.orderProduct.OrderProductFragment;
import ainullov.kamil.com.shoeshop.manager.ordersAccounting.OrdersAccountingFragment;
import ainullov.kamil.com.shoeshop.manager.salesAccounting.SalesAccountingFragment;
import ainullov.kamil.com.shoeshop.manager.salesVolume.SalesVolumeFragment;
import ainullov.kamil.com.shoeshop.manager.storageContent.StorageContentFragment;

public class ManagerFragment extends Fragment implements View.OnClickListener {
    TextView tvOrderProduct;
    TextView tvProvider;
    TextView tvStorageContent;

    TextView tvSalesAccounting;
    TextView tvDayResults;
    TextView tvSalesVolume;
    TextView tvEmployeePerf;
    Button btnExit;

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

        btnExit = (Button) view.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);

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
        OrdersAccountingFragment ordersAccountingFragment = new OrdersAccountingFragment();
        StorageContentFragment storageContentFragment = new StorageContentFragment();
        SalesAccountingFragment salesAccountingFragment = new SalesAccountingFragment();
        DayResultsFragment dayResultsFragment = new DayResultsFragment();
        SalesVolumeFragment salesVolumeFragment = new SalesVolumeFragment();
        EmployeePerfFragment employeePerfFragment = new EmployeePerfFragment();

        LoginFragment loginFragment = new LoginFragment();

        FragmentTransaction fTrans;
        fTrans = getFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.tvOrderProduct:
                fTrans.replace(R.id.container, orderProductFragment);
                fTrans.addToBackStack(null);
                break;
            case R.id.tvProvider:
                fTrans.replace(R.id.container, ordersAccountingFragment);
                fTrans.addToBackStack(null);
                break;
            case R.id.tvStorageContent:
                fTrans.replace(R.id.container, storageContentFragment);
                fTrans.addToBackStack(null);
                break;

            case R.id.tvSalesAccounting:  /// Могут быть ошибки, т.к. id в layout'ах  смешались с storageContentFragment, ИСПРАВИТЬ!
                fTrans.replace(R.id.container, salesAccountingFragment);
                fTrans.addToBackStack(null);
                break;
            case R.id.tvDayResults:
                fTrans.replace(R.id.container, dayResultsFragment);
                fTrans.addToBackStack(null);
                break;
            case R.id.tvSalesVolume:
                fTrans.replace(R.id.container, salesVolumeFragment);
                fTrans.addToBackStack(null);
                break;
            case R.id.tvEmployeePerf:
                fTrans.replace(R.id.container, employeePerfFragment);
                fTrans.addToBackStack(null);
                break;
            case R.id.btnExit:
                MainActivity.USERNAME_USER_DB = "";
                MainActivity.USERNAME_BASKET_DB = "";
                MainActivity.USERNAME_FAVORITE_DB = "";
                fTrans.remove(this);
                fTrans.add(R.id.container, loginFragment);
                break;
        }
        fTrans.commit();
    }
}