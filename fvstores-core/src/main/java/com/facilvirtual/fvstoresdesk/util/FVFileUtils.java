package com.facilvirtual.fvstoresdesk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FVFileUtils {
   static final Logger logger = LoggerFactory.getLogger(FVFileUtils.class);

   public FVFileUtils() {
   }

   public static boolean isZip(File fileIn) {
      return "zip".equalsIgnoreCase(getFileType(fileIn.getName()));
   }

   public static void deleteFile(String filePath) {
      try {
         File file = new File(filePath);
         if (file.delete()) {
         }
      } catch (Exception var2) {
         var2.printStackTrace();
      }

   }

   public static void copyFile(String fileIn, String fileOut) {
      try {
         File f1 = new File(fileIn);
         File f2 = new File(fileOut);
         InputStream in = new FileInputStream(f1);
         OutputStream out = new FileOutputStream(f2);
         byte[] buf = new byte[1024];

         int len;
         while((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
         }

         in.close();
         out.close();
      } catch (FileNotFoundException var8) {
         logger.error(var8.getMessage() + " in the specified directory.");
      } catch (IOException var9) {
         logger.error(var9.getMessage());
      }

   }

   public static void createDir(String dir) {
      File folder = new File(dir);
      folder.mkdir();
   }

   public static Long getFileSize(File icon) {
      return icon.length();
   }

   public static String getFileType(String fileName) {
      String ext = null;
      int i = fileName.lastIndexOf(46);
      if (i > 0 && i < fileName.length() - 1) {
         ext = fileName.substring(i + 1).toLowerCase();
      }

      return ext;
   }

   public static List<File> unZip(String zipFile) {
      List<File> files = new ArrayList();
      String property = "java.io.tmpdir";
      String tempDir = System.getProperty(property);
      byte[] buffer = new byte[1024];

      try {
         File folder = new File(tempDir);
         if (!folder.exists()) {
            folder.mkdir();
         }

         ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
         ZipEntry ze = zis.getNextEntry();

         while(ze != null) {
            String fileName = ze.getName();
            File newFile = new File(tempDir + File.separator + fileName);
            logger.info("file unzip : " + newFile.getAbsoluteFile());
            (new File(newFile.getParent())).mkdirs();
            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while((len = zis.read(buffer)) > 0) {
               fos.write(buffer, 0, len);
            }

            fos.close();
            ze = zis.getNextEntry();
            files.add(newFile);
         }

         zis.closeEntry();
         zis.close();
         logger.info("Done");
      } catch (IOException var12) {
         logger.error("Error al descomprimir archvo", var12);
      }

      return files;
   }

   public static boolean isPlainText(String fileType) {
      return "txt".equalsIgnoreCase(fileType);
   }

   public static boolean isExcelType(String fileType) {
      if (fileType != null) {
         fileType = fileType.toLowerCase();
         return "xls".toLowerCase().equals(fileType) || "xlsx".toLowerCase().equals(fileType) || "lsx".toLowerCase().equals(fileType);
      } else {
         return false;
      }
   }

   public static List<String> getFilesTypesInZip(String zipFile) {
      String property = "java.io.tmpdir";
      String tempDir = System.getProperty(property);
      List<String> filesTypes = new ArrayList();

      try {
         File folder = new File(tempDir);
         if (!folder.exists()) {
            folder.mkdir();
         }

         ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));

         for(ZipEntry ze = zis.getNextEntry(); ze != null; ze = zis.getNextEntry()) {
            String fileName = ze.getName();
            filesTypes.add(getFileType(fileName));
         }

         zis.closeEntry();
         zis.close();
      } catch (IOException var8) {
         logger.error("Error al descomprimir archivo", var8);
      }

      return filesTypes;
   }

   public static void initFileStructure(String root, String path) {
      String[] directoryNames = path.split("/");
      StringBuilder currentDirectoryPath = new StringBuilder(root);

      for(int idx = 0; idx < directoryNames.length - 1; ++idx) {
         currentDirectoryPath.append(directoryNames[idx]).append("/");
         createDirectoryIfNonExists(currentDirectoryPath.toString());
      }

   }

   public static void createDirectoryIfNonExists(String dir) {
      File folder = new File(dir);
      if (!folder.exists()) {
         folder.mkdir();
      }

   }

   public static boolean isBDF(String fileType) {
      return "vdf".equalsIgnoreCase(fileType) || "bdf".equalsIgnoreCase(fileType);
   }

   public static boolean isCSV(String fileType) {
      return "csv".equalsIgnoreCase(fileType);
   }

   public static void copyFolder(File src, File dest) throws IOException {
      if (src.isDirectory()) {
         if (!dest.exists()) {
            dest.mkdir();
         }

         String[] files = src.list();
         String[] var6 = files;
         int var5 = files.length;

         for(int var4 = 0; var4 < var5; ++var4) {
            String file = var6[var4];
            File srcFile = new File(src, file);
            File destFile = new File(dest, file);
            copyFolder(srcFile, destFile);
         }
      } else {
         copyFileUsingChannel(src, dest);
      }

   }

   private static void copyFileUsingChannel(File source, File dest) throws IOException {
      FileChannel sourceChannel = null;
      FileChannel destChannel = null;

      try {
         sourceChannel = (new FileInputStream(source)).getChannel();
         destChannel = (new FileOutputStream(dest)).getChannel();
         destChannel.transferFrom(sourceChannel, 0L, sourceChannel.size());
      } finally {
         sourceChannel.close();
         destChannel.close();
      }

   }

   public static void copyFolderOLD(File src, File dest) throws IOException {
      int length;
      if (src.isDirectory()) {
         if (!dest.exists()) {
            dest.mkdir();
         }

         String[] files = src.list();
         String[] var6 = files;
         length = files.length;

         for(int var4 = 0; var4 < length; ++var4) {
            String file = var6[var4];
            File srcFile = new File(src, file);
            File destFile = new File(dest, file);
            copyFolder(srcFile, destFile);
         }
      } else {
         InputStream in = new FileInputStream(src);
         OutputStream out = new FileOutputStream(dest);
         byte[] buffer = new byte[1024];

         while((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
         }

         in.close();
         out.close();
      }

   }
}
