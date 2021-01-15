package co.com.mirecarga.vendedor.notification;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import co.com.mirecarga.vendedor.R;
import co.com.mirecarga.vendedor.business.NotificacionDato;

public class NotificationListFragment extends Fragment {

    //@BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    static NotificacionAdapter mAdapter;
    static List<NotificacionDato> mItems;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification_list, container, false);
        ButterKnife.bind(this, view);

        setupList(view);

        return view;
    }

    private void setupList(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Create and set an adapter
        mItems = new ArrayList<>();
        mItems.add(new NotificacionDato("Perdida de conexión", "Por favor verifique el estado de su conexión.", "1"));
        mItems.add(new NotificacionDato("Celda 01", "Se ha abierto un tiquete en la Celda 01, por favor dirijase a la ubicación designada.", "2"));
        mItems.add(new NotificacionDato("Celda 02", "Se ha cerrado un tiquete en la Celda 02, por favor dirijase a la ubicación designada.", "2"));
        mItems.add(new NotificacionDato("Obtener de imagen", "Es necesario que tome una foto, por favor dirijase a la ubicación designada.", "3"));
        mItems.add(new NotificacionDato("Captura de placa", "Es necesario que tome una foto a la plata de un vehículo, por favor dirijase a su ubicación.", "3"));

        mAdapter = new NotificacionAdapter(getActivity(), mItems);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mAdapter != null) {
            mAdapter.saveStates(outState);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable final Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (mAdapter != null) {
            mAdapter.restoreStates(savedInstanceState);
        }
    }
}
