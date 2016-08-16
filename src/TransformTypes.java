
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;


import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;

public class TransformTypes {
	
	public TransformTypes(){
		
	}
	public static final String FILETYPE_JPG = "jpg";
	public static final String SUFF_IMAGE = "." + FILETYPE_JPG;

/**
	 * 将指定pdf文件的首页转换为指定路径的缩略图
	 *@param filepath 原文件路径，例如d:/test.pdf
	 *@param imagepath 图片生成路径，例如 d:/test-1.jpg
	 *@param zoom     缩略图显示倍数，1表示不缩放，0.3则缩小到30%
	 */
	public void changePdfToImg2(String filepath, String imagepath, float zoom)
			throws PDFException, PDFSecurityException, IOException {
		// ICEpdf document class
		org.icepdf.core.pobjects.Document document = null;

		float rotation = 0f;

		document = new org.icepdf.core.pobjects.Document();
		document.setFile(filepath);
		// maxPages = document.getPageTree().getNumberOfPages();

		BufferedImage img = (BufferedImage) document.getPageImage(0,
				GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, rotation,
				zoom);

		Iterator iter = ImageIO.getImageWritersBySuffix(FILETYPE_JPG);
		ImageWriter writer = (ImageWriter) iter.next();
		File outFile = new File(imagepath+SUFF_IMAGE);
		FileOutputStream out = new FileOutputStream(outFile);
		ImageOutputStream outImage = ImageIO.createImageOutputStream(out);
		writer.setOutput(outImage);
		writer.write(new IIOImage(img, null, null));

		out.close();
	}
	
	public void changePdfToImg(File SrcPdfFile,String ImageOutPath, String type) {
		try {
			File file = SrcPdfFile;
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			FileChannel channel = raf.getChannel();
			MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,
					0, channel.size());
			PDFFile pdffile = new PDFFile(buf);
			for (int i = 1; i <= pdffile.getNumPages(); i++) {
				PDFPage page = pdffile.getPage(i);
				java.awt.Rectangle rect = new java.awt.Rectangle(0, 0, ((int) page.getBBox()
						.getWidth()), ((int) page.getBBox().getHeight()));
				java.awt.Image img = page.getImage(rect.width, rect.height, rect, null,true,true);
				BufferedImage tag = new BufferedImage(rect.width, rect.height,
						BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(img, 0, 0, rect.width ,
						rect.height, null);
				FileOutputStream out = new FileOutputStream(ImageOutPath+"\\" + i
						+ ".jpg"); // 输出到文件流
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				JPEGEncodeParam param2 = encoder.getDefaultJPEGEncodeParam(tag);
				param2.setQuality(1f, false);// 1f是提高生成的图片质量
				encoder.setJPEGEncodeParam(param2);
				encoder.encode(tag); // JPEG编码
				out.close();
				
				if(type.equals("bmp")){
					File tmpfile = new File(ImageOutPath+"\\" + i + ".jpg");
					if(tmpfile.exists()){
						List<File> files = new ArrayList<File>();
						files.add(tmpfile);
						try {
							this.picturetoBmp(files, tmpfile.getParent());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						tmpfile.delete();
					}
				}
				if(type.equals("png")){
					File tmpfile = new File(ImageOutPath+"\\" + i + ".jpg");
					if(tmpfile.exists()){
						List<File> files = new ArrayList<File>();
						files.add(tmpfile);
						try {
							this.picturetoPng(files, tmpfile.getParent());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						tmpfile.delete();
					}
				}
				if(type.equals("jpg/jpeg"))
					continue;
			}
			channel.close();
			raf.close();
			unmap(buf);// 如果要在转图片之后删除pdf，就必须要这个关闭流和清空缓冲的方法
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	　　* 清空缓冲
	　　* @param buffer
	　　*/
	@SuppressWarnings("unchecked")
	public static void unmap(final Object buffer) {
		AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				try {
					Method getCleanerMethod = buffer.getClass().getMethod(
							"cleaner", new Class[0]);
					getCleanerMethod.setAccessible(true);
					sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod
							.invoke(buffer, new Object[0]);
					cleaner.clean();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}
	
	/*
	 * images to pdf*/
	public void ImageToPdf(List<File> allFiles,String parentpath) throws Exception{
		// TODO Auto-generated method stub
		//setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		String path =parentpath.toString();
		int numpoint = path.indexOf(".");
		if(numpoint>0)
			path = path.substring(0, numpoint)+".pdf";
		else{
			File filetmp = new File(path);
			
			path = path+"/"+filetmp.getName()+".pdf";
		}
		Document pdfdocument = new Document();
		PdfWriter.getInstance(pdfdocument, new FileOutputStream(path));
		pdfdocument.addAuthor("Red hat");

		pdfdocument.addSubject("Picture To Pdf.");
		Image imageIn = null;
		List<Image> imageList = new ArrayList<Image>();
		float height = PageSize.A5.getHeight();
		float width = PageSize.A5.getWidth();
		
		for(int i=0;i<allFiles.size();i++){
			 imageIn = Image.getInstance(allFiles.get(i).getPath());
			 imageList.add(imageIn);
			 if(height<imageIn.getHeight())
				 height = imageIn.getHeight();
			 if(width<imageIn.getWidth())
				 width = imageIn.getWidth();
		}
		
		pdfdocument.setPageSize(new Rectangle(width,height+20));
		
		pdfdocument.open();
	
		pdfdocument.setMargins(0, 0, 0, 0);
		for(int i=0;i<imageList.size();i++)
			pdfdocument.add(imageList.get(i));
		pdfdocument.close();
		//setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}
//	protected static void translatefile(List<File> allFiles,String parentpath) throws Exception{
//		// TODO Auto-generated method stub
//		//setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//		String path =parentpath.toString();
//		int numpoint = path.indexOf(".");
//		if(numpoint>0)
//			path = path.substring(0, numpoint)+".pdf";
//		else{
//			File filetmp = new File(path);
//			
//			path = path+"/"+filetmp.getName()+".pdf";
//		}
//		Document pdfdocument = new Document();
//		PdfWriter.getInstance(pdfdocument, new FileOutputStream(path));
//		pdfdocument.addAuthor("Red hat");
//
//		pdfdocument.addSubject("Picture To Pdf.");
//		Image imageIn = null;
//		List<Image> imageList = new ArrayList<Image>();
//		float height = PageSize.A5.getHeight();
//		float width = PageSize.A5.getWidth();
//		
//		for(int i=0;i<allFiles.size();i++){
//			 imageIn = Image.getInstance(allFiles.get(i).getPath());
//			 imageList.add(imageIn);
//			 if(height<imageIn.getHeight())
//				 height = imageIn.getHeight();
//			 if(width<imageIn.getWidth())
//				 width = imageIn.getWidth();
//		}
//		
//		pdfdocument.setPageSize(new Rectangle(width,height+20));
//		
//		pdfdocument.open();
//	
//		pdfdocument.setMargins(0, 0, 0, 0);
//		for(int i=0;i<imageList.size();i++)
//			pdfdocument.add(imageList.get(i));
//		pdfdocument.close();
//		//setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//	}
	
	public void picturetoBmp(List<File> allFiles,String parentpath) throws Exception{
		BufferedImage src;
		OutputStream output = null;
		String targetFile;
		for(File sourcefile : allFiles){
			src	= ImageIO.read(sourcefile); 
			File parent = new File(parentpath);
			if(!parent.exists()){
				parent.createNewFile();
			}
			targetFile = parentpath+"//"+sourcefile.getName().split("\\.")[0]+".bmp";
	    	try {
				output = new FileOutputStream(targetFile,true);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	ImageIO.write(src, "bmp", output);
	    	src.flush();
	    	output.close();
		}
		
	}
	
	public void picturetoJpg(List<File> allFiles,String parentpath) throws Exception{
		BufferedImage src;
		OutputStream output = null;
		String targetFile;
		for(File sourcefile : allFiles){
			src	= ImageIO.read(sourcefile); 
			File parent = new File(parentpath);
			if(!parent.exists()){
				parent.createNewFile();
			}
			String[] tmp = sourcefile.getName().split("\\.");
			targetFile = parentpath+"//"+tmp[0]+".jpg";
	    	try {
				output = new FileOutputStream(targetFile,true);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	ImageIO.write(src, "jpg", output);
	    	src.flush();
	    	output.close();
		}
		
	}
	
	public void picturetoPng(List<File> allFiles,String parentpath) throws Exception{
		BufferedImage src;
		OutputStream output = null;
		String targetFile;
		for(File sourcefile : allFiles){
			src	= ImageIO.read(sourcefile); 
			File parent = new File(parentpath);
			if(!parent.exists()){
				parent.createNewFile();
			}
			targetFile = parentpath+"//"+sourcefile.getName().split("\\.")[0]+".png";
	    	try {
				output = new FileOutputStream(targetFile,true);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    	ImageIO.write(src, "png", output);
	    	src.flush();
	    	output.close();
		}
		
	}
}
