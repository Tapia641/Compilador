package LR;

public class Token {
    private String Id;
    private String lexema;
    private int tok;

    public String getId() {
        return Id;
    }

    public String getLexema() {
        return lexema;
    }

    public int getTok() {
        return tok;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public void setTok(int tok) {
        this.tok = tok;
    }


}
