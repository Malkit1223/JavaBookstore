/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapp4;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 *
 * @author MALKIT SINGH
 */
public class LoadingBar{
    private JFrame mainFrame;
    private JFrame loadingFrame;
    private JProgressBar progressBar;

    public LoadingBar() {

    

   
    
                loadingFrame = new JFrame("Loading Frame");
               loadingFrame.setResizable(false);
                loadingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                loadingFrame.getContentPane().setLayout(null);
 loadingFrame.setSize(770,500);
loadingFrame.getRootPane().setBorder(BorderFactory.createLineBorder(Color.green, 3));
loadingFrame.setLocationRelativeTo(null);
JLabel loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
loadingLabel.setFont(new Font("Arial", Font.BOLD, 18));
loadingLabel.setBounds(0, 260, loadingFrame.getWidth(), 30);
loadingFrame.getContentPane().add(loadingLabel);
 
JLabel imagelabel = new JLabel("", new ImageIcon("C:\\Users\\MALKIT SINGH\\Desktop\\java app\\javaapp4\\src\\javaapp4\\R.gif"),SwingConstants.CENTER);

     
       imagelabel.setBounds(0, -30, 770, 250); // Set bounds based on scaled height
        loadingFrame.getContentPane().add(imagelabel);
                progressBar = new JProgressBar();
                
         int progressBarWidth = progressBar.getPreferredSize().width;
int progressBarHeight = progressBar.getPreferredSize().height;
int horizontalCenter = (loadingFrame.getWidth() - progressBarWidth) / 2;
int verticalCenter = (loadingFrame.getHeight() - progressBarHeight) / 2;
progressBar.setBounds(horizontalCenter, verticalCenter, progressBarWidth, progressBarHeight);
                progressBar.setStringPainted(true); // Display the progress percentage
                loadingFrame.getContentPane().add(progressBar);

                new Thread(() -> {
                    for (int i = 0; i <= 100; i++) {
                        final int value = i;
                        SwingUtilities.invokeLater(() -> progressBar.setValue(value));
                        try {
                            Thread.sleep(10); // Simulate work being done
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    SwingUtilities.invokeLater(() -> {
                        loadingFrame.dispose(); // Close the loading frame
                        showNewFrame(); // Show the new frame
                    });
                }).start();

                loadingFrame.setVisible(true);
            }
      

     
    

    public void showNewFrame() {
      homepage1 page=new homepage1();
        
    }
}