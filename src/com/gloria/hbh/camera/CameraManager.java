package com.gloria.hbh.camera;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.gloria.hbh.util.ScreenUtils;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.widget.Toast;

final class CameraManager {

	private static CameraManager cameraManager;
	private Camera camera;
	private final Context context;

	private boolean previewing;

	private Handler autoFocusHandler;
	private Handler previewHandler;
	private int previewMessage;

	public static void init(Context context) {
		if (cameraManager == null) {
			cameraManager = new CameraManager(context);
		}
	}

	public static CameraManager get() {
		return cameraManager;
	}

	private CameraManager(Context context) {
		this.context = context;
		camera = null;
		previewing = false;

	}

	@SuppressLint("NewApi")
	public String openDriver(SurfaceHolder holder) throws IOException {
		String result = null;
		if (camera == null) {
			camera = Camera.open();
			camera.setPreviewDisplay(holder);
			camera.setDisplayOrientation(90);

			/**
			 * 在android模拟器设置回出现错误，在真机上可以设置，如果在真机上不设置，在拍照后获取照片会很慢
			 */
			// Camera.Parameters p =camera.getParameters();
			// p.setPreviewFormat(PixelFormat.JPEG);
			// p.setPreviewSize(CameraPreview.ScrrenWidth,
			// CameraPreview.ScrrenHeight);
			// p.setPictureSize(CameraPreview.ScrrenWidth,
			// CameraPreview.ScrrenHeight);
			// camera.setParameters(p);

		}
		return result;
	}

	public void closeDriver() {
		if (camera != null) {
			camera.release();
			camera = null;
		}
	}

	public void startPreview() {
		if (camera != null && !previewing) {
			camera.startPreview();
			previewing = true;
		}
	}

	public void stopPreview() {
		if (camera != null && previewing) {
			// if (!useOneShotPreviewCallback) {
			// camera.setPreviewCallback(null);
			// }

			camera.stopPreview();
			previewHandler = null;
			autoFocusHandler = null;
			previewing = false;
		}
	}

	public void requestPreviewFrame(Handler handler, int message, final CameraPreview instanceCameraPreview) {
		if (camera != null && previewing) {
			previewHandler = handler;
			previewMessage = message;
			camera.autoFocus(new AutoFocusCallback() {
				public void onAutoFocus(boolean success, Camera camera1) {
					// TODO Auto-generated method stub
					// success为true表示对焦成功
					if (success) {
						if (camera1 != null) {
							camera1.takePicture(null, null, jpegCallback);
							Toast.makeText(context, "正在保存照片  请稍后", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(context, "拍照错误,请重新拍摄", Toast.LENGTH_SHORT).show();
							instanceCameraPreview.finish();
						}
					} else {
						Toast.makeText(context, "拍照错误,请重新拍摄", Toast.LENGTH_SHORT).show();
						instanceCameraPreview.finish();
					}
				}
			});
		} else {
			Toast.makeText(context, "拍照错误,请重新拍摄", Toast.LENGTH_SHORT).show();
			instanceCameraPreview.finish();
		}
	}

	public void requestAutoFocus(Handler handler, int message) {
		// if (camera != null && previewing) {
		// autoFocusHandler = handler;
		// autoFocusMessage = message;
		// camera.autoFocus(autoFocusCallback);
		// }
	}

	private ShutterCallback shuuterCallback = new ShutterCallback() {

		public void onShutter() {

		}
	};

	ProgressDialog alertDialog;
	private PictureCallback jpegCallback = new PictureCallback() {

		public void onPictureTaken(byte[] data, Camera camera) {

			if (previewHandler != null) {

				try {

					Bitmap $bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

					int sizew = ScreenUtils.getInstance().getWidth();
					int sizeh = ScreenUtils.getInstance().getHeight();
					float scaleWidth = ((float) sizew) / $bitmap.getWidth();
					float scaleHeight = ((float) sizeh) / $bitmap.getHeight();
					Matrix matrix = new Matrix();
					matrix.postScale(scaleWidth, scaleHeight);
					matrix.setRotate(90);

					Bitmap resizedBitmap = Bitmap.createBitmap($bitmap, 0, 0, $bitmap.getWidth(), $bitmap.getHeight(),
							matrix, true);

					if ($bitmap != null && !$bitmap.isRecycled()) {
						$bitmap.recycle();
					}

					ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
					resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

					byte[] array = out.toByteArray();

					out.flush();
					out.close();

					Message message = previewHandler.obtainMessage(previewMessage, array);
					message.sendToTarget();
					previewHandler = null;

					System.gc();
				} catch (Exception ex) {
				}
			}
		}

	};

	private PictureCallback rawCallback = new PictureCallback() {

		public void onPictureTaken(byte[] data, Camera camera) {

		}

	};

}
