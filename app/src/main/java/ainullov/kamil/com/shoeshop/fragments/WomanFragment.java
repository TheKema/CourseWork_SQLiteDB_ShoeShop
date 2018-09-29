package ainullov.kamil.com.shoeshop.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ainullov.kamil.com.shoeshop.R;
import ainullov.kamil.com.shoeshop.adapters.ShoeTypeAdapter;
import ainullov.kamil.com.shoeshop.pojo.ShoeType;

public class WomanFragment extends Fragment {

    List<ShoeType> shoeTypes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_woman, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        shoeTypes.add(new ShoeType("Ботинки"));
        shoeTypes.add(new ShoeType("Кеды"));
        shoeTypes.add(new ShoeType("Кроссовки"));
        shoeTypes.add(new ShoeType("Туфли"));
        shoeTypes.add(new ShoeType("Сапоги"));
        shoeTypes.add(new ShoeType("Балетки"));
        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ShoeTypeAdapter adapter = new ShoeTypeAdapter(getActivity(), shoeTypes);
        recyclerView.setAdapter(adapter);
    }
}
