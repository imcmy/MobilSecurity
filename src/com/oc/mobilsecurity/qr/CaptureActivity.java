package com.oc.mobilsecurity.qr;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.oc.mobilsecurity.R;
import com.oc.mobilsecurity.qr.camera.CameraManager;
import com.oc.mobilsecurity.qr.control.AmbientLightManager;
import com.oc.mobilsecurity.qr.control.BeepManager;
import com.oc.mobilsecurity.qr.decode.CaptureActivityHandler;
import com.oc.mobilsecurity.qr.decode.FinishListener;
import com.oc.mobilsecurity.qr.decode.InactivityTimer;
import com.oc.mobilsecurity.qr.view.ViewfinderView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public final class CaptureActivity extends Activity implements
		SurfaceHolder.Callback {
	private CameraManager cameraManager;
	private CaptureActivityHandler handler;
	private Result savedResultToShow;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Collection<BarcodeFormat> decodeFormats;
	private Map<DecodeHintType, ?> decodeHints;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private BeepManager beepManager;
	private AmbientLightManager ambientLightManager;

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public CameraManager getCameraManager() {
		return cameraManager;
	}

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);

		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.activity_capture);

		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		beepManager = new BeepManager(this);
		ambientLightManager = new AmbientLightManager(this);
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();

		cameraManager = new CameraManager(getApplication());

		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		viewfinderView.setCameraManager(cameraManager);

		handler = null;
		resetStatusView();
		
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}

		beepManager.updatePrefs();
		ambientLightManager.start(cameraManager);
		inactivityTimer.onResume();
		
		decodeFormats = null;
		characterSet = null;
	}

	@Override
	protected void onPause() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		inactivityTimer.onPause();
		ambientLightManager.stop();
		cameraManager.closeDriver();
		if (!hasSurface) {
			SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surfaceHolder.removeCallback(this);
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_FOCUS:
		case KeyEvent.KEYCODE_CAMERA:
			return true;
		case KeyEvent.KEYCODE_BACK:
	        Intent intent = new Intent();
	        setResult(RESULT_CANCELED, intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
		if (handler == null) {
			savedResultToShow = result;
		} else {
			if (result != null) {
				savedResultToShow = result;
			}
			if (savedResultToShow != null) {
				Message message = Message.obtain(handler,
						R.id.decode_succeeded, savedResultToShow);
				handler.sendMessage(message);
			}
			savedResultToShow = null;
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	/** 结果处理 */
	public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
		Log.v("tag", "SCANNING");
		inactivityTimer.onActivity();
		beepManager.playBeepSoundAndVibrate();
		
		String scanResult = rawResult.getText();
		if (scanResult == null || "".equals(scanResult)) {
			scanResult = "SCAN ERROR";
		}
		
		Intent intent = getIntent();
		Bundle bundle = new Bundle();
		bundle.putString("scanResult", scanResult);
		intent.putExtras(bundle);
		setResult(RESULT_OK, intent);
		finish();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		if (surfaceHolder == null) {
			throw new IllegalStateException("No SurfaceHolder provided");
		}
		if (cameraManager.isOpen()) {
			return;
		}
		try {
			cameraManager.openDriver(surfaceHolder);
			if (handler == null) {
				handler = new CaptureActivityHandler(this, decodeFormats,
						decodeHints, characterSet, cameraManager);
			}
			decodeOrStoreSavedBitmap(null, null);
		} catch (IOException ioe) {
			displayFrameworkBugMessageAndExit();
		} catch (RuntimeException e) {
			displayFrameworkBugMessageAndExit();
		}
	}

	private void displayFrameworkBugMessageAndExit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("警告");
		builder.setMessage("抱歉，相机出现问题，您可能需要重启设备");
		builder.setPositiveButton("确定", new FinishListener(this));
		builder.setOnCancelListener(new FinishListener(this));
		builder.show();
	}

	public void restartPreviewAfterDelay(long delayMS) {
		if (handler != null) {
			handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
		}
		resetStatusView();
	}

	private void resetStatusView() {
		viewfinderView.setVisibility(View.VISIBLE);
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}
}
