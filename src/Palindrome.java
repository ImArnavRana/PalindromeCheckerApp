import java.util.*;

public class Palindrome {

    /* =========================
       Program 1: Username Checker
       ========================= */

    static class UsernameChecker {

        HashMap<String,Integer> users = new HashMap<>();
        HashMap<String,Integer> attempts = new HashMap<>();

        boolean checkAvailability(String username){
            attempts.put(username, attempts.getOrDefault(username,0)+1);
            return !users.containsKey(username);
        }

        void register(String username,int id){
            users.put(username,id);
        }

        List<String> suggestAlternatives(String username){

            List<String> list = new ArrayList<>();

            for(int i=1;i<=3;i++){
                String s = username + i;
                if(!users.containsKey(s)){
                    list.add(s);
                }
            }

            String mod = username.replace("_",".");
            if(!users.containsKey(mod)){
                list.add(mod);
            }

            return list;
        }

        String getMostAttempted(){

            String result="";
            int max=0;

            for(String u:attempts.keySet()){

                if(attempts.get(u)>max){
                    max=attempts.get(u);
                    result=u;
                }
            }

            return result;
        }
    }


    /* =========================
       Program 2: Inventory Manager
       ========================= */

    static class InventoryManager{

        HashMap<String,Integer> stock = new HashMap<>();
        HashMap<String,Queue<Integer>> waiting = new HashMap<>();

        void addProduct(String id,int qty){

            stock.put(id,qty);
            waiting.put(id,new LinkedList<>());
        }

        int checkStock(String id){
            return stock.getOrDefault(id,0);
        }

        synchronized String purchaseItem(String id,int user){

            int available = stock.getOrDefault(id,0);

            if(available>0){
                stock.put(id,available-1);
                return "Success, remaining "+(available-1);
            }

            Queue<Integer> q = waiting.get(id);
            q.add(user);

            return "Added to waiting list position "+q.size();
        }
    }


    /* =========================
       Program 3: DNS Cache
       ========================= */

    static class DNSEntry{

        String ip;
        long expiry;

        DNSEntry(String ip,long ttl){

            this.ip = ip;
            expiry = System.currentTimeMillis()+ttl;
        }
    }

    static class DNSCache{

        HashMap<String,DNSEntry> cache = new HashMap<>();

        int hits=0;
        int misses=0;

        String resolve(String domain){

            DNSEntry e = cache.get(domain);

            if(e!=null && e.expiry>System.currentTimeMillis()){
                hits++;
                return e.ip;
            }

            misses++;

            String ip = "172.217.14."+new Random().nextInt(200);

            cache.put(domain,new DNSEntry(ip,300000));

            return ip;
        }

        void getCacheStats(){

            int total = hits+misses;

            double rate = total==0?0:(hits*100.0/total);

            System.out.println("Hit Rate: "+rate+"%");
        }
    }


    /* =========================
       Program 4: Plagiarism Detector
       ========================= */

    static class PlagiarismDetector{

        HashMap<String,Set<String>> index = new HashMap<>();

        List<String> extract(String text){

            String[] words = text.split(" ");

            List<String> grams = new ArrayList<>();

            for(int i=0;i<=words.length-5;i++){

                StringBuilder g = new StringBuilder();

                for(int j=0;j<5;j++){
                    g.append(words[i+j]).append(" ");
                }

                grams.add(g.toString().trim());
            }

            return grams;
        }

        void addDocument(String id,String text){

            List<String> grams = extract(text);

            for(String g:grams){

                index.putIfAbsent(g,new HashSet<>());

                index.get(g).add(id);
            }
        }

        void analyze(String text){

            List<String> grams = extract(text);

            HashMap<String,Integer> match = new HashMap<>();

            for(String g:grams){

                if(index.containsKey(g)){

                    for(String doc:index.get(g)){

                        match.put(doc,match.getOrDefault(doc,0)+1);
                    }
                }
            }

            for(String doc:match.keySet()){

                double sim = match.get(doc)*100.0/grams.size();

                System.out.println(doc+" similarity "+sim+"%");
            }
        }
    }


    /* =========================
       Program 5: Website Analytics
       ========================= */

    static class Analytics{

        HashMap<String,Integer> pageViews = new HashMap<>();
        HashMap<String,Set<String>> unique = new HashMap<>();
        HashMap<String,Integer> sources = new HashMap<>();

        void processEvent(String url,String user,String source){

            pageViews.put(url,pageViews.getOrDefault(url,0)+1);

            unique.putIfAbsent(url,new HashSet<>());

            unique.get(url).add(user);

            sources.put(source,sources.getOrDefault(source,0)+1);
        }

        void getDashboard(){

            for(String p:pageViews.keySet()){

                System.out.println(p+" views "+pageViews.get(p)
                        +" unique "+unique.get(p).size());
            }
        }
    }


    /* =========================
       Program 6: Rate Limiter
       ========================= */

    static class TokenBucket{

        int tokens;
        int max;
        long last;

        TokenBucket(int max){

            this.max=max;
            tokens=max;
            last=System.currentTimeMillis();
        }
    }

    static class RateLimiter{

        HashMap<String,TokenBucket> clients = new HashMap<>();

        boolean checkRateLimit(String id){

            clients.putIfAbsent(id,new TokenBucket(1000));

            TokenBucket b = clients.get(id);

            long now = System.currentTimeMillis();

            if(now-b.last>3600000){

                b.tokens=b.max;
                b.last=now;
            }

            if(b.tokens>0){

                b.tokens--;
                return true;
            }

            return false;
        }
    }


    /* =========================
       Program 7: Autocomplete
       ========================= */

    static class Autocomplete{

        HashMap<String,Integer> queries = new HashMap<>();

        void updateFrequency(String q){

            queries.put(q,queries.getOrDefault(q,0)+1);
        }

        List<String> search(String prefix){

            List<String> result = new ArrayList<>();

            for(String q:queries.keySet()){

                if(q.startsWith(prefix)){
                    result.add(q);
                }
            }

            return result;
        }
    }


    /* =========================
       Program 8: Parking Lot
       ========================= */

    static class ParkingLot{

        String[] table;
        int size;

        ParkingLot(int s){

            size=s;
            table=new String[size];
        }

        int hash(String plate){
            return Math.abs(plate.hashCode())%size;
        }

        int parkVehicle(String plate){

            int i = hash(plate);

            while(table[i]!=null){
                i=(i+1)%size;
            }

            table[i]=plate;

            return i;
        }

        void exitVehicle(String plate){

            for(int i=0;i<size;i++){

                if(plate.equals(table[i])){
                    table[i]=null;
                }
            }
        }
    }


    /* =========================
       Program 9: Two Sum Fraud Detection
       ========================= */

    static class Transaction{

        int id;
        int amount;

        Transaction(int id,int amount){

            this.id=id;
            this.amount=amount;
        }
    }

    static class FraudDetection{

        List<String> twoSum(List<Transaction> list,int target){

            HashMap<Integer,Integer> map = new HashMap<>();

            List<String> res = new ArrayList<>();

            for(Transaction t:list){

                int comp = target - t.amount;

                if(map.containsKey(comp)){
                    res.add(map.get(comp)+" + "+t.id);
                }

                map.put(t.amount,t.id);
            }

            return res;
        }
    }


    /* =========================
       Program 10: Multi Level Cache
       ========================= */

    static class MultiCache{

        LinkedHashMap<String,String> L1 =
                new LinkedHashMap<>(10000,0.75f,true);

        HashMap<String,String> L2 = new HashMap<>();

        String getVideo(String id){

            if(L1.containsKey(id)){
                return L1.get(id);
            }

            if(L2.containsKey(id)){

                String data=L2.get(id);

                L1.put(id,data);

                return data;
            }

            String data="VideoData:"+id;

            L2.put(id,data);

            return data;
        }
    }


    /* =========================
       Main Method
       ========================= */

    public static void main(String[] args) {

        System.out.println("All Programs Loaded Successfully");

        UsernameChecker uc = new UsernameChecker();
        uc.register("john_doe",1);
        System.out.println("Available: "+uc.checkAvailability("john_doe"));

        InventoryManager im = new InventoryManager();
        im.addProduct("IPHONE",100);
        System.out.println("Stock: "+im.checkStock("IPHONE"));

        DNSCache dns = new DNSCache();
        System.out.println("DNS: "+dns.resolve("google.com"));

        RateLimiter rl = new RateLimiter();
        System.out.println("Rate Limit: "+rl.checkRateLimit("client1"));

        ParkingLot pl = new ParkingLot(500);
        System.out.println("Parked at spot "+pl.parkVehicle("ABC123"));
    }

}