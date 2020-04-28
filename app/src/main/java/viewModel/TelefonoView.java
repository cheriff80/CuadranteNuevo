package viewModel;

import androidx.lifecycle.ViewModel;

public class TelefonoView extends ViewModel {

    private String numTelefono;

    public String getNumTelefono(){

        loadNumTelefono(numTelefono);
        return numTelefono;
    }
    public void loadNumTelefono( String telefono) {

        numTelefono = telefono;

    }
}
