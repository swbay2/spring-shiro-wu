package com.wangzhixuan.commons.shiro;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

/**
 * shiro密码盐加密所用
 * 此处为了redis缓存实现序列化
 * @author L.cm
 *
 */
public class ShiroByteSource extends SimpleByteSource implements Serializable {
	private static final long serialVersionUID = -6814382603612799610L;

	public ShiroByteSource(byte[] bytes) {
		super(bytes);
	}

	public ShiroByteSource(ByteSource source) {
		super(source);
	}

	public ShiroByteSource(char[] chars) {
		super(chars);
	}

	public ShiroByteSource(File file) {
		super(file);
	}

	public ShiroByteSource(InputStream stream) {
		super(stream);
	}

	public ShiroByteSource(String string) {
		super(string);
	}

	/**
	 * Returns a new {@code ByteSource} instance representing the specified string's bytes.  The byte
	 * array is obtained assuming {@code UTF-8} encoding.
	 *
	 * @param string the string to represent as a {@code ByteSource} instance.
	 * @return a new {@code ByteSource} instance representing the specified string's bytes.
	 */
	public static ByteSource of(String string) {
		return new ShiroByteSource(string);
	}
}
