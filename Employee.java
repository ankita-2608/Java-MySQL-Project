import net.proteanit.sql.DbUtils;

import java.sql.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Employee {
    private JPanel Main;
    private JTextField txtName;
    private JTextField txtSalary;
    private JTextField txtMobile;
    private JButton saveButton;
    private JTable table1;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JTextField txtId;
    private JScrollPane table_1;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Employee");
        frame.setContentPane(new Employee().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    Connection con;
    PreparedStatement pst;
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/rbcompnay", "root", "");
            System.out.println("success");
        }
        catch(ClassNotFoundException ex) {

        }
        catch (SQLException ex) {

        }
    }


    void table_load() {
        try {
            pst = con.prepareStatement("select * from employee");
            ResultSet rs = pst.executeQuery(); //this method will load all the details to the table.
            table1.setModel(DbUtils.resultSetToTableModel(rs));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Employee() {
        connect();
        table_load();
    saveButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        String EName, Salary, Phone;
        EName = txtName.getText();
        Salary = txtSalary.getText();
        Phone = txtMobile.getText();

        try {
            pst = con.prepareStatement("insert into employee(EName, Salary, Phone)values(?,?,?)");
            pst.setString(1, EName);
            pst.setString(2, Salary);
            pst.setString(3, Phone);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Record Added!!!"); //display of the message box after the record is added.
            table_load();
            txtName.setText("");
            txtSalary.setText("");
            txtMobile.setText("");
            txtName.requestFocus();
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        }
    });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String Id = txtId.getText();
                    pst = con.prepareStatement("select EName, Salary, Phone from employee where id = ?");
                    pst.setString(1, Id);
                    ResultSet rs = pst.executeQuery();

                    if(rs.next()==true) { //if the record is available
                        String EName = rs.getString(1);
                        String Salary = rs.getString(2);
                        String Phone = rs.getString(3);

                        txtName.setText(EName);
                        txtSalary.setText(Salary);
                        txtMobile.setText(Phone);
                    }
                    else {
                        txtName.setText("");
                        txtSalary.setText("");
                        txtMobile.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid Employee number!!");
                    }
                }
                catch (SQLException ex) {

                }



            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String EName, Salary, Phone, Id;
                EName = txtName.getText();
                Salary = txtSalary.getText();
                Phone = txtMobile.getText();
                Id = txtId.getText();
                try {
                    pst = con.prepareStatement("update employee set EName = ?, Salary = ?, Phone = ? where Id = ?");
                    pst.setString(1, EName);
                    pst.setString(2, Salary);
                    pst.setString(3, Phone);
                    pst.setString(4, Id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Updates!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }
                catch(SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Id;
                Id = txtId.getText();

                try {
                    pst = con.prepareStatement("delete from employee  where Id = ?");

                    pst.setString(1, Id);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Record Deleteeeeee!!!!!");
                    table_load();
                    txtName.setText("");
                    txtSalary.setText("");
                    txtMobile.setText("");
                    txtName.requestFocus();
                }

                catch (SQLException e1)
                {

                    e1.printStackTrace();
                }
            }

        });
    }
}
