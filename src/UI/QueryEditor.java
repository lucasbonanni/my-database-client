package UI;

import syntax.SyntaxResult;
import syntax.SyntaxValidator;

import javax.swing.*;

public class QueryEditor extends JTextArea {

    private SyntaxValidator syntaxValidator;

    public QueryEditor(SyntaxValidator syntaxValidator){
        super();
        this.syntaxValidator = syntaxValidator;
    }

    public SyntaxResult IsValid(){
        return syntaxValidator.IsValid(this.GetQuery());
    }

    public String GetQuery()
    {
        return this.getText();
    }
}
