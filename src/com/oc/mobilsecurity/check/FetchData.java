package com.oc.mobilsecurity.check;

import java.io.*;
import java.lang.reflect.Method;

import android.util.Log;
//显示系统build的一些属性:可以读取文件(/system/build.prop)获得
//输出系统一些属性

public class FetchData {
	private static StringBuffer buffer;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String fetch_baseband_version() {
		String result = "";
		try {
			Class cl = Class.forName("android.os.SystemProperties");
			Object invoker = cl.newInstance();
			Method m = cl.getMethod("get", new Class[] { String.class,
					String.class });
			result = (String)m.invoke(invoker, new Object[] {
					"gsm.version.baseband", "no message" });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String fetch_kernel_version() {
		String kernelVersion = "";
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("/proc/version");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return kernelVersion;
		}
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream), 8 * 1024);
		String info = "";
		String line = "";
		try {
			while ((line = bufferedReader.readLine()) != null) {
				info += line;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bufferedReader.close();
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			if (info != "") {
				final String keyword = "version ";
				int index = info.indexOf(keyword);
				line = info.substring(index + keyword.length());
				index = line.indexOf(" ");
				kernelVersion = line.substring(0, index);
			}
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
		}

		return kernelVersion;
	}

	// cpu info
	public static String fetch_cpu_info() {
		
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/cat", "/proc/cpuinfo" };
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Log.v("TAG", "CPU: " + result);
		return result;
	}

	// disk info主要文件的一些容量使用状态(cache,sqlite,dev...)
	public static String fetch_disk_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/df" };
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	// netstat info
	public static String fetch_netstat_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/netstat" };
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	// version info
	public static String fetch_version_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/cat", "/proc/version" };
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}

	// netstat info 显示Android内核输出的Log
	public static String fetch_dmesg_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/dmesg" };
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			Log.i("fetch_dmesg_info", "ex=" + ex.toString());
		}
		return result;
	}

	// fetch_process_info系统CPU使用状态
	public static String fetch_process_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/top", "-n", "1" };
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			Log.i("fetch_process_info", "ex=" + ex.toString());
		}
		return result;
	}

	// 得到网络链接状态
	public static String fetch_netcfg_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/netcfg" };
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			Log.i("fetch_process_info", "ex=" + ex.toString());
		}
		return result;
	}

	// fetch_mount_info
	public static String fetch_mount_info() {
		String result = null;
		CMDExecute cmdexe = new CMDExecute();
		try {
			String[] args = { "/system/bin/mount" };
			result = cmdexe.run(args, "/system/bin/");
		} catch (IOException ex) {
			Log.i("fetch_process_info", "ex=" + ex.toString());
		}
		return result;
	}

	/**
	 * 系统信息查看方法
	 */
	public static String getSystemProperty() {
		buffer = new StringBuffer();
		initProperty("java.vendor.url", "java.vendor.url");
		initProperty("java.class.path", "java.class.path");
		initProperty("user.home", "user.home");
		initProperty("java.class.version", "java.class.version");
		initProperty("os.version", "os.version");
		initProperty("java.vendor", "java.vendor");
		initProperty("user.dir", "user.dir");
		initProperty("user.timezone", "user.timezone");
		initProperty("path.separator", "path.separator");
		initProperty(" os.name", " os.name");
		initProperty("os.arch", "os.arch");
		initProperty("line.separator", "line.separator");
		initProperty("file.separator", "file.separator");
		initProperty("user.name", "user.name");
		initProperty("java.version", "java.version");
		initProperty("java.home", "java.home");
		return buffer.toString();
	}

	private static String initProperty(String description, String propertyStr) {
		if (buffer == null) {
			buffer = new StringBuffer();
		}
		buffer.append(description).append(":");
		buffer.append(System.getProperty(propertyStr)).append("\n");
		return buffer.toString();
	}
}
