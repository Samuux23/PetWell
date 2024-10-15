package com.example.tutorial01;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private List<Item> itemList;
    private List<Item> cartItemList;
    private TextView cartCount;
    private int cartItemCount = 0;

    public ShopFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_shop_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        cartCount = view.findViewById(R.id.cart_count);
        ImageView cartIcon = view.findViewById(R.id.cart_icon);

        itemList = new ArrayList<>();
        cartItemList = new ArrayList<>();
        // Add your items to the list
        itemList.add(new Item(R.drawable.item_image_1, "Classic Pet - Adult", "$32"));
        itemList.add(new Item(R.drawable.item_image_2, "Lamb and Rice 20kg", "$115"));
        itemList.add(new Item(R.drawable.item_image_3, "Pedigree Chicken & Vegetables", "$20"));
        itemList.add(new Item(R.drawable.item_image_4, "Pedigree Chicken CIS Pouch 80g", "$15"));
        itemList.add(new Item(R.drawable.item_image_5, "Puppy Dog Food Chicken and Egg 1.2Kg", "$10"));
        itemList.add(new Item(R.drawable.item_image_6, "NexGard Flea & Tick Treatment", "$25"));
        // Add more items as needed

        adapter = new CartAdapter(itemList, new CartAdapter.OnItemClickListener() {
            @Override
            public void onAddToCartClicked(Item item) {
                cartItemCount++;
                cartCount.setText(String.valueOf(cartItemCount));
                cartItemList.add(item);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(adapter);

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItemList.isEmpty()) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Cart is empty")
                            .setMessage("Please add items to the cart before proceeding.")
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                } else {
                    // Open CartFragment and pass cart items
                    CartFragment cartFragment = new CartFragment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("cartItems", new ArrayList<>(cartItemList));
                    cartFragment.setArguments(bundle);

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, cartFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }

    public static class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

        private List<Item> itemList;
        private OnItemClickListener listener;

        public interface OnItemClickListener {
            void onAddToCartClicked(Item item);
        }

        public CartAdapter(List<Item> itemList, OnItemClickListener listener) {
            this.itemList = itemList;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Item item = itemList.get(position);
            holder.itemImage.setImageResource(item.getImageRes());
            holder.itemTitle.setText(item.getTitle());
            holder.itemPrice.setText(item.getPrice());

            holder.addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAddToCartClicked(item);
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            ImageView itemImage;
            TextView itemTitle;
            TextView itemPrice;
            ImageButton addToCart;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                itemImage = itemView.findViewById(R.id.item_image);
                itemTitle = itemView.findViewById(R.id.item_title);
                itemPrice = itemView.findViewById(R.id.item_price);
                addToCart = itemView.findViewById(R.id.add_to_cart);
            }
        }
    }
}
