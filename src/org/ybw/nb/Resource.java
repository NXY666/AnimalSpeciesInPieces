package org.ybw.nb;

import com.google.gson.Gson;

import java.io.*;
import java.util.stream.Collectors;

public class Resource {
	/**
	 * 获取文件的InputStream
	 *
	 * @param path 以“#”开头则尝试访问内部文件（失败则继续访问外部文件），否则访问外部文件。
	 */
	public static InputStream getStream(String path) throws IOException {
		InputStream is = null;
		if (path.charAt(0) == '#') {
			path = path.substring(1);
			is = Resource.class.getResourceAsStream('/' + path);
		}
		if (is == null) {
			is = new FileInputStream(path);
		}
		return is;
	}

	public static String getString(String path) throws IOException {
		return new BufferedReader(new InputStreamReader(getStream(path))).lines().collect(Collectors.joining());
	}

	public static <T> T getJson(String path, Class<T> clazz) throws IOException {
		String jsonStr = getString(path);
		return new Gson().fromJson(jsonStr, clazz);
	}
}