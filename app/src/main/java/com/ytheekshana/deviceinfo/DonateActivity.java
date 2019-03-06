package com.ytheekshana.deviceinfo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DonateActivity extends AppCompatActivity implements PurchasesUpdatedListener {
    private BillingClient mBillingClient;
    TextView txt_coffee_price, txt_lunch_price, txt_sandwich_price, txt_huge_price;
    Button btn_coffee, btn_sandwich, btn_lunch, btn_huge;
    SkuDetailsParams.Builder skuDetailsParams;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        int themeId = sharedPrefs.getInt("ThemeBar", R.style.AppTheme);
        int themeColor = sharedPrefs.getInt("accent_color_dialog", Color.parseColor("#2196f3"));
        int themeColorDark = GetDetails.getDarkColor(this, themeColor);
        setTheme(themeId);

        if (sharedPrefs.getInt("ThemeBar", 0) != R.style.AppThemeDark) {
            Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(themeColor));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getWindow().setStatusBarColor(themeColorDark);
        }
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
        ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(getString(R.string.app_name), icon, themeColor);
        setTaskDescription(taskDescription);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        activity = this;

        txt_coffee_price = findViewById(R.id.txt_coffee_price);
        txt_lunch_price = findViewById(R.id.txt_lunch_price);
        txt_sandwich_price = findViewById(R.id.txt_sandwich_price);
        txt_huge_price = findViewById(R.id.txt_huge_price);
        btn_coffee = findViewById(R.id.btn_coffee);
        btn_sandwich = findViewById(R.id.btn_sandwich);
        btn_lunch = findViewById(R.id.btn_lunch);
        btn_huge = findViewById(R.id.btn_huge);

        try {
            mBillingClient = BillingClient.newBuilder(this).setListener(this).build();
            mBillingClient.startConnection(new BillingClientStateListener() {
                @Override
                public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                    if (billingResponseCode == BillingClient.BillingResponse.OK) {

                        List<String> skuList = new ArrayList<>();
                        skuList.add("donate_coffee");
                        skuList.add("donate_lunch");
                        skuList.add("donate_sandwich");
                        skuList.add("donate_huge");
                        skuDetailsParams = SkuDetailsParams.newBuilder();
                        skuDetailsParams.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                        mBillingClient.querySkuDetailsAsync(skuDetailsParams.build(), (responseCode, skuDetailsList) -> {
                            if (responseCode == BillingClient.BillingResponse.OK && skuDetailsList != null) {
                                for (SkuDetails skuDetails : skuDetailsList) {
                                    String sku = skuDetails.getSku();
                                    String price = skuDetails.getPrice();
                                    if ("donate_coffee".equals(sku)) {
                                        txt_coffee_price.setText(price);
                                    } else if ("donate_lunch".equals(sku)) {
                                        txt_lunch_price.setText(price);
                                    } else if ("donate_sandwich".equals(sku)) {
                                        txt_sandwich_price.setText(price);
                                    } else if ("donate_huge".equals(sku)) {
                                        txt_huge_price.setText(price);
                                    }
                                }

                                btn_coffee.setOnClickListener(view -> {
                                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                            .setSkuDetails(skuDetailsList.get(0))
                                            .build();
                                    mBillingClient.launchBillingFlow(activity, flowParams);
                                });

                                btn_huge.setOnClickListener(view -> {
                                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                            .setSkuDetails(skuDetailsList.get(1))
                                            .build();
                                    mBillingClient.launchBillingFlow(activity, flowParams);
                                });

                                btn_lunch.setOnClickListener(view -> {
                                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                            .setSkuDetails(skuDetailsList.get(2))
                                            .build();
                                    mBillingClient.launchBillingFlow(activity, flowParams);
                                });

                                btn_sandwich.setOnClickListener(view -> {
                                    BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                                            .setSkuDetails(skuDetailsList.get(3))
                                            .build();
                                    mBillingClient.launchBillingFlow(activity, flowParams);
                                });
                            }
                        });
                    }
                }

                @Override
                public void onBillingServiceDisconnected() {
                    Toast.makeText(activity, "Check your connection", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        try {
            if (responseCode == BillingClient.BillingResponse.OK && purchases != null) {
                for (Purchase purchase : purchases) {
                    mBillingClient.consumeAsync(purchase.getPurchaseToken(), (responseCode1, purchaseToken) -> Toast.makeText(activity, "Thanks for the donation", Toast.LENGTH_SHORT).show());
                }
            } else if (responseCode == BillingClient.BillingResponse.USER_CANCELED) {
                Toast.makeText(activity, "Purchase Cancelled", Toast.LENGTH_SHORT).show();
            } else if (responseCode == BillingClient.BillingResponse.ITEM_ALREADY_OWNED) {
                Toast.makeText(activity, "Already Purchased", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(activity, "Please try again later", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
