import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.util.Random;


public class WindowOperation extends JFrame {


    private JTextArea showArea;
    private JButton btnOk;
    private JButton btnGuess;
    private JFileChooser jfc;
    private JScrollPane jScrollPane1;
    private JButton chooseFile;
    private JTextField filepathArea;

    private String YES = "1";

    private String NO = "0";

    private JLabel jlImage = new JLabel();

    private File image;

    private Color color = new Color(255, 255, 255);

    private Random randNum = new Random();
    private int rand = randNum.nextInt();
    private String randStr = String.valueOf(rand);


    public WindowOperation() {
        getContentPane().setLayout(null);
        jfc = new JFileChooser("wenjian");


        btnOk = new JButton();
        getContentPane().add(btnOk);
        btnOk.setText("转换");
        btnOk.setBounds(530, 12, 60, 27);

        jScrollPane1 = new JScrollPane();
        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(458, 56, 300, 400);

        showArea = new JTextArea();

        jScrollPane1.setViewportView(showArea);
        showArea.setBounds(458, 56, 300, 400);

        filepathArea = new JTextField();
        getContentPane().add(filepathArea);
        filepathArea.setBounds(80, 12, 200, 27);

        chooseFile = new JButton();
        getContentPane().add(chooseFile);
        chooseFile.setText("打开");
        chooseFile.setBounds(278, 12, 60, 27);

        btnGuess = new JButton();
        getContentPane().add(btnGuess);
        btnGuess.setText("猜测");
        btnGuess.setBounds(630, 12, 60, 27);


        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().remove(jlImage);
                jfc.setDialogTitle("Open JPEG file");
                jfc.showOpenDialog(jfc);
                image = jfc.getSelectedFile();
                if (image == null)
                    return;
                String path = image.getAbsolutePath();
                filepathArea.setText(path);
                jlImage = new JLabel(new ImageIcon(image.getAbsolutePath()));
                getContentPane().add(jlImage);
                jlImage.setBounds(0, 56, 470, 334);
            }
        });


        btnOk.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int offset = 1;
                showArea.setText("");
                String str = "";

                BufferedImage bi = null;
                try {
                    bi = ImageIO.read(image);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                for (int i = offset / 2; i < bi.getWidth(); i += offset) {
                    str = "";
                    for (int j = offset / 2; j < bi.getHeight(); j += offset) {
                        int pixel = bi.getRGB(j, i);
                        if (pixel != color.getRGB()) {
                            str += YES;
                        } else {
                            str += NO;
                        }
                    }

                    showArea.append(str + "\n");
                    System.out.println(str);


                    try{

                        File f = new File("samples/test/"+randStr+"_"+randStr+".txt");
                        BufferedWriter output = new BufferedWriter(new FileWriter(f,true));
                        output.append(str+"\n");
                        output.close();
                    }
                    catch (IOException er)
                    {
                        er.printStackTrace();
                    }


                }

            }
        });


        btnGuess.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result[] = KNN.classify(randStr+"_"+randStr+".txt");
                JOptionPane.showConfirmDialog(null, "这是 " + result[1] + " 吗？", "确认对话框", JOptionPane.YES_NO_OPTION);
                rand = randNum.nextInt();
                randStr = String.valueOf(rand);
            }
        });

    }



}