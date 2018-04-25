package sunmi.l3demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author by 47673 on 2017/5/15.
 */
public class CustomConsumeActivity extends Activity implements View.OnClickListener {
    private EditText transTypeEdit, paymentTypeEdit, amountEdit, transID, oriVoucherNoEdit, oriDateEdit, oriReferenceNoEdit, QROrderNoEdit, appIDEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_consume);
        transTypeEdit = (EditText) findViewById(R.id.input_trans_type_edt);
        paymentTypeEdit = (EditText) findViewById(R.id.input_payment_type_edt);
        amountEdit = (EditText) findViewById(R.id.input_money_edt);
        transID = (EditText) findViewById(R.id.input_trans_id);
        oriVoucherNoEdit = (EditText) findViewById(R.id.input_ori_voucher_no);
        oriDateEdit = (EditText) findViewById(R.id.ori_date_edt);
        oriReferenceNoEdit = (EditText) findViewById(R.id.ori_reference_no_edt);
        QROrderNoEdit = (EditText) findViewById(R.id.ori_QROrderNo_edt);
        appIDEdit = (EditText) findViewById(R.id.appId);
        appIDEdit.setText(getPackageName());
        Button ok = (Button) findViewById(R.id.ok_btn);
        ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_btn:
                try {
                    Intent intent = new Intent("sunmi.payment.L3");
                    String transId = System.currentTimeMillis()+ "";
                    intent.putExtra("transId",transId);
                    try {
                        if (!TextUtils.isEmpty(transTypeEdit.getText().toString())) {
                            intent.putExtra("transType", Integer.parseInt(String.valueOf(transTypeEdit.getText().toString())));
                        }
                        if (!TextUtils.isEmpty(paymentTypeEdit.getText().toString())) {
                            intent.putExtra("paymentType", Integer.parseInt(String.valueOf(paymentTypeEdit.getText().toString())));
                        }
                        if (!TextUtils.isEmpty(amountEdit.getText().toString())) {
                            intent.putExtra("amount", Long.parseLong(amountEdit.getText().toString()));
                        }
                        if (!TextUtils.isEmpty(transID.getText().toString())) {
                            intent.putExtra("transId", Long.parseLong(transID.getText().toString()));
                        }
                        if (!TextUtils.isEmpty(oriVoucherNoEdit.getText().toString())) {
                            intent.putExtra("oriVoucherNo", oriVoucherNoEdit.getText().toString());
                        }
                        if (!TextUtils.isEmpty(oriDateEdit.getText().toString())) {
                            intent.putExtra("oriTransDate", oriDateEdit.getText().toString());
                        }
                        if (!TextUtils.isEmpty(oriReferenceNoEdit.getText().toString())) {
                            intent.putExtra("oriReferenceNo", oriReferenceNoEdit.getText().toString());//oriReferenceNo不能为"",否则交易失败
                        }
                        if (!TextUtils.isEmpty(QROrderNoEdit.getText().toString())) {
                            intent.putExtra("oriQROrderNo", QROrderNoEdit.getText().toString());//oriReferenceNo不能为"",否则交易失败
                        }
                        if (!TextUtils.isEmpty(appIDEdit.getText().toString())) {
                            intent.putExtra("appId", appIDEdit.getText().toString());
                        }
                    } catch (Exception e) {
                        Toast.makeText(this, "输入错误", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (Util.isIntentExisting(intent, this)) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "此机器上没有安装L3应用", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception exception) {
                    Toast.makeText(this, "输入错误", Toast.LENGTH_LONG).show();
                }

                break;
        }
    }
}
