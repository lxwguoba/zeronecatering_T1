package sunmi.l3demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import static sunmi.l3demo.R.id.wechat_code_rb;

/**
 * @author by xurong on 2017/5/15.
 */

public class ReturnGoodsActivity extends Activity implements View.OnClickListener {
    private EditText oriReferenceNoEdt, oriDateEdt, moneyEdt;
    private RadioButton bankCardRb, alipayScanRb, wechatScanRb, userOptionalRb, alipayCodeRb, wechatCodeRb;
    private int paymentType;
    private Button okBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        moneyEdt = (EditText) findViewById(R.id.input_money_edt);
        oriDateEdt = (EditText) findViewById(R.id.ori_date_edt);
        oriReferenceNoEdt = (EditText) findViewById(R.id.ori_reference_no_edt);
        bankCardRb = (RadioButton) findViewById(R.id.bank_card_rb);
        alipayScanRb = (RadioButton) findViewById(R.id.alipay_scan_rb);
        wechatScanRb = (RadioButton) findViewById(R.id.wechat_scan_rb);
        userOptionalRb = (RadioButton) findViewById(R.id.optional_rb);
        alipayCodeRb = (RadioButton) findViewById(R.id.alipay_code_rb);
        wechatCodeRb = (RadioButton) findViewById(wechat_code_rb);
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
                intent.putExtra("transType", 2);
                intent.putExtra("paymentType", paymentType);
                try {
                    intent.putExtra("amount", Long.parseLong(moneyEdt.getText().toString()));
                }catch (Exception e) {
                    if(paymentType == 0) {
                        Toast.makeText(this, "消费金额填写错误", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                intent.putExtra("appId", getPackageName());
                if (!TextUtils.isEmpty(oriReferenceNoEdt.getText().toString())) {
                   if(paymentType == 1 || paymentType == 2 ||paymentType == 3 || paymentType == 4){
                        intent.putExtra("oriQROrderNo", oriReferenceNoEdt.getText().toString());
                    } else {
                        intent.putExtra("oriReferenceNo", oriReferenceNoEdt.getText().toString());//oriReferenceNo不能为"",否则交易失败
                    }
                }else{
                    if(paymentType == 0) {
                        Toast.makeText(this, "原交易参考号不能为空", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
                if (!TextUtils.isEmpty(oriDateEdt.getText().toString())) {
                    intent.putExtra("oriTransDate", oriDateEdt.getText().toString());
                }
                if (Util.isIntentExisting(intent, this)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "此机器上没有安装L3应用", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void checkEdit() {
        if (!TextUtils.isEmpty(oriReferenceNoEdt.getText()) && !TextUtils.isEmpty(moneyEdt.getText())) {
            okBtn.setEnabled(true);
        }
    }


    private void setPaymentType() {
        if (bankCardRb.isChecked()) {
            paymentType = 0;
        } else if (alipayScanRb.isChecked()) {
            paymentType = 1;
        } else if (alipayCodeRb.isChecked()) {
            paymentType = 2;
        } else if (wechatScanRb.isChecked()) {
            paymentType = 3;
        } else if (wechatCodeRb.isChecked()) {
            paymentType = 4;
        } else if (userOptionalRb.isChecked()) {
            paymentType = -1;
        }
    }
}
