package as4questionh;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JButton;
public class AddEmployees extends JFrame
{
    //Declaring variables necessary for data base connection
 
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private ResultSetMetaData rsMetaData;
    //Declaring variable necessary for GUI panel
    private Container container;
    private JTable table;
    private JTextField input;
    private JButton addSalariedEmployee, addCommissionEmployee, addBasePlusCommissionEmployee, addHourlyEmployee;
    public AddEmployees() {
        super("Add Employees");
        String url = "jdbc:mysql:employees";
        try {
            Class.forName("com.mysql.jdbc.DB2jDriver");
            connection = DriverManager.getConnection(url);
        } 
        catch (ClassNotFoundException ex) 
        {
            System.err.println("Failed to load JDBC driver");
            ex.printStackTrace();
            System.exit(1);//terminate program
        } 
        catch (SQLException ex) 
        {
            System.err.println("Unable to connect");
            ex.printStackTrace();
            System.exit(1);
        }
        // if conected to database, set up GUI
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JLabel("Enter query to insert employees:"));
        input = new JTextField(50);
        topPanel.add(input);
        input.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                addEmployee(input.getText());
            }
        }
        );
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout());
        addSalariedEmployee = new JButton("Add Salaried Employee");
        addSalariedEmployee.addActionListener(new ButtonHandler());
        addCommissionEmployee = new JButton("Add Commission Employee");
        addBasePlusCommissionEmployee = new JButton("Add Base Plus Commission Employee");
        addBasePlusCommissionEmployee.addActionListener(new ButtonHandler());
        addHourlyEmployee = new JButton("Add Hourly Employee");
        addHourlyEmployee.addActionListener(new ButtonHandler());
        // add four buttons to centerPanel
        centerPanel.add(addSalariedEmployee);
        centerPanel.add(addCommissionEmployee);
        centerPanel.add(addBasePlusCommissionEmployee);
        centerPanel.add(addHourlyEmployee);
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(topPanel, BorderLayout.NORTH);
        inputPanel.add(centerPanel, BorderLayout.CENTER);
        table = new JTable(4, 4);
        container = getContentPane();
        container.setLayout(new BorderLayout());
        container.add(inputPanel, BorderLayout.NORTH);
        container.add(table, BorderLayout.CENTER);
        getTable();
        setSize(800, 300);
        setVisible(true);
    }
//getTable method creates a database statement and tries to execute query to retrive all elements from employee table    
        private void getTable ()
        {
         try 
        {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM employees");
            displayResultSet(resultSet);
        } 
        catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }
    private void addEmployee(String query) 
    {
        try 
        {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            getTable();
        } 
        catch (SQLException sqlex) 
        {
            sqlex.printStackTrace();
        }
    }
    private void displayResultSet ( ResultSet rs )
            throws SQLException
    {
    // position to first record 
    boolean moreRecords = rs.next();
    if ( !moreRecords )
    {
        JOptionPane.showMessageDialog(this, "ResultSet contained no records");
        return;
    }
    Vector columnHeads = new Vector();
    Vector rows = new Vector();
        try
        {
            ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 1; i <= rsmd.getColumnCount(); ++i) 
        {
            columnHeads.addElement (rsmd.getColumnName(i));
        do 
        {
            rows.addElement(getNextRow(rs, rsmd));
        } 
        while (rs.next());
        // display table with resultset contents
        table = new JTable(rows, columnHeads);
        JScrollPane scroller = new JScrollPane( table );
        container.remove(1);
        container.add(scroller, BorderLayout.CENTER );
        container.validate();
        }
        }
        catch ( SQLException ex )
            {
             ex.printStackTrace();
            }
    }
        private Vector getNextRow( ResultSet rs, ResultSetMetaData rsmd ) 
        throws SQLException
        {
            Vector currentRow = new Vector();
            for ( int i = 1;i<= rsmd.getColumnCount();++i)
              switch(rsmd.getColumnType(i))
              {
                  case Types.VARCHAR:
                  case Types.LONGNVARCHAR:
                   currentRow.addElement(rs.getString(i));
                   break;
                  case Types.INTEGER:
                      currentRow.addElement(
                      new Long( rs.getLong(i)));
                      break;
                  case Types.REAL:
                     currentRow.addElement (new Float( rs.getDouble(i)));
                     break;
                  case Types.DATE:
                     currentRow.addElement (rs.getDate(i));
                     break;
                  default:
                       System.out.println("Type was:" + rsmd.getColumnTypeName(i));
                }
            return currentRow;
        } // end of method
        public void shutDown()
        {
            try
            {
                connection.close();
            }
            catch ( SQLException sqlex)
            {
              System.err.println("Unable to disconnect");
              sqlex.printStackTrace();
            }
        }
        public static void main( String[] args)
            {
                final AddEmployees application = new AddEmployees();
                application.addWindowListener( new WindowAdapter()
       {
            public void windowClosing( WindowEvent e)
            {
                application.shutDown();
                System.exit( 0 );
            }
            }
                );
            }
        // inner class ButtonHandler handle button event
        private class ButtonHandler
                implements ActionListener
        {
            public void actionPerformed( ActionEvent event )
            {
                String socialSecurityNumber = JOptionPane.showInputDialog( "Employee Social Security Number" );
                String insertQuery = "", displayQuery = "";
                if (event.getSource()
                        == addSalariedEmployee )
            {
                double weeklySalary = Double.parseDouble(JOptionPane.showInputDialog("Weekly Salary:"));
    insertQuery = "INSERT INTO salariedEmployees VALUES ( '" + socialSecurityNumber + "', '" + weeklySalary + "', '0' )";
    displayQuery = "SELECT employees.socialSecurityNumber, " + "employees.firstName, employees.lastName, " + 
            "employees.employeeType, salariedEmployees.weeklySalary" + "FROM employees, salariedEmployees WHERE " + 
            "employees.socialSecurityNumber = " +"salariedEmployees.socialSecurityNumber";
            }
                else if(event.getSource()
                        == addCommissionEmployee )
                {
                 int grossSales = Integer.parseInt (
                 JOptionPane.showInputDialog( "Gross Sales:"));
                 double commissionRate = Double.parseDouble(JOptionPane.showInputDialog("Commission Rate:"));
                 insertQuery = "INSERT INTO commissionEmployees VALUES ( '" + socialSecurityNumber + "','0' )";
                 displayQuery = "SELECT employees.socialSecurityNumber," + "employees.firstName, employees.lastName,"
            +" employees.employeesType,commissionEmployees.grossSales," +
            " commissionEmployees.commissionRate FROM employees," + " commissionEmployees WHERE employees.socialSecurityNumber="
             +" commissionEmployees.socialSecurityNumber";
                }
                else if ( event.getSource()
                    == addBasePlusCommissionEmployee )
                {
                    int grossSales = Integer.parseInt(JOptionPane.showInputDialog("Gross Sales:"));
                    double commissionRate = Double.parseDouble(JOptionPane.showInputDialog("Commission Rate:"));
                    double baseSalary = Double.parseDouble(JOptionPane.showInputDialog("Base Salary:"));
                    insertQuery = "INSERT INTO basePlusCommissionEmployees "+"VALUES ( '" + socialSecurityNumber + "','" 
                    + grossSales + " ', '" + commissionRate + "', '"+ baseSalary + "', '0')";
                    displayQuery = "SELECT employees.socialSecurityNumber,"+ 
                   "employees.firstName, employees.lastName, employees." + "employeeType, basePlusCommissionEmployees.baseSalary," + 
                     "basePlusCommissionEmployees.grossSales, basePlus"+
                   "CommissionEmployees.commissionRate FROM employees, " +"basePlusCommissionEmployees WHERE " 
                    + "employees.socialSecurityNumber = " + "basePlusCommissionEmployees.socialSecurityNumber";
                }
                //add hourly employees to table 
                else
                {
                  int hours = Integer.parseInt( JOptionPane.showInputDialog("Hours:" ));
                  double wage = Double.parseDouble(JOptionPane.showInputDialog("Wage:"));
                  insertQuery = "INSERT INTO hourlyEmployees VALUES ( '" + socialSecurityNumber + "', '0' )";
                  displayQuery = "SELECT employees.socialSecurityNumber, "+ "employees.firstName, employees.lastName,"+
              "employees.employeeType, hourlyEmployees.hours,"+ "hourlyEmployees.wage FROM employees,hourlyEmployees " + 
                  "WHERE employees.socialSecurityNumber =" + "hourlyEmployees.socialSecurityNumber";
                } 
                // execute insert query
                try 
                { 
                    statement = connection.createStatement();
                    statement.executeUpdate(insertQuery);
                    statement = connection.createStatement();
                    resultSet = statement.executeQuery( displayQuery);
                    displayResultSet(resultSet);
                }
                catch ( SQLException exception)
                {
                    exception.printStackTrace();
                }
            }
            
        }    
}

