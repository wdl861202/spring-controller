
package drr.framework.file;

import java.io.File;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import drr.constant.common.PunctuationConst;

/**
 * 上传文件存储到磁盘
 *
 * @author wangdl
 *
 */
public class FileUploadUtils {

	private FileUploadUtils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * 上传文件，保存到本地
	 *
	 * @param file
	 * @param storePath
	 * @throws Exception
	 */
	public static void fileupload(MultipartFile file, String storePath) throws Exception {
		if (Optional.ofNullable(file).isPresent()) {
			File targetfile = new File(storePath);
			if (!targetfile.exists()) {
				targetfile.mkdirs();
			}

			storeFile(file, getLocation(file, storePath));
		}
	}

	/**
	 * 按照最终路径存储文件
	 *
	 * @param file
	 * @param path
	 * @throws Exception
	 */
	public static void storeFile(MultipartFile file, String path) throws Exception {
		File dest = new File(path);
		if (dest.createNewFile()) {
			file.transferTo(dest);
		} else {
			throw new Exception("存储文件失败！");
		}
	}

	/**
	 * 生成本地路径，上传文件名+时间戳
	 *
	 * @param file
	 * @param storePath
	 * @return
	 */
	public static String getLocation(MultipartFile file, String storePath) {
		StringBuilder sb = new StringBuilder(storePath);
		String originalFilename = file.getOriginalFilename();
		int lastIndexOf = originalFilename.lastIndexOf(PunctuationConst.DOT);
		if (lastIndexOf != -1) {
			return sb.append(originalFilename.substring(0, lastIndexOf)).append(PunctuationConst.UNDERSCORE).append(System.nanoTime()).append(originalFilename.substring(lastIndexOf)).toString();
		} else {
			return sb.append(originalFilename).append(PunctuationConst.UNDERSCORE).append(System.currentTimeMillis()).toString();
		}
	}
}
