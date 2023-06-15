package com.neuedu.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 文件读取工具类
 * @author koala
 * @param <E>
 *
 */
public class FileOptUtil<E> {
	
	public final static String FILE_READ = "read";
	public final static String FILE_WRITE = "write";
	
	public final static String OPT_SUCCESS = "Success";
	public final static String OPT_SUCCESS_MSG = "成功";
	public final static String OPT_ERR_CREATEFILE = "CreateFileError";
	public final static String OPT_ERR_CREATEFILE_MSG = "创建文件错误";
	public final static String OPT_ERR_OPTTYPE = "OptTypeError";
	public final static String OPT_ERR_OPTTYPE_MSG = "读写操作类型错误";
	public final static String OPT_ERR_IO = "IOError";
	public final static String OPT_ERR_IO_MSG = "读写时错误";
	public final static String OPT_ERR_CLOSE = "CloseError";
	public final static String OPT_ERR_CLOSE_MSG = "释放资源错误";
	/**
	 *  文件读写操作 
	 * @param entityList  read时：装载结果List   write时： 要写入的内容  
	 * @param optType  读写标志 read： 读  write ： 写
	 * @param fileName  目标文件
	 * @return
	 * 		返回类型： Success : 成功
	 * 						CreateFileError： 创建文件错误
	 * 						OptTypeError： 读写操作类型错误
	 * 						IOError	： 读写时错误
	 * 						CloseError： 释放资源错误
	 */
	public String fileOption (List<E> entityList , String optType, Class<?> cls){
		String fileName = "";
		try {
			fileName = (String) cls.getField("ENTITY_FILENAME").get(cls);
		} catch (NoSuchFieldException | SecurityException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
		
		String optFlag = OPT_SUCCESS;
		BufferedReader br  = null ;
		BufferedWriter bw = null;
		FileReader reader = null ;
		FileWriter writer  = null ; 
		String path = this.getClass().getResource("/").getPath()+"com/neuedu/data/"+fileName+".date";
		System.out.println("文件路径" + path);
		File file = new File(path);
		
		if( ! file.exists() ){
			try {
				System.out.println("文件不存在， 创建文件");
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("创建文件失败");
				e.printStackTrace();
				optFlag = OPT_ERR_CREATEFILE;
				return optFlag;
			}
		}
		
		try {
			if("read".equals(optType)){
				String line="";
				reader = new FileReader(path);
				br = new BufferedReader(reader); 
				// 清空list 内容
				//	entityList = new ArrayList<E>();							
				while((line = br.readLine()) != null){
					entityList.add((E)JSONObject.parseObject( line , cls));
				}
				System.out.println(fileName+"读取成功 ， 文件长度为：" +  entityList.size() );
			}else if("write".equals(optType)){
				writer = new FileWriter(file);
				bw = new BufferedWriter(writer);
				for( int i = 0 ; i < entityList.size() ; i ++ ){
					bw.write(JSON.toJSONString(entityList.get(i)));
					bw.newLine(); 
				}
				System.out.println(fileName+"写入成功， 写入长度为： "+entityList.size());
				bw.flush();
			}else{
				System.out.println("文件操作类型错误！");
				optFlag=OPT_ERR_OPTTYPE;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			optFlag = OPT_ERR_IO;
		}
		finally
		{
			try
			{
				if(null != reader) reader.close();
				if(null != br) br.close();
				if(null != writer) writer.close();
				if(null != bw) bw.close();
			}
 			catch(IOException e) 
			{
				System.out.println(e.toString());
				optFlag = OPT_ERR_CLOSE;
			}
		}
		return optFlag;
	}
	
}

