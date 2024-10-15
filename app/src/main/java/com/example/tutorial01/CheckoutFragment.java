package com.example.tutorial01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class CheckoutFragment extends Fragment {

    private EditText cardNumber;
    private EditText cvcNumber;
    private EditText cardExpiryMonth;
    private EditText cardExpiryYear;
    private Button confirmPaymentButton;

    public CheckoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);

        cardNumber = view.findViewById(R.id.card_number);
        cvcNumber = view.findViewById(R.id.cvc_number);
        cardExpiryMonth = view.findViewById(R.id.card_expiry_month);
        cardExpiryYear = view.findViewById(R.id.card_expiry_year);
        confirmPaymentButton = view.findViewById(R.id.confirm_payment_button);

        confirmPaymentButton.setOnClickListener(v -> {
            String cardNum = cardNumber.getText().toString();
            String cvc = cvcNumber.getText().toString();
            String expiryMonth = cardExpiryMonth.getText().toString();
            String expiryYear = cardExpiryYear.getText().toString();

            if (cardNum.isEmpty() || cvc.isEmpty() || expiryMonth.isEmpty() || expiryYear.isEmpty()) {
                Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Payment Successful", Toast.LENGTH_SHORT).show();
                // Navigate to PaymentDoneFragment
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, new PaymentDoneFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
