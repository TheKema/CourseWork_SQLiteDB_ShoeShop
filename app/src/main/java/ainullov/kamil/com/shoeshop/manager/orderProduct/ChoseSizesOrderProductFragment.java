package ainullov.kamil.com.shoeshop.manager.orderProduct;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.manager.storageContent.StorageContentChangeProductFragment;

public class ChoseSizesOrderProductFragment extends Fragment implements View.OnClickListener {
    Button btnAddSizes;

    EditText et1, et2, et3, et4, et5, et6, et7, et8, et9, et10, et11, et12, et13, et14;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chosesizes_orderproduct, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAddSizes = (Button) view.findViewById(R.id.btnAddSizes);
        btnAddSizes.setOnClickListener(this);

        et1 = (EditText) view.findViewById(R.id.et1);
        et2 = (EditText) view.findViewById(R.id.et2);
        et3 = (EditText) view.findViewById(R.id.et3);
        et4 = (EditText) view.findViewById(R.id.et4);
        et5 = (EditText) view.findViewById(R.id.et5);
        et6 = (EditText) view.findViewById(R.id.et6);
        et7 = (EditText) view.findViewById(R.id.et7);
        et8 = (EditText) view.findViewById(R.id.et8);
        et9 = (EditText) view.findViewById(R.id.et9);
        et10 = (EditText) view.findViewById(R.id.et10);
        et11 = (EditText) view.findViewById(R.id.et11);
        et12 = (EditText) view.findViewById(R.id.et12);
        et13 = (EditText) view.findViewById(R.id.et13);
        et14 = (EditText) view.findViewById(R.id.et14);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddSizes:
                OrderProductFragment.size = "";

                int[] isizes = new int[]{Integer.valueOf(et1.getText().toString()),
                        Integer.valueOf(et3.getText().toString()), Integer.valueOf(et5.getText().toString()),
                        Integer.valueOf(et7.getText().toString()), Integer.valueOf(et9.getText().toString()),
                        Integer.valueOf(et11.getText().toString()), Integer.valueOf(et13.getText().toString())};

                int[] iquantity = new int[]{Integer.valueOf(et2.getText().toString()),
                        Integer.valueOf(et4.getText().toString()), Integer.valueOf(et6.getText().toString()),
                        Integer.valueOf(et8.getText().toString()), Integer.valueOf(et10.getText().toString()),
                        Integer.valueOf(et12.getText().toString()), Integer.valueOf(et14.getText().toString())};

                // ArrayList, из чисел - размер обуви, превращаем в строку и суем в db, потом достанем и превратив в ArrayList
                ArrayList<String> items = new ArrayList<>();
//                Проход по всем et
                for (int j = 0; j < iquantity.length; j++) {
                    if (isizes[j] != 0 && iquantity[j] != 0) {
                        for (int k = 0; k < iquantity[j]; k++) {
                            items.add(String.valueOf(isizes[j]));
                        }
                    }
                }

                JSONObject json = new JSONObject();
                try {
                    json.put("uniqueArrays", new JSONArray(items));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Bundle bundleAddOrChange = this.getArguments();
                String strAddOrChange = bundleAddOrChange.getString("addorchange");
                if (strAddOrChange.equals("add")) {
                    OrderProductFragment.size = json.toString();
                    OrderProductFragment.quantity = items.size();
                } else if (strAddOrChange.equals("change")) {
                    StorageContentChangeProductFragment.size = json.toString();
                    StorageContentChangeProductFragment.quantity = items.size();
                }
                getActivity().getFragmentManager().popBackStack();
                break;
        }
    }
}