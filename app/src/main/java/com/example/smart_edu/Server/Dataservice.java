package com.example.smart_edu.Server;

import android.content.res.Resources;

import com.example.smart_edu.Model.Book;
import com.example.smart_edu.Model.Class;
import com.example.smart_edu.Model.Student;
import com.example.smart_edu.Model.Theme;
import com.example.smart_edu.Model.User;
import com.example.smart_edu.Model.Vocab;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Dataservice {

    @GET("books")
    Call<List<Book>> GetDataBook();

    @GET("taikhoans")
    Call<List<User>> GetDataUser();

    @GET("Students")
    Call<List<Student>> GetDataStudent();

    @GET("Classes")
    Call<List<Class>> GetDataClass();

    @POST("students/searchHV/{word}")
    Call<List<Student>> SearchHV(String word);

    @DELETE("vocabularies/deletevocab/{Id_vocab}")
    Call<Void> DeleteVocab(@Path("Id_vocab") int id_vocab);

    @PUT("vocabularies/update/{Id_vocab}")
    Call<Void> UpdateVocab(@Path("Id_vocab") int id_vocab, @Body Vocab tumoi);

    @PUT("students/Update/{Id_HV}")
    Call<Void> UpdateHV(@Path("Id_HV") int id, @Body Student hocvien);

    @GET("students/getbyidclass/{Id_class}")
    Call<List<Student>> GetStudent(@Path("Id_class") int id_class);

    @GET("students/getbyemail/{Email}")
    Call<Student> GetStudentEmail(@Path("Email") String email);

    @GET("students/getbyidHV/{Id_HV}")
    Call<List<Student>> GetStudentID(@Path("Id_HV") int id_HV);

    @GET("vocabularies/getbyid/{Id_theme}")
    Call<List<Vocab>> GetVocabulary(@Path("Id_theme") int id_theme);

    @GET("themes/getbyid/{Id_book}")
    Call<List<Theme>> GetTheme_book(@Path("Id_book") int id_book);

    @FormUrlEncoded
    @POST("themes/AddTheme")
    Call<Book> addBook(@Field("Name_book") String name_theme, @Field("Image_book") String id_lession);

    @FormUrlEncoded
    @POST("vocabularies/AddVocab")
    Call<Vocab> addVocab(@Field("Id_theme") int id_theme ,@Field("Word") String word, @Field("Meaning") String meaning);

    @FormUrlEncoded
    @POST("themes/AddTheme")
    Call<Theme> addTheme(@Field("Id_book") int id_book ,@Field("Name_theme") String name_theme, @Field("Id_lession") String id_lession);

    @FormUrlEncoded
    @POST("Students/AddStudent")
    Call<Student> addHV(@Field("Name_HV") String nameHV, @Field("Id_class") int id_class,
                        @Field("Tuition") int hocphi, @Field("Name_PH") String namePH,
                        @Field("Phone_PH") String phone, @Field("Email") String email,
                        @Field("Address_HV") String address, @Field("Date_of_birth") Date ngay);

    @FormUrlEncoded
    @POST("Taikhoans/AddTaikhoan")
    Call<User> addTK(@Field("Email") String email, @Field("Type_persional") String type);
}
