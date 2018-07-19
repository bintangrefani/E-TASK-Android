package com.example.lenovo.e_task.SessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.lenovo.e_task.menu.LoginActivity;
import com.example.lenovo.e_task.menu.SidebarActivity;

import java.util.HashMap;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private int MODE_PRIVATE = 0;


    public static final String user = "namauser";
    public static final String isLogin = "isLogin";
    public static final String id_user = "id_user";
    private final String SHARE_NAME = "loginsession";

    //digunakan untuk awal-awal manggil
    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = this.context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    //membuat session,
    public void createSession(String namauser, String id_user){
        editor.putBoolean(isLogin,true);
        editor.putString(user, namauser);
        editor.putString(this.id_user, id_user);
        editor.commit();
    }

    //untuk mengambil data detail usernya
    public HashMap getDetail(){
        HashMap<String,String> map = new HashMap<>();
        map.put(SHARE_NAME,sharedPreferences.getString(SHARE_NAME,null));
        map.put(user,sharedPreferences.getString(user,null));
        map.put(id_user,sharedPreferences.getString(id_user,null));
        return map;
    }

    public void checkLogin(){
        if (!this.isLoggedin()){
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }else {
            Intent i = new Intent(context, SidebarActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public boolean isLoggedin(){
        return sharedPreferences.getBoolean(isLogin,false);
    }
    public void logout(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
