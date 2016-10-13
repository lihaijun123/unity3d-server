package com.focustech.cief.filemanage.dataserver.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * zip解压缩 *
 *
 * @author lihaijun
 *
 */
public class ZipUtil {

	private static void unCompress(String zipFilePath, String outputDirectory) {
		File outputDirectoryFile = new File(outputDirectory);
		if (!outputDirectoryFile.exists()) {
			outputDirectoryFile.mkdir();
		}
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			Enumeration e = zipFile.getEntries();
			ZipEntry zipEntry = null;
			while (e.hasMoreElements()) {
				zipEntry = (ZipEntry) e.nextElement();
				String zipEntryName = zipEntry.getName();
				System.out.println("unziping " + zipEntryName);
				if (zipEntry.isDirectory()) {
					zipEntryName = zipEntryName.substring(0, zipEntryName.length() - 1);
					File f = new File(outputDirectory + File.separator + zipEntryName);
					f.mkdir();
				} else {
					zipEntryName = zipEntryName.replace('\\', '/');
					if (zipEntryName.indexOf("/") != -1) {
						createDirectory(outputDirectory, zipEntryName.substring(0,zipEntryName.lastIndexOf("/")));
					}
					File f = new File(outputDirectory + zipEntryName);
					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);
					byte[] by = new byte[1024];
					int c;
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();
				}
			}
			System.out.println("done!");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 创建解压缩的子目录 *
	 *
	 * @param directory
	 * @param subDirectory
	 */
	private static void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if ("".equals(subDirectory) && !fl.exists())
				fl.mkdir();
			else if (!"".equals(subDirectory)) {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (!subFile.exists()) {
						subFile.mkdir();
					}
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
