package com.github.zzzzbw.sample;
import com.github.zzzzbw.core.annotation.Controller;
import com.github.zzzzbw.mvc.annotation.RequestMapping;
import com.github.zzzzbw.mvc.annotation.RequestMethod;
import com.github.zzzzbw.mvc.annotation.RequestParam;
import com.github.zzzzbw.mvc.annotation.ResponseBody;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


@Controller
@RequestMapping("/")
public class DoodleController {
    @RequestMapping()

    public String hello() {
        return "index.jsp";
    }

    @RequestMapping("index")
    public String index() {
        return "index.jsp";
    }
    private static final long serialVersionUID = 1L;

    @ResponseBody
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    public String store_file( @RequestParam("request") HttpServletRequest request) {
        try {


            ServletInputStream inputStream = request.getInputStream();
            Scanner scanner=new Scanner(inputStream);
           scanner.nextLine();
           String s=scanner.nextLine();
            int ori = s.indexOf("filename");
            String substring = s.substring(ori);
             int start= substring.indexOf("\"");
             int end=  substring.indexOf("\"",start+1);

             String fileName=substring.substring(start+1,end);
            System.out.println(fileName);

            File file=new File("d:\\file\\"+fileName);
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            PrintWriter printWriter=new PrintWriter(fileOutputStream);
            while (scanner.hasNextLine())
            {
                String s1=scanner.nextLine();
                if(s1.contains("Content-Type")||s1.contains("------"))
                {
                   if(s1.contains("Content-Type"))
                       scanner.nextLine();
                }
                else
                {
                    printWriter.println(s1);
                }

            }
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try {
//            String contextPath = request.getContextPath();
//            Map<String, String[]> parameterMap = request.getParameterMap();
//            String requestURI = request.getRequestURI();
//            Collection<Part> parts = request.getParts();
//            Part img = request.getPart("img");
//            String filepath = "D:\\file\\";
//            String file_name = img.getSubmittedFileName();
//            img.write(filepath + file_name);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ServletException e) {
//            e.printStackTrace();
//        }


//        if (!ServletFileUpload.isMultipartContent(request)) {
//            // 如果不是则停止
//            PrintWriter writer = response.getWriter();
//            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
//            writer.flush();
//            return "false";
//        }
//
//        // 配置上传参数
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
//        factory.setSizeThreshold(MEMORY_THRESHOLD);
//        // 设置临时存储目录
//        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
//
//        ServletFileUpload upload = new ServletFileUpload(factory);
//
//        // 设置最大文件上传值
//        upload.setFileSizeMax(MAX_FILE_SIZE);
//
//        // 设置最大请求值 (包含文件和表单数据)
//        upload.setSizeMax(MAX_REQUEST_SIZE);
//
//        // 中文处理
//        upload.setHeaderEncoding("UTF-8");
//
//        // 构造临时路径来存储上传的文件
//        // 这个路径相对当前应用的目录
//        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;
//
//
//        // 如果目录不存在则创建
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
//
//        try {
//            // 解析请求的内容提取文件数据
//            @SuppressWarnings("unchecked")
//            List<FileItem> formItems = upload.parseRequest(request);
//
//            if (formItems != null && formItems.size() > 0) {
//                // 迭代表单数据
//                for (FileItem item : formItems) {
//                    // 处理不在表单中的字段
//                    if (!item.isFormField()) {
//                        String fileName = new File(item.getName()).getName();
//                        String filePath = uploadPath + File.separator + fileName;
//                        File storeFile = new File(filePath);
//                        // 在控制台输出文件的上传路径
//                        System.out.println(filePath);
//                        // 保存文件到硬盘
//                        item.write(storeFile);
//                        request.setAttribute("message",
//                                "文件上传成功!");
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            request.setAttribute("message",
//                    "错误信息: " + ex.getMessage());
//        }
//        // 跳转到 message.jsp
//        request.getServletContext().getRequestDispatcher("/message.jsp").forward(
//                request, response);
        return "OK";

    }
}

