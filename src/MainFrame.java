
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;

public class MainFrame extends JFrame {
	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		language = new Language("");
		MainFrame t = new MainFrame();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // 获取当前屏幕大小
		Dimension frameSize = t.getPreferredSize();// 获取当前窗口大小
		t.setLocation((screenSize.width - frameSize.width)/2, (screenSize.height - frameSize.height)/2);//保持窗口弹出位置
		t.setVisible(true);
	}
	private String jFolderPath="";
	private List<File> filelist=null;
	private TransformTypes transform;
	private static Language language;
	public MainFrame() {
		isTray();
		initcoment();
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		filelist = new ArrayList<File>();
		transform = new TransformTypes();
	}
	private void initcoment() {
		// TODO Auto-generated method stub
		lfrom = new JLabel();
		lto = new JLabel();
		lfilelist = new JLabel();
		lsavepath = new JLabel();
		jtsavepath = new JTextField();
		Cbfrom = new JComboBox();
		Cbto = new JComboBox();
		Cblanguage = new JComboBox();
		Tfilelist = new JTable();
		listpanel = new JScrollPane();
		btnadd = new JButton();
		btndel = new JButton();
		btnclear = new JButton();
		btnchsavepath = new JButton();
		btntranfrom = new JButton();
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(language.TITLE);
        setSize(new Dimension(280, 350));
       
		lfrom.setText(language.IMPORTTYPE);
		lfrom.setName("lfrom");
		
		lto.setText(language.EXPORTTYPE);
		lto.setName("lto");
		
		lfilelist.setText("File List:");
		lfilelist.setName("lfilelist");
		
		lsavepath.setText(language.SAVEPATH+" :");
		lsavepath.setName("lsavepath");
		
		jtsavepath.setName("jtsavepath");
		jtsavepath.setEditable(false);
		jtsavepath.setPreferredSize(new Dimension(300, 20));// 设置文本框大小
		
		//lsavepath.setPreferredSize(new Dimension(300,10));
		Cbfrom.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "pdf", "bmp", "jpg/jpeg", "png" }));
		Cbfrom.setName("Cbfrom");
		Cbfrom.setPreferredSize(new Dimension(80,22));
		Cbto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "pdf", "bmp", "jpg/jpeg", "png" }));
		Cbto.setName("Cbto");
		Cbto.setPreferredSize(new Dimension(80,22));
		
		Cblanguage.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "English", "中文"}));
		Cblanguage.setName("Cblanguage");
		Cblanguage.setPreferredSize(new Dimension(100,22));
		Cblanguage.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				changelanguage();
			}
			
		});
		
		Tfilelist.setModel(new DefaultTableModel(
				new Object [][] {},	new String [] {language.FILENAME}) {
			Class[] types = new Class[] { String.class };
			boolean[] canEdit = new boolean[] { false };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		Tfilelist.setName("Tfilelist");
		Tfilelist.getColumnModel().getColumn(0).setCellRenderer(new ModelDatabaseTableCellRenderer());
		Tfilelist.addMouseListener(
				new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						 Point mousepoint;                     
	                     mousepoint =e.getPoint();
	                     showview(Tfilelist.rowAtPoint(mousepoint)+1);
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
			
		});
		listpanel.setViewportView(Tfilelist);
		
		btnadd.setText(language.ADDFILE);
		btnadd.setName("btnadd");
		btnadd.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				addfiles();
			}
			
		});
		btnclear.setText(language.CLEARALL);
		btnclear.setName("btnclear");
		btnclear.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				clearAll();
			}
			
		});
		btndel.setText(language.DELETEFILE);
		btndel.setName("btndel");
		btndel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				deletefile();
			}
			
		});
		btnchsavepath.setText(language.SAVEPATH);
		btnchsavepath.setName("btnchsavepath");
		btnchsavepath.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				choosesavepath();
			}
			
		});
		btntranfrom.setText(language.TRANSFORM);
		btntranfrom.setName("btntranfrom");
		btntranfrom.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				transform();
			}
			
		});
		JPanel leftpanel = new JPanel(); 
		leftpanel.setLayout(new GridLayout(2,1,0,15));
		leftpanel.add(lfrom);
		leftpanel.add(lto);
		

		
		JPanel centerpanel = new JPanel(); 
		centerpanel.setLayout(new GridLayout(2,1,0,10));
		centerpanel.add(Cbfrom);
		centerpanel.add(Cbto);
		
		JPanel rightpanel = new JPanel(); 
		rightpanel.setLayout(new GridLayout(2,1,0,10));
		rightpanel.add(Cblanguage);
		rightpanel.add(new JLabel(" "));
		
		JPanel toppanel = new JPanel(); 
		toppanel.setLayout(new FlowLayout(FlowLayout.LEADING,0,10));
		toppanel.add(leftpanel);
		centerpanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));
		toppanel.add(centerpanel);
		rightpanel.setBorder(BorderFactory.createEmptyBorder(0, 200, 0, 0));
		toppanel.add(rightpanel,FlowLayout.RIGHT);
		
		JPanel topbpanel = new JPanel(); 
		topbpanel.setLayout(new FlowLayout(FlowLayout.LEADING,0,10));
		topbpanel.add(btnchsavepath);
		lsavepath.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0));
		topbpanel.add(lsavepath);
		topbpanel.add(jtsavepath);
		
		JPanel bottompanel = new JPanel(); 
		bottompanel.setLayout(new FlowLayout(FlowLayout.RIGHT,10,10));
		bottompanel.add(btnadd);
		bottompanel.add(btndel);
		bottompanel.add(btnclear);
		bottompanel.add(btntranfrom);
		
		JPanel myinfo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		Font myfont = new Font("\uff2d\uff33 \uff30\u30b4\u30b7\u30c3\u30af", Font.PLAIN, 9);
		JLabel label = new JLabel("Create by redhat.");
		label.setFont(myfont);
		myinfo.add(label);
		 javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		    getContentPane().setLayout(layout);
		    layout.setHorizontalGroup(
		        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup()
		        	.addContainerGap()
		            .addComponent(toppanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		            .addContainerGap())
		        .addGroup(layout.createSequentialGroup()
		        	.addContainerGap()
		            .addComponent(topbpanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		            .addContainerGap())
		        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
		            .addContainerGap()
		            .addComponent(listpanel)
		            .addContainerGap())
		        .addGroup(layout.createSequentialGroup()
		            .addContainerGap()
		            .addComponent(bottompanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		            .addContainerGap())
		            .addComponent(myinfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		    layout.setVerticalGroup(
		        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
		        .addGroup(layout.createSequentialGroup()
		        	.addComponent(toppanel)
		        	.addComponent(topbpanel)
		            .addComponent(listpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
		            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
		            .addComponent(bottompanel)
		            .addComponent(myinfo)
		            )
		    );
		    pack();
	}
	
	protected void clearAll() {
		// TODO Auto-generated method stub
		filelist.clear();
		Tfilelist.removeAll();
		resetTable();
	}
	protected void showview(int i) {
		// TODO Auto-generated method stub
		String path = this.Tfilelist.getValueAt(i, 0).toString();
        Tfilelist.setToolTipText("<html><center><img src="+path+"><width="+"140"+"></html>");  
	}
	protected void changelanguage() {
		// TODO Auto-generated method stub
		String str = this.Cblanguage.getSelectedItem().toString();
		if(str.equals("English")){
			this.language.changelanguage("E");
		}else{
			this.language.changelanguage("C");
		}
		this.setTitle(this.language.TITLE);
		this.btnadd.setText(this.language.ADDFILE);
		this.btndel.setText(this.language.DELETEFILE);
		this.btnchsavepath.setText(this.language.SAVEPATH);
		this.btntranfrom.setText(this.language.TRANSFORM);
		this.lfrom.setText(this.language.IMPORTTYPE);
		this.lto.setText(this.language.EXPORTTYPE);
		this.lsavepath.setText(this.language.SAVEPATH+" : ");
		Tfilelist.setModel(new DefaultTableModel(
				new Object [][] {},	new String [] {language.FILENAME}) {
			Class[] types = new Class[] { String.class };
			boolean[] canEdit = new boolean[] { false };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
	}
	protected void transform() {
		// TODO Auto-generated method stub
		String importtype = Cbfrom.getSelectedItem().toString();
		String exporttype = Cbto.getSelectedItem().toString();
		if(importtype.equals(exporttype)){
			JOptionPane.showMessageDialog(this.getContentPane(),
					"转换类型与输出类型相同，请更正！", "Warning", JOptionPane.INFORMATION_MESSAGE);
		}
		if(filelist==null||filelist.size()<=0){
			JOptionPane.showMessageDialog(this.getContentPane(),
					"没有转换文件！", "Warning", JOptionPane.INFORMATION_MESSAGE);			
		}
//		if(importtype.equals("pdf")&&exporttype.equals("jpg/jpeg")){
//			pdftojpg();
//		}
		if(exporttype.equals("pdf")){
			topdf(importtype);
		}
		if(exporttype.equals("jpg/jpeg")){
			tojpg(importtype);
		}
		if(exporttype.equals("png")){
			topng(importtype);
		}
		if(exporttype.equals("bmp")){
			tobmp(importtype);
		}
		JOptionPane.showMessageDialog(this.getContentPane(),
				"TransFrom Complate!", "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	private void tobmp(String importtype) {
		// TODO Auto-generated method stub
		if(importtype.equals("pdf")){
			pdftopicture("bmp");
			return;
		}
		String namepath = jtsavepath.getText().toString();
		try {
			transform.picturetoBmp(filelist, namepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void topng(String importtype) {
		// TODO Auto-generated method stub
		if(importtype.equals("pdf")){
			pdftopicture("png");
			return;
		}
		String namepath = jtsavepath.getText().toString();

		try {
			transform.picturetoPng(filelist, namepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void tojpg(String importtype) {
		// TODO Auto-generated method stub
		if(importtype.equals("pdf")){
			pdftopicture("jpg");
			return;
		}
		String namepath = jtsavepath.getText().toString();

		try {
			transform.picturetoJpg(filelist, namepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void topdf(String importtype) {
		// TODO Auto-generated method stub
		String namepath = jtsavepath.getText().toString();
		File filetmp;
		if(!namepath.endsWith("pdf")){
			for(int i=0;i<filelist.size();i++){
				filetmp = filelist.get(i);
				String[] tmpstr = filetmp.getName().split("\\.");
				namepath =namepath  + "\\"+tmpstr[0]+".pdf";
			}
		}
		try {
			transform.ImageToPdf(filelist, namepath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void pdftopicture(String type) {
		// TODO Auto-generated method stub
		File filetmp;
		for(int i=0;i<filelist.size();i++){
			filetmp = filelist.get(i);
			String[] tmpstr = filetmp.getName().split("\\.");
			String namepath = jtsavepath.getText().toString() + "\\"+tmpstr[0];
			System.out.println(namepath);
			File newfile = new File(namepath);
			if(!newfile.exists()){
				newfile.mkdir();
			}

			System.out.println(namepath);
//			try {
//				transform.changePdfToImg2(filetmp.getPath(),namepath,(float)1.0);
//			} catch (PDFException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (PDFSecurityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			transform.changePdfToImg(filetmp, namepath,type);
		}
	}
	protected void deletefile() {
		// TODO Auto-generated method stub
		int row = Tfilelist.getSelectedRow();
		if(row<0)
			return;
		filelist.remove(row);
		resetTable();
	}
	protected void choosesavepath() {
		// TODO Auto-generated method stub
		JFileChooser folderchoose = new JFileChooser();
		folderchoose.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		folderchoose.setMultiSelectionEnabled(true);
		folderchoose.showSaveDialog(null);
		File choosefile = folderchoose.getSelectedFile();
		jtsavepath.setText(choosefile.getPath());
		jFolderPath = choosefile.getPath();
		//lsavepath.setText("Save Path:"+choosefile[0].getPath());
	}
	protected void addfiles() {
		// TODO Auto-generated method stub
		File[] allFiles = null;
		JFileChooser folderchoose = new JFileChooser();
		//folderchoose.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		String[] ext1 = null;
		String filetype =  Cbfrom.getSelectedItem().toString();
		if(filetype.equals("pdf")){
			ext1 =new String[]{ "pdf file (*.pdf)", "pdf" };
		}
		if(filetype.equals("bmp")){
			ext1 =new String[]{ "bmp file (*.bmp)", "bmp" };
		}
		if(filetype.equals("png")){
			ext1 =new String[]{ "png file (*.png)", "png" };
		}
		if(filetype.equals("jpg/jpeg")){
			ext1 =new String[]{ "jpg file (*.jpg;*.jpeg)", "jpg" };
		}
		List<String[]> extensionInfo = new ArrayList<String[]>();
		
		extensionInfo.add(ext1);
		
		FileNameExtensionFilter filter = null;
		for (String[] extInfo : extensionInfo)
		{
			filter = new FileNameExtensionFilter(extInfo[0],
							extInfo[1].split(","));
			folderchoose.addChoosableFileFilter(filter);
			folderchoose.setFileFilter(filter);
		}
		
		folderchoose.setMultiSelectionEnabled(true);
		folderchoose.showOpenDialog(null);
		File[] choosefile = folderchoose.getSelectedFiles();
		if(choosefile==null||choosefile.length<=0)
			return;
		if(allFiles==null||allFiles.length>0)
			allFiles = null;
		allFiles = choosefile;
		for(int i=0;i<allFiles.length;i++){
			filelist.add(allFiles[i]);
		}
		resetTable();
	}
	private void resetTable() {
		// TODO Auto-generated method stub
		DefaultTableModel tableModel = (DefaultTableModel) Tfilelist
				.getModel();
		removeAllRows();
		for(int i=0;i<filelist.size();i++){
			tableModel.addRow(new Object[]{filelist.get(i).getPath()});
		}
	}
	private class ModelDatabaseTableCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent (
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            if (row % 2 == 0) {
                setBackground(Color.white);
               
            } else if (row % 2 == 1) {
                setBackground(new Color(232, 232, 232));
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
    public void removeAllRows () {
        DefaultTableModel model = (DefaultTableModel)Tfilelist.getModel();
        while (model.getRowCount() > 0)
            model.removeRow(0);
    }
    public void isTray()   
    {   
        try 
        {   
          if (SystemTray.isSupported())   
          {// 判断当前平台是否支持系统托盘   
            Image image = Toolkit.getDefaultToolkit().getImage(   
            		this.getClass().getResource("/resource/icon.png"));
           this.setIconImage(image);
          }   
        }   
        catch (Exception e)   
        {   
               
        }  
    }   
	private JLabel lfrom;
	private JLabel lto;
	private JLabel lfilelist;
	private JLabel lsavepath;
	private	JTextField jtsavepath;
	private JComboBox Cbfrom;
	private JComboBox Cbto;
	private JComboBox Cblanguage;
	private JTable Tfilelist;
	private JScrollPane listpanel;
	private JButton	btnadd;
	private JButton btndel;
	private JButton btnclear;
	private JButton btnchsavepath;
	private JButton btntranfrom;
}
