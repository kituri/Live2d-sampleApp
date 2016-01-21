/**
 *  You can modify and use this source freely
 *  only for the development of application related Live2D.
 *
 *  (c) Live2D Inc. All rights reserved.
 */

package com.kituri.tankmmlive2dshow;

import java.io.IOException;

import com.kituri.tankmmlive2dshow.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import jp.live2d.utils.android.FileManager;
import jp.live2d.utils.android.SoundManager;

/*
 * SampleApplicationはActivityを継承し、サンプル・アプリケーションのエントリポイント（メインのActivityクラス）となります。
 */
public class SampleApplication extends Activity
{
	//  Live2Dの管理
	private LAppLive2DManager live2DMgr ;
	static private Activity instance;

	public SampleApplication( )
	{
		instance=this;
		if(LAppDefine.DEBUG_LOG)
		{
			Log.d( "", "==============================================\n" ) ;
			Log.d( "", "   Live2D Sample  \n" ) ;
			Log.d( "", "==============================================\n" ) ;
		}

		SoundManager.init(this);
		live2DMgr = new LAppLive2DManager() ;
	}

	 static public void exit()
    {
		SoundManager.release();
    	instance.finish();
    }


	/*
	 * Activityが作成されたときのイベント
	 */
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initLive2dRes("live2d");
        // GUIを初期化
      	setupGUI();
      	FileManager.init(this.getApplicationContext());
    }


	/*
	 * GUIの初期化
	 * activity_main.xmlからViewを作成し、そこにLive2Dを配置する
	 */
	void setupGUI()
	{
    	setContentView(R.layout.activity_main);

        //  Viewの初期化
        LAppView view = live2DMgr.createView(this) ;

        // activity_main.xmlにLive2DのViewをレイアウトする
        FrameLayout layout=(FrameLayout) findViewById(R.id.live2DLayout);
		layout.addView(view, 0, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));


		// モデル切り替えボタン
		ImageButton iBtn = (ImageButton)findViewById(R.id.imageButton1);
		ClickListener listener = new ClickListener();
		iBtn.setOnClickListener(listener);
	}


	// ボタンを押した時のイベント
	class ClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Toast.makeText(getApplicationContext(), "change model", Toast.LENGTH_SHORT).show();
			live2DMgr.changeModel();//Live2D Event
		}
	}


	/*
	 * Activityを再開したときのイベント。
	 */
	@Override
	protected void onResume()
	{
		//live2DMgr.onResume() ;
		super.onResume();
	}

	 private void initLive2dRes(String path) {
	        try {
	            String str[] = getAssets().list(path);
	            if (str.length > 0) {//如果是目录
	                for (String string : str) {
	                    LAppDefine.MODELS.add(string);
	                }
	            }
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	
	/*
	 * Activityを停止したときのイベント。
	 */
	@Override
	protected void onPause()
	{
		live2DMgr.onPause() ;
    	super.onPause();
	}
}
