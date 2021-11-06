package syntax;

public class SyntaxValidator {

    public SyntaxResult IsValid(String query){
       boolean isValid = query.endsWith(";");
       String message = new String();
       if(!isValid)
       {
           return  new SyntaxResult(isValid,"Le falta el punto y coma al final de la sentencia");
       }
       return new SyntaxResult(isValid,message);
    }
}
