package vn.edu.vhu.ltdd.a4events;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.content.ContextCompat;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Đổi MSSV
    private static final String TAG = "A4_221A310077";

    private EditText edtSoA, edtSoB, edtCanNang, edtChieuCao;
    private TextView tvKetQua, tvBmi, tvPhanLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets bars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            bars.left,
                            bars.top,
                            bars.right,
                            bars.bottom
                    );

                    return insets;
                }
        );

        // ================= ÁNH XẠ VIEW =================

        edtSoA = findViewById(R.id.edtSoA);
        edtSoB = findViewById(R.id.edtSoB);

        tvKetQua = findViewById(R.id.tvKetQua);

        edtCanNang = findViewById(R.id.edtCanNang);
        edtChieuCao = findViewById(R.id.edtChieuCao);

        tvBmi = findViewById(R.id.tvBmi);
        tvPhanLoai = findViewById(R.id.tvPhanLoai);

        Button btnCong = findViewById(R.id.btnCong);
        Button btnTru = findViewById(R.id.btnTru);
        Button btnNhan = findViewById(R.id.btnNhan);
        Button btnChia = findViewById(R.id.btnChia);

        Button btnXoa = findViewById(R.id.btnXoa);
        Button btnTinhBmi = findViewById(R.id.btnTinhBmi);
        Button btnPhanTram = findViewById(R.id.btnPhanTram);
        Button btnDaoDau = findViewById(R.id.btnDaoDau);


        // ================= XỬ LÝ SỰ KIỆN =================

        // Cộng
        btnCong.setOnClickListener(v -> tinhToan('+'));

        // Trừ
        btnTru.setOnClickListener(v -> tinhToan('-'));


        // Nhân + Chia dùng chung listener
        View.OnClickListener chung = v -> {

            int id = v.getId();

            if (id == R.id.btnNhan) {

                tinhToan('*');

            } else if (id == R.id.btnChia) {

                tinhToan('/');

            }
        };

        btnNhan.setOnClickListener(chung);
        btnChia.setOnClickListener(chung);


        // Xóa
        btnXoa.setOnClickListener(v -> xoaTrang());


        // BMI
        btnTinhBmi.setOnClickListener(v -> tinhBmi());
        btnPhanTram.setOnClickListener(v -> tinhPhanTram());

        btnDaoDau.setOnClickListener(v -> daoDau());
    }


    // ==================================================
    // MÁY TÍNH
    // ==================================================

    private void tinhToan(char phepToan) {

        String chuoiA =
                edtSoA.getText().toString().trim();

        String chuoiB =
                edtSoB.getText().toString().trim();


        // ===== BƯỚC 1: KIỂM TRA RỖNG =====

        if (chuoiA.isEmpty()) {

            edtSoA.setError(
                    getString(R.string.err_empty)
            );

            edtSoA.requestFocus();

            return;
        }


        if (chuoiB.isEmpty()) {

            edtSoB.setError(
                    getString(R.string.err_empty)
            );

            edtSoB.requestFocus();

            return;
        }


        // ===== BƯỚC 2: CHUYỂN SANG DOUBLE =====

        double a;
        double b;

        try {

            a = Double.parseDouble(chuoiA);
            b = Double.parseDouble(chuoiB);

        } catch (NumberFormatException e) {

            Log.e(
                    TAG,
                    "Dữ liệu nhập không phải số",
                    e
            );

            Toast.makeText(
                    this,
                    R.string.err_not_number,
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // ===== BƯỚC 3: CHIA CHO 0 =====

        if (phepToan == '/' && b == 0) {

            edtSoB.setError(
                    getString(R.string.err_divide_zero)
            );

            Toast.makeText(
                    this,
                    R.string.err_divide_zero,
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }


        // ===== TÍNH TOÁN =====

        double ketQua;

        switch (phepToan) {

            case '+':
                ketQua = a + b;
                break;

            case '-':
                ketQua = a - b;
                break;

            case '*':
                ketQua = a * b;
                break;

            default:
                ketQua = a / b;
                break;
        }


        // ===== HIỂN THỊ =====

        tvKetQua.setText(
                String.format(
                        Locale.getDefault(),
                        "%.2f %c %.2f = %.2f",
                        a,
                        phepToan,
                        b,
                        ketQua
                )
        );


        Log.d(
                TAG,
                "Phép tính: "
                        + a
                        + " "
                        + phepToan
                        + " "
                        + b
                        + " = "
                        + ketQua
        );
    }


    // ==================================================
    // XÓA
    // ==================================================

    private void xoaTrang() {

        edtSoA.setText("");
        edtSoB.setText("");

        edtSoA.setError(null);
        edtSoB.setError(null);

        tvKetQua.setText(
                R.string.result_placeholder
        );

        edtSoA.requestFocus();
    }


    // ==================================================
    // BMI
    // ==================================================

    private void tinhBmi() {

        try {

            double canNang =
                    Double.parseDouble(
                            edtCanNang
                                    .getText()
                                    .toString()
                                    .trim()
                    );

            double chieuCao =
                    Double.parseDouble(
                            edtChieuCao
                                    .getText()
                                    .toString()
                                    .trim()
                    );


            // Kiểm tra > 0

            if (canNang <= 0 || chieuCao <= 0) {

                Toast.makeText(
                        this,
                        R.string.err_positive,
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }


            // Nếu nhập cm
            // Ví dụ 170 -> 1.70

            if (chieuCao > 3) {

                chieuCao =
                        chieuCao / 100.0;
            }


            // Công thức BMI

            double bmi =
                    canNang
                            / (chieuCao * chieuCao);


            // Hiển thị BMI

            tvBmi.setText(
                    String.format(
                            Locale.getDefault(),
                            "BMI = %.1f",
                            bmi
                    )
            );

            String ketQuaPhanLoai = phanLoai(bmi);

            tvPhanLoai.setText(ketQuaPhanLoai);

// Đổi màu theo phân loại BMI
            if (bmi < 18.5) {

                tvPhanLoai.setTextColor(
                        ContextCompat.getColor(
                                this,
                                R.color.bmi_blue
                        )
                );

            } else if (bmi < 23) {

                tvPhanLoai.setTextColor(
                        ContextCompat.getColor(
                                this,
                                R.color.bmi_green
                        )
                );

            } else if (bmi < 25) {

                tvPhanLoai.setTextColor(
                        ContextCompat.getColor(
                                this,
                                R.color.bmi_yellow
                        )
                );

            } else {

                tvPhanLoai.setTextColor(
                        ContextCompat.getColor(
                                this,
                                R.color.bmi_red
                        )
                );
            }

        } catch (NumberFormatException e) {

            Log.e(
                    TAG,
                    "Lỗi nhập liệu BMI",
                    e
            );

            Toast.makeText(
                    this,
                    R.string.err_not_number,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    // ==================================================
    // PHÂN LOẠI BMI
    // ==================================================

    private String phanLoai(double bmi) {

        if (bmi < 18.5) {

            return getString(
                    R.string.bmi_under
            );

        }

        if (bmi < 23) {

            return getString(
                    R.string.bmi_normal
            );

        }

        if (bmi < 25) {

            return getString(
                    R.string.bmi_over
            );
        }

        return getString(
                R.string.bmi_obese
        );
    }
    // ================= NÂNG CAO NC1 =================

    // Nút %
    private void tinhPhanTram() {

        String chuoiA = edtSoA.getText().toString().trim();

        if (chuoiA.isEmpty()) {
            edtSoA.setError(getString(R.string.err_empty));
            edtSoA.requestFocus();
            return;
        }

        try {

            double a = Double.parseDouble(chuoiA);

            double ketQua = a / 100.0;

            edtSoA.setText(
                    String.format(
                            Locale.getDefault(),
                            "%.2f",
                            ketQua
                    )
            );

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    R.string.err_not_number,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }


    // Nút ±
    private void daoDau() {

        String chuoiA = edtSoA.getText().toString().trim();

        if (chuoiA.isEmpty()) {
            edtSoA.setError(getString(R.string.err_empty));
            edtSoA.requestFocus();
            return;
        }

        try {

            double a = Double.parseDouble(chuoiA);

            a = a * -1;

            edtSoA.setText(
                    String.format(
                            Locale.getDefault(),
                            "%.2f",
                            a
                    )
            );

        } catch (NumberFormatException e) {

            Toast.makeText(
                    this,
                    R.string.err_not_number,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}