package com.focustech.cief.filemanage.app.id;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.focustech.cief.filemanage.dataserver.common.utils.FileUtil;
/**
 *
 * *
 * @author lihaijun
 *
 */
public class FileEveryDaySerialNumber extends EveryDaySerialNumber {

	private AppResource resource;
	 /**
     * 持久化存储的文件
     */
    private File file = null;

    /**
     * 存储的分隔符
     */
    private final static String FIELD_SEPARATOR = ",";

    @Override
    protected int getOrUpdateNumber(Date current, int start) {
        String date = format(current);
        int num = start;
        if(file.exists()) {
            List<String> list = FileUtil.readList(file);
            String[] data = list.get(0).split(FIELD_SEPARATOR);
            if(date.equals(data[0])) {
                num = Integer.parseInt(data[1]);
            }
        }
        FileUtil.rewrite(file, date + FIELD_SEPARATOR + (num + 1));
        return num;
    }

	public AppResource getResource() {
		return resource;
	}

	public void setResource(AppResource resource) {
		this.resource = resource;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public void cInit() {
		try {
			file = new File(resource.getAppIdResource().getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
