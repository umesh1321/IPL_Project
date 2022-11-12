import entity.Deliveries;
import entity.Matches;

import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Matches> listOfMatches = new ArrayList<>();
        ArrayList<Deliveries> listOfDeliveries = new ArrayList<>();

        //Reading File matches.csv
        BufferedReader sc = new BufferedReader(new FileReader("/home/umesh/IdeaProjects/IPL/Data/matches.csv"));
        String line="";
        String splitBy=",";
        while ((line=sc.readLine())!=null)  //returns a boolean value
        {
            String[] match=line.split(splitBy);
            Matches matches = new Matches();
            matches.setId(match[0]);
            matches.setSeason(match[1]);
            matches.setCity(match[2]);
            matches.setDate(match[3]);
            matches.setTeam1(match[4]);
            matches.setTeam2(match[5]);
            matches.setToss_winner(match[6]);
            matches.setToss_decision(match[7]);
            matches.setResult(match[8]);
            matches.setDl_applied(match[9]);
            matches.setWinner(match[10]);
            matches.setWin_by_runs(match[11]);
            matches.setWin_by_wickets(match[12]);
            matches.setPlayer_of_the_match(match[13]);
            matches.setVenue(match[14]);

            if(match.length>15) {
                matches.setUmpire1(match[15]);
            }

            if(match.length>16) {
                matches.setUmpire2(match[16]);
            }

            if(match.length>17) {
                matches.setUmpire3(match[17]);
            }
            listOfMatches.add(matches);

        }
        sc.close();


        //Reading file deliveries.csv
        sc = new BufferedReader(new FileReader("/home/umesh/IdeaProjects/IPL/Data/deliveries.csv"));
        String line1="";
        String splitBy1=",";
        while ((line1=sc.readLine())!=null)  //returns a boolean value
        {
            String[] delivery = line1.split(splitBy1);
            Deliveries deliveries = new Deliveries();
            deliveries.setMatch_id(delivery[0]);
            deliveries.setInnings(delivery[1]);
            deliveries.setBatting_team(delivery[2]);
            deliveries.setBowling_team(delivery[3]);
            deliveries.setOver(delivery[4]);
            deliveries.setBall(delivery[5]);
            deliveries.setBatsman(delivery[6]);
            deliveries.setNon_striker(delivery[7]);
            deliveries.setBowler(delivery[8]);
            deliveries.setIs_super_over(delivery[9]);
            deliveries.setWide_runs(delivery[10]);
            deliveries.setBye_runs(delivery[11]);
            deliveries.setLegbye_runs(delivery[12]);
            deliveries.setNoball_runs(delivery[13]);
            deliveries.setPenalty_runs(delivery[14]);
            deliveries.setBatsman(delivery[15]);
            deliveries.setExtra_runs(delivery[16]);
            deliveries.setTotal_runs(delivery[17]);

            if(delivery.length>18) {
                deliveries.setPlayer_dismissed(delivery[18]);
            }

            if(delivery.length>19) {
                deliveries.setDismissal_kind(delivery[19]);
            }

            if(delivery.length>20) {
                deliveries.setFielder(delivery[20]);
            }

            listOfDeliveries.add(deliveries);
        }
        sc.close();

        int n1 = listOfMatches.size();
        int n2 = listOfDeliveries.size();

        //Number of matches played per year of all the years in IPL
        TreeMap<String, Integer> map = new TreeMap<>();
        for(int i=1; i<n1;i++){
            String year = listOfMatches.get(i).getSeason();
            if(map.containsKey(year)){
                int of = map.get(year);
                int nf = of +1;
                map.put(year, nf);
            }
            else{
                map.put(year,1);
            }
        }

        System.out.println(map);


        //Number of matches won of all teams over all the years of IPL
        TreeMap<String, Integer> map1 = new TreeMap<>();
        for(int i=1; i<n1;i++){
            String team = listOfMatches.get(i).getWinner();
            if(team.equals("")){
                team = team + "no result";
            }
            if(map1.containsKey(team)){
                int of = map1.get(team);
                int nf = of +1;
                map1.put(team, nf);
            }
            else{
                map1.put(team, 1);
            }

        }

        System.out.println(map1);


        //For the year 2016 get the extra runs conceded per team.
        TreeMap<String, Integer> map2 = new TreeMap<>();
        for(int i=1; i<n2;i++){
            int id = Integer.parseInt(listOfDeliveries.get(i).getMatch_id());
            if(id>=577 && id<=636) {
                String team = listOfDeliveries.get(i).getBowling_team();
                int extraRuns = Integer.parseInt(listOfDeliveries.get(i).getExtra_runs());

                if (map2.containsKey(team)) {
                    int of = map2.get(team);
                    int nf = of + extraRuns;
                    map2.put(team, nf);
                } else {
                    map2.put(team, extraRuns);
                }
            }
        }
        System.out.println(map2);


        //For the year 2015 get the top economical bowlers.
        ArrayList<String> bowler = new ArrayList<>();
        ArrayList<Float> balls = new ArrayList<>();
        ArrayList<Float> totalRuns = new ArrayList<>();

        TreeMap<String, Integer> map3 = new TreeMap<>();//bowler,balls
        TreeMap<String, Float> map4 = new TreeMap<>();//boeler,total runs
        for(int i=1; i<n2;i++){
            int id = Integer.parseInt(listOfDeliveries.get(i).getMatch_id());
            if(id>=518 && id<=576){
               String bow = listOfDeliveries.get(i).getBowler();
               int ball = Integer.parseInt(listOfDeliveries.get(i).getBall());

               if(map3.containsKey(bow)){
                   int of = map3.get(bow);
                   int nf = of + 1;
                   map3.put(bow, nf);
               }
               else{
                   map3.put(bow, 1);
               }
            }
        }
        ArrayList<String> listOfkeys=new ArrayList<>();

        for(int i=1; i<n2;i++){
            int id = Integer.parseInt(listOfDeliveries.get(i).getMatch_id());
            if(id>=518 && id<=576){
                String bow = listOfDeliveries.get(i).getBowler();
                float totRuns = Float.parseFloat(listOfDeliveries.get(i).getTotal_runs());
                if(map4.containsKey(bow)){
                    float of = map4.get(bow);
                    float nf = of + Float.parseFloat(listOfDeliveries.get(i).getTotal_runs());
                    map4.put(bow, nf);
                }
                else{
                    map4.put(bow, Float.parseFloat(listOfDeliveries.get(i).getTotal_runs()));
                    listOfkeys.add(bow);
                }
            }
        }
        HashMap<String,String> map5=new HashMap<>();
        listOfkeys.forEach(key->{
            int map3Value = map3.get(key);
            float over = (float)(map3Value/6) + ((float)(map3Value %6))/6 ;
            float map4Value = (float) map4.get(key);
            Double economy =(double) map4Value/over;



            map5.put(key, String.format("%.2f",economy));

        });


        // sort map5 according to values.
        List<Map.Entry<String, String> > list =  new LinkedList<Map.Entry<String, String> >(map5.entrySet());


        Collections.sort(list, new Comparator<Map.Entry<String, String> >() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2)
            {
                Float f1 = Float.parseFloat(o1.getValue());
                Float f2 = Float.parseFloat(o2.getValue());

                return f1.compareTo(f2);
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, String> map6 = new LinkedHashMap<String, String>();
        for (Map.Entry<String, String> aa : list) {
            map6.put(aa.getKey(), aa.getValue());
        }

        System.out.println(map6);


        //in year 2008, list the teams that win the toss and the match as ell and count such events also.
       List<String> list1 = new ArrayList<>();
        for(int i=1; i<n1;i++) {
            int id = Integer.parseInt(listOfMatches.get(i).getId());
            if (id >= 60 && id <= 117) {
                String s1 = listOfMatches.get(i).getToss_winner();
                String s2 = listOfMatches.get(i).getWinner();
                if (s1.equals(s2)) {
                    list1.add(s1);
                }
            }
        }

        TreeMap<String, Integer> map7 = new TreeMap<>();
        for(int i=0; i<list1.size();i++){
            String str = list1.get(i);
            if(map7.containsKey(str)){
                int of = map7.get(str);
                int nf = of + 1;
                map7.put(str, nf);
            }
            else{
                map7.put(str,1);
            }
        }

        System.out.println(map7);


















    }
}