package test;

import com.opensymphony.xwork2.ActionSupport;
import entity.PLU;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DemoAction extends ActionSupport {
    //表单文件,必须是一个file类型，此处的upload和表单中的name值必须是一样的
    private File upload;
    //上传的文件名,格式必须是File名称 + FileName
    private String uploadFileName;
    //上传的文件类型,格式必须是File名称 + ContentType
    private String uploadContentType;

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    //上传
    public String uploaderFile() throws IOException {
        System.out.println("上传过来的文件的文件名：" + getUploadFileName());
        if (getUploadFileName() != null) {
            // 获得工作簿
            Workbook workbook = null;
            if (getUploadFileName().endsWith("xls")) {
                //2003版
                workbook = new HSSFWorkbook(new FileInputStream(upload));
            } else if (getUploadFileName().endsWith("xlsx")) {
                //2007版
                workbook = new XSSFWorkbook(new FileInputStream(upload));
            }
            //工作表
            Sheet sheet = null;
            // 遍历所有工作表
            sheet = workbook.getSheetAt(0);//指操作第一张sheet表
            System.out.println("列数：" + sheet.getRow(0).getLastCellNum());
            if (sheet.getRow(0).getLastCellNum() != 10) {
//                            System.out.println("格式有误");
            } else {
                System.out.println("格式正确");
                // getLastRowNum，获取最后一行的行标，从0开始，但第一行是标题，所以此处从1开始
                for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
                    Row row = sheet.getRow(j);

                    PLU plu = new PLU();
                    if (row != null) {
                        //获取单元格数据
                        //Number
                        if (row.getCell(0) != null) {
//                                plu.setNumber(String.valueOf(goodsMaxNumber++));
                        }
                        //Name
                        if (row.getCell(1) != null) {
                            plu.setName(row.getCell(1).toString());
                        }
                        //Barcode
                        if (row.getCell(2) != null) {
                            plu.setBarcode(row.getCell(2).toString());
                        }
                        //Price
                        if (row.getCell(3) != null) {
                            plu.setPrice(row.getCell(3).toString());
                        }
                        //RRP
                        if (row.getCell(4) != null) {
                            plu.setRrp(row.getCell(4).toString());
                        }
                        //Tax_Index
                        if (row.getCell(5) != null) {
                            plu.setTax_Index(row.getCell(5).toString());
                        }
                        //Stock_Control
                        if (row.getCell(6) != null) {
                            plu.setStock_Control(row.getCell(6).toString());
                        }
                        //Stock_Amount
                        if (row.getCell(7) != null) {
                            plu.setStock_Amount(row.getCell(7).toString());
                        }
                        //Currency
                        if (row.getCell(8) != null) {
                            plu.setCurrency(row.getCell(8).toString());
                        }
                        //Used
                        if (row.getCell(9) != null) {
                            plu.setUsed(row.getCell(9).toString());
                        }
                        System.out.println(plu.toString());
                    }
                }
                workbook.close();
            }
        }


//        System.out.println("fileName:" + this.getUploadFileFileName());
//        System.out.println("contentType:" + this.getUploadFileContentType());
//        System.out.println("File:" + this.getUploadFile());
//        String path = ServletActionContext.getServletContext().getRealPath("/upload");
//        System.out.println("文件路径：" + path);
//        File file = new File(path);
//        if (!file.exists()) {        //如果路径不存在，则创建
//            file.mkdirs();
//        }
//
////        FileUtils.copyFile(uploadFile, new File(file, uploadFileFileName));//copy临时文件并保存
//        FileUtils.copyFile(uploadFile, new File(file, uploadFileFileName));//copy临时文件并保存
//        //如果提交过来的File不为null，才执行上传操作
//        if (uploadFile != null) {
//            String fileName = uploadFile.getName();
////            File file2 = new File(path + "\\" + uploadFileFileName);
//            File file2 = new File(uploadFile, uploadFileFileName);
//            //注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
//            //处理获取到的上传文件的文件名的路径部分，只保留文件名部分
////            fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);//如：1.xls
////					saveFileName=fileName;
//            System.out.println("上传过来的文件的文件名：" + file2);
//            if (fileName != null) {
////						File fullFile = new File(new String(fileItem.getName().getBytes(),
////								"utf-8")); // 解决文件名乱码问题
////                        File savedFile = new File(savePath, fileName);
////                        fileItem.write(savedFile);//保存
//                // 获得工作簿
//                Workbook workbook = null;
//                //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
////                if (file2.getName().endsWith("xls")) {
////                    //2003版
////                    workbook = new HSSFWorkbook(new FileInputStream(file2));
////                } else if (file2.getName().endsWith("xlsx")) {
////                    //2007版
////                    workbook = new XSSFWorkbook(new FileInputStream(file2));
////                }
//                if (this.getUploadFileFileName().endsWith("xls")) {
//                    //2003版
//                    workbook = new HSSFWorkbook(new FileInputStream(uploadFile));
//                } else if (this.getUploadFileFileName().endsWith("xlsx")) {
//                    //2007版
//                    workbook = new XSSFWorkbook(new FileInputStream(uploadFile));
//                }
//                //工作表
//                Sheet sheet = null;
//                // 遍历所有工作表
//                sheet = workbook.getSheetAt(0);//指操作第一张sheet表
//                System.out.println("列数：" + sheet.getRow(0).getLastCellNum());
//                if (sheet.getRow(0).getLastCellNum() != 10) {
////                            System.out.println("格式有误");
//                } else {
//                    System.out.println("格式正确");
//                    // getLastRowNum，获取最后一行的行标，从0开始，但第一行是标题，所以此处从1开始
//                    for (int j = 1; j < sheet.getLastRowNum() + 1; j++) {
//                        Row row = sheet.getRow(j);
//
//                        PLU plu = new PLU();
//                        if (row != null) {
//                            //获取单元格数据
//                            //Number
//                            if (row.getCell(0) != null) {
////                                plu.setNumber(String.valueOf(goodsMaxNumber++));
//                            }
//                            //Name
//                            if (row.getCell(1) != null) {
//                                plu.setName(row.getCell(1).toString());
//                            }
//                            //Barcode
//                            if (row.getCell(2) != null) {
//                                plu.setBarcode(row.getCell(2).toString());
//                            }
//                            //Price
//                            if (row.getCell(3) != null) {
//                                plu.setPrice(row.getCell(3).toString());
//                            }
//                            //RRP
//                            if (row.getCell(4) != null) {
//                                plu.setRrp(row.getCell(4).toString());
//                            }
//                            //Tax_Index
//                            if (row.getCell(5) != null) {
//                                plu.setTax_Index(row.getCell(5).toString());
//                            }
//                            //Stock_Control
//                            if (row.getCell(6) != null) {
//                                plu.setStock_Control(row.getCell(6).toString());
//                            }
//                            //Stock_Amount
//                            if (row.getCell(7) != null) {
//                                plu.setStock_Amount(row.getCell(7).toString());
//                            }
//                            //Currency
//                            if (row.getCell(8) != null) {
//                                plu.setCurrency(row.getCell(8).toString());
//                            }
//                            //Used
//                            if (row.getCell(9) != null) {
//                                plu.setUsed(row.getCell(9).toString());
//                            }
//                            System.out.println(plu.toString());
//                        }
//                    }
//                    workbook.close();
//                }
//            }
//        }
        return "success";
    }
}
