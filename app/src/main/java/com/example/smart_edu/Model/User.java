package com.example.smart_edu.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

@SerializedName("Id_user")
@Expose
private Integer idUser;
@SerializedName("Email")
@Expose
private String email;
@SerializedName("Type_persional")
@Expose
private String typePersional;

public Integer getIdUser() {
return idUser;
}

public void setIdUser(Integer idUser) {
this.idUser = idUser;
}

public String getEmail() {
return email;
}

public void setEmail(String email) {
this.email = email;
}

public String getTypePersional() {
return typePersional;
}

public void setTypePersional(String typePersional) {
this.typePersional = typePersional;
}

}