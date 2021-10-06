```java
//File temp = new File("../drivers/postgresql-42.2.24.jar");
        try {


            Class.forName("org.postgresql.Driver");
            //}
            //DriverManager.registerDriver()
            //jdbc:postgresql://{host}[:{port}]/[{database}]
            String url = "jdbc:postgresql://localhost:5432/bm_ar_tax_company00";
            Properties props = new Properties();
            props.setProperty("user","postgres");
            props.setProperty("password","-PhilBert34");
            //props.setProperty("ssl","true");
            //try {
            Connection conn = DriverManager.getConnection(url, props);
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT code, description FROM public.activity limit 5");
            while (rs.next())
            {
                //System.out.print("Column 1 returned ");
                System.out.println("code: " + rs.getString(1) + "  Description: "+ rs.getString(2));
            }
            rs.close();
            st.close();
            conn.close();
            //System.console().readLine();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

/*        String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
        Connection conn = DriverManager.getConnection(url);*/

```