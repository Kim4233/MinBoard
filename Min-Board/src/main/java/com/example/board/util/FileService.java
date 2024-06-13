package com.example.board.util;

import com.example.board.model.board.AttachedFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileService {
    @Value("${file.upload.path}")
    private String uploadPath;

    public AttachedFile saveFile(MultipartFile mfile) {
        if (mfile == null || mfile.isEmpty() || mfile.getSize() == 0) {
            return null;
        }

        File path = new File(uploadPath);
        if (!path.isDirectory()) {
            path.mkdirs();
        }

        String originalFilename = mfile.getOriginalFilename();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String savedFilename = sdf.format(new Date());

        String ext;
        int lastIndex = originalFilename.lastIndexOf(".");

        //拡張子がない場合
        if (lastIndex == -1) {
            ext = "";
        }

      
        else {
            ext = "." + originalFilename.substring(lastIndex + 1);
        }

      //保存するフルパスを含むFileオブジェクト
        File serverFile = null;

      //同じ名前のファイルがある場合の処理
        while (true) {
            serverFile = new File(uploadPath + "/" + savedFilename + ext);
            // 같은 이름의 파일이 없으면 나감
            if (!serverFile.isFile()) break;
          //同じ名前のファイルがある場合、名前の後にlong型の時間情報を付け加えます。
            savedFilename = savedFilename + new Date().getTime();
        }

        //ファイル保存
        try {
            mfile.transferTo(serverFile);
        } catch (Exception e) {
            savedFilename = null;
            e.printStackTrace();
        }

        return new AttachedFile(originalFilename, savedFilename + ext, mfile.getSize());
    }

   
    public boolean deleteFile(String fullpath) {
    	// ファイルを削除するかどうかを返す変数
        boolean result = false;

        //渡されたフルパスでFileオブジェクトを作成する
        File delFile = new File(fullpath);

        // そのファイルが存在する場合は削除
        if (delFile.isFile()) {
            delFile.delete();

            return true;
        }

        return result;
    }
}
