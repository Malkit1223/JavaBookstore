/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapp4;
import java.sql.PreparedStatement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.JFrame;
/**
 *
 * @author MALKIT SINGH
 */
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class horror extends JPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3360/bookapp?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public horror() {
       setLayout(new GridLayout(0, 2,40,40));
this.setBackground(Color.WHITE);


        try (
               
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM bookadd WHERE category = 'horror'")) {

            int rowCount = 0;
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                String imageUrl = resultSet.getString("imageurl");

                Book book = new Book(name, imageUrl, price);
                JPanel panel = createBookPanel(book);
                     if (rowCount % 2 == 0) {
                    add(panel);
                } else {
                    JPanel row = (JPanel) getComponent(getComponentCount() - 1);
                    row.add(panel);
                }

                rowCount++;
            }
    
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
private JPanel createBookPanel(Book book) throws IOException {
     JPanel panel = new JPanel();
  panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
  panel.setBackground(Color.WHITE);
    // Create a border panel for the image
  
      JPanel imagePanel = new JPanel(new BorderLayout()); 
    imagePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Adjust color as needed
    imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS)); // Change layout to Y_AXIS
 imagePanel.setBackground(Color.WHITE);
 panel.add(Box.createVerticalStrut(20)); 
 
 
    // Add vertical strut for space
    panel.add(imagePanel);

    ImageIcon imageIcon = null;
    try {
        URL imageUrl = new URL(book.getImageUrl());
        Image image = ImageIO.read(imageUrl);
        Image resizedImage = image.getScaledInstance(190, -1, Image.SCALE_SMOOTH); // Adjust image size
        imageIcon = new ImageIcon(resizedImage);
    } catch (MalformedURLException e) {
        // Handle invalid image URL
    } catch (IOException e) {
        // Handle image loading errors
    } catch (NullPointerException e) {
        // Handle null image URL
    }

    if (imageIcon != null) {
        // Create a label for the image with a layout manager that centers the image
        JLabel imageLabel = new JLabel(imageIcon);
       
        imageLabel.setAlignmentY(JLabel.CENTER);

        // Add the image label to the imagePanel
        imagePanel.add(imageLabel);
panel.add(Box.createVerticalStrut(20));
        // Create a panel for the price and name
        JPanel priceNamePanel = new JPanel();
        priceNamePanel.setLayout(new BoxLayout(priceNamePanel, BoxLayout.Y_AXIS));
 priceNamePanel.setBackground(Color.white);
  
        // Add price to priceNamePanel
        JLabel priceLabel = new JLabel("$" + book.getPrice());
        priceLabel.setAlignmentY(JLabel.CENTER);
         priceLabel.setFont(new Font("MV boli",Font.BOLD,20));
         priceLabel.setForeground(Color.green);
     ImageIcon icon = new ImageIcon("your_icon_path.png"); // Adjust the path accordingly
        JLabel iconLabel = new JLabel(icon);
        priceNamePanel.add(priceLabel);
    
        // Add name to priceNamePanel
        
        JLabel nameLabel = new JLabel(book.getName());
        nameLabel.setAlignmentX(JLabel.CENTER);
        priceNamePanel.add(nameLabel);
        // Add priceNamePanel to panel
        JPanel wishlistButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
wishlistButtonPanel.setBackground(Color.WHITE);
nameLabel.setFont(new Font("MV boli",Font.BOLD,20));
   final boolean[] isAdded = {false};

JButton wishlistButton = new JButton("Add to Wishlist");
try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    String query = "SELECT status FROM bookadd WHERE name = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, book.getName());
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String status = resultSet.getString("status");
                if (status.equals("selected")) {
                    wishlistButton.setBackground(Color.RED);
                    wishlistButton.setText("Added to Wishlist");
                    isAdded[0] = true;
                }
            }
        }
    }
} catch (SQLException ex) {
    ex.printStackTrace();
}
  wishlistButton.setFont(new Font("MV boli", Font.BOLD, 15));
    wishlistButton.setPreferredSize(new Dimension(100, 30));
    wishlistButton.setHorizontalTextPosition(SwingConstants.CENTER);
    wishlistButton.setFocusable(false);
    wishlistButtonPanel.add(wishlistButton);
    

  priceNamePanel.add(wishlistButton);
  
 // Track if the book is already added to wishlist
wishlistButton.addActionListener(e -> {
    if (!isAdded[0]) {
        // Insert book into the wishlist table
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO wishlist (name, price, category) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, book.getName());
                preparedStatement.setInt(2, book.getPrice());
                preparedStatement.setString(3, "horror"); // Assuming category is "horror"
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Added to Wishlist", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        // Update status to 'selected' in the bookadd table
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String updateQuery = "UPDATE bookadd SET status = ? WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, "selected");
                preparedStatement.setString(2, book.getName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        // Change button text and color
        wishlistButton.setText("Added to Wishlist");
        wishlistButton.setBackground(Color.RED);
        isAdded[0] = true;
    } else {
        // Remove book from the wishlist table
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String deleteQuery = "DELETE FROM wishlist WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setString(1, book.getName());
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Removed from Wishlist", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        // Update status to 'notselected' in the bookadd table
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String updateQuery = "UPDATE bookadd SET status = ? WHERE name = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, "notselected");
                preparedStatement.setString(2, book.getName());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        // Revert button text and color
        wishlistButton.setText("Add to Wishlist");
        wishlistButton.setBackground(UIManager.getColor("Button.background")); // Revert to default color
        isAdded[0] = false;
    }
});
        panel.add(priceNamePanel);     
 
  JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Use FlowLayout with LEFT alignment
        buttonPanel.setBackground(Color.WHITE);
        
 buttonPanel.add(Box.createHorizontalStrut(140)); 
        // Add Buy button
        JButton buyButton = new JButton("Buy");
        buyButton.setFont(new Font("MV boli", Font.BOLD, 20));
        buttonPanel.add(buyButton);
         
buyButton.addActionListener(e -> {
            // Insert order into the database
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "INSERT INTO orders (name, price, category) VALUES (?, ?, ?)";
                try (
                        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, book.getName());
                    preparedStatement.setInt(2, book.getPrice());
                    preparedStatement.setString(3, "horror"); // Assuming category is "horror"
                    preparedStatement.executeUpdate();
                     JOptionPane.showMessageDialog(null, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    String query = "DELETE FROM bookadd WHERE name = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, book.getName());
        preparedStatement.executeUpdate();
       
    }
} catch (SQLException ex) {
    ex.printStackTrace();
}
});
        // Add Donate button
        JButton donateButton = new JButton("Donate");
        donateButton.setFont(new Font("MV boli", Font.BOLD, 20));
        buttonPanel.add(donateButton);
donateButton.addActionListener(e -> {
            // Insert order into the database
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "INSERT INTO donate (name, price, category) VALUES (?, ?, ?)";
                try (
                        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, book.getName());
                    preparedStatement.setInt(2, book.getPrice());
                    preparedStatement.setString(3, "horror"); // Assuming category is "horror"
                    preparedStatement.executeUpdate();
                     JOptionPane.showMessageDialog(null, "Thank You for your Donation", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
    String query = "DELETE FROM bookadd WHERE name = ?";
    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
        preparedStatement.setString(1, book.getName());
        preparedStatement.executeUpdate();
       
    }
} catch (SQLException ex) {
    ex.printStackTrace();
}
});
        // Add buttonPanel to priceNameButtonPanel
        priceNamePanel.add(buttonPanel);
       
     priceNamePanel.add(Box.createVerticalStrut(10));
      
    }

    return panel;
}
}
 class Book {
    private String name;
    private String imageUrl;
    private int price;

    public Book(String name, String imageUrl, int price) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }
}


class JavaApp4 {
   public static void main(String args[]) {
    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 600);

            JPanel topPanel = new JPanel();
            topPanel.setBackground(Color.BLACK);
            topPanel.setForeground(Color.WHITE);
            JLabel horrorLabel = new JLabel("Horror", SwingConstants.CENTER);
            horrorLabel.setFont(new Font("MV boli", Font.BOLD, 30));
            horrorLabel.setForeground(Color.WHITE);
            topPanel.add(horrorLabel);

            frame.getContentPane().add(topPanel, BorderLayout.NORTH);
            frame.getContentPane().add(new categories(), BorderLayout.CENTER);
  frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    });
}

}