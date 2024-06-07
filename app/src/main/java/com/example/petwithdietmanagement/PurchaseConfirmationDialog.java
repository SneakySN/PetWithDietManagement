package com.example.petwithdietmanagement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PurchaseConfirmationDialog extends Dialog {

    private String message;
    private PurchaseConfirmationDialogListener listener;

    public PurchaseConfirmationDialog(Context context, String message, PurchaseConfirmationDialogListener listener) {
        super(context);
        this.message = message;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_purchase_confirmation);

        TextView dialogMessage = findViewById(R.id.dialog_message);
        dialogMessage.setText(message);

        Button buttonConfirm = findViewById(R.id.button_confirm);
        Button buttonCancel = findViewById(R.id.button_cancel);

        // 다이얼로그의 배경을 투명하게 만듭니다.
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onConfirm();
                dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
                dismiss();
            }
        });
    }

    public interface PurchaseConfirmationDialogListener {
        void onConfirm();
        void onCancel();
    }
}
