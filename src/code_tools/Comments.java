package code_tools;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.BufferedReader;
public class Comments {

    enum State {
        START,
        SLASH,
        COMMIT,
        SIMBOL_STAR,
        ONE_STR_COMMIT,
        BACKSLASH,
        CHAR_BODY,
        SPECIAL_SIMBOL_CHAR,
        STRING_BODY,
        SPECIAL_SIMBOL_STR
    };

    public static String string(String strToHandle) {
        StringBuilder builder = new StringBuilder();

        State state = State.START;// Initiate
        for (int i = 0; i < strToHandle.length(); ++i) {
            char c = strToHandle.charAt(i);
            switch (state) {
                case START:
                    if (c == '/') {
                        state = State.SLASH;
                    }else {
                        builder.append(c);
                        if(c=='\'') {
                            state=State.CHAR_BODY;
                        }else if(c=='\"') {
                            state=State.STRING_BODY;
                        }
                    }
                    break;
                case SLASH:
                    if (c == '*') {
                        state = State.COMMIT;
                    } else if (c == '/') {
                        state = State.ONE_STR_COMMIT;
                    } else {
                        builder.append('/');
                        builder.append(c);
                        state = State.START;
                    }
                    break;
                case COMMIT:
                    if(c=='*') {
                        state=State.SIMBOL_STAR;
                    }else {
                        if(c=='\n') {
                            builder.append("\r\n");
                        }
                        state=State.COMMIT;
                    }
                    break;
                case SIMBOL_STAR:
                    if(c=='/') {
                        state=State.START;
                    }else if(c=='*') {
                        state=State.SIMBOL_STAR;
                    }
                    else {
                        state=State.COMMIT;
                    }
                    break;
                case ONE_STR_COMMIT:
                    if(c=='\\') {
                        state=State.BACKSLASH;
                    }else if(c=='\n'){
                        builder.append("\r\n");
                        state=State.START;
                    }else {
                        state=State.ONE_STR_COMMIT;
                    }
                    break;
                case BACKSLASH:
                    if(c=='\\' || c=='\r'||c=='\n') {
                        if(c=='\n') {
                            builder.append("\r\n");
                        }
                        state=State.BACKSLASH;
                    }else {
                        state=State.ONE_STR_COMMIT;
                    }
                    break;
                case CHAR_BODY:
                    builder.append(c);
                    if(c=='\\') {
                        state=State.SPECIAL_SIMBOL_CHAR;
                    }else if(c=='\'') {
                        state=State.START;
                    }else {
                        state=State.CHAR_BODY;
                    }
                    break;
                case SPECIAL_SIMBOL_CHAR:
                    builder.append(c);
                    state=State.CHAR_BODY;
                    break;
                case STRING_BODY:
                    builder.append(c);
                    if(c=='\\') {
                        state=State.SPECIAL_SIMBOL_STR;
                    }else if(c=='\"') {
                        state=State.START;
                    }else {
                        state=State.STRING_BODY;
                    }
                    break;
                case SPECIAL_SIMBOL_STR:
                    builder.append(c);
                    state=State.STRING_BODY;
                    break;
                default:
                    break;
            }
        }
        return builder.toString();
    }


    public static String readFile(String inputFileName) {
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(inputFileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            String string;
            while ((string = reader.readLine()) != null) {
                builder.append(string);
                builder.append("\r\n");
            }
            reader.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return builder.toString();
    }

    public static void writeFile(String outputFileName, String strHandled) {
        try {
            FileOutputStream fos = new FileOutputStream(outputFileName);
            OutputStreamWriter dos = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(dos);
            writer.write(strHandled);
            writer.close();
            dos.close();
            fos.close();
            System.out.println("code that without note has been saved successfully in " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // Удаляемый файл с комментарием
        // System.out.println("The fileName that will be delete note:");
        String inputFileName = "input.txt";
        // Сохраняем файл «удалить код комментария»
        //  System.out.println("The fileName that will save code without note:");
        String outputFileName = "output.txt";

        String strToHandle = readFile(inputFileName);
        String strHandled = string(strToHandle);
        writeFile(outputFileName, strHandled);

    }

}