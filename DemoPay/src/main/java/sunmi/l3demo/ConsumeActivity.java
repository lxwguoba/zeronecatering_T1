package sunmi.l3demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import static sunmi.l3demo.R.id.wechat_code_rb;

/**
 * @author xurong on 2017/5/15.
 */

public class ConsumeActivity extends Activity implements View.OnClickListener {

    private EditText amountEdit;

    private RadioButton bankCardRb, aliPayScanRb, weChatScanRb, userOptionalRb, aliPayCodeRb, weChatCodeRb;

    private int paymentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consume);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        final Button okBtn;
        amountEdit = (EditText) findViewById(R.id.input_money_edt);
        bankCardRb = (RadioButton) findViewById(R.id.bank_card_rb);
        aliPayScanRb = (RadioButton) findViewById(R.id.alipay_scan_rb);
        weChatScanRb = (RadioButton) findViewById(R.id.wechat_scan_rb);
        userOptionalRb = (RadioButton) findViewById(R.id.optional_rb);
        aliPayCodeRb = (RadioButton) findViewById(R.id.alipay_code_rb);
        weChatCodeRb = (RadioButton) findViewById(wechat_code_rb);
        okBtn = (Button) findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok_btn:
                setPaymentType();
                Intent intent = new Intent("sunmi.payment.L3");
                String transId = System.currentTimeMillis()+ "";
                intent.putExtra("transId",transId);
                intent.putExtra("transType", 0);
                intent.putExtra("paymentType", paymentType);

                String amount = amountEdit.getText().toString();
                try {
                    intent.putExtra("amount", Long.parseLong(amount));
                } catch (Exception e) {
                    Toast.makeText(this, "消费金额填写错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("appId", getPackageName());
                if (Util.isIntentExisting(intent, this)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "此机器上没有安装L3应用", Toast.LENGTH_SHORT).show();
                }
        }

    }

    private void setPaymentType() {
        if (bankCardRb.isChecked()) {
            paymentType = 0;
        } else if (aliPayScanRb.isChecked()) {
            paymentType = 1;
        } else if (weChatScanRb.isChecked()) {
            paymentType = 3;
        } else if (aliPayCodeRb.isChecked()) {
            paymentType = 2;
        } else if (weChatCodeRb.isChecked()) {
            paymentType = 4;
        } else if (userOptionalRb.isChecked()) {
            paymentType = -1;
        }
    }

}
