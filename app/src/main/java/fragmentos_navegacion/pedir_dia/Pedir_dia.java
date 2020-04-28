package fragmentos_navegacion.pedir_dia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cuadrante.R;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Pedir_dia extends Fragment {

    private String numeroTelefono;
    private PedirDiaViewModel mViewModel;

    public static Pedir_dia newInstance() {
        return new Pedir_dia();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.solicitar_dia_fragment, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(PedirDiaViewModel.class);

    }


}
