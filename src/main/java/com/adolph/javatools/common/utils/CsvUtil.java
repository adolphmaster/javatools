package com.adolph.javatools.common.utils;

import com.xzixi.utils.archivetools.ArchiveTools;
import com.xzixi.utils.archivetools.enums.ArchiveType;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author adolph
 * @Date 2020/5/28 10:40
 * @Version 1.0
 * @Description
 **/
public class CsvUtil {
    public static void main(String[] args) throws Exception {
        List<String> listFile = new ArrayList<>();
        listFile.add("D:\\test1.csv");
        long l1 = System.currentTimeMillis();
        List<RecordData> list = new ArrayList<>();
        for(int i=0; i<4000000;i++){
            RecordData build = RecordData.builder().decisionType(i%2==0?"A":"B")
                    .acount(""+i).adjustResult(i%2==0?"是":"否")
                    .decisionEndDate("202"+i%10+"-"+i%2+""+i%10+"-"+i%2+""+i%10).decisionEndTime(i%2+""+i%10+":33:"+i%10+""+i%10).decisionResult(i%2==0?"是":"否").build();
            list.add(build);
        }
        long l2 = System.currentTimeMillis();
        System.out.println(l2-l1);
        try {
            csvWriteExport("D:\\test1.csv", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long l3 = System.currentTimeMillis();
        System.out.println(l3-l2);
        System.out.println(l3-l1);


        zipFiles(listFile, "D:\\test1.zip");
        long l4 = System.currentTimeMillis();
        System.out.println(l4-l3);

        Compress7z("D:\\test1.csv","D:\\test1.7z");
        long l5 = System.currentTimeMillis();
        System.out.println(l5-l4);

        /**
         * 压缩
         *
         * @param archiveName 压缩包绝对路径，不需要包含扩展名
         * @param archiveType 压缩格式
         * @param fileNames   压缩文件，支持通配符
         * @param timeout     超时时间，毫秒，必须大于0
         * @param options     7z命令的其他可选参数
         * @return            压缩包绝对路径
         */
        String compress = ArchiveTools.compress("D:\\test1", ArchiveType.DEFAULT, listFile, 100000L, null);
        System.out.println(System.currentTimeMillis()-l1+compress);
    }

    /**
     * @Author guoqi
     * @Date 2020/6/1 13:54
     * @Param [excelPath, dataList]
     * @return void
     * @Description CSV文件生成
     **/
    public static void csvWriteExport(String excelPath, List<RecordData> dataList) throws Exception {
        FileOutputStream fos = new FileOutputStream(excelPath);
        OutputStreamWriter osw = new OutputStreamWriter(fos, "GBK");

        CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader("账号","日期","时间","策略","结果","是否调额");
        CSVPrinter csvPrinter = new CSVPrinter(osw, csvFormat);
        //添加数据
        for (int n = 0; n < dataList.size(); n++) {
            if(n%500000==0){
                System.out.println("rows 【"+n+"】 add");
            }
            RecordData recordData = dataList.get(n);
            /*
             * csv的格式要求可以在每个字段前面或者后面加 "\t"
             * 或者前面加 "="
             */
            csvPrinter.printRecord(recordData.getAcount(), recordData.getDecisionEndDate(),
                    recordData.getDecisionEndTime(), recordData.getDecisionType(),
                    recordData.getDecisionResult(), recordData.getAdjustResult());
        }
        csvPrinter.flush();
        csvPrinter.close();
    }


    /**
     * @param fileRealPathList 待压缩的文件列表
     * @param zipFileRealPath  压缩后的文件名称
     * @return boolean
     * @throws :Exception
     * @Function: zipFiles
     * @Description: 多个文件的ZIP压缩
     */
    public static void zipFiles(List<String> fileRealPathList, String zipFileRealPath)
            throws IOException
    {
        FileOutputStream out = null;
        ZipOutputStream zipOut = null;
        try
        {
            // 根据文件路径构造一个文件实例
            File zipFile = new File(zipFileRealPath);
            // 判断目前文件是否存在,如果不存在,则新建一个
            if (!zipFile.exists())
            {
                zipFile.createNewFile();
            }
            // 根据文件路径构造一个文件输出流
            out = new FileOutputStream(zipFileRealPath);
            // 传入文件输出流对象,创建ZIP数据输出流对象
            zipOut = new ZipOutputStream(out);
            // 循环待压缩的文件列表
            for (String fileRealPath : fileRealPathList)
            {
                FileInputStream in = null;
                try
                {
                    File file = new File(fileRealPath);
                    if (!file.exists())
                    {
                        System.out.println("文件不存在");
                        throw new FileNotFoundException("文件不存在");
                    }

                    // 创建文件输入流对象
                    in = new FileInputStream(fileRealPath);
                    // 得到当前文件的文件名称
                    //判断操作系统
                    String separateCharacter = "";
                    String os = System.getProperty("os.name");
                    if (os.toLowerCase().startsWith("win"))
                    {
                        //windows操作系统
                        separateCharacter = "\\";
                    }
                    else
                    {
                        //非windows操作系统
                        separateCharacter = "/";
                    }
                    String fileName = fileRealPath.substring(
                            fileRealPath.lastIndexOf(separateCharacter) + 1, fileRealPath.length());
                    // 创建指向压缩原始文件的入口
                    ZipEntry entry = new ZipEntry(fileName);
                    zipOut.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber = 0;
                    byte[] buffer = new byte[512];
                    while ((nNumber = in.read(buffer)) != -1)
                    {
                        zipOut.write(buffer, 0, nNumber);
                    }
                }
                catch (IOException e)
                {
                    System.out.println("文件压缩异常-in，原因："+ e);
                    throw new IOException("文件压缩异常");
                }
                finally
                {
                    // 关闭创建的流对象
                    if (null != in)
                    {
                        in.close();
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("文件压缩异常-out，原因："+ e);
            throw new IOException("文件压缩异常");
        }
        finally
        {
            if (null != zipOut)
            {
                zipOut.close();
            }
            if (null != out)
            {
                out.close();
            }
        }
    }
    /**
     * 7z文件压缩
     *
     * @param inputFile  待压缩文件夹/文件名
     * @param outputFile 生成的压缩包名字
     */

    public static void Compress7z(String inputFile, String outputFile) throws Exception {
        File input = new File(inputFile);
        if (!input.exists()) {
            throw new Exception(input.getPath() + "待压缩文件不存在");
        }
        SevenZOutputFile out = new SevenZOutputFile(new File(outputFile));

        compress(out, input, null);
        out.close();
    }

    /**
     * @param name 压缩文件名，可以写为null保持默认
     */
    //递归压缩
    public static void compress(SevenZOutputFile out, File input, String name) throws IOException {
        if (name == null) {
            name = input.getName();
        }
        SevenZArchiveEntry entry = null;
        //如果路径为目录（文件夹）
        if (input.isDirectory()) {
            //取出文件夹中的文件（或子文件夹）
            File[] flist = input.listFiles();

            if (flist.length == 0)//如果文件夹为空，则只需在目的地.7z文件中写入一个目录进入
            {
                entry = out.createArchiveEntry(input,name + "/");
                out.putArchiveEntry(entry);
            } else//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            {
                for (int i = 0; i < flist.length; i++) {
                    compress(out, flist[i], name + "/" + flist[i].getName());
                }
            }
        } else//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入7z文件中
        {
            FileInputStream fos = new FileInputStream(input);
            BufferedInputStream bis = new BufferedInputStream(fos);
            entry = out.createArchiveEntry(input, name);
            out.putArchiveEntry(entry);
            int len = -1;
            //将源文件写入到7z文件中
            byte[] buf = new byte[102400000];
            while ((len = bis.read(buf)) != -1) {
                System.out.println("--"+len);
                out.write(buf, 0, len);
            }
            bis.close();
            fos.close();
            out.closeArchiveEntry();
        }
    }


}
