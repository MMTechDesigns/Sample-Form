package com.mmtech.mysample;

import java.awt.Dimension;
import java.awt.Toolkit;

public class MySampleApplication {

    SampleFrame sampleframe;
    /*final private EntityManagerFactory factory;
    final private EntityManager em;*/
    
    public MySampleApplication() {
        
        /**
         * uses ToolKit and screen size to set up and display JFrame Class SampleFrame
         */        
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int frameWidth = 1097;//(int)(screensize.getWidth()*0.75);
        int frameHeight = (int)(screensize.getHeight()*0.75);
        
        sampleframe = new SampleFrame();
        sampleframe.setTitle("Sample App V3.0");
        sampleframe.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        sampleframe.setPreferredSize(new Dimension(frameWidth,frameHeight));
        sampleframe.setSize(new Dimension(frameWidth,frameHeight));
        sampleframe.pack();
        sampleframe.setLocationRelativeTo(null);
        sampleframe.setVisible(true);
    }
       
    public static void main(String[] args) {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MySampleApplication main = new MySampleApplication();
            }
        });
    }
    
}
