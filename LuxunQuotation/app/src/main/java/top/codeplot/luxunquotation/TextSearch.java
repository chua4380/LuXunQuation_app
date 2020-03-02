package top.codeplot.luxunquotation;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TextSearch {
    private static char []text;
    private static String [][]title;
    private static int [][]index;
    private static int text_len;
    private static HashMap<Character, ArrayList<Integer>> char_map;

    public static String[][] getTitle() {
        return title;
    }

    public static String getText(int i) {
        return String.valueOf(Arrays.copyOfRange(text, index[i][0], index[i][1]));
    }

    public static void load_file(InputStream datafile, BufferedReader map_reader) {
        text = new char[4000000];
        title = new String[1161][2];
        index = new int[1161][2];
        try{
            int char_read = 0;
            Reader reader = null;
            reader = new InputStreamReader(datafile);
            while (true) {
                int cnt = reader.read(text, char_read, 2000000);
                if (cnt == -1)
                    break;
                char_read += cnt;
            }
            System.out.println(char_read);
            text_len = char_read;
        }catch(Exception e){
            e.printStackTrace();
        }
        try {
            //BufferedReader map_reader = new BufferedReader(new FileReader(mapfile));
            for (int i = 0; i < 1161; i++) {
                String tempString = map_reader.readLine();
                String[] tmp = tempString.split("\t");
                title[i][0] = tmp[0];
                title[i][1] = tmp[1];
                index[i][0] = Integer.parseInt(tmp[2]);
                index[i][1] = Integer.parseInt(tmp[3]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        char_map = new HashMap<>();
        int i = 0;
        for (char ch : text) {
            if (ch == '\u3000' || ch == '\n' || ch == ' '|| ch =='。'|| ch ==','|| ch ==':'|| ch =='!'|| ch =='“'|| ch =='?'|| ch =='…'|| ch =='”'|| ch =='《'|| ch =='》') {
                i ++;
                continue;
            }
            if (!char_map.containsKey(ch)) {
                char_map.put(ch, new ArrayList<Integer>());
            }
            char_map.get(ch).add(i);
            i += 1;
        }
    }

    private static void set_title(int id, Result r) {
        for (int i = 0; i < title.length; i++) {
            if (id < index[i][1]) {
                r.setVolume(title[i][0]);
                r.setTitle(title[i][1]);
                r.setId(i);
                return;
            }
        }
    }

    public static ArrayList<Result> search_text(String s_raw, int start, int max_record) {
        ArrayList<Result> result = new ArrayList<>();
        String s = "";
        for (int i = 0; i < s_raw.length(); i++) {
            char ch = s_raw.charAt(i);
            if (ch != ',' && ch != '《' && ch != '》' && ch != ' ') {
                s += ch;
            }
        }
        int last_id = 0;
        for (int i : char_map.get(s.charAt(0))) {
            if (start > 0) {
                start --; continue;
            }
            boolean flag = true;
            if (i < last_id) continue;
            int offset = 0;
            for (int j = 1; j-offset < s.length() && i + j < text_len; j++) {
                if (text[i + j] == '《' || text[i + j] == '》' || text[i + j] == '，' ) {
                    offset += 1;
                    continue;
                }
                if (s.charAt(j-offset) != text[i+j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                Result r = new Result();
                set_title(i, r);
                int st = i, ed = i + s.length();
                while (st > 0 && text[st] != '\u3000' && text[st] != ' ' && text[st] != '·'
                        && text[st] != '；' && text[st] != '！' && text[st] != '：' && text[st] != '。'
                        && text[st] != '\n' && text[st] != '？') {
                    st --;
                }
                while (ed < text_len && text[ed] != '\u3000' && text[ed] != ' ' && text[ed] != '·'
                        && text[ed] != '；' && text[ed] != '！' && text[ed] != '：' && text[ed] != '。'
                        && text[ed] != '\n' && text[ed] != '？') {
                    ed ++;
                }
                if (ed < text_len - 1) ed ++;
                r.setSentence(String.valueOf(Arrays.copyOfRange(text, st+2, ed)));
                result.add(r);
                max_record -= 1;
                last_id = ed;
                if (max_record <= 0) break;
            }
        }
        return result;
    }
}