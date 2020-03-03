package fragmentos_navegacion.aniadir_companiero;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cuadrante.R;

public class aniadir_companiero extends Fragment {

    private AniadirCompanieroViewModel mViewModel;

    public static aniadir_companiero newInstance() {
        return new aniadir_companiero();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.aniadir_companiero_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AniadirCompanieroViewModel.class);
        // TODO: Use the ViewModel
    }

}
