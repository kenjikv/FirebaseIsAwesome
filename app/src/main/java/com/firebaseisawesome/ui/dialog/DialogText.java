package com.firebaseisawesome.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebaseisawesome.R;


/**
 * Created by Kenji Kawaida on 2019-07-22.
 */
public class DialogText extends Dialog {

    private Context context;

    private TextView title, description;
    private EditText editText;
    private Button btnSave;

    private OnDialogFinish onDialogFinish;

    public DialogText(Context context, final OnDialogFinish onDialogFinish) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_text);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        this.context = context;
        this.onDialogFinish = onDialogFinish;

        this.title = findViewById(R.id.title);
        this.description = findViewById(R.id.description);
        this.editText = findViewById(R.id.edittext);
        this.btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString().trim();
                onDialogFinish.onDialogFinish(text);
                DialogText.this.dismiss();
            }
        });
    }

    public void setTitle(String text){
        title.setText(text);
    }

    public void setDescription(String text){
        if(text.trim().isEmpty()){
            return;
        }
        description.setText(text);
        description.setVisibility(View.VISIBLE);
    }

    public void setTextHint(String text){
        editText.setHint(text);
    }

    public void setTextButton(String text){
        btnSave.setText(text);
    }

    public void setTextInputType(int inputType){
        editText.setInputType(inputType);
    }

    public void setText(String text){
        editText.setText(text);
    }

    public interface OnDialogFinish{
        public void onDialogFinish(String text);
    }
}
