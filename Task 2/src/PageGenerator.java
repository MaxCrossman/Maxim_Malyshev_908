import java.io.*;
import java.util.*;

public class PageGenerator {
    private static String messagesFolder = "src\\messages.csv";
    private static String usersFolder = "src\\users.csv";
    private static String postsFolder = "src\\posts.csv";
    private static String messagesPageFilename = "src\\pages\\messages.html";
    private static String feedPageFilename = "src\\pages\\feed.html";
    private static String idPageFilename = "src\\pages\\id.html";
    private static String errorPageFilename = "src\\pages\\404.html";
    private static int curUserId = 0;
    private static String path;

    public static void message() throws IOException {
        BufferedReader mesfr = new BufferedReader(new FileReader(messagesFolder));
        Scanner sc = new Scanner(mesfr);
        sc.nextLine();
        String[] cur;
        FileWriter mainfw = new FileWriter(messagesPageFilename);
        mainfw.write("<html>\n" +
                "<head>\n" +
                "    <title>Messages</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div>\n" +
                "        <font color=\"red\"><h1>Messages</h1></font>\n" +
                "        <hr color=\"red\">\n" +
                "    </div>\n");
        int messages = 0;
        LinkedList<LinkedList<String[]>> ll = new LinkedList<>();
        boolean found;
        while(sc.hasNextLine()) {
            cur = sc.nextLine().split(";");
            if (Integer.parseInt(cur[3]) == curUserId || Integer.parseInt(cur[4]) == curUserId) {
                if (Integer.parseInt(cur[4]) == curUserId) {
                    if (!ll.isEmpty()) {
                        found = false;
                        for (LinkedList<String[]> curll : ll) {
                            if (curll.getFirst()[0].equals(cur[3])) {
                                curll.add(new String[]{"get",cur[0],cur[1],cur[2],cur[3]});
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            ll.add(new LinkedList<String[]>());
                            ll.getLast().add(new String[]{cur[3]});
                            ll.getLast().add(new String[]{"get",cur[0],cur[1],cur[2],cur[3]});
                        }
                    } else {
                        ll.add(new LinkedList<String[]>());
                        ll.getLast().add(new String[]{cur[3]});
                        ll.getLast().add(new String[]{"get",cur[0],cur[1],cur[2],cur[3]});
                    }
                } else if (Integer.parseInt(cur[3]) == curUserId) {
                    if (!ll.isEmpty()) {
                        found = false;
                        for (LinkedList<String[]> curll : ll) {
                            if (curll.getFirst()[0].equals(cur[4])) {
                                curll.add(new String[]{"send", cur[0], cur[1], cur[2], cur[4]});
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            ll.add(new LinkedList<String[]>());
                            ll.getLast().add(new String[]{cur[4]});
                            ll.getLast().add(new String[]{"send", cur[0], cur[1], cur[2], cur[4]});
                        }
                    } else {
                        ll.add(new LinkedList<String[]>());
                        ll.getLast().add(new String[]{cur[4]});
                        ll.getLast().add(new String[]{"send", cur[0], cur[1], cur[2], cur[4]});
                    }
                }
            }
        }
        LinkedList<String[]> nll = new LinkedList<>();
        HashMap<Date,String[]> hm = new HashMap<>();
        Date curdate;
        String name;
        String id;
        String[] time;
        for (LinkedList<String[]> cll: ll) {
            FileWriter curfw = new FileWriter(cll.getFirst()[0]+".html");
            name = "";
            BufferedReader usfr = new BufferedReader(new FileReader(usersFolder));
            Scanner cursc = new Scanner(usfr);
            String[] curcur = new String[0];
            found = false;
            while (cursc.hasNextLine()) {
                curcur = cursc.nextLine().split(";");
                if (curcur[0].equals(cll.getFirst()[0])) {
                    name = curcur[1];
                    found = true;
                    break;
                }
            }
            if (!found) {
                error();
                return;
            }
            id = cll.getFirst()[0];
            usfr.close();
            cll.removeFirst();
            curfw.write("<html>\n" +
                    "<head>\n" +
                    "    <title>Messages</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div>\n" +
                    "        <font color=\"red\"><h1>Messages</h1></font>\n" +
                    "        <hr color=\"red\">\n" +
                    "    </div>\n" +
                    "    <div>\n" +
                    "        <font color=\"red\"><h2>"+name+"</h2></font>\n" +
                    "        <hr color=\"red\">\n" +
                    "    </div>\n");
            String date = "";
            for (String[] mas: cll) {
                if (!date.equals(mas[1])) {
                    date = mas[1];
                    curfw.write("   <font color=\"red\"><h4 align=\"center\"><i>" + date + "</i></h4></font>\n" +
                            "   <hr color=\"red\">\n");
                }
                if (mas[0].equals("send")) {
                    curfw.write("    <d  iv align=\"right\">\n" +
                            "        <font color=\"red\"></font> <sup>" + mas[2] + "</sup>   ");
                    for (int i = 0; i < mas[3].length(); i += 30) {
                        if (i+30 <= mas[3].length()) curfw.write("     " + mas[3].substring(i, i + 30) + "<br>");
                        else curfw.write("     " + mas[3].substring(i) + "<br>");
                    }
                    curfw.write("\n" +
                            "        </font>\n" +
                            "    </div>");
                } else if (mas[0].equals("get")) {
                    curfw.write("    <d  iv align=\"left\">\n" +
                            "        <font color=\"red\"></font> <sup>" + mas[2] + "</sup>   ");
                    for (int i = 0; i < mas[3].length(); i += 30) {
                        if (i+30 <= mas[3].length()) curfw.write(mas[3].substring(i, i + 30) + "<br>");
                        else curfw.write(mas[3].substring(i) + "<br>");
                    }
                    curfw.write("\n" +
                            "        </font>\n" +
                            "    </div>\n");
                }
            }
            curfw.write("</body>\n" +
                    "</html>");
            curfw.close();
            cur = new String[3];
            cur = cll.getLast()[1].split(".");
            time = cll.getLast()[2].split(":");
            Date da = new Date(Integer.parseInt(cur[2]), Integer.parseInt(cur[1]), Integer.parseInt(cur[0]), Integer.parseInt(time[0]), Integer.parseInt(time[1]), Integer.parseInt(time[2]));
            hm.put(da, new String[]{name, curcur[3].length() > 15 ? curcur[3].substring(0, 12) + "..." : curcur[3]});
        }
        Set<Date> hs = (Set<Date>) hm.keySet();
        for (Date d:hs) System.out.println(d.toString());
        int n = hs.size();
        Date max;
        for (int i = 0; i < n; i++) {
            max = new Date(0);
            for (Date cd : hs) {
                if (cd.compareTo(max) > 0) max = cd;
            }
            hs.remove(max);
            mainfw.write("    <a href=\"\"><div>\n" +
                    "        <font color=\"red\">\n" +
                    "            <big><b>" + hm.get(max)[0] + "</b></big><br><br>\n" +
                    "            <i>" + hm.get(max)[1] + "</i>  <sup>" + max.toString() + "</sup><br><br>\n" +
                    "            ________________________________\n" +
                    "        </font>\n" +
                    "    </div></a>\n");
        }
        mainfw.write("</body>\n" +
                "</html>");
        mainfw.close();
    }

    public static void feed() throws IOException {
        BufferedReader pfr = new BufferedReader(new FileReader(postsFolder));
        Scanner sc = new Scanner(pfr);
        sc.nextLine();
        String[] cur;
        FileWriter fw = new FileWriter(feedPageFilename);
        fw.write("<html>\n" +
                "<head>\n" +
                "    <title>Feed</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div>\n" +
                "        <font color=\"red\"><h1>Feed</h1></font>\n" +
                "        <hr color=\"red\">\n" +
                "    </div>\n");
        LinkedList<String[]> ll = new LinkedList<>();
        while (sc.hasNextLine()) {
            cur = sc.nextLine().split(";");
            if (Integer.parseInt(cur[3]) != (curUserId)) ll.add(cur);
        }
        pfr.close();
        int n = ll.size();
        String id;
        String name = "";
        BufferedReader fr;
        for (int i = 0; i < n; i++) {
            fr = new BufferedReader(new FileReader(usersFolder));
            sc = new Scanner(fr);
            sc.nextLine();
            while (sc.hasNextLine()) {
                cur = sc.nextLine().split(";");
                if (cur[0].equals(ll.getLast()[3])) {
                    name = cur[1];
                    break;
                }
            }
            fw.write("    <div>\n" +
                    "        <font color=\"red\">\n" +
                    "            <h3>" + name + "</h3><sup>" + ll.getLast()[0] + " " + ll.getLast()[1] + "</sup><br>\n" +
                    "            " + ll.getLast()[2] + "<br><br>\n" +
                    "            _____________________________\n" +
                    "        </font>\n" +
                    "    </div>\n");
            fr.close();
            ll.removeLast();
        }
        fw.write("</body>\n" +
                "</html>");
        fw.close();
    }

    public static void UserPage(int id) throws IOException {
        BufferedReader userfr = new BufferedReader(new FileReader(usersFolder));
        Scanner sc = new Scanner(userfr);
        sc.nextLine();
        String[] name = new String[0];
        boolean found = false;
        while(sc.hasNextLine()) {
            name = sc.nextLine().split(";");
            if (Integer.parseInt(name[0]) == id) {
                found = true;
                break;
            }
        }
        userfr.close();
        if (!found) {
            error();
            return;
        }
        FileWriter fw = new FileWriter(idPageFilename);
        fw.write("<html>\n" +
                "<head>\n" +
                "    <title>" + name[1] + "</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div>\n" +
                "        <font color=\"red\"><h1>" + name[1] + "</h1></font>\n" +
                "        <hr color=\"red\">\n" +
                "    </div>\n");
        BufferedReader pfr = new BufferedReader(new FileReader(postsFolder));
        sc = new Scanner(pfr);
        sc.nextLine();
        String[] cur;
        LinkedList<String[]> ll = new LinkedList<>();
        while (sc.hasNextLine()) {
            cur = sc.nextLine().split(";");
            if (Integer.parseInt(cur[3]) == id) {
                ll.add(cur);
            }
        }
        pfr.close();
        int n = ll.size();
        for (int i = 0; i < n; i++) {
            fw.write("    <div>\n" +
                    "        <font color=\"red\">\n" +
                    "            <h3>" + name[1] + "</h3><sup>" + ll.getLast()[0] + " " + ll.getLast()[1] + "</sup><br>\n" +
                    "            " + ll.getLast()[2] + "<br><br>\n" +
                    "            _____________________________\n" +
                    "        </font>\n" +
                    "    </div>\n");
            ll.removeLast();
        }
        fw.close();
    }

    public static void error() throws IOException {
        FileWriter fw = new FileWriter(errorPageFilename);
        fw.write("<html>\n" +
                "<head>\n" +
                "    <title>404</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div>\n" +
                "        <font color=\"red\"><h1>404</h1></font>\n" +
                "        <hr color=\"red\">\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>");
        fw.close();
    }


    public static void main(String[] args) throws IOException {
        File f = new File ( "data" );
        path = f.getAbsolutePath();
        message();
        feed();
        UserPage(0);
    }
}
