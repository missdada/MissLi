package com.example.demo.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.common.util.ExcelImportUtils;
import com.example.demo.service.BatchImportService;
@Controller
@RequestMapping("/api")
public class TestController {

	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	@Autowired
	private BatchImportService batchImportService;

	// 文件上传
	@RequestMapping(value = "/upload")
	@ResponseBody
	public String upload(@RequestParam("test") MultipartFile file) {
		if (file.isEmpty()) {
			return "文件为空";
		}
		// 获取文件名
		String fileName = file.getOriginalFilename();
		logger.info("上传的文件名为：" + fileName);
		// 获取文件的后缀名
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		logger.info("上传的后缀名为：" + suffixName);
		// 文件上传后的路径
		String filePath = "D://test//";
		// 解决中文问题，liunx下中文路径，图片显示问题
		// fileName = UUID.randomUUID() + suffixName;
		File dest = new File(filePath + fileName);
		// 检测是否存在目录
		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}
		try {
			file.transferTo(dest);
			return "上传成功";
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "上传失败";
	}

	// 文件下载
	@RequestMapping("/download")
	public String downloadFile(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "233EB630-7DF7-422a-A3B5-E17D68676AC7.png";
		if (fileName != null) {
			// 当前是从该工程的WEB-INF//File//下获取文件(该目录可以在下面一行代码配置)然后下载到C:\\users\\downloads即本机的默认下载的目录
			// String realPath =
			// request.getServletContext().getRealPath("//WEB-INF//");
			String realPath = "D://test//";
			File file = new File(realPath, fileName);
			if (file.exists()) {
				response.setContentType("application/force-download");// 设置强制下载不打开
				response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while (i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					System.out.println("success");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fis != null) {
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return null;
	}

	// 多文件上传
	@RequestMapping(value = "/batch/upload", method = RequestMethod.POST)
	@ResponseBody
	public String handleFileUpload(HttpServletRequest request,MultipartFile[] file) {
		//List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
		MultipartFile[] files = file;
		MultipartFile filet = null;
		BufferedOutputStream stream = null;
		String filePath = "D://test//";
		for (int i = 0; i < files.length; ++i) {
			filet = files[i];
			if (!filet.isEmpty()) {
				try {
					byte[] bytes = filet.getBytes();
					String extName = filet.getOriginalFilename().substring(filet.getOriginalFilename().lastIndexOf("."));
					stream = new BufferedOutputStream(
							new FileOutputStream(new File(filePath + System.currentTimeMillis() + extName)));
					stream.write(bytes);
					stream.close();

				} catch (Exception e) {
					stream = null;
					return "You failed to upload " + i + " => " + e.getMessage();
				}
			} else {
				return "You failed to upload " + i + " because the file was empty.";
			}
		}
		return "upload successful";
	}
	
    //导入
    @PostMapping(value = "/batchImport")
    @ResponseBody//这个注释加不加都可以实现，加的话跳转重定向到固定页面，不加就是ajax返回
    public String batchImportUserKnowledge(@RequestParam(value="filename") MultipartFile file,
            HttpServletRequest request,HttpServletResponse response,HttpSession session
//            ,@SessionAttribute(Constants.ACCOUNT_SESSION_KEY) Account account
            ) throws IOException{
		
        //判断文件是否为空
        if(file==null){
       	 session.setAttribute("msg","文件不能为空！");
       	 return "redirect:toUserKnowledgeImport";
        }
        
        //获取文件名
        String fileName=file.getOriginalFilename();
        
        //验证文件名是否合格
        if(!ExcelImportUtils.validateExcel(fileName)){
       	 session.setAttribute("msg","文件必须是excel格式！");
       	 return "redirect:toUserKnowledgeImport";
        }
        
        //进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
        long size=file.getSize();
        if(StringUtils.isEmpty(fileName) || size==0){
       	 session.setAttribute("msg","文件不能为空！");
       	 return "redirect:toUserKnowledgeImport";
        }
        
        //批量导入
        String message = batchImportService.batchImport(fileName,file,"admin");
        session.setAttribute("msg",message);
        return "redirect:toUserKnowledgeImport";
    }

}
