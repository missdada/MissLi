package com.example.demo.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.util.ExcelImportUtils;
import com.example.demo.service.BatchImportService;
@Component
public class BatchImportServiceImpl implements BatchImportService {
	

	@Override
	public String batchImport(String fileName, MultipartFile mfile, String userName) {
		File uploadDir = new  File("d:\\test");
	       //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
	       if (!uploadDir.exists()) uploadDir.mkdirs();
	       //新建一个文件
	       File tempFile = new File("d:\\test\\" + new Date().getTime() + ".xlsx"); 
	       //初始化输入流
	       InputStream is = null;  
	       try{
	    	   //将上传的文件写入新建的文件中
	    	   mfile.transferTo(tempFile);
	    	   
	    	   //根据新建的文件实例化输入流
	           is = new FileInputStream(tempFile);
	    	   
	    	   //根据版本选择创建Workbook的方式
	           Workbook wb = null;
	           //根据文件名判断文件是2003版本还是2007版本
	           if(ExcelImportUtils.isExcel2007(fileName)){
	        	  wb = new XSSFWorkbook(is); 
	           }else{
	        	  wb = new HSSFWorkbook(is); 
	           }
	           //根据excel里面的内容读取知识库信息
	           return readExcelValue(wb,userName,tempFile);
	      }catch(Exception e){
	          e.printStackTrace();
	      } finally{
	          if(is !=null)
	          {
	              try{
	                  is.close();
	              }catch(IOException e){
	                  is = null;    
	                  e.printStackTrace();  
	              }
	          }
	      }
     return "导入出错！请检查数据格式！";

	}
	
	/**
	   * 解析Excel里面的数据
	   * @param wb
	   * @return
	   */
	  private String readExcelValue(Workbook wb,String userName,File tempFile){
		  
		   //错误信息接收器
		   String errorMsg = "";
	       //得到第一个shell  
	       Sheet sheet=wb.getSheetAt(0);
	       //得到Excel的行数
	       int totalRows=sheet.getPhysicalNumberOfRows();
	       //总列数
		   int totalCells = 0; 
	       //得到Excel的列数(前提是有行数)，从第二行算起
	       if(totalRows>=2 && sheet.getRow(1) != null){
	            totalCells=sheet.getRow(1).getPhysicalNumberOfCells();
	       }
//	       List<DimEquipmentType> userKnowledgeBaseList=new ArrayList<DimEquipmentType>();
//	       DimEquipmentType tempUserKB;    
	       
	       String br = "<br/>";
	       
	       //循环Excel行数,从第二行开始。标题不入库
	       for(int r=1;r<totalRows;r++){
	    	   String rowMessage = "";
	           Row row = sheet.getRow(r);
	           if (row == null){
	        	   errorMsg += br+"第"+(r+1)+"行数据有问题，请仔细检查！";
	        	   continue;
	           }
//	           tempUserKB = new DimEquipmentType();
	           
	           String question = "";
	           String answer = "";
	           
	           //循环Excel的列
	           for(int c = 0; c <totalCells; c++){
	               Cell cell = row.getCell(c);
	               if (null != cell){
	                   if(c==0){
	                	   question = cell.getStringCellValue();
	                	   if(StringUtils.isEmpty(question)){
	                		   rowMessage += "问题不能为空；";
	                	   }else if(question.length()>60){
	                		   rowMessage += "问题的字数不能超过60；";
	                	   }
	                	  // tempUserKB.setName(question);
	                	   System.out.println("q:"+question);
	                   }else if(c==1){
	                	   answer = cell.getStringCellValue();
	                	   if(StringUtils.isEmpty(answer)){
	                		   rowMessage += "答案不能为空；";
	                	   }else if(answer.length()>1000){
	                		   rowMessage += "答案的字数不能超过1000；";
	                	   }
	                	  // tempUserKB.setPointName(answer);
	                	   System.out.println("a:"+answer);
	                   }
	               }else{
	            	   rowMessage += "第"+(c+1)+"列数据有问题，请仔细检查；";
	               }
	           }
	           //拼接每行的错误提示
	           if(!StringUtils.isEmpty(rowMessage)){
	        	   errorMsg += br+"第"+(r+1)+"行，"+rowMessage;
	           }else{
	        	   //userKnowledgeBaseList.add(tempUserKB);
	           }
	       }
	       
	       //删除上传的临时文件
	       if(tempFile.exists()){
	    	   tempFile.delete();
	       }
	       
	       //全部验证通过才导入到数据库
	       if(StringUtils.isEmpty(errorMsg)){
//	    	   for(DimEquipmentType userKnowledgeBase : userKnowledgeBaseList){
//	    		   mapper.insertSelective(userKnowledgeBase);
//	    	   }
//	    	   errorMsg = "导入成功，共"+userKnowledgeBaseList.size()+"条数据！";
	       }
	       return errorMsg;
	  }

}
