package com.example.tutorial01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartFragment extends Fragment {

    private RecyclerView cartRecyclerView;
    private TextView totalAmount;
    private Button makePaymentButton;
    private List<Item> cartItems;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        cartRecyclerView = view.findViewById(R.id.cart_recycler_view);
        totalAmount = view.findViewById(R.id.total_amount);
        makePaymentButton = view.findViewById(R.id.make_payment_button);

        // Retrieve the cart items and display them
        cartItems = getArguments().getParcelableArrayList("cartItems");
        CartAdapter adapter = new CartAdapter(cartItems);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        cartRecyclerView.setAdapter(adapter);

        // Calculate and display the total amount
        updateTotalAmount();

        makePaymentButton.setOnClickListener(v -> {
            // Navigate to CheckoutFragment
            CheckoutFragment checkoutFragment = new CheckoutFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, checkoutFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    private void updateTotalAmount() {
        int total = 0;
        for (Item item : cartItems) {
            total += item.getQuantity() * Integer.parseInt(item.getPrice().replace("$", ""));
        }
        totalAmount.setText("Total: $" + total);
    }

    public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

        private List<Item> cartItems;

        public CartAdapter(List<Item> cartItems) {
            this.cartItems = cartItems;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Item item = cartItems.get(position);
            holder.itemImage.setImageResource(item.getImageRes());
            holder.itemTitle.setText(item.getTitle());
            holder.itemPrice.setText(item.getPrice());

            holder.itemQuantity.setMinValue(1);
            holder.itemQuantity.setMaxValue(10);
            holder.itemQuantity.setValue(item.getQuantity());

            holder.itemQuantity.setOnValueChangedListener((picker, oldVal, newVal) -> {
                item.setQuantity(newVal);
                updateTotalAmount();
            });
        }

        @Override
        public int getItemCount() {
            return cartItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView itemImage;
            TextView itemTitle;
            TextView itemPrice;
            NumberPicker itemQuantity;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemImage = itemView.findViewById(R.id.item_image);
                itemTitle = itemView.findViewById(R.id.item_title);
                itemPrice = itemView.findViewById(R.id.item_price);
                itemQuantity = itemView.findViewById(R.id.item_quantity);
            }
        }
    }
}
