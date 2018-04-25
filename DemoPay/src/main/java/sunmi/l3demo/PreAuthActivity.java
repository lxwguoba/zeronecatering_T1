package sunmi.l3demo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

/**
 * Created by xurong on 2017/5/15.
 */

public class PreAuthActivity extends Activity implements View.OnClickListener {
    private RadioButton preauth_rb;
    private RadioButton preauth_revoke_rb;
    private RadioButton preauth_complete_rb;
    private RadioButton preauth_complete_revoke_rb;
    private EditText input_ori_voucher_no_edt;
    private EditText input_money_edt;
    private Button ok_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preauth);
        preauth_rb = (RadioButton) findViewById(R.id.preauth_rb);
        preauth_revoke_rb = (RadioButton) findViewById(R.id.preauth_revoke_rb);
        preauth_complete_rb = (RadioButton) findViewById(R.id.preauth_complete_rb);
        preauth_complete_revoke_rb = (RadioButton) findViewById(R.id.preauth_complete_revoke_rb);
        input_ori_voucher_no_edt = (EditText) findViewById(R.id.input_ori_voucher_no_edt);
        input_money_edt = (EditText) findViewById(R.id.input_money_edt);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(this);
    }


    private int getTransType() {
        int transType;
        if (preauth_rb.isChecked()) {
            transType = 3;
        } else if (preauth_revoke_rb.isChecked()) {
            transType = 4;
        } else if (preauth_complete_rb.isChecked()) {
            transType = 5;
        } else {
            transType = 6;
        }
        return transType;
    }

    @Override
    public void onClick(View v) {
        int transType = getTransType();
        String voucherNo = "";
        String amount = "";
        if (transType != 3) {
            //预授权没有原凭证号
            voucherNo = input_ori_voucher_no_edt.getText().toString();
            if (TextUtils.isEmpty(voucherNo)) {
                Toast.makeText(getBaseContext(), getString(R.string.please_input_voucher_no), Toast.LENGTH_SHORT).show();
                return;
            }
        }

        long _amount = 0;
        try {
            //预授权撤销和预授权完成撤销没有金额
            amount = input_money_edt.getText().toString();
            _amount = Long.valueOf(amount);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), getString(R.string.please_input_amount), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent("sunmi.payment.L3");
        String transId = System.currentTimeMillis()+ "";
        intent.putExtra("transId",transId);
        intent.putExtra("transType", transType);
        intent.putExtra("amount", _amount);
        intent.putExtra("oriVoucherNo", voucherNo);
        intent.putExtra("appId", getPackageName());

        if (isIntentExisting(intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "此机器上没有安装L3应用", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isIntentExisting(Intent intent) {
        final PackageManager packageManager = getPackageManager();
        List<ResolveInfo> resolveInfo =
                packageManager.queryIntentActivities(intent,
                        PackageManager.MATCH_DEFAULT_ONLY);
        if (resolveInfo.size() > 0) {
            return true;
        }
        return false;
    }
}
