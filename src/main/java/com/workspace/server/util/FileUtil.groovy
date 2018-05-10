package com.workspace.server.util


import groovy.util.logging.Slf4j
import org.junit.Test
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.security.MessageDigest

@Component
@Slf4j
class FileUtil {

    public static final TYPE_UNDEFINE = 'undefine/type'
    public static final TYPE_IMAGE = 'image/type'
    public static final TYPE_DOCUMENT = 'document/type'
    public static final TYPE_AUDIO = 'audio/type'
    public static final TYPE_VIDEO = 'video/type'

    public static final Map<String, List<String>> fileSuffixes = [
            'image/type': [ 'jpg','bmp','png','gif','tif','psd','ai','svg' ],
            'document/type': [ 'txt','rtf','html','pdf','doc','ppt','xls','docx','pptx','xlsx','wps' ],
            'audio/type': [ 'mp3','wma','wav','asf','aac','flac','ape','mid','ogg' ],
            'video/type': [ 'mp4','avi','rm','rmvb','3gp','mov','mkv','mpg','flv' ]
    ]

    String getSuffix (String filename) {
        if (filename) {
            return filename.substring(filename.lastIndexOf('.') + 1, filename.length())
        }
        return ''
    }

    String getFileType (String filename) {
        if (filename) {
            return fileSuffixes?.find { k, v -> v?.contains(getSuffix(filename)) }?.key ?: TYPE_UNDEFINE
        }
        return TYPE_UNDEFINE
    }

    String getFileMd5(MultipartFile file) {
        if (file) {
            MessageDigest messageDigest = null
            BufferedInputStream bufferedInputStream = null
            try {
                messageDigest = MessageDigest.getInstance('MD5')
                bufferedInputStream = new BufferedInputStream(file.getInputStream())
                int len
                byte[] buffer = new byte[1024]
                while ((len = bufferedInputStream.read(buffer)) != -1) {
                    messageDigest.update(buffer, 0, len)
                }
            } catch (Exception e) {
                log.info('[workspace-server] Encode File MD5 Exception: ' + e.toString())
            } finally {
                bufferedInputStream?.close()
            }
            byte[] digest = messageDigest.digest()
            StringBuffer buffer = new StringBuffer()
            for (int i = 0; i < digest.length; i++) {
                buffer.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1))
            }
            return buffer?.toString()
        }
        return ''
    }

    public static final String ASSET_FOLDER_NAME = 'asset'

    File getFile (String md5) {
        File serverBaseFolder = new File(System.getProperty('user.dir'))
        File assetFolder = new File(serverBaseFolder, ASSET_FOLDER_NAME)
        return new File(assetFolder, md5)
    }

}
