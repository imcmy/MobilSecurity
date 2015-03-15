package com.oc.mobilsecurity.contact;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import com.oc.mobilsecurity.R;
import android.content.Context;
import android.util.Log;

public class Client {

	private static final int SERVER_PORT = 7777;// 端口号
	private static final String SERVER_IP = "192.168.1.102";// 连接IP

	private static final String CLIENT_KET_PASSWORD = "mfs007";// 私钥密码
	private static final String CLIENT_TRUST_PASSWORD = "mfs007";// 信任证书密码

	private static final String CLIENT_AGREEMENT = "TLS";// 使用协议
	private static final String CLIENT_KEY_MANAGER = "X509";// 密钥管理器
	private static final String CLIENT_TRUST_MANAGER = "X509";
	private static final String CLIENT_KEY_KEYSTORE = "BKS";// 密库，这里用的是BouncyCastle密库
	private static final String CLIENT_TRUST_KEYSTORE = "BKS";

	private SSLSocket sslSocket;

	public void init(Context context) {
		try {
			SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);
			KeyManagerFactory keyManager = KeyManagerFactory
					.getInstance(CLIENT_KEY_MANAGER);
			TrustManagerFactory trustManager = TrustManagerFactory
					.getInstance(CLIENT_TRUST_MANAGER);
			KeyStore kks = KeyStore.getInstance(CLIENT_KEY_KEYSTORE);
			KeyStore tks = KeyStore.getInstance(CLIENT_TRUST_KEYSTORE);
			kks.load(context.getResources().openRawResource(R.raw.debug),
					CLIENT_KET_PASSWORD.toCharArray());
			tks.load(context.getResources().openRawResource(R.raw.debug),
					CLIENT_TRUST_PASSWORD.toCharArray());
			keyManager.init(kks, CLIENT_KET_PASSWORD.toCharArray());
			trustManager.init(tks);
			sslContext.init(keyManager.getKeyManagers(),
					trustManager.getTrustManagers(), null);
			sslSocket = (SSLSocket) sslContext.getSocketFactory().createSocket(
					SERVER_IP, SERVER_PORT);
		} catch (Exception e) {
			Log.v("TAG", e.toString());
		}
	}

	public String process(String message) {
		if (sslSocket == null) {
			return "null";
		} else {
			try {
				InputStream input = sslSocket.getInputStream();
				OutputStream output = sslSocket.getOutputStream();

				BufferedInputStream bis = new BufferedInputStream(input);
				BufferedOutputStream bos = new BufferedOutputStream(output);
				
				bos.write(message.getBytes());
				bos.flush();

				byte[] buffer = new byte[4096];
				bis.read(buffer);
				String result = new String(buffer).trim();
				
				sslSocket.close();
				return result;
			} catch (Exception e) {
				return "error";
			}
		}
	}
}
