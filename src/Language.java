
public class Language {

	private static final String E_TITLE = "Image Transform";
	private static final String E_IMPORTTYPE = "Import file type: ";
	private static final String E_EXPORTTYPE= "Export file type: ";
	private static final String E_SAVEPATH = "Save Path";
	private static final String E_FILENAME = "File Name";
	private static final String E_ADDFILE = "Add";
	private static final String E_DELETEFILE = "Delete";
	private static final String E_TRANSFORM = "TransForm";
	
	private static final String C_TITLE = "图片转换器";
	private static final String C_IMPORTTYPE = "源文件类型: ";
	private static final String C_EXPORTTYPE= "输出文件类型: ";
	private static final String C_SAVEPATH = "保存路径";
	private static final String C_FILENAME = "文件名";
	private static final String C_ADDFILE = "添加";
	private static final String C_DELETEFILE = "删除";
	private static final String C_TRANSFORM = "转换";
	public Language(String type){
		changelanguage(type);
	}
	public void changelanguage(String type){
		if(type.equals("C")){
			TITLE = C_TITLE;
			IMPORTTYPE = C_IMPORTTYPE;
			EXPORTTYPE= C_EXPORTTYPE;
			SAVEPATH = C_SAVEPATH;
			FILENAME = C_FILENAME;
			ADDFILE = C_ADDFILE;
			DELETEFILE = C_DELETEFILE;
			TRANSFORM = C_TRANSFORM;
		}else{
			TITLE = E_TITLE;
			IMPORTTYPE = E_IMPORTTYPE;
			EXPORTTYPE= E_EXPORTTYPE;
			SAVEPATH = E_SAVEPATH;
			FILENAME = E_FILENAME;
			ADDFILE = E_ADDFILE;
			DELETEFILE = E_DELETEFILE;
			TRANSFORM = E_TRANSFORM;
		}
	}
	public String TITLE = "";
	public String IMPORTTYPE = "";
	public String EXPORTTYPE= "";
	public String SAVEPATH = "";
	public String FILENAME = "";
	public String ADDFILE = "";
	public String DELETEFILE = "";
	public String TRANSFORM = "";
}
