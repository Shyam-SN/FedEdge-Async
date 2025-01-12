package com.fededge.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.fededge.engine.NativeTrainer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView statusText;
    private Button startButton;
    private NativeTrainer trainer;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        startButton = findViewById(R.id.startButton);
        
        // Initialize Native Engine
        trainer = new NativeTrainer();
        long handle = trainer.initEngine("cpu");

        startButton.setOnClickListener(v -> {
            // PHASE 5: Resource-Aware Shell check
            if (!checkResourcesSafe()) {
                statusText.setText("Training paused: Resources too low (Battery/Thermal)");
                return;
            }

            startButton.setEnabled(false);
            statusText.setText("Training started...");
            
            executor.execute(() -> {
                // Run training epoch
                float loss = trainer.trainEpoch(handle, 1, 10);
                
                // Get Delta
                byte[] payload = trainer.getUpdatePayload(handle);
                
                // Switch back to UI thread
                runOnUiThread(() -> {
                    statusText.setText("Training complete! Loss: " + loss + "\nPayload size: " + (payload != null ? payload.length : 0) + " bytes.");
                    startButton.setEnabled(true);
                });
                
                // Submit via mock gRPC client
                if (payload != null) {
                    com.fededge.app.GrpcClientService.submitUpdate(payload);
                }
            });
        });
    }

    private boolean checkResourcesSafe() {
        // In a real app, query BatteryManager and ThermalManager
        int mockBatteryLevel = 45; 
        boolean isThermalSevere = false;
        
        if (mockBatteryLevel < 20) return false;
        if (isThermalSevere) return false;
        
        return true;
    }
}
