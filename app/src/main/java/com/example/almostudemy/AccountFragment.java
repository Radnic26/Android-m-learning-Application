package com.example.almostudemy;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {
    public Button btnLogOut;
    public Button btnDeleteAccount, btnGenerate1, btnGenerate2, btnChangePicture;
    public View view;
    public ImageView profilePic;
    public DatabaseHelper db;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account, container, false);
        db = new DatabaseHelper(getActivity());
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnDeleteAccount = view.findViewById(R.id.btnDeleteAccount);
        btnGenerate1 = view.findViewById(R.id.btnGenerateReport1);
        btnGenerate2 = view.findViewById(R.id.btnGenerateReport2);
        btnChangePicture = view.findViewById(R.id.btnChangeProfilePic);
        profilePic = view.findViewById(R.id.profilePic);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                Intent homeToLogin = new Intent(getContext(), MainActivity.class);
                startActivity(homeToLogin);
                getActivity().finish();
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("checkbox", MODE_PRIVATE);
                String username = preferences.getString("username", "");
                String password = preferences.getString("password", "");
                int row = db.deleteStudent(username, password);

                if(row > 0) {
                    Toast.makeText(getContext(), "Account Deleted", Toast.LENGTH_SHORT).show();
                    Intent homeToLogin = new Intent(getContext(), MainActivity.class);
                    startActivity(homeToLogin);
                    getActivity().finish();
                }
                else {
                    Toast.makeText(getContext(), "Error Deleting Account", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnGenerate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountToReport = new Intent(getContext(), FirstGeneratedReport.class);
                startActivity(accountToReport);
            }
        });

        btnGenerate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent accountToReport = new Intent(getContext(), SecondGeneratedReport.class);
                startActivity(accountToReport);
            }
        });

        btnChangePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = { Manifest.permission.READ_EXTERNAL_STORAGE };
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        pickImageFromGallery();
                    }
                }
                else {
                    pickImageFromGallery();
                }
            }
        });

        return view;
    }

    public void pickImageFromGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK);
        pickImage.setType("image/*");
        startActivityForResult(pickImage, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if(grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(getContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            profilePic.setImageURI(data.getData());
        }
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        outState.putInt();
//        super.onSaveInstanceState(outState);
//    }
}
