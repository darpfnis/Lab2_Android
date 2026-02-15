package com.example.lab2;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.fragment.app.Fragment;

public class FirstFragment extends Fragment {

    private EditText editTextFlower, editTextCustomer,
            editTextEmail, editTextNotes;
    private RadioGroup colorGroup, priceGroup;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        editTextFlower = view.findViewById(R.id.editTextFlower);
        editTextCustomer = view.findViewById(R.id.editTextCustomer);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextNotes = view.findViewById(R.id.editTextNotes);

        colorGroup = view.findViewById(R.id.radioGroupColor);
        priceGroup = view.findViewById(R.id.radioGroupPrice);
        Button buttonOK = view.findViewById(R.id.buttonOK);

        buttonOK.setOnClickListener(v -> validateAndProceed(view));

        return view;
    }

    private void validateAndProceed(View view) {

        String flower = editTextFlower.getText().toString().trim();
        String customer = editTextCustomer.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String notes = editTextNotes.getText().toString().trim();

        if (TextUtils.isEmpty(flower) ||
                TextUtils.isEmpty(customer) ||
                TextUtils.isEmpty(email) ||
                colorGroup.getCheckedRadioButtonId() == -1 ||
                priceGroup.getCheckedRadioButtonId() == -1) {

            Toast.makeText(getContext(),
                    "Please fill in all required fields!",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Invalid email address");
            return;
        }

        RadioButton selectedColor =
                view.findViewById(colorGroup.getCheckedRadioButtonId());

        RadioButton selectedPrice =
                view.findViewById(priceGroup.getCheckedRadioButtonId());

        String result =
                "ORDER SUMMARY\n\n" +
                        "Flower: " + flower + "\n" +
                        "Customer: " + customer + "\n" +
                        "Email: " + email + "\n" +
                        "Color: " + selectedColor.getText() + "\n" +
                        "Package: " + selectedPrice.getText() + "\n\n" +
                        "Notes: " + (notes.isEmpty() ? "None" : notes);

        SecondFragment fragment =
                SecondFragment.newInstance(result);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        editTextFlower.setText("");
        editTextCustomer.setText("");
        editTextEmail.setText("");
        editTextNotes.setText("");
        colorGroup.clearCheck();
        priceGroup.clearCheck();
    }
}
