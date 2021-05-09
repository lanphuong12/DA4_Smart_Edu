package com.example.smart_edu.Admin.QlyHocvien;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smart_edu.Model.Student;
import com.example.smart_edu.R;

import java.util.ArrayList;

public class HV_adapter extends RecyclerView.Adapter<HV_adapter.ViewHolder> {

    Context context;
    ArrayList<Student> arrayListHV;

    public HV_adapter(Context context, ArrayList<Student> arrayListHV) {
        this.context = context;
        this.arrayListHV = arrayListHV;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.qlyhv_adapter,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student hv = arrayListHV.get(position);
        holder.name_hv.setText(hv.getNameHV());
        holder.id_class.setText(hv.getIdClass().toString());
        holder.phone_ph.setText(hv.getPhonePH());
        holder.diachi.setText(hv.getAddressHV());
    }

    @Override
    public int getItemCount() {
        return arrayListHV.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements ActivityCompat.OnRequestPermissionsResultCallback {
        TextView name_hv, id_class, phone_ph, diachi ;
        String message;
        Button diemdanh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_hv = (TextView) itemView.findViewById(R.id.tv_name_HV);
            id_class = (TextView) itemView.findViewById(R.id.tv_admin_hs_lop);
            phone_ph = (TextView) itemView.findViewById(R.id.tv_admin_hs_sdt);
            diachi = itemView.findViewById(R.id.tv_admin_hs_diachi);
            diemdanh = (Button) itemView.findViewById(R.id.bt_vang);
            diemdanh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequesingPermission();
                    sendSMS_by_smsManager(phone_ph.getText().toString());
                }
            });
        }
        private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
        private void RequesingPermission(){
            //checl if the permission is not granted
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.SEND_SMS)){

                }
                else {
                    //a pop up will appear for required permission i.2 allow or deny
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
                }
            }
        }


        private void sendSMS_by_smsManager(String phoneNumber)  {

            message ="Trung tâm tiếng anh Smart Edu xin thông báo: Đã đến giờ học nhưng bạn "
                    + name_hv.getText().toString() +
                    " vẫn chưa đến lớp. Phụ huynh phản hồi lại thông tin với trung tâm khi nhận được thông báo!" ;

            try {
                // Get the default instance of the SmsManager
                SmsManager smsManager = SmsManager.getDefault();
                // Send Message
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);

                //smsManager.sendTextMessage(phoneNumber, null, message, null, null);

                Toast.makeText(context,"Bạn đã gửi tin nhắn thành công!", Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(context,"Lỗi khi gửi tin nhắn đến sđt " + phoneNumber, Toast.LENGTH_LONG).show();
                ex.printStackTrace();
            }

        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            //will check the requestCode
            switch (requestCode){
                case MY_PERMISSIONS_REQUEST_SEND_SMS:
                    //Check whetner the length of grantResult is greater than 0 and is equa; to PERMISSON GRANTED
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(context, "Thanks for permitting!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(context, "Why didn't you permit me idiot!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:break;
            }
        }
    }
}
