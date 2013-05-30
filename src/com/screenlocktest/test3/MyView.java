package com.screenlocktest.test3;

import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.screenlocktest3.R;

public class MyView extends View {
	private Paint paint;
	private Camera camera;
	private String time = "21:30", csdanddate = "2013/5/8       多云   30℃";
	private int mode = -1, view = 0;
	private Bitmap bp_s1bac, bp_lockbac, bp_appbt;
	private float pis;
	private List<Drawable> apps;
	private boolean isanimation = false;
	private Context context;
	private boolean isdrawloacbac = true;
	private int pos;
	private SharedPreferences sp;
	private float s2offsit = 0;

	public MyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
		sp = context.getSharedPreferences("bacpos", context.MODE_PRIVATE);
		pos = sp.getInt("pos", 0);
		bp_s1bac = BitmapFactory.decodeResource(getResources(),
				Constants.pacbacress[pos]);
		bp_lockbac = Constants.bp_lockbac;
		bp_appbt = Constants.bp_appbt;
		paint = new Paint();
		camera = new Camera();

	}

	private OnLockListener oll;

	public interface OnLockListener {
		public void onlock();
	}

	private OnAppClickListener oacl;

	public interface OnAppClickListener {
		public void onapp(int pos);
	}

	private OnLongClickListener olc;

	public interface OnLongClickListener {
		public void onlong(int pos);
	}

	private On2SLockListener o2ll;

	public interface On2SLockListener {
		public void onlock();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		drawScreen2(canvas);
		drawScreen1(canvas);

		super.onDraw(canvas);
	}

	private void drawScreen1(Canvas canvas) {
		Log.i("test", "ondraw");
		Matrix mt = new Matrix();
		canvas.save();
		camera.save();
		camera.translate(0, -getHeight() + getHeight() * pis, 0);
		camera.rotateX(pis * 90);
		camera.getMatrix(mt);
		camera.restore();
		mt.preTranslate(-getWidth() / 2, 0);
		mt.postTranslate(getWidth() / 2, 0);
		canvas.concat(mt);

		canvas.drawBitmap(bp_s1bac, new Rect(0, 0, bp_s1bac.getWidth(),
				bp_s1bac.getHeight()),
				new Rect(0, -getHeight(), getWidth(), 0), paint);

		if (Constants.bp_sibac2 != null) {
			canvas.drawBitmap(Constants.bp_sibac2,
					new Rect(0, 0, Constants.bp_sibac2.getWidth(),
							Constants.bp_sibac2.getHeight()), new Rect(
							(int) s2offsit, -getHeight(),
							(int) (getWidth() + s2offsit), 0), paint);
		}

		drawLock(canvas);
		canvas.drawBitmap(bp_appbt, getWidth() / 2 - bp_appbt.getWidth() / 2,
				-bp_appbt.getHeight(), paint);
		drawTimeImage(canvas);
		paint.setColor(0xffffffff);
		paint.setTypeface(Typeface.DEFAULT_BOLD);
		paint.setTextSize(22);
		paint.setTextScaleX(1.0f);
		canvas.drawText(csdanddate, (float) getWidth() / 2 - 120, -getHeight()
				+ Constants.bp_time[0].getHeight() + 70, paint);

		canvas.restore();
	}

	private void drawTimeImage(Canvas canvas) {
		Bitmap bp[] = new Bitmap[5];
		for (int i = 0; i < 5; i++) {
			char c = time.charAt(i);
			switch (c) {
			case '0':
				bp[i] = Constants.bp_time[0];
				break;
			case '1':
				bp[i] = Constants.bp_time[1];
				break;
			case '2':
				bp[i] = Constants.bp_time[2];
				break;
			case '3':
				bp[i] = Constants.bp_time[3];
				break;
			case '4':
				bp[i] = Constants.bp_time[4];
				break;
			case '5':
				bp[i] = Constants.bp_time[5];
				break;
			case '6':
				bp[i] = Constants.bp_time[6];
				break;
			case '7':
				bp[i] = Constants.bp_time[7];
				break;
			case '8':
				bp[i] = Constants.bp_time[8];
				break;
			case '9':
				bp[i] = Constants.bp_time[9];
				break;
			case ':':
				bp[i] = Constants.bp_time[10];
				break;

			default:
				break;
			}
		}
		canvas.drawBitmap(bp[0],
				getWidth() / 2 - bp[2].getWidth() / 2 - bp[1].getWidth()
						- bp[0].getWidth() + 20, -getHeight() + 50, paint);
		canvas.drawBitmap(bp[1],
				getWidth() / 2 - bp[2].getWidth() / 2 - bp[1].getWidth() + 10,
				-getHeight() + 50, paint);
		canvas.drawBitmap(bp[2], getWidth() / 2 - bp[2].getWidth() / 2,
				-getHeight() + 50, paint);
		canvas.drawBitmap(bp[3], getWidth() / 2 + bp[2].getWidth() / 2 - 10,
				-getHeight() + 50, paint);
		canvas.drawBitmap(bp[4],
				getWidth() / 2 + bp[2].getWidth() / 2 + bp[3].getWidth() - 20,
				-getHeight() + 50, paint);
	}

	private void drawLock(Canvas canvas) {
		// 基线y
		float y = -getHeight() * 1 / 4;
		Rect src = new Rect((int) offsitx, 0, bp_lockbac.getWidth(),
				bp_lockbac.getHeight());
		Rect dst = new Rect(
				(int) (getWidth() / 2 - bp_lockbac.getWidth() / 2 + offsitx+20),
				(int) y, bp_lockbac.getWidth() / 2 - bp_lockbac.getWidth() / 2
						+ bp_lockbac.getWidth(),
				(int) (y + bp_lockbac.getHeight()));
		canvas.drawBitmap(bp_lockbac, src, dst, paint);

		if (isdrawloacbac) {
			canvas.drawBitmap(
					Constants.bp_lockpic,
					getWidth() / 2 - bp_lockbac.getWidth() / 2
							+ Constants.bp_lockbt.getWidth() + 10,
					y
							+ (bp_lockbac.getHeight() / 2 - Constants.bp_lockpic
									.getHeight() / 2), paint);
		}
		canvas.drawBitmap(
				Constants.bp_lockbt,
				getWidth() / 2 - bp_lockbac.getWidth() / 2 + offsitx,
				y
						+ (bp_lockbac.getHeight() / 2 - Constants.bp_lockbt
								.getHeight() / 2), paint);

	}

	private void drawScreen2(Canvas canvas) {
		Matrix mt = new Matrix();
		canvas.save();
		camera.save();
		camera.translate(0, -getHeight() + getHeight() * pis, 0);
		camera.rotateX(-45 + pis * 45);
		camera.getMatrix(mt);
		camera.restore();
		mt.preTranslate(-getWidth() / 2, 0);
		mt.postTranslate(getWidth() / 2, 0);
		canvas.concat(mt);

		paint.setColor(0xff000000);
		canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

		drawTitle(canvas);
		draw9APP(canvas);

		canvas.restore();
	}

	private void drawTitle(Canvas canvas) {
		canvas.drawBitmap(Constants.bp_title,
				new Rect(0, 0, Constants.bp_title.getWidth(),
						Constants.bp_title.getHeight()), new Rect(0, 0,
						getWidth(), 40), paint);
		paint.setTextSize(20);
		paint.setColor(0xffffffff);
		canvas.drawText(time, getWidth() / 2 - 30, 30, paint);
	}

	private void draw9APP(Canvas canvas) {
		if (apps != null) {
			Log.i("test", "" + apps.size());
		}
		float width = (float) (getWidth() - 4) / 3;
		float height = (float) (getHeight() - 8 - 40 - Constants.bp_appdown
				.getHeight()) / 3;
		Rect src = new Rect(0, 0, Constants.bp_appno.getWidth(),
				Constants.bp_appno.getHeight());
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Rect dst = new Rect((int) (j * (width + 2)), (int) (40 + i
						* (height + 2)), (int) (j * (width + 2) + width),
						(int) (40 + i * (height + 2) + height));
				if (apps != null) {
					if (i * 3 + j < apps.size()) {
						canvas.drawBitmap(Constants.bp_appbac, src, dst, paint);
						BitmapDrawable bd = (BitmapDrawable) (apps.get(i * 3
								+ j));
						Bitmap bp = bd.getBitmap();
						Rect bpsrc = new Rect(0, 0, bp.getWidth(),
								bp.getHeight());
						Rect bpdst = new Rect(
								(int) (j * (width + 2) + width / 2 - width / 5),
								(int) (40 + i * (height + 2) + height / 2 - height / 6),
								(int) (j * (width + 2) + width / 2 + width / 5),
								(int) (40 + i * (height + 2) + height / 2 + height / 6));
						canvas.drawBitmap(bp, bpsrc, bpdst, paint);
					} else if (i * 3 + j == apps.size()) {
						canvas.drawBitmap(Constants.bp_appno, src, dst, paint);
					} else {
						canvas.drawBitmap(Constants.bp_appbac, src, dst, paint);
					}
				} else {
					if (i * 3 + j == 0) {
						canvas.drawBitmap(Constants.bp_appno, src, dst, paint);
					} else {
						canvas.drawBitmap(Constants.bp_appbac, src, dst, paint);
					}
				}
			}
		}

		canvas.drawBitmap(Constants.bp_appdown,
				new Rect(0, 0, Constants.bp_appdown.getWidth(),
						Constants.bp_appdown.getHeight()),
				new Rect(0, (int) (40 + 3 * (height + 2)), getWidth(),
						getHeight()), paint);
	}

	private float prey, prex, offsitx;
	private int iddown, idup;
	private long precurrent;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (!isanimation) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (view == 0) {
					prey = event.getY();
					prex = event.getX();
					if (prex >= getWidth() / 2
							- Constants.bp_lockbac.getWidth() / 2
							&& prex <= getWidth() / 2
									- Constants.bp_lockbac.getWidth() / 2
									+ Constants.bp_lockbt.getWidth()
							&& prey >= getHeight()
									* 3
									/ 4
									+ (Constants.bp_lockbac.getHeight() / 2 - Constants.bp_lockbt
											.getHeight() / 2)
							&& prey <= getHeight()
									* 3
									/ 4
									+ (Constants.bp_lockbac.getHeight() / 2 + Constants.bp_lockbt
											.getHeight() / 2)) {
						mode = 1;
						bp_lockbac = Constants.bp_lockbac1;
						isdrawloacbac = false;
					} else if (prex >= getWidth() / 2
							- Constants.bp_appbt.getWidth() / 2 - 20
							&& prex <= getWidth() / 2
									+ Constants.bp_appbt.getWidth() / 2 + 20
							&& prey >= getHeight()
									- Constants.bp_appbt.getHeight() - 20
							&& prey <= getHeight()) {
						bp_appbt = Constants.bp_appbt1;
						mode = 2;
					} else {
						mode = 3;
					}
				} else if (view == 1) {
					prey = event.getY();
					prex = event.getX();
					iddown = getBTid(event.getX(), event.getY());
					precurrent = System.currentTimeMillis();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (view == 0) {
					if (mode == 1) {
						offsitx = event.getX() - prex;
						if (offsitx < 0) {
							offsitx = 0;
						} else if (offsitx > bp_lockbac.getWidth()
								- Constants.bp_lockbt.getWidth()) {
							offsitx = bp_lockbac.getWidth()
									- Constants.bp_lockbt.getWidth();
						}
						invalidate();
					} else if (mode == 2) {
						pis = (prey - event.getY()) / getHeight();
						if (pis <= 0) {
							pis = 0;
						}
						invalidate();
					}
				} else if (view == 1) {
					if (Math.abs(event.getX() - prex) < 10
							&& Math.abs(event.getY() - prey) < 10
							&& apps != null && iddown < apps.size()) {
						if (System.currentTimeMillis() - precurrent > 1200) {
							if (olc != null) {
								olc.onlong(iddown);
								iddown = -1;
							}
						}
					} else {
						if (mode == 11) {
							pis = 1 - (event.getY() - prey) / getHeight();
							if (pis >= 1) {
								pis = 1;
							}
						}
						if (event.getY() - prey > 10) {
							mode = 11;
						} else {
							mode = 10;
						}
						invalidate();
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				if (view == 0) {
					if (mode == 1) {
						if (offsitx > bp_lockbac.getWidth()
								- Constants.bp_lockbt.getWidth() * 3 / 2
								&& oll != null) {
							oll.onlock();
						}
					} else if (mode == 2) {
						if (pis >= 0.0001 && pis <= 0.999999) {
							new ScThread().start();
						}
					} else if (mode == 3) {
						if (event.getX() - prex > 100) {
							pos--;
							if (pos < 0) {
								pos = Constants.pacbacress.length - 1;
							}
							Constants.bp_sibac2 = BitmapFactory.decodeResource(
									getResources(), Constants.pacbacress[pos]);
							s2offsit = -getWidth();
							new Sc2Thread().start();
						} else if (prex - event.getX() > 100) {
							pos++;
							if (pos >= Constants.pacbacress.length) {
								pos = 0;
							}
							Constants.bp_sibac2 = BitmapFactory.decodeResource(
									getResources(), Constants.pacbacress[pos]);
							s2offsit = getWidth();
							new Sc2Thread().start();
						}

					}

					// if (pis >= 0.5) {
					// view = 1;
					// pis = 1;
					// } else {
					// view = 0;
					// pis = 0;
					// }
				} else if (view == 1) {
					idup = getBTid(event.getX(), event.getY());
					if (mode == 11) {
						if (pis >= 0.0001 && pis <= 0.999999) {
							new ScThread().start();
						}
					} else if (idup != -1 && iddown == idup) {
						Log.i("test", "id:" + idup);
						if (idup == 9) {
							// pis = 0;
							// view = 0;
							// new ScBackThread().start();
							if (o2ll != null) {
								o2ll.onlock();
							}
						} else {
							if (oacl != null) {
								oacl.onapp(idup);
							}
						}
					}
				}
				mode = -1;
				offsitx = 0;
				isdrawloacbac = true;
				bp_lockbac = Constants.bp_lockbac;
				bp_appbt = Constants.bp_appbt;
				invalidate();
				break;
			default:
				break;
			}
		}
		return true;
	}

	private Handler hand = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0) {
				invalidate();
			}
			if (msg.what == 1) {
				invalidate();
				isanimation = false;
			}
		};
	};

	private class Sc2Thread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			isanimation = true;
			float dx = s2offsit / 200;
			boolean b = false;
			if (s2offsit > 0) {
				b = true;
			}
			while (s2offsit < 0 && !b || s2offsit > 0 && b) {
				Log.i("test", "thread while");
				s2offsit -= dx;
				hand.sendEmptyMessage(0);
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			hand.sendEmptyMessage(1);
			bp_s1bac = Constants.bp_sibac2;
			Constants.bp_sibac2 = null;
			s2offsit = 0;
			sp.edit().putInt("pos", pos).commit();
			super.run();
		}
	}

	private class ScThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			isanimation = true;
			float dp = 1.0f / getHeight() * 2;
			if (pis >= 0.5) {
				dp = -dp;
			}
			boolean bb = true;
			while (pis >= 0 && pis <= 1) {
				Log.i("test", "pis:" + pis);
				pis -= dp;
				hand.sendEmptyMessage(0);
				if (bb) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (bb) {
					bb = false;
				} else {
					bb = true;
				}
			}
			if (pis <= 0) {
				pis = 0;
				view = 0;
			}
			if (pis >= 1) {
				pis = 1;
				view = 1;
			}
			hand.sendEmptyMessage(1);
			super.run();
		}
	}

	private class ScBackThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			isanimation = true;
			float dp = 1.0f / getHeight() * 3;
			boolean bb = true;
			while (pis >= 0 && pis <= 1) {
				Log.i("test", "pis:" + pis);
				pis -= dp;
				hand.sendEmptyMessage(0);
				if (bb) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (bb) {
					bb = false;
				} else {
					bb = true;
				}
			}
			if (pis <= 0) {
				pis = 0;
				view = 0;
			}
			if (pis >= 1) {
				pis = 1;
				view = 1;
			}
			hand.sendEmptyMessage(0);
			super.run();
		}
	}

	// 判断点击的是哪个按钮
	private int getBTid(float x, float y) {
		int id = -1;
		float width = (float) (getWidth() - 10) / 3;
		float height = (float) (getHeight() - 20 - 40 - Constants.bp_appdown
				.getHeight()) / 3;
		if (x >= 0 + 20 && x <= width - 20) {
			if (y >= 45 + 20 && y <= 45 + height - 20) {
				id = 0;
			} else if (y >= 45 + 20 + height + 5
					&& y <= 2 * height + 45 + 5 - 20) {
				id = 3;
			} else if (y >= 2 * height + 45 + 10 + 20
					&& y <= 3 * height + 45 + 10 - 20) {
				id = 6;
			}
		} else if (x >= width + 5 + 20 && x <= width * 2 + 5 - 20) {
			if (y >= 45 + 20 && y <= 45 + height) {
				id = 1;
			} else if (y >= 45 + height + 5 + 20
					&& y <= 2 * height + 45 + 5 - 20) {
				id = 4;
			} else if (y >= 2 * height + 45 + 10 + 20
					&& y <= 3 * height + 45 + 10 - 20) {
				id = 7;
			}
		} else if (x >= width * 2 + 10 + 20 && x <= getWidth() - 20) {
			if (y >= 45 + 20 && y <= 45 + height - 20) {
				id = 2;
			} else if (y >= 45 + height + 5 + 20
					&& y <= 2 * height + 45 + 5 - 20) {
				id = 5;
			} else if (y >= 2 * height + 45 + 10 + 20
					&& y <= 3 * height + 45 + 10 - 20) {
				id = 8;
			}
		}
		if (y >= 3 * height + 45 + 10) {
			id = 9;
		}
		return id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCsdanddate() {
		return csdanddate;
	}

	public void setCsdanddate(String csdanddate) {
		this.csdanddate = csdanddate;
	}

	public List<Drawable> getApps() {
		return apps;
	}

	public void setApps(List<Drawable> apps) {
		this.apps = apps;
		invalidate();
	}

	public OnLockListener getOll() {
		return oll;
	}

	public void setOll(OnLockListener oll) {
		this.oll = oll;
	}

	public OnAppClickListener getOacl() {
		return oacl;
	}

	public void setOacl(OnAppClickListener oacl) {
		this.oacl = oacl;
	}

	public OnLongClickListener getOlc() {
		return olc;
	}

	public On2SLockListener getO2ll() {
		return o2ll;
	}

	public void setO2ll(On2SLockListener o2ll) {
		this.o2ll = o2ll;
	}

	public void setOlc(OnLongClickListener olc) {
		this.olc = olc;
	}

}
