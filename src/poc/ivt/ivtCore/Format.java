package poc.ivt.ivtCore;

public class Format {

    public static boolean checkStringIsNumeric(String s){
        String str = s;
        boolean numeric = false;
        numeric = str.matches("-?\\d+(\\.\\d+)?");
        if(numeric) {
            numeric = true;
        }else{
            numeric = false;
        }
        return numeric;
        }
    }
