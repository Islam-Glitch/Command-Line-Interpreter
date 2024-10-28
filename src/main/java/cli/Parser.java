package cli;

public class Parser {
    private static boolean append = false;
    private static boolean redirect = false;
    private static boolean pipe = false;
    private String[] tokens;


    public static boolean getAppend(){
        return append;
    }
    public static boolean getRedirect(){
        return redirect;
    }
    public static boolean getPipe(){
        return pipe;
    }

    public String[] getTokens() {
        return tokens;
    }

    public void parse(String s){
//        String command;
//        String parameter = "";

        int redirecting = 0;    //use to know if you will append or rewrite

        for (int i = 0; i < s.length(); ++i){
            if (s.charAt(i) == '>'){
                redirecting += 1;
                redirect = true;
            }
            if (s.charAt(i) == '|'){
                pipe = true;
            }
        }

        if (redirecting == 2) append = true;

        tokens = s.trim().split("\\s+");


    }
}