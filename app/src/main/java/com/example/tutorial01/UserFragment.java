package com.example.tutorial01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserFragment extends Fragment {

    private EditText editTextNewName, editTextNewPassword;
    private Button buttonUpdate, buttonLogout;
    private DatabaseReference databaseReference;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_user_fragment, container, false);

        editTextNewName = view.findViewById(R.id.editTextNewName);
        editTextNewPassword = view.findViewById(R.id.editTextNewPassword);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonLogout = view.findViewById(R.id.buttonLogout);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        return view;
    }

    private void updateUserInfo() {
        String newName = editTextNewName.getText().toString().trim();
        String newPassword = editTextNewPassword.getText().toString().trim();

        if (newName.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter both name and password", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = getUserId(); // Retrieve the current user ID from shared preferences

        databaseReference.child(userId).child("name").setValue(newName);
        databaseReference.child(userId).child("password").setValue(newPassword);

        Toast.makeText(getActivity(), "User info updated", Toast.LENGTH_SHORT).show();
    }

    private void logoutUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Clear the user data
        editor.apply();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish(); // Close the current activity
    }

    // Retrieve the user ID from shared preferences
    private String getUserId() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("userId", "defaultUserId");
    }
}
