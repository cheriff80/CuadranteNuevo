package viewModel;

import androidx.lifecycle.ViewModel;

import clases.Usuario;

public class UserViewModel extends ViewModel {

    private Usuario user;

    public Usuario getUser(){
        if(user == null){
            loadUser(user);
        }
        return user;
    }
    public void loadUser( Usuario user) {

      this.user = user;

    }


}
