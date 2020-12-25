package com.israf.makesurvey.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.israf.makesurvey.MainActivity;
import com.israf.makesurvey.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class MakeSurvey extends Fragment {
    private Button buttonadd, buttonsave,save;
    private final ArrayList<SurveyAnswer> Survey = new ArrayList<SurveyAnswer>();
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    private EditText SurveyName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.fragment_make_survey, container, false);
        buttonadd = (Button) V.findViewById(R.id.button1);
        buttonsave = V.findViewById(R.id.buttonSave);
        auth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(getActivity(), buttonadd);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.Selected) {
                            addSelected();
                        } else if (item.getItemId() == R.id.clasic) {
                            addClassic();
                        } else if (item.getItemId() == R.id.multiple) {
                            addMultiple();
                        }


                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method

        buttonsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              onButtonShowPopupWindowClick(inflater ,V);
             //mDatabase.child("Survey").child("Questions").setValue(Survey);

            }
        });
        return V;
    }

    public void addSelected() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.add(R.id.addsurvey, new Selected());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();


    }

    private void addMultiple() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.add(R.id.addsurvey, new Multiple());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();
    }


    private void addClassic() {
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
        ft.add(R.id.addsurvey, new Classic());
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
        ft.commit();
    }

    @Subscribe
    public void onEvent(SurveyAnswer s) {
        if (Survey.contains(s)) {
            Survey.remove(s);
        } else Survey.add(s);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }
    public void onButtonShowPopupWindowClick(LayoutInflater inflater ,View view) {

        // inflate the layout of the popup window

        View popupView = inflater.inflate(R.layout.popup_window, null);
         SurveyName=popupView.findViewById(R.id.editsave);
         save=popupView.findViewById(R.id.save);
        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Surveys").child(auth.getCurrentUser().getUid()).child(SurveyName.getText().toString()).child("Questions").setValue(Survey);
                // Create new fragment and transaction
                deneme(v);
                Intent intent = new Intent(getContext(), MainActivity.class);

                // startActivity metoduna yazdığımız intent'i veriyoruz Bu şekilde diğer activity'ye geçeceğiz.
                startActivity(intent);

            }
        });
        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
    public static final String APP_SCRIPT_WEB_APP_URL = "https://script.google.com/macros/s/AKfycbycd3bzRnJCnOQpYN2vbkIi92KURottGxQm79NIBJQ7vadN3kM/exec";
    public static final String ADD_USER_URL = APP_SCRIPT_WEB_APP_URL;
    public static final String LIST_USER_URL = APP_SCRIPT_WEB_APP_URL+"?action=readAll";

    public static final String KEY_ID = "uId";
    public static final String KEY_NAME = "uName";
    public static final String KEY_IMAGE = "uImage";
    public  static final String KEY_ACTION = "action";
    private  void  deneme (View v){
        final ProgressDialog loading = ProgressDialog.show(v.getContext(),"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,ADD_USER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                      String[] words = response.split(" ");
                      ArrayList<String> a=new ArrayList<>();
                      a.add(words[0]);
                      a.add(words[2]);
                        mDatabase.child("SurveyLink").child(SurveyName.getText().toString()).setValue(a);
                        Toast.makeText(v.getContext(),response,Toast.LENGTH_LONG).show();
                        Log.i("sedat",response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("sedat",error.getLocalizedMessage());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("SurveyName",SurveyName.getText().toString());
                String data = new Gson().toJson(Survey);
                params.put("Survey", data);
                return params;
            }

        };

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);


        RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());

        requestQueue.add(stringRequest);
    }


}