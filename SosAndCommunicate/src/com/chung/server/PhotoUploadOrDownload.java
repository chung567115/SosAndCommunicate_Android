package com.chung.server;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.chung.sosandcommunicate.LoginActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class PhotoUploadOrDownload {
	//private String url="http://192.168.23.1:80/0/index.php/Uploadpic/uploadpic/";//���URL
	//private String url = "http://10.0.3.2:80/0/index.php/Uploadpic/uploadpic/";// Genymotion URL
	private static String url = LoginActivity.SAE_URL+"index.php/Uploadpic/uploadpic/";// SAE URL
	public static final String SAEHEADPICURL = "http://sosandcommunicate-public.stor.sinaapp.com/";
	
	private static String end = "\r\n";
	private static String twoHyphens = "--";
	private static String boundary = "*****";

//	protected int flag;
	
	
	/* �ϴ��ļ����������ķ��� */
	public static boolean uploadFile(final String path) {

		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
					/* ����Input��Output����ʹ��Cache */
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setUseCaches(false);
					/* ���ô��͵�method=POST */
					conn.setRequestMethod("POST");
					/* setRequestProperty */
					conn.setRequestProperty("Connection", "Keep-Alive");
					conn.setRequestProperty("Charset", "UTF-8");
					conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary);

			          /* ����DataOutputStream */
			          DataOutputStream ds = new DataOutputStream(conn.getOutputStream());
			          ds.writeBytes(twoHyphens + boundary + end);
			          ds.writeBytes("Content-Disposition: form-data; "+ "name=\"headpic\";filename=\""+ path + "\"" + end);
			          ds.writeBytes(end);  
			          /* ȡ���ļ���FileInputStream */
			          FileInputStream fStream =new FileInputStream(path);
			          /* ����ÿ��д��1024bytes */
			          int bufferSize =1024;
			          byte[] buffer =new byte[bufferSize];
			          int length =-1;
			          /* ���ļ���ȡ������������ */
			          while((length = fStream.read(buffer)) !=-1)
			          {
			            /* ������д��DataOutputStream�� */
			            ds.write(buffer, 0, length);
			          }
			          ds.writeBytes(end);
			          ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			          /* close streams */
			          fStream.close();
			          ds.flush();
			          /* ȡ��Response���� */
			          InputStream is = conn.getInputStream();
			          int ch;
			          StringBuffer b =new StringBuffer();
			          while( ( ch = is.read() ) !=-1 )
			          {
			            b.append( (char)ch );
			          }
			          /* ��Response��ʾ��Dialog */
			          Log.i("UploadHeadPic", "�ϴ��ɹ�"+b.toString().trim());
			          //showDialog("�ϴ��ɹ�"+b.toString().trim());
			          /* �ر�DataOutputStream */
			          ds.close();
//			          flag = 1;
				} catch (Exception e) {
					Log.i("UploadHeadPic", "�ϴ�ʧ��" + e);
//					flag = 0;
				}
			}
		});
		thread1.start();
		
		return thread1.isAlive()?false:true;
	}

	
	/**
	 * ��SAE��ȡ��ӦstudentID��HeadPic
	 */
	public static Bitmap getHeadPic(String stuID){
		Bitmap cacheBitmap = null;
		String imageUrl = SAEHEADPICURL + stuID + ".png";
		Log.i("imageUrl!!!!-----", imageUrl);
        //httpGet���Ӷ���  
        HttpGet httpRequest = new HttpGet(imageUrl);  
        //ȡ��HttpClient ����  
        HttpClient httpclient = new DefaultHttpClient();
		try {
	        //����httpClient ��ȡ��HttpRestponse  
	        HttpResponse httpResponse = httpclient.execute(httpRequest);
	        if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
	             HttpEntity httpEntity = httpResponse.getEntity();  //ȡ�������Ϣ ȡ��HttpEntiy
	             InputStream is = httpEntity.getContent();  //���һ�������� 
	             cacheBitmap = BitmapFactory.decodeStream(is);  
	             is.close();
	             Log.i("getHeadPic", "return--not--null");
	        }
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cacheBitmap;
	}
	
}