package syntax;

public class SyntaxResult {
    private boolean isValid;
    private String message;

    public SyntaxResult(boolean isValid, String message) {
        this.isValid = isValid;
        this.message = message;
    }


    public boolean IsValid(){
        return this.isValid;
    }

     public String GetMessage(){
        return this.message;
     }
}
