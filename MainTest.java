package com.zit.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.zit.bean.Student;
import com.zit.dao.DaoUtil;
import com.zit.util.Util;
	     
public class MainTest {
	private DaoUtil daoUtil=new DaoUtil();
	
	@Test
	public void test01(){
		try {
			Reader reader=new FileReader("student.txt");
			BufferedReader br=new BufferedReader(reader);
			
			String str;
			Student stu=null;
			while( (str=br.readLine())!=null ){
				stu=new Student();
				String[] stuInfo=str.split("-");
				stu.setStuid(Integer.valueOf(stuInfo[0]));
				stu.setStuname(stuInfo[1]);
				
				Date birth=Util.setSqlDate(stuInfo[2], stuInfo[3], stuInfo[4]);
				stu.setBirth(birth);
				
				System.out.println(stu.toString());
				//System.out.println(daoUtil.add(stu));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test02(){
		String sql="select stuid,stuname,birth from tblstudent where stuname=?";
		List<Student> list=daoUtil.queryForList(Student.class, sql, "曹操");
		for (Student s : list) {
			s.setStuname("曹孟德");
			System.out.println(daoUtil.update(s));;
		}
	}
	
	@Test
	public void test03(){
		String sql="select stuid,stuname,birth from tblstudent where stuname=?";
		List<Student> list=daoUtil.queryForList(Student.class, sql, "庞统");
		for (Student s : list) {
			System.out.println(daoUtil.delete(s));;
		}
	}
	
	@Test
	public void test04(){
		String sql="select stuid,stuname,birth from tblstudent order by stuid";
		List<Student> list=daoUtil.queryForList(Student.class, sql);
		StringBuffer sb=new StringBuffer();
		for (Student s : list) {
			sb.append(s.getStuid()).append("-");
			sb.append(s.getStuname()).append("-");
			Date birth=new Date(s.getBirth().getTime());
			sb.append( Util.setDateStr(birth, "yyyy-MM-dd") ).append("\n");
		}
		//System.out.println(sb.toString());
		Util.write("student.txt", sb.toString());
	}
}
