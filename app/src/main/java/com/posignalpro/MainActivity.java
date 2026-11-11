package com.posignalpro;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {
    private static final int REQUEST_SCREEN_CAPTURE = 4101;
    private LinearLayout root;
    private TextView status;
    private TextView captureStatus;
    private Button startButton;
    private boolean running = false;
    private boolean capturePermissionGranted = false;

    private final String[] pairs = {
        "GBP/USD OTC", "AUD/CHF OTC", "EUR/USD OTC", "AUD/CAD OTC",
        "EUR/GBP OTC", "GBP/JPY OTC", "USD/JPY OTC", "EUR/JPY OTC",
        "NZD/USD OTC", "USD/CHF OTC"
    };

    private int dp(int v) {
        return (int)(v * getResources().getDisplayMetrics().density + 0.5f);
    }

    private TextView text(String value, int size, int color) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        return t;
    }

    private void addPair(String pair) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(14), dp(12), dp(14), dp(12));
        row.setBackgroundColor(Color.rgb(29, 41, 59));

        TextView name = text(pair, 15, Color.WHITE);
        row.addView(name, new LinearLayout.LayoutParams(0, -2, 1));
        TextView waiting = text("В ОЧЕРЕДИ", 11, Color.rgb(255, 196, 87));
        row.addView(waiting);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.bottomMargin = dp(6);
        root.addView(row, p);
    }

    private void addSection(String value) {
        TextView label = text(value, 14, Color.rgb(160, 180, 205));
        label.setPadding(0, dp(18), 0, dp(10));
        root.addView(label);
    }

    private void requestScreenCapture() {
        MediaProjectionManager manager =
            (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        if (manager == null) {
            status.setText("На этом устройстве недоступен захват экрана.");
            return;
        }
        startActivityForResult(manager.createScreenCaptureIntent(), REQUEST_SCREEN_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SCREEN_CAPTURE) {
            if (resultCode == RESULT_OK && data != null) {
                capturePermissionGranted = true;
                captureStatus.setText("Разрешение Android получено");
                status.setText("Разрешение получено. Захват кадров и распознавание — следующий этап разработки.");
                startButton.setText("ЗАПУСТИТЬ СКАНИРОВАНИЕ (ПОСЛЕ ОБНОВЛЕНИЯ)");
                startButton.setEnabled(false);
            } else {
                capturePermissionGranted = false;
                captureStatus.setText("Разрешение не предоставлено");
                status.setText("Для анализа графика необходимо разрешить захват экрана.");
            }
        }
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(18), dp(24), dp(18), dp(24));
        root.setBackgroundColor(Color.rgb(16, 24, 39));
        scroll.addView(root);

        TextView title = text("PO SIGNAL PRO", 26, Color.WHITE);
        title.setGravity(Gravity.CENTER);
        root.addView(title);

        TextView subtitle = text("ANDROID • BUILD 0.2 • SCREEN MODE", 12, Color.LTGRAY);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, dp(6), 0, dp(16));
        root.addView(subtitle);

        TextView mode = text("РЕЖИМ: ВСЕ OTC-ПАРЫ → ТОЛЬКО СИЛЬНЫЕ СИГНАЛЫ", 13, Color.rgb(110, 220, 150));
        mode.setGravity(Gravity.CENTER);
        mode.setPadding(dp(8), dp(12), dp(8), dp(12));
        mode.setBackgroundColor(Color.rgb(29, 41, 59));
        root.addView(mode);

        addSection("СПИСОК ПАР ДЛЯ БУДУЩЕГО СКАНИРОВАНИЯ");
        for (String pair : pairs) addPair(pair);

        addSection("ДОСТУП К ЭКРАНУ ANDROID");
        captureStatus = text("Разрешение ещё не запрошено", 13, Color.LTGRAY);
        root.addView(captureStatus);

        Button permission = new Button(this);
        permission.setText("РАЗРЕШИТЬ ЗАХВАТ ЭКРАНА");
        root.addView(permission, new LinearLayout.LayoutParams(-1, -2));
        permission.setOnClickListener(v -> requestScreenCapture());

        status = text("Ожидание разрешения Android", 14, Color.WHITE);
        status.setGravity(Gravity.CENTER);
        status.setPadding(0, dp(18), 0, dp(10));
        root.addView(status);

        startButton = new Button(this);
        startButton.setText("СКАНИРОВАНИЕ ПОКА НЕ ПОДКЛЮЧЕНО");
        startButton.setEnabled(false);
        root.addView(startButton, new LinearLayout.LayoutParams(-1, -2));

        TextView note = text(
            "ЧЕСТНЫЙ СТАТУС ВЕРСИИ 0.2: добавлен экран выбора режима и запрос системного разрешения на захват экрана. " +
            "Захват видеокадров, OCR/распознавание свечей, автоматическое переключение пар и алгоритм CALL/PUT ещё НЕ реализованы. " +
            "Список OTC-пар — стартовый список, фактическая доступность зависит от Pocket Option. " +
            "Не используйте макет для реальных сделок.",
            12, Color.rgb(190, 202, 218));
        note.setPadding(0, dp(22), 0, 0);
        root.addView(note);

        setContentView(scroll);
    }
}
